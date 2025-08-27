package hunglcb.example.projectmd3.repository.order;

import hunglcb.example.projectmd3.dto.CustomerOrderDTO;
import hunglcb.example.projectmd3.dto.OrderItemDTO;
import hunglcb.example.projectmd3.repository.ConnectionDB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    @Override
    public Integer createOrder(Integer accountId, Integer shippingAddressId, Integer paymentMethodId, BigDecimal totalAmount) {
        String sql = "INSERT INTO orders (account_id, shipping_address_id, status, total_amount, payment_method_id, created_at) VALUES (?, ?, 'pending', ?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, accountId);
            if (shippingAddressId == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, shippingAddressId);
            ps.setBigDecimal(3, totalAmount);
            if (paymentMethodId == null) ps.setNull(4, Types.INTEGER); else ps.setInt(4, paymentMethodId);
            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean insertItemsFromCart(Integer accountId, Integer orderId) {
        String sql = "INSERT INTO order_items (order_id, product_id, size_id, quantity, price)\n" +
                "SELECT ?, ci.product_id, ci.size_id, ci.quantity, (p.price + IFNULL(ps.price_adjustment,0))\n" +
                "FROM carts c\n" +
                "JOIN cart_items ci ON ci.cart_id = c.id\n" +
                "JOIN products p ON p.id = ci.product_id\n" +
                "LEFT JOIN product_sizes ps ON ps.id = ci.size_id\n" +
                "WHERE c.account_id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, accountId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean clearCart(Integer accountId) {
        String sql = "DELETE ci FROM cart_items ci JOIN carts c ON ci.cart_id = c.id WHERE c.account_id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean updateStatus(Integer orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public List<CustomerOrderDTO> findAllOrders() throws SQLException {
        String sql = "SELECT o.id AS order_id, o.status AS order_status, o.total_amount, o.created_at AS order_date, " +
                "a.id AS account_id, a.email, up.full_name, up.phone, " +
                "ua.address_line, ua.city, ua.state, ua.postal_code, ua.country " +
                "FROM orders o " +
                "JOIN accounts a ON o.account_id = a.id " +
                "LEFT JOIN user_profiles up ON a.id = up.account_id " +
                "LEFT JOIN user_addresses ua ON o.shipping_address_id = ua.id " +
                "ORDER BY o.created_at DESC";

        List<CustomerOrderDTO> orders = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnectDB();
            Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CustomerOrderDTO order = new CustomerOrderDTO();
                order.setOrderId(rs.getLong("order_id"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                order.setAccountId(rs.getLong("account_id"));
                order.setEmail(rs.getString("email"));
                order.setFullName(rs.getString("full_name"));
                order.setPhone(rs.getString("phone"));
                order.setAddressLine(rs.getString("address_line"));
                order.setCity(rs.getString("city"));
                order.setState(rs.getString("state"));
                order.setPostalCode(rs.getString("postal_code"));
                order.setCountry(rs.getString("country"));

                // Lấy danh sách sản phẩm
                order.setItems(findOrderItemsByOrderId(order.getOrderId()));

                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public List<OrderItemDTO> findOrderItemsByOrderId(Long orderId) throws SQLException {
        String sql = "SELECT oi.product_id, p.name AS product_name, oi.quantity, oi.price, ps.size, p.image_url " +
                "FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.id " +
                "LEFT JOIN product_sizes ps ON oi.size_id = ps.id " +
                "WHERE oi.order_id = ?";
        List<OrderItemDTO> items = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnectDB();

            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderItemDTO item = new OrderItemDTO();
                item.setProductId(rs.getLong("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setSize(rs.getString("size"));
                item.setImageUrl(rs.getString("image_url"));
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public boolean updateOrderStatus(Long orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setLong(2, orderId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public CustomerOrderDTO findOrderById(Long orderId) throws SQLException {
        String sql = "SELECT o.id AS order_id, o.status AS order_status, o.total_amount, o.created_at AS order_date, " +
                "a.id AS account_id, a.email, up.full_name, up.phone, " +
                "ua.address_line, ua.city, ua.state, ua.postal_code, ua.country " +
                "FROM orders o " +
                "JOIN accounts a ON o.account_id = a.id " +
                "LEFT JOIN user_profiles up ON a.id = up.account_id " +
                "LEFT JOIN user_addresses ua ON o.shipping_address_id = ua.id " +
                "WHERE o.id = ?";

        CustomerOrderDTO order = null;
        try (Connection conn = ConnectionDB.getConnectDB();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                order = new CustomerOrderDTO();
                order.setOrderId(rs.getLong("order_id"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                order.setAccountId(rs.getLong("account_id"));
                order.setEmail(rs.getString("email"));
                order.setFullName(rs.getString("full_name"));
                order.setPhone(rs.getString("phone"));
                order.setAddressLine(rs.getString("address_line"));
                order.setCity(rs.getString("city"));
                order.setState(rs.getString("state"));
                order.setPostalCode(rs.getString("postal_code"));
                order.setCountry(rs.getString("country"));

                order.setItems(findOrderItemsByOrderId(order.getOrderId()));
            }
        }
        return order;
    }
}


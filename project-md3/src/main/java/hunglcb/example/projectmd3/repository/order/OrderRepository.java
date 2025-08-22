package hunglcb.example.projectmd3.repository.order;

import hunglcb.example.projectmd3.model.Order;
import hunglcb.example.projectmd3.model.OrderItem;
import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.ProductSize;
import hunglcb.example.projectmd3.model.UserAddress;
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
    public List<Order> getOrdersByAccountId(Integer accountId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, pm.name as payment_method_name " +
                    "FROM orders o " +
                    "LEFT JOIN payment_methods pm ON o.payment_method_id = pm.id " +
                    "WHERE o.account_id = ? " +
                    "ORDER BY o.created_at DESC";
        
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setAccountId(rs.getInt("account_id"));
                order.setShippingAddressId(rs.getInt("shipping_address_id"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setPaymentMethodId(rs.getInt("payment_method_id"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setPaymentMethodName(rs.getString("payment_method_name"));
                
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT o.*, pm.name as payment_method_name " +
                    "FROM orders o " +
                    "LEFT JOIN payment_methods pm ON o.payment_method_id = pm.id " +
                    "WHERE o.id = ?";
        
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setAccountId(rs.getInt("account_id"));
                order.setShippingAddressId(rs.getInt("shipping_address_id"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setPaymentMethodId(rs.getInt("payment_method_id"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setPaymentMethodName(rs.getString("payment_method_name"));
                
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.*, p.name as product_name, p.image_url, p.description, " +
                    "ps.size, c.name as category_name, b.name as brand_name " +
                    "FROM order_items oi " +
                    "JOIN products p ON oi.product_id = p.id " +
                    "LEFT JOIN product_sizes ps ON oi.size_id = ps.id " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
                    "LEFT JOIN brands b ON p.brand_id = b.id " +
                    "WHERE oi.order_id = ?";
        
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getInt("id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setSizeId(rs.getInt("size_id"));
                
                // Create Product object
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setImageUrl(rs.getString("image_url"));
                product.setDescription(rs.getString("description"));
                product.setCategoryName(rs.getString("category_name"));
                product.setBrandName(rs.getString("brand_name"));
                
                item.setProduct(product);
                
                // Create ProductSize object if exists
                if (rs.getInt("size_id") != 0) {
                    ProductSize size = new ProductSize();
                    size.setId(rs.getInt("size_id"));
                    size.setSize(rs.getString("size"));
                    item.setProductSize(size);
                }
                
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public Order getOrderWithDetails(Integer orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            List<OrderItem> items = getOrderItemsByOrderId(orderId);
            order.setOrderItems(items);
            
            // Get shipping address if exists
            if (order.getShippingAddressId() != null) {
                UserAddress shippingAddress = getShippingAddressById(order.getShippingAddressId());
                order.setShippingAddress(shippingAddress);
            }
        }
        return order;
    }
    
    private UserAddress getShippingAddressById(Integer addressId) {
        String sql = "SELECT * FROM user_addresses WHERE id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addressId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                UserAddress address = new UserAddress();
                address.setId(rs.getInt("id"));
                address.setAccountId(rs.getInt("account_id"));
                address.setAddressLine(rs.getString("address_line"));
                address.setCity(rs.getString("city"));
                address.setState(rs.getString("state"));
                address.setPostalCode(rs.getString("postal_code"));
                address.setCountry(rs.getString("country"));
                address.setIsDefault(rs.getBoolean("is_default"));
                address.setCreatedAt(rs.getTimestamp("created_at"));
                return address;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}



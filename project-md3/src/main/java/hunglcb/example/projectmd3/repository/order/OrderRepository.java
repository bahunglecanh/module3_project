package hunglcb.example.projectmd3.repository.order;

import hunglcb.example.projectmd3.repository.ConnectionDB;

import java.math.BigDecimal;
import java.sql.*;

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
}



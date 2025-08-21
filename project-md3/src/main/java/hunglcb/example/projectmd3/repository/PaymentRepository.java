package hunglcb.example.projectmd3.repository;

import java.sql.*;

public class PaymentRepository implements IPaymentRepository {
    @Override
    public Integer findIdByName(String name) {
        String sql = "SELECT id FROM payment_methods WHERE name = ? AND is_active = 1 LIMIT 1";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}



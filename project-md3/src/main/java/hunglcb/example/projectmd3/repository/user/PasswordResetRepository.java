package hunglcb.example.projectmd3.repository.user;

import hunglcb.example.projectmd3.repository.ConnectionDB;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class PasswordResetRepository implements IPasswordResetRepository {

    @Override
    public boolean ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS password_resets (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "email VARCHAR(100) NOT NULL, " +
                "otp_code VARCHAR(10) NOT NULL, " +
                "expires_at DATETIME NOT NULL, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "INDEX idx_email (email)" +
                ")";
        try (Connection conn = ConnectionDB.getConnectDB();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createOrUpdateOtp(String email, String otpCode, LocalDateTime expiresAt) {
        ensureTable();
        String deleteSql = "DELETE FROM password_resets WHERE email = ?";
        String insertSql = "INSERT INTO password_resets (email, otp_code, expires_at) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnectDB()) {
            conn.setAutoCommit(false);
            try (PreparedStatement del = conn.prepareStatement(deleteSql)) {
                del.setString(1, email);
                del.executeUpdate();
            }
            try (PreparedStatement ins = conn.prepareStatement(insertSql)) {
                ins.setString(1, email);
                ins.setString(2, otpCode);
                ins.setTimestamp(3, Timestamp.valueOf(expiresAt));
                int r = ins.executeUpdate();
                conn.commit();
                return r > 0;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getValidOtp(String email) {
        ensureTable();
        String sql = "SELECT otp_code FROM password_resets WHERE email = ? AND expires_at > NOW() ORDER BY id DESC LIMIT 1";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("otp_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LocalDateTime getExpiry(String email) {
        String sql = "SELECT expires_at FROM password_resets WHERE email = ? ORDER BY id DESC LIMIT 1";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("expires_at");
                if (ts != null) {
                    return ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean invalidate(String email) {
        String sql = "DELETE FROM password_resets WHERE email = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}



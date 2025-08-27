package hunglcb.example.projectmd3.repository.address;

import hunglcb.example.projectmd3.model.UserAddress;
import hunglcb.example.projectmd3.repository.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository implements IAddressRepository {
    @Override
    public List<UserAddress> findByAccountId(Integer accountId) {
        String sql = "SELECT * FROM user_addresses WHERE account_id = ? ORDER BY is_default DESC, created_at DESC";
        List<UserAddress> list = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public UserAddress findDefaultByAccountId(Integer accountId) {
        String sql = "SELECT * FROM user_addresses WHERE account_id = ? AND is_default = 1 LIMIT 1";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean save(UserAddress address) {
        String sql = "INSERT INTO user_addresses (account_id, address_line, city, state, postal_code, country, is_default, created_at) VALUES (?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, address.getAccountId());
            ps.setString(2, address.getAddressLine());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getState());
            ps.setString(5, address.getPostalCode());
            ps.setString(6, address.getCountry());
            ps.setBoolean(7, Boolean.TRUE.equals(address.getIsDefault()));
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        address.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean setDefault(Integer accountId, Integer addressId) {
        String unsetSql = "UPDATE user_addresses SET is_default = 0 WHERE account_id = ?";
        String setSql = "UPDATE user_addresses SET is_default = 1 WHERE id = ? AND account_id = ?";
        try (Connection conn = ConnectionDB.getConnectDB()) {
            try (PreparedStatement ps = conn.prepareStatement(unsetSql)) {
                ps.setInt(1, accountId);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(setSql)) {
                ps.setInt(1, addressId);
                ps.setInt(2, accountId);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean unsetDefault(Integer accountId) {
        String sql = "UPDATE user_addresses SET is_default = 0 WHERE account_id = ? AND is_default = 1";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean deleteById(Integer accountId, Integer addressId) {
        String sql = "DELETE FROM user_addresses WHERE id = ? AND account_id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addressId);
            ps.setInt(2, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private UserAddress map(ResultSet rs) throws SQLException {
        UserAddress a = new UserAddress();
        a.setId(rs.getInt("id"));
        a.setAccountId(rs.getInt("account_id"));
        a.setAddressLine(rs.getString("address_line"));
        a.setCity(rs.getString("city"));
        a.setState(rs.getString("state"));
        a.setPostalCode(rs.getString("postal_code"));
        a.setCountry(rs.getString("country"));
        a.setIsDefault(rs.getBoolean("is_default"));
        a.setCreatedAt(rs.getTimestamp("created_at"));
        return a;
    }
}



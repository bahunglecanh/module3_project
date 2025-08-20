package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Account;
import hunglcb.example.projectmd3.model.User;

import java.sql.*;

public class UserRepository implements IUserRepository {
    
    @Override
    public Account authenticateByEmail(String email, String password) {
        String sql = "SELECT * FROM accounts WHERE email = ? AND password_hash = ? AND status = 'active'";
        
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setEmail(rs.getString("email"));
                account.setPasswordHash(rs.getString("password_hash"));
                
                // Simple role conversion
                String roleStr = rs.getString("role");
                if ("admin".equalsIgnoreCase(roleStr)) {
                    account.setRole(Account.Role.ADMIN);
                } else {
                    account.setRole(Account.Role.USER);
                }
                
                // Status conversion from ENUM
                String statusStr = rs.getString("status");
                if ("active".equalsIgnoreCase(statusStr)) {
                    account.setStatus(Account.Status.ACTIVE);
                } else if ("banned".equalsIgnoreCase(statusStr)) {
                    account.setStatus(Account.Status.BANNED);
                } else {
                    account.setStatus(Account.Status.INACTIVE);
                }
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public User findUserByEmail(String email) {
        // JOIN accounts with user_profiles (correct table name)
        String sql = "SELECT a.*, up.full_name, up.phone, up.avatar_url FROM accounts a " +
                    "LEFT JOIN user_profiles up ON a.id = up.account_id " +
                    "WHERE a.email = ? AND a.status = 'active'";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(email);
                
                // Get full_name from user_profiles, fallback to email prefix
                String fullName = rs.getString("full_name");
                if (fullName == null || fullName.isEmpty()) {
                    fullName = email.split("@")[0];
                }
                user.setFullName(fullName);
                user.setPhone(rs.getString("phone"));
                user.setAvatarUrl(rs.getString("avatar_url"));
                
                // Role conversion
                String roleStr = rs.getString("role");
                if ("admin".equalsIgnoreCase(roleStr)) {
                    user.setRole(Account.Role.ADMIN);
                } else {
                    user.setRole(Account.Role.USER);
                }
                
                // Status conversion
                String statusStr = rs.getString("status");
                if ("active".equalsIgnoreCase(statusStr)) {
                    user.setStatus(Account.Status.ACTIVE);
                } else if ("banned".equalsIgnoreCase(statusStr)) {
                    user.setStatus(Account.Status.BANNED);
                } else {
                    user.setStatus(Account.Status.INACTIVE);
                }
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public User findUserById(int id) {
        String sql = "SELECT a.*, up.full_name, up.phone, up.avatar_url FROM accounts a " +
                    "LEFT JOIN user_profiles up ON a.id = up.account_id " +
                    "WHERE a.id = ? AND a.status = 'active'";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                
                // Get full_name from user_profiles, fallback to email prefix
                String fullName = rs.getString("full_name");
                if (fullName == null || fullName.isEmpty()) {
                    fullName = rs.getString("email").split("@")[0];
                }
                user.setFullName(fullName);
                user.setPhone(rs.getString("phone"));
                user.setAvatarUrl(rs.getString("avatar_url"));
                
                // Role conversion
                String roleStr = rs.getString("role");
                if ("admin".equalsIgnoreCase(roleStr)) {
                    user.setRole(Account.Role.ADMIN);
                } else {
                    user.setRole(Account.Role.USER);
                }
                
                // Status conversion
                String statusStr = rs.getString("status");
                if ("active".equalsIgnoreCase(statusStr)) {
                    user.setStatus(Account.Status.ACTIVE);
                } else if ("banned".equalsIgnoreCase(statusStr)) {
                    user.setStatus(Account.Status.BANNED);
                } else {
                    user.setStatus(Account.Status.INACTIVE);
                }
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE email = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean saveUser(User user) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnectDB();
            conn.setAutoCommit(false);
            
            // Insert account first - use correct column names
            String accountSql = "INSERT INTO accounts (email, password_hash, role, status) VALUES (?, ?, 'user', 'active')";
            PreparedStatement accountStmt = conn.prepareStatement(accountSql, Statement.RETURN_GENERATED_KEYS);
            accountStmt.setString(1, user.getEmail());
            accountStmt.setString(2, user.getPasswordHash());
            
            int rowsAffected = accountStmt.executeUpdate();
            if (rowsAffected == 0) {
                conn.rollback();
                return false;
            }
            
            // Get generated account ID
            ResultSet generatedKeys = accountStmt.getGeneratedKeys();
            int accountId;
            if (generatedKeys.next()) {
                accountId = generatedKeys.getInt(1);
            } else {
                conn.rollback();
                return false;
            }
            
            // Insert user profile (correct table name: user_profiles)
            String userSql = "INSERT INTO user_profiles (account_id, full_name, phone, avatar_url) VALUES (?, ?, ?, ?)";
            PreparedStatement userStmt = conn.prepareStatement(userSql);
            userStmt.setInt(1, accountId);
            userStmt.setString(2, user.getFullName());
            userStmt.setString(3, user.getPhone());
            userStmt.setString(4, user.getAvatarUrl());
            
            int userRows = userStmt.executeUpdate();
            if (userRows > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE user_profiles SET full_name = ?, phone = ?, avatar_url = ? WHERE account_id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getAvatarUrl());
            stmt.setInt(4, user.getId());  // user.getId() is account_id
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean deleteAccount(int userId) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnectDB();
            conn.setAutoCommit(false);
            
            // Delete user profile first (CASCADE will handle this, but explicit is better)
            String deleteProfileSql = "DELETE FROM user_profiles WHERE account_id = ?";
            PreparedStatement deleteProfileStmt = conn.prepareStatement(deleteProfileSql);
            deleteProfileStmt.setInt(1, userId);  // userId is actually account_id
            deleteProfileStmt.executeUpdate();
            
            // Delete account (this will cascade delete user_profiles due to FK)
            String deleteAccountSql = "DELETE FROM accounts WHERE id = ?";
            PreparedStatement deleteAccountStmt = conn.prepareStatement(deleteAccountSql);
            deleteAccountStmt.setInt(1, userId);
            int accountRows = deleteAccountStmt.executeUpdate();
            
            if (accountRows > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}

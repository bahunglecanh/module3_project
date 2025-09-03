package hunglcb.example.projectmd3.repository.user;

import hunglcb.example.projectmd3.dto.UserDTO;
import hunglcb.example.projectmd3.model.Account;
import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.model.UserProfile;
import hunglcb.example.projectmd3.repository.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    @Override
    public List<UserDTO> findAllUser() {
        List<UserDTO> users = new ArrayList<>();
                        String sql = "SELECT a.id, a.email, a.role, a.status, a.created_at, " +
                "p.full_name, p.phone, p.gender " +
                "FROM accounts a " +
                "LEFT JOIN user_profiles p ON a.id = p.account_id " +
                "WHERE a.status IN ('active', 'banned') " +
                "ORDER BY a.created_at DESC";

        try (Connection connection = ConnectionDB.getConnectDB()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                Account.Role role = Account.Role.valueOf(resultSet.getString("role").toUpperCase());
                Account.Status status = Account.Status.valueOf(resultSet.getString("status").toUpperCase());
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                String fullName = resultSet.getString("full_name");
                String phone = resultSet.getString("phone");

                UserProfile.Gender gender = null;
                String genderStr = resultSet.getString("gender");
                if (genderStr != null) {
                    gender = UserProfile.Gender.valueOf(genderStr.toUpperCase());
                }

                users.add(new UserDTO(id, email, role, status, createdAt, fullName, phone, gender));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi query findAllUser");
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public List<UserDTO> searchByName(String fullName) {
        String sql = "SELECT a.id, a.email, a.role, a.status, a.created_at, " +
                "p.full_name, p.phone, p.gender " +
                "FROM accounts a " +
                "LEFT JOIN user_profiles p ON a.id = p.account_id " +
                "WHERE a.status IN ('active', 'banned') " +
                "AND p.full_name LIKE CONCAT('%',?,'%') " +
                "ORDER BY a.created_at DESC";
        List<UserDTO> users = new ArrayList<>();
        try(Connection connection = ConnectionDB.getConnectDB()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fullName);
            ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            Account.Role role = Account.Role.valueOf(resultSet.getString("role").toUpperCase());
            Account.Status status = Account.Status.valueOf(resultSet.getString("status").toUpperCase());
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            String actualFullName = resultSet.getString("full_name");
            String phone = resultSet.getString("phone");

            UserProfile.Gender gender = null;
            String genderStr = resultSet.getString("gender");
            if (genderStr != null) {
                gender = UserProfile.Gender.valueOf(genderStr.toUpperCase());
            }

            users.add(new UserDTO(id, email, role, status, createdAt, actualFullName, phone, gender));
        }
    } catch (SQLException e) {
        System.out.println("Lỗi query findAllUser");
        e.printStackTrace();
    }

        return users;
    }


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
        String sql = "SELECT a.*, up.id AS profile_id, up.full_name, up.phone, up.gender, up.birth_date, up.avatar_url FROM accounts a " +
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

                // Profile fields
                int profileId = rs.getInt("profile_id");
                if (!rs.wasNull()) {
                    user.setProfileId(profileId);
                }
                String fullName = rs.getString("full_name");
                if (fullName == null || fullName.isEmpty()) {
                    fullName = email.split("@")[0];
                }
                user.setFullName(fullName);
                user.setPhone(rs.getString("phone"));
                String genderStr = rs.getString("gender");
                if (genderStr != null) {
                    if ("male".equalsIgnoreCase(genderStr)) {
                        user.setGender(hunglcb.example.projectmd3.model.UserProfile.Gender.MALE);
                    } else if ("female".equalsIgnoreCase(genderStr)) {
                        user.setGender(hunglcb.example.projectmd3.model.UserProfile.Gender.FEMALE);
                    } else if ("other".equalsIgnoreCase(genderStr)) {
                        user.setGender(hunglcb.example.projectmd3.model.UserProfile.Gender.OTHER);
                    }
                }
                user.setBirthDate(rs.getDate("birth_date"));
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
        String sql = "SELECT a.*, up.id AS profile_id, up.full_name, up.phone, up.gender, up.birth_date, up.avatar_url FROM accounts a " +
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

                int profileId = rs.getInt("profile_id");
                if (!rs.wasNull()) {
                    user.setProfileId(profileId);
                }
                String fullName = rs.getString("full_name");
                if (fullName == null || fullName.isEmpty()) {
                    fullName = rs.getString("email").split("@")[0];
                }
                user.setFullName(fullName);
                user.setPhone(rs.getString("phone"));
                String genderStr = rs.getString("gender");
                if (genderStr != null) {
                    if ("male".equalsIgnoreCase(genderStr)) {
                        user.setGender(hunglcb.example.projectmd3.model.UserProfile.Gender.MALE);
                    } else if ("female".equalsIgnoreCase(genderStr)) {
                        user.setGender(hunglcb.example.projectmd3.model.UserProfile.Gender.FEMALE);
                    } else if ("other".equalsIgnoreCase(genderStr)) {
                        user.setGender(hunglcb.example.projectmd3.model.UserProfile.Gender.OTHER);
                    }
                }
                user.setBirthDate(rs.getDate("birth_date"));
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
        String updateSql = "UPDATE user_profiles SET full_name = ?, phone = ?, gender = ?, birth_date = ?, avatar_url = COALESCE(?, avatar_url) WHERE account_id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            updateStmt.setString(1, user.getFullName());
            updateStmt.setString(2, user.getPhone());
            String genderVal = null;
            if (user.getGender() != null) {
                switch (user.getGender()) {
                    case MALE:
                        genderVal = "male";
                        break;
                    case FEMALE:
                        genderVal = "female";
                        break;
                    case OTHER:
                        genderVal = "other";
                        break;
                }
            }
            updateStmt.setString(3, genderVal);
            updateStmt.setDate(4, user.getBirthDate());
            updateStmt.setString(5, user.getAvatarUrl());
            updateStmt.setInt(6, user.getId());
            int rows = updateStmt.executeUpdate();
            if (rows > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If no row was updated, insert new profile
        String insertSql = "INSERT INTO user_profiles (account_id, full_name, phone, gender, birth_date, avatar_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            insertStmt.setInt(1, user.getId());
            insertStmt.setString(2, user.getFullName());
            insertStmt.setString(3, user.getPhone());
            String genderVal = null;
            if (user.getGender() != null) {
                switch (user.getGender()) {
                    case MALE:
                        genderVal = "male";
                        break;
                    case FEMALE:
                        genderVal = "female";
                        break;
                    case OTHER:
                        genderVal = "other";
                        break;
                }
            }
            insertStmt.setString(4, genderVal);
            insertStmt.setDate(5, user.getBirthDate());
            insertStmt.setString(6, user.getAvatarUrl());
            return insertStmt.executeUpdate() > 0;
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

    @Override
    public boolean banUser(int userId) {
        String sql = "UPDATE accounts SET status = 'banned', updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean unbanUser(int userId) {
        String sql = "UPDATE accounts SET status = 'active', updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePasswordByEmail(String email, String newPasswordHash) {
        String sql = "UPDATE accounts SET password_hash = ?, updated_at = CURRENT_TIMESTAMP WHERE email = ? AND status = 'active'";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPasswordHash);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

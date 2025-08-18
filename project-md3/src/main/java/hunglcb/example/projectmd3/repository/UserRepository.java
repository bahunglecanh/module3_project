package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Account;
import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.model.UserProfile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of User repository using accounts and user_profiles tables
 */
public class UserRepository implements IUserRepository {

    @Override
    public boolean saveAccount(Account account) {
        String sql = "INSERT INTO accounts (email, password_hash, role, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, account.getEmail());
            statement.setString(2, account.getPasswordHash());
            statement.setString(3, account.getRole().name().toLowerCase());
            statement.setString(4, account.getStatus().name().toLowerCase());
            statement.setTimestamp(5, account.getCreatedAt());
            statement.setTimestamp(6, account.getUpdatedAt());
            
            int result = statement.executeUpdate();
            if (result > 0) {
                // Get generated ID
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    account.setId(rs.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account findAccountById(Integer id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToAccount(resultSet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account findAccountByEmail(String email) {
        String sql = "SELECT * FROM accounts WHERE email = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToAccount(resultSet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateAccount(Account account) {
        String sql = "UPDATE accounts SET email = ?, password_hash = ?, role = ?, status = ?, updated_at = ? WHERE id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            account.updateTimestamp();
            
            statement.setString(1, account.getEmail());
            statement.setString(2, account.getPasswordHash());
            statement.setString(3, account.getRole().name().toLowerCase());
            statement.setString(4, account.getStatus().name().toLowerCase());
            statement.setTimestamp(5, account.getUpdatedAt());
            statement.setInt(6, account.getId());
            
            return statement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAccount(Integer id) {
        String sql = "UPDATE accounts SET status = 'inactive', updated_at = ? WHERE id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setInt(2, id);
            
            return statement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE email = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account authenticateByEmail(String email, String passwordHash) {
        String sql = "SELECT * FROM accounts WHERE email = ? AND password_hash = ? AND status = 'active'";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, email);
            statement.setString(2, passwordHash);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToAccount(resultSet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> findAccountsByRole(Account.Role role) {
        String sql = "SELECT * FROM accounts WHERE role = ? AND status = 'active' ORDER BY created_at DESC";
        List<Account> accounts = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, role.name().toLowerCase());
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                accounts.add(mapResultSetToAccount(resultSet));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean saveProfile(UserProfile profile) {
        String sql = "INSERT INTO user_profiles (account_id, full_name, phone, gender, birth_date, avatar_url) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setInt(1, profile.getAccountId());
            statement.setString(2, profile.getFullName());
            statement.setString(3, profile.getPhone());
            statement.setString(4, profile.getGender() != null ? profile.getGender().name().toLowerCase() : null);
            statement.setDate(5, profile.getBirthDate());
            statement.setString(6, profile.getAvatarUrl());
            
            int result = statement.executeUpdate();
            if (result > 0) {
                // Get generated ID
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    profile.setId(rs.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserProfile findProfileByAccountId(Integer accountId) {
        String sql = "SELECT * FROM user_profiles WHERE account_id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToProfile(resultSet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateProfile(UserProfile profile) {
        String sql = "UPDATE user_profiles SET full_name = ?, phone = ?, gender = ?, birth_date = ?, avatar_url = ? WHERE account_id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, profile.getFullName());
            statement.setString(2, profile.getPhone());
            statement.setString(3, profile.getGender() != null ? profile.getGender().name().toLowerCase() : null);
            statement.setDate(4, profile.getBirthDate());
            statement.setString(5, profile.getAvatarUrl());
            statement.setInt(6, profile.getAccountId());
            
            return statement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProfile(Integer accountId) {
        String sql = "DELETE FROM user_profiles WHERE account_id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, accountId);
            return statement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User findUserById(Integer id) {
        String sql = "SELECT a.*, p.id as profile_id, p.full_name, p.phone, p.gender, p.birth_date, p.avatar_url " +
                    "FROM accounts a LEFT JOIN user_profiles p ON a.id = p.account_id WHERE a.id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        String sql = "SELECT a.*, p.id as profile_id, p.full_name, p.phone, p.gender, p.birth_date, p.avatar_url " +
                    "FROM accounts a LEFT JOIN user_profiles p ON a.id = p.account_id WHERE a.email = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "SELECT a.*, p.id as profile_id, p.full_name, p.phone, p.gender, p.birth_date, p.avatar_url " +
                    "FROM accounts a LEFT JOIN user_profiles p ON a.id = p.account_id WHERE a.status = 'active' ORDER BY a.created_at DESC";
        List<User> users = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> findUsersByRole(Account.Role role) {
        String sql = "SELECT a.*, p.id as profile_id, p.full_name, p.phone, p.gender, p.birth_date, p.avatar_url " +
                    "FROM accounts a LEFT JOIN user_profiles p ON a.id = p.account_id WHERE a.role = ? AND a.status = 'active' ORDER BY a.created_at DESC";
        List<User> users = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, role.name().toLowerCase());
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean saveUser(User user) {
        Connection connection = null;
        try {
            connection = ConnectionDB.getConnectDB();
            connection.setAutoCommit(false); // Start transaction
            
            // 1. Save account
            Account account = user.toAccount();
            if (!saveAccountInTransaction(account, connection)) {
                connection.rollback();
                return false;
            }
            
            // 2. Save profile if fullName is provided
            if (user.getFullName() != null && !user.getFullName().trim().isEmpty()) {
                UserProfile profile = user.toUserProfile();
                profile.setAccountId(account.getId());
                if (!saveProfileInTransaction(profile, connection)) {
                    connection.rollback();
                    return false;
                }
            }
            
            connection.commit();
            user.setId(account.getId()); // Set the generated ID
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        Connection connection = null;
        try {
            connection = ConnectionDB.getConnectDB();
            connection.setAutoCommit(false); // Start transaction
            
            // 1. Update account
            if (!updateAccountInTransaction(user.toAccount(), connection)) {
                connection.rollback();
                return false;
            }
            
            // 2. Update or create profile
            UserProfile existingProfile = findProfileByAccountId(user.getId());
            if (existingProfile != null) {
                // Update existing profile
                if (!updateProfileInTransaction(user.toUserProfile(), connection)) {
                    connection.rollback();
                    return false;
                }
            } else if (user.getFullName() != null && !user.getFullName().trim().isEmpty()) {
                // Create new profile
                UserProfile profile = user.toUserProfile();
                profile.setAccountId(user.getId());
                if (!saveProfileInTransaction(profile, connection)) {
                    connection.rollback();
                    return false;
                }
            }
            
            connection.commit();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    // Helper methods for transactions
    private boolean saveAccountInTransaction(Account account, Connection connection) throws SQLException {
        String sql = "INSERT INTO accounts (email, password_hash, role, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getEmail());
            statement.setString(2, account.getPasswordHash());
            statement.setString(3, account.getRole().name().toLowerCase());
            statement.setString(4, account.getStatus().name().toLowerCase());
            statement.setTimestamp(5, account.getCreatedAt());
            statement.setTimestamp(6, account.getUpdatedAt());
            
            int result = statement.executeUpdate();
            if (result > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    account.setId(rs.getInt(1));
                }
                return true;
            }
        }
        return false;
    }

    private boolean saveProfileInTransaction(UserProfile profile, Connection connection) throws SQLException {
        String sql = "INSERT INTO user_profiles (account_id, full_name, phone, gender, birth_date, avatar_url) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, profile.getAccountId());
            statement.setString(2, profile.getFullName());
            statement.setString(3, profile.getPhone());
            statement.setString(4, profile.getGender() != null ? profile.getGender().name().toLowerCase() : null);
            statement.setDate(5, profile.getBirthDate());
            statement.setString(6, profile.getAvatarUrl());
            
            int result = statement.executeUpdate();
            if (result > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    profile.setId(rs.getInt(1));
                }
                return true;
            }
        }
        return false;
    }

    private boolean updateAccountInTransaction(Account account, Connection connection) throws SQLException {
        String sql = "UPDATE accounts SET email = ?, password_hash = ?, role = ?, status = ?, updated_at = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            account.updateTimestamp();
            
            statement.setString(1, account.getEmail());
            statement.setString(2, account.getPasswordHash());
            statement.setString(3, account.getRole().name().toLowerCase());
            statement.setString(4, account.getStatus().name().toLowerCase());
            statement.setTimestamp(5, account.getUpdatedAt());
            statement.setInt(6, account.getId());
            
            return statement.executeUpdate() > 0;
        }
    }

    private boolean updateProfileInTransaction(UserProfile profile, Connection connection) throws SQLException {
        String sql = "UPDATE user_profiles SET full_name = ?, phone = ?, gender = ?, birth_date = ?, avatar_url = ? WHERE account_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, profile.getFullName());
            statement.setString(2, profile.getPhone());
            statement.setString(3, profile.getGender() != null ? profile.getGender().name().toLowerCase() : null);
            statement.setDate(4, profile.getBirthDate());
            statement.setString(5, profile.getAvatarUrl());
            statement.setInt(6, profile.getAccountId());
            
            return statement.executeUpdate() > 0;
        }
    }

    // Helper methods to map ResultSet to objects
    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setEmail(resultSet.getString("email"));
        account.setPasswordHash(resultSet.getString("password_hash"));
        account.setRole(Account.Role.valueOf(resultSet.getString("role").toUpperCase()));
        account.setStatus(Account.Status.valueOf(resultSet.getString("status").toUpperCase()));
        account.setCreatedAt(resultSet.getTimestamp("created_at"));
        account.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return account;
    }

    private UserProfile mapResultSetToProfile(ResultSet resultSet) throws SQLException {
        UserProfile profile = new UserProfile();
        profile.setId(resultSet.getInt("id"));
        profile.setAccountId(resultSet.getInt("account_id"));
        profile.setFullName(resultSet.getString("full_name"));
        profile.setPhone(resultSet.getString("phone"));
        
        String gender = resultSet.getString("gender");
        if (gender != null) {
            profile.setGender(UserProfile.Gender.valueOf(gender.toUpperCase()));
        }
        
        profile.setBirthDate(resultSet.getDate("birth_date"));
        profile.setAvatarUrl(resultSet.getString("avatar_url"));
        return profile;
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        
        // Map account data
        user.setId(resultSet.getInt("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPasswordHash(resultSet.getString("password_hash"));
        user.setRole(Account.Role.valueOf(resultSet.getString("role").toUpperCase()));
        user.setStatus(Account.Status.valueOf(resultSet.getString("status").toUpperCase()));
        user.setCreatedAt(resultSet.getTimestamp("created_at"));
        user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        
        // Map profile data (if exists)
        Integer profileId = resultSet.getObject("profile_id", Integer.class);
        if (profileId != null) {
            user.setProfileId(profileId);
            user.setFullName(resultSet.getString("full_name"));
            user.setPhone(resultSet.getString("phone"));
            
            String gender = resultSet.getString("gender");
            if (gender != null) {
                user.setGender(UserProfile.Gender.valueOf(gender.toUpperCase()));
            }
            
            user.setBirthDate(resultSet.getDate("birth_date"));
            user.setAvatarUrl(resultSet.getString("avatar_url"));
        }
        
        return user;
    }
}
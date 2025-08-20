package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Account;
import hunglcb.example.projectmd3.model.User;

public interface IUserRepository {
    
    /**
     * Authenticate user by email and password
     */
    Account authenticateByEmail(String email, String password);
    
    /**
     * Find user by email
     */
    User findUserByEmail(String email);
    
    /**
     * Find user by ID
     */
    User findUserById(int id);
    
    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Save new user
     */
    boolean saveUser(User user);
    
    /**
     * Update user
     */
    boolean updateUser(User user);
    
    /**
     * Delete user account
     */
    boolean deleteAccount(int userId);
}

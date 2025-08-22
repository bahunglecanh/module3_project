package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.service.UserService.ServiceResult;

import java.util.List;

/**
 * Service interface for User business logic operations
 */
public interface IUserService {
    
    /**
     * Register a new user
     * @param email Email
     * @param password Password
     * @param confirmPassword Confirm password
     * @param fullName Full name
     * @return ServiceResult with user data or error message
     */
    ServiceResult<User> register(String email, String password, String confirmPassword, String fullName);
    
    /**
     * Login user with email and password
     * @param email Email
     * @param password Password
     * @return ServiceResult with user data or error message
     */
    ServiceResult<User> login(String email, String password);
    
    /**
     * Get user by ID
     * @param id User ID
     * @return ServiceResult with user data or error message
     */
    ServiceResult<User> getUserById(Long id);
    
    /**
     * Update user profile information
     * @param user Current user
     * @param fullName New full name
     * @param phone New phone number
     * @param address New address
     * @return ServiceResult with updated user data or error message
     */
    ServiceResult<User> updateProfile(User user, String fullName, String phone, String address);
    
    /**
     * Change user password
     * @param user Current user
     * @param currentPassword Current password
     * @param newPassword New password
     * @param confirmPassword Confirm new password
     * @return ServiceResult with success/error message
     */
    ServiceResult<Void> changePassword(User user, String currentPassword, String newPassword, String confirmPassword);
    
    /**
     * Get all users (Admin only)
     * @return ServiceResult with list of users or error message
     */
    ServiceResult<List<User>> getAllUsers();
    
    /**
     * Delete user (Admin only)
     * @param userId User ID to delete
     * @return ServiceResult with success/error message
     */
    ServiceResult<Void> deleteUser(Long userId);
}

package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.model.Account;
import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.repository.IUserRepository;
import hunglcb.example.projectmd3.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Simple service for user operations
 */
public class UserService implements IUserService {
    private IUserRepository userRepository;
    
    // Simple validation patterns
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String PASSWORD_REGEX = "^.{6,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    public UserService() {
        this.userRepository = new UserRepository();
    }

    @Override
    public ServiceResult<User> register(String email, String password, String confirmPassword, String fullName) {
        try {
            // Validate input
            ServiceResult<Void> validationResult = validateRegistrationInput(email, password, confirmPassword, fullName);
            if (!validationResult.isSuccess()) {
                return ServiceResult.error(validationResult.getMessage());
            }

            // Check if email already exists
            if (userRepository.existsByEmail(email)) {
                return ServiceResult.error("Email đã được sử dụng!");
            }

            // Store plain password (matching database format)
            String passwordToStore = password;

            // Create new user
            User newUser = new User(email, passwordToStore, fullName);
            
            // Save to database
            if (userRepository.saveUser(newUser)) {
                // Return user without password
                newUser.setPasswordHash(null);
                return ServiceResult.success(newUser, "Đăng ký thành công!");
            } else {
                return ServiceResult.error("Lỗi tạo tài khoản!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.error("Lỗi hệ thống!");
        }
    }

    @Override
    public ServiceResult<User> login(String email, String password) {
        try {
            // Validate input
            if (email == null || email.trim().isEmpty()) {
                return ServiceResult.error("Vui lòng nhập email!");
            }
            if (password == null || password.trim().isEmpty()) {
                return ServiceResult.error("Vui lòng nhập mật khẩu!");
            }

            // Use plain password for comparison (database stores plain text)
            String passwordToCheck = password;

            // Authenticate user with plain password
            Account account = userRepository.authenticateByEmail(email.trim(), passwordToCheck);
            
            if (account != null) {
                // Get full user info
                User user = userRepository.findUserByEmail(email.trim());
                
                if (user != null) {
                    // Return user without password
                    user.setPasswordHash(null);
                    return ServiceResult.success(user, "Đăng nhập thành công!");
                }
            }
            
            return ServiceResult.error("Email hoặc mật khẩu không đúng!");

        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.error("Lỗi hệ thống!");
        }
    }

    @Override
    public ServiceResult<User> getUserById(Long id) {
        try {
            User user = userRepository.findUserById(id.intValue());
            if (user != null) {
                user.setPasswordHash(null); // Don't return password
                return ServiceResult.success(user, "Lấy thông tin người dùng thành công!");
            } else {
                return ServiceResult.error("Không tìm thấy người dùng!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.error("Lỗi hệ thống!");
        }
    }

    @Override
    public ServiceResult<User> updateProfile(User user, String fullName, String phone, String address) {
        try {
            // Validate input
            if (fullName == null || fullName.trim().isEmpty()) {
                return ServiceResult.error("Vui lòng nhập họ tên!");
            }

            // Update user information
            user.setFullName(fullName.trim());
            user.setPhone(phone != null ? phone.trim() : null);
            // Note: address is now handled in user_addresses table

            // Save to database
            if (userRepository.updateUser(user)) {
                user.setPasswordHash(null); // Don't return password
                return ServiceResult.success(user, "Cập nhật thông tin thành công!");
            } else {
                return ServiceResult.error("Lỗi cập nhật thông tin!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.error("Lỗi hệ thống!");
        }
    }

    @Override
    public ServiceResult<Void> changePassword(User user, String currentPassword, String newPassword, String confirmPassword) {
        try {
            // Validate current password
            String hashedCurrentPassword = hashPassword(currentPassword);
            User existingUser = userRepository.findUserById(user.getId());
            
            if (existingUser == null || !existingUser.getPasswordHash().equals(hashedCurrentPassword)) {
                return ServiceResult.error("Mật khẩu hiện tại không đúng!");
            }

            // Validate new password
            if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
                return ServiceResult.error("Mật khẩu mới phải có ít nhất 6 ký tự!");
            }

            if (!newPassword.equals(confirmPassword)) {
                return ServiceResult.error("Xác nhận mật khẩu không khớp!");
            }

            // Hash new password
            String hashedNewPassword = hashPassword(newPassword);
            if (hashedNewPassword == null) {
                return ServiceResult.error("Lỗi xử lý mật khẩu!");
            }

            // Update password
            existingUser.setPasswordHash(hashedNewPassword);
            
            if (userRepository.updateUser(existingUser)) {
                return ServiceResult.success(null, "Đổi mật khẩu thành công!");
            } else {
                return ServiceResult.error("Lỗi đổi mật khẩu!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.error("Lỗi hệ thống!");
        }
    }

    @Override
    public ServiceResult<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAllUsers();
            // Remove passwords from all users
            users.forEach(user -> user.setPasswordHash(null));
            return ServiceResult.success(users, "Lấy danh sách người dùng thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.error("Lỗi hệ thống!");
        }
    }

    @Override
    public ServiceResult<Void> deleteUser(Long userId) {
        try {
            if (userRepository.deleteAccount(userId.intValue())) {
                return ServiceResult.success(null, "Xóa người dùng thành công!");
            } else {
                return ServiceResult.error("Lỗi xóa người dùng!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResult.error("Lỗi hệ thống!");
        }
    }

    // Validate registration input
    private ServiceResult<Void> validateRegistrationInput(String email, String password, String confirmPassword, String fullName) {
        if (email == null || email.trim().isEmpty()) {
            return ServiceResult.error("Vui lòng nhập email!");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return ServiceResult.error("Email không hợp lệ!");
        }
        if (password == null || password.isEmpty()) {
            return ServiceResult.error("Vui lòng nhập mật khẩu!");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return ServiceResult.error("Mật khẩu phải có ít nhất 6 ký tự!");
        }
        if (!password.equals(confirmPassword)) {
            return ServiceResult.error("Xác nhận mật khẩu không khớp!");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            return ServiceResult.error("Vui lòng nhập họ tên!");
        }
        
        return ServiceResult.success(null, "Validation passed");
    }

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Inner class for service results
    public static class ServiceResult<T> {
        private boolean success;
        private String message;
        private T data;

        private ServiceResult(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public static <T> ServiceResult<T> success(T data, String message) {
            return new ServiceResult<>(true, message, data);
        }

        public static <T> ServiceResult<T> error(String message) {
            return new ServiceResult<>(false, message, null);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public T getData() {
            return data;
        }
    }
}

package hunglcb.example.projectmd3.service.user;

import hunglcb.example.projectmd3.dto.UserDTO;
import hunglcb.example.projectmd3.model.Account;
import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.repository.user.IUserRepository;
import hunglcb.example.projectmd3.repository.user.UserRepository;

import java.util.List;

public class UserService implements IUserService {

    private IUserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }
    @Override
    public User login(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }

        Account account = userRepository.authenticateByEmail(email.trim(), password);
        if (account != null && account.isActive()) {
            // Load full user with profile details if available
            User fullUser = userRepository.findUserById(account.getId());
            if (fullUser != null) {
                return fullUser;
            }
            // Fallback minimal user (should rarely happen)
            User user = new User();
            user.setId(account.getId());
            user.setEmail(account.getEmail());
            user.setRole(account.getRole());
            user.setStatus(account.getStatus());
            user.setFullName(account.getEmail().split("@")[0]);
            return user;
        }
        return null;
    }

    @Override
    public boolean register(User user) {
        if (user == null || user.getEmail() == null || user.getPasswordHash() == null) {
            return false;
        }

        // Check if email exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }

        return userRepository.saveUser(user);
    }

    /**
     * Find user by email
     */
    @Override
    public User findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return userRepository.findUserByEmail(email.trim());
    }


    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findById(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() == 0) {
            return false;
        }
        return userRepository.updateUser(user);
    }

    @Override
        public List<UserDTO> findAllUsers() {
            return userRepository.findAllUser();
        }
    }



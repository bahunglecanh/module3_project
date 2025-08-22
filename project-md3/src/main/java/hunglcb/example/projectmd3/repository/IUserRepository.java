package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.dto.UserDTO;
import hunglcb.example.projectmd3.model.Account;
import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.model.UserProfile;

import java.util.List;

/**
 * Repository interface for User operations (combines Account and UserProfile)
 */
public interface IUserRepository {

    List<UserDTO> findAllUser();
    
    // Account operations
    boolean saveAccount(Account account);
    Account findAccountById(Integer id);
    Account findAccountByEmail(String email);
    boolean updateAccount(Account account);
    boolean deleteAccount(Integer id);
    boolean existsByEmail(String email);
    Account authenticateByEmail(String email, String passwordHash);
    List<Account> findAccountsByRole(Account.Role role);
    
    // Profile operations
    boolean saveProfile(UserProfile profile);
    UserProfile findProfileByAccountId(Integer accountId);
    boolean updateProfile(UserProfile profile);
    boolean deleteProfile(Integer accountId);
    
    // Combined User operations (Account + Profile)
    User findUserById(Integer id);
    User findUserByEmail(String email);
    List<User> findAllUsers();
    List<User> findUsersByRole(Account.Role role);
    boolean saveUser(User user); // Creates both account and profile
    boolean updateUser(User user); // Updates both account and profile
}
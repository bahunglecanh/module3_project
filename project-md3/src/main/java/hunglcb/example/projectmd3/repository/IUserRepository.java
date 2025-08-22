package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Account;
import hunglcb.example.projectmd3.model.User;

public interface IUserRepository {

    Account authenticateByEmail(String email, String password);
    User findUserByEmail(String email);
    User findUserById(int id);
    boolean existsByEmail(String email);
    boolean saveUser(User user);
    boolean updateUser(User user);
    boolean deleteAccount(int userId);
}

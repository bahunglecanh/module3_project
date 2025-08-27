package hunglcb.example.projectmd3.repository.user;

import hunglcb.example.projectmd3.dto.UserDTO;
import hunglcb.example.projectmd3.model.Account;
import hunglcb.example.projectmd3.model.User;

import java.util.List;

public interface IUserRepository {

    List<UserDTO> findAllUser();

    Account authenticateByEmail(String email, String password);
    User findUserByEmail(String email);
    User findUserById(int id);
    boolean existsByEmail(String email);
    boolean saveUser(User user);
    boolean updateUser(User user);
    boolean deleteAccount(int userId);
    boolean updatePasswordByEmail(String email, String newPasswordHash);
}

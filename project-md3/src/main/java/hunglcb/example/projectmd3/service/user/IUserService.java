package hunglcb.example.projectmd3.service.user;

import hunglcb.example.projectmd3.dto.UserDTO;
import hunglcb.example.projectmd3.model.User;

import java.util.List;

public interface IUserService {
    

    User login(String email, String password);
    boolean register(User user);
    User findByEmail(String email);
    boolean emailExists(String email);
    User findById(int id);
    boolean updateUser(User user);
    List<UserDTO> findAllUsers();
    List<UserDTO> searchByName(String fullName);
    boolean banUser(int userId);
    boolean unbanUser(int userId);
    int getTotalUsersCount();
}

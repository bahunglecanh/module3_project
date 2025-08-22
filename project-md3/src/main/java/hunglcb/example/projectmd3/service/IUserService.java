package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.model.User;

public interface IUserService {


    User login(String email, String password);
    boolean register(User user);
    User findByEmail(String email);
    boolean emailExists(String email);
    User findById(int id);
    boolean updateUser(User user);
}
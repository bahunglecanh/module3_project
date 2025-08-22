package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.UserAddress;

import java.util.List;

public interface IAddressRepository {
    List<UserAddress> findByAccountId(Integer accountId);
    UserAddress findDefaultByAccountId(Integer accountId);
    boolean save(UserAddress address);
    boolean setDefault(Integer accountId, Integer addressId);
    boolean unsetDefault(Integer accountId);
    boolean deleteById(Integer accountId, Integer addressId);
}



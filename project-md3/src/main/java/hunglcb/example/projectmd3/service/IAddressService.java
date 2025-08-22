package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.model.UserAddress;

import java.util.List;

public interface IAddressService {
    List<UserAddress> getAddresses(Integer accountId);
    UserAddress getDefaultAddress(Integer accountId);
    boolean addAddress(UserAddress address, boolean setDefault);
    boolean setDefault(Integer accountId, Integer addressId);
    boolean unsetDefault(Integer accountId);
    boolean delete(Integer accountId, Integer addressId);
}



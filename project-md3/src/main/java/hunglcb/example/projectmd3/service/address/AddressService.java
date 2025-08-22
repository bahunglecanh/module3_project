package hunglcb.example.projectmd3.service.address;

import hunglcb.example.projectmd3.model.UserAddress;
import hunglcb.example.projectmd3.repository.address.AddressRepository;
import hunglcb.example.projectmd3.repository.address.IAddressRepository;

import java.util.List;

public class AddressService implements IAddressService {
    private final IAddressRepository repo;

    public AddressService() { this.repo = new AddressRepository(); }
    public AddressService(IAddressRepository repo) { this.repo = repo; }

    @Override
    public List<UserAddress> getAddresses(Integer accountId) {
        return repo.findByAccountId(accountId);
    }

    @Override
    public UserAddress getDefaultAddress(Integer accountId) {
        return repo.findDefaultByAccountId(accountId);
    }

    @Override
    public boolean addAddress(UserAddress address, boolean setDefault) {
        boolean ok = repo.save(address);
        if (ok && setDefault) {
            repo.setDefault(address.getAccountId(), address.getId() != null ? address.getId() : fetchLatestId(address.getAccountId()));
        }
        return ok;
    }

    @Override
    public boolean setDefault(Integer accountId, Integer addressId) {
        return repo.setDefault(accountId, addressId);
    }

    // Helper: get the latest inserted id for account (simple fallback)
    private Integer fetchLatestId(Integer accountId) {
        List<UserAddress> list = repo.findByAccountId(accountId);
        return list.isEmpty() ? null : list.get(0).getId();
    }

    @Override
    public boolean unsetDefault(Integer accountId) {
        return repo.unsetDefault(accountId);
    }

    @Override
    public boolean delete(Integer accountId, Integer addressId) {
        return repo.deleteById(accountId, addressId);
    }
}



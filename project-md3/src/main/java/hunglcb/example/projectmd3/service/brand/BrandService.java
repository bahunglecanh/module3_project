package hunglcb.example.projectmd3.service.brand;

import hunglcb.example.projectmd3.model.Brand;
import hunglcb.example.projectmd3.repository.brand.IBrandRepository;
import hunglcb.example.projectmd3.repository.brand.BrandRepository;

import java.util.List;

public class BrandService implements IBrandService {
    
    IBrandRepository brandRepository=new BrandRepository();

    
    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
    
    @Override
    public List<Brand> getActiveBrands() {
        return brandRepository.findActive();
    }
    
    @Override
    public Brand getBrandById(Integer id) {
        return brandRepository.findById(id);
    }
    
    @Override
    public Brand getBrandByName(String name) {
        return brandRepository.findByName(name);
    }
    
    @Override
    public boolean addBrand(Brand brand) {
        return brandRepository.save(brand);
    }
    
    @Override
    public boolean updateBrand(Brand brand) {
        return brandRepository.update(brand);
    }
    
    @Override
    public boolean deleteBrand(Integer id) {
        return brandRepository.delete(id);
    }
}

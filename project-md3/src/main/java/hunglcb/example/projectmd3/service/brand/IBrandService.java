package hunglcb.example.projectmd3.service.brand;

import hunglcb.example.projectmd3.model.Brand;

import java.util.List;

public interface IBrandService {
    List<Brand> getAllBrands();
    List<Brand> getActiveBrands();
    Brand getBrandById(Integer id);
    Brand getBrandByName(String name);
    boolean addBrand(Brand brand);
    boolean updateBrand(Brand brand);
    boolean deleteBrand(Integer id);
}

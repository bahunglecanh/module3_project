package hunglcb.example.projectmd3.repository.brand;

import hunglcb.example.projectmd3.model.Brand;

import java.util.List;

public interface IBrandRepository {

    List<Brand> findAll();
    Brand findById(Integer id);
    Brand findByName(String name);
    List<Brand> findActive();
    boolean save(Brand brand);
    boolean update(Brand brand);
    boolean delete(Integer id);
}

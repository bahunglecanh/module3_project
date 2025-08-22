package hunglcb.example.projectmd3.repository.category;

import hunglcb.example.projectmd3.model.Category;

import java.util.List;

public interface ICategoryRepository {
    
    boolean save(Category category);
    Category findById(Integer id);
    boolean update(Category category);
    boolean delete(Integer id);
    List<Category> findAll();
    Category findByName(String name);
}

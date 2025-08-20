package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.model.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
    Category getCategoryByName(String name);
    boolean addCategory(Category category);
    boolean updateCategory(Category category);
    boolean deleteCategory(Integer id);
    boolean hasProducts(Integer categoryId);
}

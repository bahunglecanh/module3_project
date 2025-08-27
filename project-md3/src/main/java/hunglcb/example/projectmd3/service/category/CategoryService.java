package hunglcb.example.projectmd3.service.category;

import hunglcb.example.projectmd3.model.Category;
import hunglcb.example.projectmd3.repository.category.ICategoryRepository;
import hunglcb.example.projectmd3.repository.product.IProductRepository;
import hunglcb.example.projectmd3.repository.category.CategoryRepository;
import hunglcb.example.projectmd3.repository.product.ProductRepository;

import java.util.List;

public class CategoryService implements ICategoryService {
    
    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;
    
    public CategoryService() {
        this.categoryRepository = new CategoryRepository();
        this.productRepository = new ProductRepository();
    }
    
    // Constructor for dependency injection
    public CategoryService(ICategoryRepository categoryRepository, IProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }
    
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }
    
    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
    
    @Override
    public boolean addCategory(Category category) {
        return categoryRepository.save(category);
    }
    
    @Override
    public boolean updateCategory(Category category) {
        return categoryRepository.update(category);
    }
    
    @Override
    public boolean deleteCategory(Integer id) {
        // Kiểm tra có sản phẩm không trước khi xóa
        if (hasProducts(id)) {
            return false;
        }
        return categoryRepository.delete(id);
    }
    
    @Override
    public boolean hasProducts(Integer categoryId) {
        return productRepository.countByCategoryId(categoryId) > 0;
    }
}

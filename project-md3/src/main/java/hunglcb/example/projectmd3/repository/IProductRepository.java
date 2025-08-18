package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Product;

import java.util.List;

/**
 * Repository interface for Product operations
 */
public interface IProductRepository {
    
    boolean save(Product product);
    Product findById(Integer id);
    boolean update(Product product);
    boolean delete(Integer id);
    List<Product> findAll();
    List<Product> findByCategoryId(Integer categoryId);
    List<Product> findByNameContaining(String name);
    List<Product> findFeaturedProducts(int limit);
    List<Product> findLatestProducts(int limit);
    List<Product> findProductsInStock();
    
    // Pagination methods
    List<Product> findAllWithPagination(int page, int size);
    List<Product> findByCategoryIdWithPagination(Integer categoryId, int page, int size);
    List<Product> findByNameContainingWithPagination(String name, int page, int size);
    int countAll();
    int countByCategoryId(Integer categoryId);
    int countByNameContaining(String name);
}

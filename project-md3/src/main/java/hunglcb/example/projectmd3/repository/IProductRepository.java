package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.ProductSize;

import java.util.List;

/**
 * Repository interface for Product operations
 */
public interface IProductRepository {
    
    boolean save(Product product);
    Product findById(Integer id);
    boolean update(Product product);
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

    List<ProductSize> getProductSizes(Integer productId);
    List<ProductSize> getAvailableSizes(Integer productId);
    Integer getTotalStock(Integer productId);
    boolean isSizeAvailable(Integer productId, String size, Integer quantity);
    boolean reduceStock(Integer productId, String size, Integer quantity);
}

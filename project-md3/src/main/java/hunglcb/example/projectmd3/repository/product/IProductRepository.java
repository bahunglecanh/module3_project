package hunglcb.example.projectmd3.repository.product;

import hunglcb.example.projectmd3.dto.ProductDTO;
import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.ProductSize;

import java.util.List;

public interface IProductRepository {
    
    // CRUD Operations
    boolean save(Product product);
    Product findById(Integer id);
    boolean update(Product product);
    boolean delete(Integer id);

    //Lấy tất cả product
    List<ProductDTO> findAllProducts();
    
    // Pagination methods
    List<Product> findAllWithPagination(int page, int size);
    List<Product> findByCategoryIdWithPagination(Integer categoryId, int page, int size);
    List<Product> findByNameContainingWithPagination(String name, int page, int size);
    
    // Count methods for pagination
    int countAll();
    int countByCategoryId(Integer categoryId);
    int countByNameContaining(String name);
    
    // Price filter methods
    List<Product> findByPriceRangeAndCategoryWithPagination(Double minPrice, Double maxPrice, Integer categoryId, int page, int size);
    int countByPriceRangeAndCategory(Double minPrice, Double maxPrice, Integer categoryId);
    
    // Product Size operations
    List<ProductSize> findSizesByProductId(Integer productId);
    List<ProductSize> findAvailableSizesByProductId(Integer productId);
    ProductSize findProductSizeByProductIdAndSize(Integer productId, String size);
}

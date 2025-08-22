package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.ProductSize;

import java.util.List;

public interface IProductService {
    
    // Product Details
    Product getProductDetail(Integer productId);
    
    // Pagination methods
    List<Product> getProductsWithPagination(int page, int size);
    List<Product> getProductsByCategoryWithPagination(Integer categoryId, int page, int size);
    List<Product> searchProductsByName(String name, int page, int size);
    
    // Count methods for pagination
    int getTotalProductsCount();
    int getProductsCountByCategory(Integer categoryId);
    int getProductsCountByName(String name);
    
    // Featured products (using pagination internally)
    List<Product> getFeaturedProducts(int limit);
    
    // Price filter methods
    List<Product> getProductsByPriceRangeAndCategory(Double minPrice, Double maxPrice, Integer categoryId, int page, int size);
    int getProductsCountByPriceRangeAndCategory(Double minPrice, Double maxPrice, Integer categoryId);
    
    // Product Size operations
    List<ProductSize> getProductSizes(Integer productId);
    List<ProductSize> getAvailableProductSizes(Integer productId);
    ProductSize getProductSizeByProductIdAndSize(Integer productId, String size);
    boolean isProductSizeAvailable(Integer productId, String size, Integer quantity);
}

package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.ProductSize;

import java.util.List;

public interface IProductService {
    

    Product getProductDetail(Integer productId);
    List<Product> getProductsWithPagination(int page, int size);
    List<Product> getProductsByCategoryWithPagination(Integer categoryId, int page, int size);
    List<Product> searchProductsByName(String name, int page, int size);
    List<Product> getFeaturedProducts(int limit);
    List<Product> getLatestProducts(int limit);
    List<Product> getProductsInStock();
    int getTotalProductsCount();
    int getProductsCountByCategory(Integer categoryId);
    int getProductsCountByName(String name);
    List<ProductSize> getProductSizes(Integer productId);
    List<ProductSize> getAvailableProductSizes(Integer productId);
    Integer getTotalStock(Integer productId);
    boolean isProductSizeAvailable(Integer productId, String size, Integer quantity);
    boolean reduceProductStock(Integer productId, String size, Integer quantity);
}

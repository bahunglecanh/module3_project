package hunglcb.example.projectmd3.service.product;

import hunglcb.example.projectmd3.dto.ProductDTO;
import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.ProductSize;
import hunglcb.example.projectmd3.repository.product.IProductRepository;
import hunglcb.example.projectmd3.repository.product.ProductRepository;

import java.util.List;

public class ProductService implements IProductService {
    
    private final IProductRepository productRepository;
    
    public ProductService() {
        this.productRepository = new ProductRepository();
    }
    
    // Constructor for dependency injection
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    // ====== CRUD OPERATIONS ======
    
    @Override
    public boolean addProduct(Product product) {
        return productRepository.save(product);
    }
    
    @Override
    public boolean updateProduct(Product product) {
        return productRepository.update(product);
    }
    
    @Override
    public boolean deleteProduct(Integer id) {
        return productRepository.delete(id);
    }
    
    @Override
    public Product getProductDetail(Integer productId) {
        return productRepository.findById(productId);
    }
    
    @Override
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAllProducts();
    }
    
    @Override
    public List<Product> getProductsWithPagination(int page, int size) {
        return productRepository.findAllWithPagination(page, size);
    }
    
    @Override
    public List<Product> getProductsByCategoryWithPagination(Integer categoryId, int page, int size) {
        return productRepository.findByCategoryIdWithPagination(categoryId, page, size);
    }
    
    @Override
    public List<Product> searchProductsByName(String name, int page, int size) {
        return productRepository.findByNameContainingWithPagination(name, page, size);
    }
    
    @Override
    public List<Product> getFeaturedProducts(int limit) {
        // Sử dụng pagination với page=0 thay vì method riêng
        return productRepository.findAllWithPagination(0, limit);
    }
    
    @Override
    public int getTotalProductsCount() {
        return productRepository.countAll();
    }
    
    @Override
    public int getProductsCountByCategory(Integer categoryId) {
        return productRepository.countByCategoryId(categoryId);
    }
    
    @Override
    public int getProductsCountByName(String name) {
        return productRepository.countByNameContaining(name);
    }

    // ====== PRICE FILTER METHODS ======

    @Override
    public List<Product> getProductsByPriceRangeAndCategory(Double minPrice, Double maxPrice, Integer categoryId, int page, int size) {
        return productRepository.findByPriceRangeAndCategoryWithPagination(minPrice, maxPrice, categoryId, page, size);
    }

    @Override
    public int getProductsCountByPriceRangeAndCategory(Double minPrice, Double maxPrice, Integer categoryId) {
        return productRepository.countByPriceRangeAndCategory(minPrice, maxPrice, categoryId);
    }

    // ====== PRODUCT SIZE METHODS ======

    @Override
    public List<ProductSize> getProductSizes(Integer productId) {
        return productRepository.findSizesByProductId(productId);
    }

    @Override
    public List<ProductSize> getAvailableProductSizes(Integer productId) {
        return productRepository.findAvailableSizesByProductId(productId);
    }

    @Override
    public ProductSize getProductSizeByProductIdAndSize(Integer productId, String size) {
        return productRepository.findProductSizeByProductIdAndSize(productId, size);
    }

    @Override
    public boolean isProductSizeAvailable(Integer productId, String size, Integer quantity) {
        ProductSize productSize = getProductSizeByProductIdAndSize(productId, size);
        if (productSize == null || !productSize.getIsAvailable()) {
            return false;
        }
        return productSize.getStockQuantity() >= quantity;
    }
}

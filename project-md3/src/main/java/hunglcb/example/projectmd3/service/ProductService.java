package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.ProductSize;
import hunglcb.example.projectmd3.repository.IProductRepository;
import hunglcb.example.projectmd3.repository.ProductRepository;

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
    
    @Override
    public Product getProductDetail(Integer productId) {
        return productRepository.findById(productId);
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
        return productRepository.findFeaturedProducts(limit);
    }
    
    @Override
    public List<Product> getLatestProducts(int limit) {
        return productRepository.findLatestProducts(limit);
    }
    
    @Override
    public List<Product> getProductsInStock() {
        return productRepository.findProductsInStock();
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
    
    @Override
    public List<ProductSize> getProductSizes(Integer productId) {
        return productRepository.getProductSizes(productId);
    }
    
    @Override
    public List<ProductSize> getAvailableProductSizes(Integer productId) {
        return productRepository.getAvailableSizes(productId);
    }
    
    @Override
    public Integer getTotalStock(Integer productId) {
        return productRepository.getTotalStock(productId);
    }
    

    
    @Override
    public boolean isProductSizeAvailable(Integer productId, String size, Integer quantity) {
        return productRepository.isSizeAvailable(productId, size, quantity);
    }
    
    @Override
    public boolean reduceProductStock(Integer productId, String size, Integer quantity) {
        return productRepository.reduceStock(productId, size, quantity);
    }
    
}

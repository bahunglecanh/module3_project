package hunglcb.example.projectmd3.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ProductSize model for product sizes
 * Mapped từ bảng product_sizes trong database
 */
public class ProductSize {
    private Integer id;
    private Integer productId;
    private String size;
    private Integer stockQuantity;
    private BigDecimal priceAdjustment;
    private Boolean isAvailable;
    private LocalDateTime createdAt;

    // Constructors
    public ProductSize() {}

    public ProductSize(Integer productId, String size, Integer stockQuantity) {
        this.productId = productId;
        this.size = size;
        this.stockQuantity = stockQuantity;
        this.priceAdjustment = BigDecimal.ZERO;
        this.isAvailable = true;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public BigDecimal getPriceAdjustment() {
        return priceAdjustment;
    }

    public void setPriceAdjustment(BigDecimal priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Helper methods
    public boolean isInStock() {
        return stockQuantity != null && stockQuantity > 0 && Boolean.TRUE.equals(isAvailable);
    }

    public boolean isLowStock() {
        return isInStock() && stockQuantity <= 5;
    }

    @Override
    public String toString() {
        return "ProductSize{" +
                "id=" + id +
                ", productId=" + productId +
                ", size='" + size + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
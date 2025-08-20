package hunglcb.example.projectmd3.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ProductSize {
    private Integer id;
    private Integer productId;
    private String size;
    private Integer stockQuantity;
    private BigDecimal priceAdjustment;
    private Boolean isAvailable;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public ProductSize() {
        this.stockQuantity = 0;
        this.priceAdjustment = BigDecimal.ZERO;
        this.isAvailable = true;
    }

    // Constructor with essential fields
    public ProductSize(Integer productId, String size, Integer stockQuantity) {
        this();
        this.productId = productId;
        this.size = size;
        this.stockQuantity = stockQuantity;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Helper methods
    public boolean isInStock() {
        return isAvailable && stockQuantity != null && stockQuantity > 0;
    }

    public boolean isLowStock() {
        return isInStock() && stockQuantity <= 5;
    }

    public String getStockStatus() {
        if (!isInStock()) {
            return "out_of_stock";
        } else if (isLowStock()) {
            return "low_stock";
        } else {
            return "in_stock";
        }
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

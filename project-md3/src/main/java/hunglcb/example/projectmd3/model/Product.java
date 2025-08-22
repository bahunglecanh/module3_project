package hunglcb.example.projectmd3.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product {
    // ===== COLUMNS FROM PRODUCTS TABLE =====
    private Integer id;
    private Integer categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private Integer brandId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // ===== JOIN FIELDS (for display only) =====
    private String categoryName;
    private String brandName;

    // Default constructor
    public Product() {
        this.stockQuantity = 0;
    }

    // Constructor with basic info
    public Product(String name, String description, BigDecimal price) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Full constructor
    public Product(Integer id, Integer categoryId, String name, String description, 
                  BigDecimal price, Integer stockQuantity, String imageUrl, 
                  Integer brandId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.brandId = brandId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ===== GETTERS AND SETTERS =====
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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

    // JOIN FIELDS
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    // ===== UTILITY METHODS =====
    public boolean isInStock() {
        return stockQuantity != null && stockQuantity > 0;
    }

    public String getFormattedPrice() {
        if (price != null) {
            return String.format("%,.0f₫", price);
        }
        return "0₫";
    }

    public void updateTimestamp() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", brandId=" + brandId +
                ", categoryName='" + categoryName + '\'' +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
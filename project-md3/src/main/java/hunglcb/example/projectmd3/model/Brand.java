package hunglcb.example.projectmd3.model;

import java.sql.Timestamp;

public class Brand {
    private Integer id;
    private String name;
    private String description;
    private String logoUrl;
    private Timestamp createdAt;

    // Default constructor
    public Brand() {
    }

    // Constructor with essential fields
    public Brand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Full constructor
    public Brand(Integer id, String name, String description, String logoUrl, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
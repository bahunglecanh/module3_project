package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.ProductSize;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository {

    @Override
    public boolean save(Product product) {
        String sql = "INSERT INTO products (category_id, name, description, price, stock_quantity, image_url, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setObject(1, product.getCategoryId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setBigDecimal(4, product.getPrice());
            statement.setInt(5, product.getStockQuantity());
            statement.setString(6, product.getImageUrl());
            statement.setTimestamp(7, product.getCreatedAt());
            statement.setTimestamp(8, product.getUpdatedAt());
            
            int result = statement.executeUpdate();
            if (result > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    product.setId(rs.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Product findById(Integer id) {
        String sql = "SELECT p.*, c.name as category_name, b.name as brand_name " +
                    "FROM products p " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
                    "LEFT JOIN brands b ON p.brand_id = b.id " +
                    "WHERE p.id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToProduct(resultSet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE products SET category_id = ?, name = ?, description = ?, price = ?, stock_quantity = ?, image_url = ?, updated_at = ? WHERE id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            product.updateTimestamp();
            
            statement.setObject(1, product.getCategoryId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setBigDecimal(4, product.getPrice());
            statement.setInt(5, product.getStockQuantity());
            statement.setString(6, product.getImageUrl());
            statement.setTimestamp(7, product.getUpdatedAt());
            statement.setInt(8, product.getId());
            
            return statement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> findAllWithPagination(int page, int size) {
        String sql = "SELECT p.*, c.name as category_name, b.name as brand_name " +
                    "FROM products p " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
                    "LEFT JOIN brands b ON p.brand_id = b.id " +
                    "ORDER BY p.created_at DESC LIMIT ? OFFSET ?";
        List<Product> products = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, size);
            statement.setInt(2, page * size);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findByCategoryIdWithPagination(Integer categoryId, int page, int size) {
        String sql = "SELECT p.*, c.name as category_name, b.name as brand_name " +
                    "FROM products p " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
                    "LEFT JOIN brands b ON p.brand_id = b.id " +
                    "WHERE p.category_id = ? ORDER BY p.created_at DESC LIMIT ? OFFSET ?";
        List<Product> products = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, categoryId);
            statement.setInt(2, size);
            statement.setInt(3, page * size);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findByNameContainingWithPagination(String name, int page, int size) {
        String sql = "SELECT p.*, c.name as category_name " +
                    "FROM products p " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
                    "WHERE p.name LIKE ? ORDER BY p.created_at DESC LIMIT ? OFFSET ?";
        List<Product> products = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, "%" + name + "%");
            statement.setInt(2, size);
            statement.setInt(3, page * size);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM products";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int countByCategoryId(Integer categoryId) {
        String sql = "SELECT COUNT(*) FROM products WHERE category_id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int countByNameContaining(String name) {
        String sql = "SELECT COUNT(*) FROM products WHERE name LIKE ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public List<Product> findByPriceRangeAndCategoryWithPagination(Double minPrice, Double maxPrice, Integer categoryId, int page, int size) {
        String sql = "SELECT p.*, c.name as category_name " +
                "FROM products p " +
                "LEFT JOIN categories c ON p.category_id = c.id " +
                "WHERE (? IS NULL OR p.price >= ?) " +
                "AND (? IS NULL OR p.price <= ?) " +
                "AND (? IS NULL OR p.category_id = ?) " +
                "ORDER BY p.created_at DESC LIMIT ? OFFSET ?";
        List<Product> products = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            if (minPrice != null) { statement.setDouble(1, minPrice); statement.setDouble(2, minPrice); } else { statement.setNull(1, Types.DOUBLE); statement.setNull(2, Types.DOUBLE); }
            if (maxPrice != null) { statement.setDouble(3, maxPrice); statement.setDouble(4, maxPrice); } else { statement.setNull(3, Types.DOUBLE); statement.setNull(4, Types.DOUBLE); }
            if (categoryId != null) { statement.setInt(5, categoryId); statement.setInt(6, categoryId); } else { statement.setNull(5, Types.INTEGER); statement.setNull(6, Types.INTEGER); }
            statement.setInt(7, size);
            statement.setInt(8, page * size);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public int countByPriceRangeAndCategory(Double minPrice, Double maxPrice, Integer categoryId) {
        String sql = "SELECT COUNT(*) FROM products p " +
                "WHERE (? IS NULL OR p.price >= ?) " +
                "AND (? IS NULL OR p.price <= ?) " +
                "AND (? IS NULL OR p.category_id = ?)";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (minPrice != null) { statement.setDouble(1, minPrice); statement.setDouble(2, minPrice); } else { statement.setNull(1, Types.DOUBLE); statement.setNull(2, Types.DOUBLE); }
            if (maxPrice != null) { statement.setDouble(3, maxPrice); statement.setDouble(4, maxPrice); } else { statement.setNull(3, Types.DOUBLE); statement.setNull(4, Types.DOUBLE); }
            if (categoryId != null) { statement.setInt(5, categoryId); statement.setInt(6, categoryId); } else { statement.setNull(5, Types.INTEGER); statement.setNull(6, Types.INTEGER); }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) { return resultSet.getInt(1); }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    // Helper method to map ResultSet to Product object
    private Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        
        product.setId(resultSet.getInt("id"));
        
        Integer categoryId = resultSet.getObject("category_id", Integer.class);
        product.setCategoryId(categoryId);
        
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setStockQuantity(resultSet.getInt("stock_quantity"));
        product.setImageUrl(resultSet.getString("image_url"));
        product.setCreatedAt(resultSet.getTimestamp("created_at"));
        product.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        
        // Set category name if available from JOIN
        try {
            String categoryName = resultSet.getString("category_name");
            product.setCategoryName(categoryName);
        } catch (SQLException e) {
            // Category name not available, skip
        }
        
        // Set brand name if available from JOIN
        try {
            String brandName = resultSet.getString("brand_name");
            product.setBrandName(brandName);
        } catch (SQLException e) {
            // Brand name not available, skip
        }
        
        return product;
    }

    // ====== PRODUCT SIZE METHODS ======

    @Override
    public List<ProductSize> findSizesByProductId(Integer productId) {
        String sql = "SELECT * FROM product_sizes WHERE product_id = ? ORDER BY size";
        List<ProductSize> sizes = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                sizes.add(mapResultSetToProductSize(resultSet));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return sizes;
    }

    @Override
    public List<ProductSize> findAvailableSizesByProductId(Integer productId) {
        String sql = "SELECT * FROM product_sizes WHERE product_id = ? AND stock_quantity > 0 AND is_available = 1 ORDER BY size";
        List<ProductSize> sizes = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                sizes.add(mapResultSetToProductSize(resultSet));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return sizes;
    }


    @Override
    public ProductSize findProductSizeByProductIdAndSize(Integer productId, String size) {
        String sql = "SELECT * FROM product_sizes WHERE product_id = ? AND size = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, productId);
            statement.setString(2, size);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToProductSize(resultSet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // Helper method to map ResultSet to ProductSize object
    private ProductSize mapResultSetToProductSize(ResultSet resultSet) throws SQLException {
        ProductSize productSize = new ProductSize();
        
        productSize.setId(resultSet.getInt("id"));
        productSize.setProductId(resultSet.getInt("product_id"));
        productSize.setSize(resultSet.getString("size"));
        productSize.setStockQuantity(resultSet.getInt("stock_quantity"));
        productSize.setPriceAdjustment(resultSet.getBigDecimal("price_adjustment"));
        productSize.setIsAvailable(resultSet.getBoolean("is_available"));
        
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        if (createdAt != null) {
            productSize.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        return productSize;
    }
}
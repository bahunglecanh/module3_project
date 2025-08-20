package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.ProductSize;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ProductRepository
 */
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
        String sql = "SELECT p.*, c.name as category_name, b.name as brand_name, " +
                    "COALESCE(SUM(ps.stock_quantity), 0) as total_stock " +
                    "FROM products p " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
                    "LEFT JOIN brands b ON p.brand_id = b.id " +
                    "LEFT JOIN product_sizes ps ON p.id = ps.product_id AND ps.is_available = TRUE " +
                    "WHERE p.id = ? GROUP BY p.id";
        
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
    public List<Product> findFeaturedProducts(int limit) {
        String sql = "SELECT p.*, c.name as category_name, b.name as brand_name, " +
                    "COALESCE(SUM(ps.stock_quantity), 0) as total_stock " +
                    "FROM products p " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
                    "LEFT JOIN brands b ON p.brand_id = b.id " +
                    "LEFT JOIN product_sizes ps ON p.id = ps.product_id AND ps.is_available = TRUE " +
                    "GROUP BY p.id " +
                    "ORDER BY p.created_at DESC LIMIT ?";
        List<Product> products = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Product product = mapResultSetToProduct(resultSet);
                products.add(product);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }





    @Override
    public List<Product> findAllWithPagination(int page, int size) {
        String sql = "SELECT p.*, c.name as category_name, b.name as brand_name FROM products p " +
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
        String sql = "SELECT p.*, c.name as category_name, b.name as brand_name FROM products p " +
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
        String sql = "SELECT p.*, c.name as category_name FROM products p " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
                    "WHERE p.name LIKE ? ORDER BY p.created_at DESC LIMIT ? OFFSET ?";
        List<Product> products = new ArrayList<>();
        
        System.out.println("=== SEARCH DEBUG ===");
        System.out.println("Search term: '" + name + "'");
        System.out.println("SQL pattern: '%" + name + "%'");
        System.out.println("Page: " + page + ", Size: " + size);
        System.out.println("SQL: " + sql);
        
        // Check if there are any products at all
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM products")) {
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Total products in database: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Error checking total products: " + e.getMessage());
        }
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, "%" + name + "%");
            statement.setInt(2, size);
            statement.setInt(3, page * size);
            
            System.out.println("Executing query...");
            ResultSet resultSet = statement.executeQuery();
            
            int count = 0;
            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
                count++;
                System.out.println("Found product: " + resultSet.getString("name"));
            }
            
            System.out.println("Total products found: " + count);
            
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== END SEARCH DEBUG ===");
        return products;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM products";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
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
        
        System.out.println("=== COUNT DEBUG ===");
        System.out.println("Counting for: '" + name + "'");
        System.out.println("SQL: " + sql);
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                System.out.println("Count result: " + count);
                return count;
            }
            
        } catch (SQLException e) {
            System.out.println("Count SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== END COUNT DEBUG ===");
        return 0;
    }

    // Product size related methods
    @Override
    public List<ProductSize> getProductSizes(Integer productId) {
        // Use specific columns instead of SELECT *
        String sql = "SELECT id, product_id, size, stock_quantity, price_adjustment, is_available, created_at FROM product_sizes WHERE product_id = ? ORDER BY size";
        List<ProductSize> productSizes = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                ProductSize productSize = mapResultSetToProductSize(resultSet);
                productSizes.add(productSize);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return productSizes;
    }

    @Override
    public List<ProductSize> getAvailableSizes(Integer productId) {
        String sql = "SELECT * FROM product_sizes WHERE product_id = ? AND is_available = TRUE AND stock_quantity > 0 ORDER BY size";
        List<ProductSize> productSizes = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                ProductSize productSize = mapResultSetToProductSize(resultSet);
                productSizes.add(productSize);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return productSizes;
    }



    @Override
    public Integer getTotalStock(Integer productId) {
        String sql = "SELECT SUM(stock_quantity) as total_stock FROM product_sizes WHERE product_id = ? AND is_available = TRUE";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt("total_stock");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean isSizeAvailable(Integer productId, String size, Integer quantity) {
        String sql = "SELECT stock_quantity FROM product_sizes WHERE product_id = ? AND size = ? AND is_available = TRUE";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, productId);
            statement.setString(2, size);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int stock = resultSet.getInt("stock_quantity");
                return stock >= quantity;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean reduceStock(Integer productId, String size, Integer quantity) {
        String sql = "UPDATE product_sizes SET stock_quantity = stock_quantity - ?, updated_at = CURRENT_TIMESTAMP " +
                    "WHERE product_id = ? AND size = ? AND stock_quantity >= ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            statement.setString(3, size);
            statement.setInt(4, quantity);
            
            return statement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to ProductSize object
    private ProductSize mapResultSetToProductSize(ResultSet resultSet) throws SQLException {
        ProductSize productSize = new ProductSize();
        
        try {
            productSize.setId(resultSet.getInt("id"));
            productSize.setProductId(resultSet.getInt("product_id"));
            productSize.setSize(resultSet.getString("size"));
            productSize.setStockQuantity(resultSet.getInt("stock_quantity"));
            productSize.setPriceAdjustment(resultSet.getBigDecimal("price_adjustment"));
            productSize.setIsAvailable(resultSet.getBoolean("is_available"));
            productSize.setCreatedAt(resultSet.getTimestamp("created_at"));
            
            // Check if updated_at column exists
            try {
                productSize.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            } catch (SQLException e) {
                productSize.setUpdatedAt(null);
            }
                             
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return productSize;
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
        
        // Ưu tiên total_stock từ JOIN, fallback về stock_quantity cũ
        try {
            product.setStockQuantity(resultSet.getInt("total_stock"));
        } catch (SQLException e) {
            try {
                product.setStockQuantity(resultSet.getInt("stock_quantity"));
            } catch (SQLException e2) {
                product.setStockQuantity(0); // Default fallback
            }
        }
        
        product.setImageUrl(resultSet.getString("image_url"));
        product.setCreatedAt(resultSet.getTimestamp("created_at"));
        product.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        
        // Set category name if available from JOIN
        String categoryName = resultSet.getString("category_name");
        product.setCategoryName(categoryName);
        
        // Set brand information if available from JOIN
        try {
            Integer brandId = resultSet.getObject("brand_id", Integer.class);
            product.setBrandId(brandId);
        } catch (SQLException e) {
            product.setBrandId(null);
        }
        
        try {
            String brandName = resultSet.getString("brand_name");
            product.setBrandName(brandName);
        } catch (SQLException e) {
            product.setBrandName(null);
        }
        
        return product;
    }

    @Override
    public List<Product> findLatestProducts(int limit) {
        String sql = "SELECT p.*, c.name as category_name, b.name as brand_name, " +
                    "COALESCE(SUM(ps.stock_quantity), 0) as total_stock " +
                    "FROM products p " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
                    "LEFT JOIN brands b ON p.brand_id = b.id " +
                    "LEFT JOIN product_sizes ps ON p.id = ps.product_id AND ps.is_available = TRUE " +
                    "GROUP BY p.id " +
                    "ORDER BY p.created_at DESC " +
                    "LIMIT ?";
        
        List<Product> products = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Product product = mapResultSetToProduct(resultSet);
                products.add(product);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }

    @Override
    public List<Product> findProductsInStock() {
        String sql = "SELECT p.*, c.name as category_name, b.name as brand_name, " +
                    "COALESCE(SUM(ps.stock_quantity), 0) as total_stock " +
                    "FROM products p " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
                    "LEFT JOIN brands b ON p.brand_id = b.id " +
                    "LEFT JOIN product_sizes ps ON p.id = ps.product_id AND ps.is_available = TRUE " +
                    "GROUP BY p.id " +
                    "HAVING total_stock > 0 " +
                    "ORDER BY p.name";
        
        List<Product> products = new ArrayList<>();
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Product product = mapResultSetToProduct(resultSet);
                products.add(product);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }
}

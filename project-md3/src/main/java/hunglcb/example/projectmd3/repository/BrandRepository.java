package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Brand;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandRepository implements IBrandRepository {
    
    @Override
    public List<Brand> findAll() {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM brands ORDER BY name";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                Brand brand = mapResultSetToBrand(resultSet);
                brands.add(brand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return brands;
    }
    
    @Override
    public Brand findById(Integer id) {
        String sql = "SELECT * FROM brands WHERE id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToBrand(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public Brand findByName(String name) {
        String sql = "SELECT * FROM brands WHERE name = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToBrand(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<Brand> findActive() {
        // For now, all brands are considered active
        // You can add is_active column later if needed
        return findAll();
    }
    
    @Override
    public boolean save(Brand brand) {
        String sql = "INSERT INTO brands (name, description, logo_url) VALUES (?, ?, ?)";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, brand.getName());
            statement.setString(2, brand.getDescription());
            statement.setString(3, brand.getLogoUrl());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    brand.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public boolean update(Brand brand) {
        String sql = "UPDATE brands SET name = ?, description = ?, logo_url = ? WHERE id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, brand.getName());
            statement.setString(2, brand.getDescription());
            statement.setString(3, brand.getLogoUrl());
            statement.setInt(4, brand.getId());
            
            return statement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM brands WHERE id = ?";
        
        try (Connection connection = ConnectionDB.getConnectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private Brand mapResultSetToBrand(ResultSet resultSet) throws SQLException {
        Brand brand = new Brand();
        brand.setId(resultSet.getInt("id"));
        brand.setName(resultSet.getString("name"));
        brand.setDescription(resultSet.getString("description"));
        brand.setLogoUrl(resultSet.getString("logo_url"));
        brand.setCreatedAt(resultSet.getTimestamp("created_at"));
        return brand;
    }
}

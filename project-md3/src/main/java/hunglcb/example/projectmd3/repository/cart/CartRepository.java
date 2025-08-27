package hunglcb.example.projectmd3.repository.cart;

import hunglcb.example.projectmd3.model.Cart;
import hunglcb.example.projectmd3.model.CartItem;
import hunglcb.example.projectmd3.dto.CartItemView;
import hunglcb.example.projectmd3.repository.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartRepository implements ICartRepository {
    @Override
    public Cart findOrCreateCartByAccountId(Integer accountId) {
        Cart existing = findCartByAccountId(accountId);
        if (existing != null) return existing;

        String sql = "INSERT INTO carts (account_id, created_at) VALUES (?, CURRENT_TIMESTAMP)";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, accountId);
            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Cart cart = new Cart();
                    cart.setId(rs.getInt(1));
                    cart.setAccountId(accountId);
                    cart.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    return cart;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cart findCartByAccountId(Integer accountId) {
        String sql = "SELECT id, account_id, created_at FROM carts WHERE account_id = ? LIMIT 1";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setAccountId(rs.getInt("account_id"));
                cart.setCreatedAt(rs.getTimestamp("created_at"));
                return cart;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addOrIncrementCartItem(Integer cartId, Integer productId, Integer sizeId, Integer quantity) {
        // Try update existing row
        String updateSql = "UPDATE cart_items SET quantity = quantity + ? WHERE cart_id = ? AND product_id = ? AND ( (size_id IS NULL AND ? IS NULL) OR size_id = ? )";
        String insertSql = "INSERT INTO cart_items (cart_id, product_id, size_id, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnectDB()) {
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, quantity);
                ps.setInt(2, cartId);
                ps.setInt(3, productId);
                if (sizeId == null) { ps.setNull(4, Types.INTEGER); ps.setNull(5, Types.INTEGER); }
                else { ps.setInt(4, sizeId); ps.setInt(5, sizeId); }
                int updated = ps.executeUpdate();
                if (updated > 0) return true;
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, cartId);
                ps.setInt(2, productId);
                if (sizeId == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, sizeId);
                ps.setInt(4, quantity);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCartItemQuantity(Integer cartItemId, Integer quantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCartItem(Integer cartItemId) {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<CartItem> findItemsByCartId(Integer cartId) {
        String sql = "SELECT id, cart_id, product_id, size_id, quantity FROM cart_items WHERE cart_id = ?";
        List<CartItem> items = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getInt("id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                int sizeVal = rs.getInt("size_id");
                item.setSizeId(rs.wasNull() ? null : sizeVal);
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<CartItemView> findItemViewsByAccountId(Integer accountId) {
        String sql = "SELECT ci.id as cart_item_id, p.id as product_id, p.name, p.image_url, ps.size, p.price + IFNULL(ps.price_adjustment,0) as unit_price, ci.quantity " +
                "FROM carts c " +
                "JOIN cart_items ci ON ci.cart_id = c.id " +
                "JOIN products p ON p.id = ci.product_id " +
                "LEFT JOIN product_sizes ps ON ps.id = ci.size_id " +
                "WHERE c.account_id = ?";
        List<CartItemView> views = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItemView v = new CartItemView();
                v.setCartItemId(rs.getInt("cart_item_id"));
                v.setProductId(rs.getInt("product_id"));
                v.setProductName(rs.getString("name"));
                v.setProductImageUrl(rs.getString("image_url"));
                v.setSize(rs.getString("size"));
                v.setUnitPrice(rs.getBigDecimal("unit_price"));
                v.setQuantity(rs.getInt("quantity"));
                v.setLineTotal(rs.getBigDecimal("unit_price").multiply(new java.math.BigDecimal(v.getQuantity())));
                views.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return views;
    }

    @Override
    public boolean clearCart(Integer accountId) {
        String findSql = "SELECT id FROM carts WHERE account_id = ?";
        try (Connection conn = ConnectionDB.getConnectDB();
             PreparedStatement find = conn.prepareStatement(findSql)) {
            find.setInt(1, accountId);
            ResultSet rs = find.executeQuery();
            if (!rs.next()) return true;
            int cartId = rs.getInt("id");
            try (PreparedStatement del = conn.prepareStatement("DELETE FROM cart_items WHERE cart_id = ?")) {
                del.setInt(1, cartId);
                del.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}



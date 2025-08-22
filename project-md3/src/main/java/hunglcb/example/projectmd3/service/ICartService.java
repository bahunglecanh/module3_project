package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.model.Cart;
import hunglcb.example.projectmd3.model.CartItem;
import hunglcb.example.projectmd3.model.dto.CartItemView;
import hunglcb.example.projectmd3.model.dto.CartSummary;

import java.util.List;

public interface ICartService {
    Cart getOrCreateCart(Integer accountId);
    boolean addToCart(Integer accountId, Integer productId, String size, Integer quantity);
    List<CartItem> getCartItems(Integer accountId);
    List<CartItemView> getCartItemViews(Integer accountId);
    CartSummary getCartSummary(Integer accountId);
    boolean updateItemQuantity(Integer cartItemId, Integer quantity);
    boolean removeItem(Integer cartItemId);
    boolean clear(Integer accountId);
}



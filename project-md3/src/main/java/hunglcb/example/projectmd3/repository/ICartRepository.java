package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Cart;
import hunglcb.example.projectmd3.model.CartItem;
import hunglcb.example.projectmd3.model.dto.CartItemView;

import java.util.List;

public interface ICartRepository {
    Cart findOrCreateCartByAccountId(Integer accountId);
    Cart findCartByAccountId(Integer accountId);
    boolean addOrIncrementCartItem(Integer cartId, Integer productId, Integer sizeId, Integer quantity);
    boolean updateCartItemQuantity(Integer cartItemId, Integer quantity);
    boolean removeCartItem(Integer cartItemId);
    List<CartItem> findItemsByCartId(Integer cartId);
    List<CartItemView> findItemViewsByAccountId(Integer accountId);
    boolean clearCart(Integer accountId);
}



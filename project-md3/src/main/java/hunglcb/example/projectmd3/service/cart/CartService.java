package hunglcb.example.projectmd3.service.cart;

import hunglcb.example.projectmd3.model.Cart;
import hunglcb.example.projectmd3.model.CartItem;
import hunglcb.example.projectmd3.model.ProductSize;
import hunglcb.example.projectmd3.dto.CartItemView;
import hunglcb.example.projectmd3.dto.CartSummary;
import hunglcb.example.projectmd3.repository.cart.CartRepository;
import hunglcb.example.projectmd3.repository.cart.ICartRepository;
import hunglcb.example.projectmd3.service.product.IProductService;
import hunglcb.example.projectmd3.service.product.ProductService;

import java.util.List;

public class CartService implements ICartService {
    private final ICartRepository cartRepository;
    private final IProductService productService;

    public CartService() {
        this.cartRepository = new CartRepository();
        this.productService = new ProductService();
    }

    public CartService(ICartRepository cartRepository, IProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public Cart getOrCreateCart(Integer accountId) {
        return cartRepository.findOrCreateCartByAccountId(accountId);
    }

    @Override
    public boolean addToCart(Integer accountId, Integer productId, String size, Integer quantity) {
        if (quantity == null || quantity <= 0) return false;

        // Validate size availability and resolve sizeId
        Integer sizeId = null;
        if (size != null && !size.isEmpty()) {
            ProductSize ps = productService.getProductSizeByProductIdAndSize(productId, size);
            if (ps == null || !Boolean.TRUE.equals(ps.getIsAvailable())) {
                return false;
            }
            if (ps.getStockQuantity() == null || ps.getStockQuantity() < quantity) {
                return false;
            }
            sizeId = ps.getId();
        }

        Cart cart = cartRepository.findOrCreateCartByAccountId(accountId);
        if (cart == null) return false;

        return cartRepository.addOrIncrementCartItem(cart.getId(), productId, sizeId, quantity);
    }

    @Override
    public List<CartItem> getCartItems(Integer accountId) {
        Cart cart = cartRepository.findCartByAccountId(accountId);
        if (cart == null) return List.of();
        return cartRepository.findItemsByCartId(cart.getId());
    }

    @Override
    public List<CartItemView> getCartItemViews(Integer accountId) {
        return cartRepository.findItemViewsByAccountId(accountId);
    }

    @Override
    public CartSummary getCartSummary(Integer accountId) {
        java.util.List<CartItemView> views = getCartItemViews(accountId);
        CartSummary summary = new CartSummary();
        int totalItems = 0;
        java.math.BigDecimal subtotal = java.math.BigDecimal.ZERO;
        for (CartItemView v : views) {
            totalItems += v.getQuantity();
            subtotal = subtotal.add(v.getLineTotal());
        }
        summary.setTotalItems(totalItems);
        summary.setSubtotal(subtotal);
        return summary;
    }

    @Override
    public boolean updateItemQuantity(Integer cartItemId, Integer quantity) {
        if (quantity == null || quantity <= 0) return false;
        return cartRepository.updateCartItemQuantity(cartItemId, quantity);
    }

    @Override
    public boolean removeItem(Integer cartItemId) {
        return cartRepository.removeCartItem(cartItemId);
    }

    @Override
    public boolean clear(Integer accountId) {
        return cartRepository.clearCart(accountId);
    }
}



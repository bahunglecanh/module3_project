package hunglcb.example.projectmd3.model;

public class CartItem {
    private Integer id;
    private Integer cartId;
    private Integer productId;
    private Integer sizeId; // nullable in schema
    private Integer quantity;

    public CartItem() {}

    public CartItem(Integer cartId, Integer productId, Integer sizeId, Integer quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}



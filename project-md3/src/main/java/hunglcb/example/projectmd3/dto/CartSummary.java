package hunglcb.example.projectmd3.dto;

import java.math.BigDecimal;

public class CartSummary {
    private int totalItems;
    private BigDecimal subtotal = BigDecimal.ZERO;

    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}



package hunglcb.example.projectmd3.repository.order;

import java.math.BigDecimal;

public interface IOrderRepository {
    Integer createOrder(Integer accountId, Integer shippingAddressId, Integer paymentMethodId, BigDecimal totalAmount);
    boolean insertItemsFromCart(Integer accountId, Integer orderId);
    boolean clearCart(Integer accountId);
    boolean updateStatus(Integer orderId, String status);
}



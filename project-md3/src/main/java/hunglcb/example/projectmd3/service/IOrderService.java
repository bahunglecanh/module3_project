package hunglcb.example.projectmd3.service;

import java.math.BigDecimal;

public interface IOrderService {
    Integer placeOrderCOD(Integer accountId, Integer shippingAddressId, BigDecimal totalAmount);
    Integer prepareOrderPendingPayment(Integer accountId, Integer shippingAddressId, Integer paymentMethodId, BigDecimal totalAmount);
    boolean finalizePaidOrder(Integer orderId);
}
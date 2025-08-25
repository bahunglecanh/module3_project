package hunglcb.example.projectmd3.service.order;

import hunglcb.example.projectmd3.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface IOrderService {
    Integer placeOrderCOD(Integer accountId, Integer shippingAddressId, BigDecimal totalAmount);
    Integer prepareOrderPendingPayment(Integer accountId, Integer shippingAddressId, Integer paymentMethodId, BigDecimal totalAmount);
    boolean finalizePaidOrder(Integer orderId);
    
    // Methods for viewing orders
    List<Order> getUserOrders(Integer accountId);
    Order getOrderDetails(Integer orderId);
    boolean cancelOrder(Integer orderId, Integer accountId);
    
    // Methods for admin
    boolean confirmOrder(Integer orderId);
    boolean shipOrder(Integer orderId);
    boolean deliverOrder(Integer orderId);
}



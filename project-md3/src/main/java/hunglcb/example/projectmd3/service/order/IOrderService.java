package hunglcb.example.projectmd3.service.order;

import hunglcb.example.projectmd3.dto.CustomerOrderDTO;
import hunglcb.example.projectmd3.dto.OrderItemDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface IOrderService {
    Integer placeOrderCOD(Integer accountId, Integer shippingAddressId, BigDecimal totalAmount);
    Integer prepareOrderPendingPayment(Integer accountId, Integer shippingAddressId, Integer paymentMethodId, BigDecimal totalAmount);
    boolean finalizePaidOrder(Integer orderId);
    List<CustomerOrderDTO> getAllOrders() throws SQLException;
    CustomerOrderDTO getOrderById(Long orderId) throws SQLException;
    boolean updateOrderStatus(Long orderId, String status) throws SQLException;
}
package hunglcb.example.projectmd3.repository.order;

import hunglcb.example.projectmd3.dto.CustomerOrderDTO;
import hunglcb.example.projectmd3.dto.OrderItemDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface IOrderRepository {
    Integer createOrder(Integer accountId, Integer shippingAddressId, Integer paymentMethodId, BigDecimal totalAmount);
    boolean insertItemsFromCart(Integer accountId, Integer orderId);
    boolean clearCart(Integer accountId);
    boolean updateStatus(Integer orderId, String status);
    List<CustomerOrderDTO> findAllOrders() throws SQLException;
    List<OrderItemDTO> findOrderItemsByOrderId(Long orderId) throws SQLException;
}

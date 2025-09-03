package hunglcb.example.projectmd3.repository.order;

import hunglcb.example.projectmd3.model.Order;
import hunglcb.example.projectmd3.model.OrderItem;
import hunglcb.example.projectmd3.model.UserAddress;

import hunglcb.example.projectmd3.dto.CustomerOrderDTO;
import hunglcb.example.projectmd3.dto.OrderItemDTO;

import java.math.BigDecimal;
import java.util.List;
import java.sql.SQLException;
import java.util.List;

public interface IOrderRepository {
    Integer createOrder(Integer accountId, Integer shippingAddressId, Integer paymentMethodId, BigDecimal totalAmount);
    boolean insertItemsFromCart(Integer accountId, Integer orderId);
    boolean clearCart(Integer accountId);
    boolean updateStatus(Integer orderId, String status);

    // Methods for viewing orders
    List<Order> getOrdersByAccountId(Integer accountId);
    Order getOrderById(Integer orderId);
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
    Order getOrderWithDetails(Integer orderId);
    List<CustomerOrderDTO> findAllOrders() throws SQLException;
    List<OrderItemDTO> findOrderItemsByOrderId(Long orderId) throws SQLException;
    CustomerOrderDTO findOrderById(Long orderId) throws SQLException;
    boolean updateOrderStatus(Long orderId, String status) throws SQLException;

    // Utility methods
    UserAddress getShippingAddressById(Integer addressId);
}



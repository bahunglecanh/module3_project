package hunglcb.example.projectmd3.service.order;

import hunglcb.example.projectmd3.dto.CustomerOrderDTO;
import hunglcb.example.projectmd3.dto.OrderItemDTO;
import hunglcb.example.projectmd3.repository.order.IOrderRepository;
import hunglcb.example.projectmd3.repository.order.OrderRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository;

    public OrderService() { this.orderRepository = new OrderRepository(); }
    public OrderService(IOrderRepository orderRepository) { this.orderRepository = orderRepository; }

    @Override
    public Integer placeOrderCOD(Integer accountId, Integer shippingAddressId, BigDecimal totalAmount) {
        Integer orderId = orderRepository.createOrder(accountId, shippingAddressId, null, totalAmount);
        if (orderId == null) return null;
        orderRepository.insertItemsFromCart(accountId, orderId);
        orderRepository.clearCart(accountId);
        orderRepository.updateStatus(orderId, "confirmed");
        return orderId;
    }

    @Override
    public Integer prepareOrderPendingPayment(Integer accountId, Integer shippingAddressId, Integer paymentMethodId, BigDecimal totalAmount) {
        Integer orderId = orderRepository.createOrder(accountId, shippingAddressId, paymentMethodId, totalAmount);
        if (orderId == null) return null;
        orderRepository.insertItemsFromCart(accountId, orderId);
        // status remains 'pending' until payment callback confirms
        return orderId;
    }

    @Override
    public boolean finalizePaidOrder(Integer orderId) {
        return orderRepository.updateStatus(orderId, "confirmed");
    }

    @Override
    public List<CustomerOrderDTO> getAllOrders() throws SQLException {
        return orderRepository.findAllOrders();
    }

    @Override
    public boolean updateOrderStatus(Long orderId, String status) throws SQLException {
        // Validate trạng thái hợp lệ
        if (!status.matches("pending|confirmed|shipped|delivered|cancelled")) {
            throw new IllegalArgumentException("Trạng thái đơn hàng không hợp lệ!");
        }
        return orderRepository.updateOrderStatus(orderId, status);
    }

    @Override
    public CustomerOrderDTO getOrderById(Long orderId) throws SQLException {
        return orderRepository.findOrderById(orderId);
    }
}


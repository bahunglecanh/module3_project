package hunglcb.example.projectmd3.service.order;

import hunglcb.example.projectmd3.dto.CustomerOrderDTO;
import hunglcb.example.projectmd3.dto.OrderItemDTO;
import hunglcb.example.projectmd3.model.Order;
import hunglcb.example.projectmd3.dto.CustomerOrderDTO;
import hunglcb.example.projectmd3.dto.OrderItemDTO;
import hunglcb.example.projectmd3.repository.order.IOrderRepository;
import hunglcb.example.projectmd3.repository.order.OrderRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        // COD orders should be pending for admin approval
        orderRepository.updateStatus(orderId, "pending");
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
        // VNPay payment successful, but order should remain pending for admin approval
        // Clear the cart since payment was successful
        Order order = orderRepository.getOrderById(orderId);
        if (order != null) {
            orderRepository.clearCart(order.getAccountId());
        }
        return true; // Payment processed, order stays pending
    }

    @Override
    public List<Order> getUserOrders(Integer accountId) {
        if (accountId == null) {
            return new ArrayList<>();
        }
        return orderRepository.getOrdersByAccountId(accountId);
    }

    @Override
    public Order getOrderDetails(Integer orderId) {
        return orderRepository.getOrderWithDetails(orderId);
    }

    @Override
    public boolean cancelOrder(Integer orderId, Integer accountId) {
        // Check for null parameters
        if (orderId == null || accountId == null) {
            return false;
        }
        
        // Check if order belongs to user and can be cancelled
        Order order = orderRepository.getOrderById(orderId);
        if (order == null || !order.getAccountId().equals(accountId)) {
            return false;
        }
        
        if (!order.canCancel()) {
            return false;
        }
        
        return orderRepository.updateStatus(orderId, "cancelled");
    }


    @Override
    public boolean confirmOrder(Integer orderId) {
        // Check if order can be confirmed
        Order order = orderRepository.getOrderById(orderId);
        if (order == null || !order.canConfirm()) {
            return false;
        }
        // Admin confirms the order (changes from pending to confirmed)
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
    public boolean shipOrder(Integer orderId) {
        // Check if order can be shipped
        Order order = orderRepository.getOrderById(orderId);
        if (order == null || !order.canShip()) {
            return false;
        }
        // Admin ships the order (changes from confirmed to shipped)
        return orderRepository.updateStatus(orderId, "shipped");
    }

    @Override
    public boolean deliverOrder(Integer orderId) {
        // Check if order can be delivered
        Order order = orderRepository.getOrderById(orderId);
        if (order == null || !order.canDeliver()) {
            return false;
        }
        // Admin marks order as delivered (changes from shipped to delivered)
        return orderRepository.updateStatus(orderId, "delivered");
    }

    @Override
    public CustomerOrderDTO getOrderById(Long orderId) throws SQLException {
        return orderRepository.findOrderById(orderId);
    }
}



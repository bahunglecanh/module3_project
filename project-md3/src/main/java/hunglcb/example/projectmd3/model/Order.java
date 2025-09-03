package hunglcb.example.projectmd3.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order {
    private Integer id;
    private Integer accountId;
    private Integer shippingAddressId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private Integer paymentMethodId;
    private Timestamp createdAt;

    // Additional fields for display
    private User user;
    private UserAddress shippingAddress;
    private List<OrderItem> orderItems;
    private String paymentMethodName;

    public enum OrderStatus {
        PENDING("pending", "Chờ xác nhận"),
        CONFIRMED("confirmed", "Đã xác nhận"),
        SHIPPED("shipped", "Đang giao hàng"),
        DELIVERED("delivered", "Đã giao hàng"),
        CANCELLED("cancelled", "Đã hủy");

        private final String value;
        private final String displayName;

        OrderStatus(String value, String displayName) {
            this.value = value;
            this.displayName = displayName;
        }

        public String getValue() {
            return value;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static OrderStatus fromString(String text) {
            for (OrderStatus status : OrderStatus.values()) {
                if (status.value.equalsIgnoreCase(text)) {
                    return status;
                }
            }
            return PENDING;
        }
    }

    // Constructors
    public Order() {}

    public Order(Integer id, Integer accountId, Integer shippingAddressId, OrderStatus status,
                 BigDecimal totalAmount, Integer paymentMethodId, Timestamp createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.shippingAddressId = shippingAddressId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.paymentMethodId = paymentMethodId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Integer shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = OrderStatus.fromString(status);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(UserAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    // Utility methods
    public String getStatusDisplayName() {
        return status != null ? status.getDisplayName() : "Không xác định";
    }

    public boolean isPending() {
        return status == OrderStatus.PENDING;
    }

    public boolean isConfirmed() {
        return status == OrderStatus.CONFIRMED;
    }

    public boolean isShipped() {
        return status == OrderStatus.SHIPPED;
    }

    public boolean isDelivered() {
        return status == OrderStatus.DELIVERED;
    }

    public boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }

    public boolean canCancel() {
        return status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED;
    }

    public boolean canConfirm() {
        return status == OrderStatus.PENDING;
    }

    public boolean canShip() {
        return status == OrderStatus.CONFIRMED;
    }

    public boolean canDeliver() {
        return status == OrderStatus.SHIPPED;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", createdAt=" + createdAt +
                '}';
    }
}
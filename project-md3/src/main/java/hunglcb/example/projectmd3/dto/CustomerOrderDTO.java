package hunglcb.example.projectmd3.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerOrderDTO {
    private Long orderId;
    private String orderStatus;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

    // Thông tin khách hàng
    private Long accountId;
    private String email;
    private String fullName;
    private String phone;

    // Địa chỉ giao hàng
    private String addressLine;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    // Danh sách sản phẩm trong đơn
    private List<OrderItemDTO> items;

    public CustomerOrderDTO() {}

    public CustomerOrderDTO(Long orderId, String orderStatus, BigDecimal totalAmount, LocalDateTime orderDate,
                            Long accountId, String email, String fullName, String phone,
                            String addressLine, String city, String state, String postalCode, String country,
                            List<OrderItemDTO> items) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.accountId = accountId;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.items = items;
    }

    // Getter & Setter
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CustomerOrderDTO{" +
                "orderId=" + orderId +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", accountId=" + accountId +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", items=" + items +
                '}';
    }
}

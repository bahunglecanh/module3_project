package hunglcb.example.projectmd3.model;

import java.util.Date;

public class Order {
    private int id;
    private int accountId;
    private int shippingAddressId;
    private String status;
    private double totalAmount;
    private Date createdAt;

    public Order() {
    }

    public Order(int id, int accountId, int shippingAddressId, String status, double totalAmount, Date createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.shippingAddressId = shippingAddressId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
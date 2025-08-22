package hunglcb.example.projectmd3.model;

import java.sql.Timestamp;

public class Cart {
    private Integer id;
    private Integer accountId;
    private Timestamp createdAt;

    public Cart() {}

    public Cart(Integer id, Integer accountId, Timestamp createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.createdAt = createdAt;
    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}



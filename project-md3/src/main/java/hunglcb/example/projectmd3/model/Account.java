package hunglcb.example.projectmd3.model;

import java.sql.Timestamp;

public class Account {
    private Integer id;
    private String email;
    private String passwordHash;
    private Role role;
    private Status status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Enum for account roles
    public enum Role {
        USER, ADMIN
    }
    
    // Enum for account status
    public enum Status {
        ACTIVE, INACTIVE, BANNED
    }

    // Default constructor
    public Account() {
        this.role = Role.USER;
        this.status = Status.ACTIVE;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    // Constructor with basic info
    public Account(String email, String passwordHash) {
        this();
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Full constructor
    public Account(Integer id, String email, String passwordHash, Role role, Status status, 
                   Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Utility methods
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    public boolean isUser() {
        return this.role == Role.USER;
    }

    public boolean isActive() {
        return this.status == Status.ACTIVE;
    }

    public void updateTimestamp() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

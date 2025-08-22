package hunglcb.example.projectmd3.dto;

import hunglcb.example.projectmd3.model.Account;
import hunglcb.example.projectmd3.model.UserProfile;

import java.sql.Timestamp;

public class UserDTO {
    private String email;
    private Account.Role role;
    private Account.Status status;
    private Timestamp createdAt;
    
    private String fullName;
    private String phone;
    private UserProfile.Gender gender;

    public UserDTO() {
    }

    public UserDTO(String email, Account.Role role, Account.Status status, Timestamp createdAt, 
                   String fullName, String phone, UserProfile.Gender gender) {
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.fullName = fullName;
        this.phone = phone;
        this.gender = gender;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account.Role getRole() {
        return role;
    }

    public void setRole(Account.Role role) {
        this.role = role;
    }

    public Account.Status getStatus() {
        return status;
    }

    public void setStatus(Account.Status status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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

    public UserProfile.Gender getGender() {
        return gender;
    }

    public void setGender(UserProfile.Gender gender) {
        this.gender = gender;
    }


    public boolean isAdmin() {
        return this.role == Account.Role.ADMIN;
    }

    public boolean isActive() {
        return this.status == Account.Status.ACTIVE;
    }

    public String getFirstLetter() {
        if (fullName != null && !fullName.isEmpty()) {
            return fullName.substring(0, 1).toUpperCase();
        }
        if (email != null && !email.isEmpty()) {
            return email.substring(0, 1).toUpperCase();
        }
        return "U";
    }

    public String getDisplayName() {
        return fullName != null && !fullName.trim().isEmpty() ? fullName : email;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                '}';
    }
}
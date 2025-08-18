package hunglcb.example.projectmd3.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * User DTO class that combines Account and UserProfile information
 * This is used for easier handling in controllers and services
 */
public class User {
    // Account information
    private Integer id;
    private String email;
    private String passwordHash;
    private Account.Role role;
    private Account.Status status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Profile information
    private Integer profileId;
    private String fullName;
    private String phone;
    private UserProfile.Gender gender;
    private Date birthDate;
    private String avatarUrl;

    // Default constructor
    public User() {
        this.role = Account.Role.USER;
        this.status = Account.Status.ACTIVE;
    }

    // Constructor with basic registration info
    public User(String email, String passwordHash, String fullName) {
        this();
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
    }

    // Static factory method to create User from Account and UserProfile
    public static User fromAccountAndProfile(Account account, UserProfile profile) {
        User user = new User();
        
        // Copy account data
        user.setId(account.getId());
        user.setEmail(account.getEmail());
        user.setPasswordHash(account.getPasswordHash());
        user.setRole(account.getRole());
        user.setStatus(account.getStatus());
        user.setCreatedAt(account.getCreatedAt());
        user.setUpdatedAt(account.getUpdatedAt());
        
        // Copy profile data if exists
        if (profile != null) {
            user.setProfileId(profile.getId());
            user.setFullName(profile.getFullName());
            user.setPhone(profile.getPhone());
            user.setGender(profile.getGender());
            user.setBirthDate(profile.getBirthDate());
            user.setAvatarUrl(profile.getAvatarUrl());
        }
        
        return user;
    }

    // Convert to Account entity
    public Account toAccount() {
        return new Account(id, email, passwordHash, role, status, createdAt, updatedAt);
    }

    // Convert to UserProfile entity
    public UserProfile toUserProfile() {
        return new UserProfile(profileId, id, fullName, phone, gender, birthDate, avatarUrl);
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    // Utility methods
    public boolean isAdmin() {
        return this.role == Account.Role.ADMIN;
    }

    public boolean isUser() {
        return this.role == Account.Role.USER;
    }

    public boolean isActive() {
        return this.status == Account.Status.ACTIVE;
    }

    public String getFirstLetter() {
        if (fullName != null && !fullName.isEmpty()) {
            return fullName.substring(0, 1).toUpperCase();
        }
        return "U";
    }

    public void updateTimestamp() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
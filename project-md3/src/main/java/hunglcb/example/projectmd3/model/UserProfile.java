package hunglcb.example.projectmd3.model;

import java.sql.Date;

public class UserProfile {
    private Integer id;
    private Integer accountId;
    private String fullName;
    private String phone;
    private Gender gender;
    private Date birthDate;
    private String avatarUrl;
    
    // Enum for gender
    public enum Gender {
        MALE, FEMALE, OTHER
    }

    // Default constructor
    public UserProfile() {
    }

    // Constructor with basic info
    public UserProfile(Integer accountId, String fullName, String phone) {
        this.accountId = accountId;
        this.fullName = fullName;
        this.phone = phone;
    }

    // Full constructor
    public UserProfile(Integer id, Integer accountId, String fullName, String phone, 
                      Gender gender, Date birthDate, String avatarUrl) {
        this.id = id;
        this.accountId = accountId;
        this.fullName = fullName;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
        this.avatarUrl = avatarUrl;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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
    public String getFirstLetter() {
        if (fullName != null && !fullName.isEmpty()) {
            return fullName.substring(0, 1).toUpperCase();
        }
        return "U";
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}

package hunglcb.example.projectmd3.service.user;

public interface IPasswordResetService {
    boolean requestOtp(String email);
    boolean verifyOtp(String email, String otp);
    boolean resetPassword(String email, String newPassword);
}



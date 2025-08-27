package hunglcb.example.projectmd3.service.user;

import hunglcb.example.projectmd3.repository.user.IPasswordResetRepository;
import hunglcb.example.projectmd3.repository.user.PasswordResetRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class PasswordResetService implements IPasswordResetService {

    private final IPasswordResetRepository repository;
    private final IUserService userService;
    private final IEmailService emailService;

    public PasswordResetService(IUserService userService) {
        this.repository = new PasswordResetRepository();
        this.userService = userService;
        this.emailService = new EmailService();
    }

    @Override
    public boolean requestOtp(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        // Only allow for existing emails
        if (!userService.emailExists(email.trim())) return false;
        String otp = generateOtp(6);
        LocalDateTime expires = LocalDateTime.now().plusMinutes(5);
        boolean stored = repository.createOrUpdateOtp(email.trim(), otp, expires);
        if (!stored) return false;
        String subject = "Code OTP đat lai mat khau";
        String html = "<p>Xin chào,</p>" +
                "<p>Mã OTP của bạn là: <b>" + otp + "</b></p>" +
                "<p>OTP sẽ hết hạn lúc: " + expires + "</p>" +
                "<p>Nếu bạn không yêu cầu, vui lòng bỏ qua email này.</p>";
        boolean sent = emailService.send(email.trim(), subject, html);
        if (!sent) {
            // fallback: vẫn cho tiến trình tiếp tục để dev có thể test qua console
            System.out.println("[OTP] Send OTP to " + email + ": " + otp + " (expires at " + expires + ")");
        }
        return sent || true;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        if (email == null || otp == null) return false;
        String valid = repository.getValidOtp(email.trim());
        return otp.trim().equals(valid);
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        if (email == null || newPassword == null || newPassword.length() < 6) return false;
        boolean updated = userService.updatePasswordByEmail(email.trim(), newPassword);
        if (updated) {
            repository.invalidate(email.trim());
        }
        return updated;
    }

    private String generateOtp(int length) {
        SecureRandom random = new SecureRandom();
        String digits = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(digits.charAt(random.nextInt(digits.length())));
        }
        return sb.toString();
    }
}



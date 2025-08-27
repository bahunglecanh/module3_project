package hunglcb.example.projectmd3.repository.user;

import java.time.LocalDateTime;

public interface IPasswordResetRepository {

    boolean ensureTable();

    boolean createOrUpdateOtp(String email, String otpCode, LocalDateTime expiresAt);

    String getValidOtp(String email);

    LocalDateTime getExpiry(String email);

    boolean invalidate(String email);
}



package hunglcb.example.projectmd3.service.user;

public interface IEmailService {
    boolean send(String toEmail, String subject, String htmlContent);
}



package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.service.user.EmailService;
import org.junit.jupiter.api.Test;

public class EmailServiceTest {

    @Test
    public void testEmailServiceConfiguration() {
        System.out.println("=== Testing EmailService Configuration ===");
        EmailService emailService = new EmailService();
        System.out.println("EmailService initialized successfully");
    }

    @Test
    public void testSendEmail() {
        System.out.println("=== Testing Email Sending ===");
        EmailService emailService = new EmailService();
        
        String testEmail = "test@example.com"; // Change this to your email for testing
        String subject = "Test Email from Shoe Store";
        String htmlContent = "<h1>Test Email</h1><p>This is a test email to verify the email service is working.</p>";
        
        System.out.println("Attempting to send test email...");
        boolean result = emailService.send(testEmail, subject, htmlContent);
        
        if (result) {
            System.out.println("✅ Email sent successfully!");
        } else {
            System.out.println("❌ Email sending failed!");
        }
    }
}

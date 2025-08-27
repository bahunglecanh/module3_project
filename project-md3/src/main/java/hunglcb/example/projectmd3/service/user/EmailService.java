package hunglcb.example.projectmd3.service.user;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

public class EmailService implements IEmailService {

    private final String smtpHost;
    private final String smtpPort;
    private final String username;
    private final String password;
    private final boolean useTls;
    private final String fromName;

    public EmailService() {
        Properties fileProps = loadPropertiesFromClasspath("/smtp.properties");
        this.smtpHost = getConfig(fileProps, "smtp.host", "SMTP_HOST", "smtp.gmail.com");
        this.smtpPort = getConfig(fileProps, "smtp.port", "SMTP_PORT", "587");
        this.username = getConfig(fileProps, "smtp.username", "SMTP_USERNAME", "");
        this.password = getConfig(fileProps, "smtp.password", "SMTP_PASSWORD", "");
        this.useTls = Boolean.parseBoolean(getConfig(fileProps, "smtp.tls", "SMTP_TLS", "true"));
        this.fromName = getConfig(fileProps, "smtp.fromName", "SMTP_FROM_NAME", "Shoe Store");
    }

    @Override
    public boolean send(String toEmail, String subject, String htmlContent) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", String.valueOf(useTls));
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, fromName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Properties loadPropertiesFromClasspath(String path) {
        Properties properties = new Properties();
        try (InputStream in = EmailService.class.getResourceAsStream(path)) {
            if (in != null) {
                properties.load(in);
            }
        } catch (Exception ignored) {
        }
        return properties;
    }

    private static String getConfig(Properties props, String fileKey, String envKey, String def) {
        if (props != null) {
            String v = props.getProperty(fileKey);
            if (v != null && !v.isEmpty()) return v;
        }
        String env = System.getenv(envKey);
        if (env != null && !env.isEmpty()) return env;
        return def;
    }
}



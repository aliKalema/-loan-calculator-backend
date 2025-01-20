package co.ke.resilient.loan_calculator.config.notification;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "calc.email.template")
public class EmailTemplate {
    private String emailConfirmationSubject;
    private String emailConfirmationContent;
    private String otp;
}

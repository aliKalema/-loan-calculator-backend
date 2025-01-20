package co.ke.resilient.loan_calculator.config.notification;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "calc.email.configuration")
public class DefaultEmailConfig {
    private String host;
    private Integer port;
    private String username;
    private String password;
}

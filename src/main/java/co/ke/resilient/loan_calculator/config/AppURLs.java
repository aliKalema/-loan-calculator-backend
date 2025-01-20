package co.ke.resilient.loan_calculator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "calc")
public class AppURLs {
    private String resetPasswordUrl;
    private String setUsernamePasswordUrl;
}

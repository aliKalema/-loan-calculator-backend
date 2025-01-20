package co.ke.resilient.loan_calculator.config.notification;

import lombok.Data;

@Data
public class EmailConfig {
    private String host;
    private Integer port;
    private String username;
    private String password;
}

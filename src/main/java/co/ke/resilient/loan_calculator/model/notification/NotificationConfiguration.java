package co.ke.resilient.loan_calculator.model.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationConfiguration {
    private String host;

    private int port;

    private String username;

    private String password;
}

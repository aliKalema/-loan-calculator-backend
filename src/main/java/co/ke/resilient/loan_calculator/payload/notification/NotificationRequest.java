package co.ke.resilient.loan_calculator.payload.notification;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class NotificationRequest {
    private String recipient;

    private String sender;

    private String subject;

    private String message;

    private List<String> attachments;

    private String template;

    private Map<String, Object> variables;

    private String priority;
}

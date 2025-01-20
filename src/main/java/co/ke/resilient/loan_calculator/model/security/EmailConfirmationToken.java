package co.ke.resilient.loan_calculator.model.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
public class EmailConfirmationToken {
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @CreatedDate
    @ReadOnlyProperty
    private LocalDateTime timeStamp;
    
    @ManyToOne
    private User user;

    public EmailConfirmationToken() {}
}

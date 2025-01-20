package co.ke.resilient.loan_calculator.payload.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {
    private String username;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
}

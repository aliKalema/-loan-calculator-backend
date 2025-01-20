package co.ke.resilient.loan_calculator.model.security;

import java.util.List;

public enum RoleGroupEnum {
    USER(List.of("ROLE_USER")),
    ADMIN(List.of("ROLE_ADMIN"));

    private final List<String> roles;

    RoleGroupEnum(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return this.roles;
    }
}

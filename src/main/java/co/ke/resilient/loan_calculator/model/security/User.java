package co.ke.resilient.loan_calculator.model.security;

import co.ke.resilient.loan_calculator.payload.security.UserRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    @NotNull
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    @NotNull
    @Column(nullable = false)
    private String email;

    private String phone;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean multiFacterEnabled = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @JsonIgnore
    private RoleGroupEnum roleGroup;

    @JsonIgnore
    private boolean accountNonExpired;

    @JsonIgnore
    private boolean isEnabled;

    @JsonIgnore
    private boolean accountNonLocked;

    @JsonIgnore
    private boolean credentialsNonExpired;

    public User(UserRequest userRequest) {
        this.username = userRequest.getUsername();
        this.firstName = userRequest.getFirstName();
        this.lastName = userRequest.getLastName();
        this.phone = userRequest.getPhone();
        this.email = userRequest.getEmail();
        this.multiFacterEnabled = false;
        this.isEnabled = false;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return  this.roleGroup.getRoles().stream().map((SimpleGrantedAuthority::new)).toList();
    }
}

package co.ke.resilient.loan_calculator.config.security;

import co.ke.resilient.loan_calculator.model.security.User;
import co.ke.resilient.loan_calculator.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(String.format("user with username: %s Not Found", username)));
            log.info("User found in the database: {}", username);
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            user.getRoles().forEach(role -> {
//                authorities.add(new SimpleGrantedAuthority(role.()));
//            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}

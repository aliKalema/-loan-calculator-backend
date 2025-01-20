package co.ke.resilient.loan_calculator.service.security;

import co.ke.resilient.loan_calculator.model.security.User;
import co.ke.resilient.loan_calculator.payload.security.AuthResponse;
import co.ke.resilient.loan_calculator.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class  AuthService {
    private final AuthenticationProvider authenticationProvider;
    private final JWTService jwtService;
    private final OtpService otpService;
    private final UserRepository userRepository;

    public ResponseEntity<AuthResponse> login(String username, String password) {
        try {
            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if(user.getMultiFacterEnabled()) {
//                otpService.sendOtp();
                return ResponseEntity.ok(AuthResponse.builder()
                        .user(user)
                        .tokenValid(Boolean.FALSE)
                        .authValid(Boolean.TRUE)
                        .mfaRequired(Boolean.TRUE)
                        .accessToken("")
                        .refreshToken("")
                        .message("User Authenticated using username and Password")
                        .build());
            }
            return ResponseEntity.ok(AuthResponse.builder()
                    .tokenValid(Boolean.TRUE)
                    .authValid(Boolean.TRUE)
                    .mfaRequired(Boolean.FALSE)
                    .accessToken(jwtService.generateJwt(user))
                    .roles(user.getRoleGroup().getRoles())
                    .user(user)
                    .refreshToken("")
                    .message("User Authenticated using username and Password")
                    .build());
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.ok(AuthResponse.builder()
                    .tokenValid(Boolean.FALSE)
                    .authValid(Boolean.FALSE)
                    .mfaRequired(Boolean.FALSE)
                    .message("Invalid Credentials. Please try again.")
                    .accessToken("")
                    .refreshToken("")
                    .build());
        }
    }

}

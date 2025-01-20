package co.ke.resilient.loan_calculator.service.security;
import co.ke.resilient.loan_calculator.exceptions.Exceptions;
import co.ke.resilient.loan_calculator.model.security.EmailConfirmationToken;
import co.ke.resilient.loan_calculator.model.security.RoleGroupEnum;
import co.ke.resilient.loan_calculator.model.security.User;
import co.ke.resilient.loan_calculator.payload.security.EmailVerificationResponse;
import co.ke.resilient.loan_calculator.payload.security.UserRequest;
import co.ke.resilient.loan_calculator.repository.security.EmailConfirmationTokenRepository;
import co.ke.resilient.loan_calculator.repository.security.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService{

  private final UserRepository userRepository;
  final PasswordEncoder passwordEncoder;

  private final OtpService otpService;
  private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;

  @Transactional
  public void createUser(UserRequest userRequest, RoleGroupEnum roleGroup) {
    log.info("creating user : {}", userRequest);
    Optional<User> exist = userRepository.findByUsername(userRequest.getUsername());
    if (exist.isPresent()) {
      throw new Exceptions.UserExistException(String.format("Account with name: %s already exists", userRequest.getUsername()));
    }
    User user = new User(userRequest);
    user.setRoleGroup(roleGroup);
    user.setMultiFacterEnabled(false);
    user.setPassword("INVITE SENT");
    user=userRepository.save(user);
    log.info("created User  {}", user);
    otpService.inviteUser(user);
  }

@Transactional
  public EmailVerificationResponse setPassword(String token, String password) {
    Optional<EmailConfirmationToken> emailConfirmationToken = emailConfirmationTokenRepository.findByToken(token);
    String invalidMessage = "User details not found. Please login and regenerate the confirmation link.";
    if (emailConfirmationToken.isEmpty())
      return EmailVerificationResponse.builder().message(invalidMessage).verified(false).build();

    if(!token.equals(emailConfirmationToken.get().getToken())){
      return EmailVerificationResponse.builder().message(invalidMessage).verified(false).build();
    }
    User user = emailConfirmationToken.get().getUser();
    if (Objects.isNull(user)){
      return EmailVerificationResponse.builder().message(invalidMessage).verified(false).build();
    }
    user.setPassword(passwordEncoder.encode(password));
    user.setEnabled(true);
    userRepository.save(user);
    emailConfirmationTokenRepository.delete(emailConfirmationToken.get());
    return EmailVerificationResponse.builder().message("Password  Set Successfully.").verified(true).build();
  }

  public User getSessionUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = (String) authentication.getPrincipal();
    return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found with username: " + username));
  }

}

package co.ke.resilient.loan_calculator.service.security;
import co.ke.resilient.loan_calculator.config.AppURLs;
import co.ke.resilient.loan_calculator.config.notification.DefaultEmailConfig;
import co.ke.resilient.loan_calculator.config.notification.EmailTemplate;
import co.ke.resilient.loan_calculator.model.notification.NotificationConfiguration;
import co.ke.resilient.loan_calculator.model.security.EmailConfirmationToken;
import co.ke.resilient.loan_calculator.model.security.EmailService;
import co.ke.resilient.loan_calculator.model.security.User;
import co.ke.resilient.loan_calculator.payload.notification.NotificationRequest;
import co.ke.resilient.loan_calculator.repository.security.EmailConfirmationTokenRepository;
import co.ke.resilient.loan_calculator.utility.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpService {
    private final EmailService emailService;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    private final DefaultEmailConfig defaultEmailConfig;
    private final EmailTemplate emailTemplate;
    private final AppURLs appContext;
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");
    private final Map<String, String> otpManager = new HashMap<>();


    @Transactional
    public void inviteUser(final User user) {
        String token = StringUtils.generateNumerics(5);
        EmailConfirmationToken emailConfirmationToken =  EmailConfirmationToken.builder()
                .token(token)
                .user(user)
                .timeStamp(LocalDateTime.now())
                .build();
        emailConfirmationTokenRepository.save(emailConfirmationToken);
        NotificationConfiguration notificationConfiguration = NotificationConfiguration.builder()
                .host(defaultEmailConfig.getHost())
                .port(defaultEmailConfig.getPort())
                .username(defaultEmailConfig.getUsername())
                .password(defaultEmailConfig.getPassword())
                .build();

        NotificationRequest notificationRequest =  NotificationRequest.builder()
                .subject("Registration Invite")
                .recipient(user.getEmail())
                .message("")
                .build();
        Map<String, Object> variables =  new HashMap<>();
        String url = "";
        variables.put("url", token);
        variables.put("firstName", user.getFirstName());
        variables.put("companyName", "Resilient ltd");
        variables.put("username", user.getUsername());
        var images = Map.of("facebook","facebook2x.png",
                            "twitter","twitter2x.png",
                            "linkedin","linkedin2x.png",
                            "instagram","instagram2x.png",
                            "email-illustration","Email-Illustration.png");
        emailService.sendHtmlEmail(notificationConfiguration, notificationRequest, "emailconfirmation", variables,true, images);
    }

    public String generateOtp(User user) {
        String otp = StringUtils.generateNumerics(5);
        otpManager.put(user.getUsername(), otp);
        NotificationConfiguration notificationConfiguration = NotificationConfiguration.builder()
                .host(defaultEmailConfig.getHost())
                .port(defaultEmailConfig.getPort())
                .username(defaultEmailConfig.getUsername())
                .password(defaultEmailConfig.getPassword())
                .build();

        NotificationRequest notificationRequest =  NotificationRequest.builder()
                .subject(String.format("HRMS Verification Code: %s", otp))
                .recipient(user.getEmail())
                .build();
        Map<String, Object> variables =  new HashMap<>();
        variables.put("otp", otp);
        emailService.sendHtmlEmail(notificationConfiguration, notificationRequest, "otp", variables,false, Map.of());
        scheduleOtpExpiry(user.getUsername(), otp);
        return otp;
    }

    public boolean validateOtp(String username, String otp) {
        return otp.equals(otpManager.get(username));
    }

    private void scheduleOtpExpiry(String email, String otp) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    public void run() {
                        otpManager.remove(email, otp);
                    }
                },
                TimeUnit.MINUTES.toMillis(5)
        );
    }

    public void sendOtp(String code, String secret){

    }
}

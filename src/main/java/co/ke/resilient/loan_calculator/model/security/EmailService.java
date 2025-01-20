package co.ke.resilient.loan_calculator.model.security;

import co.ke.resilient.loan_calculator.config.notification.DefaultEmailConfig;
import co.ke.resilient.loan_calculator.exceptions.Exceptions;
import co.ke.resilient.loan_calculator.model.notification.NotificationConfiguration;
import co.ke.resilient.loan_calculator.payload.notification.NotificationRequest;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public  class EmailService {

    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    private final DefaultEmailConfig defaultEmailConfig;

    @Async
    public  void sendSimpleMessage(NotificationConfiguration notificationConfig, NotificationRequest request){
        assert notificationConfig != null;
        try {
            SimpleMailMessage message =  new SimpleMailMessage();
            message.setSubject(request.getSubject());
            message.setText(request.getMessage());
            message.setTo(request.getRecipient());
            message.setFrom(notificationConfig.getUsername());
            javaMailSender.send(message);
        }
        catch(Exception e){
            throw new Exceptions.EmailException("Failed to send Email");
        }
    }

    @Async
    public  void sendMimeMessageWithAttachments(NotificationConfiguration notificationConfig, NotificationRequest request, List<File> files){
        assert notificationConfig != null;
        try {
            MimeMessage message =  getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF_8_ENCODING");
            helper.setPriority(1);
            helper.setSubject(request.getSubject());
            helper.setText(request.getMessage());
            helper.setTo(request.getRecipient());
            files.stream().map(FileSystemResource::new).forEach((fsr)->{
                try {
                    helper.addAttachment(Objects.requireNonNull(fsr.getFilename()), fsr);
                } catch (MessagingException e) {
                    throw new Exceptions.MailAttachmentException("Failed to Embed a file to email");
                }
            });
            message.setFrom(notificationConfig.getUsername());
            javaMailSender.send(message);
        }
        catch(Exception e){
            throw new Exceptions.EmailException("Failed to send Email");
        }
    }

    @Async
    public  void sendMimeMessageWithEmbeddedFiles(final NotificationConfiguration notificationConfig, NotificationRequest request, List<File> files){
        assert notificationConfig != null;
        try {
            MimeMessage message =  getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF_8_ENCODING");
            helper.setPriority(1);
            helper.setSubject(request.getSubject());
            helper.setText(request.getMessage());
            helper.setTo(request.getRecipient());
            files.stream().map(FileSystemResource::new).forEach((fsr)->{
                try {
                    helper.addInline(getContentId(fsr.getFilename()), fsr);
                } catch (MessagingException e) {
                    throw new Exceptions.MailEmbededException("Failed to Embed a file to email");
                }
            });
            message.setFrom(notificationConfig.getUsername());
            javaMailSender.send(message);
        }
        catch(Exception e){
            throw new Exceptions.EmailException("Failed to send Email");
        }
    }

    @Async
    public  void sendHtmlEmail(NotificationConfiguration notificationConfig, NotificationRequest request, String templateName, Map<String, Object> variables, boolean containsImages, Map<String,String> images){
        assert notificationConfig != null;
        try {
            MimeMessage message =  getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setPriority(1);
            helper.setSubject(request.getSubject());
            helper.setTo(request.getRecipient());
            message.setFrom(notificationConfig.getUsername());
            Context context = new Context();
            context.setVariables(variables);
            String text = templateEngine.process(templateName, context);
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, "text/html");
            multipart.addBodyPart(messageBodyPart);
            if (containsImages) {
                images.forEach((key, value) -> {
                    BodyPart imageBodyPart = new MimeBodyPart();
                    ClassPathResource imgResource = new ClassPathResource(String.format("images/%s", value));
                    try (InputStream imgInputStream = imgResource.getInputStream()) {
                        DataSource dataSource = new ByteArrayDataSource(imgInputStream, "image/png");
                        imageBodyPart.setDataHandler(new DataHandler(dataSource));
                        imageBodyPart.setHeader("Content-ID", key);
                        multipart.addBodyPart(imageBodyPart);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to load image from resources", e);
                    }
                });
            }
            message.setContent(multipart);
            javaMailSender.send(message);
        }
        catch(Exception e){
            throw new Exceptions.EmailException(e.getMessage());
        }
    }

    private MimeMessage getMimeMessage(){
        return javaMailSender.createMimeMessage();
    }

    private String getContentId(String fileName){
        return String.format("<%s>",fileName);
    }

}

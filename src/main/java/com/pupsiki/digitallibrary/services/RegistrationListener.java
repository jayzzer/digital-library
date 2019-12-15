package com.pupsiki.digitallibrary.services;

import com.pupsiki.digitallibrary.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IUserService service;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Подтверждение аккаунта";
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<h3>Вы успешно зарегистрировались!</h3><a href=\"http://localhost:8081" + confirmationUrl + "\">Нажмите здесь для подтверждения почты</a>";
        helper.setText(htmlMsg, true);
        helper.setTo(recipientAddress);
        helper.setSubject(subject);
        mailSender.send(mimeMessage);
    }
}

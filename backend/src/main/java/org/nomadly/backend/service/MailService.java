package org.nomadly.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.nomadly.backend.model.MailStructure;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final ChangePasswordLinkService changePasswordLinkService;

    public void sendMail(String mail, MailStructure mailStructure) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(mail);

        mailSender.send(simpleMailMessage);
    }

    public void welcomeMail(String mail, String username) {
        String message = """
                Hi %s,

                Congratulations on becoming a part of the largest community of journalists! 🌟

                We're thrilled to have you join us. As a member of Journalist Junction, you'll have access to an array of powerful tools and resources to help you thrive in the dynamic world of journalism.

                Here's what you can look forward to:
                - Exclusive insights from industry experts 🧠
                - Networking opportunities with fellow journalists from around the globe 🌍
                - Access to cutting-edge tools to enhance your journalistic endeavors 🛠️

                If you ever need assistance, don't hesitate to reach out to us at: team.journalistjunction@gmail.com. We're here to support you every step of the way.

                Best wishes,
                The Journalist Junction Team
                """;
        String formattedMessage = String.format(message, username);

        sendMail(mail, new MailStructure("Congratulations!", formattedMessage));
    }

    public void sendSetPasswordEmail(String email) throws MessagingException {
        String uuid = UUID.randomUUID().toString();
        changePasswordLinkService.addNewLink(uuid, email);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set Password");
        mimeMessageHelper.setText("""
                Journalist-Junction Support Here!
                                
                Changing Password Is A Simple And Fast Process, So Don't Worry!
                                
                <h4 style={{color:'red'}}>*THE REQUEST LINK IS AVAILABLE ONLY 60 MINUTES*</h4>                
                <a href="http://localhost:3000/change-forgotten-password/%s" target="_blank"> Click Here To Set The New Password </a>
                </div>
                """.formatted(uuid), true);
        mailSender.send(mimeMessage);
    }

    public void sendPostNotification(String toEmail, Long articleId, String fromUsername, String toUsername) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Hurry up! %s Just Posted A New Article!".formatted(fromUsername));
        mimeMessageHelper.setText("""
                Hello %s,
                                                                                                                                   
                 We have some exciting news for you! 📰
                 %s has just published a new article on our platform, and we think you'll find it interesting.
                                
                <h4><b>Don’t miss out on this opportunity to check it out! </b>📚</h4>                
                <a href="http://localhost:3000/article/read/%s" target="_blank"> Read it now by following THIS LINK </a>
                <br />
                We hope you find the article both valuable and engaging!
                <br />
                <br />         
                Best regards,
                <br />
                The Journalist-Junction Team
                </div>
                """.formatted(toUsername, fromUsername, articleId), true);

        mailSender.send(mimeMessage);
    }
}

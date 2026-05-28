package vn.edu.hcmuaf.fit.laptrinhweb2.Auth;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class MailUtils {

    public static void sendOTP(String toEmail, String otp) {

        final String fromEmail = "flowerbed2603@gmail.com";
        final String password  = "zlleaawwzbckcamx";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );

            message.setSubject("SPGYM - OTP đăng ký tài khoản");

            message.setText("OTP Đăng ký tài khoản SPGYM: " + otp +
                    "\nHiệu lực trong vòng 3 phút.\nVui lòng nhập trước khi hết hạn.");

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

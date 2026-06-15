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

    public static void sendSecurityOTP(String toEmail, String otp, String type) throws Exception {
        final String fromEmail = "flowerbed2603@gmail.com";
        final String password  = "zlleaawwzbckcamx";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

        if ("UPDATE".equalsIgnoreCase(type)) {
            message.setSubject("SPGYM - Xác nhận Upload Public Key");
            message.setText("Bạn đang yêu cầu cập nhật Public Key trên hệ thống SPGYM.\n" +
                    "Mã OTP xác nhận của bạn là: " + otp + "\n" +
                    "Mã có hiệu lực trong 3 phút. Nếu không phải bạn thực hiện, hãy báo cáo ngay.");
        }
        else if ("LOST".equalsIgnoreCase(type) || "REPORT_LOSS".equalsIgnoreCase(type)) {
            message.setSubject("SPGYM - Xác nhận báo mất Public Key");
            message.setText("Hệ thống ghi nhận yêu cầu báo mất Public Key từ tài khoản của bạn.\n" +
                    "Mã OTP xác nhận báo mất là: " + otp + "\n" +
                    "Mã có hiệu lực trong 3 phút. Sau khi xác nhận, Public Key sẽ được vô hiệu hóa.");
        } else {
            message.setSubject("SPGYM - Thông báo bảo mật");
            message.setText("Mã xác thực bảo mật hệ thống của bạn là: " + otp);
        }

        Transport.send(message);
    }
}

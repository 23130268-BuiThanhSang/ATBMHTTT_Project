package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Random;
import vn.edu.hcmuaf.fit.laptrinhweb2.Auth.MailUtils;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

@WebServlet("/ReportLostKeyOTPController")
public class ReportLostKeyOTPController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            Account authUser = (Account) session.getAttribute("auth");

            if (authUser == null) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại!\"}");
                return;
            }

            String otp = String.format("%06d", new Random().nextInt(999999));

            session.setAttribute("LOST_KEY_OTP", otp);

            MailUtils.sendSecurityOTP(authUser.getAccountEmail(), otp, "LOST");

            response.getWriter().write("{\"status\":\"success\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Lỗi hệ thống khi gửi mail: " + e.getMessage() + "\"}");
        }
    }
}
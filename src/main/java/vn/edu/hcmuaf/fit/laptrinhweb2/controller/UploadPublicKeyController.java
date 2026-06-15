package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import vn.edu.hcmuaf.fit.laptrinhweb2.Auth.MailUtils;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@WebServlet("/UploadPublicKeyController")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
        maxFileSize = 1024 * 1024 * 10,       // 10 MB
        maxRequestSize = 1024 * 1024 * 100    // 100 MB
)
public class UploadPublicKeyController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            Account authUser = (Account) session.getAttribute("auth");

            if (authUser == null) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Phiên đăng nhập đã hết hạn hoặc máy chủ vừa khởi động lại! Vui lòng đăng nhập lại.\"}");
                return;
            }

            Part filePart = request.getPart("publicKeyFile");
            if (filePart == null || filePart.getSize() == 0) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Tệp tin không hợp lệ hoặc trống.\"}");
                return;
            }

// 1. Đọc toàn bộ file dưới dạng mảng byte thô (Cân được cả Nhị phân lẫn Văn bản)
            byte[] fileBytes = filePart.getInputStream().readAllBytes();
            String rawKey = "";

// 2. Kiểm tra xem file tải lên là định dạng Văn bản (PEM) hay định dạng Nhị phân (DER)
            String testStr = new String(fileBytes, StandardCharsets.UTF_8);

            if (testStr.contains("-----BEGIN PUBLIC KEY-----")) {
                // Nếu là file PEM văn bản chuẩn, giữ nguyên nội dung chuỗi
                rawKey = testStr.trim();
            } else {
                // Nếu là file Nhị phân (DER) chứa ký tự lạ, tự động chuyển nó sang dạng chuỗi Base64 chuẩn mã hóa
                rawKey = java.util.Base64.getEncoder().encodeToString(fileBytes);
            }

// 3. Kiểm tra an toàn trước khi tạo OTP
            if (rawKey.isEmpty()) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Không thể phân tích dữ liệu tệp khóa này!\"}");
                return;
            }

            // Tạo mã OTP ngẫu nhiên
            String otp = String.format("%06d", new Random().nextInt(999999));
            session.setAttribute("KEY_UPDATE_OTP", otp);
            session.setAttribute("TEMP_KEY", rawKey);

            String email = authUser.getAccountEmail();

            // Gửi OTP đồng bộ (Đợi gửi xong mới phản hồi cho Client)
            MailUtils.sendSecurityOTP(email, otp, "UPDATE");

            response.getWriter().write("{\"status\":\"success\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Lỗi máy chủ: " + e.getMessage() + "\"}");
        }
    }
}
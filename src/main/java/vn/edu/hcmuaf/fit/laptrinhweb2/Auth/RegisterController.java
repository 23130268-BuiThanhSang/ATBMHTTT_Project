package vn.edu.hcmuaf.fit.laptrinhweb2.Auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.io.IOException;

@WebServlet("/Register")
public class RegisterController extends HttpServlet {

    private AuthDao authDao = new AuthDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String step = request.getParameter("step");

        /* ================= STEP 1: NHẬP INFO ================= */
        if ("info".equals(step)) {

            String username = request.getParameter("reg_username");
            String email    = request.getParameter("reg_email");
            String phone    = request.getParameter("reg_phone");

            if (username == null || username.isBlank() || email == null || email.isBlank()) {
                request.setAttribute("regError", "Nhập đầy đủ thông tin");
                request.setAttribute("activeStep", "register-step1");
                request.getRequestDispatcher("login.jsp").forward(request,response);
                return;
            }

            if (authDao.existsByUsername(username)) {
                request.setAttribute("regError", "Username đã tồn tại");
                request.setAttribute("activeStep", "register-step1");
                request.getRequestDispatcher("login.jsp").forward(request,response);
                return;
            }

            if (authDao.existsByEmail(email)) {
                request.setAttribute("regError", "Email đã được sử dụng");
                request.setAttribute("activeStep", "register-step1");
                request.getRequestDispatcher("login.jsp").forward(request,response);
                return;
            }


            String otp = String.valueOf((int)(Math.random()*900000 + 100000));

            request.getSession().setAttribute("registerOTP", otp);
            request.getSession().setAttribute("reg_username", username);
            request.getSession().setAttribute("reg_email", email);
            request.getSession().setAttribute("reg_phone", phone);

            MailUtils.sendOTP(email, otp);

            request.setAttribute("activeStep", "register-step2");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }

        /* ================= STEP 2: OTP ================= */
        else if ("otp".equals(step)) {

            String inputOtp = request.getParameter("otp");
            String realOtp  = (String) request.getSession().getAttribute("registerOTP");

            if (inputOtp == null || realOtp == null || !inputOtp.equals(realOtp)) {
                request.setAttribute("regError", "OTP không đúng hoặc đã hết hạn");
                request.setAttribute("activeStep", "register-step2");
                request.getRequestDispatcher("login.jsp").forward(request,response);
                return;
            }

            request.setAttribute("activeStep", "register-step3");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }

        /* ================= STEP 3: PASSWORD ================= */
        else if ("password".equals(step)) {

            String pass = request.getParameter("reg_pass");
            String confirm = request.getParameter("reg_confirm");

            if (pass == null || confirm == null || !pass.equals(confirm)) {
                request.setAttribute("regError", "Mật khẩu không khớp");
                request.setAttribute("activeStep", "register-step3");
                request.getRequestDispatcher("login.jsp").forward(request,response);
                return;
            }

            String username = (String) request.getSession().getAttribute("reg_username");
            String email    = (String) request.getSession().getAttribute("reg_email");
            String phone    = (String) request.getSession().getAttribute("reg_phone");

            Account acc = new Account();
            acc.setUsername(username);
            acc.setAccountEmail(email);
            acc.setPhoneNumber(phone);
            acc.setPassword(PasswordUtils.md5(pass));
            acc.setAccountStatus(1);
            acc.setAvatarUrl("https://t4.ftcdn.net/jpg/07/03/86/11/360_F_703861114_7YxIPnoH8NfmbyEffOziaXy0EO1NpRHD.jpg");

            authDao.insert(acc);

            request.getSession().invalidate();

            request.setAttribute("success", "Đăng ký thành công");
            request.setAttribute("activeStep", "loginPage");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }
}


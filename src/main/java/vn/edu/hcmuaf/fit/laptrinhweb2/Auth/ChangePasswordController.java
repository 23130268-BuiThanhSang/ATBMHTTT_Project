package vn.edu.hcmuaf.fit.laptrinhweb2.Auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.laptrinhweb2.Auth.PasswordUtils;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.io.IOException;
@WebServlet("/ChangePassword")
public class ChangePasswordController extends HttpServlet {

    private AuthDao authDao = new AuthDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String step = request.getParameter("step");

        /* ===== STEP 1: EMAIL ===== */
        if ("email".equals(step)) {

            String email = request.getParameter("cp_email");

            Account acc = authDao.getUserByEmail(email);
            if (acc == null) {
                request.setAttribute("cpError","Email không tồn tại");
                request.setAttribute("activeStep","forgot-step1");
                request.getRequestDispatcher("login.jsp").forward(request,response);
                return;
            }

            String otp = String.valueOf((int)(Math.random()*900000 + 100000));
            request.getSession().setAttribute("forgotOTP", otp);
            request.getSession().setAttribute("forgotEmail", email);

            MailUtils.sendOTP(email, otp);

            request.setAttribute("activeStep","forgot-step2");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }

        /* ===== STEP 2: OTP ===== */
        else if ("otp".equals(step)) {

            String input = request.getParameter("otp");
            String real  = (String) request.getSession().getAttribute("forgotOTP");

            if (!input.equals(real)) {
                request.setAttribute("cpError","OTP không khớp");
                request.setAttribute("activeStep","forgot-step2");
                request.getRequestDispatcher("login.jsp").forward(request,response);
                return;
            }

            request.setAttribute("activeStep","forgot-step3");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }

        /* ===== STEP 3: PASSWORD ===== */
        else if ("password".equals(step)) {

            String pass = request.getParameter("cp_pass");
            String email = (String) request.getSession().getAttribute("forgotEmail");

            authDao.updatePasswordByEmail(email, PasswordUtils.md5(pass));
            request.getSession().invalidate();

            request.setAttribute("cpSuccess","Đổi mật khẩu thành công");
            request.setAttribute("activeStep","loginPage");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }
}


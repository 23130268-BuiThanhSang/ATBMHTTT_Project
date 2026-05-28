package vn.edu.hcmuaf.fit.laptrinhweb2.Auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.io.IOException;

@WebServlet(name = "AccountChangePassword", value = "/AccountChangePassword")
public class AccountChangePassword extends HttpServlet {

    private AuthDao authDao = new AuthDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Account sessionAcc = (Account) request.getSession().getAttribute("auth");

        if (sessionAcc == null) {
            response.sendRedirect("login.jsp");
            return;
        }

// lấy lại account từ DB (có password)
        Account acc = authDao.getUserByUsername(sessionAcc.getUsername());

        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        String confirm = request.getParameter("confirmPass");

        if (oldPass == null || newPass == null || confirm == null ||
                oldPass.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {

            request.setAttribute("cpError", "Vui lòng nhập đầy đủ thông tin.");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }

        if (!PasswordUtils.md5(oldPass).equals(acc.getPassword())) {
            request.setAttribute("cpError", "Mật khẩu hiện tại không đúng.");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }

        if (!newPass.equals(confirm)) {
            request.setAttribute("cpError", "Xác nhận mật khẩu không khớp.");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }

        if (!PasswordUtils.isStrongPassword(newPass)) {
            request.setAttribute("cpError", "Mật khẩu không đủ mạnh.");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }

        String hashed = PasswordUtils.md5(newPass);
        authDao.updatePasswordById(acc.getId(), hashed);

        request.setAttribute("cpSuccess", "Đổi mật khẩu thành công.");
        request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);

    }
}


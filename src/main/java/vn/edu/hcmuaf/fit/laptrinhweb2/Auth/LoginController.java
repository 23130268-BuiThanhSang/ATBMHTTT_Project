package vn.edu.hcmuaf.fit.laptrinhweb2.Auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/Login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        AuthService as = new AuthService();
        Account acc = as.login(username, pass);

        if(acc != null) {
            HttpSession session = request.getSession();
            session.setAttribute("auth", acc);
            session.setAttribute("auth_role",acc.getRole());
            response.sendRedirect("Home");
        } else {
            request.setAttribute("error", "tên tài khoản hoặc mật khẩu không khớp!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
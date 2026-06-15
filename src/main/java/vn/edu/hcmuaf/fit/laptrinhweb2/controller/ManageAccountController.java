package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

@WebServlet(urlPatterns = {"/account", "/profile", "/change-password", "/order-history"})
public class ManageAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account authUser = (Account) session.getAttribute("auth");

        if (authUser == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String servletPath = request.getServletPath();
        String targetJsp = "";

        switch (servletPath) {
            case "/account":
                request.setAttribute("activeTab", "account");
                targetJsp = "/AccountInfo.jsp";
                break;
            case "/profile":
                request.setAttribute("activeTab", "profile");
                targetJsp = "/UpdateAccountProfile.jsp";
                break;
            case "/change-password":
                request.setAttribute("activeTab", "password");
                targetJsp = "/ChangePassword.jsp";
                break;
            case "/order-history":
                request.setAttribute("activeTab", "orders");
                targetJsp = "/HistorySale.jsp";
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/account");
                return;
        }

        request.getRequestDispatcher(targetJsp).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

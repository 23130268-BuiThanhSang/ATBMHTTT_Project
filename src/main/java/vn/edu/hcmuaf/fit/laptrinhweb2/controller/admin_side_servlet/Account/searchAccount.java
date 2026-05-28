package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Account;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.DTO.AccountAdminView;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.AccountAdminService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "searchUser", value = "/searchAccount")
public class searchAccount extends HttpServlet {
    private final AccountAdminService accountService = new AccountAdminService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String searchInfo = request.getParameter("keyword");
        List<AccountAdminView> accounts = accountService.searchUser(searchInfo);
        request.setAttribute("accounts", accounts);

        request.getRequestDispatcher("manageUser.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
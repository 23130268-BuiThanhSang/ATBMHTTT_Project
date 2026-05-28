package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Account;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.DTO.AccountAdminView;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.AccountAdminService;

import java.io.IOException;

@WebServlet(name = "getAccountDetail", value = "/getAccountDetail")
public class getAccountDetail extends HttpServlet {
    private final AccountAdminService service = new AccountAdminService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);

        AccountAdminView account = service.getById(id);

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/getAllAccount");
            return;
        }
        request.setAttribute("account", account);
        request.setAttribute("base_username",account.getAccount().getUsername());
        request.setAttribute("base_mail",account.getAccount().getAccountEmail());
        request.getRequestDispatcher("manageUser_UserDetail.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
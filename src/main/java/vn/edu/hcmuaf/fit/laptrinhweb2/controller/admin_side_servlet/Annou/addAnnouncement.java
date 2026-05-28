package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Annou;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.AccountStatus;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.AnnouncementService;

import java.io.IOException;

@WebServlet(name = "addAnnouncementServlet", value = "/addAnnouncement")
public class addAnnouncement extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
                System.out.println("not admin");
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
        }

        try {
            String customerType = request.getParameter("customer_type");
            String content = request.getParameter("content");
            String targetUrl = request.getParameter("target_url");

            Integer expDay = Integer.parseInt(request.getParameter("exp_day"));
            Integer expMonth = Integer.parseInt(request.getParameter("exp_month"));
            Integer expYear = Integer.parseInt(request.getParameter("exp_year"));

            AccountStatus accountStatus;

            switch (customerType) {
                case "MEMBER":
                    accountStatus = AccountStatus.MEMBER;
                    break;
                case "NORMAL":
                    accountStatus = AccountStatus.NOT_MEMBER;
                    break;
                case "ALL":
                    accountStatus = AccountStatus.ALL;
                    break;
                default:
                    response.sendRedirect("manageAnnouncement.jsp?error=invalid_customer_type");
                    return;
            }

            AnnouncementService service = new AnnouncementService();
            service.addAnnouncement(
                    accountStatus,
                    content,
                    targetUrl,
                    expDay,
                    expMonth,
                    expYear
            );

            response.sendRedirect("manageAnoun.jsp?success=added");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manageAnoun.jsp?error=server_error");
        }
    }
}

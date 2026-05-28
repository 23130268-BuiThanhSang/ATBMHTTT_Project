package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Annou;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.AccountStatus;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Announcement;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.AnnouncementService;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "updateAnnoun", value = "/updateAnnoun")
public class updateAnnoun extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        String accountStatus = request.getParameter("accountStatus");
        String content = request.getParameter("content");
        String targetUrl = request.getParameter("targetUrl");
        String expiresAt = request.getParameter("expiresAt");
        boolean isActive = request.getParameter("isActive") != null;

        AnnouncementService  announcementService = new AnnouncementService();
        Announcement  announcement = new Announcement();
        announcement.setId(id);
        announcement.setAccountStatus(AccountStatus.valueOf(accountStatus));
        announcement.setContent(content);
        announcement.setTargetUrl(targetUrl);
        announcement.setExpiresAt(LocalDateTime.parse(expiresAt));
        announcement.setActive(isActive);

        announcementService.updateAnnouncement(announcement);
        response.sendRedirect("page_manageAnoun");
    }
}
package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Annou;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Announcement;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.AnnouncementService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "page_manageAnoun", value = "/page_manageAnoun")
public class page_manageAnoun extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        AnnouncementService announcementService = new AnnouncementService();
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        request.setAttribute("announs", announcements);
        request.getRequestDispatcher("manageAnoun_AllAnnoun.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
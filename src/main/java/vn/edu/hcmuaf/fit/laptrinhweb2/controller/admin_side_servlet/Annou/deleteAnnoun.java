package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Annou;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.AnnouncementService;

import java.io.IOException;

@WebServlet(name = "deleteAnnoun", value = "/deleteAnnoun")
public class deleteAnnoun extends HttpServlet {
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

        // Delete logic here using DAO
        AnnouncementService announcementService = new AnnouncementService();
        announcementService.deleteAnnouncement(id);

        response.sendRedirect("page_manageAnoun");
    }
}
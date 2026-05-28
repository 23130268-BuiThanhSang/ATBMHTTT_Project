package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Banner;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Banner;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.BannerService;

import java.io.IOException;

@WebServlet(name = "page_bannerDetail", value = "/page_bannerDetail")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class page_bannerDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        BannerService bannerService = new BannerService();
        Banner banner = bannerService.getById(id);

        if (banner == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("banner", banner);
        request.getRequestDispatcher("manageBanner_BannerDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
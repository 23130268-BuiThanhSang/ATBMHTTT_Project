package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Banner;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Banner;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.BannerService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "getAllBanner", value = "/page_manageBanner")
public class page_manageBanner extends HttpServlet {
    private final BannerService bannerService = new BannerService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<Banner> banners = bannerService.getAllBanners();
        request.setAttribute("banners", banners);
        request.getRequestDispatcher("manageBanner.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
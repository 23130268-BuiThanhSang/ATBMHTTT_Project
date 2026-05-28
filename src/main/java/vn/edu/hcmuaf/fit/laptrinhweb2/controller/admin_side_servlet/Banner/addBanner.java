package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Banner;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Banner;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.BannerService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;

import java.io.IOException;


@WebServlet(name = "addBanner", value = "/addBanner")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class addBanner extends HttpServlet {
    private final BannerService bannerService = new BannerService();
    ImageService imageService = new ImageService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Banner banner = new Banner();
        banner.setTargetUrl(request.getParameter("target_url"));

        Part imagePart = request.getPart("bannerImage");
        if (imagePart != null && imagePart.getSize() > 0) {
            String imageUrl = imageService.uploadImage(null, imagePart);
            banner.setImageUrl(imageUrl);
        }

        banner.setDisplayOrder(
                Integer.parseInt(request.getParameter("display_order"))
        );
        banner.setActive(
                "1".equals(request.getParameter("is_active"))
        );

        bannerService.addBanner(banner);

        // PRG pattern
        response.sendRedirect(request.getContextPath() + "/page_manageBanner");
    }
}
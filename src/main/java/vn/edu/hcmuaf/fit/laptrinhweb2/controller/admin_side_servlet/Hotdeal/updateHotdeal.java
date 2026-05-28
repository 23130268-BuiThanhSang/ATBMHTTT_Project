package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Hotdeal;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.HotDeal;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.HotDealService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;

import java.io.IOException;

@WebServlet(name = "updateHotdeal", value = "/updateHotdeal")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 50 * 1024 * 1024
)
public class updateHotdeal extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ImageService imageService = new ImageService();
        HotDealService hotDealService = new HotDealService();
        int id = Integer.parseInt(request.getParameter("id"));

        String name = request.getParameter("name");
        int displayOrder = Integer.parseInt(request.getParameter("display_order"));
        double discountPer = Double.parseDouble(request.getParameter("discount_per"));

        String thumbnailUrl = null;
        String mainImageUrl = null;

        Part thumbnailPart = request.getPart("thumbnailImage");
        if (thumbnailPart != null && thumbnailPart.getSize() > 0) {
            thumbnailUrl = imageService.uploadImage(null, thumbnailPart);
        }

        Part mainImagePart = request.getPart("mainImage");
        if (mainImagePart != null && mainImagePart.getSize() > 0) {
            mainImageUrl = imageService.uploadImage(null, mainImagePart);
        }

        HotDeal hotDeal = new HotDeal();
        hotDeal.setId(id);
        hotDeal.setName(name);
        hotDeal.setThumbnailUrl(thumbnailUrl);
        hotDeal.setMainImageUrl(mainImageUrl);
        hotDeal.setDisplayOrder(displayOrder);
        hotDeal.setDiscountPercentage(discountPer);

        hotDealService.updateHotDeal(hotDeal);

        // PRG pattern
        response.sendRedirect(
                request.getContextPath() + "/getHotdeal?id=" + id
        );
    }
}
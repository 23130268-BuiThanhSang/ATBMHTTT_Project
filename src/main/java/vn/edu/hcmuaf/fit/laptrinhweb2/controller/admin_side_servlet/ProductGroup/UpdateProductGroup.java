package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.ProductGroup;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductGroupService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;

@WebServlet(name = "UpdateProductGroupServlet", urlPatterns = {"/updateProductGroup"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class UpdateProductGroup extends HttpServlet {

    private final ProductGroupService productGroupService = new ProductGroupService();
    private final ImageService imageService = new ImageService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String typeParam = request.getParameter("groupType");
        GroupType type = GroupType.valueOf(typeParam);

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String thumbnailUrl = request.getParameter("oldThumbnailUrl");
            Part imagePart = request.getPart("thumbnailImage");
            if (imagePart != null && imagePart.getSize() > 0) {
                thumbnailUrl = imageService.uploadImage(null, imagePart);
            }
            String imageUrl = request.getParameter("oldImageUrl");
            Part imagePart2 = request.getPart("mainImage");
            if (imagePart2 != null && imagePart2.getSize() > 0) {
                imageUrl = imageService.uploadImage(null, imagePart2);
            }
            String displayOrderStr = request.getParameter("displayOrder");

            Integer displayOrder = null;
            if (displayOrderStr != null && !displayOrderStr.isEmpty()) {
                displayOrder = Integer.parseInt(displayOrderStr);
            }
            productGroupService.updateGroup(type, id, name, thumbnailUrl, imageUrl, displayOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(getRedirectUrl(type));
    }

    private String getRedirectUrl(GroupType type) {
        switch (type) {
            case BRAND:
                return "manageBrand";
            case COLLECTION:
                return "manageCollection";
            case SPORT:
                return "manageSport";
            default:
                return "manageBrand";
        }
    }
}
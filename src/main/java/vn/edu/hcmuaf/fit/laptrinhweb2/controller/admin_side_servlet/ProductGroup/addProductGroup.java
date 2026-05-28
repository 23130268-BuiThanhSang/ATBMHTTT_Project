package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.ProductGroup;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductGroupService;

import java.io.IOException;

@WebServlet(name = "AddProductGroupServlet", urlPatterns = {"/addProductGroup"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class addProductGroup extends HttpServlet {

    private final ProductGroupService productGroupService = new ProductGroupService();
    private final ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String typeParam = request.getParameter("type");
        GroupType type = GroupType.valueOf(typeParam);

        request.setAttribute("groupType", type.name());
        request.setAttribute("groupTypeName", getGroupTypeName(type));

        request.getRequestDispatcher("manageProductGroup_addPG.jsp").forward(request, response);
    }

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

        try {
            String typeParam = request.getParameter("groupType");
            GroupType type = GroupType.valueOf(typeParam);

            String name = request.getParameter("name");

            String thumbnailUrl = "";
            Part imagePart = request.getPart("thumbnailImage");
            if (imagePart != null && imagePart.getSize() > 0) {
                thumbnailUrl = imageService.uploadImage(null, imagePart);
            }
            String imageUrl = "";
            Part imagePart2 = request.getPart("mainImage");
            if (imagePart2 != null && imagePart2.getSize() > 0) {
                imageUrl = imageService.uploadImage(null, imagePart2);
            }
            String displayOrderStr = request.getParameter("displayOrder");
            Integer displayOrder = null;
            if (displayOrderStr != null && !displayOrderStr.isEmpty()) {
                displayOrder = Integer.parseInt(displayOrderStr);
            }

            productGroupService.addGroup(type, name, thumbnailUrl, imageUrl, displayOrder);

            response.sendRedirect(getRedirectUrl(type));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getGroupTypeName(GroupType type) {
        switch (type) {
            case BRAND:
                return "Thương hiệu";
            case COLLECTION:
                return "Bộ sưu tập";
            case SPORT:
                return "Môn thể thao";
            default:
                return "Nhóm sản phẩm";
        }
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
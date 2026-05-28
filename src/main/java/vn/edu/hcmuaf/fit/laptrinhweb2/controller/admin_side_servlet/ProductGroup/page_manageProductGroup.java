package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.ProductGroup;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductGroup;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductGroupService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageProductGroupServlet", urlPatterns = {
        "/manageBrand",
        "/manageCollection",
        "/manageSport"
})
public class page_manageProductGroup extends HttpServlet {

    private final ProductGroupService productGroupService = new ProductGroupService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        GroupType type = getGroupTypeFromPath(request);

        List<ProductGroup> groups = productGroupService.getGroups(type);
        request.setAttribute("groups", groups);
        request.setAttribute("groupType", type.name());
        request.setAttribute("groupTypeName", getGroupTypeName(type));

        request.getRequestDispatcher("/manageProductGroup.jsp").forward(request, response);
    }

    private GroupType getGroupTypeFromPath(HttpServletRequest request) {
        String path = request.getServletPath();
        if (path.contains("Brand")) {
            return GroupType.BRAND;
        } else if (path.contains("Collection")) {
            return GroupType.COLLECTION;
        } else if (path.contains("Sport")) {
            return GroupType.SPORT;
        }
        throw new IllegalArgumentException("Invalid path");
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
}
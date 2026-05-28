package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.ProductGroup;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductGroupService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;

@WebServlet(name = "DeleteProductGroupServlet", urlPatterns = {"/deleteProductGroup"})
public class deleteProductGroup extends HttpServlet {

    private final ProductGroupService productGroupService = new ProductGroupService();
    private final ProductService productService = new ProductService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String typeParam = request.getParameter("groupType");
        GroupType type = GroupType.valueOf(typeParam);

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (type == GroupType.COLLECTION) {
                productService.removeCollectionFromProducts(id);
            }
            productGroupService.deleteGroup(type, id);
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
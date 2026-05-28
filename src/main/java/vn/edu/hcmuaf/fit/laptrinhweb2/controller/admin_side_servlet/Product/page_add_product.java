package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductGroup;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductGroupService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "page_add_product", value = "/page_add_product")
public class page_add_product extends HttpServlet {
    private final ProductGroupService productGroupService = new ProductGroupService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        List<ProductGroup> brandList =
                productGroupService.getGroups(GroupType.BRAND);

        List<ProductGroup> collectionList =
                productGroupService.getGroups(GroupType.COLLECTION);

        List<ProductGroup> sportList =
                productGroupService.getGroups(GroupType.SPORT);


        request.setAttribute("brandList", brandList);
        request.setAttribute("collectionList", collectionList);
        request.setAttribute("sportList", sportList);
        request.setAttribute("defaultTab", "addProduct");
        request.getRequestDispatcher("ManageProduct_AddProduct.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
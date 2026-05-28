package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Product;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductGroup;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductGroupService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "getProductDetail", value = "/getProductDetail")
public class getProductDetail extends HttpServlet {
    ProductService productService = new ProductService();
    ProductGroupService productGroupService = new ProductGroupService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("page_allProduct");
            return;
        }

        try {
            int productId = Integer.parseInt(idStr);
            Product product = productService.getProduct(productId);
            if (product == null) {
                response.sendRedirect("admin_list_product?error=NotFound");
                return;
            }
            request.setAttribute("product", product);

            List<ProductGroup> brands = productGroupService.getGroups(GroupType.BRAND);
            request.setAttribute("brandList", brands);
            List<ProductGroup> collections = productGroupService.getGroups(GroupType.COLLECTION);
            request.setAttribute("collectionList", collections);
            request.getRequestDispatcher("manageProduct_ProductDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("page_allProduct");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.DTO.ProductCard;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductGroup;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.optionListProduct.SizeOption;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductCardService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductGroupService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "page_allProduct", value = "/page_allProduct")
public class page_allProduct extends HttpServlet {
    ProductCardService productCardService = new ProductCardService();
    ProductGroupService productGroupService = new ProductGroupService();
    ProductService productService = new ProductService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


        Integer productTypeId = null;
        String pt = request.getParameter("productType");
        if (pt != null && !pt.isEmpty()) {
            productTypeId = Integer.parseInt(pt);
        }

        Map<Integer, List<SizeOption>> sizeOptionsMap = new HashMap<>();

        // Shoes
        List<SizeOption> shoeSizes = new ArrayList<>();
        for (int i = 35; i <= 43; i++) shoeSizes.add(new SizeOption(String.valueOf(i), String.valueOf(i)));
        sizeOptionsMap.put(4, shoeSizes);

        // Equipment
        List<SizeOption> equipmentSizes = new ArrayList<>();
        equipmentSizes.add(new SizeOption("100", "Nhỏ"));
        equipmentSizes.add(new SizeOption("200", "Vừa"));
        equipmentSizes.add(new SizeOption("300", "Lớn"));
        sizeOptionsMap.put(1, equipmentSizes);

        // Clothes
        List<SizeOption> clothesSizes = new ArrayList<>();
        String[] sizes = {"S", "M", "L", "XL", "XXL"};
        for(int i=0; i<sizes.length; i++) clothesSizes.add(new SizeOption(String.valueOf(i+1), sizes[i]));
        sizeOptionsMap.put(3, clothesSizes);

        // Accessories
        sizeOptionsMap.put(2, new ArrayList<>());

        List<SizeOption> sizeOptions = null;
        if (productTypeId != null) {
            sizeOptions = sizeOptionsMap.get(productTypeId);
        }
        request.setAttribute("sizeOptions", sizeOptions);

        // 3. Pagination Logic
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try { page = Integer.parseInt(pageParam); } catch (Exception ignored) {}
        }

        // 4. Get Filter Parameters
        String color = request.getParameter("color");
        String gender = request.getParameter("gender");

        String brandIdStr = request.getParameter("brandId");
        Integer brandId = (brandIdStr != null && !brandIdStr.isEmpty()) ? Integer.parseInt(brandIdStr) : null;

        String collectionStr = request.getParameter("collectionId");
        Integer collectionId = (collectionStr != null && !collectionStr.isEmpty()) ? Integer.parseInt(collectionStr) : null;

        String sizeStr = request.getParameter("size");
        Integer size = (sizeStr != null && !sizeStr.isEmpty()) ? Integer.parseInt(sizeStr) : null;

        String keyword = request.getParameter("keyword");
        if (keyword != null) keyword = keyword.trim();

        // 5. Fetch Data from Service
        List<ProductCard> products = productCardService.getAllProductCardForListProductAndFilterAndSearch(
                productTypeId, page, keyword, color, gender, brandId, collectionId, size
        );

        // Fill details (ratings, review counts)
        for (ProductCard productCard : products) {
            productCard.setReviewCount(productService.countReviews(productCard.getId()));
            productCardService.fillRating(productCard);
        }

        int totalPages = productCardService.getTotalPagesForListProductFilterAndSearch(
                productTypeId, keyword, color, gender, brandId, collectionId, size
        );

        // 6. Set Attributes for JSP
        request.setAttribute("products", products);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);

        // Maintain Filter State
        request.setAttribute("keyword", keyword);
        request.setAttribute("color", color);
        request.setAttribute("gender", gender);
        request.setAttribute("brandId", brandId);
        request.setAttribute("size", size);
        request.setAttribute("collectionId", collectionId);
        request.setAttribute("productType", productTypeId);

        // Load Sidebar Options
        List<ProductGroup> brands = productGroupService.getGroups(GroupType.BRAND);
        request.setAttribute("brands", brands);

        List<ProductGroup> collections = productGroupService.getGroups(GroupType.COLLECTION);
        request.setAttribute("collections", collections);

        // 7. Forward to Admin JSP
        request.getRequestDispatcher("manageProduct_AllProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
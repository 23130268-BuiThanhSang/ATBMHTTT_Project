package vn.edu.hcmuaf.fit.laptrinhweb2.controller.ListProduct;

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

@WebServlet(name = "ListAllProductNewController", value = "/listNewProduct")
public class ListAllProductNewController extends HttpServlet {
    ProductCardService productCardService = new ProductCardService();
    ProductGroupService productGroupService = new ProductGroupService();
    ProductService productService = new ProductService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * thực hiện lây tham số loại sản phẩm, để là Integer để có thể nhận null khi người dùng chưa thực hiện chọn kiểu sản phẩm
         */
        Integer productTypeId = null;
        String pt = request.getParameter("productType");
        if (pt != null && !pt.isEmpty()) {
            productTypeId = Integer.parseInt(pt);
        }
        /**
         * thực hiện sử dụng map để lưu trữ size option cho từng loại sản phẩm
         */
        Map<Integer, List<SizeOption>> sizeOptionsMap = new HashMap<>();
        /**
         * thục hiện tao size option cho giày
         */
        List<SizeOption> shoeSizes = new ArrayList<>();
        shoeSizes.add(new SizeOption("35", "35"));
        shoeSizes.add(new SizeOption("36", "36"));
        shoeSizes.add(new SizeOption("37", "37"));
        shoeSizes.add(new SizeOption("38", "38"));
        shoeSizes.add(new SizeOption("39", "39"));
        shoeSizes.add(new SizeOption("40", "40"));
        shoeSizes.add(new SizeOption("41", "41"));
        shoeSizes.add(new SizeOption("42", "42"));
        shoeSizes.add(new SizeOption("43", "43"));
        sizeOptionsMap.put(4, shoeSizes);
        /**
         * thục hiện tao size option cho thiết bị
         */
        List<SizeOption> equipmentSizes = new ArrayList<>();
        equipmentSizes.add(new SizeOption("100", "Nhỏ"));
        equipmentSizes.add(new SizeOption("200", "Vừa"));
        equipmentSizes.add(new SizeOption("300", "Lớn"));
        sizeOptionsMap.put(1, equipmentSizes);

        /**
         * thục hiện tao size option cho quần áo
         */
        List<SizeOption> clothesSizes = new ArrayList<>();
        clothesSizes.add(new SizeOption("1", "S"));
        clothesSizes.add(new SizeOption("2", "M"));
        clothesSizes.add(new SizeOption("3", "L"));
        clothesSizes.add(new SizeOption("4", "XL"));
        clothesSizes.add(new SizeOption("5", "XXL"));
        sizeOptionsMap.put(3, clothesSizes);
        /**
         * thục hiện tao size option cho phụ kiệnn, do phụ kiện không có size nên để trống
         */
        sizeOptionsMap.put(2, new ArrayList<>());
        /**
         * truyền size option lên jsp
         */
        List<SizeOption> sizeOptions = null;
        if (productTypeId != null) {
            sizeOptions = sizeOptionsMap.get(productTypeId);
        }
        request.setAttribute("sizeOptions", sizeOptions);


        /**
         * thực hiện lấy thamm số trang đảm bảo phân trang hoạt động đúng và biết cách lấy dữ liệu từ database
         */
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (Exception ignored) {
            }
        }
        /**
         * thực hiện lấy tham số từ filter để thực hiện lọc sản phẩm
         */
        String color = request.getParameter("color");
        String gender = request.getParameter("gender");
        String brandIdStr = request.getParameter("brandId");
        Integer brandId = (brandIdStr != null && !brandIdStr.isEmpty()) ? Integer.parseInt(brandIdStr) : null;
        String collectionStr = request.getParameter("collectionId");
        Integer collectionId = (collectionStr != null && !collectionStr.isEmpty()) ? Integer.parseInt(collectionStr) : null;
        String sizeStr = request.getParameter("size");
        Integer size = (sizeStr != null && !sizeStr.isEmpty()) ? Integer.parseInt(sizeStr) : null;
        request.setAttribute("size", size);
        /**
         * thực hiện lấy tham số từ ô search
         */
        String keyword = request.getParameter("keyword");
        if (keyword != null) {
            keyword = keyword.trim();
        }

        /**
         * thực hiện gọi xuống service để lấy danh sách sản phẩm theo filter va search
         */
        List<ProductCard> products = productCardService.getAllNewProductCardForListProductAndFilterAndSearch(productTypeId, page, keyword, color, gender, brandId,collectionId, size);
        for (ProductCard productCard : products) {
            productCard.setReviewCount(productService.countReviews(productCard.getId()));
            productCardService.fillRating(productCard);
        }
        int totalPages = productCardService.getTotalPagesForListNewProductFilterAndSearch(productTypeId, keyword, color, gender, brandId,collectionId, size);

        /**
         * truyền dữ liệu lên jsp
         */
        request.setAttribute("products", products);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);

        /**
         * truyền lại các tham số filter để giữ trạng thái lọc trên giao diện và search
         */
        request.setAttribute("keyword", keyword);
        request.setAttribute("color", color);
        request.setAttribute("gender", gender);
        request.setAttribute("brandId", brandId);
        request.setAttribute("size", size);
        request.setAttribute("collectionId", collectionId);
        request.setAttribute("productType", productTypeId);
        /**
         * lấy danh sách brand để hiển thị trên filter
         */
        List<ProductGroup> brands = productGroupService.getGroups(GroupType.BRAND);
        request.setAttribute("brands", brands);
        /**
         * thực hiện lấy collection cho filter
         */
        List<ProductGroup> collections = productGroupService.getGroups(GroupType.COLLECTION);
        request.setAttribute("collections", collections);
        request.getRequestDispatcher("PageAllProductNew.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
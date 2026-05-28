package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Product;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductReview;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ProductMainPage")
public class ProductMainPageController extends HttpServlet {

    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = 1;
        String idRaw = request.getParameter("id");
        if (idRaw != null) {
            try {
                id = Integer.parseInt(idRaw);
            } catch (NumberFormatException e) {
                id = 1;
            }
        }

        Product product = productService.getProduct(id);
        if (product == null) {
            response.sendRedirect("Home");
            return;
        }

        String ratingRaw = request.getParameter("rating");

        Integer rating = null;
        if (ratingRaw != null && !ratingRaw.isEmpty()) {
            rating = Integer.parseInt(ratingRaw);
        }

        // ====== GỌI SERVICE ======
        List<ProductReview> reviews = productService.getReviews(id, rating);
        int totalReviewCount = productService.countReviews(id);
        Map<Integer, String> sizeMap = productService.buildSizeMap(product);
        Map<String, Object> ratingInfo = productService.buildRatingInfo(id);

        // ====== SET ATTRIBUTE ======
        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);
        request.setAttribute("reviewCount", totalReviewCount);
        request.setAttribute("avgRating", ratingInfo.get("avgRating"));
        request.setAttribute("fullStars", ratingInfo.get("fullStars"));
        request.setAttribute("hasHalfStar", ratingInfo.get("hasHalfStar"));
        request.setAttribute("sizeMap", sizeMap);
        request.setAttribute("selectedRating", rating);
        request.setAttribute("isLogin",
                request.getSession().getAttribute("auth") != null);

        request.getRequestDispatcher("/ProductMainPage.jsp")
                .forward(request, response);
    }
}




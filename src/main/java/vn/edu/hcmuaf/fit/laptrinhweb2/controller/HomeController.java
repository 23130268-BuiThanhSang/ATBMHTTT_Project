package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Banner;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.DTO.ProductCard;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.HotDeal;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductGroup;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.*;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeController", value = "/Home")
public class HomeController extends HttpServlet {
    private BannerService bannerService= new BannerService();
    private HotDealService hotDealService= new HotDealService();
    private ProductCardService productCardService= new ProductCardService();
    private ProductService productService= new ProductService();
    private ProductGroupService productGroupService= new ProductGroupService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * Lấy 5 banner active để hiển thị trên trang chủ
         */
        List<Banner> banners = bannerService.getTop5BannersActive();
        /**
         * lấy hotdeal chính và phụ để hiển thị trên trang chủ
         */
        HotDeal mainHotDeal = hotDealService.getHotDealTheMostDisplayOrder();
        List<HotDeal> subHotDeals = hotDealService.getSubHotdeal();

        /**
         * lấy danh sách sản phẩm hotdeal để hiển thị trên trang chủ
         * phương thức quan trọng
         * kêt hợp thực hiện đếm số lượng đánh giá và điền rating cho từng sản phẩm
         */
        List<ProductCard> hotDealProductCards = productCardService.getTopDiscountProductsForHome();
        for (ProductCard productCard : hotDealProductCards) {
            productCard.setReviewCount( productService.countReviews(productCard.getId()));
            productCardService.fillRating(productCard);
        }
        /**
         * lấy danh sách sản phẩm mới nhất để hiển thị trên trang chủ
         * phương thức quan trọng
         */
        List<ProductCard> ProductCardsNewest= productCardService.getNewestProductsForHome();
        for (ProductCard productCard1 : ProductCardsNewest) {
            productCard1.setReviewCount( productService.countReviews(productCard1.getId()));
            productCardService.fillRating(productCard1);
        }
        /**
         * thực hiện lấy 12 thương hiệu có có display order cao nhất do admin thiết lập để hiển thị trên trang chủ
         */
        List<ProductGroup> brands = productGroupService.getTopProductGroupsByType(GroupType.BRAND);
        /**
         * thực hiện lấy 10 bộ sưu tập có display order cao nhất do admin thiết lập để hiển thị trên trang chủ
         */
        List<ProductGroup> collections = productGroupService.getTopProductGroupsByType(GroupType.COLLECTION);
        /**
        * set attribute và chuyển trang đến HomePage.jsp
        */
        request.setAttribute("hotDealProductCards",hotDealProductCards);
        request.setAttribute("ProductCardsNewest",ProductCardsNewest);
        request.setAttribute("brands", brands);
        request.setAttribute("collections", collections);
        request.setAttribute("mainHotDeal", mainHotDeal);
        request.setAttribute("subHotDeals", subHotDeals);
        request.setAttribute("banners", banners);
        request.getRequestDispatcher("HomePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
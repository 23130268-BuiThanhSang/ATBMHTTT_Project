package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.DTO.ProductCard;

import java.util.List;

public class ProductCardService {

    private final ProductDao productDao = new ProductDao();
    int pageSize = 12;

    /**
     * đây là phương thức thực hiện láy danh sách sản phẩm dưới dạng productCard chưa xóa vì có thể sử dụng cho trang khác
     * @param page
     * @return
     */
    public List<ProductCard> getProductCards(int page) {
        int offset = (page - 1) * pageSize;
        List<ProductCard> cards = productDao.getProductCardRaw(pageSize, offset);

        for (ProductCard card : cards) {
            applyDiscount(card);
        }

        return cards;
    }

    /**
     *  đây là phương thức thực hiện láy danh sách sản phẩm dưới dạng productCard theo loại sản phẩm phương thức chưa bỏ đi do có thể có trang sử dụng
     * @param productTypeId
     * @param page
     * @return
     */
    public List<ProductCard> getProductCardsByType(int productTypeId, int page) {
        int offset = (page - 1) * pageSize;
        List<ProductCard> cards = productDao.getProductCardByType(productTypeId, pageSize, offset);

        for (ProductCard card : cards) {
            applyDiscount(card);
        }
        return cards;
    }

    /**
     * đây là phương thức thực hiện áp dụng giảm giá cho sản phẩm
     * @param card
     */
    private void applyDiscount(ProductCard card) {
        double discount = card.getDiscountPercent();

        if (discount > 0) {
            double basePrice = card.getPrice();
            card.setOldPrice(basePrice);
            card.setPrice(basePrice * (100 - discount) / 100);
        } else {
            card.setOldPrice(null);
        }
    }
    /**
     * đây là phương thức thực hiện tính tổng số trang sản phẩm theo loại sản phẩm chưa xóa do có thể có trang sử dụng
     * @param productTypeId
     * @return
     */
    public int getTotalPagesByType(int productTypeId) {
        int total = productDao.countProductByType(productTypeId);
        return (int) Math.ceil((double) total / pageSize);
    }

    /**
     * đây là phương thức thực hiện tính số sao để hiên thị lên productCard
     * @param p
     */
    public void fillRating(ProductCard p) {
        double avg = productDao.getAverageRatingByProduct(p.getId());
        int full = (int) avg;
        boolean half = (avg - full) > 0;
        p.setFullStars(full);
        p.setHasHalfStar(half);
    }

    /**
     * đây là phương thức thực hiện láy danh sách sản phẩm dưới dạng productCard theo loại sản phẩm và bộ lọc
     * chưa thực hiện bỏ do có thể có trang sử dụng
     * @param productTypeId
     * @param page
     * @param color
     * @param gender
     * @param brandId
     * @param size
     * @return
     */
    public List<ProductCard> getProductCardsByTypeAndFilter(int productTypeId, int page, String color, String gender, Integer brandId, Integer size) {
        int offset = (page - 1) * pageSize;
        return productDao.getProductCardByTypeAndFilter(productTypeId, pageSize, offset, color, gender, brandId, size);
    }

    /**
     * đây là phương thức thực hiện tính tổng số trang sản phẩm theo loại sản phẩm và bộ lọc
     * chưa thực hiện bỏ do có thể có trang sử dụng
     * @param productTypeId
     * @param color
     * @param gender
     * @param brandId
     * @param size
     * @return
     */
    public int getTotalPagesByTypeAndFilter(int productTypeId, String color, String gender, Integer brandId, Integer size) {
        int total = productDao.countProductByTypeAndFilter(productTypeId, color, gender, brandId, size);
        return (int) Math.ceil((double) total / pageSize);
    }

    /**
     * đây là phương thức thực hiện láy danh sách sản phẩm dưới dạng productCard theo loại sản phẩm và bộ lọc và tìm kiếm
     * đặc biệt phương thức có thể sử dụng lấy tất cả sản phẩm nếu chưa chọn danh mục phục vụ cho trang tất cả sản phẩm
     * dùng cho trang tất cả sản phẩm
     * @param productTypeId
     * @param page
     * @param keyword
     * @param color
     * @param gender
     * @param brandId
     * @param collectionId
     * @param size
     * @return
     */
    public List<ProductCard> getAllProductCardForListProductAndFilterAndSearch(
            Integer productTypeId,
            int page,
            String keyword,
            String color,
            String gender,
            Integer brandId,
            Integer collectionId,
            Integer size
    ) {
        int offset = (page - 1) * pageSize;

        List<ProductCard> cards = productDao.getProductCardsByFullFilter(
                productTypeId,
                pageSize,
                offset,
                keyword,
                color,
                gender,
                brandId,
                collectionId,
                size
        );

        for (ProductCard card : cards) {
            applyDiscount(card);
        }

        return cards;
    }

    /**
     * đây là phương thức thực hiện tính tổng số trang sản phẩm theo loại sản phẩm và bộ lọc và tìm kiếm
     * đặc biệt phương thức có thể sử dụng lấy tất cả sản phẩm nếu chưa chọn danh mục phục vụ cho trang tất cả sản phẩm
     * dùng cho trang tất cả sản phẩm
     * @param productTypeId
     * @param keyword
     * @param color
     * @param gender
     * @param brandId
     * @param collectionId
     * @param size
     * @return
     */
    public int getTotalPagesForListProductFilterAndSearch(
            Integer productTypeId,
            String keyword,
            String color,
            String gender,
            Integer brandId,
            Integer collectionId,
            Integer size
    ) {
        int total = productDao.countProductByFullFilter(
                productTypeId,
                keyword,
                color,
                gender,
                brandId,
                collectionId,
                size
        );

        return (int) Math.ceil((double) total / pageSize);
    }

    /**
     * đây là phương thức thực hiện láy danh sách sản phẩm mới dưới dạng productCard theo loại sản phẩm và bộ lọc và tìm kiếm
     * đặc biệt phương thức có thể sử dụng lấy tất cả sản phẩm nếu chưa chọn danh mục phục vụ cho san phẩm mới
     * dùng cho trang sản phẩm mới
     * @param productTypeId
     * @param page
     * @param keyword
     * @param color
     * @param gender
     * @param brandId
     * @param collectionId
     * @param size
     * @return
     */
    public List<ProductCard> getAllNewProductCardForListProductAndFilterAndSearch(
            Integer productTypeId,
            int page,
            String keyword,
            String color,
            String gender,
            Integer brandId,
            Integer collectionId,
            Integer size
    ) {
        int offset = (page - 1) * pageSize;

        List<ProductCard> cards = productDao.getNewProductCardsByFullFilter(
                productTypeId,
                pageSize,
                offset,
                keyword,
                color,
                gender,
                brandId,
                collectionId,
                size
        );

        for (ProductCard card : cards) {
            applyDiscount(card);
        }

        return cards;
    }

    /**
     * đây là phương thức thực hiện tính tổng số trang sản phẩm mới theo loại sản phẩm và bộ lọc và tìm kiếm
     * đặc biệt phương thức có thể sử dụng lấy tất cả sản phẩm nếu chưa chọn danh mục phục vụ cho san phẩm mới
     * dùng cho trang sản phẩm mới
     * @param productTypeId
     * @param keyword
     * @param color
     * @param gender
     * @param brandId
     * @param collectionId
     * @param size
     * @return
     */
    public int getTotalPagesForListNewProductFilterAndSearch(
            Integer productTypeId,
            String keyword,
            String color,
            String gender,
            Integer brandId,
            Integer collectionId,
            Integer size
    ) {
        int total = productDao.countNewProductByFullFilter(
                productTypeId,
                keyword,
                color,
                gender,
                brandId,
                collectionId,
                size
        );

        return (int) Math.ceil((double) total / pageSize);
    }

    /**
     * đây là phương thức thực hiện láy danh sách sản phẩm giảm giá dưới dạng productCard theo loại sản phẩm và bộ lọc và tìm kiếm
     * đặc biệt phương thức có thể sử dụng lấy tất cả sản phẩm nếu chưa chọn danh
     * mục phục vụ cho san phẩm giảm giá
     * dùng cho trang sản phẩm giảm giá
     * @param productTypeId
     * @param page
     * @param keyword
     * @param color
     * @param gender
     * @param brandId
     * @param collectionId
     * @param size
     * @return
     */
    public List<ProductCard> getAllHotDealProductCardForListProductAndFilterAndSearch(
            Integer productTypeId,
            int page,
            String keyword,
            String color,
            String gender,
            Integer brandId,
            Integer collectionId,
            Integer size,
            Integer hotDealId
    ) {
        int offset = (page - 1) * pageSize;

        List<ProductCard> cards = productDao.getHotDealProductCards(
                productTypeId,
                pageSize,
                offset,
                keyword,
                color,
                gender,
                brandId,
                collectionId,
                size,
                hotDealId
        );

        for (ProductCard card : cards) {
            applyDiscount(card);
        }
        return cards;
    }


    /**
     * đây là phương thức thực hiện tính tổng số trang sản phẩm giảm giá theo loại sản phẩm và bộ lọc và tìm kiếm
     * đặc biệt phương thức có thể sử dụng lấy tất cả sản phẩm nếu chưa chọn danh
     * mục phục vụ phân trang cho san phẩm giảm giá
     * dùng cho trang sản phẩm giảm giá
     * @param productTypeId
     * @param keyword
     * @param color
     * @param gender
     * @param brandId
     * @param collectionId
     * @param size
     * @return
     */

    public int getTotalPagesForListHotDealProductFilterAndSearch(
            Integer productTypeId,
            String keyword,
            String color,
            String gender,
            Integer brandId,
            Integer collectionId,
            Integer size,
            Integer hotDealId
    ) {
        int total = productDao.countHotDealProducts(
                productTypeId,
                keyword,
                color,
                gender,
                brandId,
                collectionId,
                size,
                hotDealId
        );

        return (int) Math.ceil((double) total / pageSize);
    }


    /**
     * đây là phương thức thực hiện láy danh sách sản phẩm giảm giá nhiều nhất để hiển thị trên trang chủ
     * @return
     */
    public List<ProductCard> getTopDiscountProductsForHome() {
        List<ProductCard> cards =
                productDao.getTopDiscountProductCardsForHome();
        for (ProductCard card : cards) {
            applyDiscount(card);
        }
        return cards;
    }

    /**
     * đây là phương thức thực hiện láy danh sách sản phẩm mới nhất để hiển thị trên trang chủ
     * @return
     */
    public List<ProductCard> getNewestProductsForHome() {
        List<ProductCard> cards =
                productDao.getNewestProductCardsForHome();
        for (ProductCard card : cards) {
            applyDiscount(card);
        }
        return cards;
    }






}

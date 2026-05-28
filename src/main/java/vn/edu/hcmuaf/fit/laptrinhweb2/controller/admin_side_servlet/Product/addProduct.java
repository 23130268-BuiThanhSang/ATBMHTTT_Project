package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductTypeDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Product;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductVariant;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductVariantImage;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductGroupService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "addProduct", value = "/addProduct")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 50 * 1024 * 1024
)
public class addProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setCharacterEncoding("UTF-8");

        //raw product
        Product product = new Product();

        //base info
        product.setName(request.getParameter("product_name"));
        product.setProductInfor(request.getParameter("product_info"));
        product.setProductCareInstruction(request.getParameter("product_care_instruction"));
        product.setProductReturnInfor(request.getParameter("product_return_info"));

        //product type
        String category = request.getParameter("category");
        ProductTypeDao  productTypeDao = new ProductTypeDao();
        product.setProductType(productTypeDao.getById(Integer.parseInt(category)));

        //brand sport collection
        String brandId = request.getParameter("brand_id");
        String sportId = request.getParameter("sport_ids");
        String collectionId = request.getParameter("collection_ids");
        ProductGroupService productGroupService = new ProductGroupService();
        if (brandId != null) {
            product.setBrand(productGroupService.getGroup(GroupType.BRAND,Integer.parseInt(brandId)));
        }
        if (sportId != null) {
            product.setSport(productGroupService.getGroup(GroupType.SPORT,Integer.parseInt(sportId)));
        }
        if (collectionId != null) {
            product.setCollection(productGroupService.getGroup(GroupType.COLLECTION,Integer.parseInt(collectionId)));
        }

        //gender
        product.setProductGender(request.getParameter("genders"));


        //variant
        ImageService  imageService = new ImageService();
        List<ProductVariant> variants = new ArrayList<>();

        int index = 0;
        while (true) {

            String color = request.getParameter("variants[" + index + "].color");
            if (color == null) break; // no more variants

            ProductVariant variant = new ProductVariant();
            variant.setColor(color);

            String raw_size = request.getParameter("variants[" + index + "].size");
            int real_size = -1;
            switch (category) {
                case "3":
                    if (raw_size.equals("S")) {real_size = 1;}
                    if (raw_size.equals("M")) {real_size = 2;}
                    if (raw_size.equals("L")) {real_size = 3;}
                    if (raw_size.equals("XL")) {real_size = 4;}
                    if (raw_size.equals("XXL")) {real_size = 5;}
                    break;
                case "4":
                    real_size = Integer.parseInt(raw_size);
                    break;
                case "2":
                    real_size = -1;
                    break;
                case "1":
                    if (raw_size.equals("Nhỏ")) {real_size = 100;}
                    if (raw_size.equals("Vừa")) {real_size = 200;}
                    if (raw_size.equals("Lớn")) {real_size = 300;}
                    break;
            }
            variant.setSize(real_size);
            variant.setPrice(Double.parseDouble(
                    request.getParameter("variants[" + index + "].price")
            ));
            variant.setStock(Integer.parseInt(
                    request.getParameter("variants[" + index + "].stock")
            ));

            // images
            List<ProductVariantImage> images = new ArrayList<>();

            for (Part part : request.getParts()) {
                if (part.getName().equals("variants[" + index + "].images")
                        && part.getSize() > 0) {

                    // upload file & get url
                    String imageUrl = imageService.uploadImage(null, part);

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        ProductVariantImage img = new ProductVariantImage();
                        img.setImageUrl(imageUrl);
                        images.add(img);
                    }
                }
            }

            variant.setImages(images);


            variant.setImages(images);
            variants.add(variant);

            index++;
        }
        product.setVariants(variants);
        ProductService productService = new ProductService();
        boolean suckseed = productService.addProduct(product);
        if (suckseed) {
            System.out.println("all done");
        } else {
            System.out.println("wrong somewhere, database probably on fire now");
        }
        response.sendRedirect("page_add_product");
    }
}
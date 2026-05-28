package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductTypeDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductVariantDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductVariantImageDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.GroupType;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductGroupService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "updateProduct", value = "/updateProduct")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class updateProduct extends HttpServlet {
    ProductGroupService productGroupService = new ProductGroupService();
    ProductService productService = new ProductService();
    ImageService imageService = new ImageService();
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

        try {
            // base info
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = new Product();
            product.setId(id);
            product.setName(request.getParameter("product_name"));
            product.setProductInfor(request.getParameter("product_info"));
            product.setProductCareInstruction(request.getParameter("product_care_instruction"));
            product.setProductReturnInfor(request.getParameter("product_return_info"));
            product.setProductGender(request.getParameter("genders"));

            String categoryId = request.getParameter("category");
            product.setProductType(productService.getProductType(Integer.parseInt(categoryId)));

            String brandId = request.getParameter("brand_id");
            String collectionId = request.getParameter("collection_ids");

            if (brandId != null) product.setBrand(productGroupService.getGroup(GroupType.BRAND, Integer.parseInt(brandId)));
            if (collectionId != null) product.setCollection(productGroupService.getGroup(GroupType.COLLECTION, Integer.parseInt(collectionId)));

            // Update Base Product Data
            productService.updateProduct(product);

            // delete image
            String delImgIds = request.getParameter("deletedImageIds");
            if (delImgIds != null && !delImgIds.isEmpty()) {
                List<Integer> ids = Arrays.stream(delImgIds.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                productService.deleteVariantImage(ids);
            }

            // variant
            int index = 0;
            while (true) {
                String color = request.getParameter("variants[" + index + "].color");
                if (color == null) break;

                String variantIdStr = request.getParameter("variants[" + index + "].id");

                ProductVariant variant = new ProductVariant();
                variant.setProduct_id(id);
                variant.setColor(color);

                String raw_size = request.getParameter("variants[" + index + "].size");
                int real_size = -1;
                switch (categoryId) {
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

                variant.setPrice(Double.parseDouble(request.getParameter("variants[" + index + "].price")));
                variant.setStock(Integer.parseInt(request.getParameter("variants[" + index + "].stock")));

                if (variantIdStr != null && !variantIdStr.isEmpty()) {
                    int currentVariantId = Integer.parseInt(variantIdStr);
                    variant.setId(currentVariantId);

                    productService.updateVariant(variant);

                    for (Part part : request.getParts()) {
                        if (part.getName().equals("variants[" + index + "].newImages") && part.getSize() > 0) {
                            String imageUrl = imageService.uploadImage(null, part);
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                ProductVariantImage img = new ProductVariantImage();
                                img.setVariantId(currentVariantId);
                                img.setImageUrl(imageUrl);

                                productService.insertImage(img);
                            }
                        }
                    }

                } else {
                    List<ProductVariantImage> imagesList = new ArrayList<>();
                    for (Part part : request.getParts()) {
                        if (part.getName().equals("variants[" + index + "].images") && part.getSize() > 0) {
                            String imageUrl = imageService.uploadImage(null, part);
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                ProductVariantImage img = new ProductVariantImage();
                                img.setImageUrl(imageUrl);
                                imagesList.add(img);
                            }
                        }
                    }
                    variant.setImages(imagesList);
                    productService.insertVariant(variant);
                }
                index++;
            }

            response.sendRedirect("getProductDetail?id=" + id + "&msg=success");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("page_allProduct?error=UpdateFailed");
        }
    }
}
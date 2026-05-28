package vn.edu.hcmuaf.fit.laptrinhweb2.Cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Product;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.ProductVariant;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;

@WebServlet("/add-to-cart")
public class AddToCartController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
        }

        int variantId = Integer.parseInt(request.getParameter("variantId"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        String color = request.getParameter("color");

        int sizeId = Integer.parseInt(request.getParameter("size"));
        ProductService productService = new ProductService();
        String sizeText = productService.getSizeText(sizeId);

        double price = Double.parseDouble(request.getParameter("price"));
        String image = request.getParameter("selectedImage");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        CartItem item = new CartItem(
                productId,
                variantId,
                productName,
                color,
                sizeText,
                image,
                price,
                quantity
        );

        cart.addItem(item);
        session.setAttribute("cart", cart);

        response.getWriter().print("OK");
    }
}




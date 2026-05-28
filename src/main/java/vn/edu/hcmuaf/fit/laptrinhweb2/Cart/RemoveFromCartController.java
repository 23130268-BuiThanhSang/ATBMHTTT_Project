package vn.edu.hcmuaf.fit.laptrinhweb2.Cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/remove-from-cart")
public class RemoveFromCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            int variantId = Integer.parseInt(request.getParameter("variantId"));
            cart.remove(variantId);
        }

        response.sendRedirect("Cart.jsp");
    }
}

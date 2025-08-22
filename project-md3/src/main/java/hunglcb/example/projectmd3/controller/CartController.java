package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.dto.CartItemView;
import hunglcb.example.projectmd3.model.dto.CartSummary;
import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.service.CartService;
import hunglcb.example.projectmd3.service.ICartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    private ICartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute("user") : null;
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login?redirect=" +
                    java.net.URLEncoder.encode(request.getContextPath() + "/cart", "UTF-8"));
            return;
        }

        // set header info
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("isLoggedIn", true);
        request.setAttribute("isAdmin", currentUser.isAdmin());

        List<CartItemView> items = cartService.getCartItemViews(currentUser.getId());
        CartSummary summary = cartService.getCartSummary(currentUser.getId());
        request.setAttribute("items", items);
        request.setAttribute("summary", summary);
        request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute("user") : null;
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login?redirect=" +
                    java.net.URLEncoder.encode(request.getContextPath() + "/cart", "UTF-8"));
            return;
        }

        // set header info
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("isLoggedIn", true);
        request.setAttribute("isAdmin", currentUser.isAdmin());

        String action = request.getParameter("action");
        try {
            if ("update".equals(action)) {
                Integer cartItemId = Integer.valueOf(request.getParameter("cartItemId"));
                Integer quantity = Integer.valueOf(request.getParameter("quantity"));
                cartService.updateItemQuantity(cartItemId, quantity);
            } else if ("remove".equals(action)) {
                Integer cartItemId = Integer.valueOf(request.getParameter("cartItemId"));
                cartService.removeItem(cartItemId);
            } else if ("clear".equals(action)) {
                cartService.clear(currentUser.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }
}



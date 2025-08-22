package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.*;
import hunglcb.example.projectmd3.model.dto.CartItemView;
import hunglcb.example.projectmd3.model.dto.CartSummary;
import hunglcb.example.projectmd3.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckoutController", urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    private ICartService cartService;
    private IAddressService addressService;

    @Override
    public void init() throws ServletException {
        cartService = new CartService();
        addressService = new AddressService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute("user") : null;
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login?redirect=" +
                    java.net.URLEncoder.encode(request.getContextPath() + "/checkout", "UTF-8"));
            return;
        }

        request.setAttribute("currentUser", currentUser);
        request.setAttribute("isLoggedIn", true);
        request.setAttribute("isAdmin", currentUser.isAdmin());

        List<CartItemView> items = cartService.getCartItemViews(currentUser.getId());
        CartSummary summary = cartService.getCartSummary(currentUser.getId());
        List<UserAddress> addresses = addressService.getAddresses(currentUser.getId());
        UserAddress defaultAddress = addressService.getDefaultAddress(currentUser.getId());

        request.setAttribute("items", items);
        request.setAttribute("summary", summary);
        request.setAttribute("addresses", addresses);
        request.setAttribute("defaultAddress", defaultAddress);
        request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute("user") : null;
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login?redirect=" +
                    java.net.URLEncoder.encode(request.getContextPath() + "/checkout", "UTF-8"));
            return;
        }

        String action = request.getParameter("action");
        try {
            if ("add_address".equals(action)) {
                UserAddress addr = new UserAddress();
                addr.setAccountId(currentUser.getId());
                addr.setAddressLine(request.getParameter("addressLine"));
                addr.setCity(request.getParameter("city"));
                addr.setState(request.getParameter("state"));
                addr.setPostalCode(request.getParameter("postalCode"));
                addr.setCountry(request.getParameter("country"));
                boolean makeDefault = "on".equalsIgnoreCase(request.getParameter("makeDefault"));
                addr.setIsDefault(makeDefault);
                addressService.addAddress(addr, makeDefault);
            } else if ("set_default_address".equals(action)) {
                Integer addressId = Integer.valueOf(request.getParameter("addressId"));
                addressService.setDefault(currentUser.getId(), addressId);
            } else if ("unset_default_address".equals(action)) {
                addressService.unsetDefault(currentUser.getId());
            } else if ("delete_address".equals(action)) {
                Integer addressId = Integer.valueOf(request.getParameter("addressId"));
                addressService.delete(currentUser.getId(), addressId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/checkout");
    }
}



package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.service.*;
import hunglcb.example.projectmd3.service.IPaymentService;
import hunglcb.example.projectmd3.service.PaymentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;

@WebServlet(name = "PaymentController", urlPatterns = {"/payment/checkout", "/payment/vnpay-return"})
public class PaymentController extends HttpServlet {
    private IOrderService orderService;
    private IPaymentService paymentService;
    private ICartService cartService;
    private IAddressService addressService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        paymentService = new PaymentService();
        cartService = new CartService();
        addressService = new AddressService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String servletPath = request.getServletPath();
        if ("/payment/checkout".equals(servletPath)) {
            handleCheckout(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void handleCheckout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute("user") : null;
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login?redirect=" + URLEncoder.encode(request.getContextPath() + "/checkout", "UTF-8"));
            return;
        }

        String method = request.getParameter("method");
        Integer shippingAddressId = addressService.getDefaultAddress(currentUser.getId()) != null ? addressService.getDefaultAddress(currentUser.getId()).getId() : null;
        BigDecimal total = cartService.getCartSummary(currentUser.getId()).getSubtotal();

        if ("COD".equalsIgnoreCase(method)) {
            Integer orderId = orderService.placeOrderCOD(currentUser.getId(), shippingAddressId, total);
            String msg = orderId != null ? "Đặt hàng COD thành công! Mã đơn: " + orderId : "Không thể đặt hàng COD";
            response.sendRedirect(request.getContextPath() + "/?message=" + URLEncoder.encode(msg, "UTF-8"));
            return;
        }

        if ("VNPay".equalsIgnoreCase(method)) {
            // Simplified VNPay QR flow: create pending order, show QR for gateway URL
            Integer pmId = paymentService.findMethodIdByName("VNPay");
            Integer orderId = orderService.prepareOrderPendingPayment(currentUser.getId(), shippingAddressId, pmId, total);
            if (orderId == null) {
                response.sendRedirect(request.getContextPath() + "/checkout?message=" + URLEncoder.encode("Không thể khởi tạo thanh toán", "UTF-8"));
                return;
            }
            String returnUrl = request.getRequestURL().toString().replace("/payment/checkout", "/payment/vnpay-return");
            // In real integration, build VNPay signed URL; here we use return URL as placeholder
            String payUrl = request.getRequestURL().toString().replace("/payment/checkout", "")
                    + request.getContextPath() + "/payment/vnpay-return?orderId=" + orderId + "&vnp_ResponseCode=00";

            // Set header info for JSP header
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("isLoggedIn", true);
            request.setAttribute("isAdmin", currentUser.isAdmin());

            request.setAttribute("orderId", orderId);
            request.setAttribute("amount", total);
            request.setAttribute("paymentUrl", payUrl);
            try {
                request.getRequestDispatcher("/views/payment/vnpay.jsp").forward(request, response);
            } catch (ServletException e) {
                response.sendRedirect(payUrl);
            }
            return;
        }

        response.sendRedirect(request.getContextPath() + "/checkout");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/payment/checkout".equals(servletPath)) {
            // If user navigates back to /payment/checkout via GET, send them to /checkout
            response.sendRedirect(request.getContextPath() + "/checkout");
            return;
        }
        if ("/payment/vnpay-return".equals(servletPath)) {
            handleVnPayReturn(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void handleVnPayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("vnp_ResponseCode");
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/checkout?message=" + URLEncoder.encode("Thiếu mã đơn hàng", "UTF-8"));
            return;
        }
        Integer orderId = Integer.valueOf(orderIdStr);
        if ("00".equals(code)) {
            orderService.finalizePaidOrder(orderId);
            response.sendRedirect(request.getContextPath() + "/?message=" + URLEncoder.encode("Thanh toán VNPay thành công! Mã đơn: " + orderId, "UTF-8"));
        } else {
            response.sendRedirect(request.getContextPath() + "/checkout?message=" + URLEncoder.encode("Thanh toán VNPay thất bại", "UTF-8"));
        }
    }
}



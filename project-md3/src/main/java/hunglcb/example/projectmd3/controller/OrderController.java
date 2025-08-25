package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.Order;
import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.service.order.IOrderService;
import hunglcb.example.projectmd3.service.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderController", urlPatterns = {"/orders", "/order-detail", "/cancel-order"})
public class OrderController extends HttpServlet {
    private final IOrderService orderService;

    public OrderController() {
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getServletPath();
        
        // Check if user is logged in
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            switch (action) {
                case "/orders":
                    showUserOrders(request, response, user);
                    break;
                case "/order-detail":
                    showOrderDetail(request, response, user);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/orders");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getServletPath();
        
        // Check if user is logged in
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            switch (action) {
                case "/cancel-order":
                    cancelOrder(request, response, user);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/orders");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void showUserOrders(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        List<Order> orders = orderService.getUserOrders(user.getId());
        
        request.setAttribute("orders", orders);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/views/orders.jsp").forward(request, response);
    }

    private void showOrderDetail(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        String orderIdParam = request.getParameter("id");
        
        if (orderIdParam == null || orderIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/orders");
            return;
        }

        try {
            Integer orderId = Integer.parseInt(orderIdParam);
            Order order = orderService.getOrderDetails(orderId);
            
            if (order == null) {
                request.setAttribute("error", "Không tìm thấy đơn hàng");
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                return;
            }
            
            // Check if order belongs to current user
            if (!order.getAccountId().equals(user.getId())) {
                request.setAttribute("error", "Bạn không có quyền xem đơn hàng này");
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("order", order);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/views/order-detail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID đơn hàng không hợp lệ");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        String orderIdParam = request.getParameter("orderId");
        
        if (orderIdParam == null || orderIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/orders");
            return;
        }

        try {
            Integer orderId = Integer.parseInt(orderIdParam);
            boolean success = orderService.cancelOrder(orderId, user.getId());
            
            if (success) {
                request.setAttribute("message", "Đã hủy đơn hàng thành công");
            } else {
                request.setAttribute("error", "Không thể hủy đơn hàng. Vui lòng kiểm tra lại trạng thái đơn hàng.");
            }
            
            // Redirect back to order detail
            response.sendRedirect(request.getContextPath() + "/order-detail?id=" + orderId);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID đơn hàng không hợp lệ");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}

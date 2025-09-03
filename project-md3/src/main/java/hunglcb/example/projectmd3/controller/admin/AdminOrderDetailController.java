package hunglcb.example.projectmd3.controller.admin;

import hunglcb.example.projectmd3.dto.CustomerOrderDTO;
import hunglcb.example.projectmd3.service.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AdminOrderDetailController", urlPatterns = {"/admin/order/detail"})
public class AdminOrderDetailController extends HttpServlet {

    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            req.setAttribute("errorMessage", "Không tìm thấy ID đơn hàng!");
            req.getRequestDispatcher("/views/admin/order-list.jsp").forward(req, resp);
            return;
        }

        try {
            Long orderId = Long.parseLong(idParam);
            CustomerOrderDTO order = orderService.getOrderById(orderId);

            if (order == null) {
                req.setAttribute("errorMessage", "Đơn hàng không tồn tại!");
                req.getRequestDispatcher("/views/admin/order-list.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("order", order);
            req.getRequestDispatcher("/views/admin/order-detail.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "ID đơn hàng không hợp lệ!");
            req.getRequestDispatcher("/views/admin/order-list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("orderId");
        String status = req.getParameter("status");

        try {
            Long orderId = Long.parseLong(idParam);
            orderService.updateOrderStatus(orderId, status);

            // Redirect về trang chi tiết để thấy trạng thái đã thay đổi
            resp.sendRedirect(req.getContextPath() + "/admin/order/detail?id=" + orderId);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "ID đơn hàng không hợp lệ!");
            req.getRequestDispatcher("/views/admin/order-detail.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/views/admin/order-detail.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("errorMessage", "Lỗi khi cập nhật trạng thái đơn hàng!");
            req.getRequestDispatcher("/views/admin/order-detail.jsp").forward(req, resp);
        }
    }

}

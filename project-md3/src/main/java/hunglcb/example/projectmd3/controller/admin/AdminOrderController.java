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
import java.util.List;

@WebServlet(name = "AdminOrderController", urlPatterns = {"/admin/order"})
public class AdminOrderController extends HttpServlet {

    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<CustomerOrderDTO> orders = orderService.getAllOrders();
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/views/admin/order-list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

    }

}

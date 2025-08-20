package hunglcb.example.projectmd3.controller.admin;

import hunglcb.example.projectmd3.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set character encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Check authentication
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login?message=Vui lòng đăng nhập");
            return;
        }
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login?message=Vui lòng đăng nhập");
            return;
        }
        
        // Check admin role
        if (!currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/?message=Bạn không có quyền truy cập");
            return;
        }
        
        // Set user info for dashboard
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("isAdmin", true);
        
        // Set some mock stats for display
        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("totalUsers", 156);
        stats.put("totalProducts", 89);
        stats.put("totalOrders", 47);
        stats.put("totalMessages", 23);
        request.setAttribute("stats", stats);
        
        // Forward to dashboard
        request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
    }
}

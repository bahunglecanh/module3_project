package hunglcb.example.projectmd3.controller.admin;

import hunglcb.example.projectmd3.service.user.IUserService;
import hunglcb.example.projectmd3.service.user.UserService;
import hunglcb.example.projectmd3.service.product.IProductService;
import hunglcb.example.projectmd3.service.product.ProductService;
import hunglcb.example.projectmd3.service.category.ICategoryService;
import hunglcb.example.projectmd3.service.category.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admin/dashboard"})
public class DashboardController extends HttpServlet {

    private IUserService userService;
    private IProductService productService;
    private ICategoryService categoryService;
    
    @Override
    public void init() throws ServletException {
        userService = new UserService();
        productService = new ProductService();
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalUsers", userService.getTotalUsersCount());
            stats.put("totalProducts", productService.getTotalProductsCount());
            stats.put("totalCategories", categoryService.getAllCategories().size());
            stats.put("totalOrders", 0);
            
            request.setAttribute("stats", stats);
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalUsers", 0);
            stats.put("totalProducts", 0);
            stats.put("totalCategories", 0);
            stats.put("totalOrders", 0);
            request.setAttribute("stats", stats);
        }

        request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
    }
}


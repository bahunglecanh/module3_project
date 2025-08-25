package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.Category;
import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.service.product.IProductService;
import hunglcb.example.projectmd3.service.category.ICategoryService;
import hunglcb.example.projectmd3.service.product.ProductService;
import hunglcb.example.projectmd3.service.category.CategoryService;
import hunglcb.example.projectmd3.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeController", urlPatterns = {"", "/", "/home"})
public class HomeController extends HttpServlet {
    
    private IProductService productService;
    private ICategoryService categoryService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set character encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Get current user from session and refresh latest profile avatar if needed
        HttpSession session = request.getSession(false);
        User currentUser = null;
        if (session != null) {
            currentUser = (User) session.getAttribute("user");
            if (currentUser != null) {
                try {
                    // Refresh user to ensure latest avatar after profile update
                    UserService us = new UserService();
                    User refreshed = us.findById(currentUser.getId());
                    if (refreshed != null) {
                        currentUser = refreshed;
                        session.setAttribute("user", refreshed);
                    }
                } catch (Exception ignored) {}
            }
        }
        
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("isLoggedIn", currentUser != null);
        
        boolean isAdmin = currentUser != null && currentUser.isAdmin();
        request.setAttribute("isAdmin", isAdmin);
        
        String viewParam = request.getParameter("view");
        boolean viewHome = "home".equals(viewParam);
        
        // Redirect admin users to admin dashboard (unless they explicitly want to view home)
        if (currentUser != null && isAdmin && !viewHome) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // Get any message from URL parameters
        String message = request.getParameter("message");
        if (message != null && !message.trim().isEmpty()) {
            request.setAttribute("message", message);
        }
        
        // Load data from database
        try {
            // Load featured products (latest 8 products)
            List<Product> featuredProducts = productService.getFeaturedProducts(8);
            request.setAttribute("featuredProducts", featuredProducts);
            
            // Load all categories
            List<Category> categories = categoryService.getAllCategories();
            request.setAttribute("categories", categories);
            
            // Load latest products for showcase (using pagination)
            List<Product> latestProducts = productService.getFeaturedProducts(12);
            request.setAttribute("latestProducts", latestProducts);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Set empty lists if error occurs
            request.setAttribute("featuredProducts", List.of());
            request.setAttribute("categories", List.of());
            request.setAttribute("latestProducts", List.of());
        }
        
        // Forward to home page
        request.getRequestDispatcher("/views/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Redirect POST requests to GET
        response.sendRedirect(request.getContextPath() + "/");
    }
}

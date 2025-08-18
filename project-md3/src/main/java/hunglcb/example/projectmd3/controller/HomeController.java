package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.Category;
import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.repository.CategoryRepository;
import hunglcb.example.projectmd3.repository.ICategoryRepository;
import hunglcb.example.projectmd3.repository.IProductRepository;
import hunglcb.example.projectmd3.repository.ProductRepository;

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
    
    private IProductRepository productRepository;
    private ICategoryRepository categoryRepository;

    @Override
    public void init() throws ServletException {
        productRepository = new ProductRepository();
        categoryRepository = new CategoryRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set character encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Get current user from session
        HttpSession session = request.getSession(false);
        User currentUser = null;
        if (session != null) {
            currentUser = (User) session.getAttribute("user");
        }
        
        // Set user info for the view
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("isLoggedIn", currentUser != null);
        
        // Check if user is admin
        boolean isAdmin = currentUser != null && currentUser.isAdmin();
        request.setAttribute("isAdmin", isAdmin);
        
        // Get any message from URL parameters
        String message = request.getParameter("message");
        if (message != null && !message.trim().isEmpty()) {
            request.setAttribute("message", message);
        }
        
        // Load data from database
        try {
            // Load featured products (latest 8 products)
            List<Product> featuredProducts = productRepository.findFeaturedProducts(8);
            request.setAttribute("featuredProducts", featuredProducts);
            
            // Load all categories
            List<Category> categories = categoryRepository.findAll();
            request.setAttribute("categories", categories);
            
            // Load latest products for showcase
            List<Product> latestProducts = productRepository.findLatestProducts(12);
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

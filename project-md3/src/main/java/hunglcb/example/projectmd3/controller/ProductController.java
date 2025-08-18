package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.Category;
import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.repository.CategoryRepository;
import hunglcb.example.projectmd3.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductController", urlPatterns = {"/products"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set character encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Create repositories
        ProductRepository productRepository = new ProductRepository();
        CategoryRepository categoryRepository = new CategoryRepository();
        
        try {
            // Load ALL products - simplest possible way
            List<Product> products = productRepository.findAll();
            System.out.println("DEBUG: Loaded " + products.size() + " products");
            
            // Load ALL categories
            List<Category> categories = categoryRepository.findAll();
            System.out.println("DEBUG: Loaded " + categories.size() + " categories");
            
            // Set attributes for JSP
            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.setAttribute("totalProducts", products.size());
            
            // Print first product for debug
            if (!products.isEmpty()) {
                Product firstProduct = products.get(0);
                System.out.println("DEBUG: First product - ID: " + firstProduct.getId() + ", Name: " + firstProduct.getName());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: " + e.getMessage());
            
            // Set empty lists
            request.setAttribute("products", List.of());
            request.setAttribute("categories", List.of());
            request.setAttribute("totalProducts", 0);
        }
        
        // Forward to JSP
        request.getRequestDispatcher("/views/products.jsp").forward(request, response);
    }
}
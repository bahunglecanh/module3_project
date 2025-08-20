package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.Category;
import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.service.IProductService;
import hunglcb.example.projectmd3.service.ICategoryService;
import hunglcb.example.projectmd3.service.ProductService;
import hunglcb.example.projectmd3.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductController", urlPatterns = {"/products"})
public class ProductController extends HttpServlet {
    
    private static final int PRODUCTS_PER_PAGE = 9; // 9 sản phẩm mỗi trang
    
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
        
        try {
            // Lấy parameters
            String categoryParam = request.getParameter("category");
            String searchParam = request.getParameter("search");
            int page = getPageParameter(request);
            
            List<Product> products;
            int totalProducts;
            
            // Tìm kiếm theo tên sản phẩm
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                String searchQuery = searchParam.trim();
                System.out.println("=== CONTROLLER DEBUG ===");
                System.out.println("Received search parameter: '" + searchParam + "'");
                System.out.println("Trimmed search query: '" + searchQuery + "'");
                System.out.println("Page: " + page + ", Size: " + PRODUCTS_PER_PAGE);
                
                products = productService.searchProductsByName(searchQuery, page - 1, PRODUCTS_PER_PAGE);
                totalProducts = productService.getProductsCountByName(searchQuery);
                
                System.out.println("Service returned " + products.size() + " products");
                System.out.println("Total count: " + totalProducts);
                System.out.println("=== END CONTROLLER DEBUG ===");
                
                request.setAttribute("searchQuery", searchQuery);
            }
            // Lọc theo category
            else if (categoryParam != null && !categoryParam.trim().isEmpty()) {
                try {
                    Integer categoryId = Integer.valueOf(categoryParam);
                    // Lấy sản phẩm theo category với phân trang
                    products = productService.getProductsByCategoryWithPagination(categoryId, page - 1, PRODUCTS_PER_PAGE);
                    totalProducts = productService.getProductsCountByCategory(categoryId);
                } catch (NumberFormatException e) {
                    // Category ID không hợp lệ
                    products = productService.getProductsWithPagination(page - 1, PRODUCTS_PER_PAGE);
                    totalProducts = productService.getTotalProductsCount();
                }
            } else {
                // Lấy tất cả sản phẩm với phân trang
                products = productService.getProductsWithPagination(page - 1, PRODUCTS_PER_PAGE);
                totalProducts = productService.getTotalProductsCount();
            }
            
            // Tính toán thông tin phân trang
            int totalPages = (int) Math.ceil((double) totalProducts / PRODUCTS_PER_PAGE);
            boolean hasNextPage = page < totalPages;
            boolean hasPrevPage = page > 1;
            
            // Load categories cho sidebar
            List<Category> categories = categoryService.getAllCategories();
            
            // Set attributes for JSP
            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("selectedCategory", categoryParam);
            
            // Pagination attributes
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("hasNextPage", hasNextPage);
            request.setAttribute("hasPrevPage", hasPrevPage);
            request.setAttribute("productsPerPage", PRODUCTS_PER_PAGE);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Set empty lists
            request.setAttribute("products", List.of());
            request.setAttribute("categories", List.of());
            request.setAttribute("totalProducts", 0);
            request.setAttribute("currentPage", 1);
            request.setAttribute("totalPages", 0);
            request.setAttribute("hasNextPage", false);
            request.setAttribute("hasPrevPage", false);
        }
        
        // Forward to JSP
        request.getRequestDispatcher("/views/products.jsp").forward(request, response);
    }
    
    private int getPageParameter(HttpServletRequest request) {
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                int page = Integer.parseInt(pageParam);
                return Math.max(1, page); // Đảm bảo page >= 1
            } catch (NumberFormatException e) {
                return 1;
            }
        }
        return 1;
    }
}
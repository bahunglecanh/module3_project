package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.Product;
import hunglcb.example.projectmd3.model.ProductSize;
import hunglcb.example.projectmd3.model.Category;
import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.service.IProductService;
import hunglcb.example.projectmd3.service.ICategoryService;
import hunglcb.example.projectmd3.service.ProductService;
import hunglcb.example.projectmd3.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;

@WebServlet(name = "ProductDetailController", urlPatterns = {"/detail/*"})
public class ProductDetailController extends HttpServlet {

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
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Lấy product ID từ URL path
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }
        
        try {
            // Parse product ID từ URL như /product/1
            String productIdStr = pathInfo.substring(1); // Bỏ dấu / đầu
            Integer productId = Integer.valueOf(productIdStr);
            
            // Lấy thông tin sản phẩm
            Product product = productService.getProductDetail(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sản phẩm không tồn tại");
                return;
            }
            
            // Lấy sizes thực từ database
            List<ProductSize> productSizes = productService.getProductSizes(productId);
            List<ProductSize> availableSizes = productService.getAvailableProductSizes(productId);
            

            
            // Lấy sản phẩm liên quan (cùng category)
            List<Product> relatedProducts = null;
            if (product.getCategoryId() != null) {
                relatedProducts = productService.getProductsByCategoryWithPagination(
                    product.getCategoryId(), 0, 4); // Lấy 4 sản phẩm liên quan
            }
            
            // Lấy user hiện tại
            HttpSession session = request.getSession(false);
            User currentUser = null;
            if (session != null) {
                currentUser = (User) session.getAttribute("user");
            }
            
            // Tính tổng stock từ tất cả sizes
            Integer totalStock = productService.getTotalStock(productId);
            
            // Set attributes
            request.setAttribute("product", product);
            request.setAttribute("productSizes", productSizes);
            request.setAttribute("availableSizes", availableSizes);
            request.setAttribute("totalStock", totalStock);
            request.setAttribute("relatedProducts", relatedProducts);
            request.setAttribute("currentUser", currentUser);
            request.setAttribute("isLoggedIn", currentUser != null);
            
            // Forward to detail page
            request.getRequestDispatcher("/views/product-detail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm không hợp lệ");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Xử lý thêm vào giỏ hàng
        String action = request.getParameter("action");
        
        if ("add_to_cart".equals(action)) {
            handleAddToCart(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action không hợp lệ");
        }
    }
    
    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Lấy thông tin từ form
            Integer productId = Integer.valueOf(request.getParameter("productId"));
            String selectedSize = request.getParameter("size");
            Integer quantity = Integer.valueOf(request.getParameter("quantity"));
            
            // Kiểm tra user đã login chưa
            HttpSession session = request.getSession(false);
            User currentUser = null;
            if (session != null) {
                currentUser = (User) session.getAttribute("user");
            }
            
            if (currentUser == null) {
                // Chưa login -> redirect to login
                String returnUrl = request.getContextPath() + "/product/" + productId;
                response.sendRedirect(request.getContextPath() + "/auth/login?redirect=" + 
                    java.net.URLEncoder.encode(returnUrl, "UTF-8"));
                return;
            }
            
            // Kiểm tra sản phẩm tồn tại
            Product product = productService.getProductDetail(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sản phẩm không tồn tại");
                return;
            }
            
            // Kiểm tra size và stock
            if (!productService.isProductSizeAvailable(productId, selectedSize, quantity)) {
                request.setAttribute("errorMessage", "Size " + selectedSize + " không có sẵn hoặc không đủ hàng.");
                doGet(request, response);
                return;
            }
            
            // TODO: Thêm vào giỏ hàng (sẽ implement sau)
            // Hiện tại chỉ hiển thị thông báo thành công
            
            // Thông báo thành công
            String successMessage = String.format("Đã thêm %s (Size %s, SL: %d) vào giỏ hàng!", 
                product.getName(), selectedSize, quantity);
            
            // Redirect về trang detail với thông báo
            response.sendRedirect(request.getContextPath() + "/product/" + productId + 
                "?message=" + java.net.URLEncoder.encode(successMessage, "UTF-8"));
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu không hợp lệ");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống");
        }
    }
}

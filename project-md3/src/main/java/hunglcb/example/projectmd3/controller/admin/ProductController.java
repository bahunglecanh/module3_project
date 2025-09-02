package hunglcb.example.projectmd3.controller.admin;

import hunglcb.example.projectmd3.dto.ProductDTO;
import hunglcb.example.projectmd3.model.Category;
import hunglcb.example.projectmd3.service.product.IProductService;
import hunglcb.example.projectmd3.service.product.ProductService;
import hunglcb.example.projectmd3.service.category.ICategoryService;
import hunglcb.example.projectmd3.service.category.CategoryService;
import hunglcb.example.projectmd3.service.brand.IBrandService;
import hunglcb.example.projectmd3.service.brand.BrandService;
import hunglcb.example.projectmd3.model.Brand;
import hunglcb.example.projectmd3.model.Product;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminProductController", urlPatterns = {"/admin/products"})
public class ProductController extends HttpServlet {
    
    private IProductService productService;
    private ICategoryService categoryService;
    private IBrandService brandService;
    
    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();
        brandService = new BrandService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if (action == null) {
            showProductList(request, response);
        } else {
            switch (action) {
                case "add":
                    showAddProductForm(request, response);
                    break;
                case "edit":
                    showEditProductForm(request, response);
                    break;
                case "delete":
                    handleDeleteProduct(request, response);
                    break;
                default:
                    showProductList(request, response);
                    break;
            }
        }
    }
    
    private void showProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<ProductDTO> products = productService.findAllProducts();
            request.setAttribute("products", products);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("products", java.util.Collections.emptyList());
        }
        
        request.getRequestDispatcher("/views/admin/listproducts.jsp").forward(request, response);
    }
    
    private void showAddProductForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("categories", categoryService.getAllCategories());
            request.setAttribute("brands", brandService.getAllBrands());
            request.setAttribute("action", "add");
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/views/admin/product-form.jsp").forward(request, response);
    }
    
    private void showEditProductForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            Product product = productService.getProductDetail(Integer.parseInt(idStr));
            request.setAttribute("product", product);
            request.setAttribute("categories", categoryService.getAllCategories());
            request.setAttribute("brands", brandService.getAllBrands());
            request.setAttribute("action", "edit");
            request.getRequestDispatcher("/views/admin/product-form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set character encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Handle different actions (add, edit, delete product)
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
            return;
        }
        
        switch (action) {
            case "add":
                handleAddProduct(request, response);
                break;
            case "edit":
                handleEditProduct(request, response);
                break;
            case "delete":
                handleDeleteProduct(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/products");
                break;
        }
    }
    
    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String stockQuantityStr = request.getParameter("stockQuantity");
            String imageUrl = request.getParameter("imageUrl");
            String categoryIdStr = request.getParameter("categoryId");
            String brandIdStr = request.getParameter("brandId");
            
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(new BigDecimal(priceStr != null ? priceStr : "0"));
            product.setStockQuantity(Integer.parseInt(stockQuantityStr != null ? stockQuantityStr : "0"));
            product.setImageUrl(imageUrl);
            product.setCategoryId(categoryIdStr != null ? Integer.parseInt(categoryIdStr) : null);
            product.setBrandId(brandIdStr != null ? Integer.parseInt(brandIdStr) : null);
            product.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            
            productService.addProduct(product);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
    
    private void handleEditProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String stockQuantityStr = request.getParameter("stockQuantity");
            String imageUrl = request.getParameter("imageUrl");
            String categoryIdStr = request.getParameter("categoryId");
            String brandIdStr = request.getParameter("brandId");
            
            Product product = new Product();
            product.setId(Integer.parseInt(idStr));
            product.setName(name);
            product.setDescription(description);
            product.setPrice(new BigDecimal(priceStr != null ? priceStr : "0"));
            product.setStockQuantity(Integer.parseInt(stockQuantityStr != null ? stockQuantityStr : "0"));
            product.setImageUrl(imageUrl);
            product.setCategoryId(categoryIdStr != null ? Integer.parseInt(categoryIdStr) : null);
            product.setBrandId(brandIdStr != null ? Integer.parseInt(brandIdStr) : null);
            product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            
            productService.updateProduct(product);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
    
    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            productService.deleteProduct(Integer.parseInt(idStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}

package hunglcb.example.projectmd3.controller.admin;

import hunglcb.example.projectmd3.model.Category;
import hunglcb.example.projectmd3.service.category.ICategoryService;
import hunglcb.example.projectmd3.service.category.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminCategoryController", urlPatterns = {"/admin/categories"})
public class CategoryController extends HttpServlet {
    
    private ICategoryService categoryService;
    
    @Override
    public void init() throws ServletException {
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if (action == null) {
            showCategoryList(request, response);
        } else {
            switch (action) {
                case "add":
                    showAddCategoryForm(request, response);
                    break;
                case "edit":
                    showEditCategoryForm(request, response);
                    break;
                case "delete":
                    handleDeleteCategory(request, response);
                    break;
                default:
                    showCategoryList(request, response);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/categories");
            return;
        }
        
        switch (action) {
            case "add":
                handleAddCategory(request, response);
                break;
            case "edit":
                handleEditCategory(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/categories");
                break;
        }
    }
    
    private void showCategoryList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("categories", categoryService.getAllCategories());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("categories", java.util.Collections.emptyList());
        }
        request.getRequestDispatcher("/views/admin/listcategories.jsp").forward(request, response);
    }
    
    private void showAddCategoryForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("action", "add");
        request.getRequestDispatcher("/views/admin/category-form.jsp").forward(request, response);
    }
    
    private void showEditCategoryForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            Category category = categoryService.getCategoryById(Integer.parseInt(idStr));
            request.setAttribute("category", category);
            request.setAttribute("action", "edit");
            request.getRequestDispatcher("/views/admin/category-form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/categories");
        }
    }
    
    private void handleAddCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            categoryService.addCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }
    
    private void handleEditCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            
            Category category = new Category();
            category.setId(Integer.parseInt(idStr));
            category.setName(name);
            category.setDescription(description);
            categoryService.updateCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }
    
    private void handleDeleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            categoryService.deleteCategory(Integer.parseInt(idStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }
}

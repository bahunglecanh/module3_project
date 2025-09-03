package hunglcb.example.projectmd3.controller.admin;

import hunglcb.example.projectmd3.dto.UserDTO;
import hunglcb.example.projectmd3.service.user.IUserService;
import hunglcb.example.projectmd3.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "AdminUserController", urlPatterns = {"/admin/listuser"})
public class UserController extends HttpServlet {
    
    private IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                showUserList(request, response);
                break;
            case "search":
                searchUsers(request, response);
                break;
            default:
                showUserList(request, response);
                break;
        }
    }

    private void showUserList(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<UserDTO> users = userService.findAllUsers();
        request.setAttribute("users", users != null ? users : Collections.emptyList());
        request.setAttribute("totalRecords", users != null ? users.size() : 0);
        
        request.getRequestDispatcher("/views/admin/listuser.jsp").forward(request, response);
    }
    
    private void searchUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String searchTerm = request.getParameter("search");
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            showUserList(request, response);
            return;
        }
        
        List<UserDTO> searchResults = userService.searchByName(searchTerm.trim());
        request.setAttribute("users", searchResults != null ? searchResults : Collections.emptyList());
        request.setAttribute("totalRecords", searchResults != null ? searchResults.size() : 0);
        request.setAttribute("searchTerm", searchTerm.trim());
        
        request.getRequestDispatcher("/views/admin/listuser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/listuser");
            return;
        }
        
        switch (action) {
            case "ban":
                banUser(request, response);
                break;
            case "unban":
                unbanUser(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/listuser");
                break;
        }
    }
    
    private void banUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        handleUserStatusChange(request, response, true);
    }
    
    private void unbanUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        handleUserStatusChange(request, response, false);
    }
    
    private void handleUserStatusChange(HttpServletRequest request, HttpServletResponse response, boolean isBan) 
            throws IOException {
        try {
            String userIdStr = request.getParameter("userId");
            int userId = Integer.parseInt(userIdStr);
            
            if (isBan) {
                userService.banUser(userId);
            } else {
                userService.unbanUser(userId);
            }
            
        } catch (Exception e) {
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/listuser");
    }
}

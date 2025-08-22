package hunglcb.example.projectmd3.controller.admin;

import hunglcb.example.projectmd3.dto.UserDTO;
import hunglcb.example.projectmd3.service.IUserService;
import hunglcb.example.projectmd3.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "AdminUserController", urlPatterns = {"/admin/listuser"})
public class AdminUserController extends HttpServlet {
    
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
        try {
            List<UserDTO> users = userService.findAllUsers();
            
            if (users != null) {
                request.setAttribute("users", users);
                request.setAttribute("totalRecords", users.size());
            } else {
                request.setAttribute("errorMessage", "Lỗi khi lấy danh sách user!");
                request.setAttribute("users", Collections.emptyList());
                request.setAttribute("totalRecords", 0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống khi lấy danh sách user!");
            request.setAttribute("users", Collections.emptyList());
            request.setAttribute("totalRecords", 0);
        }
        
        request.getRequestDispatcher("/views/admin/listuser.jsp").forward(request, response);
    }
    
    private void searchUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String searchTerm = request.getParameter("search");
            
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                showUserList(request, response);
                return;
            }
            
            final String finalSearchTerm = searchTerm.trim().toLowerCase();
            
            // Lấy tất cả users từ service và filter
            List<UserDTO> allUsers = userService.findAllUsers();
            
            if (allUsers != null) {
                // Filter users theo search term
                List<UserDTO> filteredUsers = allUsers.stream()
                    .filter(user -> 
                        (user.getFullName() != null && user.getFullName().toLowerCase().contains(finalSearchTerm)) ||
                        (user.getEmail() != null && user.getEmail().toLowerCase().contains(finalSearchTerm))
                    )
                    .collect(Collectors.toList());
                
                // Set attributes
                request.setAttribute("users", filteredUsers);
                request.setAttribute("totalRecords", filteredUsers.size());
                request.setAttribute("searchTerm", finalSearchTerm);
                
                if (filteredUsers.size() == 0) {
                    request.setAttribute("infoMessage", "Không tìm thấy user nào với từ khóa: " + finalSearchTerm);
                }
                
            } else {
                request.setAttribute("errorMessage", "Lỗi khi tìm kiếm user!");
                request.setAttribute("users", Collections.emptyList());
                request.setAttribute("totalRecords", 0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống khi tìm kiếm user!");
            request.setAttribute("users", Collections.emptyList());
            request.setAttribute("totalRecords", 0);
        }
        
        request.getRequestDispatcher("/views/admin/listuser.jsp").forward(request, response);
    }
}

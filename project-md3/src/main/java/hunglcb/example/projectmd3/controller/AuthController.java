package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.service.IUserService;
import hunglcb.example.projectmd3.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthController", urlPatterns = {"/auth/*"})
public class AuthController extends HttpServlet {
    
    private IUserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getPathInfo();
        
        if (path == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        switch (path) {
            case "/login":
                showLoginPage(request, response);
                break;
            case "/register":
                showRegisterPage(request, response);
                break;
            case "/logout":
                handleLogout(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String path = request.getPathInfo();
        
        if (path == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        switch (path) {
            case "/login":
                handleLogin(request, response);
                break;
            case "/register":
                handleRegister(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    private void showLoginPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        // preserve redirect param
        String redirect = request.getParameter("redirect");
        if (redirect != null && !redirect.isEmpty()) {
            request.setAttribute("redirect", redirect);
        }
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }

    private void showRegisterPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String errorMessage = null;

        // Quick validation
        if (email == null || email.trim().isEmpty()) {
            errorMessage = "Vui lòng nhập email!";
        } else if (password == null || password.trim().isEmpty()) {
            errorMessage = "Vui lòng nhập mật khẩu!";
        } else {
            try {
                // Try login via service
                UserService.ServiceResult<User> user = userService.login(email, password);
                
                if (user != null) {
                    // Success
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    String redirect = request.getParameter("redirect");
                    if (redirect != null && !redirect.isEmpty()) {
                        response.sendRedirect(redirect);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/");
                    }
                    return;
                }
                errorMessage = "Email hoặc mật khẩu không đúng!";
            } catch (Exception e) {
                errorMessage = "Lỗi hệ thống!";
                e.printStackTrace();
            }
        }

        // Show error
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("email", email);
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String errorMessage = null;

        // Quick validation
        if (email == null || email.trim().isEmpty()) {
            errorMessage = "Vui lòng nhập email!";
        } else if (password == null || password.length() < 6) {
            errorMessage = "Mật khẩu phải có ít nhất 6 ký tự!";
        } else if (!password.equals(confirmPassword)) {
            errorMessage = "Xác nhận mật khẩu không khớp!";
        } else if (fullName == null || fullName.trim().isEmpty()) {
            errorMessage = "Vui lòng nhập họ tên!";
        } else {
            try {
                // Try register via service
                if (userService.emailExists(email.trim())) {
                    errorMessage = "Email đã được sử dụng!";
                } else {
                    // Create user object
                    User newUser = new User(email.trim(), password, fullName.trim());
                    if (userService.register(newUser).isSuccess()) {
                        // Success
                        request.setAttribute("successMessage", "Đăng ký thành công!");
                        request.setAttribute("showLoginLink", true);
                        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
                        return;
                    } else {
                        errorMessage = "Lỗi tạo tài khoản!";
                    }
                }
            } catch (Exception e) {
                errorMessage = "Lỗi hệ thống!";
                e.printStackTrace();
            }
        }

        // Show error
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("email", email);
        request.setAttribute("fullName", fullName);
        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        response.sendRedirect(request.getContextPath() + "/");
    }
}

package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.service.user.IPasswordResetService;
import hunglcb.example.projectmd3.service.user.IUserService;
import hunglcb.example.projectmd3.service.user.PasswordResetService;
import hunglcb.example.projectmd3.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ForgotPasswordController", urlPatterns = {"/auth/forgot-password/*"})
public class ForgotPasswordController extends HttpServlet {

    private IPasswordResetService passwordResetService;
    private IUserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        passwordResetService = new PasswordResetService(userService);
    }
    


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path == null || "/".equals(path)) {
            request.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(request, response);
            return;
        }
        switch (path) {
            case "/verify":
                request.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(request, response);
                return;
            case "/reset":
                request.getRequestDispatcher("/views/auth/reset-password.jsp").forward(request, response);
                return;
            default:
                response.sendRedirect(request.getContextPath() + "/auth/forgot-password");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getPathInfo();
        if (path == null || "/".equals(path)) {
            handleRequestOtp(request, response);
            return;
        }
        switch (path) {
            case "/request":
                handleRequestOtp(request, response);
                break;
            case "/verify":
                handleVerifyOtp(request, response);
                break;
            case "/reset":
                handleResetPassword(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/auth/forgot-password");
        }
    }

    private void handleRequestOtp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng nhập email đã đăng ký");
            request.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(request, response);
            return;
        }
        if (!userService.emailExists(email.trim())) {
            request.setAttribute("errorMessage", "Email không tồn tại trong hệ thống");
            request.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(request, response);
            return;
        }
        boolean ok = passwordResetService.requestOtp(email.trim());
        if (ok) {
            request.setAttribute("email", email.trim());
            request.setAttribute("message", "Đã gửi OTP đến email. Vui lòng kiểm tra hộp thư.");
            request.setAttribute("otpExpiryTime", passwordResetService.getOtpExpiryTime(email.trim()));
            request.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Không thể gửi OTP. Thử lại sau.");
            request.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(request, response);
        }
    }

    private void handleVerifyOtp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String otp = request.getParameter("otp");
        if (email == null || otp == null) {
            request.setAttribute("errorMessage", "Thiếu thông tin");
            request.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(request, response);
            return;
        }
        boolean verified = passwordResetService.verifyOtp(email.trim(), otp.trim());
        if (verified) {
            request.setAttribute("email", email.trim());
            request.getRequestDispatcher("/views/auth/reset-password.jsp").forward(request, response);
        } else {
            request.setAttribute("email", email.trim());
            request.setAttribute("errorMessage", "OTP không hợp lệ hoặc đã hết hạn");
            request.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(request, response);
        }
    }

    private void handleResetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");
        if (email == null || password == null || confirm == null) {
            request.setAttribute("errorMessage", "Thiếu thông tin");
            request.getRequestDispatcher("/views/auth/reset-password.jsp").forward(request, response);
            return;
        }
        if (!password.equals(confirm)) {
            request.setAttribute("email", email.trim());
            request.setAttribute("errorMessage", "Xác nhận mật khẩu không khớp");
            request.getRequestDispatcher("/views/auth/reset-password.jsp").forward(request, response);
            return;
        }
        if (password.length() < 6) {
            request.setAttribute("email", email.trim());
            request.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 6 ký tự");
            request.getRequestDispatcher("/views/auth/reset-password.jsp").forward(request, response);
            return;
        }
        boolean ok = passwordResetService.resetPassword(email.trim(), password);
        if (ok) {
            request.setAttribute("message", "Đổi mật khẩu thành công. Vui lòng đăng nhập lại.");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
        } else {
            request.setAttribute("email", email.trim());
            request.setAttribute("errorMessage", "Không thể đổi mật khẩu. Thử lại.");
            request.getRequestDispatcher("/views/auth/reset-password.jsp").forward(request, response);
        }
    }
}



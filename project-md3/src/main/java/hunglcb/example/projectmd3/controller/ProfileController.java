package hunglcb.example.projectmd3.controller;

import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.model.UserProfile;
import hunglcb.example.projectmd3.service.user.IUserService;
import hunglcb.example.projectmd3.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet(name = "ProfileController", urlPatterns = {"/profile", "/profile/", "/profile/edit", "/profile/update"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024,          // 5MB
        maxRequestSize = 10 * 1024 * 1024)      // 10MB
public class ProfileController extends HttpServlet {

    private IUserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute("user") : null;
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login?redirect=" + java.net.URLEncoder.encode(request.getRequestURI(), "UTF-8"));
            return;
        }

        // Refresh user from DB to get latest profile details
        User freshUser = userService.findById(currentUser.getId());
        if (freshUser == null) {
            freshUser = currentUser;
        }

        request.setAttribute("currentUser", freshUser);
        request.setAttribute("isLoggedIn", true);
        request.setAttribute("isAdmin", freshUser.isAdmin());

        String servletPath = request.getServletPath();
        if ("/profile/edit".equals(servletPath)) {
            request.getRequestDispatcher("/views/profile-edit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute("user") : null;
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login?redirect=" + java.net.URLEncoder.encode(request.getRequestURI(), "UTF-8"));
            return;
        }

        String servletPath = request.getServletPath();
        if ("/profile/update".equals(servletPath)) {
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String birthDateStr = request.getParameter("birthDate");
            String avatarUrl = null;

            // Handle avatar file upload if provided
            try {
                Part avatarPart = request.getPart("avatarFile");
                if (avatarPart != null && avatarPart.getSize() > 0) {
                    String fileName = extractFileName(avatarPart);
                    if (fileName != null && !fileName.isBlank()) {
                        fileName = System.currentTimeMillis() + "_" + fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
                        File baseUploadsDir = resolveUploadsBaseDir(request.getServletContext());
                        File uploadDir = new File(baseUploadsDir, "avatars");
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }
                        File dest = new File(uploadDir, fileName);
                        try (InputStream in = avatarPart.getInputStream()) {
                            Files.copy(in, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                        // Save relative path for serving
                        String rel = ("uploads/avatars/" + fileName).replace("\\", "/");
                        avatarUrl = rel;
                    }
                }
            } catch (Exception e) {
                // ignore upload error but do not block other updates
            }

            User updated = new User();
            updated.setId(currentUser.getId());
            updated.setEmail(currentUser.getEmail());
            updated.setFullName(fullName != null ? fullName.trim() : null);
            updated.setPhone(phone != null ? phone.trim() : null);
            if (gender != null) {
                if ("male".equalsIgnoreCase(gender)) {
                    updated.setGender(UserProfile.Gender.MALE);
                } else if ("female".equalsIgnoreCase(gender)) {
                    updated.setGender(UserProfile.Gender.FEMALE);
                } else if ("other".equalsIgnoreCase(gender)) {
                    updated.setGender(UserProfile.Gender.OTHER);
                }
            }
            if (birthDateStr != null && !birthDateStr.trim().isEmpty()) {
                try {
                    updated.setBirthDate(Date.valueOf(birthDateStr));
                } catch (IllegalArgumentException e) {
                    // ignore invalid date, keep null
                }
            }
            if (avatarUrl != null && !avatarUrl.trim().isEmpty()) {
                updated.setAvatarUrl(avatarUrl.trim());
            }

            boolean ok = userService.updateUser(updated);
            if (ok) {
                // Refresh session user
                User fresh = userService.findById(currentUser.getId());
                if (fresh != null && session != null) {
                    session.setAttribute("user", fresh);
                }
                response.sendRedirect(request.getContextPath() + "/profile?success=1");
            } else {
                request.setAttribute("errorMessage", "Cập nhật thông tin thất bại, vui lòng thử lại!");
                request.setAttribute("currentUser", currentUser);
                request.setAttribute("isLoggedIn", true);
                request.setAttribute("isAdmin", currentUser.isAdmin());
                request.getRequestDispatcher("/views/profile-edit.jsp").forward(request, response);
            }
            return;
        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        if (contentDisp == null) return null;
        for (String cd : contentDisp.split(";")) {
            String trimmed = cd.trim();
            if (trimmed.startsWith("filename")) {
                String fileName = trimmed.substring(trimmed.indexOf('=') + 1).trim().replace("\"", "");
                // In some browsers full path is sent
                if (fileName.contains("/")) fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
                if (fileName.contains("\\")) fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);
                return fileName;
            }
        }
        return null;
    }

    private File resolveUploadsBaseDir(ServletContext ctx) {
        String configured = ctx.getInitParameter("uploadBaseDir");
        File base;
        if (configured != null && !configured.trim().isEmpty()) {
            base = new File(configured.trim());
        } else {
            String catalinaBase = System.getProperty("catalina.base");
            if (catalinaBase != null && !catalinaBase.isEmpty()) {
                base = new File(catalinaBase, "uploads");
            } else {
                base = new File(System.getProperty("user.home"), "uploads");
            }
        }
        if (!base.exists()) {
            base.mkdirs();
        }
        return base;
    }
}



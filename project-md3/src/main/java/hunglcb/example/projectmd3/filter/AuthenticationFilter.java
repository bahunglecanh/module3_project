package hunglcb.example.projectmd3.filter;

import hunglcb.example.projectmd3.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/admin/*", "/profile/*", "/orders/*", "/cart/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        
        // Get the path relative to context
        String path = requestURI.substring(contextPath.length());
        
        // Check if user is logged in
        HttpSession session = httpRequest.getSession(false);
        User currentUser = null;
        if (session != null) {
            currentUser = (User) session.getAttribute("user");
        }
        
        // If user is not logged in, redirect to login page
        if (currentUser == null) {
            String loginURL = contextPath + "/auth/login?redirect=" + java.net.URLEncoder.encode(requestURI, "UTF-8");
            httpResponse.sendRedirect(loginURL);
            return;
        }
        
        // Check admin access
        if (path.startsWith("/admin/")) {
            if (!currentUser.isAdmin()) {
                // Non-admin trying to access admin area
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }
        }
        
        // User is authenticated and authorized, continue with the request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}

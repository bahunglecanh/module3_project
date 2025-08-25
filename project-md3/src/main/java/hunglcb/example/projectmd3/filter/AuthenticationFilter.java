package hunglcb.example.projectmd3.filter;

import hunglcb.example.projectmd3.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());
        
        User currentUser = getCurrentUser(httpRequest);
        
        if (currentUser == null) {
            redirectToLogin(httpRequest, httpResponse, requestURI);
            return;
        }
        
        if (isAdminPath(path) && !currentUser.isAdmin()) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }
        
        chain.doFilter(request, response);
    }

    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        
        Object user = session.getAttribute("user");
        return (user instanceof User) ? (User) user : null;
    }

    private boolean isAdminPath(String path) {
        return path.startsWith("/admin/");
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response, 
                               String requestURI) throws IOException {
        String contextPath = request.getContextPath();
        String loginURL = contextPath + "/auth/login?redirect=" + 
                         java.net.URLEncoder.encode(requestURI, "UTF-8");
        response.sendRedirect(loginURL);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}
}

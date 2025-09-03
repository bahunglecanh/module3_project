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

        // false là chưa có sesstion thi tra ve null tu tao session moi
        HttpSession session = httpRequest.getSession(false);
        // gia tri ban dau chua tro toi doi tuong nao
        User currentUser = null;
        if (session != null) {
            currentUser = (User) session.getAttribute("user");
        }


        if (currentUser == null) {
            String loginURL = contextPath + "/auth/login?redirect=" + java.net.URLEncoder.encode(requestURI, "UTF-8");
            httpResponse.sendRedirect(loginURL);
            return;
        }

        // Check admin access
        if (path.startsWith("/admin/")) {
            if (!currentUser.isAdmin()) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }
        }
        // Tiep tuc requeest response
        chain.doFilter(request, response);
    }

}
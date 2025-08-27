package hunglcb.example.projectmd3.filter;

import hunglcb.example.projectmd3.model.User;
import hunglcb.example.projectmd3.service.user.IUserService;
import hunglcb.example.projectmd3.service.user.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserContextFilter implements Filter {

    private IUserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userService = new UserService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            Object sessUser = session.getAttribute("user");
            if (sessUser instanceof User) {
                User currentUser = (User) sessUser;
                // If avatar is missing, try to refresh once from DB
                if (currentUser.getAvatarUrl() == null || currentUser.getAvatarUrl().trim().isEmpty()) {
                    try {
                        User refreshed = userService.findById(currentUser.getId());
                        if (refreshed != null) {
                            currentUser = refreshed;
                            session.setAttribute("user", refreshed);
                        }
                    } catch (Exception ignored) {}
                }
                request.setAttribute("currentUser", currentUser);
                request.setAttribute("isLoggedIn", true);
                request.setAttribute("isAdmin", currentUser.isAdmin());
            } else {
                request.setAttribute("isLoggedIn", false);
            }
        } else {
            request.setAttribute("isLoggedIn", false);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}



package com.ashop.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ashop.entity.User;

/**
 * Filter kiểm tra đăng nhập cho các URL cần xác thực
 * Loại trừ: /login, /register, /logout, /, /home, static resources
 */
@WebFilter(urlPatterns={"/user/*", "/admin/*", "/profile", "/account", "/orders", "/cart"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        System.out.println("AuthFilter initialized!");
    }

    @Override
    public void destroy() {
        System.out.println("AuthFilter destroyed!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Cast to HTTP objects
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        // Lấy Session và User Object
        HttpSession session = req.getSession(false); 
        User user = null; 
        
        if (session != null) {
             Object userObj = session.getAttribute("currentUser");
             if (userObj instanceof User) {
                 user = (User) userObj;
             }
        }

        // Kiểm tra đã đăng nhập
        if (user != null) { 
            // Đã đăng nhập -> Cho phép yêu cầu đi tiếp
            chain.doFilter(request, response);
            return;
            
        } else {
            // Chưa đăng nhập -> Chuyển hướng về login
            String contextPath = req.getContextPath(); 
            String loginUrl = contextPath + "/login";
            
            resp.sendRedirect(loginUrl); 
        }
    }
}

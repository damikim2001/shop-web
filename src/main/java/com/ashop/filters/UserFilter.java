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
 * Filter bảo vệ các URL dành cho user thông thường
 * Chỉ cho phép user có role "USER" truy cập các URL /user/*
 */
@WebFilter(urlPatterns="/user/*")
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        System.out.println("UserFilter initialized!");
    }

    @Override
    public void destroy() {
        System.out.println("UserFilter destroyed!");
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

        // Kiểm tra đã đăng nhập và có quyền USER
        if (user != null && "USER".equalsIgnoreCase(user.getRole())) { 
            // Hợp lệ -> Cho phép yêu cầu đi tiếp
            chain.doFilter(request, response);
            return;
            
        } else {
            // Không hợp lệ -> Chuyển hướng về trang chủ hoặc login
            String contextPath = req.getContextPath(); 
            String redirectUrl = contextPath + "/";
            
            // Nếu chưa đăng nhập, chuyển về login
            if (user == null) {
                redirectUrl = contextPath + "/login";
            }
            
            resp.sendRedirect(redirectUrl); 
        }
    }
}

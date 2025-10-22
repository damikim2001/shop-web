package com.ashop.controllers;

import com.ashop.entity.User;
import com.ashop.services.UserService;
import com.ashop.services.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Controller quản lý profile của user
 */
@WebServlet("/user/profile")
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final UserService userService = new UserServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser != null) {
            // Lấy thông tin user mới nhất từ database
            User user = userService.findById(currentUser.getUserId());
            request.setAttribute("user", user);
        }
        
        request.getRequestDispatcher("/views/user/profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser != null) {
            try {
                // Lấy dữ liệu từ form
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                
                // Cập nhật thông tin user
                User user = userService.findById(currentUser.getUserId());
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPhone(phone);
                user.setAddress(address);
                
                userService.saveOrUpdate(user);
                
                // Cập nhật session
                session.setAttribute("currentUser", user);
                
                request.getSession().setAttribute("message", "Cập nhật thông tin thành công!");
                
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Lỗi cập nhật thông tin: " + e.getMessage());
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/user/profile");
    }
}

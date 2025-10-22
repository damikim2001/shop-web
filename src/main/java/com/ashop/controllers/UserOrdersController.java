package com.ashop.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller quản lý đơn hàng của user
 */
@WebServlet("/user/orders")
public class UserOrdersController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // TODO: Lấy danh sách đơn hàng của user từ database
        // Hiện tại chỉ hiển thị trang demo
        
        request.getRequestDispatcher("/views/user/orders.jsp").forward(request, response);
    }
}

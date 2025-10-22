package com.ashop.controllers.admin;

import com.ashop.entity.Video;
import com.ashop.services.VideoService;
import com.ashop.services.impl.VideoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/videos")
public class VideoListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final VideoService videoService = new VideoServiceImpl();
    private static final int ITEMS_PER_PAGE = 10;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // Lấy tham số phân trang
            String pageParam = request.getParameter("page");
            int currentPage = (pageParam != null && !pageParam.isEmpty()) ? 
                Integer.parseInt(pageParam) : 0;
            
            // Lấy danh sách video
            List<Video> videos = videoService.findAll(currentPage, ITEMS_PER_PAGE);
            long totalVideos = videoService.count();
            int totalPages = (int) Math.ceil((double) totalVideos / ITEMS_PER_PAGE);
            
            // Đặt dữ liệu vào request
            request.setAttribute("videos", videos);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalVideos", totalVideos);
            
            // Forward đến trang JSP
            request.getRequestDispatcher("/views/admin/video-list.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi tải danh sách video: " + e.getMessage());
            request.getRequestDispatcher("/views/admin/video-list.jsp").forward(request, response);
        }
    }
}

package com.ashop.controllers.admin;

import com.ashop.entity.Category;
import com.ashop.entity.Video;
import com.ashop.services.CategoryService;
import com.ashop.services.VideoService;
import com.ashop.services.impl.CategoryServiceImpl;
import com.ashop.services.impl.VideoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@WebServlet({"/admin/video/add", "/admin/video/edit"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class VideoFormController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final VideoService videoService = new VideoServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();
    private static final String UPLOAD_DIR = "/images/videos/";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // Lấy danh sách categories cho dropdown
        List<Category> categories = categoryService.findAll();
        request.setAttribute("categories", categories);
        
        String servletPath = request.getServletPath();
        
        if (servletPath.equals("/admin/video/edit")) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int videoId = Integer.parseInt(idParam);
                    Video video = videoService.findById(videoId);
                    
                    if (video != null) {
                        request.setAttribute("video", video);
                    } else {
                        request.getSession().setAttribute("message", "Lỗi: Không tìm thấy video.");
                        response.sendRedirect(request.getContextPath() + "/admin/videos");
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("message", "Lỗi: ID video không hợp lệ.");
                    response.sendRedirect(request.getContextPath() + "/admin/videos");
                    return;
                }
            }
        }
        
        request.getRequestDispatcher("/views/admin/video-form.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String servletPath = request.getServletPath();
        Video video;
        String message;
        String posterPath = null;
        String oldPoster = request.getParameter("oldPoster");

        try {
            // Lấy dữ liệu form
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String videoUrl = request.getParameter("videoUrl");
            String categoryIdParam = request.getParameter("categoryId");
            String activeParam = request.getParameter("active");
            boolean active = "on".equalsIgnoreCase(activeParam) || "true".equalsIgnoreCase(activeParam);

            // Xử lý Upload Poster
            Part filePart = request.getPart("posterFile");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String fullUploadPath = request.getServletContext().getRealPath(UPLOAD_DIR);
                
                // Tạo thư mục nếu chưa tồn tại
                Path uploadPath = Paths.get(fullUploadPath);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                // Lưu file
                Path filePath = Paths.get(fullUploadPath, fileName);
                filePart.write(filePath.toString());
                posterPath = UPLOAD_DIR + fileName;
            } else {
                posterPath = oldPoster; // Giữ lại poster cũ
            }

            // Lấy Category
            Category category = categoryService.findById(Integer.parseInt(categoryIdParam));
            if (category == null) {
                throw new IllegalArgumentException("Danh mục không hợp lệ.");
            }

            // Logic CẬP NHẬT (EDIT)
            if (servletPath.equals("/admin/video/edit")) {
                int videoId = Integer.parseInt(request.getParameter("id"));
                video = videoService.findById(videoId);
                
                if (video == null) {
                    throw new RuntimeException("Không tìm thấy video để cập nhật.");
                }

                video.setTitle(title);
                video.setDescription(description);
                video.setVideoUrl(videoUrl);
                video.setPoster(posterPath);
                video.setActive(active);
                video.setCategory(category);

                message = "Cập nhật video **" + title + "** thành công!";

            // Logic THÊM MỚI (ADD)
            } else {
                video = new Video();
                video.setTitle(title);
                video.setDescription(description);
                video.setVideoUrl(videoUrl);
                video.setPoster(posterPath);
                video.setActive(active);
                video.setCategory(category);
                
                message = "Thêm mới video **" + title + "** thành công!";
            }
            
            // Lưu/Cập nhật
            videoService.saveOrUpdate(video);

            // Chuyển hướng
            request.getSession().setAttribute("message", message);
            response.sendRedirect(request.getContextPath() + "/admin/videos");

        } catch (Exception e) {
            e.printStackTrace();
            
            // Tái tạo dữ liệu form khi có lỗi
            request.setAttribute("categories", categoryService.findAll());
            
            Video errorVideo = new Video();
            if (servletPath.equals("/admin/video/edit") && request.getParameter("id") != null) {
                errorVideo.setId(Integer.parseInt(request.getParameter("id")));
            }
            
            errorVideo.setTitle(request.getParameter("title"));
            errorVideo.setDescription(request.getParameter("description"));
            errorVideo.setVideoUrl(request.getParameter("videoUrl"));
            errorVideo.setPoster(posterPath != null ? posterPath : oldPoster);
            errorVideo.setActive("on".equalsIgnoreCase(request.getParameter("active")));
            
            request.setAttribute("video", errorVideo);
            request.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
            request.getRequestDispatcher("/views/admin/video-form.jsp").forward(request, response);
        }
    }
}

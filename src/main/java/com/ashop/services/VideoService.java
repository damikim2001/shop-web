package com.ashop.services;

import com.ashop.entity.Video;
import java.util.List;

public interface VideoService {
    
    /**
     * Lưu hoặc cập nhật video
     */
    Video saveOrUpdate(Video video);
    
    /**
     * Tìm video theo ID
     */
    Video findById(Integer id);
    
    /**
     * Lấy tất cả video
     */
    List<Video> findAll();
    
    /**
     * Lấy danh sách video có phân trang
     */
    List<Video> findAll(int page, int size);
    
    /**
     * Lấy danh sách video theo category
     */
    List<Video> findByCategory(Integer categoryId);
    
    /**
     * Lấy danh sách video đang hoạt động
     */
    List<Video> findActiveVideos();
    
    /**
     * Đếm tổng số video
     */
    long count();
    
    /**
     * Xóa video
     */
    void delete(Integer id);
    
    /**
     * Tìm kiếm video theo tiêu đề
     */
    List<Video> searchByTitle(String title);
}

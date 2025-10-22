package com.ashop.services.impl;

import com.ashop.dao.VideoDAO;
import com.ashop.dao.impl.VideoDAOImpl;
import com.ashop.entity.Video;
import com.ashop.services.VideoService;
import java.util.List;

public class VideoServiceImpl implements VideoService {
    
    private final VideoDAO videoDAO;
    
    public VideoServiceImpl() {
        this.videoDAO = new VideoDAOImpl();
    }

    @Override
    public Video saveOrUpdate(Video video) {
        return videoDAO.saveOrUpdate(video);
    }

    @Override
    public Video findById(Integer id) {
        return videoDAO.findById(id);
    }

    @Override
    public List<Video> findAll() {
        return videoDAO.findAll();
    }

    @Override
    public List<Video> findAll(int page, int size) {
        return videoDAO.findAll(page, size);
    }

    @Override
    public List<Video> findByCategory(Integer categoryId) {
        return videoDAO.findByCategory(categoryId);
    }

    @Override
    public List<Video> findActiveVideos() {
        return videoDAO.findActiveVideos();
    }

    @Override
    public long count() {
        return videoDAO.count();
    }

    @Override
    public void delete(Integer id) {
        videoDAO.delete(id);
    }

    @Override
    public List<Video> searchByTitle(String title) {
        return videoDAO.searchByTitle(title);
    }
}

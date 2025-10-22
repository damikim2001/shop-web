package com.ashop.dao.impl;

import com.ashop.dao.VideoDAO;
import com.ashop.entity.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class VideoDAOImpl implements VideoDAO {
    
    private EntityManagerFactory entityManagerFactory;
    
    public VideoDAOImpl() {
        this.entityManagerFactory = com.ashop.configs.JPAConfig.getEntityManagerFactory();
    }

    @Override
    public Video saveOrUpdate(Video video) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (video.getId() == null) {
                em.persist(video);
            } else {
                video = em.merge(video);
            }
            em.getTransaction().commit();
            return video;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error saving video", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Video findById(Integer id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.find(Video.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Video> query = em.createQuery("SELECT v FROM Video v ORDER BY v.createdAt DESC", Video.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findAll(int page, int size) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Video> query = em.createQuery("SELECT v FROM Video v ORDER BY v.createdAt DESC", Video.class);
            query.setFirstResult(page * size);
            query.setMaxResults(size);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findByCategory(Integer categoryId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Video> query = em.createQuery(
                "SELECT v FROM Video v WHERE v.category.categoryId = :categoryId ORDER BY v.createdAt DESC", 
                Video.class
            );
            query.setParameter("categoryId", categoryId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findActiveVideos() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Video> query = em.createQuery(
                "SELECT v FROM Video v WHERE v.active = true ORDER BY v.createdAt DESC", 
                Video.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(v) FROM Video v", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            Video video = em.find(Video.class, id);
            if (video != null) {
                em.remove(video);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error deleting video", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> searchByTitle(String title) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Video> query = em.createQuery(
                "SELECT v FROM Video v WHERE LOWER(v.title) LIKE LOWER(:title) ORDER BY v.createdAt DESC", 
                Video.class
            );
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

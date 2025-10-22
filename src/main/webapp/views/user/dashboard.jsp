<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="col-sm-9">
    <h1><small>Trang Cá Nhân</small></h1>
    <hr/>
    
    <div class="alert alert-success">
        Chào mừng, ${sessionScope.currentUser.fullName}! 
        Đây là khu vực dành cho người dùng.
    </div>

    <div class="row">
        <div class="col-md-4 mb-4">
            <div class="card text-white bg-info shadow">
                <div class="card-body">
                    <h5 class="card-title">Đơn Hàng Của Tôi</h5>
                    <p class="card-text fs-3">3</p>
                </div>
                <div class="card-footer">
                    <a href="<c:url value="/user/orders"/>" class="text-white">Xem đơn hàng <i class="fas fa-arrow-circle-right"></i></a>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 mb-4">
            <div class="card text-white bg-warning shadow">
                <div class="card-body">
                    <h5 class="card-title">Thông Tin Cá Nhân</h5>
                    <p class="card-text">Quản lý profile</p>
                </div>
                <div class="card-footer">
                    <a href="<c:url value="/user/profile"/>" class="text-white">Cập nhật thông tin <i class="fas fa-arrow-circle-right"></i></a>
                </div>
            </div>
        </div>

        <div class="col-md-4 mb-4">
            <div class="card text-white bg-primary shadow">
                <div class="card-body">
                    <h5 class="card-title">Giỏ Hàng</h5>
                    <p class="card-text fs-3">5</p>
                </div>
                <div class="card-footer">
                    <a href="<c:url value="/user/cart"/>" class="text-white">Xem giỏ hàng <i class="fas fa-arrow-circle-right"></i></a>
                </div>
            </div>
        </div>
    </div>
    
    <div class="row mt-4">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    <h5>Thông Tin Tài Khoản</h5>
                </div>
                <div class="card-body">
                    <p><strong>Tên đăng nhập:</strong> ${sessionScope.currentUser.username}</p>
                    <p><strong>Họ tên:</strong> ${sessionScope.currentUser.fullName}</p>
                    <p><strong>Email:</strong> ${sessionScope.currentUser.email}</p>
                    <p><strong>Vai trò:</strong> ${sessionScope.currentUser.role}</p>
                </div>
            </div>
        </div>
    </div>
</div>

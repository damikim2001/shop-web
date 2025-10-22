<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="col-sm-9">
    <h1><small>Đơn Hàng Của Tôi</small></h1>
    <hr/>
    
    <div class="card">
        <div class="card-header">
            <h5>Danh Sách Đơn Hàng</h5>
        </div>
        <div class="card-body">
            <div class="alert alert-info">
                <i class="fas fa-info-circle"></i>
                Tính năng quản lý đơn hàng đang được phát triển. 
                Sẽ có sớm trong phiên bản tiếp theo.
            </div>
            
            <div class="text-center py-4">
                <i class="fas fa-shopping-cart fa-3x text-muted mb-3"></i>
                <p class="text-muted">Chưa có đơn hàng nào</p>
                <a href="<c:url value="/"/>" class="btn btn-primary">
                    <i class="fas fa-shopping-bag"></i> Tiếp tục mua sắm
                </a>
            </div>
        </div>
    </div>
</div>

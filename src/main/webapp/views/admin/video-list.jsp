<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="col-sm-9">
    <h1><small>Danh sách Video</small></h1>
    <hr/>
    
    <%-- Hiển thị thông báo (Message) --%>
    <c:if test="${sessionScope.message != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${sessionScope.message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="message" scope="session"/>
    </c:if>

    <p>
        <%-- Link thêm video mới --%>
        <a href="<c:url value="/admin/video/add"/>" class="btn btn-primary mb-3">
            <i class="fas fa-plus-circle"></i> Thêm Video mới
        </a>
        <span class="text-muted ms-3">Tổng cộng: ${totalVideos} video</span>
    </p>
    <br/>
    
    <div class="table-responsive">
        <table class="table table-bordered table-striped table-hover shadow-sm">
            <thead class="table-dark">
                <tr>
                    <th style="width: 5%;">ID</th>
                    <th style="width: 20%;">Tiêu đề</th>
                    <th style="width: 15%;">Danh mục</th>
                    <th style="width: 10%;">Lượt xem</th>
                    <th style="width: 10%;">Trạng thái</th>
                    <th style="width: 15%;">Ngày tạo</th>
                    <th class="text-center" style="width: 15%;">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="video" items="${videos}">
                    <tr>
                        <td>${video.id}</td>
                        <td>
                            <strong>${video.title}</strong>
                            <c:if test="${not empty video.description}">
                                <br><small class="text-muted">${video.description.length() > 50 ? video.description.substring(0, 50) + '...' : video.description}</small>
                            </c:if>
                        </td>
                        <td>
                            <span class="badge bg-info">${video.category.categoryName}</span>
                        </td>
                        <td>
                            <span class="badge bg-secondary">${video.views}</span>
                        </td>
                        <td>
                            <span class="badge ${video.active ? 'bg-success' : 'bg-warning'}">
                                ${video.active ? 'Hoạt động' : 'Ẩn'}
                            </span>
                        </td>
                        <td>
                            <small>${video.createdAt}</small>
                        </td>
                        <td class="text-center">
                            <a href="<c:url value="/admin/video/edit?id=${video.id}"/>" class="btn btn-sm btn-primary me-2" title="Chỉnh sửa video">
                                <i class="fas fa-edit"></i>
                            </a>
                            <button onclick="confirmDelete(${video.id}, '${video.title}')" class="btn btn-sm btn-danger" title="Xóa video">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty videos}">
                    <tr>
                        <td colspan="7" class="text-center">Không tìm thấy video nào.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <%-- Logic phân trang --%>
    <c:if test="${totalPages > 1}">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                
                <%-- Nút Previous --%>
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link" href="<c:url value="/admin/users"><c:param name="page" value="${currentPage - 1}"/></c:url>" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <%-- Các nút số trang --%>
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                        <a class="page-link" href="<c:url value="/admin/users"><c:param name="page" value="${i}"/></c:url>">${i}</a>
                    </li>
                </c:forEach>

                <%-- Nút Next --%>
                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="<c:url value="/admin/users"><c:param name="page" value="${currentPage + 1}"/></c:url>" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </c:if>
</div>

<%-- Form ẩn và Script cho việc xóa (POST) --%>
<form id="deleteForm" action="<c:url value="/admin/user/delete"/>" method="post" style="display: none;">
    <input type="hidden" name="id" id="userIdToDelete">
</form>

<script>
    function confirmDelete(id, username) {
        if (confirm('Bạn có chắc chắn muốn KHÓA/XÓA tài khoản "' + username + '" không?')) {
            document.getElementById('userIdToDelete').value = id;
            document.getElementById('deleteForm').submit();
        }
    }
</script>
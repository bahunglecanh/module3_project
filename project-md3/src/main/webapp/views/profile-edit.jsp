<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Cập nhật thông tin cá nhân" />
<jsp:include page="common/header.jsp" />

<div class="container py-4">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Cập nhật thông tin</h5>
                    <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/profile">
                        <i class="fas fa-arrow-left me-1"></i>Quay lại
                    </a>
                </div>
                <div class="card-body">
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">${errorMessage}</div>
                    </c:if>
                    <form method="post" action="${pageContext.request.contextPath}/profile/update" enctype="multipart/form-data">
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label">Họ và tên</label>
                                <input type="text" name="fullName" class="form-control" value="${currentUser.fullName}" required />
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Số điện thoại</label>
                                <input type="text" name="phone" class="form-control" value="${currentUser.phone}" />
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Giới tính</label>
                                <select name="gender" class="form-select">
                                    <option value="" ${empty currentUser.gender ? 'selected' : ''}>-- Chọn --</option>
                                    <option value="male" ${currentUser.gender == 'MALE' ? 'selected' : ''}>Nam</option>
                                    <option value="female" ${currentUser.gender == 'FEMALE' ? 'selected' : ''}>Nữ</option>
                                    <option value="other" ${currentUser.gender == 'OTHER' ? 'selected' : ''}>Khác</option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Ngày sinh</label>
                                <input type="date" name="birthDate" class="form-control" value="${currentUser.birthDate}" />
                            </div>
                            <div class="col-12">
                                <label class="form-label">Ảnh đại diện</label>
                                <input type="file" name="avatarFile" class="form-control" accept="image/*" />
                                <c:if test="${not empty currentUser.avatarUrl}">
                                    <small class="text-muted">Ảnh hiện tại: ${currentUser.avatarUrl}</small>
                                </c:if>
                            </div>
                        </div>
                        <div class="mt-4 d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/profile" class="btn btn-light">Hủy</a>
                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />



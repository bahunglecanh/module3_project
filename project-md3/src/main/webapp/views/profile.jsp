<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Thông tin cá nhân" />
<jsp:include page="common/header.jsp" />

<div class="container py-4">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Thông tin cá nhân</h5>
                    <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/profile/edit">
                        <i class="fas fa-edit me-1"></i>Chỉnh sửa
                    </a>
                </div>
                <div class="card-body">
                    <c:if test="${param.success == '1'}">
                        <div class="alert alert-success">Cập nhật thông tin thành công!</div>
                    </c:if>

                    <div class="d-flex align-items-center mb-4">
                        <div style="width:70px;height:70px;border-radius:50%;overflow:hidden;background:#f0f2f5;display:flex;align-items:center;justify-content:center;font-size:28px;font-weight:700;color:#555;">
                            <c:choose>
                                <c:when test="${not empty currentUser.avatarUrl}">
                                    <c:set var="imgSrc" value="${currentUser.avatarUrl}" />
                                    <c:if test="${not fn:startsWith(imgSrc, 'http')}">
                                        <c:set var="imgSrc" value="${pageContext.request.contextPath}/${imgSrc}" />
                                    </c:if>
                                    <img src="${imgSrc}" alt="Avatar" style="width:100%;height:100%;object-fit:cover;"/>
                                </c:when>
                                <c:otherwise>
                                    ${currentUser.firstLetter}
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="ms-3">
                            <h5 class="mb-1">${currentUser.fullName}</h5>
                            <div class="text-muted">${currentUser.email}</div>
                        </div>
                    </div>

                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label text-muted">Họ và tên</label>
                            <div class="form-control">${empty currentUser.fullName ? 'Chưa cập nhật' : currentUser.fullName}</div>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label text-muted">Số điện thoại</label>
                            <div class="form-control">${empty currentUser.phone ? 'Chưa cập nhật' : currentUser.phone}</div>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label text-muted">Giới tính</label>
                            <div class="form-control">
                                <c:choose>
                                    <c:when test="${currentUser.gender == 'MALE'}">Nam</c:when>
                                    <c:when test="${currentUser.gender == 'FEMALE'}">Nữ</c:when>
                                    <c:when test="${currentUser.gender == 'OTHER'}">Khác</c:when>
                                    <c:otherwise>Chưa cập nhật</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label text-muted">Ngày sinh</label>
                            <div class="form-control">
                                <c:choose>
                                    <c:when test="${not empty currentUser.birthDate}">${currentUser.birthDate}</c:when>
                                    <c:otherwise>Chưa cập nhật</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <c:if test="${empty currentUser.fullName || empty currentUser.phone || empty currentUser.gender || empty currentUser.birthDate}">
                        <div class="mt-4">
                            <div class="alert alert-info d-flex justify-content-between align-items-center">
                                <span>Bạn chưa điền đủ thông tin cá nhân. Vui lòng cập nhật để trải nghiệm tốt hơn.</span>
                                <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/profile/edit">Cập nhật ngay</a>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />



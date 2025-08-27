<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Đặt lại mật khẩu" />
<jsp:include page="../common/header.jsp" />

<div class="auth-container">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-5 col-md-7 col-sm-9">
                <div class="auth-card">
                    <div class="auth-header">
                        <div class="auth-icon">
                            <i class="fas fa-lock"></i>
                        </div>
                        <h2 class="auth-title">Đặt lại mật khẩu</h2>
                        <p class="auth-subtitle">Nhập mật khẩu mới của bạn</p>
                    </div>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">${errorMessage}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/auth/forgot-password/reset" method="post" class="auth-form">
                        <input type="hidden" name="email" value="${email}" />
                        <div class="form-group">
                            <label for="password" class="form-label">Mật khẩu mới</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="Tối thiểu 6 ký tự" required>
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                        </div>

                        <button type="submit" class="btn btn-primary auth-btn">Đổi mật khẩu</button>
                    </form>

                    <div class="auth-footer" style="text-align:center;margin-top:15px;">
                        <a class="auth-link" href="${pageContext.request.contextPath}/auth/login">Về trang đăng nhập</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />



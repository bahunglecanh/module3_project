<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Quên mật khẩu" />
<jsp:include page="../common/header.jsp" />

<div class="auth-container">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-5 col-md-7 col-sm-9">
                <div class="auth-card">
                    <div class="auth-header">
                        <div class="auth-icon">
                            <i class="fas fa-key"></i>
                        </div>
                        <h2 class="auth-title">Quên mật khẩu</h2>
                        <p class="auth-subtitle">Nhập email đã đăng ký để nhận OTP</p>
                    </div>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger" role="alert">${errorMessage}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/auth/forgot-password/request" method="post" class="auth-form">
                        <div class="form-group">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" value="${email}" placeholder="Nhập email đã đăng ký" required>
                        </div>

                        <button type="submit" class="btn btn-primary auth-btn">Gửi OTP</button>
                    </form>

                    <div class="auth-footer" style="text-align:center;margin-top:15px;">
                        <a class="auth-link" href="${pageContext.request.contextPath}/auth/login">Quay lại đăng nhập</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />



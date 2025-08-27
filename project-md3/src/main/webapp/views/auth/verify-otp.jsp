<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Xác thực OTP" />
<jsp:include page="../common/header.jsp" />

<div class="auth-container">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-5 col-md-7 col-sm-9">
                <div class="auth-card">
                    <div class="auth-header">
                        <div class="auth-icon">
                            <i class="fas fa-shield-alt"></i>
                        </div>
                        <h2 class="auth-title">Xác thực OTP</h2>
                        <p class="auth-subtitle">Vui lòng kiểm tra email và nhập mã OTP</p>
                    </div>

                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">${errorMessage}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/auth/forgot-password/verify" method="post" class="auth-form">
                        <input type="hidden" name="email" value="${email}" />
                        <div class="form-group">
                            <label for="otp" class="form-label">Mã OTP</label>
                            <input type="text" class="form-control" id="otp" name="otp" placeholder="Nhập mã gồm 6 số" required>
                        </div>

                        <button type="submit" class="btn btn-primary auth-btn">Xác thực</button>
                    </form>

                    <div class="auth-footer" style="text-align:center;margin-top:15px;">
                        <a class="auth-link" href="${pageContext.request.contextPath}/auth/forgot-password">Gửi lại OTP</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />



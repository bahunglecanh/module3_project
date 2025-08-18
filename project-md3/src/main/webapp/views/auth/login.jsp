<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Đăng nhập" />
<jsp:include page="../common/header.jsp" />

<div class="auth-container">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-5 col-md-7 col-sm-9">
                <div class="auth-card">
                    <div class="auth-header">
                        <div class="auth-icon">
                            <i class="fas fa-sign-in-alt"></i>
                        </div>
                        <h2 class="auth-title">Đăng nhập</h2>
                        <p class="auth-subtitle">Chào mừng bạn quay trở lại!</p>
                    </div>

                    <form action="${pageContext.request.contextPath}/auth/login" method="post" class="auth-form">
                        <div class="form-group">
                            <label for="email" class="form-label">
                                <i class="fas fa-envelope me-2"></i>Email
                            </label>
                            <input type="email" 
                                   class="form-control" 
                                   id="email" 
                                   name="email" 
                                   value="${email}"
                                   placeholder="Nhập địa chỉ email"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="password" class="form-label">
                                <i class="fas fa-lock me-2"></i>Mật khẩu
                            </label>
                            <div class="password-field">
                                <input type="password" 
                                       class="form-control" 
                                       id="password" 
                                       name="password" 
                                       placeholder="Nhập mật khẩu"
                                       required>
                                <button type="button" class="password-toggle" onclick="togglePassword('password')">
                                    <i class="fas fa-eye" id="password-eye"></i>
                                </button>
                            </div>
                        </div>

                        <div class="form-options">
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                                <label class="form-check-label" for="rememberMe">
                                    Ghi nhớ đăng nhập
                                </label>
                            </div>
                            <a href="${pageContext.request.contextPath}/auth/forgot-password" class="forgot-link">
                                Quên mật khẩu?
                            </a>
                        </div>

                        <button type="submit" class="btn btn-primary auth-btn">
                            <i class="fas fa-sign-in-alt me-2"></i>Đăng nhập
                        </button>
                    </form>

                    <div class="auth-divider">
                        <span>Hoặc</span>
                    </div>

                    <div class="social-login">
                        <button type="button" class="btn btn-social btn-google">
                            <i class="fab fa-google me-2"></i>Đăng nhập với Google
                        </button>
                        <button type="button" class="btn btn-social btn-facebook">
                            <i class="fab fa-facebook-f me-2"></i>Đăng nhập với Facebook
                        </button>
                    </div>

                    <div class="auth-footer">
                        <p>Chưa có tài khoản? 
                            <a href="${pageContext.request.contextPath}/auth/register" class="auth-link">
                                Đăng ký ngay
                            </a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    .auth-container {
        min-height: calc(100vh - 200px);
        display: flex;
        align-items: center;
        padding: 40px 0;
        background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    }

    .auth-card {
        background: var(--white);
        border-radius: 16px;
        box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
        padding: 40px;
        width: 100%;
        transition: transform 0.3s ease;
    }

    .auth-card:hover {
        transform: translateY(-5px);
    }

    .auth-header {
        text-align: center;
        margin-bottom: 30px;
    }

    .auth-icon {
        width: 80px;
        height: 80px;
        background: linear-gradient(135deg, var(--secondary-color), #3742fa);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto 20px;
        color: var(--white);
        font-size: 24px;
        box-shadow: 0 8px 25px rgba(52, 152, 219, 0.3);
    }

    .auth-title {
        color: var(--primary-color);
        font-weight: 700;
        margin-bottom: 8px;
        font-size: 28px;
    }

    .auth-subtitle {
        color: var(--dark-gray);
        margin-bottom: 0;
        font-size: 16px;
    }

    .auth-form {
        margin-bottom: 30px;
    }

    .form-group {
        margin-bottom: 20px;
    }

    .form-label {
        color: var(--primary-color);
        font-weight: 600;
        margin-bottom: 8px;
        display: block;
    }

    .form-control {
        border: 2px solid var(--light-gray);
        border-radius: var(--border-radius);
        padding: 12px 16px;
        font-size: 16px;
        transition: all 0.3s ease;
        background: var(--white);
    }

    .form-control:focus {
        outline: none;
        border-color: var(--secondary-color);
        box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
        background: var(--white);
    }

    .password-field {
        position: relative;
    }

    .password-toggle {
        position: absolute;
        right: 12px;
        top: 50%;
        transform: translateY(-50%);
        background: none;
        border: none;
        color: var(--dark-gray);
        cursor: pointer;
        padding: 4px;
        transition: color 0.3s ease;
    }

    .password-toggle:hover {
        color: var(--secondary-color);
    }

    .form-options {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 25px;
    }

    .form-check-label {
        color: var(--dark-gray);
        font-size: 14px;
    }

    .forgot-link {
        color: var(--secondary-color);
        text-decoration: none;
        font-size: 14px;
        font-weight: 500;
    }

    .forgot-link:hover {
        color: #2980b9;
        text-decoration: underline;
    }

    .auth-btn {
        width: 100%;
        padding: 14px;
        font-size: 16px;
        font-weight: 600;
        border-radius: var(--border-radius);
        border: none;
        background: linear-gradient(135deg, var(--secondary-color), #3742fa);
        transition: all 0.3s ease;
    }

    .auth-btn:hover {
        background: linear-gradient(135deg, #2980b9, #2f3542);
        transform: translateY(-1px);
        box-shadow: 0 8px 25px rgba(52, 152, 219, 0.3);
    }

    .auth-divider {
        position: relative;
        text-align: center;
        margin: 25px 0;
    }

    .auth-divider::before {
        content: '';
        position: absolute;
        top: 50%;
        left: 0;
        right: 0;
        height: 1px;
        background: var(--light-gray);
    }

    .auth-divider span {
        background: var(--white);
        color: var(--dark-gray);
        padding: 0 20px;
        font-size: 14px;
    }

    .social-login {
        display: flex;
        flex-direction: column;
        gap: 12px;
        margin-bottom: 25px;
    }

    .btn-social {
        width: 100%;
        padding: 12px;
        font-weight: 500;
        border-radius: var(--border-radius);
        border: 2px solid;
        transition: all 0.3s ease;
    }

    .btn-google {
        background: var(--white);
        color: #db4437;
        border-color: #db4437;
    }

    .btn-google:hover {
        background: #db4437;
        color: var(--white);
    }

    .btn-facebook {
        background: var(--white);
        color: #4267B2;
        border-color: #4267B2;
    }

    .btn-facebook:hover {
        background: #4267B2;
        color: var(--white);
    }

    .auth-footer {
        text-align: center;
        padding-top: 20px;
        border-top: 1px solid var(--light-gray);
    }

    .auth-footer p {
        color: var(--dark-gray);
        margin: 0;
    }

    .auth-link {
        color: var(--secondary-color);
        text-decoration: none;
        font-weight: 600;
    }

    .auth-link:hover {
        color: #2980b9;
        text-decoration: underline;
    }

    /* Responsive */
    @media (max-width: 576px) {
        .auth-card {
            padding: 30px 20px;
            margin: 20px;
        }
        
        .auth-icon {
            width: 60px;
            height: 60px;
            font-size: 20px;
        }
        
        .auth-title {
            font-size: 24px;
        }
        
        .form-options {
            flex-direction: column;
            gap: 10px;
            text-align: center;
        }
    }
</style>

<script>
    function togglePassword(fieldId) {
        const passwordField = document.getElementById(fieldId);
        const eyeIcon = document.getElementById(fieldId + '-eye');
        
        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            eyeIcon.classList.remove('fa-eye');
            eyeIcon.classList.add('fa-eye-slash');
        } else {
            passwordField.type = 'password';
            eyeIcon.classList.remove('fa-eye-slash');
            eyeIcon.classList.add('fa-eye');
        }
    }
</script>

<jsp:include page="../common/footer.jsp" />

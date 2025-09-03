<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Đăng ký" />
<jsp:include page="../common/header.jsp" />

<div class="auth-container">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-md-8 col-sm-10">
                <div class="auth-card">
                    <div class="auth-header">
                        <div class="auth-icon">
                            <i class="fas fa-user-plus"></i>
                        </div>
                        <h2 class="auth-title">Đăng ký tài khoản</h2>
                        <p class="auth-subtitle">Tạo tài khoản để bắt đầu mua sắm!</p>
                    </div>

                    <c:if test="${showLoginLink}">
                        <div class="success-message">
                            <i class="fas fa-check-circle me-2"></i>
                            <span>Đăng ký thành công! 
                                <a href="${pageContext.request.contextPath}/auth/login" class="success-link">
                                    Đăng nhập ngay
                                </a>
                            </span>
                        </div>
                    </c:if>

                    <c:if test="${not showLoginLink}">
                        <form action="${pageContext.request.contextPath}/auth/register" method="post" class="auth-form" id="registerForm">
                            <div class="form-group">
                                <label for="fullName" class="form-label">
                                    <i class="fas fa-id-card me-2"></i>Họ và tên *
                                </label>
                                <input type="text" 
                                       class="form-control" 
                                       id="fullName" 
                                       name="fullName" 
                                       value="${fullName}"
                                       placeholder="Nhập họ và tên"
                                       required>
                            </div>

                            <div class="form-group">
                                <label for="email" class="form-label">
                                    <i class="fas fa-envelope me-2"></i>Email *
                                </label>
                                <input type="email" 
                                       class="form-control" 
                                       id="email" 
                                       name="email" 
                                       value="${email}"
                                       placeholder="Nhập địa chỉ email"
                                       required>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="password" class="form-label">
                                            <i class="fas fa-lock me-2"></i>Mật khẩu *
                                        </label>
                                        <div class="password-field">
                                            <input type="password" 
                                                   class="form-control" 
                                                   id="password" 
                                                   name="password" 
                                                   placeholder="Nhập mật khẩu"
                                                   required
                                                   minlength="6">
                                            <button type="button" class="password-toggle" onclick="togglePassword('password')">
                                                <i class="fas fa-eye" id="password-eye"></i>
                                            </button>
                                        </div>
                                        <div class="form-text">Ít nhất 6 ký tự</div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="confirmPassword" class="form-label">
                                            <i class="fas fa-lock me-2"></i>Xác nhận mật khẩu *
                                        </label>
                                        <div class="password-field">
                                            <input type="password" 
                                                   class="form-control" 
                                                   id="confirmPassword" 
                                                   name="confirmPassword" 
                                                   placeholder="Nhập lại mật khẩu"
                                                   required
                                                   minlength="6">
                                            <button type="button" class="password-toggle" onclick="togglePassword('confirmPassword')">
                                                <i class="fas fa-eye" id="confirmPassword-eye"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="password-strength" id="passwordStrength" style="display: none;">
                                <div class="strength-bar">
                                    <div class="strength-fill" id="strengthFill"></div>
                                </div>
                                <span class="strength-text" id="strengthText"></span>
                            </div>

                            <div class="form-check-container">
                                <div class="form-check">
                                    <input type="checkbox" class="form-check-input" id="agreeTerms" required>
                                    <label class="form-check-label" for="agreeTerms">
                                        Tôi đồng ý với 
                                        <a href="${pageContext.request.contextPath}/terms" target="_blank" class="terms-link">
                                            Điều khoản sử dụng
                                        </a> 
                                        và 
                                        <a href="${pageContext.request.contextPath}/privacy" target="_blank" class="terms-link">
                                            Chính sách bảo mật
                                        </a>
                                    </label>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-primary auth-btn" id="submitBtn">
                                <i class="fas fa-user-plus me-2"></i>Đăng ký tài khoản
                            </button>
                        </form>

                        <div class="auth-divider">
                            <span>Hoặc</span>
                        </div>

                        <div class="social-login">
                            <button type="button" class="btn btn-social btn-google">
                                <i class="fab fa-google me-2"></i>Đăng ký với Google
                            </button>
                            <button type="button" class="btn btn-social btn-facebook">
                                <i class="fab fa-facebook-f me-2"></i>Đăng ký với Facebook
                            </button>
                        </div>
                    </c:if>

                    <div class="auth-footer">
                        <p>Đã có tài khoản? 
                            <a href="${pageContext.request.contextPath}/auth/login" class="auth-link">
                                Đăng nhập ngay
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
        background: white;
    }

    .success-message {
        background: rgba(39, 174, 96, 0.1);
        color: var(--success-color);
        border: 1px solid rgba(39, 174, 96, 0.3);
        border-radius: var(--border-radius);
        padding: 15px;
        margin-bottom: 20px;
        text-align: center;
    }

    .success-link {
        color: var(--success-color);
        font-weight: 600;
        text-decoration: none;
    }

    .success-link:hover {
        text-decoration: underline;
    }

    .form-text {
        font-size: 12px;
        color: var(--dark-gray);
        margin-top: 4px;
    }

    .password-strength {
        margin-bottom: 20px;
    }

    .strength-bar {
        width: 100%;
        height: 4px;
        background: var(--light-gray);
        border-radius: 2px;
        overflow: hidden;
        margin-bottom: 5px;
    }

    .strength-fill {
        height: 100%;
        width: 0;
        transition: all 0.3s ease;
        border-radius: 2px;
    }

    .strength-text {
        font-size: 12px;
        font-weight: 500;
    }

    .form-check-container {
        margin-bottom: 25px;
    }

    .form-check-label {
        color: var(--dark-gray);
        font-size: 14px;
        line-height: 1.5;
    }

    .terms-link {
        color: var(--secondary-color);
        text-decoration: none;
        font-weight: 500;
    }

    .terms-link:hover {
        text-decoration: underline;
    }

    /* Password strength colors */
    .strength-weak {
        background: #e74c3c;
    }

    .strength-medium {
        background: #f39c12;
    }

    .strength-strong {
        background: #27ae60;
    }

    .text-weak {
        color: #e74c3c;
    }

    .text-medium {
        color: #f39c12;
    }

    .text-strong {
        color: #27ae60;
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

    // Password strength checker
    document.getElementById('password').addEventListener('input', function() {
        const password = this.value;
        const strengthIndicator = document.getElementById('passwordStrength');
        const strengthFill = document.getElementById('strengthFill');
        const strengthText = document.getElementById('strengthText');
        
        if (password.length === 0) {
            strengthIndicator.style.display = 'none';
            return;
        }
        
        strengthIndicator.style.display = 'block';
        
        let strength = 0;
        let strengthClass = '';
        let strengthLabel = '';
        
        // Length check
        if (password.length >= 6) strength += 25;
        if (password.length >= 8) strength += 25;
        
        // Character variety checks
        if (/[a-z]/.test(password)) strength += 12.5;
        if (/[A-Z]/.test(password)) strength += 12.5;
        if (/[0-9]/.test(password)) strength += 12.5;
        if (/[^A-Za-z0-9]/.test(password)) strength += 12.5;
        
        // Determine strength level
        if (strength < 50) {
            strengthClass = 'strength-weak';
            strengthLabel = 'Yếu';
            strengthText.className = 'strength-text text-weak';
        } else if (strength < 75) {
            strengthClass = 'strength-medium';
            strengthLabel = 'Trung bình';
            strengthText.className = 'strength-text text-medium';
        } else {
            strengthClass = 'strength-strong';
            strengthLabel = 'Mạnh';
            strengthText.className = 'strength-text text-strong';
        }
        
        strengthFill.style.width = strength + '%';
        strengthFill.className = 'strength-fill ' + strengthClass;
        strengthText.textContent = 'Độ mạnh mật khẩu: ' + strengthLabel;
    });

    // Password confirmation checker
    document.getElementById('confirmPassword').addEventListener('input', function() {
        const password = document.getElementById('password').value;
        const confirmPassword = this.value;
        
        if (confirmPassword && password !== confirmPassword) {
            this.setCustomValidity('Mật khẩu xác nhận không khớp');
        } else {
            this.setCustomValidity('');
        }
    });

    // Form validation
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const agreeTerms = document.getElementById('agreeTerms').checked;
        
        if (password !== confirmPassword) {
            e.preventDefault();
            alert('Mật khẩu và xác nhận mật khẩu không khớp!');
            return;
        }
        
        if (!agreeTerms) {
            e.preventDefault();
            alert('Vui lòng đồng ý với điều khoản sử dụng!');
            return;
        }
        
        // Disable submit button to prevent double submission
        const submitBtn = document.getElementById('submitBtn');
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Đang xử lý...';
    });
</script>

<jsp:include page="../common/footer.jsp" />

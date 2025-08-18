<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!-- Footer -->
    <footer class="footer mt-5">
        <div class="footer-content">
            <div class="container">
                <div class="row">
                    <!-- Company Info -->
                    <div class="col-lg-4 col-md-6 mb-4">
                        <div class="footer-section">
                            <h5 class="footer-title">
                                <i class="fas fa-shoe-prints me-2"></i>Shoe Store
                            </h5>
                            <p class="footer-text">
                                Cửa hàng giày chất lượng cao với nhiều mẫu mã đa dạng, 
                                phù hợp với mọi phong cách và lứa tuổi. Chúng tôi cam kết 
                                mang đến những sản phẩm tốt nhất với giá cả hợp lý.
                            </p>
                            <div class="social-links">
                                <a href="#" class="social-link"><i class="fab fa-facebook-f"></i></a>
                                <a href="#" class="social-link"><i class="fab fa-instagram"></i></a>
                                <a href="#" class="social-link"><i class="fab fa-twitter"></i></a>
                                <a href="#" class="social-link"><i class="fab fa-youtube"></i></a>
                            </div>
                        </div>
                    </div>

                    <!-- Quick Links -->
                    <div class="col-lg-2 col-md-6 mb-4">
                        <div class="footer-section">
                            <h6 class="footer-title">Liên kết nhanh</h6>
                            <ul class="footer-links">
                                <li><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
                                <li><a href="${pageContext.request.contextPath}/products">Sản phẩm</a></li>
                                <li><a href="${pageContext.request.contextPath}/about">Giới thiệu</a></li>
                                <li><a href="${pageContext.request.contextPath}/contact">Liên hệ</a></li>
                                <li><a href="${pageContext.request.contextPath}/blog">Blog</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- Customer Service -->
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="footer-section">
                            <h6 class="footer-title">Chăm sóc khách hàng</h6>
                            <ul class="footer-links">
                                <li><a href="${pageContext.request.contextPath}/help">Trung tâm trợ giúp</a></li>
                                <li><a href="${pageContext.request.contextPath}/shipping">Chính sách vận chuyển</a></li>
                                <li><a href="${pageContext.request.contextPath}/returns">Đổi trả hàng</a></li>
                                <li><a href="${pageContext.request.contextPath}/warranty">Bảo hành</a></li>
                                <li><a href="${pageContext.request.contextPath}/size-guide">Hướng dẫn chọn size</a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- Contact Info -->
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="footer-section">
                            <h6 class="footer-title">Liên hệ</h6>
                            <div class="contact-info">
                                <div class="contact-item">
                                    <i class="fas fa-map-marker-alt"></i>
                                    <span>123 Đường ABC, Quận XYZ<br>TP. Hồ Chí Minh, Việt Nam</span>
                                </div>
                                <div class="contact-item">
                                    <i class="fas fa-phone"></i>
                                    <span>1900-1234</span>
                                </div>
                                <div class="contact-item">
                                    <i class="fas fa-envelope"></i>
                                    <span>info@shoestore.com</span>
                                </div>
                                <div class="contact-item">
                                    <i class="fas fa-clock"></i>
                                    <span>8:00 - 22:00 (Thứ 2 - CN)</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer Bottom -->
        <div class="footer-bottom">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <p class="copyright">
                            &copy; 2024 Shoe Store. Tất cả quyền được bảo lưu.
                        </p>
                    </div>
                    <div class="col-md-6">
                        <div class="payment-methods">
                            <span class="payment-text">Phương thức thanh toán:</span>
                            <img src="https://via.placeholder.com/40x25/007bff/ffffff?text=VISA" alt="Visa" class="payment-icon">
                            <img src="https://via.placeholder.com/40x25/ff6b35/ffffff?text=MC" alt="Mastercard" class="payment-icon">
                            <img src="https://via.placeholder.com/40x25/00a86b/ffffff?text=ATM" alt="ATM" class="payment-icon">
                            <img src="https://via.placeholder.com/40x25/0066cc/ffffff?text=COD" alt="COD" class="payment-icon">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>

    <!-- Back to Top Button -->
    <button id="backToTop" class="back-to-top" style="display: none;">
        <i class="fas fa-arrow-up"></i>
    </button>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // Back to top functionality
        window.addEventListener('scroll', function() {
            const backToTop = document.getElementById('backToTop');
            if (window.pageYOffset > 300) {
                backToTop.style.display = 'block';
            } else {
                backToTop.style.display = 'none';
            }
        });

        document.getElementById('backToTop').addEventListener('click', function() {
            window.scrollTo({
                top: 0,
                behavior: 'smooth'
            });
        });

        // Auto hide alerts
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                if (alert.classList.contains('show')) {
                    alert.classList.remove('show');
                    alert.classList.add('fade');
                    setTimeout(function() {
                        alert.remove();
                    }, 300);
                }
            });
        }, 5000);

        // Search functionality
        document.querySelector('.search-input').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                const searchTerm = this.value.trim();
                if (searchTerm) {
                    window.location.href = '${pageContext.request.contextPath}/products?search=' + encodeURIComponent(searchTerm);
                }
            }
        });
    </script>

    <style>
        /* Footer Styles */
        .footer {
            background: linear-gradient(135deg, var(--primary-color) 0%, #34495e 100%);
            color: var(--white);
            margin-top: auto;
        }

        .footer-content {
            padding: 50px 0 30px;
        }

        .footer-title {
            color: var(--white);
            font-weight: 600;
            margin-bottom: 20px;
            font-size: 18px;
        }

        .footer-text {
            color: #bdc3c7;
            line-height: 1.7;
            margin-bottom: 20px;
        }

        .social-links {
            display: flex;
            gap: 15px;
        }

        .social-link {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            background: rgba(255, 255, 255, 0.1);
            color: var(--white);
            border-radius: 50%;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .social-link:hover {
            background: var(--secondary-color);
            color: var(--white);
            transform: translateY(-2px);
        }

        .footer-links {
            list-style: none;
            padding: 0;
        }

        .footer-links li {
            margin-bottom: 10px;
        }

        .footer-links a {
            color: #bdc3c7;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .footer-links a:hover {
            color: var(--white);
            padding-left: 5px;
        }

        .contact-info .contact-item {
            display: flex;
            align-items: flex-start;
            margin-bottom: 15px;
            color: #bdc3c7;
        }

        .contact-info .contact-item i {
            width: 20px;
            margin-right: 10px;
            margin-top: 2px;
            color: var(--secondary-color);
        }

        .footer-bottom {
            background: rgba(0, 0, 0, 0.2);
            padding: 20px 0;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
        }

        .copyright {
            margin: 0;
            color: #bdc3c7;
        }

        .payment-methods {
            text-align: right;
            display: flex;
            align-items: center;
            justify-content: flex-end;
            gap: 10px;
        }

        .payment-text {
            color: #bdc3c7;
            font-size: 14px;
            margin-right: 10px;
        }

        .payment-icon {
            height: 25px;
            border-radius: 4px;
            opacity: 0.8;
            transition: opacity 0.3s ease;
        }

        .payment-icon:hover {
            opacity: 1;
        }

        /* Back to Top Button */
        .back-to-top {
            position: fixed;
            bottom: 30px;
            right: 30px;
            width: 50px;
            height: 50px;
            background: var(--secondary-color);
            color: var(--white);
            border: none;
            border-radius: 50%;
            cursor: pointer;
            z-index: 1000;
            transition: all 0.3s ease;
            box-shadow: var(--shadow);
        }

        .back-to-top:hover {
            background: #2980b9;
            transform: translateY(-2px);
        }

        /* Responsive */
        @media (max-width: 768px) {
            .footer-content {
                padding: 40px 0 20px;
            }
            
            .payment-methods {
                text-align: center;
                margin-top: 15px;
                flex-wrap: wrap;
            }
            
            .back-to-top {
                bottom: 20px;
                right: 20px;
                width: 45px;
                height: 45px;
            }
        }
    </style>

</body>
</html>

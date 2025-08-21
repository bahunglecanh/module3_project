<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${not empty pageTitle}">${pageTitle} - </c:when>
        </c:choose>
        Shoe Store - Cửa hàng giày chất lượng
    </title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    
    <style>
        :root {
            --primary-color: #2c3e50;
            --secondary-color: #3498db;
            --accent-color: #e74c3c;
            --success-color: #27ae60;
            --warning-color: #f39c12;
            --light-gray: #ecf0f1;
            --dark-gray: #7f8c8d;
            --white: #ffffff;
            --shadow: 0 2px 10px rgba(0,0,0,0.1);
            --border-radius: 8px;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background-color: var(--white);
            color: var(--primary-color);
            line-height: 1.6;
        }

        /* Header Styles */
        .main-header {
            background: var(--white);
            box-shadow: var(--shadow);
            position: sticky;
            top: 0;
            z-index: 1000;
        }

        .top-bar {
            background: var(--primary-color);
            color: var(--white);
            padding: 8px 0;
            font-size: 14px;
        }

        .navbar-brand {
            font-weight: 700;
            font-size: 24px;
            color: var(--primary-color) !important;
            text-decoration: none;
        }

        .navbar-brand i {
            color: var(--accent-color);
            margin-right: 8px;
        }

        .navbar-nav .nav-link {
            color: var(--primary-color) !important;
            font-weight: 500;
            padding: 12px 16px !important;
            transition: all 0.3s ease;
            position: relative;
        }

        .navbar-nav .nav-link:hover {
            color: var(--secondary-color) !important;
        }

        .navbar-nav .nav-link::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            width: 0;
            height: 2px;
            background: var(--secondary-color);
            transition: all 0.3s ease;
            transform: translateX(-50%);
        }

        .navbar-nav .nav-link:hover::after {
            width: 80%;
        }

        .btn-primary {
            background: var(--secondary-color);
            border: none;
            padding: 10px 20px;
            border-radius: var(--border-radius);
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-primary:hover {
            background: #2980b9;
            transform: translateY(-1px);
        }

        .btn-outline-primary {
            color: var(--secondary-color);
            border-color: var(--secondary-color);
            padding: 8px 16px;
            border-radius: var(--border-radius);
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-outline-primary:hover {
            background: var(--secondary-color);
            border-color: var(--secondary-color);
            transform: translateY(-1px);
        }

        .user-menu {
            position: relative;
        }

        .user-avatar {
            width: 36px;
            height: 36px;
            border-radius: 50%;
            background: var(--secondary-color);
            color: var(--white);
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .user-avatar:hover {
            background: #2980b9;
            transform: scale(1.05);
        }

        .user-avatar img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 50%;
            display: block;
        }

        .cart-icon {
            position: relative;
            color: var(--primary-color);
            font-size: 20px;
            transition: all 0.3s ease;
        }

        .cart-icon:hover {
            color: var(--secondary-color);
            transform: scale(1.1);
        }

        .cart-badge {
            position: absolute;
            top: -8px;
            right: -8px;
            background: var(--accent-color);
            color: var(--white);
            border-radius: 50%;
            width: 18px;
            height: 18px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 10px;
            font-weight: 600;
        }

        /* Search Bar */
        .search-container {
            position: relative;
            max-width: 400px;
        }

        .search-input {
            border: 2px solid var(--light-gray);
            border-radius: 25px;
            padding: 10px 20px 10px 45px;
            width: 100%;
            transition: all 0.3s ease;
            background: var(--white);
        }

        .search-input:focus {
            outline: none;
            border-color: var(--secondary-color);
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
        }

        .search-icon {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: var(--dark-gray);
        }

        /* Alert Styles */
        .alert {
            border: none;
            border-radius: var(--border-radius);
            padding: 12px 20px;
            margin-bottom: 20px;
        }

        .alert-success {
            background: rgba(39, 174, 96, 0.1);
            color: var(--success-color);
            border-left: 4px solid var(--success-color);
        }

        .alert-danger {
            background: rgba(231, 76, 60, 0.1);
            color: var(--accent-color);
            border-left: 4px solid var(--accent-color);
        }

        .alert-info {
            background: rgba(52, 152, 219, 0.1);
            color: var(--secondary-color);
            border-left: 4px solid var(--secondary-color);
        }

        /* Responsive */
        @media (max-width: 991px) {
            .search-container {
                margin: 15px 0;
            }
            
            .navbar-nav {
                margin-top: 15px;
            }
            
            .user-menu {
                margin-top: 15px;
            }
        }

        @media (max-width: 576px) {
            .top-bar {
                font-size: 12px;
                text-align: center;
            }
            
            .navbar-brand {
                font-size: 20px;
            }
        }
    </style>
</head>
<body>
    <c:if test="${empty currentUser && not empty sessionScope.user}">
        <c:set var="currentUser" value="${sessionScope.user}"/>
    </c:if>
    <c:if test="${empty isLoggedIn}">
        <c:set var="isLoggedIn" value="${not empty currentUser}"/>
    </c:if>
    <c:if test="${empty isAdmin && not empty currentUser}">
        <c:set var="isAdmin" value="${currentUser.admin}"/>
    </c:if>
    <!-- Top Bar -->
    <div class="top-bar">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <small><i class="fas fa-phone me-2"></i>Hotline: 1900-1234</small>
                </div>
                <div class="col-md-6 text-end">
                    <small><i class="fas fa-truck me-2"></i>Miễn phí vận chuyển cho đơn hàng từ 500.000đ</small>
                </div>
            </div>
        </div>
    </div>

    <!-- Main Header -->
    <header class="main-header">
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container">
                <!-- Brand -->
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                    <i class="fas fa-shoe-prints"></i>Shoe Store
                </a>

                <!-- Mobile Toggle -->
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <!-- Navigation -->
                <div class="collapse navbar-collapse" id="navbarNav">
                    <!-- Search Bar -->
                    <div class="search-container mx-auto my-3 my-lg-0">
                        <form method="GET" action="${pageContext.request.contextPath}/products" class="position-relative">
                            <i class="fas fa-search search-icon"></i>
                            <input type="text" 
                                   name="search" 
                                   class="search-input" 
                                   placeholder="Tìm kiếm sản phẩm..."
                                   value="${param.search}"
                                   id="searchInput">
                            <button type="submit" style="display: none;"></button>
                        </form>
                    </div>

                    <!-- Main Navigation -->
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/">
                                <i class="fas fa-home me-1"></i>Trang chủ
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/products">
                                <i class="fas fa-th-large me-1"></i>Sản phẩm
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/about">
                                <i class="fas fa-info-circle me-1"></i>Giới thiệu
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/contact">
                                <i class="fas fa-phone me-1"></i>Liên hệ
                            </a>
                        </li>
                    </ul>

                    <!-- User Menu -->
                    <div class="d-flex align-items-center gap-3">
                        <!-- Shopping Cart -->
                        <a href="${pageContext.request.contextPath}/cart" class="cart-icon text-decoration-none">
                            <i class="fas fa-shopping-cart"></i>
                            <span class="cart-badge">0</span>
                        </a>

                        <!-- User Authentication -->
                        <c:choose>
                            <c:when test="${isLoggedIn}">
                                <!-- Logged in user menu -->
                                <div class="user-menu dropdown">
                                    <div class="user-avatar" data-bs-toggle="dropdown">
                                        <c:choose>
                                            <c:when test="${not empty currentUser.avatarUrl}">
                                                <c:set var="hdrImgSrc" value="${currentUser.avatarUrl}" />
                                                <c:if test="${not fn:startsWith(hdrImgSrc, 'http')}">
                                                    <c:set var="hdrImgSrc" value="${pageContext.request.contextPath}/${hdrImgSrc}" />
                                                </c:if>
                                                <img src="${hdrImgSrc}" alt="Avatar" />
                                            </c:when>
                                            <c:otherwise>
                                                ${currentUser.firstLetter}
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <ul class="dropdown-menu dropdown-menu-end">
                                        <li><h6 class="dropdown-header">Xin chào, ${currentUser.fullName}!</h6></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">
                                            <i class="fas fa-user me-2"></i>Thông tin cá nhân
                                        </a></li>
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/orders">
                                            <i class="fas fa-box me-2"></i>Đơn hàng của tôi
                                        </a></li>
                                        <c:if test="${isAdmin}">
                                            <li><hr class="dropdown-divider"></li>
                                            <li><a class="dropdown-item text-primary" href="${pageContext.request.contextPath}/admin/dashboard">
                                                <i class="fas fa-tachometer-alt me-2"></i>Quản trị
                                            </a></li>
                                        </c:if>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/auth/logout">
                                            <i class="fas fa-sign-out-alt me-2"></i>Đăng xuất
                                        </a></li>
                                    </ul>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <!-- Guest user menu -->
                                <div class="d-flex gap-2">
                                    <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-outline-primary btn-sm">
                                        <i class="fas fa-sign-in-alt me-1"></i>Đăng nhập
                                    </a>
                                    <a href="${pageContext.request.contextPath}/auth/register" class="btn btn-primary btn-sm">
                                        <i class="fas fa-user-plus me-1"></i>Đăng ký
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </nav>
    </header>

    <!-- Alert Messages -->
    <c:if test="${not empty message}">
        <div class="container mt-3">
            <div class="alert alert-info alert-dismissible fade show">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="container mt-3">
            <div class="alert alert-danger alert-dismissible fade show">
                ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty successMessage}">
        <div class="container mt-3">
            <div class="alert alert-success alert-dismissible fade show">
                ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </div>
    </c:if>

    <!-- Search JavaScript -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const searchInput = document.getElementById('searchInput');
            const searchForm = searchInput.closest('form');
            
            // Submit form on Enter key
            searchInput.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    searchForm.submit();
                }
            });
            
            // Submit form when clicking search icon
            const searchIcon = document.querySelector('.search-icon');
            searchIcon.addEventListener('click', function() {
                if (searchInput.value.trim() !== '') {
                    searchForm.submit();
                }
            });
            
            // Make search icon clickable
            searchIcon.style.cursor = 'pointer';
        });
    </script>

    <!-- Main Content Starts Here -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Đơn hàng</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <style>
        body { font-family: 'Inter', sans-serif; background-color: #f8fafc; color: #1e293b; }
        .dashboard-container { display: flex; min-height: 100vh; }

        /* Sidebar */
        .sidebar {
            width: 280px;
            background: #fff;
            border-right: 1px solid #e2e8f0;
            padding: 24px 0;
            position: fixed;
            height: 100vh;
            overflow-y: auto;
        }
        .logo-section { padding: 0 24px 32px; border-bottom: 1px solid #e2e8f0; margin-bottom: 24px; }
        .logo { display: flex; align-items: center; gap: 12px; text-decoration: none; }
        .logo-icon { width: 48px; height: 48px; background: linear-gradient(135deg,#6366f1 0%,#4338ca 100%); border-radius: 12px; display: flex; align-items: center; justify-content: center; color: #fff; font-size: 20px; font-weight: 600; }
        .logo-title { font-size: 24px; font-weight: 700; margin: 0; letter-spacing: -0.5px; color: #1e293b; }
        .logo-subtitle { font-size: 14px; color: #64748b; font-weight: 500; margin: 0; letter-spacing: 2px; text-transform: uppercase; }

        /* Nav Menu */
        .nav-menu { padding: 0 16px; }
        .nav-item { margin-bottom: 4px; }
        .nav-link { display: flex; align-items: center; gap: 12px; padding: 12px 16px; border-radius: 8px; text-decoration: none; color: #64748b; font-weight: 500; transition: all 0.2s ease; }
        .nav-link:hover { background: #f1f5f9; color: #1e293b; }
        .nav-link.active { background: #6366f1; color: #fff; }
        .nav-icon { width: 20px; font-size: 16px; text-align: center; }
        .nav-text { font-size: 15px; }

        /* Main Content */
        .main-content { margin-left: 280px; flex: 1; padding: 32px; }

        /* Table & Badges */
        .table thead th { background-color: #6366f1; color: #fff; }
        .badge-status { padding: 0.5em 0.75em; border-radius: 0.5rem; font-size: 0.875rem; }
        .badge-pending { background-color: #6b7280; color: #fff; }
        .badge-confirmed { background-color: #38bdf8; color: #000; }
        .badge-shipped { background-color: #3b82f6; color: #fff; }
        .badge-delivered { background-color: #22c55e; color: #fff; }
        .badge-cancelled { background-color: #ef4444; color: #fff; }
        .btn-detail { font-size: 0.875rem; }
    </style>
</head>
<body>
<div class="dashboard-container">
    <!-- Sidebar -->
    <aside class="sidebar">
        <!-- Logo Section -->
        <div class="logo-section">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="logo">
                <div class="logo-icon"><i class="fas fa-gem"></i></div>
                <div>
                    <div class="logo-title">ADMIN</div>
                    <div class="logo-subtitle">DASHBOARD</div>
                </div>
            </a>
        </div>

        <!-- Navigation Menu -->
        <nav class="nav-menu">
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link">
                    <span class="nav-icon"><i class="fas fa-home"></i></span>
                    <span class="nav-text">Dashboard</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/listuser" class="nav-link">
                    <span class="nav-icon"><i class="fas fa-users"></i></span>
                    <span class="nav-text">Users</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/products" class="nav-link">
                    <span class="nav-icon"><i class="fas fa-box"></i></span>
                    <span class="nav-text">Products</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/order" class="nav-link active">
                    <span class="nav-icon"><i class="fas fa-shopping-cart"></i></span>
                    <span class="nav-text">Orders</span>
                </a>
            </div>
            <div class="nav-item" style="margin-top: 40px; padding-top: 20px; border-top: 1px solid #e2e8f0;">
                <a href="${pageContext.request.contextPath}/auth/logout" class="nav-link">
                    <span class="nav-icon"><i class="fas fa-sign-out-alt"></i></span>
                    <span class="nav-text">Logout</span>
                </a>
            </div>
        </nav>
    </aside>

    <!-- Main Content -->
    <main class="main-content">
        <header class="mb-4">
            <h2 class="mb-1">Danh sách Đơn hàng</h2>
            <p class="text-muted">Quản lý và theo dõi trạng thái đơn hàng của khách hàng</p>
        </header>

        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                <tr class="text-center">
                    <th>Mã đơn</th>
                    <th>Ngày</th>
                    <th>Khách hàng</th>
                    <th>Email</th>
                    <th>SĐT</th>
                    <th>Địa chỉ</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td class="text-center">${order.orderId}</td>
                        <td class="text-center">${order.orderDate}</td>
                        <td>${order.fullName}</td>
                        <td>${order.email}</td>
                        <td>${order.phone}</td>
                        <td>${order.addressLine}, ${order.city}, ${order.state}, ${order.postalCode}, ${order.country}</td>
                        <td class="text-end text-danger fw-bold">${order.totalAmount} đ</td>
                        <td class="text-center">
                            <span class="badge badge-status
                                <c:choose>
                                    <c:when test="${order.orderStatus == 'pending'}">badge-pending</c:when>
                                    <c:when test="${order.orderStatus == 'confirmed'}">badge-confirmed</c:when>
                                    <c:when test="${order.orderStatus == 'shipped'}">badge-shipped</c:when>
                                    <c:when test="${order.orderStatus == 'delivered'}">badge-delivered</c:when>
                                    <c:when test="${order.orderStatus == 'cancelled'}">badge-cancelled</c:when>
                                    <c:otherwise>badge-pending</c:otherwise>
                                </c:choose>
                            ">
                                    ${order.orderStatus}
                            </span>
                        </td>
                        <td class="text-center">
                            <a href="<c:url value='/admin/order/detail?id=${order.orderId}' />" class="btn btn-primary btn-sm btn-detail">Xem chi tiết</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

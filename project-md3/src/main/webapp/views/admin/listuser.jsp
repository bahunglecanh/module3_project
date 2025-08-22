<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VENUS - Quản lý Users</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <style>
        :root {
            --primary-purple: #6366f1;
            --light-purple: #a5b4fc;
            --dark-purple: #4338ca;
            --bg-gray: #f8fafc;
            --text-gray: #64748b;
            --text-dark: #1e293b;
            --white: #ffffff;
            --border-gray: #e2e8f0;
            --success-green: #10b981;
            --danger-red: #ef4444;
        }

        body {
            font-family: 'Inter', sans-serif;
            background-color: var(--bg-gray);
            color: var(--text-dark);
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .page-header {
            background: var(--white);
            border: 1px solid var(--border-gray);
            border-radius: 12px;
            padding: 24px;
            margin-bottom: 24px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        .page-title {
            font-size: 28px;
            font-weight: 700;
            color: var(--text-dark);
            margin: 0 0 8px 0;
        }

        .page-subtitle {
            color: var(--text-gray);
            font-size: 16px;
            margin: 0;
        }

        .search-section {
            background: var(--white);
            border: 1px solid var(--border-gray);
            border-radius: 12px;
            padding: 24px;
            margin-bottom: 24px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        .search-form {
            display: flex;
            gap: 12px;
            align-items: center;
        }

        .search-input {
            flex: 1;
            padding: 12px 16px;
            border: 1px solid var(--border-gray);
            border-radius: 8px;
            font-size: 15px;
        }

        .search-input:focus {
            outline: none;
            border-color: var(--primary-purple);
            box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
        }

        .btn-search {
            background: var(--primary-purple);
            color: var(--white);
            border: none;
            padding: 12px 20px;
            border-radius: 8px;
            font-weight: 500;
            cursor: pointer;
        }

        .btn-search:hover {
            background: var(--dark-purple);
        }

        .btn-clear {
            background: var(--text-gray);
            color: var(--white);
            border: none;
            padding: 12px 16px;
            border-radius: 8px;
            font-weight: 500;
            cursor: pointer;
            text-decoration: none;
        }

        .btn-clear:hover {
            background: var(--text-dark);
            color: var(--white);
            text-decoration: none;
        }

        .table-section {
            background: var(--white);
            border: 1px solid var(--border-gray);
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        .table-header {
            padding: 20px 24px;
            border-bottom: 1px solid var(--border-gray);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .table-title {
            font-size: 18px;
            font-weight: 600;
            color: var(--text-dark);
            margin: 0;
        }

        .table-stats {
            color: var(--text-gray);
            font-size: 14px;
        }

        .user-table {
            width: 100%;
            border-collapse: collapse;
        }

        .user-table th {
            background: var(--bg-gray);
            padding: 16px 24px;
            text-align: left;
            font-weight: 600;
            color: var(--text-dark);
            font-size: 14px;
            text-transform: uppercase;
            border-bottom: 1px solid var(--border-gray);
        }

        .user-table td {
            padding: 16px 24px;
            border-bottom: 1px solid var(--border-gray);
            vertical-align: middle;
        }

        .user-table tbody tr:hover {
            background: #f8fafc;
        }

        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: linear-gradient(135deg, var(--primary-purple) 0%, var(--dark-purple) 100%);
            color: var(--white);
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 600;
            font-size: 16px;
        }

        .status-badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
        }

        .status-active {
            background: rgba(16, 185, 129, 0.1);
            color: var(--success-green);
        }

        .status-inactive {
            background: rgba(239, 68, 68, 0.1);
            color: var(--danger-red);
        }

        .role-badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
        }

        .role-admin {
            background: rgba(245, 158, 11, 0.1);
            color: #f59e0b;
        }

        .role-user {
            background: rgba(99, 102, 241, 0.1);
            color: var(--primary-purple);
        }

        .btn-delete {
            width: 32px;
            height: 32px;
            border: none;
            border-radius: 6px;
            background: rgba(239, 68, 68, 0.1);
            color: var(--danger-red);
            cursor: pointer;
            font-size: 14px;
        }

        .btn-delete:hover {
            background: var(--danger-red);
            color: var(--white);
        }

        .alert {
            padding: 16px 20px;
            border-radius: 8px;
            margin-bottom: 24px;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .alert-success {
            background: rgba(16, 185, 129, 0.1);
            color: var(--success-green);
            border: 1px solid rgba(16, 185, 129, 0.2);
        }

        .alert-danger {
            background: rgba(239, 68, 68, 0.1);
            color: var(--danger-red);
            border: 1px solid rgba(239, 68, 68, 0.2);
        }

        .alert-info {
            background: rgba(99, 102, 241, 0.1);
            color: var(--primary-purple);
            border: 1px solid rgba(99, 102, 241, 0.2);
        }

        .empty-state {
            text-align: center;
            padding: 48px;
            color: var(--text-gray);
        }

        .empty-icon {
            font-size: 48px;
            margin-bottom: 16px;
            color: var(--border-gray);
        }



        .nav-links {
            margin-bottom: 20px;
        }

        .nav-links a {
            color: var(--primary-purple);
            text-decoration: none;
            margin-right: 15px;
        }

        .nav-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Navigation Links -->
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/admin/dashboard">
                <i class="fas fa-home"></i> Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/admin/listuser">
                <i class="fas fa-users"></i> Users
            </a>
        </div>

        <!-- Page Header -->
        <div class="page-header">
            <h1 class="page-title">Quản lý Users</h1>
            <p class="page-subtitle">Danh sách tất cả người dùng trong hệ thống (sử dụng UserDTO với JOIN query)</p>
        </div>

        <!-- Alerts -->

        <c:if test="${errorMessage != null}">
            <div class="alert alert-danger">
                <i class="fas fa-exclamation-circle"></i>
                ${errorMessage}
            </div>
        </c:if>

        <c:if test="${infoMessage != null}">
            <div class="alert alert-info">
                <i class="fas fa-info-circle"></i>
                ${infoMessage}
            </div>
        </c:if>

        <!-- Search Section -->
        <div class="search-section">
            <form action="${pageContext.request.contextPath}/admin/listuser" method="get" class="search-form">
                <input type="hidden" name="action" value="search">
                <input type="text" name="search" class="search-input"
                       placeholder="Tìm kiếm theo tên hoặc email..."
                       value="${searchTerm}">
                <button type="submit" class="btn-search">
                    <i class="fas fa-search"></i> Tìm kiếm
                </button>
                <a href="${pageContext.request.contextPath}/admin/listuser" class="btn-clear">
                    <i class="fas fa-times"></i> Xóa
                </a>
            </form>
        </div>

        <!-- Table Section -->
        <div class="table-section">
            <div class="table-header">
                <h5 class="table-title">
                    <c:choose>
                        <c:when test="${searchTerm != null}">
                            Kết quả tìm kiếm: "${searchTerm}"
                        </c:when>
                        <c:otherwise>
                            Danh sách Users (UserDTO)
                        </c:otherwise>
                    </c:choose>
                </h5>
                <div class="table-stats">
                    <c:choose>
                        <c:when test="${totalRecords > 0}">
                            Tổng cộng: ${totalRecords} users
                        </c:when>
                        <c:otherwise>
                            Không có dữ liệu
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <c:choose>
                <c:when test="${users != null && users.size() > 0}">
                    <table class="user-table">
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Status</th>
                                <th>Phone</th>
                                <th>Gender</th>
                                <th>Ngày tạo</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>
                                        <div style="display: flex; align-items: center; gap: 12px;">
                                            <div class="user-avatar">
                                                <c:choose>
                                                    <c:when test="${user.fullName != null && !user.fullName.isEmpty()}">
                                                        ${user.fullName.substring(0, 1).toUpperCase()}
                                                    </c:when>
                                                    <c:when test="${user.email != null && !user.email.isEmpty()}">
                                                        ${user.email.substring(0, 1).toUpperCase()}
                                                    </c:when>
                                                    <c:otherwise>U</c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div>
                                                <div style="font-weight: 600; color: var(--text-dark);">
                                                    <c:choose>
                                                        <c:when test="${user.fullName != null && !user.fullName.trim().isEmpty()}">
                                                            ${user.fullName}
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${user.email}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="color: var(--text-dark); font-weight: 500;">
                                            ${user.email}
                                        </div>
                                    </td>
                                    <td>
                                        <span class="role-badge ${user.role == 'ADMIN' ? 'role-admin' : 'role-user'}">
                                            ${user.role}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="status-badge ${user.status == 'ACTIVE' ? 'status-active' : 'status-inactive'}">
                                            ${user.status == 'ACTIVE' ? 'Hoạt động' : 'Không hoạt động'}
                                        </span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.phone != null}">
                                                ${user.phone}
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: var(--text-gray); font-style: italic;">Chưa có</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.gender != null}">
                                                ${user.gender}
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: var(--text-gray); font-style: italic;">Chưa có</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${user.createdAt != null}">
                                            <fmt:formatDate value="${user.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <div class="empty-icon">
                            <i class="fas fa-users"></i>
                        </div>
                        <h3>Không có users</h3>
                        <p>
                            <c:choose>
                                <c:when test="${searchTerm != null}">
                                    Không tìm thấy user nào với từ khóa "${searchTerm}"
                                </c:when>
                                <c:otherwise>
                                    Chưa có user nào trong hệ thống
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        // Auto hide alerts after 5 seconds
        document.addEventListener('DOMContentLoaded', function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                setTimeout(function() {
                    alert.style.opacity = '0';
                    setTimeout(function() {
                        alert.remove();
                    }, 300);
                }, 5000);
            });
        });
    </script>
</body>
</html>
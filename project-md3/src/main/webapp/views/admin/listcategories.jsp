<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VENUS - Quản lý Danh mục</title>

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
            --warning-orange: #f59e0b;
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

        .btn-add {
            background: var(--success-green);
            color: var(--white);
            border: none;
            padding: 12px 20px;
            border-radius: 8px;
            font-weight: 500;
            cursor: pointer;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .btn-add:hover {
            background: #059669;
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

        .category-table {
            width: 100%;
            border-collapse: collapse;
        }

        .category-table th {
            background: var(--bg-gray);
            padding: 16px 24px;
            text-align: left;
            font-weight: 600;
            color: var(--text-dark);
            font-size: 14px;
            text-transform: uppercase;
            border-bottom: 1px solid var(--border-gray);
        }

        .category-table td {
            padding: 16px 24px;
            border-bottom: 1px solid var(--border-gray);
            vertical-align: middle;
        }

        .category-table tbody tr:hover {
            background: #f8fafc;
        }

        .category-name {
            font-weight: 600;
            color: var(--text-dark);
            margin-bottom: 4px;
        }

        .category-description {
            font-size: 13px;
            color: var(--text-gray);
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .btn-action {
            padding: 6px 12px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 12px;
            font-weight: 500;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 4px;
            margin-right: 6px;
        }

        .btn-edit {
            background: rgba(245, 158, 11, 0.1);
            color: var(--warning-orange);
        }

        .btn-edit:hover {
            background: var(--warning-orange);
            color: var(--white);
        }

        .btn-delete {
            background: rgba(239, 68, 68, 0.1);
            color: var(--danger-red);
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
            <a href="${pageContext.request.contextPath}/admin/products">
                <i class="fas fa-box"></i> Products
            </a>
            <a href="${pageContext.request.contextPath}/admin/categories">
                <i class="fas fa-tags"></i> Categories
            </a>
        </div>

        <!-- Page Header -->
        <div class="page-header">
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <h1 class="page-title">Quản lý Danh mục</h1>
                <a href="${pageContext.request.contextPath}/admin/categories?action=add" class="btn-add">
                    <i class="fas fa-plus"></i> Thêm danh mục
                </a>
            </div>
        </div>



        <!-- Table Section -->
        <div class="table-section">
            <div class="table-header">
                <h5 class="table-title">Danh sách Danh mục</h5>
                <div class="table-stats">
                    ${categories.size()} danh mục
                </div>
            </div>

            <c:choose>
                <c:when test="${categories != null && categories.size() > 0}">
                    <table class="category-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên danh mục</th>
                                <th>Mô tả</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="category" items="${categories}">
                                <tr>
                                    <td>
                                        <div style="font-weight: 600; color: var(--text-gray);">
                                            #${category.id}
                                        </div>
                                    </td>
                                    <td>
                                        <div class="category-name">${category.name}</div>
                                    </td>
                                    <td>
                                        <div class="category-description">${category.description}</div>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/categories?action=edit&id=${category.id}" 
                                           class="btn-action btn-edit">
                                            <i class="fas fa-edit"></i> Sửa
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/categories?action=delete&id=${category.id}" 
                                           class="btn-action btn-delete"
                                           onclick="return confirm('Bạn có chắc muốn xóa danh mục này? Chỉ có thể xóa khi danh mục không có sản phẩm nào.')">
                                            <i class="fas fa-trash"></i> Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <div class="empty-icon">
                            <i class="fas fa-tags"></i>
                        </div>
                        <h3>Không có danh mục</h3>
                        <p>Chưa có danh mục nào trong hệ thống</p>
                        <a href="${pageContext.request.contextPath}/admin/categories?action=add" class="btn-add">
                            <i class="fas fa-plus"></i> Thêm danh mục đầu tiên
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        // No validation
    </script>
</body>
</html>

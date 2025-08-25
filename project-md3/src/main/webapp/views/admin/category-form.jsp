<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VENUS - ${pageTitle}</title>

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
            max-width: 800px;
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
            margin: 0;
        }

        .form-section {
            background: var(--white);
            border: 1px solid var(--border-gray);
            border-radius: 12px;
            padding: 32px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        .form-label {
            font-weight: 600;
            color: var(--text-dark);
            margin-bottom: 8px;
        }

        .form-control, .form-select {
            border: 2px solid var(--border-gray);
            border-radius: 8px;
            padding: 12px 16px;
            font-size: 14px;
            transition: all 0.2s;
        }

        .form-control:focus, .form-select:focus {
            border-color: var(--primary-purple);
            box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
        }

        .btn-primary {
            background: var(--primary-purple);
            border-color: var(--primary-purple);
            color: var(--white);
            padding: 12px 24px;
            border-radius: 8px;
            font-weight: 600;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .btn-primary:hover {
            background: var(--dark-purple);
            border-color: var(--dark-purple);
        }

        .btn-secondary {
            background: var(--text-gray);
            border-color: var(--text-gray);
            color: var(--white);
            padding: 12px 24px;
            border-radius: 8px;
            font-weight: 600;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            text-decoration: none;
        }

        .btn-secondary:hover {
            background: #475569;
            border-color: #475569;
            color: var(--white);
            text-decoration: none;
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

        .form-buttons {
            margin-top: 32px;
            padding-top: 24px;
            border-top: 1px solid var(--border-gray);
            display: flex;
            gap: 16px;
        }

        .form-text {
            color: var(--text-gray);
            font-size: 13px;
            margin-top: 4px;
        }

        .required {
            color: var(--danger-red);
        }

        .form-icon {
            color: var(--primary-purple);
            font-size: 48px;
            margin-bottom: 24px;
            text-align: center;
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
            <h1 class="page-title">${pageTitle}</h1>
        </div>



        <!-- Form Section -->
        <div class="form-section">
            <form method="post" action="${pageContext.request.contextPath}/admin/categories">
                <input type="hidden" name="action" value="${action}">
                <c:if test="${action == 'edit'}">
                    <input type="hidden" name="id" value="${category.id}">
                </c:if>

                <!-- Category Name -->
                <div class="mb-3">
                    <label for="name" class="form-label">Tên danh mục</label>
                    <input type="text" class="form-control" id="name" name="name" 
                           value="${category.name}" placeholder="Nhập tên danh mục">
                </div>

                <!-- Description -->
                <div class="mb-3">
                    <label for="description" class="form-label">Mô tả</label>
                    <textarea class="form-control" id="description" name="description" 
                              rows="3" placeholder="Nhập mô tả">${category.description}</textarea>
                </div>

                <!-- Form Buttons -->
                <div class="form-buttons">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Lưu
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-secondary">
                        <i class="fas fa-times"></i> Hủy
                    </a>
                </div>
            </form>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // Basic functionality only
    </script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VENUS DASHBOARD</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    
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
            --hover-gray: #f1f5f9;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background-color: var(--bg-gray);
            color: var(--text-dark);
            line-height: 1.6;
        }

        /* Layout Container */
        .dashboard-container {
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar */
        .sidebar {
            width: 280px;
            background: var(--white);
            border-right: 1px solid var(--border-gray);
            padding: 24px 0;
            position: fixed;
            height: 100vh;
            overflow-y: auto;
            z-index: 1000;
        }

        /* Logo Section */
        .logo-section {
            padding: 0 24px 32px;
            border-bottom: 1px solid var(--border-gray);
            margin-bottom: 24px;
        }

        .logo {
            display: flex;
            align-items: center;
            gap: 12px;
            text-decoration: none;
        }

        .logo-icon {
            width: 48px;
            height: 48px;
            background: linear-gradient(135deg, var(--primary-purple) 0%, var(--dark-purple) 100%);
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: var(--white);
            font-size: 20px;
            font-weight: 600;
        }

        .logo-text {
            color: var(--text-dark);
        }

        .logo-title {
            font-size: 24px;
            font-weight: 700;
            margin: 0;
            letter-spacing: -0.5px;
        }

        .logo-subtitle {
            font-size: 14px;
            color: var(--text-gray);
            font-weight: 500;
            margin: 0;
            letter-spacing: 2px;
            text-transform: uppercase;
        }

        /* Navigation Menu */
        .nav-menu {
            padding: 0 16px;
        }

        .nav-item {
            margin-bottom: 4px;
        }

        .nav-link {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 16px;
            border-radius: 8px;
            text-decoration: none;
            color: var(--text-gray);
            font-weight: 500;
            transition: all 0.2s ease;
            position: relative;
        }

        .nav-link:hover {
            background: var(--hover-gray);
            color: var(--text-dark);
        }

        .nav-link.active {
            background: var(--primary-purple);
            color: var(--white);
        }

        .nav-icon {
            width: 20px;
            font-size: 16px;
            text-align: center;
        }

        .nav-text {
            font-size: 15px;
        }

        /* Main Content */
        .main-content {
            margin-left: 280px;
            flex: 1;
            padding: 32px;
        }

        /* Header */
        .content-header {
            margin-bottom: 32px;
        }

        .page-title {
            font-size: 32px;
            font-weight: 700;
            color: var(--text-dark);
            margin: 0;
            letter-spacing: -0.5px;
        }

        .page-subtitle {
            color: var(--text-gray);
            margin-top: 4px;
            font-size: 16px;
        }

        /* Cards */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 24px;
            margin-bottom: 32px;
        }

        .stat-card {
            background: var(--white);
            border: 1px solid var(--border-gray);
            border-radius: 12px;
            padding: 24px;
            transition: all 0.2s ease;
        }

        .stat-card:hover {
            box-shadow: 0 4px 12px rgba(99, 102, 241, 0.1);
            border-color: var(--light-purple);
        }

        .stat-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 16px;
        }

        .stat-title {
            font-size: 14px;
            font-weight: 600;
            color: var(--text-gray);
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin: 0;
        }

        .stat-icon {
            width: 40px;
            height: 40px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            color: var(--white);
        }

        .stat-icon.users {
            background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
        }

        .stat-icon.products {
            background: linear-gradient(135deg, #10b981 0%, #047857 100%);
        }

        .stat-icon.orders {
            background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
        }

        .stat-icon.activity {
            background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
        }

        .stat-number {
            font-size: 28px;
            font-weight: 700;
            color: var(--text-dark);
            margin-bottom: 4px;
        }

        .stat-description {
            font-size: 14px;
            color: var(--text-gray);
        }

        /* Content Grid */
        .content-grid {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 24px;
        }

        .content-card {
            background: var(--white);
            border: 1px solid var(--border-gray);
            border-radius: 12px;
            overflow: hidden;
        }

        .card-header {
            padding: 20px 24px;
            border-bottom: 1px solid var(--border-gray);
        }

        .card-title {
            font-size: 18px;
            font-weight: 600;
            color: var(--text-dark);
            margin: 0;
        }

        .card-body {
            padding: 24px;
        }

        /* Activity List */
        .activity-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .activity-item {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 0;
            border-bottom: 1px solid var(--border-gray);
        }

        .activity-item:last-child {
            border-bottom: none;
        }

        .activity-icon {
            width: 32px;
            height: 32px;
            border-radius: 6px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
            color: var(--white);
            background: var(--primary-purple);
        }

        .activity-content {
            flex: 1;
        }

        .activity-title {
            font-size: 14px;
            font-weight: 500;
            color: var(--text-dark);
            margin: 0;
        }

        .activity-time {
            font-size: 12px;
            color: var(--text-gray);
        }

        /* Quick Actions */
        .quick-actions {
            display: grid;
            gap: 12px;
        }

        .quick-action {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 16px;
            background: var(--hover-gray);
            border-radius: 8px;
            text-decoration: none;
            color: var(--text-dark);
            transition: all 0.2s ease;
        }

        .quick-action:hover {
            background: var(--primary-purple);
            color: var(--white);
            transform: translateY(-1px);
        }

        .quick-action-icon {
            width: 32px;
            height: 32px;
            border-radius: 6px;
            background: var(--primary-purple);
            color: var(--white);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
        }

        .quick-action:hover .quick-action-icon {
            background: var(--white);
            color: var(--primary-purple);
        }

        .quick-action-text {
            font-size: 14px;
            font-weight: 500;
        }

        /* Responsive */
        @media (max-width: 1024px) {
            .content-grid {
                grid-template-columns: 1fr;
            }
        }

        @media (max-width: 768px) {
            .sidebar {
                transform: translateX(-100%);
            }

            .main-content {
                margin-left: 0;
                padding: 20px;
            }

            .stats-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <aside class="sidebar">
            <!-- Logo Section -->
            <div class="logo-section">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="logo">
                    <div class="logo-icon">
                        <i class="fas fa-gem"></i>
                    </div>
                    <div class="logo-text">
                        <div class="logo-title">ADMIN</div>
                        <div class="logo-subtitle">DASHBOARD</div>
                    </div>
                </a>
            </div>

            <!-- Navigation Menu -->
            <nav class="nav-menu">
                <div class="nav-item">
                    <a href="/views/home.jsp" class="nav-link active">
                        <span class="nav-icon"><i class="fas fa-home"></i></span>
                        <span class="nav-text">Home</span>
                    </a>
                </div>

                <div class="nav-item">
                    <a href="/admin/listuser" class="nav-link">
                        <span class="nav-icon"><i class="fas fa-users"></i></span>
                        <span class="nav-text">User</span>
                    </a>
                </div>

                <div class="nav-item">
                    <a href="/admin/order" class="nav-link">
                        <span class="nav-icon"><i class="fas fa-box"></i></span>
                        <span class="nav-text">View Orders</span>
                    </a>
                </div>

                <div class="nav-item">
                    <a href="${pageContext.request.contextPath}/admin/cart" class="nav-link">
                        <span class="nav-icon"><i class="fas fa-shopping-cart"></i></span>
                        <span class="nav-text">Giỏ hàng</span>
                    </a>
                </div>

                <!-- Logout -->
                <div class="nav-item" style="margin-top: 40px; padding-top: 20px; border-top: 1px solid var(--border-gray);">
                    <a href="${pageContext.request.contextPath}/auth/logout" class="nav-link">
                        <span class="nav-icon"><i class="fas fa-sign-out-alt"></i></span>
                        <span class="nav-text">Logout</span>
                    </a>
                </div>
            </nav>
        </aside>

        <!-- Main Content -->
        <main class="main-content">
            <!-- Header -->
            <header class="content-header">
                <h1 class="page-title">Welcome back, Admin!</h1>
                <p class="page-subtitle">Here's what's happening with your system today.</p>
            </header>

            <!-- Statistics Cards -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-header">
                        <h6 class="stat-title">Total Users</h6>
                        <div class="stat-icon users">
                            <i class="fas fa-users"></i>
                        </div>
                    </div>
                    <div class="stat-number">${stats.totalUsers != null ? stats.totalUsers : 0}</div>
                    <div class="stat-description">Active system users</div>
                </div>

                <div class="stat-card">
                    <div class="stat-header">
                        <h6 class="stat-title">Total Products</h6>
                        <div class="stat-icon products">
                            <i class="fas fa-box"></i>
                        </div>
                    </div>
                    <div class="stat-number">${stats.totalProducts != null ? stats.totalProducts : 0}</div>
                    <div class="stat-description">Products in inventory</div>
                </div>

                <div class="stat-card">
                    <div class="stat-header">
                        <h6 class="stat-title">Total Orders</h6>
                        <div class="stat-icon orders">
                            <i class="fas fa-shopping-cart"></i>
                        </div>
                    </div>
                    <div class="stat-number">${stats.totalOrders != null ? stats.totalOrders : 0}</div>
                    <div class="stat-description">Customer orders</div>
                </div>

                <div class="stat-card">
                    <div class="stat-header">
                        <h6 class="stat-title">System Activity</h6>
                        <div class="stat-icon activity">
                            <i class="fas fa-chart-line"></i>
                        </div>
                    </div>
                    <div class="stat-number">98%</div>
                    <div class="stat-description">System performance</div>
                </div>
            </div>

            <!-- Content Grid -->
            <div class="content-grid">
                <!-- Recent Activity -->
                <div class="content-card">
                    <div class="card-header">
                        <h5 class="card-title">Recent Activity</h5>
                    </div>
                    <div class="card-body">
                        <ul class="activity-list">
                            <li class="activity-item">
                                <div class="activity-icon">
                                    <i class="fas fa-user"></i>
                                </div>
                                <div class="activity-content">
                                    <div class="activity-title">New user registered</div>
                                    <div class="activity-time">2 hours ago</div>
                                </div>
                            </li>
                            <li class="activity-item">
                                <div class="activity-icon">
                                    <i class="fas fa-box"></i>
                                </div>
                                <div class="activity-content">
                                    <div class="activity-title">New product added</div>
                                    <div class="activity-time">5 hours ago</div>
                                </div>
                            </li>
                            <li class="activity-item">
                                <div class="activity-icon">
                                    <i class="fas fa-shopping-cart"></i>
                                </div>
                                <div class="activity-content">
                                    <div class="activity-title">Order completed</div>
                                    <div class="activity-time">1 day ago</div>
                                </div>
                            </li>
                            <li class="activity-item">
                                <div class="activity-icon">
                                    <i class="fas fa-cog"></i>
                                </div>
                                <div class="activity-content">
                                    <div class="activity-title">System updated</div>
                                    <div class="activity-time">2 days ago</div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>

                <!-- Quick Actions -->
                <div class="content-card">
                    <div class="card-header">
                        <h5 class="card-title">Quick Actions</h5>
                    </div>
                    <div class="card-body">
                        <div class="quick-actions">
                            <a href="${pageContext.request.contextPath}/admin/users" class="quick-action">
                                <div class="quick-action-icon">
                                    <i class="fas fa-users"></i>
                                </div>
                                <div class="quick-action-text">Manage Users</div>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/products" class="quick-action">
                                <div class="quick-action-icon">
                                    <i class="fas fa-box"></i>
                                </div>
                                <div class="quick-action-text">Manage Products</div>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/orders" class="quick-action">
                                <div class="quick-action-icon">
                                    <i class="fas fa-shopping-cart"></i>
                                </div>
                                <div class="quick-action-text">View Orders</div>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/categories" class="quick-action">
                                <div class="quick-action-icon">
                                    <i class="fas fa-tags"></i>
                                </div>
                                <div class="quick-action-text">Categories</div>
                            </a>
                            <a href="${pageContext.request.contextPath}/" class="quick-action">
                                <div class="quick-action-icon">
                                    <i class="fas fa-globe"></i>
                                </div>
                                <div class="quick-action-text">View Website</div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
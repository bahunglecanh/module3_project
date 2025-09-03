<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Chi tiết Đơn hàng #${order.orderId}</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <style>
    body { 
      font-family: 'Inter', sans-serif; 
      background-color: #f8fafc; 
      color: #1e293b; 
      margin: 0;
      padding: 0;
    }
    
    .dashboard-container { 
      display: flex; 
      min-height: 100vh; 
    }

    /* Sidebar */
    .sidebar {
      width: 280px;
      background: #fff;
      border-right: 1px solid #e2e8f0;
      padding: 24px 0;
      position: fixed;
      height: 100vh;
      overflow-y: auto;
      z-index: 1000;
    }
    
    .logo-section { 
      padding: 0 24px 32px; 
      border-bottom: 1px solid #e2e8f0; 
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
      background: linear-gradient(135deg,#6366f1 0%,#4338ca 100%); 
      border-radius: 12px; 
      display: flex; 
      align-items: center; 
      justify-content: center; 
      color: #fff; 
      font-size: 20px; 
      font-weight: 600; 
    }
    
    .logo-title { 
      font-size: 24px; 
      font-weight: 700; 
      margin: 0; 
      letter-spacing: -0.5px; 
      color: #1e293b; 
    }
    
    .logo-subtitle { 
      font-size: 14px; 
      color: #64748b; 
      font-weight: 500; 
      margin: 0; 
      letter-spacing: 2px; 
      text-transform: uppercase; 
    }

    /* Nav Menu */
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
      color: #64748b; 
      font-weight: 500; 
      transition: all 0.2s ease; 
    }
    
    .nav-link:hover { 
      background: #f1f5f9; 
      color: #1e293b; 
      text-decoration: none;
    }
    
    .nav-link.active { 
      background: #6366f1; 
      color: #fff; 
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
      background-color: #f8fafc;
      min-height: 100vh;
    }

    /* Page Header */
    .page-header {
      background: #fff;
      padding: 24px 32px;
      margin: -32px -32px 32px -32px;
      border-bottom: 1px solid #e2e8f0;
      box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1);
    }

    .page-title {
      font-size: 28px;
      font-weight: 700;
      color: #1e293b;
      margin: 0;
    }

    .page-subtitle {
      color: #64748b;
      margin: 8px 0 0 0;
      font-size: 16px;
    }

    /* Cards */
    .card {
      border: none;
      border-radius: 12px;
      box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
      margin-bottom: 24px;
    }

    .card-header {
      background: linear-gradient(135deg, #6366f1 0%, #4338ca 100%);
      color: #fff;
      border: none;
      border-radius: 12px 12px 0 0;
      padding: 16px 24px;
      font-weight: 600;
    }

    .card-body {
      padding: 24px;
    }

    /* Table & Badges */
    .table {
      margin-bottom: 0;
    }
    
    .table thead th { 
      background-color: #f8fafc; 
      color: #374151;
      border-bottom: 2px solid #e5e7eb;
      font-weight: 600;
      padding: 16px 12px;
    }
    
    .table tbody td {
      padding: 16px 12px;
      vertical-align: middle;
    }
    
    .badge-status { 
      padding: 0.5em 0.75em; 
      border-radius: 0.5rem; 
      font-size: 0.875rem; 
      font-weight: 500;
    }
    
    .badge-pending { background-color: #6b7280; color: #fff; }
    .badge-confirmed { background-color: #38bdf8; color: #000; }
    .badge-shipped { background-color: #3b82f6; color: #fff; }
    .badge-delivered { background-color: #22c55e; color: #fff; }
    .badge-cancelled { background-color: #ef4444; color: #fff; }
    
    .btn-detail { 
      font-size: 0.875rem; 
    }

    /* Customer Info */
    .customer-info p {
      margin-bottom: 12px;
      padding: 8px 0;
      border-bottom: 1px solid #f1f5f9;
    }

    .customer-info p:last-child {
      border-bottom: none;
    }

    .customer-info strong {
      color: #374151;
      min-width: 120px;
      display: inline-block;
    }

    /* Product Image */
    .product-image {
      width: 80px;
      height: 80px;
      object-fit: cover;
      border-radius: 8px;
      border: 1px solid #e5e7eb;
    }

    /* Status Badge */
    .status-badge {
      padding: 8px 16px;
      border-radius: 20px;
      font-weight: 600;
      font-size: 14px;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    /* Buttons */
    .btn {
      border-radius: 8px;
      font-weight: 500;
      padding: 10px 20px;
      transition: all 0.2s ease;
    }

    .btn:hover {
      transform: translateY(-1px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    }

    /* Form */
    .form-select, .form-control {
      border-radius: 8px;
      border: 1px solid #d1d5db;
      padding: 10px 16px;
    }

    .form-select:focus, .form-control:focus {
      border-color: #6366f1;
      box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
    }

    /* Alert */
    .alert {
      border-radius: 8px;
      border: none;
      padding: 16px 20px;
    }
  </style>
  <script>
    function showStatusForm() {
      document.getElementById('statusForm').style.display = 'block';
      document.getElementById('editBtn').style.display = 'none';
    }
  </script>
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
    <!-- Page Header -->
    <div class="page-header">
      <h1 class="page-title">Chi tiết Đơn hàng #${order.orderId}</h1>
      <p class="page-subtitle">Quản lý và cập nhật trạng thái đơn hàng</p>
    </div>

    <c:if test="${not empty errorMessage}">
      <div class="alert alert-danger">
        <i class="fas fa-exclamation-triangle me-2"></i>
        ${errorMessage}
      </div>
    </c:if>

    <!-- Thông tin khách hàng -->
    <div class="card">
      <div class="card-header">
        <i class="fas fa-user me-2"></i>
        Thông tin khách hàng
      </div>
      <div class="card-body customer-info">
        <p><strong>Họ tên:</strong> ${order.fullName}</p>
        <p><strong>Email:</strong> ${order.email}</p>
        <p><strong>SĐT:</strong> ${order.phone}</p>
        <p><strong>Địa chỉ giao hàng:</strong> ${order.addressLine}, ${order.city}, ${order.state}, ${order.postalCode}, ${order.country}</p>
      </div>
    </div>

    <!-- Chi tiết sản phẩm -->
    <div class="card">
      <div class="card-header">
        <i class="fas fa-box me-2"></i>
        Chi tiết sản phẩm
      </div>
      <div class="card-body">
        <div class="table-responsive">
          <table class="table table-hover">
            <thead>
              <tr>
                <th>Ảnh</th>
                <th>Tên sản phẩm</th>
                <th>Size</th>
                <th>Số lượng</th>
                <th>Giá</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="item" items="${order.items}">
                <tr>
                  <td>
                    <img src="${item.imageUrl}" alt="${item.productName}" class="product-image">
                  </td>
                  <td><strong>${item.productName}</strong></td>
                  <td><span class="badge bg-light text-dark">${item.size}</span></td>
                  <td><span class="badge bg-info">${item.quantity}</span></td>
                  <td><strong class="text-primary">${item.price} đ</strong></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- Tổng tiền và trạng thái -->
    <div class="card">
      <div class="card-header">
        <i class="fas fa-cog me-2"></i>
        Quản lý đơn hàng
      </div>
      <div class="card-body">
        <!-- Tổng tiền -->
        <div class="row mb-4">
          <div class="col-md-6">
            <h5 class="text-muted mb-2">Tổng tiền đơn hàng</h5>
            <h3 class="text-danger fw-bold">${order.totalAmount} đ</h3>
          </div>
          <div class="col-md-6">
            <h5 class="text-muted mb-2">Trạng thái hiện tại</h5>
            <span class="status-badge
              <c:choose>
                <c:when test="${order.orderStatus == 'pending'}">bg-secondary</c:when>
                <c:when test="${order.orderStatus == 'confirmed'}">bg-info text-dark</c:when>
                <c:when test="${order.orderStatus == 'shipped'}">bg-primary</c:when>
                <c:when test="${order.orderStatus == 'delivered'}">bg-success</c:when>
                <c:when test="${order.orderStatus == 'cancelled'}">bg-danger</c:when>
                <c:otherwise>bg-secondary</c:otherwise>
              </c:choose>
            ">
              <c:choose>
                <c:when test="${order.orderStatus == 'pending'}">Chờ xác nhận</c:when>
                <c:when test="${order.orderStatus == 'confirmed'}">Đã xác nhận</c:when>
                <c:when test="${order.orderStatus == 'shipped'}">Đang giao hàng</c:when>
                <c:when test="${order.orderStatus == 'delivered'}">Đã giao hàng</c:when>
                <c:when test="${order.orderStatus == 'cancelled'}">Đã hủy</c:when>
                <c:otherwise>${order.orderStatus}</c:otherwise>
              </c:choose>
            </span>
          </div>
        </div>

        <!-- Nút Edit trạng thái -->
        <div class="d-flex gap-3 mb-4">
          <button id="editBtn" class="btn btn-primary" onclick="showStatusForm()">
            <i class="fas fa-edit me-2"></i>
            Cập nhật trạng thái
          </button>
          <a href="<c:url value='/admin/order' />" class="btn btn-outline-secondary">
            <i class="fas fa-arrow-left me-2"></i>
            Quay lại danh sách
          </a>
        </div>

        <!-- Form cập nhật trạng thái (ẩn ban đầu) -->
        <form id="statusForm" method="post" style="display:none;" class="border-top pt-4">
          <input type="hidden" name="orderId" value="${order.orderId}" />
          <div class="row">
            <div class="col-md-6">
              <div class="mb-3">
                <label for="status" class="form-label fw-bold">Cập nhật trạng thái:</label>
                <select name="status" id="status" class="form-select">
                  <option value="pending" ${order.orderStatus == 'pending' ? 'selected' : ''}>Chờ xác nhận</option>
                  <option value="confirmed" ${order.orderStatus == 'confirmed' ? 'selected' : ''}>Đã xác nhận</option>
                  <option value="shipped" ${order.orderStatus == 'shipped' ? 'selected' : ''}>Đang giao hàng</option>
                  <option value="delivered" ${order.orderStatus == 'delivered' ? 'selected' : ''}>Đã giao hàng</option>
                  <option value="cancelled" ${order.orderStatus == 'cancelled' ? 'selected' : ''}>Đã hủy</option>
                </select>
              </div>
            </div>
            <div class="col-md-6 d-flex align-items-end">
              <div class="d-flex gap-2">
                <button type="submit" class="btn btn-success">
                  <i class="fas fa-save me-2"></i>
                  Cập nhật
                </button>
                <button type="button" class="btn btn-outline-secondary" onclick="document.getElementById('statusForm').style.display='none'; document.getElementById('editBtn').style.display='inline-block';">
                  <i class="fas fa-times me-2"></i>
                  Hủy
                </button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </main>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

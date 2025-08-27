<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Chi tiết Đơn hàng #${order.orderId}</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <script>
    function showStatusForm() {
      document.getElementById('statusForm').style.display = 'block';
      document.getElementById('editBtn').style.display = 'none';
    }
  </script>
</head>
<body class="bg-light">

<div class="container mt-4">
  <h2>Chi tiết Đơn hàng #${order.orderId}</h2>

  <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
  </c:if>

  <!-- Thông tin khách hàng -->
  <div class="card mb-3">
    <div class="card-header bg-secondary text-white">Thông tin khách hàng</div>
    <div class="card-body">
      <p><strong>Họ tên:</strong> ${order.fullName}</p>
      <p><strong>Email:</strong> ${order.email}</p>
      <p><strong>SĐT:</strong> ${order.phone}</p>
      <p><strong>Địa chỉ giao hàng:</strong> ${order.addressLine}, ${order.city}, ${order.state}, ${order.postalCode}, ${order.country}</p>
    </div>
  </div>

  <!-- Chi tiết sản phẩm -->
  <div class="card mb-3">
    <div class="card-header bg-secondary text-white">Chi tiết sản phẩm</div>
    <div class="card-body">
      <table class="table table-bordered table-hover">
        <thead class="table-light">
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
            <td style="width:80px">
              <img src="${item.imageUrl}" alt="${item.productName}" class="img-fluid rounded" style="max-height:60px;">
            </td>
            <td>${item.productName}</td>
            <td>${item.size}</td>
            <td>${item.quantity}</td>
            <td>${item.price} đ</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Tổng tiền và trạng thái -->
  <div class="card mb-3">
    <div class="card-header bg-secondary text-white">Quản lý đơn hàng</div>
    <div class="card-body">
      <!-- Tổng tiền -->
      <p><strong>Tổng tiền:</strong> <span class="text-danger fw-bold">${order.totalAmount} đ</span></p>

      <!-- Trạng thái hiện tại -->
      <p><strong>Trạng thái hiện tại:</strong>
        <span class="badge
          <c:choose>
            <c:when test="${order.orderStatus == 'pending'}">bg-secondary</c:when>
            <c:when test="${order.orderStatus == 'confirmed'}">bg-info text-dark</c:when>
            <c:when test="${order.orderStatus == 'shipped'}">bg-primary</c:when>
            <c:when test="${order.orderStatus == 'delivered'}">bg-success</c:when>
            <c:when test="${order.orderStatus == 'cancelled'}">bg-danger</c:when>
            <c:otherwise>bg-secondary</c:otherwise>
          </c:choose>
        ">
          ${order.orderStatus}
        </span>
      </p>

      <!-- Nút Edit trạng thái -->
      <button id="editBtn" class="btn btn-primary" onclick="showStatusForm()">Edit trạng thái</button>

      <!-- Form cập nhật trạng thái (ẩn ban đầu) -->
      <form id="statusForm" method="post" style="display:none;" class="mt-3">
        <input type="hidden" name="orderId" value="${order.orderId}" />
        <div class="mb-3">
          <label for="status" class="form-label">Cập nhật trạng thái:</label>
          <select name="status" id="status" class="form-select">
            <option value="pending" ${order.orderStatus == 'pending' ? 'selected' : ''}>Pending</option>
            <option value="confirmed" ${order.orderStatus == 'confirmed' ? 'selected' : ''}>Confirmed</option>
            <option value="shipped" ${order.orderStatus == 'shipped' ? 'selected' : ''}>Shipped</option>
            <option value="delivered" ${order.orderStatus == 'delivered' ? 'selected' : ''}>Delivered</option>
            <option value="cancelled" ${order.orderStatus == 'cancelled' ? 'selected' : ''}>Cancelled</option>
          </select>
        </div>
        <button type="submit" class="btn btn-success">Cập nhật</button>
        <a href="<c:url value='/admin/order' />" class="btn btn-secondary">Quay lại</a>
      </form>
    </div>
  </div>

</div>

</body>
</html>

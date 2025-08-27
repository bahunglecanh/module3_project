<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý Đơn hàng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<div class="container mt-4">
    <h2 class="mb-4">Danh sách Đơn hàng</h2>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <c:forEach var="order" items="${orders}">
        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-primary text-white">
                <strong>Đơn hàng #${order.orderId}</strong> -
                Ngày đặt: ${order.orderDate} -
                Trạng thái: <span class="badge bg-warning text-dark">${order.orderStatus}</span>
            </div>
            <div class="card-body">
                <p><strong>Khách hàng:</strong> ${order.fullName} (${order.email})</p>
                <p><strong>SĐT:</strong> ${order.phone}</p>
                <p><strong>Địa chỉ:</strong> ${order.addressLine}, ${order.city}, ${order.state}, ${order.postalCode}, ${order.country}</p>
                <p><strong>Tổng tiền:</strong> <span class="text-danger fw-bold">${order.totalAmount} đ</span></p>

                <!-- Nút xem chi tiết -->
                <a href="<c:url value='/admin/order/detail?id=${order.orderId}' />"
                   class="btn btn-primary btn-sm mt-2">Xem chi tiết</a>
            </div>
        </div>
    </c:forEach>

</div>

</body>
</html>

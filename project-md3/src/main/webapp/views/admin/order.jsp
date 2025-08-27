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

    <c:forEach var="order" items="${orders}">
        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-primary text-white">
                <strong>Đơn hàng #${order.orderId}</strong> -
                Ngày đặt: ${order.orderDate} -
                Trạng thái: <span class="badge bg-warning text-dark">${order.orderStatus}</span>
            </div>
            <div class="card-body">
                <!-- Thông tin khách hàng -->
                <p><strong>Khách hàng:</strong> ${order.fullName} (${order.email})</p>
                <p><strong>SĐT:</strong> ${order.phone}</p>
                <p><strong>Địa chỉ giao hàng:</strong>
                        ${order.addressLine}, ${order.city}, ${order.state}, ${order.postalCode}, ${order.country}
                </p>
                <p><strong>Tổng tiền:</strong>
                    <span class="text-danger fw-bold">${order.totalAmount} đ</span>
                </p>

                <!-- Chi tiết sản phẩm -->
                <table class="table table-bordered table-hover mt-3">
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
    </c:forEach>

</div>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết đơn hàng #${order.id} - ShoeStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .order-detail-card {
            border: 1px solid #e0e0e0;
            border-radius: 8px;
        }
        .status-badge {
            font-size: 0.9rem;
            padding: 0.5rem 1rem;
        }
        .status-pending { background-color: #fff3cd; color: #856404; }
        .status-confirmed { background-color: #d1ecf1; color: #0c5460; }
        .status-shipped { background-color: #d4edda; color: #155724; }
        .status-delivered { background-color: #c3e6cb; color: #155724; }
        .status-cancelled { background-color: #f8d7da; color: #721c24; }
        .product-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <jsp:include page="common/header.jsp" />

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <!-- Breadcrumb -->
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">
                            <a href="${pageContext.request.contextPath}/orders">
                                <i class="fas fa-shopping-bag me-1"></i>Đơn hàng của tôi
                            </a>
                        </li>
                        <li class="breadcrumb-item active">Chi tiết đơn hàng #${order.id}</li>
                    </ol>
                </nav>

                <!-- Messages -->
                <c:if test="${not empty message}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="fas fa-check-circle me-2"></i>${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-circle me-2"></i>${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Order Header -->
                <div class="card order-detail-card mb-4">
                    <div class="card-header bg-light">
                        <div class="row align-items-center">
                            <div class="col-md-6">
                                <h5 class="mb-0">
                                    <i class="fas fa-receipt me-2"></i>Đơn hàng #${order.id}
                                </h5>
                            </div>
                            <div class="col-md-6 text-end">
                                <span class="badge status-badge status-${order.status.value}">
                                    ${order.statusDisplayName}
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <p><strong>Ngày đặt:</strong> 
                                    <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                </p>
                                <p><strong>Phương thức thanh toán:</strong> 
                                    ${order.paymentMethodName != null ? order.paymentMethodName : 'Chưa xác định'}
                                </p>
                                <c:if test="${not empty order.shippingAddress}">
                                    <p><strong>Địa chỉ giao hàng:</strong></p>
                                    <div class="ms-3">
                                        <p class="mb-1">${order.shippingAddress.addressLine}</p>
                                        <p class="mb-1">${order.shippingAddress.city}, ${order.shippingAddress.state}</p>
                                        <p class="mb-1">${order.shippingAddress.postalCode}, ${order.shippingAddress.country}</p>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-md-6">
                                <p><strong>Tổng tiền:</strong> 
                                    <span class="text-success fw-bold">
                                        <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫"/>
                                    </span>
                                </p>
                                <c:if test="${order.canCancel()}">
                                    <form action="${pageContext.request.contextPath}/cancel-order" method="post" 
                                          style="display: inline;" onsubmit="return confirm('Bạn có chắc chắn muốn hủy đơn hàng này?')">
                                        <input type="hidden" name="orderId" value="${order.id}">
                                        <button type="submit" class="btn btn-outline-danger btn-sm">
                                            <i class="fas fa-times me-1"></i>Hủy đơn hàng
                                        </button>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Order Items -->
                <div class="card order-detail-card">
                    <div class="card-header bg-light">
                        <h6 class="mb-0"><i class="fas fa-box me-2"></i>Sản phẩm đã đặt</h6>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${empty order.orderItems}">
                                <div class="text-center py-3">
                                    <p class="text-muted">Không có sản phẩm nào trong đơn hàng</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Sản phẩm</th>
                                                <th>Size</th>
                                                <th class="text-center">Số lượng</th>
                                                <th class="text-end">Đơn giá</th>
                                                <th class="text-end">Thành tiền</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="item" items="${order.orderItems}">
                                                <tr>
                                                    <td>
                                                        <div class="d-flex align-items-center">
                                                            <img src="${item.product.imageUrl}" alt="${item.product.name}" 
                                                                 class="product-image me-3">
                                                            <div>
                                                                <h6 class="mb-1">${item.product.name}</h6>
                                                                <small class="text-muted">
                                                                    ${item.product.brandName} - ${item.product.categoryName}
                                                                </small>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <span class="badge bg-secondary">${item.sizeDisplay}</span>
                                                    </td>
                                                    <td class="text-center">${item.quantity}</td>
                                                    <td class="text-end">
                                                        <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₫"/>
                                                    </td>
                                                    <td class="text-end fw-bold">
                                                        <fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="₫"/>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <tfoot class="table-light">
                                            <tr>
                                                <td colspan="4" class="text-end"><strong>Tổng cộng:</strong></td>
                                                <td class="text-end">
                                                    <strong class="text-success">
                                                        <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫"/>
                                                    </strong>
                                                </td>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="mt-4 text-center">
                    <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline-secondary me-2">
                        <i class="fas fa-arrow-left me-1"></i>Quay lại danh sách
                    </a>
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                        <i class="fas fa-shopping-cart me-1"></i>Tiếp tục mua sắm
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <jsp:include page="common/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

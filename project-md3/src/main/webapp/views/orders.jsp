<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đơn hàng của tôi - ShoeStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .order-card {
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            transition: box-shadow 0.3s ease;
        }
        .order-card:hover {
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .status-badge {
            font-size: 0.8rem;
            padding: 0.25rem 0.5rem;
        }
        .status-pending { background-color: #caca5c; color: #856404; }
        .status-confirmed { background-color: #52bc52; color: #0c5460; }
        .status-shipped { background-color: #3a526a; color: #155724; }
        .status-delivered { background-color: #a38329; color: #155724; }
        .status-cancelled { background-color: #c53636; color: #721c24; }
    </style>
</head>
<body>
    <!-- Header -->
    <jsp:include page="common/header.jsp" />

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="fas fa-shopping-bag me-2"></i>Đơn hàng của tôi</h2>
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary">
                        <i class="fas fa-arrow-left me-1"></i>Quay lại mua sắm
                    </a>
                </div>

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

                <!-- Orders List -->
                <c:choose>
                    <c:when test="${empty orders}">
                        <div class="text-center py-5">
                            <i class="fas fa-shopping-bag fa-3x text-muted mb-3"></i>
                            <h4 class="text-muted">Bạn chưa có đơn hàng nào</h4>
                            <p class="text-muted">Hãy bắt đầu mua sắm để tạo đơn hàng đầu tiên!</p>
                            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                                <i class="fas fa-shopping-cart me-2"></i>Mua sắm ngay
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="row">
                            <c:forEach var="order" items="${orders}">
                                <div class="col-12 mb-3">
                                    <div class="card order-card">
                                        <div class="card-body">
                                            <div class="row align-items-center">
                                                <div class="col-md-3">
                                                    <div class="d-flex flex-column">
                                                        <span class="text-muted small">Mã đơn hàng</span>
                                                        <strong class="text-primary">#${order.id}</strong>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="d-flex flex-column">
                                                        <span class="text-muted small">Ngày đặt</span>
                                                        <span>
                                                            <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="d-flex flex-column">
                                                        <span class="text-muted small">Tổng tiền</span>
                                                        <strong class="text-success">
                                                            <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫"/>
                                                        </strong>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="d-flex flex-column">
                                                        <span class="text-muted small">Trạng thái</span>
                                                        <span class="badge status-badge status-${order.status.value}">
                                                            ${order.statusDisplayName}
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="d-flex flex-column">
                                                        <span class="text-muted small">Thanh toán</span>
                                                        <span class="small">
                                                            ${order.paymentMethodName != null ? order.paymentMethodName : 'Chưa xác định'}
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <a href="${pageContext.request.contextPath}/order-detail?id=${order.id}" 
                                                       class="btn btn-outline-primary btn-sm">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <jsp:include page="common/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

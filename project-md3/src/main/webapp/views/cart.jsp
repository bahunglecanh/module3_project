<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Giỏ hàng" />
<jsp:include page="common/header.jsp" />

<div class="container py-5">
    <h2 class="mb-4">Giỏ hàng của bạn</h2>

    <c:choose>
        <c:when test="${empty items}">
            <div class="alert alert-info">Giỏ hàng trống.</div>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Tiếp tục mua sắm</a>
        </c:when>
        <c:otherwise>
            <div class="row">
                <div class="col-lg-8">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>Sản phẩm</th>
                                <th class="text-center">Size</th>
                                <th class="text-end">Đơn giá</th>
                                <th class="text-center" style="width: 180px;">Số lượng</th>
                                <th class="text-end">Thành tiền</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${items}">
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <div style="width:70px;height:70px;overflow:hidden;border-radius:6px;background:#f8f9fa;" class="me-3">
                                                <c:choose>
                                                    <c:when test="${not empty item.productImageUrl}">
                                                        <img src="${item.productImageUrl}" alt="${item.productName}" style="width:100%;height:100%;object-fit:cover;" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="https://images.unsplash.com/photo-1549298916-b41d501d3772?auto=format&fit=crop&w=200&q=60" alt="${item.productName}" style="width:100%;height:100%;object-fit:cover;" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div>
                                                <div class="fw-semibold">${item.productName}</div>
                                                <small class="text-muted">Mã SP: ${item.productId}</small>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="text-center">${empty item.size ? '-' : item.size}</td>
                                    <td class="text-end"><fmt:formatNumber value="${item.unitPrice}" pattern="#,#00"/>₫</td>
                                    <td>
                                        <form method="post" action="${pageContext.request.contextPath}/cart" class="d-flex justify-content-center align-items-center gap-2">
                                            <input type="hidden" name="action" value="update" />
                                            <input type="hidden" name="cartItemId" value="${item.cartItemId}" />
                                            <input type="number" min="1" name="quantity" value="${item.quantity}" class="form-control text-center" style="max-width:90px" />
                                            <button type="submit" class="btn btn-sm btn-outline-primary">Cập nhật</button>
                                        </form>
                                    </td>
                                    <td class="text-end fw-semibold"><fmt:formatNumber value="${item.lineTotal}" pattern="#,#00"/>₫</td>
                                    <td class="text-end">
                                        <form method="post" action="${pageContext.request.contextPath}/cart">
                                            <input type="hidden" name="action" value="remove" />
                                            <input type="hidden" name="cartItemId" value="${item.cartItemId}" />
                                            <button type="submit" class="btn btn-sm btn-outline-danger">Xóa</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-lg-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Tóm tắt đơn hàng</h5>
                            <div class="d-flex justify-content-between mb-2">
                                <span>Tổng số lượng</span>
                                <span class="fw-semibold">${summary.totalItems}</span>
                            </div>
                            <div class="d-flex justify-content-between mb-2">
                                <span>Tạm tính</span>
                                <span class="fw-semibold text-primary"><fmt:formatNumber value="${summary.subtotal}" pattern="#,#00"/>₫</span>
                            </div>
                            <hr/>
                            <div class="d-grid gap-2">
                                <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success">Thanh toán</a>
                                <form method="post" action="${pageContext.request.contextPath}/cart">
                                    <input type="hidden" name="action" value="clear" />
                                    <button type="submit" class="btn btn-outline-danger">Xóa toàn bộ giỏ</button>
                                </form>
                                <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-secondary">Tiếp tục mua sắm</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="common/footer.jsp" />



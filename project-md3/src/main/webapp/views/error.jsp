<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lỗi - ShoeStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .error-container {
            min-height: 60vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .error-card {
            max-width: 500px;
            text-align: center;
            padding: 2rem;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <jsp:include page="common/header.jsp" />

    <div class="container">
        <div class="error-container">
            <div class="error-card">
                <i class="fas fa-exclamation-triangle fa-3x text-warning mb-3"></i>
                <h2 class="text-danger mb-3">Đã xảy ra lỗi!</h2>
                
                <c:if test="${not empty error}">
                    <p class="text-muted mb-4">${error}</p>
                </c:if>
                
                <c:if test="${empty error}">
                    <p class="text-muted mb-4">Có lỗi xảy ra trong quá trình xử lý yêu cầu của bạn.</p>
                </c:if>
                
                <div class="d-flex gap-2 justify-content-center">
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                        <i class="fas fa-home me-1"></i>Về trang chủ
                    </a>
                    <button onclick="history.back()" class="btn btn-outline-secondary">
                        <i class="fas fa-arrow-left me-1"></i>Quay lại
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <jsp:include page="common/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="VNPay - Quét QR" />
<jsp:include page="../common/header.jsp" />

<div class="container py-5">
    <h2 class="mb-4">Thanh toán VNPay</h2>
    <div class="row">
        <div class="col-lg-6">
            <div class="card">
                <div class="card-body text-center">
                    <h5 class="card-title">Quét mã QR để thanh toán</h5>
                    <p class="text-muted">Mã đơn: #${orderId} • Số tiền: <strong><fmt:formatNumber value="${amount}" pattern="#,#00"/>₫</strong></p>
                    <div id="qrcode" class="d-inline-block p-3 border rounded bg-white"></div>
                    <p class="mt-3">Sau khi thanh toán thành công, bạn sẽ được chuyển hướng tự động.</p>
                    <a href="${pageContext.request.contextPath}/payment/vnpay-return?orderId=${orderId}&vnp_ResponseCode=00" class="btn btn-success mt-2">Tôi đã thanh toán</a>
                </div>
            </div>
        </div>
        <div class="col-lg-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Hướng dẫn</h5>
                    <ol>
                        <li>Mở ứng dụng ngân hàng/VNPay trên điện thoại.</li>
                        <li>Chọn chức năng quét QR.</li>
                        <li>Quét mã QR hiển thị bên cạnh.</li>
                        <li>Xác nhận thanh toán với số tiền hiển thị.</li>
                    </ol>
                    <div class="alert alert-info">Lưu ý: Đây là demo VNPay, URL thanh toán được giả lập.</div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // Generate QR using a public API (quick demo); replace with on-site QR lib if needed
    (function(){
        const url = encodeURIComponent('${paymentUrl}');
        const img = new Image();
        img.src = 'https://api.qrserver.com/v1/create-qr-code/?size=240x240&data=' + url;
        img.alt = 'VNPay QR';
        document.getElementById('qrcode').appendChild(img);
    })();
</script>

<jsp:include page="../common/footer.jsp" />



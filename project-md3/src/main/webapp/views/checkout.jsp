<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Thanh toán" />
<jsp:include page="common/header.jsp" />

<div class="container py-5">
    <h2 class="mb-4">Thanh toán</h2>

    <c:if test="${empty items}">
        <div class="alert alert-info">Giỏ hàng trống. Vui lòng thêm sản phẩm trước.</div>
        <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Quay lại mua sắm</a>
    </c:if>

    <c:if test="${not empty items}">
        <div class="row">
            <div class="col-lg-7">
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Thông tin giao hàng</h5>
                        <div class="row g-3 mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Họ và tên</label>
                                <input type="text" class="form-control" value="${currentUser.fullName}" placeholder="Họ và tên" />
                            </div>
                            <div class="col-md-3">
                                <label class="form-label">Email</label>
                                <input type="email" class="form-control" value="${currentUser.email}" placeholder="Email" />
                            </div>
                            <div class="col-md-3">
                                <label class="form-label">Số điện thoại</label>
                                <input type="text" class="form-control" value="${currentUser.phone}" placeholder="Số điện thoại" />
                            </div>
                        </div>

                        <h6 class="mb-2">Địa chỉ mặc định</h6>
                        <c:choose>
                            <c:when test="${not empty defaultAddress}">
                                <p class="mb-1 fw-semibold">${defaultAddress.addressLine}</p>
                                <p class="mb-1">${defaultAddress.city}, ${defaultAddress.state} ${defaultAddress.postalCode}</p>
                                <p class="text-muted">${defaultAddress.country}</p>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-warning">Chưa có địa chỉ mặc định. Vui lòng thêm địa chỉ giao hàng.</div>
                            </c:otherwise>
                        </c:choose>

                        <!-- Chọn địa chỉ mặc định -->
                        <c:if test="${not empty addresses}">
                            <form method="post" action="${pageContext.request.contextPath}/checkout" class="row g-2 align-items-end mt-3">
                                <input type="hidden" name="action" value="set_default_address" />
                                <div class="col-9">
                                    <label class="form-label">Chọn địa chỉ giao hàng:</label>
                                    <select class="form-select" name="addressId">
                                        <c:forEach var="addr" items="${addresses}">
                                            <option value="${addr.id}" ${addr.id == defaultAddress.id ? 'selected' : ''}>
                                                ${addr.addressLine}, ${addr.city} (${addr.country})
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-3 d-grid">
                                    <button class="btn btn-outline-primary" type="submit">Chọn</button>
                                </div>
                            </form>
                            <div class="d-flex gap-2 mt-2">
                                <form method="post" action="${pageContext.request.contextPath}/checkout">
                                    <input type="hidden" name="action" value="unset_default_address" />
                                    <button type="submit" class="btn btn-outline-warning">Bỏ mặc định</button>
                                </form>
                                <form method="post" action="${pageContext.request.contextPath}/checkout" onsubmit="return confirm('Xóa địa chỉ đã chọn?');">
                                    <input type="hidden" name="action" value="delete_address" />
                                    <input type="hidden" name="addressId" value="${defaultAddress != null ? defaultAddress.id : ''}" />
                                    <button type="submit" class="btn btn-outline-danger" ${defaultAddress == null ? 'disabled' : ''}>Xóa địa chỉ hiện tại</button>
                                </form>
                            </div>
                        </c:if>

                        <!-- Thêm địa chỉ mới -->
                        <form method="post" action="${pageContext.request.contextPath}/checkout" class="mt-4" id="addAddressForm">
                            <input type="hidden" name="action" value="add_address" />
                            <div class="row g-2">
                                <div class="col-12">
                                    <label class="form-label">Địa chỉ</label>
                                    <input type="text" name="addressLine" class="form-control" placeholder="Số nhà, đường..." required />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Tỉnh/Thành</label>
                                    <select class="form-select" id="provinceSelect"></select>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Quận/Huyện</label>
                                    <select class="form-select" id="districtSelect" disabled></select>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Phường/Xã</label>
                                    <select class="form-select" id="wardSelect" disabled></select>
                                </div>
                                <input type="hidden" name="city" id="cityHidden" />
                                <input type="hidden" name="state" id="stateHidden" />
                                <div class="col-md-4">
                                    <label class="form-label">Mã bưu chính</label>
                                    <input type="text" name="postalCode" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Quốc gia</label>
                                    <input type="text" name="country" value="Vietnam" class="form-control" />
                                </div>
                                <div class="col-md-4 d-flex align-items-end">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="makeDefault" id="makeDefault">
                                        <label class="form-check-label" for="makeDefault">Đặt làm mặc định</label>
                                    </div>
                                </div>
                                <div class="col-12 d-grid">
                                    <button type="submit" class="btn btn-primary">Thêm địa chỉ</button>
                                </div>
                            </div>
                        </form>
                        <script>
                            (function(){
                                const API_BASE = 'https://provinces.open-api.vn/api';
                                const provinceSelect = document.getElementById('provinceSelect');
                                const districtSelect = document.getElementById('districtSelect');
                                const wardSelect = document.getElementById('wardSelect');
                                const cityHidden = document.getElementById('cityHidden');
                                const stateHidden = document.getElementById('stateHidden');
                                const addAddressForm = document.getElementById('addAddressForm');

                                function clearOptions(select, placeholder){
                                    select.innerHTML = '';
                                    const opt = document.createElement('option');
                                    opt.value = '';
                                    opt.textContent = placeholder;
                                    select.appendChild(opt);
                                }

                                function enable(select, enabled){ select.disabled = !enabled; }

                                async function loadProvinces(){
                                    clearOptions(provinceSelect, 'Chọn Tỉnh/Thành');
                                    enable(districtSelect, false);
                                    enable(wardSelect, false);
                                    try {
                                        const res = await fetch(API_BASE + '/?depth=1');
                                        const data = await res.json();
                                        data.forEach(p => {
                                            const opt = document.createElement('option');
                                            opt.value = p.code;
                                            opt.textContent = p.name;
                                            provinceSelect.appendChild(opt);
                                        });
                                    } catch(e) { console.error(e); }
                                }

                                async function loadDistricts(provinceCode){
                                    clearOptions(districtSelect, 'Chọn Quận/Huyện');
                                    clearOptions(wardSelect, 'Chọn Phường/Xã');
                                    enable(wardSelect, false);
                                    if (!provinceCode){ enable(districtSelect, false); return; }
                                    try {
                                        const res = await fetch(API_BASE + '/p/' + provinceCode + '?depth=2');
                                        const data = await res.json();
                                        (data.districts || []).forEach(d => {
                                            const opt = document.createElement('option');
                                            opt.value = d.code;
                                            opt.textContent = d.name;
                                            districtSelect.appendChild(opt);
                                        });
                                        enable(districtSelect, true);
                                    } catch(e) { console.error(e); }
                                }

                                async function loadWards(districtCode){
                                    clearOptions(wardSelect, 'Chọn Phường/Xã');
                                    if (!districtCode){ enable(wardSelect, false); return; }
                                    try {
                                        const res = await fetch(API_BASE + '/d/' + districtCode + '?depth=2');
                                        const data = await res.json();
                                        (data.wards || []).forEach(w => {
                                            const opt = document.createElement('option');
                                            opt.value = w.code;
                                            opt.textContent = w.name;
                                            wardSelect.appendChild(opt);
                                        });
                                        enable(wardSelect, true);
                                    } catch(e) { console.error(e); }
                                }

                                provinceSelect.addEventListener('change', function(){
                                    const code = this.value;
                                    loadDistricts(code);
                                });

                                districtSelect.addEventListener('change', function(){
                                    const code = this.value;
                                    loadWards(code);
                                });

                                addAddressForm.addEventListener('submit', function(){
                                    const provinceName = provinceSelect.options[provinceSelect.selectedIndex]?.text || '';
                                    const districtName = districtSelect.options[districtSelect.selectedIndex]?.text || '';
                                    const wardName = wardSelect.options[wardSelect.selectedIndex]?.text || '';
                                    stateHidden.value = provinceName; // province -> state
                                    cityHidden.value = districtName;  // district -> city
                                    // Optionally append ward to addressLine
                                    const addrInput = addAddressForm.querySelector('input[name="addressLine"]');
                                    if (addrInput && wardName && !addrInput.value.includes(wardName)) {
                                        addrInput.value = addrInput.value + ', ' + wardName;
                                    }
                                });

                                loadProvinces();
                            })();
                        </script>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Sản phẩm</h5>
                        <table class="table align-middle">
                            <thead>
                                <tr>
                                    <th>Sản phẩm</th>
                                    <th class="text-center">Size</th>
                                    <th class="text-end">Đơn giá</th>
                                    <th class="text-center">SL</th>
                                    <th class="text-end">Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${items}">
                                    <tr>
                                        <td>
                                            <div class="d-flex align-items-center">
                                                <div style="width:60px;height:60px;overflow:hidden;border-radius:6px;background:#f8f9fa;" class="me-3">
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
                                        <td class="text-center">${item.quantity}</td>
                                        <td class="text-end fw-semibold"><fmt:formatNumber value="${item.lineTotal}" pattern="#,#00"/>₫</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-lg-5">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Tóm tắt thanh toán</h5>
                        <div class="d-flex justify-content-between mb-2">
                            <span>Tổng số lượng</span>
                            <span class="fw-semibold">${summary.totalItems}</span>
                        </div>
                        <div class="d-flex justify-content-between mb-2">
                            <span>Tạm tính</span>
                            <span class="fw-semibold text-primary"><fmt:formatNumber value="${summary.subtotal}" pattern="#,#00"/>₫</span>
                        </div>
                        <hr/>
                        <div class="mb-3">
                            <label class="form-label">Phương thức thanh toán</label>
                            <div class="d-grid gap-2">
                                <form method="post" action="${pageContext.request.contextPath}/payment/checkout">
                                    <input type="hidden" name="method" value="COD" />
                                    <button class="btn btn-outline-success w-100" type="submit">Ship COD</button>
                                </form>
                                <form method="post" action="${pageContext.request.contextPath}/payment/checkout">
                                    <input type="hidden" name="method" value="VNPay" />
                                    <button class="btn btn-primary w-100" type="submit">Thanh toán VNPay</button>
                                </form>
                            </div>
                        </div>
                        <a class="btn btn-outline-secondary w-100 mt-2" href="${pageContext.request.contextPath}/cart">Quay lại giỏ hàng</a>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="common/footer.jsp" />



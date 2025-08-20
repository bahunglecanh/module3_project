<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="${product.name}" />
<jsp:include page="common/header.jsp" />

<!-- Breadcrumb -->
<nav aria-label="breadcrumb" class="breadcrumb-nav">
    <div class="container">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/products">Sản phẩm</a></li>
            <c:if test="${not empty product.categoryName}">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/products?category=${product.categoryId}">${product.categoryName}</a></li>
            </c:if>
            <li class="breadcrumb-item active" aria-current="page">${product.name}</li>
        </ol>
    </div>
</nav>

<!-- Product Detail -->
<div class="product-detail-page">
    <div class="container">
        <div class="row">
            <!-- Product Images -->
            <div class="col-lg-6">
                <div class="product-images">
                    <div class="main-image">
                        <c:choose>
                            <c:when test="${not empty product.imageUrl}">
                                <img src="${product.imageUrl}" alt="${product.name}" class="img-fluid main-product-img" id="mainImage">
                            </c:when>
                            <c:otherwise>
                                <img src="https://images.unsplash.com/photo-1549298916-b41d501d3772?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80" 
                                     alt="${product.name}" class="img-fluid main-product-img" id="mainImage">
                            </c:otherwise>
                        </c:choose>
                        
                        <!-- Product badges -->
                        <div class="product-badges">
                            <c:if test="${product.stockQuantity == 0}">
                                <span class="badge badge-out-stock">Hết hàng</span>
                            </c:if>
                            <c:if test="${product.stockQuantity > 0 && product.stockQuantity < 10}">
                                <span class="badge badge-low-stock">Còn ít</span>
                            </c:if>
                        </div>
                    </div>
                    
                    <!-- Single image only - no thumbnails -->
                </div>
            </div>
            
            <!-- Product Info -->
            <div class="col-lg-6">
                <div class="product-info">
                    <!-- Product name and category -->
                    <div class="product-header">
                        <c:if test="${not empty product.categoryName}">
                            <p class="product-category">${product.categoryName}</p>
                        </c:if>
                        <h1 class="product-name">${product.name}</h1>
                        
                        <!-- Brand -->
                        <c:if test="${not empty product.brandName}">
                            <p class="product-brand">${product.brandName}</p>
                        </c:if>
                        
                        <!-- Rating -->
                        <div class="product-rating">
                            <div class="stars">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="far fa-star"></i>
                            </div>
                            <span class="rating-text">(4.0) - 128 đánh giá</span>
                        </div>
                    </div>
                    
                    <!-- Price -->
                    <div class="product-price">
                        <span class="current-price">${product.formattedPrice}</span>
                    </div>
                    
                    <!-- Stock status -->
                    <div class="stock-status">
                        <c:choose>
                            <c:when test="${totalStock > 10}">
                                <span class="stock-available">
                                    <i class="fas fa-check-circle"></i> Còn hàng (${totalStock} sản phẩm)
                                </span>
                            </c:when>
                            <c:when test="${totalStock > 0}">
                                <span class="stock-low">
                                    <i class="fas fa-exclamation-circle"></i> Chỉ còn ${totalStock} sản phẩm
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="stock-out">
                                    <i class="fas fa-times-circle"></i> Hết hàng
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <!-- Product description -->
                    <div class="product-description">
                        <c:choose>
                            <c:when test="${not empty product.description}">
                                <p>${product.description}</p>
                            </c:when>
                            <c:otherwise>
                                <p>Giày thể thao chất lượng cao, thiết kế hiện đại và thoải mái. Phù hợp cho các hoạt động thể thao và đi lại hàng ngày. Chất liệu cao cấp, bền bỉ theo thời gian.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <!-- Add to cart form -->
                    <c:if test="${totalStock > 0}">
                        <form action="${pageContext.request.contextPath}/detail/${product.id}" method="post" class="add-to-cart-form">
                            <input type="hidden" name="action" value="add_to_cart">
                            <input type="hidden" name="productId" value="${product.id}">
                            
                            <!-- Size selection với stock thực -->
                            <div class="size-selection mb-3">
                                <label class="form-label">Chọn size:</label>
                                <div class="size-options">
                                    <c:forEach var="size" items="${productSizes}">
                                        <div class="size-option">
                                            <input type="radio" name="size" value="${size.size}" id="size-${size.size}" 
                                                   ${size.stockQuantity > 0 ? '' : 'disabled'} 
                                                   data-stock="${size.stockQuantity}"
                                                   onchange="updateMaxQuantity(${size.stockQuantity})" required>
                                            <label for="size-${size.size}" class="size-label ${size.stockQuantity == 0 ? 'out-of-stock' : ''}">
                                                ${size.size}
                                                <c:if test="${size.stockQuantity == 0}">
                                                    <span class="out-of-stock-indicator">✗</span>
                                                </c:if>
                                                <c:if test="${size.stockQuantity > 0 && size.stockQuantity <= 5}">
                                                    <span class="low-stock-indicator">(${size.stockQuantity})</span>
                                                </c:if>
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            
                            <!-- Quantity selection -->
                            <div class="quantity-selection mb-3">
                                <label class="form-label">Số lượng:</label>
                                <div class="quantity-controls">
                                    <button type="button" class="qty-btn minus" onclick="decreaseQuantity()">-</button>
                                    <input type="number" name="quantity" id="quantity" value="1" min="1" max="1" class="qty-input" required>
                                    <button type="button" class="qty-btn plus" onclick="increaseQuantity()">+</button>
                                </div>
                                <small class="text-muted" id="stock-info">Chọn size để xem số lượng có sẵn</small>
                            </div>
                            
                            <!-- Action buttons -->
                            <div class="action-buttons">
                                <button type="submit" class="btn btn-primary btn-add-cart">
                                    <i class="fas fa-shopping-cart me-2"></i>Thêm vào giỏ hàng
                                </button>
                                <button type="button" class="btn btn-success btn-buy-now" onclick="buyNow(${product.id})">
                                    <i class="fas fa-bolt me-2"></i>Mua ngay
                                </button>
                                <button type="button" class="btn btn-outline-secondary btn-wishlist" onclick="addToWishlist(${product.id})">
                                    <i class="far fa-heart"></i>
                                </button>
                            </div>
                        </form>
                    </c:if>
                    
                    <!-- Out of stock message -->
                    <c:if test="${totalStock == 0}">
                        <div class="out-of-stock-message">
                            <p class="text-muted">Sản phẩm hiện tại đã hết hàng cho tất cả sizes.</p>
                            <button type="button" class="btn btn-outline-primary" onclick="notifyWhenAvailable(${product.id})">
                                <i class="fas fa-bell me-2"></i>Báo tôi khi có hàng
                            </button>
                        </div>
                    </c:if>
                    
                    <!-- Product features -->
                    <div class="product-features mt-4">
                        <ul class="feature-list">
                            <li><i class="fas fa-shipping-fast"></i> Miễn phí vận chuyển với đơn từ 500.000đ</li>
                            <li><i class="fas fa-undo-alt"></i> Đổi trả miễn phí trong 30 ngày</li>
                            <li><i class="fas fa-shield-alt"></i> Bảo hành chính hãng 6 tháng</li>
                            <li><i class="fas fa-medal"></i> Sản phẩm chính hãng 100%</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Related Products -->
<c:if test="${not empty relatedProducts}">
    <div class="related-products-section">
        <div class="container">
            <h3 class="section-title">Sản phẩm liên quan</h3>
            <div class="row">
                <c:forEach var="relatedProduct" items="${relatedProducts}">
                    <div class="col-lg-3 col-md-6 mb-4">
                        <div class="product-card">
                            <div class="product-image">
                                <a href="${pageContext.request.contextPath}/detail/${relatedProduct.id}">
                                    <c:choose>
                                        <c:when test="${not empty relatedProduct.imageUrl}">
                                            <img src="${relatedProduct.imageUrl}" alt="${relatedProduct.name}" class="img-fluid">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="https://images.unsplash.com/photo-1549298916-b41d501d3772?ixlib=rb-4.0.3&auto=format&fit=crop&w=400&q=80" 
                                                 alt="${relatedProduct.name}" class="img-fluid">
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </div>
                            <div class="product-info">
                                <h6 class="product-name">
                                    <a href="${pageContext.request.contextPath}/detail/${relatedProduct.id}">${relatedProduct.name}</a>
                                </h6>
                                <div class="product-price">
                                    <span class="price-current">${relatedProduct.formattedPrice}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</c:if>

<style>
    /* Breadcrumb */
    .breadcrumb-nav {
        background: var(--light-gray);
        padding: 15px 0;
    }
    
    .breadcrumb {
        margin-bottom: 0;
        background: transparent;
    }
    
    .breadcrumb-item a {
        color: var(--primary-color);
        text-decoration: none;
    }
    
    /* Product Detail Page */
    .product-detail-page {
        padding: 60px 0;
    }
    
    /* Product Images */
    .product-images {
        position: sticky;
        top: 100px;
    }
    
    .main-image {
        position: relative;
        border-radius: 15px;
        overflow: hidden;
        box-shadow: var(--shadow);
        margin-bottom: 20px;
    }
    
    .main-product-img {
        width: 100%;
        height: 500px;
        object-fit: cover;
        transition: transform 0.3s ease;
    }
    
    .main-image:hover .main-product-img {
        transform: scale(1.05);
    }
    
    .product-badges {
        position: absolute;
        top: 15px;
        left: 15px;
        z-index: 2;
    }
    
    .badge {
        padding: 5px 10px;
        border-radius: 20px;
        font-size: 12px;
        font-weight: 600;
        margin-right: 5px;
    }
    
    .badge-out-stock {
        background: #6c757d;
        color: var(--white);
    }
    
    .badge-low-stock {
        background: var(--warning-color);
        color: var(--white);
    }
    
    /* No thumbnail styles needed */
    
    /* Product Info */
    .product-info {
        padding-left: 30px;
    }
    
    .product-header .product-category {
        color: var(--secondary-color);
        font-weight: 500;
        text-transform: uppercase;
        font-size: 14px;
        margin-bottom: 10px;
    }
    
    .product-header .product-brand {
        color: var(--primary-color);
        font-size: 1.2rem;
        font-weight: 600;
        text-transform: uppercase;
        letter-spacing: 1px;
        margin-bottom: 15px;
    }
    
    .product-name {
        font-size: 2.5rem;
        font-weight: 700;
        color: var(--primary-color);
        margin-bottom: 15px;
        line-height: 1.2;
    }
    
    .product-rating {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 20px;
    }
    
    .stars {
        color: #ffc107;
    }
    
    .rating-text {
        color: var(--dark-gray);
        font-size: 14px;
    }
    
    /* Price */
    .product-price {
        display: flex;
        align-items: center;
        gap: 15px;
        margin-bottom: 20px;
    }
    
    .current-price {
        font-size: 2rem;
        font-weight: 700;
        color: var(--accent-color);
    }
    
    .original-price {
        font-size: 1.2rem;
        color: var(--dark-gray);
        text-decoration: line-through;
    }
    
    .discount-badge {
        background: var(--accent-color);
        color: var(--white);
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: 600;
    }
    
    /* Stock Status */
    .stock-status {
        margin-bottom: 20px;
    }
    
    .stock-available {
        color: var(--success-color);
        font-weight: 500;
    }
    
    .stock-low {
        color: var(--warning-color);
        font-weight: 500;
    }
    
    .stock-out {
        color: var(--accent-color);
        font-weight: 500;
    }
    
    /* Product Description */
    .product-description {
        margin-bottom: 30px;
        line-height: 1.6;
        color: var(--dark-gray);
    }
    
    /* Size Selection */
    .size-options {
        display: flex;
        gap: 10px;
        flex-wrap: wrap;
    }
    
    .size-option {
        position: relative;
    }
    
    .size-option input[type="radio"] {
        display: none;
    }
    
    .size-label {
        display: block;
        width: 50px;
        height: 50px;
        line-height: 46px;
        text-align: center;
        border: 2px solid var(--border-color);
        border-radius: 8px;
        cursor: pointer;
        transition: all 0.3s ease;
        font-weight: 500;
    }
    
    .size-option input[type="radio"]:checked + .size-label {
        border-color: var(--secondary-color);
        background: var(--secondary-color);
        color: var(--white);
    }
    
    .size-label:hover {
        border-color: var(--secondary-color);
    }
    
    .size-label.disabled {
        background-color: #f8f9fa;
        color: #6c757d;
        border-color: #dee2e6;
        cursor: not-allowed;
        opacity: 0.6;
    }
    
    .out-of-stock-indicator {
        position: absolute;
        top: -5px;
        right: -5px;
        background: var(--accent-color);
        color: var(--white);
        border-radius: 50%;
        width: 18px;
        height: 18px;
        font-size: 10px;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    
    .low-stock-indicator {
        position: absolute;
        top: -8px;
        right: -8px;
        background: var(--warning-color);
        color: var(--white);
        border-radius: 50%;
        width: 20px;
        height: 20px;
        font-size: 10px;
        font-weight: 600;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    
    /* Quantity Controls */
    .quantity-controls {
        display: flex;
        align-items: center;
        gap: 10px;
    }
    
    .qty-btn {
        width: 40px;
        height: 40px;
        border: 1px solid var(--border-color);
        background: var(--white);
        border-radius: 6px;
        cursor: pointer;
        font-weight: 600;
        transition: all 0.3s ease;
    }
    
    .qty-btn:hover {
        background: var(--secondary-color);
        color: var(--white);
        border-color: var(--secondary-color);
    }
    
    .qty-input {
        width: 80px;
        height: 40px;
        text-align: center;
        border: 1px solid var(--border-color);
        border-radius: 6px;
        font-weight: 500;
    }
    
    .qty-input:focus {
        outline: none;
        border-color: var(--secondary-color);
    }
    
    /* Action Buttons */
    .action-buttons {
        display: flex;
        gap: 15px;
        margin-bottom: 30px;
    }
    
    .btn-add-cart {
        flex: 1;
        padding: 15px 25px;
        font-size: 16px;
        font-weight: 600;
        border-radius: 8px;
        margin-right: 10px;
    }
    
    .btn-buy-now {
        flex: 1;
        padding: 15px 25px;
        font-size: 16px;
        font-weight: 600;
        border-radius: 8px;
        margin-right: 10px;
        background: linear-gradient(135deg, #28a745, #20c997);
        border: none;
        box-shadow: 0 4px 15px rgba(40, 167, 69, 0.3);
        transition: all 0.3s ease;
    }
    
    .btn-buy-now:hover {
        background: linear-gradient(135deg, #218838, #1fa085);
        transform: translateY(-2px);
        box-shadow: 0 6px 20px rgba(40, 167, 69, 0.4);
    }
    
    .btn-wishlist {
        width: 60px;
        height: 60px;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 20px;
    }
    
    /* Product Features */
    .feature-list {
        list-style: none;
        padding: 0;
    }
    
    .feature-list li {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 10px;
        color: var(--dark-gray);
    }
    
    .feature-list i {
        color: var(--success-color);
        width: 16px;
    }
    
    /* Related Products */
    .related-products-section {
        padding: 60px 0;
        background: var(--light-gray);
    }
    
    .section-title {
        text-align: center;
        font-size: 2rem;
        font-weight: 700;
        color: var(--primary-color);
        margin-bottom: 40px;
    }
    
    .related-products-section .product-card {
        background: var(--white);
        border-radius: 15px;
        overflow: hidden;
        box-shadow: var(--shadow);
        transition: transform 0.3s ease;
    }
    
    .related-products-section .product-card:hover {
        transform: translateY(-5px);
    }
    
    .related-products-section .product-image {
        height: 200px;
        overflow: hidden;
    }
    
    .related-products-section .product-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.3s ease;
    }
    
    .related-products-section .product-card:hover .product-image img {
        transform: scale(1.1);
    }
    
    .related-products-section .product-info {
        padding: 20px;
    }
    
    .related-products-section .product-name a {
        color: var(--primary-color);
        text-decoration: none;
        font-weight: 600;
    }
    
    .related-products-section .product-name a:hover {
        color: var(--secondary-color);
    }
    
    /* Out of stock */
    .out-of-stock-message {
        text-align: center;
        padding: 30px;
        background: var(--light-gray);
        border-radius: 8px;
    }
    
    /* Responsive */
    @media (max-width: 991px) {
        .product-info {
            padding-left: 0;
            margin-top: 30px;
        }
        
        .product-name {
            font-size: 2rem;
        }
        
        .action-buttons {
            flex-direction: column;
        }
        
        .btn-add-cart {
            flex: none;
        }
    }
    
    @media (max-width: 576px) {
        .product-name {
            font-size: 1.5rem;
        }
        
        .current-price {
            font-size: 1.5rem;
        }
        
        .size-options {
            gap: 5px;
        }
        
        .size-label {
            width: 40px;
            height: 40px;
            line-height: 36px;
            font-size: 14px;
        }
    }
</style>

<script>
    // Update max quantity when size is selected
    function updateMaxQuantity(stock) {
        const quantityInput = document.getElementById('quantity');
        const stockInfo = document.getElementById('stock-info');
        
        quantityInput.max = stock;
        quantityInput.value = Math.min(parseInt(quantityInput.value), stock);
        
        if (stock > 0) {
            stockInfo.textContent = `Tối đa ${stock} sản phẩm cho size này`;
            stockInfo.className = 'text-muted';
        } else {
            stockInfo.textContent = 'Size này đã hết hàng';
            stockInfo.className = 'text-danger';
        }
    }
    
    // Quantity controls
    function increaseQuantity() {
        const input = document.getElementById('quantity');
        const max = parseInt(input.getAttribute('max'));
        const current = parseInt(input.value);
        if (current < max) {
            input.value = current + 1;
        }
    }
    
    function decreaseQuantity() {
        const input = document.getElementById('quantity');
        const current = parseInt(input.value);
        if (current > 1) {
            input.value = current - 1;
        }
    }
    
    // No thumbnail functionality needed anymore
    
    // Buy now function
    function buyNow(productId) {
        // Validate size selection
        const selectedSize = document.querySelector('input[name="size"]:checked');
        if (!selectedSize) {
            alert('Vui lòng chọn size!');
            return;
        }
        
        // Validate quantity
        const quantity = document.getElementById('quantity').value;
        if (!quantity || quantity <= 0) {
            alert('Vui lòng chọn số lượng!');
            return;
        }
        
        // TODO: Implement buy now logic (redirect to checkout)
        const message = `Mua ngay: ${productId} - Size: ${selectedSize.value} - SL: ${quantity}`;
        alert(message + '\n\nChức năng checkout sẽ được phát triển sau!');
    }
    
    // Add to wishlist (placeholder)
    function addToWishlist(productId) {
        alert('Chức năng wishlist sẽ được phát triển sau!');
    }
    
    // Notify when available (placeholder)
    function notifyWhenAvailable(productId) {
        alert('Chúng tôi sẽ thông báo khi sản phẩm có hàng trở lại!');
    }
</script>

<jsp:include page="common/footer.jsp" />

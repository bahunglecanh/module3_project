<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Trang chủ" />
<jsp:include page="common/header.jsp" />

<!-- Hero Section -->
<section class="hero-section">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-6">
                <div class="hero-content">
                    <h1 class="hero-title">
                        Bộ sưu tập giày 
                        <span class="highlight">thời trang</span> 
                        mới nhất
                    </h1>
                    <p class="hero-description">
                        Khám phá những đôi giày chất lượng cao, thiết kế hiện đại 
                        và phù hợp với mọi phong cách của bạn. Từ giày thể thao 
                        năng động đến giày công sở thanh lịch.
                    </p>
                    <div class="hero-buttons">
                        <a href="${pageContext.request.contextPath}/products" class="btn btn-primary btn-lg">
                            <i class="fas fa-shopping-bag me-2"></i>Mua sắm ngay
                        </a>
                        <a href="${pageContext.request.contextPath}/about" class="btn btn-outline-secondary btn-lg">
                            <i class="fas fa-play me-2"></i>Tìm hiểu thêm
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="hero-image">
                    <img src="https://images.unsplash.com/photo-1549298916-b41d501d3772?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80" 
                         alt="Giày thời trang" class="img-fluid">
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Features Section -->
<section class="features-section">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-shipping-fast"></i>
                    </div>
                    <h5 class="feature-title">Giao hàng nhanh</h5>
                    <p class="feature-description">
                        Giao hàng trong 24-48h cho khu vực nội thành, 
                        miễn phí với đơn hàng từ 500.000đ
                    </p>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-undo-alt"></i>
                    </div>
                    <h5 class="feature-title">Đổi trả dễ dàng</h5>
                    <p class="feature-description">
                        Đổi trả miễn phí trong 30 ngày, 
                        không cần lý do với sản phẩm còn nguyên tem
                    </p>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-shield-alt"></i>
                    </div>
                    <h5 class="feature-title">Bảo hành chất lượng</h5>
                    <p class="feature-description">
                        Cam kết chất lượng 100% chính hãng, 
                        bảo hành 6 tháng cho mọi sản phẩm
                    </p>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-headset"></i>
                    </div>
                    <h5 class="feature-title">Hỗ trợ 24/7</h5>
                    <p class="feature-description">
                        Đội ngũ tư vấn chuyên nghiệp sẵn sàng 
                        hỗ trợ bạn mọi lúc, mọi nơi
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Categories Section -->
<section class="categories-section">
    <div class="container">
        <div class="section-header">
            <h2 class="section-title">Danh mục sản phẩm</h2>
            <p class="section-subtitle">
                Khám phá bộ sưu tập đa dạng phù hợp với mọi nhu cầu
            </p>
        </div>
        
        <div class="row">
            <c:forEach var="category" items="${categories}" varStatus="status">
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="category-card">
                        <div class="category-image">
                            <c:choose>
                                <c:when test="${status.index == 0}">
                                    <img src="https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80" 
                                         alt="${category.name}" class="img-fluid">
                                </c:when>
                                <c:when test="${status.index == 1}">
                                    <img src="https://images.unsplash.com/photo-1533867617858-e7b97e060509?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80" 
                                         alt="${category.name}" class="img-fluid">
                                </c:when>
                                <c:when test="${status.index == 2}">
                                    <img src="https://images.unsplash.com/photo-1515347619252-60a4bf4fff4f?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80" 
                                         alt="${category.name}" class="img-fluid">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://images.unsplash.com/photo-1549298916-b41d501d3772?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80" 
                                         alt="${category.name}" class="img-fluid">
                                </c:otherwise>
                            </c:choose>
                            <div class="category-overlay">
                                <h4 class="category-title">${category.name}</h4>
                                <a href="${pageContext.request.contextPath}/products?category=${category.id}" class="btn btn-light">
                                    Xem collection
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            
            <!-- Default message if no categories -->
            <c:if test="${empty categories}">
                <div class="col-12 text-center">
                    <p class="text-muted">Chưa có danh mục sản phẩm nào.</p>
                </div>
            </c:if>
        </div>
    </div>
</section>

<!-- Featured Products Section -->
<section class="featured-products-section">
    <div class="container">
        <div class="section-header">
            <h2 class="section-title">Sản phẩm nổi bật</h2>
            <p class="section-subtitle">
                Những sản phẩm mới nhất tại cửa hàng
            </p>
        </div>
        
        <div class="row">
            <c:forEach var="product" items="${featuredProducts}" varStatus="status">
                <div class="col-lg-3 col-md-6 mb-4">
                    <div class="product-card">
                        <div class="product-image">
                            <c:choose>
                                <c:when test="${not empty product.imageUrl}">
                                    <img src="${product.imageUrl}" alt="${product.name}" class="img-fluid">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://images.unsplash.com/photo-1549298916-b41d501d3772?ixlib=rb-4.0.3&auto=format&fit=crop&w=400&q=80" 
                                         alt="${product.name}" class="img-fluid">
                                </c:otherwise>
                            </c:choose>
                            
                            <div class="product-overlay">
                                <a href="${pageContext.request.contextPath}/products/${product.id}" class="btn btn-primary btn-sm">
                                    <i class="fas fa-eye me-1"></i>Xem chi tiết
                                </a>
                            </div>
                            
                            <c:if test="${status.index == 0}">
                                <div class="product-badges">
                                    <span class="badge badge-new">New</span>
                                </div>
                            </c:if>
                            
                            <c:if test="${not product.inStock}">
                                <div class="product-badges">
                                    <span class="badge badge-out-stock">Hết hàng</span>
                                </div>
                            </c:if>
                        </div>
                        
                        <div class="product-info">
                            <h6 class="product-name">${product.name}</h6>
                            
                            <c:if test="${not empty product.categoryName}">
                                <p class="product-category">${product.categoryName}</p>
                            </c:if>
                            
                            <div class="product-rating">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="far fa-star"></i>
                                <span class="rating-count">(${(status.index + 1) * 32})</span>
                            </div>
                            
                            <div class="product-price">
                                <span class="price-current">${product.formattedPrice}</span>
                                <c:if test="${product.stockQuantity < 10 && product.stockQuantity > 0}">
                                    <small class="stock-warning">Còn ${product.stockQuantity} sản phẩm</small>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            
            <!-- Default message if no products -->
            <c:if test="${empty featuredProducts}">
                <div class="col-12 text-center">
                    <p class="text-muted">Chưa có sản phẩm nào.</p>
                </div>
            </c:if>
        </div>
        
        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary btn-lg">
                <i class="fas fa-th-large me-2"></i>Xem tất cả sản phẩm
            </a>
        </div>
    </div>
</section>

<!-- Newsletter Section -->
<section class="newsletter-section">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-6">
                <div class="newsletter-content">
                    <h3 class="newsletter-title">Đăng ký nhận tin khuyến mãi</h3>
                    <p class="newsletter-description">
                        Nhận thông báo về các sản phẩm mới và ưu đãi đặc biệt 
                        ngay trong email của bạn!
                    </p>
                </div>
            </div>
            <div class="col-lg-6">
                <form class="newsletter-form">
                    <div class="input-group">
                        <input type="email" class="form-control" placeholder="Nhập địa chỉ email của bạn">
                        <button class="btn btn-primary" type="submit">
                            <i class="fas fa-paper-plane me-1"></i>Đăng ký
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<style>
    /* Hero Section */
    .hero-section {
        background: linear-gradient(135deg, rgba(52, 152, 219, 0.1) 0%, rgba(155, 89, 182, 0.1) 100%);
        padding: 80px 0;
    }

    .hero-title {
        font-size: 3.5rem;
        font-weight: 700;
        color: var(--primary-color);
        margin-bottom: 20px;
        line-height: 1.2;
    }

    .highlight {
        color: var(--secondary-color);
        position: relative;
    }

    .hero-description {
        font-size: 1.2rem;
        color: var(--dark-gray);
        margin-bottom: 30px;
        line-height: 1.6;
    }

    .hero-buttons {
        display: flex;
        gap: 15px;
        flex-wrap: wrap;
    }

    .hero-image img {
        border-radius: 20px;
        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
    }

    /* Features Section */
    .features-section {
        padding: 80px 0;
        background: var(--white);
    }

    .feature-card {
        text-align: center;
        padding: 30px 20px;
        border-radius: var(--border-radius);
        transition: all 0.3s ease;
        height: 100%;
    }

    .feature-card:hover {
        transform: translateY(-5px);
        box-shadow: var(--shadow);
    }

    .feature-icon {
        width: 80px;
        height: 80px;
        background: linear-gradient(135deg, var(--secondary-color), #3742fa);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto 20px;
        color: var(--white);
        font-size: 24px;
    }

    .feature-title {
        color: var(--primary-color);
        font-weight: 600;
        margin-bottom: 15px;
    }

    .feature-description {
        color: var(--dark-gray);
        line-height: 1.6;
    }

    /* Categories Section */
    .categories-section {
        padding: 80px 0;
        background: var(--light-gray);
    }

    .section-header {
        text-align: center;
        margin-bottom: 60px;
    }

    .section-title {
        font-size: 2.5rem;
        font-weight: 700;
        color: var(--primary-color);
        margin-bottom: 15px;
    }

    .section-subtitle {
        font-size: 1.1rem;
        color: var(--dark-gray);
        max-width: 600px;
        margin: 0 auto;
    }

    .category-card {
        border-radius: 15px;
        overflow: hidden;
        box-shadow: var(--shadow);
        transition: all 0.3s ease;
    }

    .category-card:hover {
        transform: translateY(-10px);
        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
    }

    .category-image {
        position: relative;
        height: 300px;
        overflow: hidden;
    }

    .category-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.3s ease;
    }

    .category-card:hover .category-image img {
        transform: scale(1.1);
    }

    .category-overlay {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: linear-gradient(45deg, rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.3));
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        color: var(--white);
    }

    .category-title {
        font-size: 1.5rem;
        font-weight: 600;
        margin-bottom: 15px;
    }

    /* Featured Products Section */
    .featured-products-section {
        padding: 80px 0;
        background: var(--white);
    }

    .product-card {
        border-radius: 15px;
        overflow: hidden;
        box-shadow: var(--shadow);
        transition: all 0.3s ease;
        background: var(--white);
    }

    .product-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
    }

    .product-image {
        position: relative;
        height: 250px;
        overflow: hidden;
    }

    .product-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.3s ease;
    }

    .product-card:hover .product-image img {
        transform: scale(1.05);
    }

    .product-overlay {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(0, 0, 0, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        opacity: 0;
        transition: opacity 0.3s ease;
    }

    .product-card:hover .product-overlay {
        opacity: 1;
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
    }

    .badge-hot {
        background: var(--accent-color);
        color: var(--white);
    }

    .badge-new {
        background: var(--success-color);
        color: var(--white);
    }

    .badge-sale {
        background: var(--warning-color);
        color: var(--white);
    }

    .badge-out-stock {
        background: #6c757d;
        color: var(--white);
    }

    .product-category {
        color: var(--dark-gray);
        font-size: 0.9rem;
        margin-bottom: 8px;
    }

    .stock-warning {
        display: block;
        color: var(--warning-color);
        font-size: 0.8rem;
        margin-top: 5px;
    }

    .product-info {
        padding: 20px;
    }

    .product-name {
        color: var(--primary-color);
        font-weight: 600;
        margin-bottom: 10px;
    }

    .product-rating {
        margin-bottom: 10px;
    }

    .product-rating i {
        color: #ffc107;
        font-size: 14px;
    }

    .rating-count {
        color: var(--dark-gray);
        font-size: 14px;
        margin-left: 5px;
    }

    .product-price {
        display: flex;
        align-items: center;
        gap: 10px;
    }

    .price-current {
        color: var(--accent-color);
        font-weight: 700;
        font-size: 1.1rem;
    }

    .price-old {
        color: var(--dark-gray);
        text-decoration: line-through;
        font-size: 0.9rem;
    }

    /* Newsletter Section */
    .newsletter-section {
        padding: 60px 0;
        background: linear-gradient(135deg, var(--primary-color) 0%, #34495e 100%);
        color: var(--white);
    }

    .newsletter-title {
        font-size: 2rem;
        font-weight: 700;
        margin-bottom: 15px;
    }

    .newsletter-description {
        font-size: 1.1rem;
        margin-bottom: 0;
        opacity: 0.9;
    }

    .newsletter-form .form-control {
        border: none;
        padding: 15px 20px;
        border-radius: 25px 0 0 25px;
        font-size: 16px;
    }

    .newsletter-form .btn {
        border-radius: 0 25px 25px 0;
        padding: 15px 25px;
        font-weight: 600;
    }

    /* Responsive */
    @media (max-width: 991px) {
        .hero-title {
            font-size: 2.5rem;
        }
        
        .hero-buttons {
            justify-content: center;
        }
        
        .section-title {
            font-size: 2rem;
        }
        
        .newsletter-form {
            margin-top: 30px;
        }
    }

    @media (max-width: 768px) {
        .hero-section {
            padding: 60px 0;
            text-align: center;
        }
        
        .hero-title {
            font-size: 2rem;
        }
        
        .hero-description {
            font-size: 1rem;
        }
        
        .features-section,
        .categories-section,
        .featured-products-section {
            padding: 60px 0;
        }
        
        .category-image {
            height: 250px;
        }
        
        .product-image {
            height: 200px;
        }
    }
</style>

<jsp:include page="common/footer.jsp" />

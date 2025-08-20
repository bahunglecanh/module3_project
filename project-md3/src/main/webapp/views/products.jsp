<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Sản phẩm" />
<jsp:include page="common/header.jsp" />

<!-- Breadcrumb -->
<nav aria-label="breadcrumb" class="breadcrumb-nav">
    <div class="container">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
            <li class="breadcrumb-item active" aria-current="page">Sản phẩm</li>
        </ol>
    </div>
</nav>

<!-- Products Page -->
<div class="products-page">
    <div class="container">
        <div class="row">
            <!-- Sidebar Filters -->
            <div class="col-lg-3">
                <div class="filters-sidebar">
                    <h5 class="filter-title">Danh mục</h5>
                    <ul class="category-list">
                        <li><a href="${pageContext.request.contextPath}/products" class="category-link ${empty selectedCategory ? 'active' : ''}">Tất cả sản phẩm</a></li>
                        <c:forEach var="category" items="${categories}">
                            <li><a href="${pageContext.request.contextPath}/products?category=${category.id}" class="category-link ${selectedCategory eq category.id ? 'active' : ''}">${category.name}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            
            <!-- Products Content -->
            <div class="col-lg-9">
                <div class="products-header">
                    <h2>
                        <c:choose>
                            <c:when test="${not empty searchQuery}">
                                Kết quả tìm kiếm cho "<span class="text-primary">${searchQuery}</span>"
                            </c:when>
                            <c:when test="${not empty selectedCategory}">
                                <c:forEach var="category" items="${categories}">
                                    <c:if test="${selectedCategory eq category.id}">
                                        ${category.name}
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                Tất cả sản phẩm
                            </c:otherwise>
                        </c:choose>
                    </h2>
                    <p class="products-count">Có ${totalProducts} sản phẩm</p>
                </div>
                
                <!-- Products Grid -->
                <div class="products-grid">
                    <c:choose>
                        <c:when test="${not empty products}">
                            <div class="row">
                                <c:forEach var="product" items="${products}">
                                    <div class="col-lg-4 col-md-6 mb-4">
                                        <div class="product-card">
                                            <div class="product-image">
                                                <c:choose>
                                                    <c:when test="${not empty product.imageUrl}">
                                                        <img src="${product.imageUrl}" alt="${product.name}" class="img-fluid lazy" loading="lazy">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="https://images.unsplash.com/photo-1549298916-b41d501d3772?ixlib=rb-4.0.3&auto=format&fit=crop&w=400&q=80" 
                                                             alt="${product.name}" class="img-fluid lazy" loading="lazy">
                                                    </c:otherwise>
                                                </c:choose>
                                                
                                                <div class="product-overlay">
                                                    <a href="${pageContext.request.contextPath}/detail/${product.id}" class="btn btn-primary btn-sm">
                                                        <i class="fas fa-eye me-1"></i>Xem chi tiết
                                                    </a>
                                                </div>
                                                
                                                <c:if test="${not product.inStock}">
                                                    <div class="product-badges">
                                                        <span class="badge badge-out-stock">Hết hàng</span>
                                                    </div>
                                                </c:if>
                                            </div>
                                            
                                            <div class="product-info">
                                                <h6 class="product-name">${product.name}</h6>
                                                
                                                <c:if test="${not empty product.brandName}">
                                                    <p class="product-brand">${product.brandName}</p>
                                                </c:if>
                                                
                                                <c:if test="${not empty product.categoryName}">
                                                    <p class="product-category">${product.categoryName}</p>
                                                </c:if>
                                                
                                                <div class="product-rating">
                                                    <i class="fas fa-star"></i>
                                                    <i class="fas fa-star"></i>
                                                    <i class="fas fa-star"></i>
                                                    <i class="fas fa-star"></i>
                                                    <i class="far fa-star"></i>
                                                    <span class="rating-count">(120)</span>
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
                            </div>
                            
                            <!-- Phân trang -->
                            <c:if test="${totalPages > 1}">
                                <div class="pagination-container mt-4">
                                    <nav aria-label="Product pagination">
                                        <ul class="pagination justify-content-center">
                                            <!-- Previous button -->
                                            <c:if test="${hasPrevPage}">
                                                <li class="page-item">
                                                    <a class="page-link" href="?page=${currentPage - 1}<c:if test='${not empty selectedCategory}'>&category=${selectedCategory}</c:if>">
                                                        <i class="fas fa-chevron-left"></i> Trước
                                                    </a>
                                                </li>
                                            </c:if>
                                            
                                            <!-- Page numbers -->
                                            <c:forEach var="pageNum" begin="1" end="${totalPages}">
                                                <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                                    <a class="page-link" href="?page=${pageNum}<c:if test='${not empty selectedCategory}'>&category=${selectedCategory}</c:if>">
                                                        ${pageNum}
                                                    </a>
                                                </li>
                                            </c:forEach>
                                            
                                            <!-- Next button -->
                                            <c:if test="${hasNextPage}">
                                                <li class="page-item">
                                                    <a class="page-link" href="?page=${currentPage + 1}<c:if test='${not empty selectedCategory}'>&category=${selectedCategory}</c:if>">
                                                        Sau <i class="fas fa-chevron-right"></i>
                                                    </a>
                                                </li>
                                            </c:if>
                                        </ul>
                                    </nav>
                                    
                                    <!-- Page info -->
                                    <div class="text-center mt-3">
                                        <small class="text-muted">
                                            Trang ${currentPage} / ${totalPages} - 
                                            Hiển thị ${(currentPage - 1) * productsPerPage + 1} - 
                                            ${currentPage * productsPerPage > totalProducts ? totalProducts : currentPage * productsPerPage} 
                                            trong tổng số ${totalProducts} sản phẩm
                                        </small>
                                    </div>
                                </div>
                            </c:if>
                            
                            <!-- Nếu chỉ có 1 trang -->
                            <c:if test="${totalPages <= 1 && totalProducts > 0}">
                                <div class="text-center mt-4">
                                    <small class="text-muted">Hiển thị tất cả ${totalProducts} sản phẩm</small>
                                </div>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <div class="no-products">
                                <div class="text-center py-5">
                                    <i class="fas fa-search fa-3x text-muted mb-3"></i>
                                    <c:choose>
                                        <c:when test="${not empty searchQuery}">
                                            <h4>Không tìm thấy sản phẩm nào</h4>
                                            <p class="text-muted">
                                                Không có sản phẩm nào phù hợp với từ khóa "<strong>${searchQuery}</strong>".
                                                <br>Hãy thử tìm kiếm với từ khóa khác.
                                            </p>
                                            <div class="mt-3">
                                                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary me-2">
                                                    <i class="fas fa-th-large me-1"></i>Xem tất cả sản phẩm
                                                </a>
                                                <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">
                                                    <i class="fas fa-home me-1"></i>Về trang chủ
                                                </a>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <h4>Chưa có sản phẩm nào</h4>
                                            <p class="text-muted">Hiện tại chưa có sản phẩm nào để hiển thị.</p>
                                            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                                                <i class="fas fa-home me-2"></i>Về trang chủ
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    /* Breadcrumb */
    .breadcrumb-nav {
        background: var(--light-gray);
        padding: 15px 0;
        margin-bottom: 0;
    }
    
    .breadcrumb {
        margin-bottom: 0;
        background: transparent;
    }
    
    .breadcrumb-item a {
        color: var(--primary-color);
        text-decoration: none;
    }
    
    .breadcrumb-item.active {
        color: var(--text-color);
    }
    
    /* Products Page */
    .products-page {
        padding: 60px 0;
        background-color: var(--white);
    }
    
    /* Sidebar */
    .filters-sidebar {
        background: var(--light-gray);
        padding: 30px;
        border-radius: 8px;
        box-shadow: var(--shadow);
        position: sticky;
        top: 100px;
    }
    
    .filter-title {
        color: var(--dark-blue);
        font-weight: 600;
        margin-bottom: 20px;
        font-size: 1.2rem;
    }
    
    .category-list {
        list-style: none;
        padding: 0;
        margin: 0;
    }
    
    .category-list li {
        margin-bottom: 12px;
    }
    
    .category-link {
        color: var(--text-color);
        text-decoration: none;
        font-size: 1rem;
        padding: 8px 12px;
        border-radius: 5px;
        display: block;
        transition: all 0.3s ease;
    }
    
    .category-link:hover,
    .category-link.active {
        color: var(--primary-color);
        background: var(--white);
        font-weight: 500;
    }
    
    /* Products Header */
    .products-header {
        margin-bottom: 30px;
    }
    
    .products-header h2 {
        color: var(--dark-blue);
        font-weight: 700;
        margin-bottom: 10px;
    }
    
    .products-count {
        color: var(--dark-gray);
        font-size: 1rem;
        margin-bottom: 0;
    }
    
    /* Product Cards */
    .product-card {
        border: 1px solid var(--border-color);
        border-radius: 8px;
        overflow: hidden;
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        background-color: var(--white);
        height: 100%;
        display: flex;
        flex-direction: column;
    }
    
    .product-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
    }
    
    .product-image {
        position: relative;
        overflow: hidden;
        padding-top: 75%;
        background-color: #f8f9fa;
    }
    
    .product-image img {
        position: absolute;
        top: 0;
        left: 0;
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
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
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
        top: 10px;
        left: 10px;
        display: flex;
        gap: 5px;
    }
    
    .badge {
        padding: 5px 10px;
        border-radius: 5px;
        font-size: 0.75rem;
        font-weight: 600;
    }
    
    .badge-out-stock {
        background: #6c757d;
        color: var(--white);
    }
    
    .product-info {
        padding: 20px;
        flex-grow: 1;
        display: flex;
        flex-direction: column;
    }
    
    .product-name {
        font-size: 1.1rem;
        font-weight: 600;
        color: var(--dark-blue);
        margin-bottom: 8px;
    }
    
    .product-brand {
        color: var(--primary-color);
        font-size: 0.85rem;
        font-weight: 500;
        margin-bottom: 5px;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }
    
    .product-category {
        color: var(--dark-gray);
        font-size: 0.9rem;
        margin-bottom: 8px;
    }
    
    .product-rating {
        color: var(--warning-color);
        font-size: 0.9rem;
        margin-bottom: 8px;
    }
    
    .rating-count {
        color: var(--text-color);
        margin-left: 5px;
    }
    
    .product-price {
        font-size: 1.2rem;
        font-weight: 700;
        color: var(--primary-color);
        margin-top: auto;
    }
    
    .stock-warning {
        display: block;
        color: var(--warning-color);
        font-size: 0.8rem;
        margin-top: 5px;
    }
    
    /* No Products */
    .no-products {
        background: var(--white);
        border-radius: 8px;
        box-shadow: var(--shadow);
    }
    
    /* Pagination */
    .pagination-container {
        margin-top: 2rem;
    }
    
    .pagination .page-link {
        color: var(--primary-color);
        border: 1px solid var(--border-color);
        padding: 8px 12px;
        margin: 0 2px;
        border-radius: 6px;
        transition: all 0.3s ease;
    }
    
    .pagination .page-link:hover {
        background-color: var(--secondary-color);
        color: var(--white);
        border-color: var(--secondary-color);
    }
    
    .pagination .page-item.active .page-link {
        background-color: var(--secondary-color);
        border-color: var(--secondary-color);
        color: var(--white);
    }
    
    .pagination .page-item.disabled .page-link {
        color: var(--dark-gray);
        background-color: var(--light-gray);
        border-color: var(--border-color);
    }
    
    /* Responsive */
    @media (max-width: 991px) {
        .filters-sidebar {
            position: static;
            margin-bottom: 30px;
        }
        
        .product-image {
            height: 200px;
        }
        
        .filters-sidebar {
            padding: 20px;
        }
    }
</style>

<jsp:include page="common/footer.jsp" />
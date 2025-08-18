# 👟 Shoe Store - Website Bán Giày

Website bán giày được xây dựng bằng Java JSP/Servlet theo mô hình MVC

## 🚀 Cách chạy project

### 1. Chuẩn bị Database
```sql
-- 1. Tạo database MySQL
-- 2. Chạy file database_schema.sql để tạo tables và dữ liệu mẫu
-- 3. Database sẽ có admin user mặc định:
--    Username: admin
--    Password: 123456
```

### 2. Cấu hình Database Connection
Kiểm tra file `src/main/java/hunglcb/example/projectmd3/repository/ConnectionDB.java`:
```java
private static final String URL ="jdbc:mysql://localhost:3306/shoe_store";
private static final String USER ="root";
private static final String PASS ="bahung123"; // Thay đổi theo MySQL của bạn
```

### 3. Build và Deploy
```bash
# Build project với Gradle
./gradlew build

# Deploy file WAR lên Tomcat server
# Hoặc chạy trực tiếp từ IDE
```

### 4. Truy cập Website
- **Trang chủ**: `http://localhost:8080/projectmd3/`
- **Đăng nhập**: `http://localhost:8080/projectmd3/auth/login`
- **Đăng ký**: `http://localhost:8080/projectmd3/auth/register`

## 📁 Cấu trúc Project (MVC)

```
📦 src/main/java/hunglcb/example/projectmd3/
├── 📂 model/           # Entities (User, Product...)
├── 📂 repository/      # Data Access Layer (Repository Pattern)
├── 📂 service/         # Business Logic Layer
├── 📂 controller/      # Servlet Controllers (MVC Controller)
└── 📂 filter/          # Filters (Authentication, Encoding...)

📦 src/main/webapp/
├── 📂 views/           # JSP Views (MVC View)
│   ├── 📂 auth/        # Login, Register pages
│   ├── 📂 common/      # Header, Footer
│   └── home.jsp        # Homepage
└── 📂 WEB-INF/         # Web configuration
```

## ⚡ Tính năng đã hoàn thành

### 🔐 Authentication System
- ✅ Đăng ký tài khoản với validation
- ✅ Đăng nhập với username/email + password  
- ✅ Đăng xuất và quản lý session
- ✅ Phân quyền User/Admin
- ✅ Password hashing (SHA-256)

### 🏠 User Interface  
- ✅ Homepage responsive với Bootstrap 5
- ✅ Header/Footer layout đẹp
- ✅ Login/Register forms với validation
- ✅ Thiết kế hiện đại, màu trắng chủ đạo

### 🛡️ Security Features
- ✅ Character encoding filter (UTF-8)
- ✅ Authentication filter cho protected routes
- ✅ Input validation và sanitization
- ✅ Session management

## 🔧 Technology Stack

- **Backend**: Java 8, JSP/Servlet
- **Frontend**: Bootstrap 5, Font Awesome, CSS3
- **Database**: MySQL 8.0
- **Build Tool**: Gradle
- **Server**: Apache Tomcat 9+

## 📋 TODO - Tính năng sẽ phát triển

- [ ] Product Management (CRUD sản phẩm)
- [ ] Shopping Cart & Checkout
- [ ] Order Management  
- [ ] Admin Dashboard
- [ ] Product Search & Filter
- [ ] Product Reviews & Ratings
- [ ] Wishlist
- [ ] Email notifications

## 👥 User Roles

### 👤 USER (Khách hàng)
- Xem sản phẩm, thêm vào giỏ hàng
- Đặt hàng, theo dõi đơn hàng
- Quản lý thông tin cá nhân

### 👨‍💼 ADMIN (Quản trị viên)  
- Quản lý sản phẩm, danh mục
- Quản lý đơn hàng, người dùng
- Xem báo cáo, thống kê

---
*Dự án được phát triển theo mô hình MVC với kiến trúc clean, maintainable và enterprise-ready.*

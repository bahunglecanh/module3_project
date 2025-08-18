# 🧪 Hướng dẫn Test Website Shoe Store

## 📋 Checklist Test các chức năng chính

### 1. 🗄️ Database Setup
- [ ] Tạo database `shoe_store` 
- [ ] Import file `database_schema.sql`
- [ ] Kiểm tra có admin user: `admin/123456`
- [ ] Kiểm tra connection trong `ConnectionDB.java`

### 2. 🏠 Homepage Test
**URL**: `http://localhost:8080/projectmd3/`

**Expected Results**:
- [ ] Trang load thành công
- [ ] Header có logo "Shoe Store"
- [ ] Navigation menu đầy đủ (Trang chủ, Sản phẩm, Giới thiệu, Liên hệ)
- [ ] Hero section với title "Bộ sưu tập giày thời trang mới nhất"
- [ ] Features section (4 features: Giao hàng nhanh, Đổi trả dễ dàng, Bảo hành chất lượng, Hỗ trợ 24/7)
- [ ] Categories section (3 categories: Giày thể thao, Giày công sở, Giày cao gót)
- [ ] Featured products section (4 sản phẩm mẫu)
- [ ] Newsletter section
- [ ] Footer đầy đủ thông tin
- [ ] Responsive design trên mobile

**Navigation Test**:
- [ ] Click "Đăng nhập" → redirect to `/auth/login`
- [ ] Click "Đăng ký" → redirect to `/auth/register`

### 3. 📝 Register Test
**URL**: `http://localhost:8080/projectmd3/auth/register`

**Test Cases**:

#### ✅ Valid Registration:
- **Input**: 
  - Username: `testuser`
  - Email: `test@gmail.com`
  - Password: `123456`
  - Confirm Password: `123456`
  - Full Name: `Test User`
- **Expected**: Success message + link to login

#### ❌ Invalid Cases:
- **Duplicate Username**: 
  - Input: username `admin` (already exists)
  - Expected: Error "Tên đăng nhập đã tồn tại!"

- **Password Mismatch**:
  - Password: `123456`, Confirm: `654321`
  - Expected: Error "Xác nhận mật khẩu không khớp!"

- **Invalid Email**:
  - Email: `invalid-email`
  - Expected: Error "Email không hợp lệ!"

- **Short Password**:
  - Password: `123`
  - Expected: Error "Mật khẩu phải có ít nhất 6 ký tự!"

### 4. 🔐 Login Test  
**URL**: `http://localhost:8080/projectmd3/auth/login`

**Test Cases**:

#### ✅ Valid Login (Admin):
- **Input**: `admin` / `123456`
- **Expected**: Redirect to `/admin/dashboard` (nếu có) hoặc homepage với user menu

#### ✅ Valid Login (User):
- **Input**: `testuser` / `123456`
- **Expected**: Redirect to homepage với user menu hiển thị

#### ❌ Invalid Cases:
- **Wrong Username**: `wronguser` / `123456`
- **Wrong Password**: `admin` / `wrongpass`
- **Expected**: Error "Tên đăng nhập/email hoặc mật khẩu không đúng!"

### 5. 👤 Session Management Test

#### After Login:
- [ ] Header shows user avatar with first letter of name
- [ ] Dropdown menu có: "Thông tin cá nhân", "Đơn hàng của tôi"
- [ ] Admin user có thêm menu "Quản trị"
- [ ] Cart icon hiển thị với badge "0"

#### Logout Test:
- [ ] Click "Đăng xuất" → redirect to login page
- [ ] Session cleared, header shows "Đăng nhập" + "Đăng ký" buttons

### 6. 🛡️ Security Test

#### Authentication Filter:
- **Direct access without login**:
  - `/admin/*` → redirect to login
  - `/profile/*` → redirect to login  
  - `/orders/*` → redirect to login
  - `/cart/*` → redirect to login

#### Role-based Access:
- **Regular user try to access admin area**:
  - Expected: 403 Forbidden error

### 7. 🎨 UI/UX Test

#### Visual Check:
- [ ] White background (theo yêu cầu)
- [ ] Modern design với blue/navy color scheme
- [ ] Bootstrap responsive layout
- [ ] Font Awesome icons hiển thị đúng
- [ ] Hover effects hoạt động
- [ ] Form validation messages hiển thị đúng

#### Mobile Responsive:
- [ ] Test trên screen < 768px
- [ ] Hamburger menu hoạt động
- [ ] Forms vẫn dễ sử dụng
- [ ] Images scale properly

### 8. 🐛 Error Handling Test

#### Database Connection:
- **Stop MySQL server** → check error handling
- **Wrong connection string** → check error handling

#### Error Pages:
- **Access non-existent URL**: `/nonexistent` → should show 404
- **Server error** → should show 500 page

## 📊 Test Results Template

```
✅ PASSED | ❌ FAILED | ⚠️  PARTIAL

[ ] Homepage loads and displays correctly
[ ] Registration with valid data works
[ ] Registration validation catches errors  
[ ] Login with correct credentials works
[ ] Login with wrong credentials shows error
[ ] Session management works correctly
[ ] Authentication filter protects routes
[ ] Role-based access control works
[ ] UI is responsive and matches design
[ ] Error handling works properly

NOTES:
- Issue 1: ...
- Issue 2: ...
```

## 🚀 Ready for Next Phase

Sau khi tất cả test cases pass, có thể tiếp tục với:
1. Product Management System
2. Shopping Cart & Checkout
3. Admin Dashboard
4. Order Management

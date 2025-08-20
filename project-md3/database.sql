
-- ==========================
-- 1. Accounts (Tài khoản + phân quyền)
-- ==========================
CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('user','admin') DEFAULT 'user',
    status ENUM('active','inactive','banned') DEFAULT 'active',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ==========================
-- 2. User Profiles (Thông tin cá nhân)
-- ==========================
CREATE TABLE user_profiles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT UNIQUE,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    gender ENUM('male','female','other'),
    birth_date DATE,
    avatar_url VARCHAR(255),
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- ==========================
-- 3. User Addresses (Địa chỉ giao hàng)
-- ==========================
CREATE TABLE user_addresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    address_line VARCHAR(255) NOT NULL,
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100) DEFAULT 'Vietnam',
    is_default BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- ==========================
-- 4. Categories (Danh mục sản phẩm)
-- ==========================
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

-- ==========================
-- 5. Products (Sản phẩm)
-- ==========================
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT DEFAULT 0,
    image_url VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- ==========================
-- 6. Carts (Giỏ hàng)
-- ==========================
CREATE TABLE carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- ==========================
-- 7. Cart Items (Chi tiết giỏ hàng)
-- ==========================
CREATE TABLE cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT DEFAULT 1,
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- ==========================
-- 8. Orders (Đơn hàng)
-- ==========================
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    shipping_address_id INT,
    status ENUM('pending','confirmed','shipped','delivered','cancelled') DEFAULT 'pending',
    total_amount DECIMAL(10,2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
    FOREIGN KEY (shipping_address_id) REFERENCES user_addresses(id) ON DELETE SET NULL
);

-- ==========================
-- 9. Order Items (Chi tiết đơn hàng)
-- ==========================
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- ==========================
-- 10. Messages (Tin nhắn hỗ trợ)
-- ==========================
CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    message TEXT NOT NULL,
    response TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- ==========================
-- 11. Notifications (Thông báo)
-- ==========================
CREATE TABLE notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- ==========================
-- 1. Accounts
-- ==========================
INSERT INTO accounts (email, password_hash, role, status)
VALUES 
('john@example.com', 'hashed_password1', 'user', 'active'),
('jane@example.com', 'hashed_password2', 'user', 'active'),
('admin@example.com', 'hashed_admin_password', 'admin', 'active');

-- ==========================
-- 2. User Profiles
-- ==========================
INSERT INTO user_profiles (account_id, full_name, phone, gender, birth_date, avatar_url)
VALUES
(1, 'John Doe', '0901234567', 'male', '1995-05-15', 'avatars/john.png'),
(2, 'Jane Smith', '0912345678', 'female', '1997-08-22', 'avatars/jane.png'),
(3, 'System Admin', '0999999999', 'other', '1990-01-01', 'avatars/admin.png');

-- ==========================
-- 3. User Addresses
-- ==========================
INSERT INTO user_addresses (account_id, address_line, city, state, postal_code, country, is_default)
VALUES
(1, '123 Lê Lợi', 'Hồ Chí Minh', 'HCM', '700000', 'Vietnam', TRUE),
(2, '456 Nguyễn Huệ', 'Hà Nội', 'HN', '100000', 'Vietnam', TRUE);

-- ==========================
-- 4. Categories
-- ==========================
INSERT INTO categories (name, description)
VALUES
('Sneakers', 'Giày sneaker thời trang, thể thao'),
('Sandals', 'Giày dép sandal'),
('Boots', 'Giày boots'),
('Formal Shoes', 'Giày tây, giày công sở');

-- ==========================
-- 5. Products (12 sản phẩm bạn đã có)
-- ==========================
INSERT INTO products (category_id, name, description, price, stock_quantity, image_url)
VALUES
(1, 'Nike Air Force 1', 'Giày sneaker Nike cổ điển', 2500000, 50, 'https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/20547d52-3e1b-4c3d-b917-f0d7e0eb8bdf/custom-nike-air-force-1-low-by-you-shoes.png'),
(1, 'Adidas Ultraboost', 'Giày chạy bộ Adidas thoải mái', 3000000, 40, 'https://assets.adidas.com/images/w_600,f_auto,q_auto/d2a64cf9cd824e5d9fcc950b5eb0b2c8_9366/Giay_Ultraboost_5_Mau_xanh_da_troi_ID8817_HM1.jpg'),
(1, 'Puma Suede Classic', 'Giày Puma Suede phong cách retro', 2000000, 30, 'https://supersports.com.vn/cdn/shop/files/39520501-1_1200x1200.jpg?v=1714986856'),
(1, 'Converse Chuck Taylor', 'Giày Converse cổ điển', 1500000, 60, 'https://www.converse.vn/media/catalog/product/0/8/0882-CONM9160C00011H-1.jpg'),
(2, 'Dép Adidas Adilette', 'Dép thời trang Adidas', 800000, 100, 'https://product.hstatic.net/1000367250/product/ep_boi_adilette_be_gw8748_04_standard_f6740deb31c649eeac77b5a80a0aa857_f0e046bf0796402e9314f9be3c15ee95_master.jpg'),
(2, 'Dép Nike Benassi', 'Dép Nike tiện lợi', 700000, 80, 'https://product.hstatic.net/200000037626/product/img_2399_beba1f5bebfb47d4806ce059400f3318.png'),
(2, 'Sandals Bitis Hunter', 'Sandals thể thao Biti’s', 600000, 70, 'https://product.hstatic.net/1000230642/product/hem000700kem1_2e0a521c4112491ea8a254c8fc622c1d.jpg'),
(3, 'Dr. Martens 1460', 'Giày boots da cổ điển', 3500000, 20, 'https://photos6.spartoo.eu/photos/278/27855019/27855019_1200_A.jpg'),
(3, 'Timberland Premium Boot', 'Giày Timberland chống nước', 4000000, 15, 'https://assets.timberland.com/images/t_img/f_auto,h_650,w_650,e_sharpen:60/dpr_2.0/v1741199070/TB110061713-HERO/Mens-Timberland-Premium-6Inch-Waterproof-Boot.png'),
(4, 'Oxford Leather Shoes', 'Giày tây Oxford da bò', 2800000, 25, 'https://www.politix.com.au/dw/image/v2/ABBA_PRD/on/demandware.static/-/Sites-politix-master-catalog/default/dwa6b468c3/images/hires/Winter%2024/G3%20Accessories/GF02-BLACK-1.jpg?sw=2500&sh=3000&sm=cut'),
(4, 'Derby Formal Shoes', 'Giày tây Derby sang trọng', 2600000, 20, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVFQRNye0NXiyCcYIoszsJmKDXyAgBGF261g&s'),
(4, 'Loafer Leather Shoes', 'Giày Loafer tiện lợi', 2400000, 30, 'https://bizweb.dktcdn.net/thumb/large/100/292/624/products/dscf1272.jpg?v=1737026214710');

-- ==========================
-- 6. Carts
-- ==========================
INSERT INTO carts (account_id)
VALUES
(1), (2);

-- ==========================
-- 7. Cart Items
-- ==========================
INSERT INTO cart_items (cart_id, product_id, quantity)
VALUES
(1, 1, 2),  -- John mua 2 đôi Nike Air Force 1
(1, 5, 1),  -- John mua 1 đôi Dép Adidas
(2, 2, 1);  -- Jane mua 1 đôi Ultraboost

-- ==========================
-- 8. Orders
-- ==========================
INSERT INTO orders (account_id, shipping_address_id, status, total_amount)
VALUES
(1, 1, 'confirmed', 5800000), -- John
(2, 2, 'pending', 3000000);   -- Jane

-- ==========================
-- 9. Order Items
-- ==========================
INSERT INTO order_items (order_id, product_id, quantity, price)
VALUES
(1, 1, 2, 2500000), -- 2 x Nike AF1
(1, 5, 1, 800000),  -- 1 x Dép Adidas
(2, 2, 1, 3000000); -- 1 x Ultraboost

-- ==========================
-- 10. Messages
-- ==========================
INSERT INTO messages (account_id, message, response)
VALUES
(1, 'Khi nào giao hàng đến vậy?', 'Đơn hàng sẽ được giao trong 3 ngày.'),
(2, 'Có size 37 cho Ultraboost không?', 'Dạ, hiện tại còn size 37.');

-- ==========================
-- 11. Notifications
-- ==========================
INSERT INTO notifications (account_id, message, is_read)
VALUES
(1, 'Đơn hàng của bạn đã được xác nhận!', FALSE),
(2, 'Bạn có tin nhắn từ Admin.', FALSE);


-- ========================================
-- SIMPLE DATABASE IMPROVEMENTS FOR SHOE STORE
-- Chỉ thêm 4 bảng thiết yếu cho web bán giày
-- ========================================

-- ==========================
-- 1. BRANDS (Thương hiệu giày)
-- ==========================
CREATE TABLE brands (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    logo_url VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ==========================
-- 2. PRODUCT_SIZES (Size giày và stock)
-- ==========================
CREATE TABLE product_sizes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    size VARCHAR(10) NOT NULL,  -- '38', '39', '40', etc.
    stock_quantity INT DEFAULT 0,
    price_adjustment DECIMAL(10,2) DEFAULT 0.00, -- +/- giá theo size
    is_available BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE KEY unique_product_size (product_id, size)
);

-- ==========================
-- 3. PRODUCT_IMAGES (Nhiều ảnh cho 1 sản phẩm)
-- ==========================
CREATE TABLE product_images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    alt_text VARCHAR(255),
    display_order INT DEFAULT 0,
    is_main_image BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- ==========================
-- 4. PAYMENT_METHODS (Phương thức thanh toán)
-- ==========================
CREATE TABLE payment_methods (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- MODIFY EXISTING TABLES (CHỈ THÊM VÀI TRƯỜNG CẦN THIẾT)
-- ========================================

-- Thêm brand_id vào products
ALTER TABLE products 
ADD COLUMN brand_id INT,
ADD FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE SET NULL;

-- Thêm size và color vào cart_items
ALTER TABLE cart_items 
ADD COLUMN size_id INT,
ADD FOREIGN KEY (size_id) REFERENCES product_sizes(id) ON DELETE SET NULL;

-- Thêm size vào order_items
ALTER TABLE order_items 
ADD COLUMN size_id INT,
ADD FOREIGN KEY (size_id) REFERENCES product_sizes(id) ON DELETE SET NULL;

-- Thêm payment_method vào orders
ALTER TABLE orders 
ADD COLUMN payment_method_id INT,
ADD FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id) ON DELETE SET NULL;

-- ========================================
-- SAMPLE DATA
-- ========================================

-- Insert Brands
INSERT INTO brands (name, description, logo_url) VALUES
('Nike', 'Just Do It - Thương hiệu thể thao hàng đầu', 'brands/nike-logo.png'),
('Adidas', 'Impossible is Nothing - Ba sọc nổi tiếng', 'brands/adidas-logo.png'),
('Puma', 'Forever Faster - Báo đen năng động', 'brands/puma-logo.png'),
('Converse', 'All Star - Phong cách cổ điển', 'brands/converse-logo.png'),
('Biti\'s', 'Thương hiệu Việt Nam chất lượng', 'brands/bitis-logo.png'),
('Dr. Martens', 'The Original - Boots huyền thoại', 'brands/drmartens-logo.png'),
('Timberland', 'Nature Needs Heroes - Boots chống nước', 'brands/timberland-logo.png');

-- Update products với brand_id
UPDATE products SET brand_id = 1 WHERE name LIKE '%Nike%';
UPDATE products SET brand_id = 2 WHERE name LIKE '%Adidas%';
UPDATE products SET brand_id = 3 WHERE name LIKE '%Puma%';
UPDATE products SET brand_id = 4 WHERE name LIKE '%Converse%';
UPDATE products SET brand_id = 5 WHERE name LIKE '%Bitis%';
UPDATE products SET brand_id = 6 WHERE name LIKE '%Dr. Martens%';
UPDATE products SET brand_id = 7 WHERE name LIKE '%Timberland%';

-- Insert Product Sizes (Ví dụ cho sản phẩm Nike Air Force 1 - id=1)
INSERT INTO product_sizes (product_id, size, stock_quantity, price_adjustment) VALUES
(1, '38', 5, 0),
(1, '39', 8, 0),
(1, '40', 12, 0),
(1, '41', 15, 0),
(1, '42', 10, 0),
(1, '43', 7, 0),
(1, '44', 3, 0),
(1, '45', 2, 0);

-- Insert thêm sizes cho Adidas Ultraboost (id=2)
INSERT INTO product_sizes (product_id, size, stock_quantity, price_adjustment) VALUES
(2, '38', 3, 0),
(2, '39', 6, 0),
(2, '40', 8, 0),
(2, '41', 12, 0),
(2, '42', 8, 0),
(2, '43', 5, 0),
(2, '44', 2, 0);


-- Insert Payment Methods
INSERT INTO payment_methods (name, description, is_active) VALUES
('COD', 'Thanh toán khi nhận hàng', TRUE),
('Banking', 'Chuyển khoản ngân hàng', TRUE),
('MoMo', 'Ví điện tử MoMo', TRUE),
('ZaloPay', 'Ví điện tử ZaloPay', TRUE),
('VNPay', 'Cổng thanh toán VNPay', TRUE);

-- ========================================
-- USEFUL INDEXES FOR PERFORMANCE
-- ========================================
CREATE INDEX idx_products_brand ON products(brand_id);
CREATE INDEX idx_product_sizes_product ON product_sizes(product_id);
CREATE INDEX idx_product_sizes_size ON product_sizes(size);
CREATE INDEX idx_product_images_product ON product_images(product_id);
CREATE INDEX idx_product_images_main ON product_images(is_main_image);

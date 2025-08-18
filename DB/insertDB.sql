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

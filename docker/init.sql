-- docker/init.sql
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      nickname VARCHAR(255),
                      phone_number VARCHAR(20),
                      birth_date VARCHAR(20),
                      role VARCHAR(20) DEFAULT 'ROLE_USER',
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE idol (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      description VARCHAR(255),
                      image_url VARCHAR(255),
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE idol_like (
                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                   user_id BIGINT NOT NULL,
                   idol_id BIGINT NOT NULL,
                   created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (user_id) REFERENCES user(id),
                   FOREIGN KEY (idol_id) REFERENCES idol(id)
);


CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(50) NOT NULL
);
INSERT INTO category (name) VALUES ('의류');
INSERT INTO category (name) VALUES ('포토카드');
INSERT INTO category (name) VALUES ('응원봉');
INSERT INTO category (name) VALUES ('키링');



CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         category_id BIGINT NOT NULL,
                         price INT NOT NULL,
                         stock INT NOT NULL,
                         description TEXT,
                         thumbnail_url VARCHAR(255),
                         view_count INT DEFAULT 0,
                         available_from DATETIME,  -- 판매 시작 시각
                         created_by BIGINT,        -- MD user_id
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (category_id) REFERENCES category(id),
                         FOREIGN KEY (created_by) REFERENCES user(id)
);
INSERT INTO product (name, category_id, price, stock, description, thumbnail_url, view_count, available_from, created_by)
VALUES
    ('핑핑이 피규어', 1, 25000, 100, '핑핑이 한정판 피규어입니다.', 'https://example.com/image1.jpg', 12, '2025-06-01 10:00:00', 5),
    ('핑핑이 반팔티', 2, 32000, 50, '여름용 핑핑이 반팔티셔츠', 'https://example.com/image2.jpg', 4, '2025-06-03 14:00:00', 6),
    ('핑핑이 포토카드 세트', 3, 7000, 200, '10장 구성의 포토카드 세트', 'https://example.com/image3.jpg', 27, '2025-06-05 09:00:00', 14),
    ('핑핑이 키링', 4, 12000, 80, '가방에 달 수 있는 귀여운 키링', 'https://example.com/image4.jpg', 8, '2025-06-07 11:00:00', 14);



INSERT INTO user (email, nickname, phone_number, birth_date)
VALUES
    ('test1@example.com', '테스터1', '010-1111-2222', '2000-01-01'),
    ('test2@example.com', '테스터2', '010-3333-4444', '1999-12-31');


-- 특정 사용자의 모든 아이돌에 대한 like 조회
SELECT
    i.id,
    i.name,
    i.description,
    i.image_url,
    il.user_id,
    il.idol_id,
    CASE WHEN il.user_id IS NOT NULL THEN true ELSE false END AS liked
FROM
    idol i
        LEFT JOIN
    idol_like il
    ON
        i.id = il.idol_id AND il.user_id = 14
ORDER BY
    i.id;

ALTER TABLE product ADD COLUMN idol_id BIGINT;
ALTER TABLE product ADD CONSTRAINT fk_product_idol
    FOREIGN KEY (idol_id) REFERENCES idol(id);

ALTER TABLE user ADD COLUMN idol_id BIGINT;
ALTER TABLE user ADD CONSTRAINT fk_user_idol
    FOREIGN KEY (idol_id) REFERENCES idol(id);

SELECT p.name, i.name FROM product p left join idol i on p.idol_id = i.id;

INSERT INTO user (email, nickname, phone_number, birth_date, role, idol_id)
VALUES
    ('md1@example.com', 'MD1', '010-0001-0001', '1990-01-01', 'ROLE_MD', 1),
    ('md2@example.com', 'MD2', '010-0002-0002', '1990-01-02', 'ROLE_MD', 2),
    ('md3@example.com', 'MD3', '010-0003-0003', '1990-01-03', 'ROLE_MD', 3),
    ('md4@example.com', 'MD4', '010-0004-0004', '1990-01-04', 'ROLE_MD', 4),
    ('md5@example.com', 'MD5', '010-0005-0005', '1990-01-05', 'ROLE_MD', 5),
    ('md6@example.com', 'MD6', '010-0006-0006', '1990-01-06', 'ROLE_MD', 6),
    ('md7@example.com', 'MD7', '010-0007-0007', '1990-01-07', 'ROLE_MD', 7),
    ('md8@example.com', 'MD8', '010-0008-0008', '1990-01-08', 'ROLE_MD', 8),
    ('md9@example.com', 'MD9', '010-0009-0009', '1990-01-09', 'ROLE_MD', 9),
    ('md10@example.com', 'MD10', '010-0010-0010', '1990-01-10', 'ROLE_MD', 10),
    ('md11@example.com', 'MD11', '010-0011-0011', '1990-01-11', 'ROLE_MD', 11),
    ('md12@example.com', 'MD12', '010-0012-0012', '1990-01-12', 'ROLE_MD', 12),
    ('md13@example.com', 'MD13', '010-0013-0013', '1990-01-13', 'ROLE_MD', 13),
-- 14번은 생략!
    ('md15@example.com', 'MD15', '010-0015-0015', '1990-01-15', 'ROLE_MD', 15),
    ('md16@example.com', 'MD16', '010-0016-0016', '1990-01-16', 'ROLE_MD', 16),
    ('md17@example.com', 'MD17', '010-0017-0017', '1990-01-17', 'ROLE_MD', 17),
    ('md18@example.com', 'MD18', '010-0018-0018', '1990-01-18', 'ROLE_MD', 18),
    ('md19@example.com', 'MD19', '010-0019-0019', '1990-01-19', 'ROLE_MD', 19),
    ('md20@example.com', 'MD20', '010-0020-0020', '1990-01-20', 'ROLE_MD', 20),
    ('md21@example.com', 'MD21', '010-0021-0021', '1990-01-21', 'ROLE_MD', 21),
    ('md22@example.com', 'MD22', '010-0022-0022', '1990-01-22', 'ROLE_MD', 22),
    ('md23@example.com', 'MD23', '010-0023-0023', '1990-01-23', 'ROLE_MD', 23),
    ('md24@example.com', 'MD24', '010-0024-0024', '1990-01-24', 'ROLE_MD', 24),
    ('md25@example.com', 'MD25', '010-0025-0025', '1990-01-25', 'ROLE_MD', 25),
    ('md26@example.com', 'MD26', '010-0026-0026', '1990-01-26', 'ROLE_MD', 26),
    ('md27@example.com', 'MD27', '010-0027-0027', '1990-01-27', 'ROLE_MD', 27),
    ('md28@example.com', 'MD28', '010-0028-0028', '1990-01-28', 'ROLE_MD', 28),
    ('md29@example.com', 'MD29', '010-0029-0029', '1990-01-29', 'ROLE_MD', 29),
    ('md30@example.com', 'MD30', '010-0030-0030', '1990-01-30', 'ROLE_MD', 30);


-- USER ID 14, idol_id = 14
INSERT INTO product (name, category_id, price, stock, description, thumbnail_url, view_count, available_from, created_by, idol_id)
VALUES
    ('MD14 굿즈1', 1, 10000, 50, '굿즈 설명입니다.', 'https://example.com/thumb1.jpg', 0, NOW(), 14, 14),
    ('MD14 굿즈2', 2, 12000, 30, '굿즈 설명입니다.', 'https://example.com/thumb2.jpg', 0, NOW(), 14, 14),
    ('MD14 굿즈3', 3, 15000, 40, '굿즈 설명입니다.', 'https://example.com/thumb3.jpg', 0, NOW(), 14, 14),
    ('MD14 굿즈4', 1, 20000, 25, '굿즈 설명입니다.', 'https://example.com/thumb4.jpg', 0, NOW(), 14, 14),
    ('MD14 굿즈5', 2, 11000, 60, '굿즈 설명입니다.', 'https://example.com/thumb5.jpg', 0, NOW(), 14, 14),
    ('MD14 굿즈6', 3, 9000, 35, '굿즈 설명입니다.', 'https://example.com/thumb6.jpg', 0, NOW(), 14, 14),
    ('MD14 굿즈7', 1, 13000, 45, '굿즈 설명입니다.', 'https://example.com/thumb7.jpg', 0, NOW(), 14, 14),
    ('MD14 굿즈8', 2, 17000, 20, '굿즈 설명입니다.', 'https://example.com/thumb8.jpg', 0, NOW(), 14, 14),
    ('MD14 굿즈9', 3, 14000, 15, '굿즈 설명입니다.', 'https://example.com/thumb9.jpg', 0, NOW(), 14, 14),
    ('MD14 굿즈10', 1, 18000, 10, '굿즈 설명입니다.', 'https://example.com/thumb10.jpg', 0, NOW(), 14, 14);

-- USER ID 15, idol_id = 1
INSERT INTO product (name, category_id, price, stock, description, thumbnail_url, view_count, available_from, created_by, idol_id)
VALUES
    ('MD15 굿즈1', 1, 10000, 50, '굿즈 설명입니다.', 'https://example.com/thumb1.jpg', 0, NOW(), 15, 1),
    ('MD15 굿즈2', 2, 12000, 30, '굿즈 설명입니다.', 'https://example.com/thumb2.jpg', 0, NOW(), 15, 1),
    ('MD15 굿즈3', 3, 15000, 40, '굿즈 설명입니다.', 'https://example.com/thumb3.jpg', 0, NOW(), 15, 1),
    ('MD15 굿즈4', 1, 20000, 25, '굿즈 설명입니다.', 'https://example.com/thumb4.jpg', 0, NOW(), 15, 1),
    ('MD15 굿즈5', 2, 11000, 60, '굿즈 설명입니다.', 'https://example.com/thumb5.jpg', 0, NOW(), 15, 1),
    ('MD15 굿즈6', 3, 9000, 35, '굿즈 설명입니다.', 'https://example.com/thumb6.jpg', 0, NOW(), 15, 1),
    ('MD15 굿즈7', 1, 13000, 45, '굿즈 설명입니다.', 'https://example.com/thumb7.jpg', 0, NOW(), 15, 1),
    ('MD15 굿즈8', 2, 17000, 20, '굿즈 설명입니다.', 'https://example.com/thumb8.jpg', 0, NOW(), 15, 1),
    ('MD15 굿즈9', 3, 14000, 15, '굿즈 설명입니다.', 'https://example.com/thumb9.jpg', 0, NOW(), 15, 1),
    ('MD15 굿즈10', 1, 18000, 10, '굿즈 설명입니다.', 'https://example.com/thumb10.jpg', 0, NOW(), 15, 1);

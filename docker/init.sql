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

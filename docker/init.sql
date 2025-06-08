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

CREATE TABLE idol_like (
                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                   user_id BIGINT NOT NULL,
                   idol_id BIGINT NOT NULL,
                   created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                   FOREIGN KEY (user_id) REFERENCES user(id),
                   FOREIGN KEY (idol_id) REFERENCES idol(id)
);


CREATE TABLE idol (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    image_url VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(50) NOT NULL
);


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



INSERT INTO user (email, nickname, phone_number, birth_date)
VALUES
    ('test1@example.com', '테스터1', '010-1111-2222', '2000-01-01'),
    ('test2@example.com', '테스터2', '010-3333-4444', '1999-12-31');
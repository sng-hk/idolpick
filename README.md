## ✨ 팬 전용 굿즈 쇼핑몰
> **나만의 아이돌을 위한 팬 굿즈 플랫폼**
> 팬들이 관심 있는 아이돌 기반으로 굿즈를 탐색하고, MD는 직접 굿즈를 등록・관리하는 커스터마이징 쇼핑몰 시스템입니다.
---
### 📌 주요 기능
| 구분                 | 설명                                |
| ------------------ | --------------------------------- |
| 🔍 아이돌 검색 | 전체 아이돌 리스트에서 키워드 기반으로 검색 가능. 검색 후 해당 아이돌 전용 굿즈 페이지 이동 |
![Image](https://github.com/user-attachments/assets/e599138c-4a09-4c27-a2c9-36b7d8ae2c5f)
| ♥️ 관심 아이돌 좋아요 | 특정 아이돌에 좋아요를 누르면 관심 아이돌로 등록됨. 이후 메인 페이지에서 해당 아이돌 굿즈만 표시 |
![Image](https://github.com/user-attachments/assets/57d9fd5a-9c8f-40b5-be52-1b8af027632e)
| 🔍 아이돌 전용 페이지  | 메인화면에서 사용자의 아이돌 전용 굿즈 목록 제공  |
![아이돌 전용 페이지](https://github.com/user-attachments/assets/4ae4d6ef-2a5d-486d-8ec6-5e065b931154)
| 📦 굿즈 관리 (MD 전용)   | MD 권한을 가진 사용자만 굿즈 등록 / 수정 / 삭제 가능 |
| 🔥 인기 굿즈 랭킹        | 굿즈 조회수를 기준으로 인기 순위 제공             |
| 🧾 카테고리 필터링        | 카테고리별로 굿즈를 필터링하여 탐색 가능            |
| 🧑‍🎤 아이돌 리스트 / 검색 | 아이돌 전체 목록 조회, 검색 및 클릭 시 전용 페이지 이동 |
| 🔐 로그인 / 인증        | kakao OAuth 로그인, JWT 기반 인증 시스템   |
![Image](https://github.com/user-attachments/assets/18879c1c-e812-49b7-b3b5-3da61d62f858)
| 📊 관리자 통계 (추후)     | 굿즈별 판매량, 조회수 기반 통계 제공 예정          |




### 🚀 실행 방법 (로컬 개발용)

#### 1. Backend

```bash
cd server
./gradlew build
docker-compose up -d
```

#### 2. Frontend

```bash
cd client
npm install
npm run dev
```

---

### 🙌 기획 의도

> 팬들이 본인의 관심 아이돌만을 위한 굿즈를 쉽게 탐색하고 구매할 수 있도록, 개인화된 경험을 제공하는 플랫폼입니다.
> 관리자(MD)는 직접 굿즈를 등록하고 판매 데이터를 기반으로 상품을 효율적으로 관리할 수 있습니다.

---

### 🛠️ 기술 스택

**Frontend**

* React
* CSS
* GitHub OAuth

**Backend**

* Java 17, Spring Boot 3.x
* Spring Security + JWT
* Spring Data JDBC
* MySQL (AWS RDS)

**DevOps / Infra**

* Docker, GitHub Actions
* AWS EC2, RDS, S3
* NGINX, Netlify (FE)


### 📢 향후 개선 예정

* 💬 사용자 리뷰 / 평점 기능
* 💖 찜하기 및 관심 상품 모아보기
* 📊 관리자용 대시보드
* 📱 모바일 최적화 UI

---

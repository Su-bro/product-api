<!-- 
● Github 의 Readme 에는 아래 내용이 포함되어야 합니다.
구현 범위에 대한 설명
코드 빌드, 테스트, 실행 방법
기타 추가 정보
-->:
# Product API

## 프로젝트 구성

- 언어 : Java 17
- 프레임워크 : Spring Boot 3.3.2, Spring Data JPA, QueryDSL
- 빌드 도구 : Gradle 8.8
- 데이터베이스 : in-memory H2 DB
- 테스트 : JUnit, Mockito, AssertJ
- 프론트엔드 : Thymeleaf
- API 문서 : SpringDoc OpenAPI

```
src
├─main
│  ├─java
│  │  └─com
│  │      └─musinsa
│  │          ├─common
│  │          │  ├─exception
│  │          │  ├─response
│  │          │  ├─ui
│  │          │  └─util
│  │          └─product
│  │              ├─application
│  │              ├─domain
│  │              │  ├─entity
│  │              │  └─repository
│  │              ├─dto
│  │              ├─infrastructure
│  │              └─ui
│  └─resources
│      └─static
│          └─css
└─test
    └─java
        └─com
            └─musinsa
                ├─common
                │  └─util
                └─product
                    ├─application
                    ├─domain
                    │  ├─entity
                    │  └─repository
                    └─ui

```

## 구현 범위

### 상품 조회 페이지
- http://localhost:8080/home
### 관리자 페이지
- http://localhost:8080/admin

### API
1. **카테고리별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API**
    - 카테고리별로 최저가격인 브랜드와 가격과 상품 총액을 조회할 수 있습니다.
    - GET /api/products/lowest-price-by-category
    - Request: None
    - Response:
    ```json
    {
        "products": [
            {
                "categoryName": "상의",
                "brandName": "C",
                "price": 10000
            },
            {
                "categoryName": "아우터",
                "brandName": "E",
                "price": 5000
            },
            {
                "categoryName": "바지",
                "brandName": "D",
                "price": 3000
            },
            {
                "categoryName": "스니커즈",
                "brandName": "A",
                "price": 9000
            },
            {
                "categoryName": "가방",
                "brandName": "A",
                "price": 2000
            },
            {
                "categoryName": "모자",
                "brandName": "D",
                "price": 1500
            },
            {
                "categoryName": "양말",
                "brandName": "I",
                "price": 1700
            },
            {
                "categoryName": "액세서리",
                "brandName": "F",
                "price": 1900
            }
        ],
        "totalPrice": 34100
    }
    ```
2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을
   조회하는 API
    - 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 상품 총액을 조회할 수 있습니다.
    - GET /api/products/lowest-price-by-brand
    - Request: None
    - Response:
    ```json
    {
        "lowestPrice": {
            "brandName": "D",
            "products": [
                {
                    "categoryName": "상의",
                    "price": 10100
                },
                {
                    "categoryName": "아우터",
                    "price": 5100
                },
                {
                    "categoryName": "바지",
                    "price": 3000
                },
                {
                    "categoryName": "스니커즈",
                    "price": 9500
                },
                {
                    "categoryName": "가방",
                    "price": 2500
                },
                {
                    "categoryName": "모자",
                    "price": 1500
                },
                {
                    "categoryName": "양말",
                    "price": 2400
                },
                {
                    "categoryName": "액세서리",
                    "price": 2000
                }
            ],
            "totalPrice": 36100
        }
    }
    ```
3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
    -  특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드의 브랜드명과 상품 가격을 조회할 수 있습니다.
    - GET /api/products/price-by-category
    - Request:
    ```json
    {
        "categoryName": "상의"
    }
    ```
    - Response:
    ```json
    {
        "categoryName": "상의",
        "lowestPrice": {
            "brandName": "C",
            "price": 10000
        },
        "highestPrice": {
            "brandName": "I",
            "price": 11400
        }
    }
    ```
4. 상품 등록 API
    - 상품을 등록할 수 있습니다.
    - POST /api/products
    - Request:
    ```json
    {
        "productName": "반팔티셔츠",
        "price": 10000,
        "categoryName": "상의",
        "brandName": "무탠다드"
    }
    ```
    - Response:
    ```json
    {
        "message": "상품 등록이 완료되었습니다."
    }
    ```
5. 상품 수정 API
    - 상품을 수정할 수 있습니다.
    - PUT /api/products/{productId}
    - Request:
    ```json
    {
        "productName": "반팔티셔츠",
        "price": 9000
    }
    ```
    - Response:
    ```json
    {
        "message": "상품 수정이 완료되었습니다."
    }
    ```
6. 상품 삭제 API
    - 상품을 삭제할 수 있습니다.
    - DELETE /api/products/{productId}
    - Response:
    ```json
    {
        "message": "상품 삭제가 완료되었습니다."
    }
    ```
7. 브랜드 등록 API
    - 브랜드를 등록할 수 있습니다.
    - POST /api/brands
    - Request:
    ```json
    {
        "brandName": "무탠다드",
        "brandDesc": "무신사 스탠다드(MUSINSA STANDARD)는 대한민국을 대표하는 온라인 패션 스토어 무신사가 전개하는 자체상표(Private Brand)입니다"
    }
    ```
    - Response:
    ```json
    {
        "message": "브랜드 등록이 완료되었습니다."
    }
    ```
8. 브랜드 수정 API
    - 브랜드를 수정할 수 있습니다.
    - PUT /api/brands
    - Request:
    ```json
    {
        "brandId": 1,
        "brandName": "무탠다드",
        "brandDesc": "무신사 스탠다드(MUSINSA STANDARD)는 대한민국을 대표하는 온라인 패션 스토어 무신사가 전개하는 자체상표(Private Brand)입니다"
    }
    ```
    - Response:
    ```json
    {
        "message": "브랜드 수정이 완료되었습니다."
    }
    ```
9. 브랜드 삭제 API
    - 브랜드를 삭제할 수 있습니다.
    - 브랜드 삭제 시, 해당 브랜드에 속한 상품도 함께 삭제됩니다.
    - DELETE /api/brands/{brandId}
    - Response:
    ```json
    {
        "message": "브랜드 삭제가 완료되었습니다."
    }
    ```

## 빌드, 테스트, 실행 방법
### 빌드&테스트
```shell
./gradlew clean build
```
### 테스트
```shell
./gradlew test
```
### 실행
```shell
java -jar build/libs/product-api-0.0.1-SNAPSHOT.war
```

## 기타 추가 정보
### H2 DB console
- URL : http://localhost:8080/h2-console
- Login 
    - JDBC URL: jdbc:h2:mem:testdb
    - User Name: sa
    - Password:
### API 문서
- URL : http://localhost:8080/swagger-ui/index.html


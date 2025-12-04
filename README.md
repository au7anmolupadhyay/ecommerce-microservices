E-Commerce Microservices System (Spring Boot + JWT + PostgreSQL)

This repository contains a scalable E-Commerce backend built using a microservices architecture.
Each service is fully independent with its own database, security, and REST APIs.

```text

                     +-------------------+
                     |    API Gateway    |   (Future)
                     +---------+---------+
                               |
     ---------------------------------------------------------
     |                        |                              |
+------------+      +----------------+              +------------------+
| Auth       |      | Product        |              | Inventory         |
| Service    |      | Service        |              | Service           |
+------------+      +----------------+              +------------------+
| Login       |      | Add Product    |              | Update Stock      |
| Register    | ---> | Get Products   |              | Reserve Stock     |
| JWT Issue   |      | Get By Id      |              | Check Availability|
| Roles       |      | ADMIN Access   |              | Sync/ProductSvc   |
+------------+      +----------------+              +------------------+
       |
       | issues JWT (email + role)
       v
Every service validates JWT using its own JWT Filter.

```
Each microservice is self-contained and has its own database, schema, and security.

Tech Stack
Backend

Spring Boot 3.5

Spring Security (Stateless JWT)

Spring Data JPA

MapStruct

Lombok

PostgreSQL

JWT (Access + Refresh Tokens)

HikariCP (Connection Pool)

Dev Tools

IntelliJ IDEA / VS Code

Maven

DBeaver

Postman

Repository Structure
ecommerce-project/
│
├── services/
│   ├── auth-service/
│   ├── product-service/
│   ├── inventory-service/        (upcoming)
│   └── api-gateway/              (planned)
│
├── README.md
├── .gitignore
└── docker-compose.yml            (future)

Database Setup (PostgreSQL)

Example for Product-Service:

CREATE ROLE product_user WITH LOGIN PASSWORD 'product_pass';
CREATE DATABASE productdb OWNER product_user;

GRANT ALL PRIVILEGES ON DATABASE productdb TO product_user;


Each service uses its own DB and user.

How to Run Each Service

Inside each service folder:

mvn clean install
mvn spring-boot:run


Default Ports:

Service	Port
Auth-Service	8080
Product-Service	8081
Inventory-Service	8082 (planned)
Security Flow (JWT + Role Based Authorization)
Auth-Service Responsibilities

Validate login

Issue Access Token and Refresh Token

Add roles to JWT (ROLE_USER, ROLE_ADMIN)

Product-Service Responsibilities

Accept JWT from header

Verify using JWT Filter

Store authentication in Spring Security Context

Allow only admin users for product creation:

POST /product/addProduct  -> ADMIN ONLY

API Documentation
Auth-Service Endpoints
Register
POST /auth/register


Body:

{
"email": "user@example.com",
"password": "123456",
"role": "USER"
}

Login
POST /auth/login


Returns:

Access Token

Refresh Token

Product-Service Endpoints
Add Product (ADMIN Only)
POST /product/addProduct
Authorization: Bearer <jwt>


Example Body:

{
"title": "iPhone 15",
"description": "Latest model",
"price": 79999,
"brand": "Apple",
"category": "Mobiles",
"imageUrl": "https://example.com/img.jpg"
}

Get All Products
GET /product/getAll
Authorization: Bearer <jwt>

Get Product By ID
GET /product/getById/{id}
Authorization: Bearer <jwt>

Testing With Postman

Hit Register

Hit Login → get Access Token

Set token in Postman → Authorization → Bearer Token

Test Product endpoints

Roadmap (Next Steps)
Inventory-Service

Stock storage

Reserve stock when order placed

Release stock on order cancellation

Sync with product service

API Gateway

Centralized routing

JWT validation globally

Rate limiting

Additional Planned Services

Order-Service

Payment-Service

Notification-Service
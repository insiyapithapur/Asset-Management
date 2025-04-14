# Asset Management System

A comprehensive solution for managing organizational assets throughout their lifecycle, built with Spring Boot and React.js.

## Overview

The Asset Management System is a full-featured application designed to streamline the process of asset tracking, allocation, and management within organizations. From requisitioning new assets to tracking allocations and handling returns, this system provides a centralized platform for efficient asset management.

## Features

- **Asset Category Management**: Create and manage different categories of assets
- **Asset Classification**: Further classify assets within categories
- **Asset Type Management**: Define specific types of assets with relevant attributes
- **Location Management**: Track assets across different organizational locations
- **Employee Management**: Manage employee profiles and their assigned assets
- **Asset Request System**: Allow employees to request assets and managers to approve/reject requests
- **Ad-hoc Asset Allocation**: Directly allocate assets to employees without the formal request process
- **Asset Lifecycle Tracking**: Monitor assets throughout their lifecycle from acquisition to disposal
- **User Authentication**: Secure JWT-based authentication system
- **Role-based Access Control**: Different permission levels for employees, managers, and administrators

## Tech Stack

### Backend
- **Spring Boot**: Java-based framework for building robust applications
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Data access layer with Hibernate
- **PostgreSQL**: Database for storing asset and user information
- **JWT**: For secure authentication

### Frontend (separate repository)
React.js: JavaScript library for building user interfaces
Zod: TypeScript-first schema validation library

### Deployment
- **Docker**: Containerization
- **Render**: Backend deployment
- **Neon Tech**: PostgreSQL database hosting
- **Vercel**: Frontend deployment

## Project Structure

The project follows a standard Spring Boot application structure:

```
src/main/java/com/elecon/asset_mgt/
├── AssetRequest/          # Asset request management
├── Category/              # Asset category management
├── Classification/        # Asset classification management
├── Config/                # Security and JWT configuration
├── Employee/              # Employee management
├── Exceptions/            # Custom exceptions
├── location/              # Location management
├── Roles/                 # User roles management
├── Tokens/                # JWT token management
├── Type/                  # Asset type management
├── utils/                 # Utility classes
├── AssetMgtApplication.java  # Main application entry point
└── WebConfig.java         # Web configuration
```

## Getting Started

### Prerequisites
- Java 21
- Maven
- PostgreSQL
- Git

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/asset-management-system.git
   cd asset-management-system
   ```

2. Configure the database connection in `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://your-database-url/asset_mgt
       username: your-username
       password: your-password
   ```

3. Build the application:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Docker Deployment

1. Build the Docker image:
   ```bash
   docker build -t asset-management-system .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 asset-management-system
   ```

## API Documentation

### Authentication
- `POST /api/v1/employee/login`: Authenticate user and get JWT token
- `POST /api/v1/employee/CreateEmployee/`: Create a new employee
- `GET /api/v1/employee/registerEmployee/{employeeId}`: Register an employee

### Asset Categories
- `POST /api/v1/category/CreateCategory/`: Create new asset category
- `GET /api/v1/category/`: Get all categories
- `GET /api/v1/category/{id}`: Get category by ID
- `PUT /api/v1/category/updateCategory`: Update an existing category
- `DELETE /api/v1/category/{id}`: Delete a category

### Asset Requests
- `POST /api/v1/AssetRequest/createAssetRequest/`: Create new asset request
- `GET /api/v1/AssetRequest/`: Get all asset requests
- `GET /api/v1/AssetRequest/{id}`: Get asset request by ID
- `PUT /api/v1/AssetRequest/updateAssetRequest`: Update an asset request
- `DELETE /api/v1/AssetRequest/{id}`: Delete an asset request

## Database Design 
![image](https://github.com/user-attachments/assets/b1e29699-f969-4d0b-bfae-9d44dd07588b)

## Workflow

![image](https://github.com/user-attachments/assets/346dfc3e-62d4-4354-a943-32906672c402)
![image](https://github.com/user-attachments/assets/7e0ec2d6-6218-4be7-b066-67aa39a33ab1)

1. **Asset Request**: 
   - Employee submits an asset request
   - Request enters pending state
   - Manager can approve or reject the request
   - If approved, asset owner allocates the asset
   - Employee receives the asset through a handover process

2. **Ad-hoc Allocation**:
   - Asset owner directly allocates assets to employees
   - System tracks the allocation and updates asset status

3. **Asset Return/Pullback**:
   - Employee initiates return or manager initiates pullback
   - Asset status is updated accordingly

## Security

The application uses JWT (JSON Web Token) for authentication. Each API request must include a valid token in the Authorization header.

## Screenshots 
![image](https://github.com/user-attachments/assets/b5394431-808c-416c-95c1-e4ccea520fc0)
![image](https://github.com/user-attachments/assets/7f02dbf4-f5ba-4f0b-887a-7f76a3b21444)
![image](https://github.com/user-attachments/assets/1eb08014-821e-4bf0-8f1a-887803a0c4ac)
![image](https://github.com/user-attachments/assets/af245376-392f-495a-b1df-54eb944bef2c)
![image](https://github.com/user-attachments/assets/071f8166-ba42-4fe9-aa90-7f26572aa23c)
![image](https://github.com/user-attachments/assets/0e933ff1-0c01-4194-bffc-fc4a5dcb5c88)
![image](https://github.com/user-attachments/assets/b1ec41a2-89aa-4b9a-a671-299cc57197cd)
![image](https://github.com/user-attachments/assets/74ec4d8f-3d3b-4b05-b427-6c578a81cc38)
![image](https://github.com/user-attachments/assets/507a9533-d6be-491f-9849-c9f41df134ef)
![image](https://github.com/user-attachments/assets/50df1214-5ee2-4a35-ae83-8813618403b0)
![image](https://github.com/user-attachments/assets/a579dd5f-19a8-454e-9bd4-60b7274795e8)
![image](https://github.com/user-attachments/assets/0828d300-a960-41dc-875f-8e7ec391b62e)
![image](https://github.com/user-attachments/assets/52210900-5215-409a-9489-9d9d03010474)

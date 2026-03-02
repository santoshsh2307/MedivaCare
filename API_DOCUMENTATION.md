# HMS REST API Documentation

This document provides comprehensive Swagger/OpenAPI documentation for the Hospital Management System (HMS) REST APIs.

## Overview

The HMS system consists of multiple microservices, each with its own API documentation accessible via Swagger UI:

### Services Documentation URLs

- **Authentication Service**: http://localhost:8081/swagger-ui.html
- **Profile Service**: http://localhost:8080/swagger-ui.html

## API Endpoints

### Authentication Service (`/auth`)

#### POST `/auth/login`
- **Description**: Authenticate user and return JWT token
- **Request Body**: `AuthRequest` containing username and password
- **Response**: JWT token with user data including roles and branch access
- **Status Codes**: 
  - 200: Login successful
  - 401: Invalid credentials

### Profile Service (`/profile`)

#### GET `/profile/me`
- **Description**: Retrieve profile information for the authenticated user
- **Headers**: `X-Auth-Username` (required)
- **Security**: Requires ADMIN or MANAGER role
- **Response**: User profile data

#### POST `/profile/onboardingDoctor`
- **Description**: Register a new doctor in the system
- **Headers**: `X-Auth-Username` (required)
- **Request Body**: `StaffOnboardingRequest` with doctor details
- **Response**: `StaffOnboardingResponse` with onboarding results
- **Status Codes**:
  - 200: Doctor onboarded successfully
  - 400: Invalid request data
  - 500: Internal server error

#### GET `/profile/active`
- **Description**: Retrieve list of all active users in the system
- **Headers**: `X-Auth-Username` (required)
- **Response**: List of active users
- **Status Codes**:
  - 200: Users retrieved successfully
  - 500: Internal server error

#### POST `/profile/createAvailability`
- **Description**: Add availability schedule for a doctor
- **Request Body**: `DoctorAvailabilityRequest` with schedule details
- **Response**: Created `DoctorAvailability` object
- **Status Codes**:
  - 200: Availability created successfully
  - 400: Invalid request data

#### GET `/profile/doctorAvailability/{doctorId}`
- **Description**: Retrieve availability schedule for a specific doctor
- **Path Variable**: `doctorId` (Long) - ID of the doctor
- **Response**: List of `DoctorAvailability` objects
- **Status Codes**:
  - 200: Availability retrieved successfully
  - 404: Doctor not found or no availability
  - 400: Invalid request

#### GET `/profile/doctorDropdown`
- **Description**: Retrieve list of doctors for dropdown selection
- **Response**: List of doctors with id and name
- **Status Codes**:
  - 200: Doctors list retrieved successfully

## Security

### Authentication
- All endpoints (except login) require JWT token authentication
- Include the token in the `Authorization` header as `Bearer <token>`

### Authorization
- Role-based access control is implemented using `@PreAuthorize` annotations
- Roles include: ADMIN, MANAGER, DOCTOR, etc.

## Data Models

### AuthRequest
```json
{
  "username": "string",
  "password": "string"
}
```

### StaffOnboardingRequest
```json
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "specialization": "string",
  "department": "string"
}
```

### DoctorAvailabilityRequest
```json
{
  "doctorId": "number",
  "dayOfWeek": "string",
  "startTime": "string",
  "endTime": "string"
}
```

## Usage Instructions

1. **Access Swagger UI**: Navigate to the service-specific Swagger UI URL
2. **Authentication**: Use the `/auth/login` endpoint to get a JWT token
3. **Authorize**: Click the "Authorize" button in Swagger UI and enter `Bearer <your-jwt-token>`
4. **Test APIs**: Use the interactive interface to test API endpoints

## Error Handling

All APIs follow consistent error response format:
```json
{
  "status": "ERROR",
  "message": "Error description",
  "data": null
}
```

## Development Setup

To enable Swagger documentation in your service:

1. Add SpringDoc OpenAPI dependency to `pom.xml`:
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
```

2. Create Swagger configuration class:
```java
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Your Service API")
                        .version("v1.0.0"));
    }
}


3. Add annotations to controllers:
```java
@Tag(name = "Service Name", description = "API description")
@RestController
public class YourController {
    
    @Operation(summary = "Endpoint summary", description = "Detailed description")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("/endpoint")
    public ResponseEntity<?> yourMethod() {
        // implementation
    }
}


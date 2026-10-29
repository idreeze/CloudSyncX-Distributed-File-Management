# CloudSyncX – Distributed File Synchronization & Version Control Platform

## Overview

CloudSyncX is an enterprise-grade file management and version control platform designed to provide secure file storage, intelligent version tracking, rollback capabilities, and real-time event notifications.

The platform enables users to upload, manage, download, and track file versions while maintaining historical records and supporting event-driven communication through WebSockets.

---

## Key Features

### File Management

* Upload files securely to the server
* Download files using unique identifiers
* Maintain file metadata in PostgreSQL
* Store files in a dedicated storage directory

### Intelligent Version Control

* Automatic version increment for repeated uploads
* Historical version tracking
* Version history retrieval
* Rollback support for previous versions

### Authentication & Security

* JWT-based authentication
* Secure API access
* Password encryption using BCrypt
* Spring Security integration

### Real-Time Communication

* WebSocket-based notifications
* Event-driven architecture
* Instant update propagation

### Database Management

* PostgreSQL persistence
* JPA/Hibernate integration
* Metadata and version history storage

---

## System Architecture

User
↓
REST APIs (Spring Boot)
↓
Service Layer
↓
PostgreSQL Database

File Upload
↓
Version Management
↓
Event Publisher
↓
WebSocket Notifications

---

## Technology Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security
* Spring WebSocket

### Database

* PostgreSQL

### Build & Dependency Management

* Maven

### Authentication

* JWT
* BCrypt

### Communication

* REST APIs
* WebSockets

### Version Control

* Git
* GitHub

---

## Project Structure

src/main/java/com/cloudsyncx

├── controller

├── service

├── repository

├── entity

├── security

├── websocket

├── event

└── dto

---

## API Endpoints

### File APIs

#### Upload File

POST /files/upload

#### Download File

GET /files/download/{id}

#### Get All Files

GET /files

#### Get File By ID

GET /files/{id}

#### Delete File

DELETE /files/{id}

---

### Version APIs

#### Get Version History

GET /versions/{fileId}

#### Rollback Version

POST /versions/rollback

---

### Authentication APIs

#### Register User

POST /auth/register

#### Login User

POST /auth/login

---

## Database Entities

### User

* id
* username
* password

### FileMetadata

* id
* fileName
* s3Url
* currentVersion
* ownerId

### FileVersion

* id
* fileId
* versionNumber
* uploadedAt
* changeDescription

---

## Workflow

### File Upload Workflow

Upload File
↓
Store Physical File
↓
Create/Update Metadata
↓
Create Version Record
↓
Publish Event
↓
Send WebSocket Notification

### Version Control Workflow

Upload Existing File
↓
Detect Existing Metadata
↓
Increment Version
↓
Save New Version Record
↓
Update Current Version

---

## Future Enhancements

* AWS S3 Integration
* Redis Caching
* Docker Deployment
* Multi-device Synchronization
* File Sharing and Permissions
* Audit Logging
* Advanced Search
* Swagger API Documentation

---

## Setup Instructions

### Clone Repository

git clone https://github.com/dheekshasaravanan/CloudSyncX.git

### Navigate to Project

cd CloudSyncX

### Configure PostgreSQL

Create a PostgreSQL database and update application.properties with your credentials.

### Run Application

./mvnw spring-boot:run

### Access APIs

http://localhost:8080

---

## Author

Dheeksha S
VIT, Vellore
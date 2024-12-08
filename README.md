# JobSeeker JobProvider Dashboard

This repository contains the code for a **JobSeeker** and **JobProvider** Dashboard application. The application allows job seekers to manage their profiles, apply for jobs, and view job applications. Job providers can manage job postings, view applicants, and update their profiles.

## Features

### Job Seeker Features:
- View and edit profile information.
- Apply for available jobs.
- Filter job listings by location and experience.
- View the status of job applications.

### Job Provider Features:
- Post new job openings.
- View and manage job seeker applications.
- Edit provider profile information (excluding email and name).
- Filter and view job seekers based on applied jobs.

## Technologies Used

- **Frontend**:
  - HTML
  - CSS
  - JavaScript (Vanilla JS)

- **Backend**:
  - Java (Spring Boot for REST APIs)
  - Database: MongoDB

## Prerequisites

- **Java** 8 or above
- **MongoDB** (can be local or hosted service such as MongoDB Atlas)
- **Git** for version control

## Installation

### Backend (Spring Boot)

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/your-repo-name.git

  ```
b) Navigate to the backend directory:

  ```bash
  cd your-repo-name/backend
  
c) Install dependencies :

  ```bash
  mvn clean install
  
d) Configure MongoDB:

  * Set up your MongoDB instance locally or use a hosted service like MongoDB Atlas.
  * Update `application.properties` or `application.yml` with your MongoDB connection details.
e) Run the backend server:

  The server will run on `http://localhost:8080` by default.
  ```
### Frontend:

a) Navigate to the frontend directory:

  ```bash
  cd your-repo-name/frontend
  ```
b) Serve the frontend:

  Since no frontend frameworks are used, you can either:

  * Open `index.html` directly in your browser.
  * Serve the files using a local server (e.g., `live-server`).

## Usage

### Job Seeker Dashboard

1. **Log in as a job seeker**: 
   - Access the platform and log in using your job seeker credentials.

2. **View job listings**: 
   - Once logged in, browse through available job listings on the dashboard.

3. **Apply for jobs**: 
   - Fill in the application form for any job that interests you and submit your application.

4. **Edit your profile**: 
   - You can update your profile information, such as skills, experience, education, and contact details.

1. **Log in as a job provider**: 
   - Access the platform and log in using your job provider credentials.

2. **Add new job listings**: 
   - Post new job openings on the platform, specifying job requirements, skills, and experience needed.

3. **View job seekers' applications**: 
   - View and manage applications submitted by job seekers for your job listings.

4. **Edit your company profile**: 
   - Update your company profile information, such as address, location, business type, and contact number (Note: Email and provider name cannot be updated).


# Diabetes Monitoring App

A full-stack web application that allows doctors to manage diabetic patients and monitor their treatment progress. Patients can view their profiles, track medications, and record daily updates about their condition.

---

## Features

### For Doctors
- View and manage patient profiles  
- Prescribe and update medications  
- Track patient progress and treatment history  

### For Patients
- View assigned medications and dosage history  
- Track daily blood sugar and health metrics  
- Access complete medication and record history  

---

## Architecture Overview

The application follows a **client-server architecture** with a RESTful API built using **Java (Maven project)** and a **dynamic single-page frontend** built with **HTML, CSS, and JavaScript**.

### Backend (API)
- Built with **Java (Maven project)**  
- Authentication with **JWT tokens**  
- DAO layer for data access  
- Resource layer exposing REST endpoints  
- Model layer for entity definitions  
- Common utilities (AuthenticationUtils, Validation, etc.)  
- Integrated with **MySQL database**  
- Database managed and deployed via **Heroku Shark MySQL Add-on**  
- Includes **Docker Compose** for local testing  
- Uses an **SQL script** to populate test data  

**Database Tables**
- `Users` – stores user credentials and roles  
- `Patients` – stores patient information  
- `Medications` – manages prescriptions and medication details  
- `DailyRecords` – stores daily logs and readings  

---

### Frontend (Web Client)
- Located under the `WebContent/` directory  
- Uses `WEB-INF` for configuration and `META-INF` for metadata  
- `WEB-INF/web.xml` defines:
  - Welcome files (entry HTML pages)
  - Servlet mapping for the main API servlet (`ApplicationServlet`)  

**Technology Stack**
- HTML, CSS, JavaScript  
- AJAX for API communication  
- Single Page Application structure (no page reloads)  
- Full CRUD support through API integration  

---

## Deployment

The application is deployed on **Heroku**.  
- The **API** is hosted as a Heroku web service.  
- The **database** uses the **Heroku Shark MySQL Add-on**, automatically linked to the deployed app.  
- The **frontend** is served directly through the same web module configured under `WebContent`.  

---


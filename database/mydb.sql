-- Create database
-- CREATE DATABASE mydb;

-- Use the created database
USE mydb;

-- Create Users table
CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('ADMIN', 'PHYSICIAN'))
);

-- Create Patients table
CREATE TABLE Patients (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    age INT,
    gender VARCHAR(50),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Create Medications table
CREATE TABLE Medications (
    medication_id INT PRIMARY KEY AUTO_INCREMENT,
    medication_name VARCHAR(255) NOT NULL,
    unit VARCHAR(20) NOT NULL
);

-- Create DailyRecords table
CREATE TABLE DailyRecords (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT,
    date DATE NOT NULL,
    blood_glucose_level DECIMAL(10, 2) NOT NULL,
    carb_intake DECIMAL(10, 2) NOT NULL,
    medication_id INT,
    medication_dose DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (medication_id) REFERENCES Medications(medication_id)
);



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


-- ADMIN USER PASSWORD: adminPass
-- PHYSICIAN USER PASSWORD: physicianPass
INSERT INTO Users (username, password, role) VALUES ('adminUser', '$2a$12$vxbK6c12xmetskELEAafQelmcTXr/26qIFhwLriWbcu2uT2dYrYh2', 'ADMIN');
INSERT INTO Users (username, password, role) VALUES ('physicianUser', '$2a$12$doktjcGITvFPo3uB4BhLf.dm9oFwlAoqtZYj7q94/WFE0rtPLUjKW', 'PHYSICIAN');


SELECT user_id INTO @physicianID FROM Users WHERE username = 'physicianUser';

INSERT INTO Patients (name, age, gender, user_id) VALUES ('John Doe', 45, 'Male', @physicianID);
INSERT INTO Patients (name, age, gender, user_id) VALUES ('Gent Osmani', 20, 'Male', @physicianID);


SELECT patient_id INTO @patientID FROM Patients WHERE name = 'John Doe';
SELECT patient_id INTO @patientID2 FROM Patients WHERE name = 'Gent Osmani';

INSERT INTO Medications (medication_name, unit) VALUES ('MedicationA', 'mg');
INSERT INTO Medications (medication_name, unit) VALUES ('MedicationB', 'mg');

SELECT medication_id INTO @medicationAID FROM Medications WHERE medication_name = 'MedicationA';
SELECT medication_id INTO @medicationBID FROM Medications WHERE medication_name = 'MedicationB';


INSERT INTO DailyRecords (patient_id, date, blood_glucose_level, carb_intake, medication_id, medication_dose) 
VALUES (@patientID, '2024-01-01', 120.00, 50.00, @medicationAID, 10.00),
       (@patientID, '2024-01-02', 110.00, 45.00, @medicationBID, 15.00),
       (@patientID, '2024-01-03', 115.00, 60.00, @medicationAID, 20.00),
       (@patientID, '2024-01-04', 130.00, 55.00, @medicationBID, 25.00),
       (@patientID, '2024-01-05', 125.00, 40.00, @medicationAID, 30.00);


INSERT INTO DailyRecords (patient_id, date, blood_glucose_level, carb_intake, medication_id, medication_dose) 
VALUES (@patientID2, '2024-01-01', 120.00, 50.00, @medicationAID, 10.00),
       (@patientID2, '2024-01-02', 110.00, 45.00, @medicationBID, 15.00),
       (@patientID2, '2024-01-03', 115.00, 60.00, @medicationAID, 20.00),
       (@patientID2, '2024-01-04', 130.00, 55.00, @medicationBID, 25.00),
       (@patientID2, '2024-01-05', 125.00, 40.00, @medicationAID, 30.00);
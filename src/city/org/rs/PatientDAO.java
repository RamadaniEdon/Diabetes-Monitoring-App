package city.org.rs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private static PatientDAO instance;
    private Connection connection;

    // Private constructor to prevent instantiation from outside
    private PatientDAO(Connection connection) {
        this.connection = connection;
    }

    // Static method to get the singleton instance
    public static PatientDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new PatientDAO(connection);
        }
        return instance;
    }
    
    // Method to add a new patient
    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO Patients (patient_id, name, age, gender, user_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patient.getPatientId());
            statement.setString(2, patient.getName());
            statement.setInt(3, patient.getAge());
            statement.setString(4, patient.getGender());
            statement.setInt(5, patient.getUserId());
            statement.executeUpdate();
        }
    }

    // Method to retrieve a patient by ID
    public Patient getPatient(int patientId) throws SQLException {
        String sql = "SELECT * FROM Patients WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Patient(
                    resultSet.getInt("patient_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("age"),
                    resultSet.getString("gender"),
                    resultSet.getInt("user_id")
                );
            } else {
                return null;
            }
        }
    }

    // Method to update a patient's details
    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE Patients SET name = ?, age = ?, gender = ?, user_id = ? WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient.getName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getGender());
            statement.setInt(4, patient.getUserId());
            statement.setInt(5, patient.getPatientId());
            statement.executeUpdate();
        }
    }

    // Method to delete a patient
    public void deletePatient(int patientId) throws SQLException {
        String sql = "DELETE FROM Patients WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            statement.executeUpdate();
        }
    }

    // Method to list all patients
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                patients.add(new Patient(
                    resultSet.getInt("patient_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("age"),
                    resultSet.getString("gender"),
                    resultSet.getInt("user_id")
                ));
            }
        }
        return patients;
    }
}

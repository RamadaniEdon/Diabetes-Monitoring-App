package city.org.rs.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import city.org.rs.ConnectionUtility;
import city.org.rs.models.Patient;
import city.org.rs.models.User;

public class PatientDAO {

    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO Patients (name, age, gender, user_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient.getName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getGender());
            statement.setInt(4, patient.getUserId());
            statement.executeUpdate();
        }
    }

    public Patient getPatient(int patientId) throws SQLException {
        String sql = "SELECT * FROM Patients WHERE patient_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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

    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE Patients SET name = ?, age = ?, gender = ? WHERE patient_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient.getName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getGender());
            statement.setInt(4, patient.getPatientId());
            statement.executeUpdate();
        }
    }

    public void deletePatient(int patientId) throws SQLException {
        String sql = "DELETE FROM Patients WHERE patient_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            statement.executeUpdate();
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients";
        try (Connection connection = ConnectionUtility.getConnection();
             Statement statement = connection.createStatement();
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

    public List<Patient> getPatientsByPhysician(User physician) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients WHERE user_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, physician.getUserId());
            ResultSet resultSet = statement.executeQuery();
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

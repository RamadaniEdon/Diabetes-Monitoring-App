package city.org.rs.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import city.org.rs.ConnectionUtility;
import city.org.rs.models.Medication;

public class MedicationDAO {

    public void addMedication(Medication medication) throws SQLException {
        String sql = "INSERT INTO Medications (medication_name, unit) VALUES (?, ?)";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, medication.getMedicationName());
            statement.setString(2, medication.getUnit());
            statement.executeUpdate();
        }
    }

    public Medication getMedication(int medicationId) throws SQLException {
        String sql = "SELECT * FROM Medications WHERE medication_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, medicationId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Medication(
                    resultSet.getInt("medication_id"),
                    resultSet.getString("medication_name"),
                    resultSet.getString("unit")
                );
            } else {
                return null;
            }
        }
    }

    public void updateMedication(Medication medication) throws SQLException {
        String sql = "UPDATE Medications SET medication_name = ?, unit = ? WHERE medication_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, medication.getMedicationName());
            statement.setString(2, medication.getUnit());
            statement.setInt(3, medication.getMedicationId());
            statement.executeUpdate();
        }
    }

    public void deleteMedication(int medicationId) throws SQLException {
        String sql = "DELETE FROM Medications WHERE medication_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, medicationId);
            statement.executeUpdate();
        }
    }

    public List<Medication> getAllMedications() throws SQLException {
        List<Medication> medications = new ArrayList<>();
        String sql = "SELECT * FROM Medications";
        try (Connection connection = ConnectionUtility.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                medications.add(new Medication(
                    resultSet.getInt("medication_id"),
                    resultSet.getString("medication_name"),
                    resultSet.getString("unit")
                ));
            }
        }
        return medications;
    }
}

package city.org.rs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationDAO {

    // Method to add a new medication
    public void addMedication(Medication medication) throws SQLException {
        String sql = "INSERT INTO Medications (medication_id, medication_name, unit) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, medication.getMedicationId());
            statement.setString(2, medication.getMedicationName());
            statement.setString(3, medication.getUnit());
            statement.executeUpdate();
        }
    }

    // Method to retrieve a medication by ID
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

    // Method to update a medication's details
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

    // Method to delete a medication
    public void deleteMedication(int medicationId) throws SQLException {
        String sql = "DELETE FROM Medications WHERE medication_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, medicationId);
            statement.executeUpdate();
        }
    }

    // Method to list all medications
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

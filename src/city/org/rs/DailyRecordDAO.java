package city.org.rs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DailyRecordDAO {

    // Method to add a new daily record
    public void addDailyRecord(DailyRecord record) throws SQLException {
        String sql = "INSERT INTO DailyRecords (record_id, patient_id, date, blood_glucose_level, carb_intake, medication_id, medication_dose) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, record.getRecordId());
            statement.setInt(2, record.getPatientId());
            statement.setDate(3, record.getDate());
            statement.setDouble(4, record.getBloodGlucoseLevel());
            statement.setDouble(5, record.getCarbIntake());
            statement.setInt(6, record.getMedication_id());
            statement.setDouble(7, record.getMedicationDose());
            statement.executeUpdate();
        }
    }

    // Method to retrieve a daily record by ID
    public DailyRecord getDailyRecord(int recordId) throws SQLException {
        String sql = "SELECT * FROM DailyRecords WHERE record_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, recordId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new DailyRecord(
                    resultSet.getInt("record_id"),
                    resultSet.getInt("patient_id"),
                    resultSet.getDate("date"),
                    resultSet.getDouble("blood_glucose_level"),
                    resultSet.getDouble("carb_intake"),
                    resultSet.getInt("medication_id"),
                    resultSet.getDouble("medication_dose")
                );
            } else {
                return null;
            }
        }
    }

    // Method to update a daily record's details
    public void updateDailyRecord(DailyRecord record) throws SQLException {
        String sql = "UPDATE DailyRecords SET patient_id = ?, date = ?, blood_glucose_level = ?, carb_intake = ?, medication_id = ?, medication_dose = ? WHERE record_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, record.getPatientId());
            statement.setDate(2, record.getDate());
            statement.setDouble(3, record.getBloodGlucoseLevel());
            statement.setDouble(4, record.getCarbIntake());
            statement.setInt(5, record.getMedication_id());
            statement.setDouble(6, record.getMedicationDose());
            statement.setInt(7, record.getRecordId());
            statement.executeUpdate();
        }
    }

    // Method to delete a daily record
    public void deleteDailyRecord(int recordId) throws SQLException {
        String sql = "DELETE FROM DailyRecords WHERE record_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, recordId);
            statement.executeUpdate();
        }
    }

    // Method to list all daily records
    public List<DailyRecord> getAllDailyRecords() throws SQLException {
        List<DailyRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM DailyRecords";
        try (Connection connection = ConnectionUtility.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                records.add(new DailyRecord(
                    resultSet.getInt("record_id"),
                    resultSet.getInt("patient_id"),
                    resultSet.getDate("date"),
                    resultSet.getDouble("blood_glucose_level"),
                    resultSet.getDouble("carb_intake"),
                    resultSet.getInt("medication_id"),
                    resultSet.getDouble("medication_dose")
                ));
            }
        }
        return records;
    }
}

package city.org.rs.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import city.org.rs.ConnectionUtility;
import city.org.rs.models.DailyRecord;

public class DailyRecordDAO {

    // Method to add a new daily record
    public void addDailyRecord(DailyRecord record) throws SQLException {
        String sql = "INSERT INTO DailyRecords (record_id, patient_id, date, blood_glucose_level, carb_intake, medication_id, medication_dose) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, record.getRecordId());
            statement.setInt(2, record.getPatientId());
            statement.setString(3, record.getDate());
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
                    resultSet.getString("date"),
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
            statement.setString(2, record.getDate());
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
                    resultSet.getString("date"),
                    resultSet.getDouble("blood_glucose_level"),
                    resultSet.getDouble("carb_intake"),
                    resultSet.getInt("medication_id"),
                    resultSet.getDouble("medication_dose")
                ));
            }
        }
        return records;
    }

    // Method to get daily records within a specified period
    public List<DailyRecord> getDailyRecordsInPeriod(Date startDate, Date endDate) throws SQLException {
        List<DailyRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM DailyRecords WHERE date >= ? AND date <= ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    records.add(new DailyRecord(
                        resultSet.getInt("record_id"),
                    resultSet.getInt("patient_id"),
                    resultSet.getString("date"),
                    resultSet.getDouble("blood_glucose_level"),
                    resultSet.getDouble("carb_intake"),
                    resultSet.getInt("medication_id"),
                    resultSet.getDouble("medication_dose")
                    ));
                }
            }
        }
        return records;
    }

    // Method to calculate the average blood glucose level over a specified period
    public Double getAverageBloodGlucoseLevel(Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT AVG(blood_glucose_level) FROM DailyRecords WHERE date >= ? AND date <= ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble(1);
                }
            }
        }
        return null;
    }

    // Method to calculate the average carb intake over a specified period
    public Double getAverageCarbIntake(Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT AVG(carb_intake) FROM DailyRecords WHERE date >= ? AND date <= ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble(1);
                }
            }
        }
        return null;
    }
}

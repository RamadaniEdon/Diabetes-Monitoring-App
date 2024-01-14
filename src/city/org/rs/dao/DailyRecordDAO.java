package city.org.rs.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import city.org.rs.ConnectionUtility;
import city.org.rs.models.DailyRecord;
import city.org.rs.models.User;
import city.org.rs.utils.Averages;

public class DailyRecordDAO {


    public void addDailyRecord(DailyRecord record) throws SQLException {
        if (record.getDate() == null) {
            record.setDate(java.time.LocalDate.now().toString());
        }
        if(isRecordExists(record.getPatientId(), record.getDate())){
            throw new SQLException("Record already exists");
        }
        String sql = "INSERT INTO DailyRecords (patient_id, date, blood_glucose_level, carb_intake, medication_id, medication_dose) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, record.getPatientId());
            statement.setString(2, record.getDate());
            statement.setDouble(3, record.getBloodGlucoseLevel());
            statement.setDouble(4, record.getCarbIntake());
            statement.setInt(5, record.getMedicationId());
            statement.setDouble(6, record.getMedicationDose());
            statement.executeUpdate();
        }
    }
    private boolean isRecordExists(int patientId, String date) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM DailyRecords WHERE patient_id = ? AND date = ?";
    
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setInt(1, patientId);
            statement.setString(2, date);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
    
        return false;
    }

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

    public void updateDailyRecord(DailyRecord record) throws SQLException {
        String sql = "UPDATE DailyRecords SET date = ?, blood_glucose_level = ?, carb_intake = ?, medication_id = ?, medication_dose = ? WHERE record_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, record.getDate());
            statement.setDouble(2, record.getBloodGlucoseLevel());
            statement.setDouble(3, record.getCarbIntake());
            statement.setInt(4, record.getMedicationId());
            statement.setDouble(5, record.getMedicationDose());
            statement.setInt(6, record.getRecordId());
            statement.executeUpdate();
        }
    }

    public void deleteDailyRecord(int recordId) throws SQLException {
        String sql = "DELETE FROM DailyRecords WHERE record_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, recordId);
            statement.executeUpdate();
        }
    }

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

    public List<DailyRecord> getDailyRecordsByPhysician(User user) throws SQLException{
        List<DailyRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM DailyRecords WHERE patient_id IN (SELECT patient_id FROM Patients WHERE user_id = ?)";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getUserId());
            ResultSet resultSet = statement.executeQuery();
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

    public List<DailyRecord> getDailyRecordsByPatient(int patientId, String startDate, String endDate) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM DailyRecords WHERE patient_id = ?");
        
        List<Object> parameters = new ArrayList<>();
        parameters.add(patientId);
    
        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND date >= ?");
            parameters.add(startDate);
        }
    
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND date <= ?");
            parameters.add(endDate);
        }
    
        List<DailyRecord> dailyRecords = new ArrayList<>();
    
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
    
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
    
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DailyRecord dailyRecord = new DailyRecord(
                            resultSet.getInt("record_id"),
                            resultSet.getInt("patient_id"),
                            resultSet.getString("date"),
                            resultSet.getDouble("blood_glucose_level"),
                            resultSet.getDouble("carb_intake"),
                            resultSet.getInt("medication_id"),
                            resultSet.getDouble("medication_dose")
                    );
                    dailyRecords.add(dailyRecord);
                }
            }
        }
    
        return dailyRecords;
    }


    public Averages getAverages(int patientId, String startDate, String endDate) {
        StringBuilder sql = new StringBuilder("SELECT AVG(blood_glucose_level) AS avg_glucose, AVG(carb_intake) AS avg_carb, AVG(medication_dose) AS avg_dose FROM DailyRecords WHERE patient_id = ?");
    
        List<Object> parameters = new ArrayList<>();
        parameters.add(patientId);
    
        if (startDate != null) {
            sql.append(" AND date >= ?");
            parameters.add(startDate);
        }
    
        if (endDate != null) {
            sql.append(" AND date <= ?");
            parameters.add(endDate);
        }
    
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
    
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
    
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    double avgGlucose = resultSet.getDouble("avg_glucose");
                    double avgCarb = resultSet.getDouble("avg_carb");
                    double avgDose = resultSet.getDouble("avg_dose");
    
                    return new Averages(avgGlucose, avgCarb, avgDose);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}

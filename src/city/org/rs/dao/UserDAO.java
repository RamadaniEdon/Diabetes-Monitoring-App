package city.org.rs.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import city.org.rs.ConnectionUtility;
import city.org.rs.models.User;
import city.org.rs.utils.PasswordUtil;

public class UserDAO {

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
            statement.setString(1, user.getUsername());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.getRole());
            statement.executeUpdate();
        }
    }

    public User getUser(int userId) throws SQLException {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role"));
            } else {
                return null;
            }
        }
    }

    public void updateUser(User user) throws SQLException {
        String sql;
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            sql = "UPDATE Users SET username = ?, role = ? WHERE user_id = ?";
        } else {
            sql = "UPDATE Users SET username = ?, role = ?, password = ? WHERE user_id = ?";
        }
    
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getRole());
    
            if (!(user.getPassword() == null || user.getPassword().isEmpty())) {
                String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
                statement.setString(3, hashedPassword);
                statement.setInt(4, user.getUserId());
            } else {
                statement.setInt(3, user.getUserId());
            }
    
            statement.executeUpdate();
        }
    }

    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection connection = ConnectionUtility.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")));
            }
        }
        return users;
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role"));
            } else {
                return null;
            }
        }
    }

    public String getUserRole(String username) throws SQLException {
        String sql = "SELECT role FROM Users WHERE username = ?";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("role");
            } else {
                return null;
            }
        }
    }

    public boolean isUserPatient(User user, int id) throws SQLException {
        String sql = "SELECT * FROM Patients WHERE user_id = ? AND patient_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getUserId());
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

}

package city.org.rs.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import city.org.rs.ConnectionUtility;
import city.org.rs.models.User;

public class UserDAO {

    // Method to add a new user
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (user_id, username, password, role) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.executeUpdate();
        }  
    }

    // Method to retrieve a user by ID
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
                    resultSet.getString("role")
                );
            } else {
                return null;
            }
        }
    }

    // Method to update a user's details
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET username = ?, password = ?, role = ? WHERE user_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setInt(4, user.getUserId());
            statement.executeUpdate();
        }
    }

    // Method to delete a user
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

    // Method to list all users
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
                    resultSet.getString("role")
                ));
            }
        }
        return users;
    }

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (Connection connection = ConnectionUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role")
                );
            } else {
                return null;
            }
        }
    }
}

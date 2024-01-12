package city.org.rs;

public class User {
    private int user_id;
    private String username;
    private String password;
    private String role;

    // Constructor
    public User(int user_id, String username, String password, String role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    //Constructor 2
  	public User(int userId) {
        this.user_id = userId;
  	}

  	//Constructor 3
  	public User() {
  	}

    // Getters and Setters
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

  
    @Override
    public String toString() {
        return "User{" +
               "userId=" + user_id +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", role='" + role + '\'' +
               '}';
    }
}

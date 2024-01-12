package city.org.rs;

public class Patient {
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private int userId; // Foreign key to Users table

    // Constructor
    public Patient(int patientId, String name, int age, String gender, int userId) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.userId = userId;
    }
    
    //Constructor 2
  	public Patient(int patientId) {
        this.patientId = patientId;
  	}

  	//Constructor 3
  	public Patient() {
  	}

    // Getters and Setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Patient{" +
               "patientId=" + patientId +
               ", name='" + name + '\'' +
               ", age=" + age +
               ", gender='" + gender + '\'' +
               ", userId=" + userId +
               '}';
    }
}


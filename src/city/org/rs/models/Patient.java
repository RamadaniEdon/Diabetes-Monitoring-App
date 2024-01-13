package city.org.rs.models;

public class Patient {
    private int patient_id;
    private String name;
    private int age;
    private String gender;
    private int user_id; // Foreign key to Users table

    // Constructor
    public Patient(int patient_id, String name, int age, String gender, int userId) {
        this.patient_id = patient_id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.user_id = user_id;
    }
    
    //Constructor 2
  	public Patient(int patient_id) {
        this.patient_id = patient_id;
  	}

  	//Constructor 3
  	public Patient() {
  	}

    // Getters and Setters
    public int getPatientId() {
        return patient_id;
    }

    public void setPatientId(int patient_id) {
        this.patient_id = patient_id;
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
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Patient{" +
               "patient_id=" + patient_id +
               ", name='" + name + '\'' +
               ", age=" + age +
               ", gender='" + gender + '\'' +
               ", user_id=" + user_id +
               '}';
    }
}


package city.org.rs;

import java.sql.Date;

public class DailyRecord {
    private int record_id;
    private int patient_id; // Foreign key to Patients table
    private java.sql.Date date;
    private double blood_glucose_level;
    private double carb_intake;
    private int medication_id;
    private double medication_dose ;
    

    

    // Constructor
    public DailyRecord(int record_id, int patient_id, java.sql.Date date, 
                       double blood_glucose_level, double carb_intake, double medication_dose, int medication_id) {
        this.record_id = record_id;
        this.patient_id= patient_id;
        this.date = date;
        this.blood_glucose_level = blood_glucose_level;
        this.carb_intake = carb_intake;
        this.medication_id = medication_id;
        this.medication_dose  = medication_dose ;
        
    }
    
  //Constructor 2
  	public DailyRecord(int record_id) {
  		 this.record_id = record_id;
  		 }

  	//Constructor 3
  	public DailyRecord() {
  	}

    public DailyRecord(int int1, int int2, Date date2, double double1, double double2, int int3, double double3) {
        //TODO Auto-generated constructor stub
    }

    // Getters and Setters
    public int getRecordId() {
        return record_id;
    }

    public void setRecordId(int record_id) {
        this.record_id = record_id;
    }

    public int getPatientId() {
        return patient_id;
    }

    public void setPatientId(int patient_id) {
        this.patient_id = patient_id;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public double blood_glucose_level() {
        return blood_glucose_level;
    }

    public void setBloodGlucoseLevel(double blood_glucose_level) {
        this.blood_glucose_level = blood_glucose_level;
    }
    
    public double getBloodGlucoseLevel() {
        return blood_glucose_level;
    }

    public double getCarbIntake() {
        return carb_intake;
    }

    public void setCarbIntake(double carb_intake) {
        this.carb_intake = carb_intake;
    }

    public double getMedicationDose() {
        return medication_dose ;
    }

    public void setMedicationDose(double medication_dose ) {
        this.medication_dose  = medication_dose ;
    }

    public int getMedication_id() {
        return medication_id;
    }

    public void setMedication_id(int medication_id) {
        this.medication_id = medication_id;
    }
    @Override
    public String toString() {
        return "DailyRecord{" +
               "record_id=" + record_id +
               ", patient_id=" + patient_id +
               ", date=" + date +
               ", blood_glucose_level=" + blood_glucose_level +
               ", carb_intake=" + carb_intake +
               ", medication_id =" + medication_id +
               ", medication_dose =" + medication_dose +
               '}';
    }
}

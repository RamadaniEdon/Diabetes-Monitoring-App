package city.org.rs.models;


public class DailyRecord {
    private int record_id;
    private int patient_id;
    private String date;
    private double blood_glucose_level;
    private double carb_intake;
    private int medication_id;
    private double medication_dose ;
    

    

    public DailyRecord(int record_id, int patient_id, String date, 
                       double blood_glucose_level, double carb_intake, int medication_id, double medication_dose) {
        this.record_id = record_id;
        this.patient_id= patient_id;
        this.date = date;
        this.blood_glucose_level = blood_glucose_level;
        this.carb_intake = carb_intake;
        this.medication_id = medication_id;
        this.medication_dose  = medication_dose ;
        
    }

    public DailyRecord(int patient_id, String date, 
                       double blood_glucose_level, double carb_intake, int medication_id, double medication_dose) {
        this.patient_id= patient_id;
        this.date = date;
        this.blood_glucose_level = blood_glucose_level;
        this.carb_intake = carb_intake;
        this.medication_id = medication_id;
        this.medication_dose  = medication_dose ;
        
    }
    
  	public DailyRecord(int record_id) {
  		 this.record_id = record_id;
  		 }

  	public DailyRecord() {
  	}

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public int getMedicationId() {
        return medication_id;
    }

    public void setMedicationId(int medication_id) {
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

package city.org.rs;

public class DailyRecord {
    private int recordId;
    private int patientId; // Foreign key to Patients table
    private java.sql.Date date;
    private double bloodGlucoseLevel;
    private double carbIntake;
    private double medicationDose;

    // Constructor
    public DailyRecord(int recordId, int patientId, java.sql.Date date, 
                       double bloodGlucoseLevel, double carbIntake, double medicationDose) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.date = date;
        this.bloodGlucoseLevel = bloodGlucoseLevel;
        this.carbIntake = carbIntake;
        this.medicationDose = medicationDose;
    }
    
  //Constructor 2
  	public DailyRecord(int recordId) {
  		 this.recordId = recordId;
  		 }

  	//Constructor 3
  	public DailyRecord() {
  	}

    // Getters and Setters
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public double getBloodGlucoseLevel() {
        return bloodGlucoseLevel;
    }

    public void setBloodGlucoseLevel(double bloodGlucoseLevel) {
        this.bloodGlucoseLevel = bloodGlucoseLevel;
    }

    public double getCarbIntake() {
        return carbIntake;
    }

    public void setCarbIntake(double carbIntake) {
        this.carbIntake = carbIntake;
    }

    public double getMedicationDose() {
        return medicationDose;
    }

    public void setMedicationDose(double medicationDose) {
        this.medicationDose = medicationDose;
    }

    @Override
    public String toString() {
        return "DailyRecord{" +
               "recordId=" + recordId +
               ", patientId=" + patientId +
               ", date=" + date +
               ", bloodGlucoseLevel=" + bloodGlucoseLevel +
               ", carbIntake=" + carbIntake +
               ", medicationDose=" + medicationDose +
               '}';
    }
}

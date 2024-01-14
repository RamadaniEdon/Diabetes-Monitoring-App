package city.org.rs.utils;

public class Averages {
  private double averageGlucoseLevel;
  private double averageCarbIntake;
  private double averageMedicationDose;

  public Averages(double averageGlucoseLevel, double averageCarbIntake, double averageMedicationDose) {
    this.averageGlucoseLevel = averageGlucoseLevel;
    this.averageCarbIntake = averageCarbIntake;
    this.averageMedicationDose = averageMedicationDose;
  }

  public double getAverageGlucoseLevel() {
    return averageGlucoseLevel;
  }

  public void setAverageGlucoseLevel(double averageGlucoseLevel) {
    this.averageGlucoseLevel = averageGlucoseLevel;
  }

  public double getAverageCarbIntake() {
    return averageCarbIntake;
  }

  public void setAverageCarbIntake(double averageCarbIntake) {
    this.averageCarbIntake = averageCarbIntake;
  }

  public double getAverageMedicationDose() {
    return averageMedicationDose;
  }

  public void setAverageMedicationDose(double averageMedicationDose) {
    this.averageMedicationDose = averageMedicationDose;
  }

  @Override
  public String toString() {
    return "Averages{" + "averageGlucoseLevel=" + averageGlucoseLevel + ", averageCarbIntake=" + averageCarbIntake
        + ", averageMedicationDose=" + averageMedicationDose + '}';
  }

  
}

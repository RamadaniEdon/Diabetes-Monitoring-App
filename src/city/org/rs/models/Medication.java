package city.org.rs.models;

    public class Medication {
        private int medication_id;
        private String medication_name;
        private String unit;
    
        public Medication(int medication_id, String medication_name, String unit) {
            this.medication_id = medication_id;
            this.medication_name = medication_name;
            this.unit = unit;
        }
    
        public Medication(String medication_name, String unit) {
            this.medication_name = medication_name;
            this.unit = unit;
        }

        public Medication() {
        }
    
        public int getMedicationId() {
            return medication_id;
        }
    
        public void setMedicationId(int medication_id) {
            this.medication_id = medication_id;
        }
    
        public String getMedicationName() {
            return medication_name;
        }
    
        public void setMedicationName(String medication_name) {
            this.medication_name = medication_name;
        }
    
        public String getUnit() {
            return unit;
        }
    
        public void setUnit(String unit) {
            this.unit = unit;
        }
    
        @Override
        public String toString() {
            return "Medication{" +
                   "medication_id=" + medication_id +
                   ", medication_name='" + medication_name + '\'' +
                   ", unit='" + unit + '\'' +
                   '}';
        }
    }
    
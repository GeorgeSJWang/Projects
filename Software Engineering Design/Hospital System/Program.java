import java.util.ArrayList;
import Patient.DeviceMeasurement;

public class Program {

	private int monitor_period;
	private ArrayList<Patient> patients;
	private Database database;

	public Program(int monitor_period, ArrayList<Patient> patients) {
		this.monitor_period = monitor_period;
		this.patients = patients;
		this.database = new Database();
		this.nurseStation = new NurseStation();
	}
	
	public void monitor() {
		for(int timer=0; timer<=monitor_period; timer+=1) {
			for(Patient patient : patients) {
				if(timer % patient.getFrequency() == 0) {
					DeviceMeasurement deviceMeasurement = patient.getFactor();
					storeDB(patient, deviceMeasurement, timer);
				}
			}
		}
		database.display();
	}
	
	public void storeDB(Patient patient, DeviceMeasurement deviceMeasurement, int currentTime) {
		database.insert(patient.getName(), deviceMeasurement.getDeviceCategory() + " " + deviceMeasurement.getDeviceName(), currentTime, deviceMeasurement.getmeasurementValue());
	}
	
	public boolean compareRange(Patient patient, DeviceMeasurement devideMeasurement, int currentTime) {
		if(true) {
			NurseStation.displayPatientDanger(currentTime, patientName, sensorName, value);
		}
	}
	
}

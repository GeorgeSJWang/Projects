public class NurseStation {
	/* Test
	 * public static void main(String argv[]){
		displayPatientDanger(1000, "Tony", "sensor1", 123);
		displayDevFall(2000, "sensor2");
	}*/
	
	public static void displayDevFall(int monitor_period, String sensorName){
		System.out.println("["+monitor_period+"] " + sensorName + " falls");
	}
	public static void displayPatientDanger(int monitor_period, String patientName, String sensorName, float value){
		System.out.print("["+monitor_period+"] " + patientName + " is in danger! Cause: " + sensorName + " ");		
		System.out.printf("%.1f\n", value);
	}
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Quiz {

	public static void main(String[] args) throws IOException {
		
		File file = new File(args[0]);
		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line = reader.readLine();
		int monitor_period = Integer.parseInt(line);
		
		ArrayList<Patient> patients = new ArrayList<Patient>();
		
		line = reader.readLine();
		while( line != null ) {
			String[] words = line.split(" ");
			if(words[0].equals("patient")) {
				Patient patient = new Patient(words);
				while( (line = reader.readLine()) != null) {
					if(line.startsWith("patient")) {
						break;
					}
					else {
						patient.attachAnalogDevice(line.split(" "));
					}
				}
				patients.add(patient);
			}
		}
		
		Program program = new Program(monitor_period, patients);
		program.monitor();

	}

}

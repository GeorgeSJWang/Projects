import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class AnalogDevice {
	
	public float  lowerRange, upperRange;
	public String name, dataSetFilename;
	public FileReader fd;
	public BufferedReader reader;

	// constructor
	public AnalogDevice(String name, String dataSetFilename, String lowRange, String upRange) {

		this.lowerRange = Float.parseFloat(lowRange);
		this.upperRange = Float.parseFloat(upRange);
		
		this.name = name;
		this.dataSetFilename = dataSetFilename;

		// open file and BufferedReader for reading/measureing data
		try{
			fd     = new FileReader(this.dataSetFilename);
			reader = new BufferedReader(this.fd);
		}catch (IOException e){
			e.printStackTrace();
		}

	}

	// when called to do measure(), go to read dataSetFile
	public float measure() {

		float  measurement = -1;
		String line;

		try{
			// anything at or after EOF is -1 
			if ( (line = reader.readLine()) != null)
				// convert String to float
				measurement = Float.parseFloat(line);
			else
				measurement = -1;

		}catch (IOException e){
			e.printStackTrace();
		}

		return measurement;
	}

	public abstract String getCategoryName();

}


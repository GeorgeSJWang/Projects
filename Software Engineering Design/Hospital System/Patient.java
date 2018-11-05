import java.util.*;

public class Patient {
	private String name;
	private Integer frequency;
	private List<AnalogDevice> analogDevices = new LinkedList<AnalogDevice>();
	
	public Patient(String[] words, String[] lines){
		this.name = words[1];
		this.frequency = Integer.parseInt(words[2]);
	}

	public String getName(){
		return name;
	}

	public Integer getFrequency(){
		return frequency;
	}
	
	public Boolean attachAnalogDevice(String[] deviceInformation){

		String deviceCategory = deviceInformation[0];
		AnalogDevice analogDevice;
		
		if (deviceCategory.equals("PulseSensor")){
			analogDevice = new PulseSensor(deviceInformation[1], deviceInformation[2], deviceInformation[3], deviceInformation[4]);
		}
		else if (deviceCategory.equals("BloodPressureSensor")){
			analogDevice = new BloodPressureSensor(deviceInformation[1], deviceInformation[2], deviceInformation[3], deviceInformation[4]);
		}
		else if (deviceCategory.equals("TemperatureSensor")){
			analogDevice = new TemperatureSensor(deviceInformation[1], deviceInformation[2], deviceInformation[3], deviceInformation[4]);
		}
		else{
			System.out.println("invalid device type");
			return false;
		}
		
		analogDevices.add(analogDevice);
		return true;
	}

	public List<DeviceMeasurement> getFactors(){
		List<DeviceMeasurement> res = new LinkedList<DeviceMeasurement>();
		for (int i=0;i<analogDevices.size();++i){
			AnalogDevice analogDevice = analogDevices.get(i);
			DeviceMeasurement deviceMeasurement = new DeviceMeasurement(analogDevice.getCategoryName(), analogDevice.name, analogDevice.measure());
			res.add(deviceMeasurement);
		}
		return res;
	}
}
public class DeviceMeasurement{
	
	private String deviceCategory;
	private String deviceName;
	private Float measurementValue;

	public DeviceMeasurement(String deviceCategory, String deviceName, Float measurementValue){
		this.deviceCategory = deviceCategory;
		this.deviceName = deviceName;
		this.measurementValue = measurementValue;
	}

	public String getDeviceCategory(){
		return deviceCategory;
	}

	public String getDeviceName(){
		return deviceName;
	}

	public Float getmeasurementValue(){
		return measurementValue;
	}
}
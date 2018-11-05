public class BloodPressureSensor extends AnalogDevice{

	public BloodPressureSensor(String name, String dataSetFilename, String lowRange, String upRange) {
		super(name, dataSetFilename, lowRange, upRange);
	}

	public String getCategoryName() {
		return "BloodPressureSensor";
	}

}
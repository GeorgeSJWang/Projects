public class TemperatureSensor extends AnalogDevice{

	public TemperatureSensor(String name, String dataSetFilename, String lowRange, String upRange) {
		super(name, dataSetFilename, lowRange, upRange);
	}

	public String getCategoryName() {
		return "TemperatureSensor";
	}

}
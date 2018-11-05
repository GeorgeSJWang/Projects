public class PulseSensor extends AnalogDevice{

	public PulseSensor(String name, String dataSetFilename, String lowRange, String upRange) {
		super(name, dataSetFilename, lowRange, upRange);
	}

	public String getCategoryName() {
		return "PulseSensor";
	}

}
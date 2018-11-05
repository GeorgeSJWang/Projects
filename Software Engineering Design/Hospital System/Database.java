import java.util.*;

public class Database{

	public List<String> patientOrder;
	public Map<String, ArrayList<String>> deviceOrder;
	public HashMap<String, HashMap<String, ArrayList<Record>>> patientRecord;

	Database(){
		patientOrder = new ArrayList<String>();
		deviceOrder = new HashMap<String, ArrayList<String>>();
		patientRecord = new HashMap<String, HashMap<String, ArrayList<Record>>>();
	}

	public void insert(String patientName, String deviceName, int time, float value){
		if(!patientRecord.containsKey(patientName)){
			patientOrder.add(patientName);
			HashMap<String, ArrayList<Record>> recordList = new HashMap<String, ArrayList<Record>>();
			patientRecord.put(patientName, recordList);
			ArrayList<String> deviceList = new ArrayList<String>();
			deviceOrder.put(patientName, deviceList);
		}
		if(!patientRecord.get(patientName).containsKey(deviceName)){
			ArrayList<Record> record = new ArrayList<Record>();
			deviceOrder.get(patientName).add(deviceName);
			patientRecord.get(patientName).put(deviceName, record);
		}

		Record tempRecord = new Record(time, value);
		patientRecord.get(patientName).get(deviceName).add(tempRecord);
	}

	public void display(){
		for(String patientName: patientOrder){
			System.out.println("patient "+patientName);
			for(String deviceName: deviceOrder.get(patientName)){
				System.out.println(deviceName);
				for(Record record: patientRecord.get(patientName).get(deviceName)){
					System.out.print("["+record.time+"] ");
					System.out.println("%.1f", record.value);
				}
			}
		}
	}

	private class Record{
		int time;
		float value;
		Record(int time, float value){
			this.time = time;
			this.value = value;
		}
	}
}
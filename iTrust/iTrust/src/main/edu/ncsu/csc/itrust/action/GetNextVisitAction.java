package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.model.old.beans.ApptBean;
import edu.ncsu.csc.itrust.model.old.beans.ApptTypeBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ApptTypeDAO;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import com.google.gson.*;

import java.text.SimpleDateFormat;

/**
 * 
 * Calculate the next Visit date and return
 *
 */
public class GetNextVisitAction {
	private ApptDAO apptDAO;
	private ApptTypeDAO apptTypeDAO;
	private long loggedInMID;
	/**
	 * 
	 * Approximately calculate next visit date
	 */
	public GetNextVisitAction(){}
	public GetNextVisitAction(DAOFactory factory, long loggedInMID){
		this.apptDAO = factory.getApptDAO();
		this.apptTypeDAO = factory.getApptTypeDAO();
		this.loggedInMID = loggedInMID;
	}
	public String GetNextDateString(ObstetricsVisitBean v){
		int numWeeks = Integer.parseInt(v.getNumWeeks().split("-",0)[0]);
		Timestamp oldTs = v.getScheduledDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldTs);
		
		if(numWeeks<13){
			cal.add(Calendar.DATE, 28);
		}else if(numWeeks<28){
			cal.add(Calendar.DATE, 14);
		}else if(numWeeks<40){
			cal.add(Calendar.DATE, 7);
		}else if(numWeeks<42){
			cal.add(Calendar.DATE, 2);
		}else if(numWeeks==42){
			
		}
		

		
		
		CheckAndUpdate(cal);
		Timestamp defaultTime = new Timestamp(cal.getTime().getTime());
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Timestamp startTime = new Timestamp(cal.getTime().getTime());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Timestamp endTime = new Timestamp(cal.getTime().getTime());
		List<Timestamp>listSchedule = new ArrayList<Timestamp>();
		try {
			listSchedule = getSchedule(startTime,endTime,v.getPatientID());
		}catch(Exception e) {
			e.printStackTrace();
		}
		defaultTime = GetNextTime(listSchedule, defaultTime);
		
		
		
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		return f.format(defaultTime);
	}
	
	/**
	 * 
	 * Approximately calculate next visit time
	 */
	public Timestamp GetNextTime(List<Timestamp> et, Timestamp dt) {
	
		// define the appointment period and increment period
		int app_slot = 1800;
		int t_inc = 1800;
		
		Timestamp st_s = dt;
		Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dt.getTime());
        cal.add(Calendar.SECOND, app_slot);
        Timestamp st_e = new Timestamp(cal.getTime().getTime());
        
        // Verify if the patient is available during the default time
        
        int List_len = et.size();
        int i = 0;
        boolean available = true;
        while (i<List_len) {
            available = available && (st_e.before(et.get(i)) || st_s.after(et.get(i+1)));
            i = i + 2;
        }
        if(available) {
        	return st_s;
        }
		
        // Loop through the day to find the earliest availability
        // Set last time slot
		cal.set(Calendar.HOUR_OF_DAY, 16);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.SECOND, - app_slot);
        Timestamp st_lim = new Timestamp(cal.getTime().getTime());
        // Initialize the start time
		cal.set(Calendar.HOUR_OF_DAY, 8);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);
        st_s = new Timestamp(cal.getTime().getTime());
        cal.add(Calendar.SECOND, app_slot);
        // Increment time
        while(st_s.before(st_lim)) {
            st_s = new Timestamp(cal.getTime().getTime());
            cal.add(Calendar.SECOND, app_slot);
            st_e = new Timestamp(cal.getTime().getTime());
        	// Loop through each event and check for the availability
            i = 0;
            available =  true;
            while (i<List_len) {
                available = available && (st_e.before(et.get(i)) || st_s.after(et.get(i+1)));
                i = i + 2;
            }
            if(available) {
            	return st_s;
            }
            cal.add(Calendar.SECOND, t_inc - app_slot);
        }
		return st_s;
	}
	
	
	/**
	 * 
	 * Check for Holidays or weekends
	 */
	public void CheckAndUpdate(Calendar cal){
		while(!isBusinessDay(cal)){
			cal.add(Calendar.DATE, 1);
		}
	}
	
	public boolean isBusinessDay(Calendar cal){
		// check if weekend
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			return false;
		}
		
		// check if New Year's Day
		if (cal.get(Calendar.MONTH) == Calendar.JANUARY
			&& cal.get(Calendar.DAY_OF_MONTH) == 1) {
			return false;
		}
		
		// check if Christmas eve
		if (cal.get(Calendar.MONTH) == Calendar.DECEMBER
			&& cal.get(Calendar.DAY_OF_MONTH) == 24) {
			return false;
		}
		
		// check if Christmas
		if (cal.get(Calendar.MONTH) == Calendar.DECEMBER
			&& cal.get(Calendar.DAY_OF_MONTH) == 25) {
			return false;
		}
		
		// check if 4th of July
		if (cal.get(Calendar.MONTH) == Calendar.JULY
			&& cal.get(Calendar.DAY_OF_MONTH) == 4) {
			return false;
		}
		
		// check Thanksgiving (4th Thursday of November)
		if (cal.get(Calendar.MONTH) == Calendar.NOVEMBER
			&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 4
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			return false;
		}
		
		// check Memorial Day (last Monday of May)
		if (cal.get(Calendar.MONTH) == Calendar.MAY
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
			&& cal.get(Calendar.DAY_OF_MONTH) > (31 - 7) ) {
			return false;
		}
		
		// check Labor Day (1st Monday of September)
		if (cal.get(Calendar.MONTH) == Calendar.SEPTEMBER
			&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			return false;
		}
		
		// check Columbus Day (2nd Monday of October)
		if (cal.get(Calendar.MONTH) == Calendar.OCTOBER
			&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 2
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			return false;
		}
		
		// check President's Day (3rd Monday of February)
		if (cal.get(Calendar.MONTH) == Calendar.FEBRUARY
		&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3
		&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
		return false;
		}
		
		
		// check MLK Day (3rd Monday of January)
		if (cal.get(Calendar.MONTH) == Calendar.JANUARY
		&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3
		&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
		return false;
		}
		
		// IF NOTHING ELSE, IT'S A BUSINESS DAY
		return true;
	}
	
	
	public String TimestampToRFC3399(Timestamp ts){
		Date d = new Date(ts.getTime());
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(d);
	}
	
	public String TimestampToGooCal(Timestamp ts){
		Date d = new Date(ts.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(d);
	}
	
	public Timestamp RFC3399ToTimestamp(String rts){
		Timestamp ts = null;
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			Date parsedDate = dateFormat.parse(rts);
		    ts = new java.sql.Timestamp(parsedDate.getTime());
		}catch(Exception e){
		}
		return ts;
	}
	
	public List<Timestamp> getSchedule(Timestamp tMin, Timestamp tMax, long pid) throws Exception{
		List<Timestamp> listSchedule = new ArrayList<Timestamp>();
		List<ApptBean> appts = apptDAO.getApptsFor(pid);
		appts.addAll(apptDAO.getApptsFor(loggedInMID));
		for (int i = 0; i < appts.size(); i++){
			if (appts.get(i).getDate().after(tMin)&&appts.get(i).getDate().before(tMax)){
				listSchedule.add(appts.get(i).getDate());
				ApptTypeBean apptType = apptTypeDAO.getApptType(appts.get(i).getApptType());
				
		        Calendar cal = Calendar.getInstance();
		        cal.setTimeInMillis(appts.get(i).getDate().getTime());
		        cal.add(Calendar.MINUTE, apptType.getDuration());
		        Timestamp endTime = new Timestamp(cal.getTime().getTime());
		        
				listSchedule.add(endTime);
			}
		}
		// check google calendar
		/*String timeMin = TimestampToRFC3399(tMin);
		String timeMax = TimestampToRFC3399(tMax);
		String url = "https://www.googleapis.com/calendar/v3/calendars/"
				+ "osha1mpeirdg6m5uun9rmnbass@group.calendar.google.com/events?"
				+ "key=AIzaSyDecA3yotvFE32SdVvzWqXHnH8AUAMkq68"
				+ "&timeMin=" + timeMin
				+ "&timeMax=" + timeMax;
		
		URLConnection conn = new URL(url).openConnection();
		conn.setRequestProperty("Accept-Charset", "UTF-8");
		InputStream response = conn.getInputStream();	
		
		Scanner s = new Scanner(response).useDelimiter("\\A");
		String result = s.hasNext() ? s.next() : "";
		
		JsonObject jobj = new Gson().fromJson(result, JsonObject.class);
		JsonArray ja = jobj.getAsJsonArray("items");
		
		for(int i=0; i<ja.size(); ++i){
			JsonElement j = ja.get(i);
			JsonObject jo = j.getAsJsonObject();
			JsonElement start = jo.get("start");
			JsonElement end   = jo.get("end");
			String startStr = start.getAsJsonObject().get("dateTime").toString();
			String endStr = end.getAsJsonObject().get("dateTime").toString();
			startStr = startStr.substring(1, startStr.length()-1);
			endStr = endStr.substring(1, endStr.length()-1);
			listSchedule.add(RFC3399ToTimestamp(startStr));
			listSchedule.add(RFC3399ToTimestamp(endStr));
		}*/
		return listSchedule;
	}
	
	public String getGoogleCalendarLink(Timestamp scheduledDate){
		String link = "https://calendar.google.com/calendar/r/eventedit?";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(scheduledDate.getTime());
		cal.add(Calendar.MINUTE, 30);
		Timestamp endTime = new Timestamp(cal.getTime().getTime());
		
		String startTimeString = TimestampToGooCal(scheduledDate);
		String endTimeString = TimestampToGooCal(endTime);
		
		link = link + "&" +
				"dates=" + startTimeString + "%2F" + endTimeString + "&" +
				"text=" + "iTrust Office Visit&" +
				"location=" + "iTrust Hospital&" +
				"detail=" + "iTrust Office Visit";
		
		return link;
	}
	
}
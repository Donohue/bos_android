package org.artsinbushwick.bos13;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import android.database.Cursor;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Hours {
	private int opens;
	private int closes;
	private String notes;
	
	public Hours() {
		
	}
	
	public Hours(Cursor cursor) {
		opens = cursor.getInt(0);
		closes = cursor.getInt(1);
		notes = cursor.getString(2);
	}
	
	public int getOpens() {
		return opens;
	}
	public long getOpensEpoch() {
		return (long)getOpens() * 1000;
	}
	public void setOpens(int opens) {
		this.opens = opens;
	}
	public int getCloses() {
		return closes;
	}
	public long getClosesEpoch() {
		return (long)getCloses() * 1000;
	}
	public void setCloses(int closes) {
		this.closes = closes;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String dayOfWeek() {
		Date open = new Date(getOpensEpoch());
		SimpleDateFormat df = new SimpleDateFormat("EEE");
		df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		String day = df.format(open);
		return day;
	}
	
	public boolean openFriday() {
		return dayOfWeek().equals("Fri");
	}
	
	public boolean openSaturday() {
		return dayOfWeek().equals("Sat");
	}
	
	public boolean openSunday() {
		return dayOfWeek().equals("Sun");
	}
	
	public String toString() {
		Date opens = new Date(getOpensEpoch());
		Date close = new Date(getClosesEpoch());
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
		df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return df.format(opens) + " - " + df.format(close);
	}
}

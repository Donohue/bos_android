package org.artsinbushwick.bos13;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import android.database.Cursor;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Event {
	private int id;
	private String short_desc;
	private String section;
	private boolean active;
	private ArrayList<Integer> artists;
	private String room;
	private int last_modified;
	private String name;
	private String long_desc;
	private ArrayList<Hours> hours;
	private ArrayList<Integer> categories;
	private int venue;
	
	
	public Event() {
		super();
	}
	
	public Event(Cursor cursor) {
		setId(cursor.getInt(0));
		setShortDesc(cursor.getString(1));
		setSection(cursor.getString(2));
		setActive(cursor.getInt(3) == 1);
		setRoom(cursor.getString(4));
		setLastModified(cursor.getInt(5));
		setName(cursor.getString(6));
		setLongDesc(cursor.getString(7));
		setVenue(cursor.getInt(8));
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShortDesc() {
		return short_desc;
	}
	public void setShort_desc(String short_desc) {
		this.short_desc = short_desc;
	}
	public void setShortDesc(String short_desc) {
		this.short_desc = short_desc;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getActive() {
		return isActive()? 1: 0;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public ArrayList<Integer> getArtists() {
		return artists;
	}
	public void setArtists(ArrayList<Integer> artists) {
		this.artists = artists;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public int getLastModified() {
		return last_modified;
	}
	public void setLast_modified(int last_modified) {
		this.last_modified = last_modified;
	}
	public void setLastModified(int last_modified) {
		this.last_modified = last_modified;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLongDesc() {
		return long_desc;
	}
	public void setLong_desc(String long_desc) {
		this.long_desc = long_desc;
	}
	public void setLongDesc(String long_desc) {
		this.long_desc = long_desc;
	}
	public ArrayList<Hours> getHours() {
		return hours;
	}
	public void setHours(ArrayList<Hours> hours) {
		this.hours = hours;
	}
	public int getVenue() {
		return venue;
	}
	public void setVenue(int venue) {
		this.venue = venue;
	}

	public ArrayList<Integer> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Integer> categories) {
		this.categories = categories;
	}
}

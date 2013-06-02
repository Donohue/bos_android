package org.artsinbushwick.bos13;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import android.database.Cursor;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Studio {
	private int id;
	private float lon;
	private boolean active;
	private int last_modified;
	private String map_identifier;
	private float lat;
	private String address;
	private String name;
	private String state;
	private String city;
	private String country;
	private String zip;
	private String eventName;
	
	public Studio() {
		
	}
	
	public Studio(Cursor cursor) {
		setId(cursor.getInt(0));
		setLon(cursor.getFloat(1));
		setActive(cursor.getInt(2) == 1);
		setLastModified(cursor.getInt(3));
		setMap_identifier(cursor.getString(4));
		setLat(cursor.getFloat(5));
		setAddress(cursor.getString(6));
		setName(cursor.getString(7));
		setState(cursor.getString(8));
		setCity(cursor.getString(9));
		setCountry(cursor.getString(10));
		setZip(cursor.getString(11));
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getLastModified() {
		return last_modified;
	}
	public void setLastModified(int last_modified) {
		this.last_modified = last_modified;
	}
	public String getMap_identifier() {
		return map_identifier;
	}
	public void setMap_identifier(String map_identifier) {
		this.map_identifier = map_identifier;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
}

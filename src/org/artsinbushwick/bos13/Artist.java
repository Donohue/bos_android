package org.artsinbushwick.bos13;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import android.database.Cursor;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Artist {
	private int id;
	private int last_modified;
	private String name;
	private boolean active;
	
	public Artist() {
		
	}
	
	public Artist(Cursor cursor) {
		setId(cursor.getInt(0));
		setLastModified(cursor.getInt(1));
		setName(cursor.getString(2));
		setActive(cursor.getInt(3) == 1);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLastModified() {
		return last_modified;
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
	public int getActive() {
		if (isActive())
			return 1;
		else
			return 0;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}

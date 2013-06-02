package org.artsinbushwick.bos13;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import android.database.Cursor;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Category {
	private int order;
	private String name;
	private String group;
	private int id;
	private boolean active;
	private int last_modified;
	
	public Category() {
		
	}

	public Category(Cursor cursor) {
		order = cursor.getInt(0);
		name = cursor.getString(1);
		group = cursor.getString(2);
		id = cursor.getInt(3);
		active = cursor.getInt(4) == 1;
		last_modified = cursor.getInt(5);
	}
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isActive() {
		return active;
	}
	public int getActive() {
		return isActive()? 1: 0;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(int last_modified) {
		this.last_modified = last_modified;
	}
}

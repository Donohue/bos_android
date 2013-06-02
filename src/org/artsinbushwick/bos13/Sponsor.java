package org.artsinbushwick.bos13;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Sponsor {
	private String name;
	private String website;
	private int id;
	private String short_desc;
	private boolean active;
	private String sponsor_level;
	private int last_modified;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
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
	public void setShortDesc(String short_desc) {
		this.short_desc = short_desc;
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
	public String getSponsorLevel() {
		return sponsor_level;
	}
	public void setSponsorLevel(String sponsor_level) {
		this.sponsor_level = sponsor_level;
	}
	public int getLastModified() {
		return last_modified;
	}
	public void setLastModified(int last_modified) {
		this.last_modified = last_modified;
	}
}

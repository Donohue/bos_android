package org.artsinbushwick.bos13;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class StudioList {
	private List<Studio> result;
	
	public List<Studio> getResult() {
		return result;
	}
	
	public void setResult(List<Studio> results) {
		this.result = results;
	}
}

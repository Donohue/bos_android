package org.artsinbushwick.bos13;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SponsorList {
	private List<Sponsor> result;
	
	public List<Sponsor> getResult() {
		return result;
	}
	
	public void setResult(List<Sponsor> results) {
		this.result = results;
	}
}

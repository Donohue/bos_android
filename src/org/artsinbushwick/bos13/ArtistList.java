package org.artsinbushwick.bos13;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ArtistList {
	public List<Artist> result;
	
	public List<Artist> getResult() {
		return result;
	}
	
	public void setResult(List<Artist> r) {
		this.result = r;
	}
}

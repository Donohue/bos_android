package org.artsinbushwick.bos13;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EventResult {
	private EventList result;

	public EventList getResult() {
		return result;
	}

	public void setResult(EventList result) {
		this.result = result;
	}
}

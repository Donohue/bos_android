package org.artsinbushwick.bos13;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class EventRequest extends SpringAndroidSpiceRequest<EventList> {
	private String ep;
	
	public EventRequest(String e) {
		super(EventList.class);
		ep = e;
	}
	
	@Override
	public EventList loadDataFromNetwork() throws Exception {
		return getRestTemplate().getForObject(ep + "/events.json", EventList.class);
	}
}

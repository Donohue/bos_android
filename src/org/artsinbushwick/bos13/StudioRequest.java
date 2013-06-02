package org.artsinbushwick.bos13;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class StudioRequest extends SpringAndroidSpiceRequest<StudioList> {
	private String ep;
	
	public StudioRequest(String e) {
		super(StudioList.class);
		ep = e;
	}
	
	@Override
	public StudioList loadDataFromNetwork() throws Exception {
		return getRestTemplate().getForObject(ep + "/venues.json", StudioList.class);
	}
}

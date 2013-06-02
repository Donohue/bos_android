package org.artsinbushwick.bos13;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class SponsorRequest extends SpringAndroidSpiceRequest<SponsorList> {
	private String ep;
	
	public SponsorRequest(String e) {
		super(SponsorList.class);
		ep = e;
	}
	
	@Override
	public SponsorList loadDataFromNetwork() throws Exception {
		return getRestTemplate().getForObject(ep + "/sponsors.json", SponsorList.class);
	}
}

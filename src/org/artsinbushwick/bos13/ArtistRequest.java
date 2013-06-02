package org.artsinbushwick.bos13;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class ArtistRequest extends SpringAndroidSpiceRequest<ArtistList> {
	private String ep;
	
	public ArtistRequest(String e) {
		super(ArtistList.class);
		ep = e;
	}
	
	@Override
	public ArtistList loadDataFromNetwork() throws Exception {
		return getRestTemplate().getForObject(ep + "/artists.json", ArtistList.class);
	}
}

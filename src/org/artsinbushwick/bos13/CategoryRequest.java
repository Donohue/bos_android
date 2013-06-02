package org.artsinbushwick.bos13;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class CategoryRequest extends SpringAndroidSpiceRequest<CategoryList> {
	private String ep;
	
	public CategoryRequest(String e) {
		super(CategoryList.class);
		ep = e;
	}
	
	@Override
	public CategoryList loadDataFromNetwork() throws Exception {
		return getRestTemplate().getForObject(ep + "/categories.json", CategoryList.class);
	}
}

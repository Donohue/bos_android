package org.artsinbushwick.bos13;

public class OfficialEventListAdapter extends EventListAdapter {
	public OfficialEventListAdapter(MainActivity activity) {
		super(activity, activity.database);
		initialize(activity.database.getFridayEvents("events"),
				   activity.database.getSaturdayEvents("events"),
				   activity.database.getSundayEvents("events"));
	}

}

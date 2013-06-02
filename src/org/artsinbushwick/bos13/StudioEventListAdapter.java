package org.artsinbushwick.bos13;

import android.app.Activity;

public class StudioEventListAdapter extends EventListAdapter {
	public StudioEventListAdapter(Activity activity, Database database, Studio studio) {
		super(activity, database);
		initialize(database.getFridayEventsAtStudio(studio),
				   database.getSaturdayEventsAtStudio(studio),
				   database.getSundayEventsAtStudio(studio));
	}
}

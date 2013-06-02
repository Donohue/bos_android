package org.artsinbushwick.bos13;

import android.app.Activity;

public class StudioCategoryListAdapter extends EventListAdapter {
	public StudioCategoryListAdapter(Activity activity, Database database, Category category) {
		super(activity, database);
		initialize(database.getFridayEventsWithCategory(category),
				   database.getSaturdayEventsWithCategory(category),
				   database.getSundayEventsWithCategory(category));
	}
}

package org.artsinbushwick.bos13;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MoreFragment extends ListFragment {
    /** An array of items to display in ArrayList */
	 String apple_versions[] = new String[]{
             "A Message to Our Community",
             "About Arts in Bushwick",
             "Our Sponsors"
     };
    	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		/** Creating array adapter to set data in listview */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, apple_versions);

        /** Setting the array adapter to the listview */
        setListAdapter(adapter);

		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = null;
		switch (position) {
		case 0:
			i = new Intent(getActivity(), MessageActivity.class);
			break;
		case 1:
			i = new Intent(getActivity(), AboutActivity.class);
			break;
		case 2:
			i = new Intent(getActivity(), SponsorActivity.class);
			break;
		}
		
		startActivity(i);
	}
}

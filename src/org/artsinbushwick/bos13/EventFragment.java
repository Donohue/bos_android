package org.artsinbushwick.bos13;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class EventFragment extends Fragment implements OnItemClickListener {
	OfficialEventListAdapter adapter;
    	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new OfficialEventListAdapter((MainActivity)getActivity());
		View view = inflater.inflate(R.layout.sticky_header_list, container, false);
		StickyListHeadersListView list_view = (StickyListHeadersListView)view.findViewById(R.id.sticky_list);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(this);
        return view;
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Event event = adapter.getItem(arg2);
		Intent i = new Intent(getActivity(), EventActivity.class);
		i.putExtra("event_id", event.getId());
		getActivity().startActivity(i);
	}
}

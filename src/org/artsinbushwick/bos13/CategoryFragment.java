package org.artsinbushwick.bos13;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.Fragment;

public class CategoryFragment extends Fragment implements OnItemClickListener {
	private CategoryListAdapter adapter;
	
	public CategoryFragment(CategoryListAdapter a) {
		adapter = a;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sticky_header_list,  container, false);
		StickyListHeadersListView list_view = (StickyListHeadersListView)view.findViewById(R.id.sticky_list);
		list_view.setAdapter(adapter);
		list_view.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Category category = adapter.getItem(arg2);
		Intent i = new Intent(getActivity(), CategoryActivity.class);
		i.putExtra("category_id", category.getId());
		getActivity().startActivity(i);		
	}
}

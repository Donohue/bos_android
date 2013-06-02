package org.artsinbushwick.bos13;

import java.util.ArrayList;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryListAdapter extends ArrayAdapter<Category> implements StickyListHeadersAdapter {
	private ArrayList<Category> categories = new ArrayList<Category>(0);
	private ArrayList<Integer> studio_counts;
	private LayoutInflater inflater;
	private String[] sections = {
			"Special",
			"Event Features",
			"Visual Arts",
			"Design/Media/Craft",
			"Performing Arts",
			"Other"
	};
	
	public CategoryListAdapter(MainActivity activity) {
		super(activity, android.R.layout.simple_list_item_1, new ArrayList<Category>(0));
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		categories = activity.database.getCategories();
		studio_counts = new ArrayList<Integer>();
		for (int i = 0; i < categories.size(); i++) {
			Category category = categories.get(i);
			studio_counts.add(activity.database.countEventsWithCategory(category));
		}
	}
	
	@Override
	public int getCount() {
		return categories.size();
	}
	
	@Override
	public Category getItem(int i) {
		return categories.get(i);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CategoryHolder holder = null;
		
		if (row == null) {
			row = inflater.inflate(R.layout.category_row, parent, false);
			
			holder = new CategoryHolder();
			holder.category_name = (TextView)row.findViewById(R.id.category_row_name);
			holder.category_count = (TextView)row.findViewById(R.id.category_row_number);
			
			row.setTag(holder);
		}
		else {
			holder = (CategoryHolder)row.getTag();
		}
		
		Category category = categories.get(position);
		Integer count = studio_counts.get(position);
		holder.category_name.setText(category.getName());
		holder.category_count.setText(count + "");
		
		return row;
	}
	
	static class CategoryHolder {
		TextView category_name;
		TextView category_count;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		TextView row = (TextView)convertView;
		
		if (row == null) {
			row = (TextView)inflater.inflate(R.layout.section_header, parent, false);;
		}
		
		Category category = categories.get(position);
		row.setText(category.getGroup());
		
		return row;
	}

	@Override
	public long getHeaderId(int position) {
		Category category = getItem(position);
		int i = 0;
		for (; i < sections.length; i++) {
			if (category.getGroup().equals(sections[i]))
				break;
		}
		
		return i;
	}
}
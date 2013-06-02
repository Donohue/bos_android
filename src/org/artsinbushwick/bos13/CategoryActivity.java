package org.artsinbushwick.bos13;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class CategoryActivity extends Activity implements OnItemClickListener {
	StudioCategoryListAdapter adapter;
	
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.sticky_header_list);
		
		Intent intent = getIntent();
		int category_id = intent.getIntExtra("category_id", -1);
		Database database = new Database(this);
		Category category = database.getCategory(category_id);
		setTitle(category.getName());
		StickyListHeadersListView list = (StickyListHeadersListView)findViewById(R.id.sticky_list);
		adapter = new StudioCategoryListAdapter(this, database, category);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Event event = adapter.getItem(arg2);
		Intent i = new Intent(this, EventActivity.class);
		i.putExtra("event_id", event.getId());
		startActivity(i);
	}
}

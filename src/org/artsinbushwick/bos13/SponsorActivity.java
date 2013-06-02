package org.artsinbushwick.bos13;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class SponsorActivity extends ListActivity {
	ArrayList<Sponsor> sponsors;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		Database database = new Database(this);
		ArrayList<Sponsor> sponsors = database.getSponsors();
		ArrayList<String> values = new ArrayList<String>();
		for (int i = 0; i < sponsors.size(); i++) {
			Sponsor sponsor = sponsors.get(i);
			if (sponsor.getName() != null) {
				System.out.println(sponsor.getName());
				values.add(sponsor.getName());
			}
			else {
				System.out.println("SPONSOR NAME NULL");
			}
		}
	
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
}

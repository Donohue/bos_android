package org.artsinbushwick.bos13;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SponsorFragment extends ListFragment {
	ArrayList<Sponsor> sponsors;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Database database = new Database(getActivity());
		sponsors = database.getSponsors();
		ArrayList<String> values = new ArrayList<String>();
		for (int i = 0; i < sponsors.size(); i++) {
			Sponsor sponsor = sponsors.get(i);
			if (sponsor.getName() != null) {
				values.add(sponsor.getName());
			}
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Sponsor sponsor = sponsors.get(position);
		new AlertDialog.Builder(getActivity())
		.setTitle("Sponsor Website")
		.setMessage("This will open the sponsor's website in your browser. Are you sure?")
		.setPositiveButton("Yes", new SponsorClickConfirm(sponsor))
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {				
			}
		}).show();
	}
	
	public class SponsorClickConfirm implements OnClickListener {
		Sponsor sponsor;
		public SponsorClickConfirm(Sponsor s) {
			sponsor = s;
		}
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(sponsor.getWebsite()));
			startActivity(intent);
		}
	}

}

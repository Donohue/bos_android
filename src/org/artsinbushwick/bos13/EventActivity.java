package org.artsinbushwick.bos13;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventActivity extends Activity implements OnTouchListener {
	private Event event;
	private Studio studio;
	
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.event_layout);
		
		Database database = new Database(this);
		Intent i = getIntent();
		int event_id = i.getIntExtra("event_id", -1);
		event = database.getEvent(event_id);
		studio = database.getStudio(event.getVenue());
		
		TextView event_name = (TextView)findViewById(R.id.event_view_name);
		event_name.setText(event.getName());

		ArrayList<Hours> hours = database.getEventHours(event);
		ArrayList<Hours> friday_hours = new ArrayList<Hours>();
		ArrayList<Hours> saturday_hours = new ArrayList<Hours>();
		ArrayList<Hours> sunday_hours = new ArrayList<Hours>();
		String hours_string = "";
		for (int j = 0; j < hours.size(); j++) {
			Hours event_hours = hours.get(j);
			Date open = new Date(event_hours.getOpensEpoch());
			SimpleDateFormat df = new SimpleDateFormat("EEE");
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			String day = df.format(open);
			System.out.println(event_hours.getOpensEpoch());
			System.out.println(open.getTime());
			System.out.println(day);
			if (day.equalsIgnoreCase("Fri")) {
				friday_hours.add(event_hours);
			}
			else if (day.equalsIgnoreCase("Sat")) {
				saturday_hours.add(event_hours);
			}
			else if (day.equalsIgnoreCase("Sun")) {
				sunday_hours.add(event_hours);
			}
		}
		
		if (friday_hours.size() > 0) {
			hours_string += "Friday, ";
		}
		
		for (int j = 0; j < friday_hours.size(); j++) {
			Hours event_hours = friday_hours.get(j);
			hours_string += event_hours.toString() + "\n";
		}
		
		if (saturday_hours.size() > 0) {
			hours_string += "Saturday, ";
		}
		
		for (int j = 0; j < saturday_hours.size(); j++) {
			Hours event_hours = saturday_hours.get(j);
			Date opens = new Date(event_hours.getOpensEpoch());
			Date close = new Date(event_hours.getClosesEpoch());
			SimpleDateFormat df = new SimpleDateFormat("h:mm a");
			df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
			hours_string += df.format(opens) + " - " + df.format(close) + "\n";
		}
		
		if (sunday_hours.size() > 0) {
			hours_string += "Sunday, ";
		}
		
		for (int j = 0; j < sunday_hours.size(); j++) {
			Hours event_hours = sunday_hours.get(j);
			Date opens = new Date(event_hours.getOpensEpoch());
			Date close = new Date(event_hours.getClosesEpoch());
			SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
			df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
			hours_string += df.format(opens) + " - " + df.format(close) + "\n";
		}
		
		TextView event_hours = (TextView)findViewById(R.id.event_hours);
		event_hours.setText(hours_string);
		
		
		
		TextView event_short_desc = (TextView)findViewById(R.id.event_short_description);
		if (event.getShortDesc() != null && event.getShortDesc().length() > 0)
			event_short_desc.setText(event.getShortDesc());
		else
			event_short_desc.setVisibility(View.GONE);
		
		TextView event_long_desc = (TextView)findViewById(R.id.event_long_description);
		if (event.getLongDesc() != null && event.getLongDesc().length() > 0)
			event_long_desc.setText(event.getLongDesc());
		else
			event_long_desc.setVisibility(View.GONE);
		
		
		LinearLayout event_address_section = (LinearLayout)findViewById(R.id.event_address_section);
		TextView event_address = (TextView)findViewById(R.id.event_view_address);
		event_address.setText(studio.getAddress() + " " + event.getRoom());
		event_address_section.setOnTouchListener(this);
		
		
		ArrayList<Category> features = database.getFeatureCategories(event);
		String feature_string = "";
		for (int j = 0; j < features.size(); j++) {
			Category category = features.get(j);
			feature_string += category.getName() + ", ";
		}
		
		TextView event_features_header = (TextView)findViewById(R.id.event_view_features_header);
		TextView features_view = (TextView)findViewById(R.id.event_view_features);
		if (features.size() > 0) {
			event_features_header.setText("FEATURES");
			feature_string = feature_string.substring(0, feature_string.length() - 2);
			
			features_view.setText(feature_string);
		}
		else {
			event_features_header.setVisibility(View.GONE);
			features_view.setVisibility(View.GONE);
		}
		
		
		ArrayList<Category> media = database.getMediaCategories(event);
		String media_string = "";
		for (int j = 0; j < media.size(); j++) {
			Category category = media.get(j);
			media_string += category.getName() + ", ";
		}
		
		TextView media_view = (TextView)findViewById(R.id.event_view_media);
		TextView event_media_header = (TextView)findViewById(R.id.event_view_media_header);
		if (features.size() > 0) {
			event_media_header.setText("MEDIA");
			media_string = media_string.substring(0, media_string.length() - 2);
			media_view.setText(media_string);
		}
		else {
			media_view.setVisibility(View.GONE);
			event_media_header.setVisibility(View.GONE);
		}
		
	
		
		ArrayList<Artist> artists = database.getArtists(event);
		String artists_string = "";
		for (int j = 0; j < artists.size(); j++) {
			Artist artist = artists.get(j);
			artists_string += artist.getName() + " ";
		}
		
		TextView event_artists = (TextView)findViewById(R.id.event_view_artists);
		TextView event_artists_header = (TextView)findViewById(R.id.event_view_artists_header);
		if (artists.size() > 0) {
			event_artists_header.setText("ARTISTS");
			event_artists.setText(artists_string);
		}
		else {
			event_artists_header.setVisibility(View.GONE);
			event_artists.setVisibility(View.GONE);
		}
		
		
		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		Intent i = new Intent(this, StudioActivity.class);
		i.putExtra("studio_id", studio.getId());
		startActivity(i);
		return false;
	}
}

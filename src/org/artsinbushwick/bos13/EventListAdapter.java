package org.artsinbushwick.bos13;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<Event> implements StickyListHeadersAdapter, LocationListener {
	protected ArrayList<Event> friday_events = new ArrayList<Event>(0);
	protected ArrayList<Event> saturday_events = new ArrayList<Event>(0);
	protected ArrayList<Event> sunday_events = new ArrayList<Event>(0);
	private ArrayList<String> addresses;
	private ArrayList<Studio> studios;
	private LayoutInflater inflater;
	Database database;
	LocationManager location_manager;
	Location last_location = null;
	
	public EventListAdapter(Activity activity, Database db) {
		super(activity.getBaseContext(), android.R.layout.simple_list_item_1, new ArrayList<Event>(0));
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		database = db;
		location_manager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
		location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		last_location = location_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	void initialize(ArrayList<Event> friday, ArrayList<Event> saturday, ArrayList<Event> sunday) {
		friday_events = friday;
		saturday_events = saturday;
		sunday_events = sunday;
		
		addresses = new ArrayList<String>();
		studios = new ArrayList<Studio>();
		for (int j = 0; j < 3; j++) {
			ArrayList<Event> events = (j == 0)? friday_events: (j == 1)? saturday_events: sunday_events;
			for (int i = 0; i < events.size(); i++) {
				Event event = events.get(i);
				Studio studio = database.getStudio(event.getVenue());
				if (studio == null || studio.getAddress() == null)
					addresses.add("");
				else
					addresses.add(studio.getAddress() + " " + event.getRoom());
				
				studios.add(studio);
				ArrayList<Hours> hours = database.getEventHours(event);
				event.setHours(hours);
			}
		}
	}
	
	@Override
	public int getCount() {
		return friday_events.size() + saturday_events.size() + sunday_events.size();
	}
	
	@Override
	public Event getItem(int i) {
		if (i < friday_events.size())
			return friday_events.get(i);
		else if (i < friday_events.size() + saturday_events.size())
			return saturday_events.get(i - friday_events.size());
		else
			return sunday_events.get(i - friday_events.size() - saturday_events.size());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		EventHolder holder = null;
		
		if (row == null) {
			row = inflater.inflate(R.layout.event_row, parent, false);
			
			holder = new EventHolder();
			holder.event_name = (TextView)row.findViewById(R.id.event_name);
			holder.event_location = (TextView)row.findViewById(R.id.event_location);
			holder.event_time = (TextView)row.findViewById(R.id.event_time);
			holder.event_distance = (TextView)row.findViewById(R.id.event_distance);
			
			row.setTag(holder);
		}
		else {
			holder = (EventHolder)row.getTag();
		}
		
		Event event = getItem(position);
		Studio studio = studios.get(position);
		String distance_str = "Your location is unknown";
		if (last_location != null && studio != null && studio.getLat() != 0) {
			Location studio_location = new Location(last_location);
			studio_location.setLatitude(studio.getLat());
			studio_location.setLongitude(studio.getLon());
			double distance = last_location.distanceTo(studio_location) * 3.28084;
			if (distance > 528) {
				double miles = distance / 5280.0;
				BigDecimal bd = new BigDecimal(miles);
				bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
				distance_str = bd.doubleValue() + " miles away";
			}
			else {
				distance_str = (int)distance + " feet away";
			}
		}
		else if (studio == null || studio.getLat() == 0)
			distance_str = "";
		holder.event_name.setText(event.getName());
		holder.event_location.setText(addresses.get(position));
		holder.event_distance.setText(distance_str);
		
		long section = getHeaderId(position);
		if (section == 0) {
			for (int i = 0; i < event.getHours().size(); i++) {
				if (event.getHours().get(i).openFriday())
					holder.event_time.setText(event.getHours().get(i).toString());
			}
		}
		else if (section == 1) {
			for (int i = 0; i < event.getHours().size(); i++) {
				if (event.getHours().get(i).openSaturday())
					holder.event_time.setText(event.getHours().get(i).toString());
			}
		}
		else if (section == 2) {
			for (int i = 0; i < event.getHours().size(); i++) {
				if (event.getHours().get(i).openSunday())
					holder.event_time.setText(event.getHours().get(i).toString());
			}
		}
		
		return row;
	}
	
	static class EventHolder {
		TextView event_name;
		TextView event_location;
		TextView event_time;
		TextView event_distance;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		TextView row = (TextView)convertView;
		
		if (row == null) {
			row = (TextView)inflater.inflate(R.layout.section_header, parent, false);;
		}
		
		long section = getHeaderId(position);
		if (section == 0)
			row.setText("Friday, May 31st");
		else if (section == 1)
			row.setText("Saturday, June 1st");
		else
			row.setText("Sunday, June 2nd");
		
		return row;
	}

	@Override
	public long getHeaderId(int position) {
		if (position < friday_events.size())
			return 0;
		else if (position < friday_events.size() + saturday_events.size())
			return 1;
		else
			return 2;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		last_location = arg0;
	}

	@Override
	public void onProviderDisabled(String arg0) {
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		
	}

}

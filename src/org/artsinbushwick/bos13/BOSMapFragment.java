package org.artsinbushwick.bos13;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

public class BOSMapFragment extends SupportMapFragment {
	private List<Studio> studios;
	private List<Boolean> statuses;
	private List<String> titles;
	private List<Marker> markers;
	private MainActivity a;
	private float last_update;
	
	public BOSMapFragment(MainActivity activity) {
		super();
		a = activity;
		
		studios = a.database.getStudios();
		statuses = new ArrayList<Boolean>();
		titles = new ArrayList<String>();
		markers = new ArrayList<Marker>();
		for (int i = 0; i < studios.size(); i++) {
			boolean studio_active = false;
			ArrayList<Event> events = a.database.getEvents(studios.get(i));
			String event_str = "";
			if (events.size() > 1)
				event_str = events.size() + " Events";
			else if (events.size() == 1 && events.get(0).getName() != null)
				event_str = events.get(0).getName();
			
			for (int j = 0; j < events.size(); j++) {
				ArrayList<Hours> event_hours = a.database.getEventHours(events.get(j));
				for (int y = 0; y < event_hours.size(); y++) {
					Hours hours = event_hours.get(y);
					long time = System.currentTimeMillis();
					studio_active = (hours.getOpensEpoch() < time && hours.getClosesEpoch() > time);
					if (studio_active)
						break;
				}
				
				if (studio_active)
					break;
			}
			
			statuses.add(studio_active);
			titles.add(event_str);
		}
		
		last_update = System.currentTimeMillis();
	}
	
	public void updateActives(GoogleMap map) {
		for (int i = 0; i < studios.size(); i++) {
			boolean studio_active = false;
			ArrayList<Event> events = a.database.getEvents(studios.get(i));
			
			for (int j = 0; j < events.size(); j++) {
				ArrayList<Hours> event_hours = a.database.getEventHours(events.get(j));
				for (int y = 0; y < event_hours.size(); y++) {
					Hours hours = event_hours.get(y);
					long time = System.currentTimeMillis();
					studio_active = (hours.getOpensEpoch() < time && hours.getClosesEpoch() > time);
					if (studio_active)
						break;
				}
				
				if (studio_active)
					break;
			}
			
			if (studio_active != statuses.get(i)) {
				statuses.set(i, studio_active);
				
				Marker marker = markers.get(i);
				marker.remove();
				marker = addMarker(map, studios.get(i), titles.get(i), studio_active);
				markers.set(i, marker);
			}
		}
		
		last_update = System.currentTimeMillis();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		GoogleMap map = getMap();
		if (map != null) {
			getMap().setMyLocationEnabled(true);
			CameraUpdate position_map =
					CameraUpdateFactory.newLatLngZoom(new LatLng(40.697799,-73.919427), 13.5f);
			map.moveCamera(position_map);
			map.setMyLocationEnabled(true);
			map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {				
				public void onInfoWindowClick(Marker arg0) {
					Studio selected_studio = null;
					for (int i = 0; i < studios.size(); i++) {
						Studio studio = studios.get(i);
						if (arg0.getSnippet().equals(studio.getAddress())) {
							selected_studio = studio;
							break;
						}
					}
					
					if (selected_studio != null) {
						Intent i = new Intent(a, StudioActivity.class);
						i.putExtra("studio_id", selected_studio.getId());
						startActivity(i);
					}
				}
			});
		}
	}
	
	public Bitmap drawTextToBitmap(Context gContext, 
			  int gResId, 
			  String gText) {
			  Resources resources = gContext.getResources();
			  float scale = resources.getDisplayMetrics().density;
			  Bitmap bitmap = 
			      BitmapFactory.decodeResource(resources, gResId);
			 
			  
			  // resource bitmaps are imutable, 
			  // so we need to convert it to mutable one
			  Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
			 
			  Canvas canvas = new Canvas(bmp);
			  // new antialised Paint
			  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			  // text color - #3D3D3D
			  paint.setColor(Color.WHITE);
			  // text size in pixels
			  paint.setTextSize((int) (8 * scale));
			  // text shadow
			  paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
			 
			  // draw text to the Canvas center
			  Rect bounds = new Rect();
			  paint.getTextBounds(gText, 0, gText.length(), bounds);
			  int x = (bitmap.getWidth() - bounds.width())/2;
			  int y = (bitmap.getHeight() + bounds.height())/2;
			 
			  canvas.drawBitmap(bitmap, 0, 0, null);
			  canvas.drawText(gText, x, y, paint);
			 
			  return bmp;
			}
	
	public Marker addMarker(GoogleMap map, Studio studio, String title, boolean active) {
		int resource;
		if (active) {
			resource = (Character.isLetter(studio.getMap_identifier().charAt(0)))?
					R.drawable.map_diamond: R.drawable.map_circle;
		}
		else
			resource = (Character.isLetter(studio.getMap_identifier().charAt(0)))?
						R.drawable.map_diamond_inactive: R.drawable.map_circle_inactive;
		Bitmap marker_icon = drawTextToBitmap(a, resource, studio.getMap_identifier());
		BitmapDescriptor bmp = BitmapDescriptorFactory.fromBitmap(marker_icon);
		MarkerOptions options = new MarkerOptions();
		options.position(new LatLng(studio.getLat(), studio.getLon()));
		options.title(title);
		options.snippet(studio.getAddress());
		options.icon(bmp);
		options.anchor(1f,.1f);
		return map.addMarker(options);
	}
	
	public void onStart() {
		super.onStart();
		
		GoogleMap map = getMap();
		for (int i = 0; i < studios.size(); i++) {
			Studio studio = studios.get(i);
			if (map != null) {
				markers.add(addMarker(map, studio, titles.get(i), statuses.get(i)));
			}
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		GoogleMap map = getMap();
		System.out.println(map);
		if (map != null &&
		    System.currentTimeMillis() - last_update > 1000 * 60 * 20) {
			updateActives(map);
			System.out.println("RESUMED");
		}
	}
}

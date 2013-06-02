package org.artsinbushwick.bos13;

import java.util.ArrayList;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class StudioActivity extends Activity implements OnTouchListener, OnItemClickListener {
	Studio studio;
	Database database;
	ArrayList<Event> events;
	private LayoutInflater inflater;
	StudioEventListAdapter adapter;
	
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.sticky_header_list);
	
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		database = new Database(this);
		Intent intent = getIntent();
		int studio_id = intent.getIntExtra("studio_id", -1);
		studio = database.getStudio(studio_id);
		events = database.getEvents(studio);
		boolean studio_active = false;
		
		for (int j = 0; j < events.size(); j++) {
			ArrayList<Hours> event_hours = database.getEventHours(events.get(j));
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
		
		View header = inflater.inflate(R.layout.studio_header, null);
		StickyListHeadersListView listView = (StickyListHeadersListView)findViewById(R.id.sticky_list);
		listView.addHeaderView(header);
		header.setOnTouchListener(this);
		
		MapFragment fragment = (MapFragment)getFragmentManager().findFragmentById(R.id.studio_view_map);
		GoogleMap map = fragment.getMap();
		if (map != null) {
			CameraUpdate position_map =
					CameraUpdateFactory.newLatLngZoom(new LatLng(studio.getLat(), studio.getLon()), 15.0f);
			map.moveCamera(position_map);
		
			int resource;
			if (studio_active) {
				resource = (Character.isLetter(studio.getMap_identifier().charAt(0)))?
						R.drawable.map_diamond: R.drawable.map_circle;
			}
			else
				resource = (Character.isLetter(studio.getMap_identifier().charAt(0)))?
						R.drawable.map_diamond_inactive: R.drawable.map_circle_inactive;
			Bitmap marker_icon = drawTextToBitmap(this, resource, studio.getMap_identifier());
			BitmapDescriptor bmp = BitmapDescriptorFactory.fromBitmap(marker_icon);
			MarkerOptions options = new MarkerOptions();
			options.position(new LatLng(studio.getLat(), studio.getLon()));
			options.icon(bmp);
			options.anchor(1f,.1f);
			map.addMarker(options);
			map.getUiSettings().setAllGesturesEnabled(false);
			map.getUiSettings().setZoomControlsEnabled(false);
		}
		
		TextView address = (TextView)findViewById(R.id.studio_view_address);
		address.setText(studio.getAddress());
		
		adapter = new StudioEventListAdapter(this, database, studio);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		new AlertDialog.Builder(this)
			.setTitle("Google Maps Directions")
			.setMessage("Directions will open in Google Maps. Are you sure?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String url = "http://maps.google.com/maps?daddr=" + studio.getAddress() + ", Brooklyn, NY";
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);					
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
				
			}).show();
		return false;
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Event event = adapter.getItem(arg2 - 1);
		Intent i = new Intent(this, EventActivity.class);
		i.putExtra("event_id", event.getId());
		startActivity(i);		
	}
}

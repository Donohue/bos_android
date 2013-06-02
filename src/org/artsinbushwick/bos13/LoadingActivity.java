package org.artsinbushwick.bos13;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class LoadingActivity extends Activity {
	public SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
	public static final String JSON_CACHE_KEY = "echo";
	public static final String ep = "http://whispering-tundra-2412.herokuapp.com/";
	private Database database;
	private ProgressBar progressBar;
	private boolean studio_failed = false;
	private boolean artist_failed = false;
	private boolean category_failed = false;
	private boolean event_failed = false;
	private boolean sponsor_failed = false;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_view);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        progressBar = (ProgressBar)findViewById(R.id.loading_view_progress);
        progressBar.setIndeterminate(true);
        
        database = new Database(this);
        if (database.getArtists().size() > 0 &&
        	database.getEvents().size() > 0 &&
        	database.getStudios().size() > 0) {
        	loadMainActivity();
        }
        else {
        	spiceManager.execute(new StudioRequest(ep), JSON_CACHE_KEY, DurationInMillis.NEVER, new StudioListener());
        }
    }
    
    public void failedRequest() {
		new AlertDialog.Builder(this)
		.setTitle("Request Failure")
		.setMessage("We were unable to download some important BOS13 data. Please check your Internet connection and try again.")
		.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (studio_failed) {
		        	spiceManager.execute(new StudioRequest(ep), JSON_CACHE_KEY, DurationInMillis.NEVER, new StudioListener());
				}
				else if (artist_failed) {
					spiceManager.execute(new ArtistRequest(ep), JSON_CACHE_KEY, DurationInMillis.NEVER, new ArtistListener());
				}
				else if (category_failed) {
					spiceManager.execute(new CategoryRequest(ep), JSON_CACHE_KEY, DurationInMillis.NEVER, new CategoryListener());
				}
				else if (event_failed) {
					spiceManager.execute(new EventRequest(ep), JSON_CACHE_KEY, DurationInMillis.NEVER, new EventListener());
				}
				else if (sponsor_failed) {
					spiceManager.execute(new SponsorRequest(ep), JSON_CACHE_KEY, DurationInMillis.NEVER, new SponsorListener());
				}
				
				studio_failed = artist_failed = category_failed = event_failed = sponsor_failed = false;
			}
		}).show();
    }
    
    private void loadMainActivity() {
    	Intent i = new Intent(this, MainActivity.class);
    	startActivity(i);
    	finish();
    }
    
	@Override
	protected void onStart() {
		super.onStart();
		spiceManager.start(this);
	}
	
	@Override
	protected void onStop() {
		spiceManager.shouldStop();
		super.onStop();
	}
    
    private class StudioListener implements RequestListener<StudioList> {
    	@Override
		public void onRequestFailure(SpiceException arg0) {
    		studio_failed = true;
    		failedRequest();
		}

		@Override
		public void onRequestSuccess(StudioList arg0) {
			database.addStudios(arg0);
			spiceManager.execute(new ArtistRequest(ep), JSON_CACHE_KEY, DurationInMillis.NEVER, new ArtistListener());
		}

    }
    
    private class ArtistListener implements RequestListener<ArtistList> {

    	public void onRequestFailure(SpiceException arg0) {
    		artist_failed = true;
    		failedRequest();
		}

		public void onRequestSuccess(ArtistList arg0) {
			database.addArtists(arg0);
			spiceManager.execute(new CategoryRequest(ep), JSON_CACHE_KEY, DurationInMillis.NEVER, new CategoryListener());
		}
    }
    
    private class CategoryListener implements RequestListener<CategoryList> {
		public void onRequestFailure(SpiceException arg0) {
			category_failed = true;
    		failedRequest();
		}

		public void onRequestSuccess(CategoryList arg0) {
			database.addCategories(arg0);
			spiceManager.execute(new EventRequest(ep), JSON_CACHE_KEY, DurationInMillis.NEVER, new EventListener());
		}
    }
    
    private class EventListener implements RequestListener<EventList> {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			event_failed = true;
    		failedRequest();
		}

		@Override
		public void onRequestSuccess(EventList arg0) {
			database.addEvents(arg0);
			spiceManager.execute(new SponsorRequest(ep), JSON_CACHE_KEY, DurationInMillis.NEVER, new SponsorListener());
		}
    }
    
    private class SponsorListener implements RequestListener<SponsorList> {

		@Override
		public void onRequestFailure(SpiceException arg0) {
			sponsor_failed = true;
    		failedRequest();
		}

		@Override
		public void onRequestSuccess(SponsorList arg0) {
			database.addSponsors(arg0);
			loadMainActivity();
		}  	
    }
}

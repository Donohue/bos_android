package org.artsinbushwick.bos13;
import android.widget.TabHost;

public class TabChangeListener implements TabHost.OnTabChangeListener {
	private MainActivity activity;
	
	public TabChangeListener(MainActivity a) {
		super();
		activity = a;
	}
	
	@Override
	public void onTabChanged(String tabId) {
		android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
		CategoryFragment categoryFragment = (CategoryFragment) fm.findFragmentByTag("studios");
		EventFragment eventFragment = (EventFragment) fm.findFragmentByTag("events");
		BOSMapFragment mapFragment = (BOSMapFragment) fm.findFragmentByTag("map");
		SponsorFragment moreFragment = (SponsorFragment) fm.findFragmentByTag("sponsors");
		android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
		
		if (categoryFragment != null)
			ft.detach(categoryFragment);
		
		if (eventFragment != null)
			ft.detach(eventFragment);
		
		if (mapFragment != null)
			ft.detach(mapFragment);
		
		if (moreFragment != null)
			ft.detach(moreFragment);
		
		if(tabId.equalsIgnoreCase("studios")){
			
			if(categoryFragment == null){		
				/** Create StudioFragment and adding to fragmenttransaction */
				ft.add(R.id.realtabcontent, new CategoryFragment(new CategoryListAdapter(activity)), "studios");						
			}else{
				/** Bring to the front, if already exists in the fragmenttransaction */
				ft.attach(categoryFragment);						
			}
			
		}
		else if (tabId.equalsIgnoreCase("events")) {
			if(eventFragment == null){
				ft.add(R.id.realtabcontent, new EventFragment(), "events");
			}else{
				/** Bring to the front, if already exists in the fragmenttransaction */
				ft.attach(eventFragment);						
			}
		}
		else if (tabId.equalsIgnoreCase("map")) {
			if (mapFragment == null) {
				ft.add(R.id.realtabcontent, new BOSMapFragment(activity), "map");
			}
			else {
				ft.attach(mapFragment);
			}
		}
		else if (tabId.equalsIgnoreCase("sponsors")) {
			if (moreFragment == null) {
				ft.add(R.id.realtabcontent, new SponsorFragment(), "sponsors");
			}
			else {
				ft.attach(moreFragment);
			}
		}
		ft.commit();
	}

}

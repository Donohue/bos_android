package org.artsinbushwick.bos13;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TabHost;
import android.widget.TabWidget;

public class MainActivity extends FragmentActivity {
	private TabHost tHost;
	public Database database;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        database = new Database(this);
        tHost = (TabHost) findViewById(android.R.id.tabhost);
        tHost.setup();
		
		/** Setting tabchangelistener for the tab */
		tHost.setOnTabChangedListener(new TabChangeListener(this));
		
        TabHost.TabSpec tSpecStudios = tHost.newTabSpec("studios");
        tSpecStudios.setIndicator("STUDIOS");
        tSpecStudios.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecStudios);
        
        TabHost.TabSpec tSpecEvents = tHost.newTabSpec("events");
        tSpecEvents.setIndicator("EVENTS");
        tSpecEvents.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecEvents);
        
        TabHost.TabSpec tSpecMap = tHost.newTabSpec("map");
        tSpecMap.setIndicator("MAP");
        tSpecMap.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecMap);
        
        TabHost.TabSpec tSpecMore = tHost.newTabSpec("sponsors");
        tSpecMore.setIndicator("SPONSORS");
        tSpecMore.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecMore);
        
        initTabsAppearance(tHost.getTabWidget());
    }
    
    private void initTabsAppearance(TabWidget tabWidget) {
        // Change background
        for(int i=0; i < tabWidget.getChildCount(); i++)
            tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_indicator_ab_example);
    }
}

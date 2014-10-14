package com.csound.wizard;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.csound.wizard.fragment.BrowseFragment;
import com.csound.wizard.fragment.ChooserFragment;
import com.csound.wizard.fragment.CurrentPlaylistFragment;
import com.csound.wizard.fragment.RecentFragment;
import com.csound.wizard.fragment.SettingsFragment;
import com.csound.wizard.fragment.UiCsdFragment;
import com.csound.wizard.model.Model;
import com.csound.wizard.model.TrackRef;
import com.example.proglayout.R;

public class MainActivity extends ExitActivity {
	private static final String	
		WHERE_AM_I = "whereAmI",
		IS_PLAY = "isPlay";
	
	
	private App app; 
	private Menu menu;
	private boolean isPlay = false;	
	private int whereAmI = R.id.action_current_playlist;
	private FragmentManager fm;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		fm = getFragmentManager();
		app = (App) getApplication();		
		app.loadModel(this);		
		
		setContentView(R.layout.activity_main);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		View homeIcon = findViewById(android.R.id.home);
		((View) homeIcon.getParent()).setVisibility(View.GONE);		
		
		app.getModel().loadTestData(this);			
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {	
		super.onSaveInstanceState(outState);
		outState.putInt(WHERE_AM_I, whereAmI);
		outState.putBoolean(IS_PLAY, isPlay);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {	
		super.onRestoreInstanceState(savedInstanceState);
		whereAmI = savedInstanceState.getInt(WHERE_AM_I);
		isPlay = savedInstanceState.getBoolean(IS_PLAY);
	}
	
	@Override
	public void onExit() {
		app.stop();		
		app.getModel().save(this);
		app.saveCacheToDisk();
	}

		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);	    
	    this.menu = menu;
	    
	    restoreLocation();
	    return true;
	}
	
	private void restoreLocation() {			

		try {
		    if (whereAmI == R.id.action_current_track) {
		    	goToTrack(app.getModel().getCurrentTrackId());
		    } else {
		    	onOptionsItemSelected(menu.findItem(whereAmI));
		    }
		} catch (Exception e) {			
			goToCurrentPlaylist();
		}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items		
		whereAmI = item.getItemId();
	    switch (item.getItemId()) {
	        case R.id.action_play:
	        	onActionPlay();	
	        	whereAmI = R.id.action_current_track;
	            return true;
	        case R.id.action_left:	     
	        	onActionLeft();
	        	whereAmI = R.id.action_current_track;
	            return true;
	        case R.id.action_right:
	        	onActionRight();
	        	whereAmI = R.id.action_current_track;
	            return true;	            
	        case R.id.action_submenu:	        	
	            return true;
	        case R.id.action_current_playlist:	        	
	        	goToCurrentPlaylist();
	            return true;
	        case R.id.action_browse:	 
	        	startFragment(new BrowseFragment());
	            return true;
	        case R.id.action_recent:
	        	startFragment(new RecentFragment());
	            return true;
	        case R.id.action_clear_track:
	        	Utils.confirmActionDialog(this, 
	        			getResources().getString(R.string.confirm), 
	        			getResources().getString(R.string.confirm_clear_track), new Utils.ConfirmActionDialog() {
					
					@Override
					public void apply() {
						app.clearCurrentTrack();
			        	goToCurrentTrackWithoutCache();	        	
			        	Toast.makeText(MainActivity.this, R.string.done, Toast.LENGTH_SHORT).show();						
					}
				});        	
	        	
	        case R.id.action_more:	        	
	            return true;
	        case R.id.action_refresh:
	        	app.getModel().removeStubs();
	        	goToCurrentPlaylist();
	            return true; 
	        case R.id.action_add_tracks:
	        	startFragment(new ChooserFragment.Add(this));
	            return true;
	        case R.id.action_delete:	
	        	startFragment(new ChooserFragment.Delete(this));
	            return true; 
	        case R.id.action_settings:	 
	        	startFragment(new SettingsFragment());	        	
	            return true;	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	
	
	private void goToCurrentTrackWithoutCache() {
		Model m = app.getModel();
		int n = m.getCurrentTrackId();
		setTitle(m.getTrackName(n));
		m.setCurrentTrack(n);
		performPlayActionWithoutCache();		
	}

	private void performPlayActionWithoutCache() {
		performPlayAction(false);	
	}
	
	private void performPlayActionWithCache() {
		performPlayAction(true);	
	}

	private void switchPlayFlag() {
		isPlay = !isPlay;
		setPlayStopIcon();		
	}
	
	private void setPlayStopIcon() {
		int file;
		if (isPlay) {
			file = R.drawable.stop;									
		} else {
			file = R.drawable.play;						
		}
		menu.getItem(0).setIcon(file);	 
	}
	
	private void onActionPlay() {
		switchPlayFlag();		
		performPlayAction(true);				
	}
	
	
	private void removeHangingTabs() {
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.removeAllTabs();		
		getFragmentManager().popBackStack();
	}
	
	private void performPlayAction(boolean useCache) {	
		setPlayStopIcon();
		whereAmI = R.id.action_current_track;
		String trackPath = app.getModel().getCurrentTrack().getName();	
		
		if (app.isSameTrack(trackPath)) {
			if (isPlay) {
				app.play(trackPath);				
			} else {
				app.stop();				
			}			
		} else {			
			app.setupPlayerFor(trackPath);		
			setTitle(app.getModel().getCurrentTrackName());
			startFragment(UiCsdFragment.newInstance(trackPath, useCache, isPlay));
		}		
	}
	
	private void startFragment(Fragment a) {
		removeHangingTabs();
		getFragmentManager().beginTransaction().
		replace(R.id.container, a).commit();	
	}
	
	public void goToCurrentPlaylist() {	
		whereAmI = R.id.action_current_playlist;
		goToPlaylist(app.getModel().getCurrentPlaylistId());
	}
	
	public void goToPlaylist(String playlistName) {
		setTitle(playlistName);
		app.getModel().setCurrentPlaylist(playlistName);
		startFragment(new CurrentPlaylistFragment());		
	}
	
	public void goToPlaylist(int n) {
		Model m = app.getModel();
		setTitle(m.getPlaylistName(n));
		app.getModel().setCurrentPlaylist(n);
		startFragment(new CurrentPlaylistFragment());		
	}

	public void goToCurrentTrack(TrackRef trackRef) {
		goToTrack(app.getModel().getCurrentTrackId());
	}	
	
	public void goToTrack(TrackRef trackRef) {			
		app.getModel().setCurrentTrack(trackRef);
		performPlayActionWithCache();		
	}
	
	public void goToTrack(int n) {
		Model m = app.getModel();
		if (m.trackExists(n)) {
			setTitle(m.getTrackName(n));
			m.setCurrentTrack(n);
			performPlayActionWithCache();
		}
	}
	
	private void onActionLeft() {		
		app.getModel().prevTrack();
		performPlayActionWithCache();
	}
	
	private void onActionRight() {		
		app.getModel().nextTrack();
		performPlayActionWithCache();				
	}

	private void setTitle(String title) {
		getActionBar().setTitle("  " + title);		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub		
		if (app.getIsWatchingCurrentPlaylist() || getFragmentManager().getBackStackEntryCount() != 0) {
			super.onBackPressed();
		} else {
			goToCurrentPlaylist();
		}		
	}
}





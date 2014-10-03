package com.example.proglayout;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.proglayout.fragment.BrowseFragment;
import com.example.proglayout.fragment.ChooserFragment;
import com.example.proglayout.fragment.CurrentPlaylistFragment;
import com.example.proglayout.fragment.NoUiCsdFragment;
import com.example.proglayout.fragment.RecentFragment;
import com.example.proglayout.fragment.SettingsFragment;
import com.example.proglayout.fragment.UiCsdFragment;
import com.example.proglayout.model.Model;
import com.example.proglayout.model.TrackRef;
import com.example.proglayout.model.TrackState;

public class MainActivity extends ExitActivity {
	public static final String CSD_RUNNER = "csd runner";
	
	private App app; 
	private Menu menu;
	private boolean isPlay = false;	
	private int whereAmI = R.id.action_current_playlist;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
		outState.putInt("whereAmI", whereAmI);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {	
		super.onRestoreInstanceState(savedInstanceState);
		whereAmI = savedInstanceState.getInt("whereAmI");
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
		startFragment(new SettingsFragment());			
		/*
		try {
		    if (whereAmI == R.id.action_current_track) {
		    	goToTrack(app.getModel().getCurrentTrackId());
		    } else {
		    	onOptionsItemSelected(menu.findItem(whereAmI));
		    }
		} catch (Exception e) {			
			goToCurrentPlaylist();
		}
		*/
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
	}
	
	private void switchPlayStop() {
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
		switchPlayStop();		
	}
	
	
	private void removeHangingTabs() {
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.removeAllTabs();		
		getFragmentManager().popBackStack();
	}
	
	private void performPlayAction(boolean useCache) {	
		whereAmI = R.id.action_current_track;
		if (app.getModel().trackExists(app.getModel().getCurrentTrackId())) {
			removeHangingTabs();			
			setTitle(app.getModel().getCurrentTrackName());
			
			InputStream is;
			try {				
				String trackPath = app.getModel().getCurrentTrack().getName();
				is = FileUtils.openInputStream(new File(trackPath));
				String uiText = Utils.getUi(is);			
				
				if (uiText.isEmpty()) {
					startFragment(new NoUiCsdFragment());										
				} else {					
					TrackState state;
					
					if (useCache) {
						state = app.loadCurrentState(trackPath); 
					} else {
						state = TrackState.readDefaultState(trackPath);
					}							
							
					startFragment(new UiCsdFragment(trackPath, uiText, state));					
				}
				
				if (isPlay) {					
					app.play(new File(trackPath));
				} else {
					app.stop();								
				}
				is.close();
			} catch (Exception e) {				
				startFragment(new NoUiCsdFragment());				
			}	
						
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





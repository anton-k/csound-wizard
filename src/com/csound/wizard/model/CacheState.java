package com.csound.wizard.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.csound.wizard.App;

public class CacheState {
	private HashMap<String, TrackState> table;
	
	public CacheState() {
		table = new HashMap<String, TrackState>();
	}
	
	public void put(String key, TrackState value) {
		table.put(key, value);
	}
	
	public TrackState get(String key) {
		return table.get(key);
	}
	
	public boolean containsKey(String key) {
		return table.containsKey(key);
	}
	
	public void remove(String key) {
		table.remove(key);
	}
	
	public void saveToDisk() {
		for (Object x: table.entrySet()) {
			Entry<String, TrackState> entry = (Entry<String, TrackState>) x;
			saveCurrentStateToDisk(entry.getKey(), entry.getValue());			
		}
	}
	
	private void createIfNotPresentStateDirectory(String trackName) {
		File dir = new File(App.getCurrentStateDir(trackName));		
		if (!dir.exists()) {
			dir.mkdir();				
		}	
	}
	
	private void saveCurrentStateToDisk(String trackName, TrackState value) {
		createIfNotPresentStateDirectory(trackName);
				
		try {
			value.save(App.getCurrentStateFileName(trackName));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}

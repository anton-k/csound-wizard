package com.csound.wizard.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class Playlist implements Serializable {	
	private static final long serialVersionUID = 7155617513866250456L;
	
	private String name = ""; 
	private List<String> tracks = new ArrayList<String>();
	
	public Playlist() {		
	}
	
	public Playlist(String name_) {
		name = name_;
		tracks = new ArrayList<String>();
	}
	
	public Playlist(String name_, String[] arr) {
		name = name_;
		tracks = new ArrayList<String>();
		for (String a: arr) {
			tracks.add(a);
		}
	}
	
	public Playlist(String name_, List<String> tracks_) {
		name = name_;
		tracks = tracks_;
				
	}
	
	public List<String> getTracks() {
		return tracks;
	}
	
	public String getName() {
		return name;
	}
	
	public void addTrack(String trackName) {
		tracks.add(trackName);
	}
	
	public List<TrackRef> getTrackRefs() {
		List<TrackRef> res = new ArrayList<TrackRef>();
		for (String trackName: getTracks()) {
			res.add(new TrackRef(trackName, name));
		}
		return res;
	}
	
	public void removeTrackById(int location) {
		tracks.remove(location);
	}

	public List<String> getTrackNames() {
		List<String> res = new ArrayList<String>();
		for (String trackName: getTracks()) {
			res.add(getShortTrackName(trackName));
		}
		return res;
	}
	
	public String getShortTrackName(String name) {
		return FilenameUtils.getName(name);		
	}
	
}

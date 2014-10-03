package com.example.proglayout.model;

public class Track {
	private String mName;
	private TrackData mData;
	
	public Track(String name, TrackData data) {
		mName = name;
		mData = data;
	}
	
	public String getName() {
		return mName;
	}
	
	public TrackData getData() {
		return mData;
	}
	
}

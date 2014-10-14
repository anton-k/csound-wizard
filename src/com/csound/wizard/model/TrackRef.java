package com.csound.wizard.model;

import java.io.Serializable;

import org.apache.commons.io.FilenameUtils;

public class TrackRef implements Serializable {
	private static final long serialVersionUID = -6752420623385888418L;
	
	private String mTrackName, mPlaylistName;
	
	public TrackRef(String trackName, String playlistName) {
		mTrackName = trackName;
		mPlaylistName = playlistName;		
	}
	
	public String getShortTrackName() {
		return FilenameUtils.getName(mTrackName);		
	}
	
	public String getTrackName() {
		return mTrackName;		
	}
	
	public String getPlaylistName() {
		return mPlaylistName;
	}
	
	@Override
	public String toString() {
		return getShortTrackName() + " / " + getPlaylistName();
	}
	
	@Override
	public boolean equals(Object that) {
		if (!(that instanceof TrackRef)) {
			return false;
		}
		return 
				mTrackName.equals(((TrackRef) that).getTrackName()) &&
				mPlaylistName.equals(((TrackRef) that).getPlaylistName());		
	}
}

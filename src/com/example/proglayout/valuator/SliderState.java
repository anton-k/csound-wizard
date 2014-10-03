package com.example.proglayout.valuator;

import android.os.Parcel;
import android.os.Parcelable;

public class SliderState implements Parcelable {	
	private float st;
	
	public SliderState() {
		st = 0;		
	}
	
	public SliderState(float x) {		
		st = x;
	}
	
	public float get() {
		return st;
	}
	
	public void set(float x) {
		st = x;	
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p, int arg1) {
		p.writeFloat(st);		
	}
	
	public static final Parcelable.Creator<SliderState> CREATOR = new Parcelable.Creator<SliderState>() {
	    public SliderState createFromParcel(Parcel in) {	        
	       return new SliderState(in.readFloat());
	    }

	    public SliderState[] newArray(int size) {
	        return new SliderState[size];
	    }		
	};
}

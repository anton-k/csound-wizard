package com.csound.wizard.layout;

import java.util.AbstractMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;

import android.view.View;

import com.csound.wizard.layout.Units.Unit;
import com.csound.wizard.layout.param.Param;
import com.csound.wizard.layout.param.Types.Id;
import com.csound.wizard.layout.param.Types.InstrId;
import com.csound.wizard.model.TrackState;
import com.csound.wizard.view.ViewUtils;

public class UnitUtils {
	
	public static String getUnitId(Object obj) {
		if (Json.isString(obj)) {
			return (String) obj;
		} else if (Json.isArray(obj)) {
			JSONArray arr = (JSONArray) obj;
			if (arr.size() > 0) {
				Object x = arr.get(0);
				if (Json.isString(x)) {
					return (String) x;					
				}
			}
		}		
		return null;
	}
	
	public static float getState(String id, TrackState st, float defVal) {
		if (st.containsKey(id)) {
			double[] ds = st.get(id);
			return (float) ds[0];			
		} else {			
			return defVal;
		}
	}
	
	public static int getState(String id, TrackState st, int defVal) {
		if (st.containsKey(id)) {
			double[] ds = st.get(id);
			return (int) ds[0];			
		} else {			
			return defVal;
		}
	}
	
	public static Entry<Integer,Integer> getState(String id, TrackState st, Entry<Integer,Integer> defVal) {
		if (st.containsKey(id)) {
			double[] ds = st.get(id);
			return new AbstractMap.SimpleEntry<Integer,Integer>((int) Math.floor(ds[0]), (int) Math.floor(ds[1]));			
		} else {
			return defVal;
		}
	}
	
	public static Entry<Float,Float> getStateFloatPair(String id, TrackState st, Entry<Float,Float> defVal) {
		if (st.containsKey(id)) {
			double[] ds = st.get(id);
			return new AbstractMap.SimpleEntry<Float,Float>((float) ds[0], (float) ds[1]);			
		} else {
			return defVal;
		}
	}	
	
	public static Entry<Integer,Float> getStateIntegerFloatPair(String id, TrackState st, Entry<Integer,Float> defVal) {
		if (st.containsKey(id)) {
			double[] ds = st.get(id);
			return new AbstractMap.SimpleEntry<Integer,Float>((int) Math.floor(ds[0]), (float) ds[1]);			
		} else {
			return defVal;
		}
	}
	
	public static boolean[] getState(String id, int nx, int ny, TrackState st, boolean[] defVal) {
		if (st.containsKey(id)) {
			double[] ds = st.get(id);
			
			int n = nx * ny;
			
			if (ds.length == n) {
				boolean[] res = new boolean[n];
				for (int i = 0; i < n; i++) {
					res[i] = (Math.abs(ds[i]) > ViewUtils.EPS);									
				}
				return res;
			} 
		}
		return defVal;
	}
	
		
	public static double[] getUnitStateFloat(float x) {
		double[] ds = new double[1];
		ds[0] = x;
		return ds;		
	}
	
		
	public static double[] getUnitStateIntPair(int selectedX, int selectedY) {
		double[] ds = new double[2];
		ds[0] = selectedX;
		ds[1] = selectedY;
		return ds;
	}
	
	public static double[] getUnitStateFloatPair(float x, float y) {
		double[] ds = new double[2];
		ds[0] = x;
		ds[1] = y;
		return ds;
	}
	
	
	public static double[] getUnitStateIntegerFloatPair(int x, float y) {
		double[] ds = new double[2];
		ds[0] = x;
		ds[1] = y;
		return ds;
	}
	
	public static double[] getUnitStateArrayOfBooleans(boolean[] arr) {
		double[] ds = new double[arr.length];
		for (int i=0; i < arr.length; i++) {
			ds[i] = (arr[i]) ? 1 : 0;
		}
		return ds;
	}
		
	public interface WithId {
		public View apply(String id);
	}
	
	public interface WithInstrId {
		public View apply(Integer id);
	}
		
	public static View run(Unit unit, LayoutContext ctx, Object tagValue, WithId withId) {
		String id = Id.parse(tagValue);
		if (id == null) {
			return Layout.errorMalformedUnitId(ctx, unit.getTag() + ": " + tagValue.toString(), new Param());
		} else {
			return withId.apply(id);
		}								
	}
	
	public static View run(Unit unit, LayoutContext ctx, Object tagValue, WithInstrId withId) {
		Integer id = InstrId.parse(tagValue);
		if (id == null) {
			return Layout.errorMalformedUnitId(ctx, unit.getTag() + ": " + tagValue.toString(), new Param());
		} else {
			return withId.apply(id);
		}								
	}	

}

package com.csound.wizard.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.view.View;
import android.view.ViewGroup;

import com.csound.wizard.Const;
import com.csound.wizard.Utils;
import com.csound.wizard.layout.Json;
import com.csound.wizard.layout.UnitUtils;
import com.csound.wizard.layout.Units;
import com.csound.wizard.layout.Units.StatefulUnit;

public class TrackState {
	private HashMap<String,double[]> table;
	
	public TrackState(HashMap<String,double[]> t) {
		table = t;		
	}
	
	public TrackState() {
		table = new HashMap<String,double[]>();
	}
	
	// decoding/encoding from JSON
	
	@SuppressWarnings("unchecked")
	private String encode() {
		JSONObject obj =new JSONObject();
		for (Map.Entry<String,double[]> entry: table.entrySet()) {
			JSONArray vals = new JSONArray(); 
			for (double d: entry.getValue()) {
				vals.add(Double.valueOf(d));								
			}
			
			obj.put(entry.getKey(), vals);
		}		
				
		return obj.toJSONString();
	}
	
	private static TrackState decode(String text) {
		Object json = JSONValue.parse(text);
		HashMap<String,double[]> res = new HashMap<String,double[]>();
		
		if (Json.isObject(json)) {
			JSONObject obj = (JSONObject) json;		
		
			for (Object it: obj.entrySet()) {
				Entry<String, Object> entry = (Entry<String, Object>) it;
				if (Json.isArray(entry.getValue())) {
					JSONArray arr = (JSONArray) entry.getValue();
					double[] vals = new double[arr.size()];
					for (int i = 0; i < arr.size(); i++) {
						Object x = arr.get(i);
						if (Json.isNumber(x)) {
							vals[i] = Json.getFloat(x);
						} else {
							vals[i] = 0;
						}
					}
					
					res.put(entry.getKey(), vals);
				}
			}			
		}
		
		return new TrackState(res);
	}
	
	// saving loading to file
	
	public void save(String fileName) throws IOException {
		FileUtils.write(new File(fileName), encode());
	}
	
	public static TrackState load(String fileName) throws IOException {		
		String text = FileUtils.readFileToString(new File(fileName), "UTF-8");		
		return decode(text);
	}
	
	// Map methods
	
	public double[] get(String key) {
		return table.get(key);		
	}
	
	public boolean containsKey(String key) {
		return table.containsKey(key);
	}
	
	public void put(String key, double[] value) {
		table.put(key, value);
	}

	public static TrackState readDefaultState(String track) {
		Object json;
		try {			
			InputStream is = FileUtils.openInputStream(new File(track));			
			json = JSONValue.parse(Utils.getUi(is));
		} catch (IOException e) {
			return new TrackState();
		}
		
		TrackState res = new TrackState();
		
		res.getStateFromJson(json);		
		
		return res;
	}
	
	private void getStateFromJson(Object json) {
		for (String name: Units.arrayUnits) {
			if (Json.getArray(name, json) != null) {
				for (Object x: Json.getArray(name, json)) {
					this.getStateFromJson(x);
				}
				return;
			}
		}		
		
		for (String name: Units.singletonArrayUnits) {
			if (Json.getObject(name, json) != null) {
				this.getStateFromJson(Json.getObject(name, json));
				return;
			}
			
		}
		
		if (Json.isArray(json)) {
			for (Object x: (JSONArray) json) {
				this.getStateFromJson(x);				
			}
			return;				
		}
		
		for (String name: Units.objectArrayUnits) {
			if (Json.getArray(name, json) != null) {
				for (Object x: Json.getArray(name, json)) {
					if (Json.isObject(x)) {
						JSONObject y = (JSONObject) x;
						Set keys = y.keySet();
						if (keys.size() > 0) {
							String key = (String) keys.iterator().next();
							this.getStateFromJson(y.get(key));
						}
					}
				}
				return;
			}			
		}			
		
		if (Json.getJson(Const.INIT, json) != null) {
			if (Json.getJson(Const.SLIDER, json) != null) {
				Float initVal = Json.getFloat(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.SLIDER, json)), toTrackState(initVal));
				}
			} else if(Json.getJson(Const.TOGGLE, json) != null) {
				Boolean initVal = Json.getBoolean(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.TOGGLE, json)), toTrackState(initVal));
				}				
			} else if (Json.getJson(Const.KNOB, json) != null) {
				Float initVal = Json.getFloat(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.KNOB, json)), toTrackState(initVal));
				}
			} else if (Json.getJson(Const.SPINNER, json) != null) {
				Integer initVal = Json.getInteger(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.SPINNER, json)), toTrackState(initVal));
				}				
			} else if (Json.getJson(Const.INTS, json) != null) {
				Integer initVal = Json.getInteger(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.INTS, json)), toTrackState(initVal));
				}	
			} else if (Json.getJson(Const.NAMES, json) != null) {
				Integer initVal = Json.getInteger(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.NAMES, json)), toTrackState(initVal));
				}	
			} else if (Json.getJson(Const.HOR_RADIO, json) != null) {
				Integer initVal = Json.getInteger(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.HOR_RADIO, json)), toTrackState(initVal));
				}
			} else if (Json.getJson(Const.VER_RADIO, json) != null) {
				Integer initVal = Json.getInteger(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.VER_RADIO, json)), toTrackState(initVal));
				}												
			} else if (Json.getJson(Const.CHESS, json) != null) {
				Entry<Integer,Integer> initVal = Json.getIntegerPair(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.CHESS, json)), toTrackStateIntegerPair(initVal));
				}
			} else if (Json.getJson(Const.PLANE, json) != null) {
				Entry<Float,Float> initVal = Json.getFloatPair(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.PLANE, json)), toTrackStateFloatPair(initVal));
				}
			} else if (Json.getJson(Const.PLANE_X, json) != null) {
				Entry<Integer,Float> initVal = Json.getIntegerFloatPair(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.PLANE_X, json)), toTrackStateIntegerFloatPair(initVal));
				}
			} else if (Json.getJson(Const.PLANE_Y, json) != null) {
				Entry<Integer,Float> initVal = Json.getIntegerFloatPair(Const.INIT, json);
				if (initVal != null) {
					this.put(UnitUtils.getUnitId(Json.getJson(Const.PLANE_Y, json)), toTrackStateIntegerFloatPair(initVal));
				}
			} 			
		}		
		
	}
	
	private static double[] toTrackState(Float x) {
		double[] ds = new double[1];
		ds[0] = x;
		return ds;
	}
	
	private static double[] toTrackState(Integer x) {
		double[] ds = new double[1];
		ds[0] = x;
		return ds;
	}
	
	private static double[] toTrackState(Boolean x) {
		double[] ds = new double[1];
		ds[0] = x ? 1 : 0;
		return ds;
	}
	
	private static double[] toTrackStateIntegerPair(Entry<Integer,Integer> x) {
		double[] ds = new double[2];
		ds[0] = x.getKey();
		ds[1] = x.getValue();
		return ds;
	}
	
	private static double[] toTrackStateFloatPair(Entry<Float,Float> x) {
		double[] ds = new double[2];
		ds[0] = x.getKey();
		ds[1] = x.getValue();
		return ds;
	}
	
	private static double[] toTrackStateIntegerFloatPair(Entry<Integer,Float> x) {
		double[] ds = new double[2];
		ds[0] = x.getKey();
		ds[1] = x.getValue();
		return ds;
	}
	
	public void saveFromView(View rootView) {
		updateState(this, rootView);						
	}
	
	private static void updateState(TrackState state, View root) {	
		if (root instanceof StatefulUnit) {
			StatefulUnit unit = (StatefulUnit) root;
			state.put(unit.getUnitId(), unit.getUnitState());			
		} else if (root instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) root; 
			for (int i = 0; i < vg.getChildCount(); i++) {
				updateState(state, vg.getChildAt(i));
			}
		}
	}	
	
}

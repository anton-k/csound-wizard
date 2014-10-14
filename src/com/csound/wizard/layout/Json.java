package com.csound.wizard.layout;

import java.util.AbstractMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Json {
	// JSON utils
	//
	// string	 java.lang.String
	// number	 java.lang.Number
	// true|false	 java.lang.Boolean
	// null	 	 null
	// array	 java.util.List
	// object	 java.util.Map
	
	public static Boolean isString(Object obj) {
		return (obj instanceof String);		
	}
	
	public static Boolean isNumber(Object obj) {
		return (obj instanceof Long) || (obj instanceof Double);		
	}

	public static Boolean isBoolean(Object obj) {
		return (obj instanceof Boolean);		
	}
	
	public static Boolean isArray(Object obj) {
		return (obj instanceof JSONArray);		
	}
	
	public static Boolean isObject(Object obj) {
		return (obj instanceof JSONObject);		
	}
	
	public static Boolean isNull(Object obj) {
		return obj == null;
	}
	
	public static int getInt(Object obj) {
		return ((Number) obj).intValue();
	}
	
	public static float getFloat(Object obj) {
		return ((Number) obj).floatValue();
	}
	
	public static Boolean getBoolean(Object obj) {
		return (Boolean) obj;
	}
	
	
	public static Float getFloat(String key, Object obj) {
		if (Json.isObject(obj)) {
			JSONObject x = (JSONObject) obj;
			if (x.containsKey(key)) {
				Object maybeVal = x.get(key);
				if (Json.isNumber(maybeVal)) {
					return getFloat(maybeVal);
				}
			}
		}		
		return null;
	}
	
	public static String getString(String key, Object obj) {
		if (Json.isObject(obj)) {
			JSONObject x = (JSONObject) obj;
			if (x.containsKey(key)) {
				Object maybeVal = x.get(key);
				if (Json.isString(maybeVal)) {
					return (String) maybeVal;
				}
			}
		}		
		return null;
	}
	
	public static JSONObject getObject(String key, Object obj) {
		if (Json.isObject(obj)) {
			JSONObject x = (JSONObject) obj;
			if (x.containsKey(key)) {
				Object maybeVal = x.get(key);
				if (Json.isObject(maybeVal)) {
					return (JSONObject) maybeVal;
				}
			}
		}		
		return null;
	}
	
	public static JSONArray getArray(String key, Object obj) {
		if (Json.isObject(obj)) {
			JSONObject x = (JSONObject) obj;
			if (x.containsKey(key)) {
				Object maybeVal = x.get(key);
				if (Json.isArray(maybeVal)) {
					return (JSONArray) maybeVal;
				}
			}
		}		
		return null;
	}
	
	public static Object getJson(String key, Object obj) {
		if (Json.isObject(obj)) {
			JSONObject x = (JSONObject) obj;
			if (x.containsKey(key)) {
				return x.get(key);
			}
		}		
		return null;
	}

	public static Integer getInteger(String key, Object obj) {
		if (Json.isObject(obj)) {
			JSONObject x = (JSONObject) obj;
			if (x.containsKey(key)) {
				Object maybeVal = x.get(key);
				if (Json.isNumber(maybeVal)) {
					return getInt(maybeVal);
				}
			}
		}		
		return null;
	}

	public static Boolean getBoolean(String key, Object obj) {
		if (Json.isObject(obj)) {
			JSONObject x = (JSONObject) obj;
			if (x.containsKey(key)) {
				Object maybeVal = x.get(key);
				if (Json.isBoolean(maybeVal)) {
					return getBoolean(maybeVal);
				}
			}
		}		
		return null;
	}
	
	public static Entry<Float,Float> getFloatPair(String key, Object obj) {
		JSONArray arr = getArray(key, obj);		
		if (arr != null) {			
			if (arr.size() == 2 && isNumber(arr.get(0)) && isNumber(arr.get(1))) {
				return new AbstractMap.SimpleEntry<Float,Float>(getFloat(arr.get(0)), getFloat(arr.get(1)));				
			}
		}
		
		return null;
	}
	
	public static Entry<Integer,Float> getIntegerFloatPair(String key, Object obj) {
		JSONArray arr = getArray(key, obj);
		if (arr != null) {			
			if (arr.size() == 2 && isNumber(arr.get(0)) && isNumber(arr.get(1))) {
				return new AbstractMap.SimpleEntry<Integer,Float>(getInt(arr.get(0)), getFloat(arr.get(1)));				
			}
		}
		
		return null;
	}
	
	public static Entry<Integer,Integer> getIntegerPair(String key, Object obj) {
		JSONArray arr = getArray(key, obj);
		if (arr != null) {			
			if (arr.size() == 2 && isNumber(arr.get(0)) && isNumber(arr.get(1))) {
				return new AbstractMap.SimpleEntry<Integer,Integer>(getInt(arr.get(0)), getInt(arr.get(1)));				
			}
		}
		
		return null;
	}

	public static Object getFromArray(int i, Object obj) {
		try {
			return ((JSONArray) obj).get(i);			
		} catch (Exception e) {
			return null;
		}		
	}
	
}

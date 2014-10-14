package com.csound.wizard.layout.param;

import java.io.Serializable;
import java.util.List;

import org.json.simple.JSONObject;

import com.csound.wizard.layout.Json;
import com.csound.wizard.layout.param.Types.Names;

public class NamesParam implements Serializable {
	private static final long serialVersionUID = 6288008481648564877L;
	
	private Names mNames;	
	
	public NamesParam() {
		this(null);
	}
	
	public NamesParam(Names names) {
		mNames = names;
	}
	
	public Names getNames() { return mNames; }
	
	public static NamesParam parse(JSONObject obj) {
		return new NamesParam(Names.parse(Json.getJson("names", obj)));
	}	
	
	public static NamesParam merge(NamesParam a, NamesParam b) {
		if (a == null) {
			return b;				
		}
		
		if (b == null) {
			return a;
		}
					
		return new NamesParam((Names) Param.mergeObjects(a.mNames, b.mNames));
	}

	public List<String> getNameList() {
		// TODO Auto-generated method stub
		return mNames.getNames();
	}			
}
package com.example.proglayout.view.param;

import java.io.Serializable;

import com.example.proglayout.layout.Json;

public class TouchParam  implements Serializable {
	private static final long serialVersionUID = -2535582224519603831L;
	
	private Integer mTouchLimit;
	
	public TouchParam() {
		this(10);
	}
	
	public TouchParam(Integer touchLimit) {
		mTouchLimit = touchLimit;
	}
	
	public Integer getTocuhLimit() { return mTouchLimit; }
	
	public static TouchParam parse(Object obj) {
		return new TouchParam(Json.getInteger("touch-limit", obj));
	}
	
	public static TouchParam merge(TouchParam a, TouchParam b) {
		return new TouchParam((Integer) Param.mergeObjects(a.mTouchLimit, b.mTouchLimit));
	}
}

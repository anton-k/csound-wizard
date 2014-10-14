package com.csound.wizard.view;

public class Listener {
	

	public interface OnPress {
		public void press();
		public void release();	
	}
	
	public static OnPress defaultOnPress() {
		return new OnPress() {
			public void press() {}
			public void release() {}		
		};
	}
	
	public interface OnPressListener {
		public void setOnPressListener(OnPress listener);		
	}
	
	public interface OnSlide {
		public void slide(float value);
	}
	
	public static OnSlide defaultOnSlide() {
		return new OnSlide() {
			public void slide(float value) {}
		};		
	}
	
	public interface OnSlideListener {
		public void setOnSlideListener(OnSlide listener);
	}
	
	public interface OnSlide2 {
		public void slide(float x, float y);		
	}
	
	public static OnSlide2 defaultOnSlide2() {
		return new OnSlide2() {
			public void slide(float x, float y) {}
		};		
	}
	
	public interface OnSlide2Listener {
		public void setOnSlide2Listener(OnSlide2 listener);
	}
	
	public interface OnTap {
		public void tap(int key);		
	}
	
	public static OnTap defaultOnTap() {
		return new OnTap() {
			public void tap(int value) {}
		};		
	}
	
	public interface OnTapListener {
		public void setOnTapListener(OnTap listener); 
	}
	
	public interface OnTap2 {
		public void tap(int x, int y);		
	}
	
	public static OnTap2 defaultOnTap2() {
		return new OnTap2() {
			public void tap(int x, int y) {}
		};		
	}	
	
	public interface OnTap2Listener {
		public void setOnTap2Listener(OnTap2 listener); 
	}
	
	
	public interface OnKeyPress {
		public void on(int key);
		public void off(int key);
	}
	
	public interface OnKeyPress2 {
		public void on(int x, int y);
		public void off(int x, int y);
	}
	
	public static OnKeyPress2 defaultOnKeyPress2() {
		return new OnKeyPress2() {
			public void on(int x, int y) {}
			public void off(int x, int y) {}
		};			
	}
	
	public interface OnKeyPress2Listener {
		public void setOnKeyPress2Listener(OnKeyPress2 listener);
	}
	
	public interface OnKeyClick {
		public void on(int key);		
	}
	
	public interface OnDynamicKeyPress {
		public void on(float vol, int key);
		public void off(int key);
	}	
	
	public interface OnDynamicKeyClick {
		public void on(float vol, int key);		
	}
	
	public interface OnDrumPad {
		public void on(float vol, int x, int y);		
	}
	
	public interface OnRadio {
		public void radio(int val);
	}
	
	public interface OnKey {
		public void on(int key, float x);
		public void move(int key, float x);
		public void off(int key);				
	}
	
	public static OnKey defaultOnKey() {
		return new OnKey() {		
			public void on(int key, float x) {}	
			public void move(int key, float x) {}
			public void off(int key) {}
		};
	}
	
	public interface OnKeyListener {
		public void setOnKeyListener(OnKey listener);
	}
	
	public interface OnKey2 {
		public void on(int key, float x, float y);
		public void move(int key, float x, float y);
		public void off(int key);				
	}
	
	public static OnKey2 defaultOnKey2() {
		return new OnKey2() {		
			public void on(int key, float x, float y) {}	
			public void move(int key, float x, float y) {}
			public void off(int key) {}
		};
	}
	
	public interface OnKey2Listener {
		public void setOnKey2Listener(OnKey2 listener);
	}
	
	// --------------------------------------------------------
	// --------------------------------------------------------
	// Outputs
	
	public interface SetSlide {
		public void setSlide(float x);
	}
	
	public interface SetSlide2 {
		public void setSlide(float x, float y);		
	}	
}
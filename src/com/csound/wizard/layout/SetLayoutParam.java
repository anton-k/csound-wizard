package com.csound.wizard.layout;

import com.csound.wizard.layout.param.LayoutParam;
import com.csound.wizard.layout.param.Types.Sides;

import android.app.ActionBar.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SetLayoutParam {	
	
	public static enum LayoutParent {
		NONE, HOR, VER, TABLE, TABLE_ROW
	}

	public static View setParams(View view, LayoutParam param, LayoutParent layoutParent) {
		int defaultWidth = getDefaultWidth(layoutParent);
		int defaultHeight = getDefaultHeight(layoutParent);
		Sizes sizes = new Sizes(defaultWidth, defaultHeight);
		
		if (param == null) {			
			view.setLayoutParams(new ViewGroup.LayoutParams(defaultWidth, defaultHeight));			
		} else {
			setPadding(view, param.getPadding());
			
			switch (layoutParent) {
				case NONE:
					setNoneParentParams(view, param, sizes);
					break;
										
				case VER: case HOR:	
					setLinearLayoutParentParams(view, param, sizes);
					break;
					
				case TABLE:
					setTableParentParams(view, param, sizes);
					break;
					
				case TABLE_ROW:
					setTableRowParentParams(view, param, sizes);
					break;
					
				default:
					break;				
			}			
		}						
		return view;
	}
	
	
	private static void setTableRowParentParams(View view, LayoutParam param, Sizes sizes) {		
		setSizes(param, sizes);		
		TableRow.LayoutParams lp = new TableRow.LayoutParams(sizes.width, sizes.height);
		
		setMargin(lp, param);
		setGravity(lp, param);
		setWeight(lp, param);
		view.setLayoutParams(lp);
	}


	private static void setTableParentParams(View view, LayoutParam param, Sizes sizes) {		
		setSizes(param, sizes);		
		TableLayout.LayoutParams lp = new TableLayout.LayoutParams(sizes.width, sizes.height);
		
		setMargin(lp, param);
		setGravity(lp, param);
		setWeight(lp, param);
		view.setLayoutParams(lp);		
	}


	private static void setLinearLayoutParentParams(View view, LayoutParam param, Sizes sizes) {		
		setSizes(param, sizes);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams (sizes.width, sizes.height);
		
		setMargin(lp, param);
		setGravity(lp, param);
		setWeight(lp, param);
		view.setLayoutParams(lp);		
	}
	
	private static void setNoneParentParams(View view, LayoutParam param, Sizes sizes) {
		view.setLayoutParams(new ViewGroup.LayoutParams(sizes.width, sizes.height));		
	}
	
	private static void setPadding(View view, Sides padding) {
		if (padding != null) {
			Sides p = Sides.merge(padding, new Sides(0));
			view.setPadding(p.getLeft(), p.getTop(), p.getRight(), p.getBottom());
		}
	}
	
	private static void setMargin(ViewGroup.MarginLayoutParams layoutParams, LayoutParam param) {
		if (param != null && param.getMargin() != null) {			
			Sides p = Sides.merge(param.getMargin(), new Sides(0));
			layoutParams.setMargins(p.getLeft(), p.getTop(), p.getRight(), p.getBottom());			
		}
	}
	
	private static void setGravity(LinearLayout.LayoutParams layoutParams, LayoutParam param) {
		if (param != null && param.getGravity() != null) {
			layoutParams.gravity = param.getGravity();
		}			
	}
	
	private static void setWeight(LinearLayout.LayoutParams layoutParams, LayoutParam param) {
		if (param != null && param.getWeight() != null) {
			layoutParams.weight = param.getWeight();
		}			
	}

	
	private static void setSizes(LayoutParam param, Sizes sizes) {
		if (param != null) {
			if (param.getWidth() != null) {
				sizes.width = param.getWidth().getInteger();
			}
			if (param.getHeight() != null) {
				sizes.height = param.getHeight().getInteger();
			}
		} 
	}

	public static int getDefaultWidth(LayoutParent layoutParent) {	
		if (layoutParent.equals(LayoutParent.VER) || layoutParent.equals(LayoutParent.NONE)) {
			return LayoutParams.MATCH_PARENT;
		} else {
			return LayoutParams.WRAP_CONTENT;			
		}		
	}
	
	public static int getDefaultHeight(LayoutParent layoutParent) {
		if (layoutParent.equals(LayoutParent.HOR) || layoutParent.equals(LayoutParent.NONE)) {
			return LayoutParams.MATCH_PARENT;
		} else {
			return LayoutParams.WRAP_CONTENT;			
		}		
	}
	
	private static class Sizes {
		public Integer width, height;
		
		public Sizes(Integer w, Integer h) {
			width = w;
			height = h;
		}
	}
}


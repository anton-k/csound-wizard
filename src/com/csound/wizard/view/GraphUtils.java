package com.csound.wizard.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SparseArray;
import android.view.View;

import com.csound.wizard.layout.param.ColorParam;

public class GraphUtils {
	
	public interface OnToggleDraw {		
		public void active(Canvas c);
		public void passive(Canvas c);
	}
	
	public static class ToggleDraw {
		
		private OnToggleDraw mDraw;
		private View mContxet;
		private boolean mIsActive = false;
		
		public ToggleDraw(OnToggleDraw draw) {
			mDraw = draw;
		}
		
		void draw(Canvas c) {
			if (mIsActive) {
				mDraw.active(c);
			} else {
				mDraw.passive(c);
			}			
		}
		
		public void toggle() {
			mIsActive = !mIsActive;
			mContxet.invalidate();
		}
		
		public boolean isActive() {
			return mIsActive;
		}
	}
	
	
	public static class Circle {
		private float mx, my, mrad;
		
		public Circle() {
			mx = 0; my = 0; mrad = 0;			
		}
		
		public Circle(float x, float y, float rad) {
			mx = x; my = y; mrad = rad;			
		}
		
		public void setCircle(float x, float y, float rad) {
			mx = x; my = y; mrad = rad;			
		}
		
		public void draw(Canvas c, Paint p) {
			c.drawCircle(mx, my, mrad, p);
		}
		
		public void drawArc(Canvas c, float startAngle, float sweepAngle, Paint p) {
			int n = 25;
			float da = Math.abs(sweepAngle) / n;
			float k = Math.signum(sweepAngle);
			float angle = startAngle;
			float angle1;
					
			for (int i = 0; i < n; i++) {
				angle1 = angle + k * da;
				c.drawLine(px(angle), py(angle), px(angle1), py(angle1), p);
				angle = angle1;
			}				
		}
		
		public void drawRim(Canvas c, Paint p) {			
			p.setStyle(Style.STROKE);
			draw(c, p);									
		}
		
		public void drawFill(Canvas c, Paint p) {
			p.setStyle(Style.FILL);
			draw(c, p);									
		}

		
		public void drawDial(Canvas c, float angle, Paint p) {
			c.drawLine(mx, my, px(angle), py(angle), p);			
		}
		
		public void drawPie(Canvas c, float startAngle, float sweepAngle, Paint p) {
			int n = 25;
			float da = Math.abs(sweepAngle) / n;
			float k = Math.signum(sweepAngle);
			float angle = startAngle;
			float angle1;
			
			Path path = new Path();
			path.moveTo(mx, my);
			path.lineTo(px(angle), py(angle));
			for (int i = 0; i < n; i++) {
				angle1 = angle + k * da;
				path.lineTo(px(angle1), py(angle1));
				angle = angle1;
			}
			path.lineTo(mx, my);
			c.drawPath(path, p);
		}
		
		public void drawPlayButton(Canvas c, Paint p) {
			drawRightPolyFill(c, 0, 3, 0.7f, p);
		}

		public void drawStopButton(Canvas c, Paint p) {
			c.drawRect(new RectF(mx - mrad/2, my - mrad/2, mx + mrad/2, my + mrad/2), p);
		}
		
		public void drawRightPoly(Canvas c, float startAngle, int n, Paint paint) {
			float da = 1.0f / n;
			float angle = startAngle;	
						
			for (int i = 0; i < n; i++) {
				c.drawLine(
						px(angle), py(angle),
						px(angle + da), py(angle + da),
						paint);
				angle += da;
			}			
		}
		
		public void drawRightPolyFill(Canvas c, float startAngle, int n, Paint paint) {
			drawRightPolyFill(c, startAngle, n, 1.0f, paint);			
		}
		
		public void drawRightPolyFill(Canvas c, float startAngle, int n, float scale, Paint paint) {
			float da = 1.0f / n;
			float angle = startAngle;
			
			Path poly = new Path();
			poly.moveTo(px(angle, scale), py(angle, scale));
			for (int i = 0; i < n; i++) {
				poly.lineTo(px(angle + da, scale), py(angle + da, scale));						
				angle += da;
			}			
			c.drawPath(poly, paint);
		}

		
		private float px(float angle) {
			return mx + (float) (mrad * Math.cos(2.0 * Math.PI * angle));			
		}
		
		private float py(float angle) {
			return my + (float) (mrad * Math.sin(2.0 * Math.PI * angle));			
		}
		
		private float px(float angle, float scale) {
			return mx + (float) (scale * mrad * Math.cos(2.0 * Math.PI * angle));			
		}
		
		private float py(float angle, float scale) {
			return my + (float) (scale * mrad * Math.sin(2.0 * Math.PI * angle));			
		}

		public boolean contains(float x, float y) {			
			return Math.sqrt((mx - x) * (mx - x) + (my - y) * (my - y)) <= mrad;
		}
	}
	
	public static class Rect {		
		private static final float 
			cornerArc = 15,
			crossSize = 20;
		
		private float mw, mh, mx, my;
		
		private RectF mRectF; 
		
		public Rect() {
			this(0, 0, 0, 0);
		}
		
		public Rect(float x, float y, float w, float h) {
			mx = x; my = y; mw = w; mh = h;
			mRectF = new RectF(mx, my, mx + mw, my + mh);
		}
		
		public void setRect(float x, float y, float w, float h) {
			mx = x; my = y; mw = w; mh = h;			
			mRectF = new RectF(mx, my, mx + mw, my + mh);
		}
		
		public RectF getRectF() {
			return mRectF; 
		}
		
		public float getLeft() {
			return mx;
		}

		public float getTop() {
			return my;
		}
		public float getRight() {
			return mx + mw;
		}
		public float getBottom() {
			return my + mh;
		}

		
		public void setView(View v) {
			setRect(
				v.getPaddingLeft(), 
				v.getPaddingTop(), 
				v.getWidth() - v.getPaddingLeft() - v.getPaddingRight(), 
				v.getHeight() - v.getPaddingTop() - v.getPaddingBottom());
		}
		
		public boolean contains(float x, float y) {
			return mRectF.contains(x, y);			
		}
		
		public float relativeX(float x) {
			return (x - mx) / mw;
		}
		
		public float relativeY(float y) {
			return (y - my) / mh;
		}

		
		public void drawRounded(Canvas c, Paint p) {
			p.setStyle(Style.FILL);
			c.drawRoundRect(mRectF, cornerArc, cornerArc, p);			
		}
		
		public void draw(Canvas c, Paint p) {
			c.drawRect(mRectF, p);		
		}
		
		public void drawRim(Canvas c, Paint p) {			
			p.setStyle(Style.STROKE);
			draw(c, p);
		}
		
		public void drawRimRounded(Canvas c, Paint p) {			
			p.setStyle(Style.STROKE);
			c.drawRoundRect(mRectF, cornerArc, cornerArc, p);
		}
		
		public void drawCross(Canvas c, float x, float y, Paint p) {			
			float cx = mx + mw * x;
			float cy = my + mh * y;
			
			float offsetX = 0, offsetY = 0;
			
			if (cx < cornerArc || cx > mx + mw - cornerArc) {
				offsetY = cornerArc;
			}
			if (cy < cornerArc || cy > my + mh - cornerArc) {
				offsetX = cornerArc;
			}
			
			c.drawLine(cx, my + offsetY, cx, my + mh - offsetY, p);
			c.drawLine(mx + offsetX, cy, mx + mw - offsetX, cy, p);
			p.setStyle(Style.FILL);
			new Circle(cx, cy, crossSize).draw(c, p);
		}
		
		public void drawSlider(Canvas c, float x, Paint p) {
			p.setStyle(Style.FILL);
			if (mw > mh) {				
				c.drawRoundRect(new RectF(mx, my, mx + x * mw, my + mh), cornerArc, cornerArc, p);
			} else {
				c.drawRoundRect(new RectF(mx, my + x * mh, mx + mw, my + mh), cornerArc, cornerArc, p);				
			}
		}

		public void drawStripesX(Canvas c, int n, Paint paint) {
			float dx = mw / n;	
			float x = mx;
						
			paint.setStyle(Style.FILL);
			paint.setAlpha(60);			
			for (int i = 0; i < n; i = i + 2) {				 				
				c.drawRoundRect(new RectF(x, my, x + dx, my + mh), cornerArc, cornerArc, paint);
				x += 2 * dx;
			}			
		}
		
		public void drawSelectedStripesX(Canvas c, int n, int selected, Paint paint) {			
			paint.setStyle(Style.STROKE);
			paint.setAlpha(100);
			float dx = mw / n;
			float x = mx + selected * dx;
			c.drawRoundRect(new RectF(x, my, x + dx, my + mh), cornerArc, cornerArc, paint);			
		}	
		
		
		public void drawStripesY(Canvas c, int n, Paint paint) {
			float dy = mh / n;	
			float y = my;
			
			paint.setStyle(Style.FILL);
			paint.setAlpha(60);			
			for (int i = 0; i < n; i = i + 2) {				 				
				c.drawRoundRect(new RectF(mx, y, mx + mw, y + dy), cornerArc, cornerArc, paint);
				y += 2 * dy;
			}			
		}
		
		public void drawSelectedStripesY(Canvas c, int n, int selected, Paint paint) {			
			paint.setStyle(Style.STROKE);
			paint.setAlpha(100);
			float dy = mh / n;
			float y = my + selected * dy;
			c.drawRoundRect(new RectF(mx, y, mx + mw, y + dy), cornerArc, cornerArc, paint);			
		}
		
		public void drawChess(Canvas c, int nx, int ny, Paint paint) {			
			paint.setStyle(Style.FILL);
			paint.setAlpha(60);

			boolean isBlack = true;
			float x, y;
			float dx = mw / nx;
			float dy = mh / ny;
			
			for (int i = 0; i < nx; i++) {
				for (int j = 0; j < ny; j++) {
					if ((i + j) % 2 == 0) {
						x = mx + i * dx;
						y = my + j * dy;
						c.drawRoundRect(new RectF(x, y, x + dx, y + dy), cornerArc, cornerArc, paint);
					}										
					isBlack = !isBlack;					
				}
			}
		}
		
		public void drawCell(Canvas c, int nx, int ny, int selectedX, int selectedY, Paint paint) {			
			paint.setStyle(Style.STROKE);
			paint.setAlpha(100);			 
			float dx = mw / nx;
			float dy = mh / ny;
			float x  = mx + dx * selectedX;
			float y  = my + dy * selectedY;
			
			c.drawRoundRect(new RectF(x, y, x + dx, y + dy), cornerArc, cornerArc, paint);			
		}
		
		public void drawRound(Canvas c, float roundness, boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom, Paint paint) {
			c.drawRoundRect(mRectF, roundness, roundness, paint);
			if (!leftTop) {
				c.drawRect(new RectF(mx, my, mx + roundness, my + roundness), paint);												
			}
			if (!leftBottom) {
				c.drawRect(new RectF(mx, my + mh - roundness, mx + roundness, my + mh), paint);
				
			}
			if (!rightTop) {
				c.drawRect(new RectF(mx + mw - roundness, my, mx + mw, my + roundness), paint);
				
			}
			if (!rightBottom) {
				c.drawRect(new RectF(mx + mw - roundness, my + mh - roundness, mx + mw, my + mh), paint);				
			}			
		}
		
		public void drawRound(Canvas c, boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom, Paint paint) {
			drawRound(c, cornerArc, leftTop, rightTop, rightBottom, leftBottom, paint);
		}

		public void drawChessLines(Canvas c, int nx, int ny, Paint paint) {
			
			paint.setAlpha(150);
			float
				dx = mw / nx,
				dy = mh / ny,
				x  = mx,
				y  = my;  
			
			
			for (int i = 0; i < nx - 1; i++) {
				x += dx;				
				c.drawLine(x, my, x, my + mh, paint);				
			}
			
			for (int i = 0; i < ny - 1; i++) {
				y += dy;
				c.drawLine(mx, y, mx + mw, y, paint);
			}
			paint.setAlpha(255);			
		}

		public void drawSelectedTightSquare(Canvas c, int nx, int ny, int selectedX, int selectedY, Paint paint) {
			
			paint.setStyle(Style.FILL);
			float 
				dx = mw / nx,
				dy = mh / ny;

			Rect rect = new Rect(mx + selectedX * dx, my + selectedY * dy, dx, dy);
			
			if (isCorner(nx, ny, selectedX, selectedY)) {
				rect.drawRound(c, 
						isLeftTopCorner(nx, ny, selectedX, selectedY), 
						isRightTopCorner(nx, ny, selectedX, selectedY), 
						isRightBottomCorner(nx, ny, selectedX, selectedY), 
						isLeftBottomCorner(nx, ny, selectedX, selectedY), 
						paint);								
			} else {
				rect.draw(c, paint);				
			}
		}
		
		private static boolean isCorner(int nx, int ny,	int selectedX, int selectedY) {
			return (
				isLeftTopCorner(nx, ny, selectedX, selectedY) ||
				isRightTopCorner(nx, ny, selectedX, selectedY) ||
				isLeftBottomCorner(nx, ny, selectedX, selectedY) ||
				isRightBottomCorner(nx, ny, selectedX, selectedY) );					
		}
		
		private static boolean isLeftTopCorner(int nx, int ny,	int selectedX, int selectedY) {
			return isLeftCorner(nx, selectedX) && isTopCorner(ny, selectedY);
		}
		private static boolean isRightTopCorner(int nx, int ny,	int selectedX, int selectedY) {
			return isRightCorner(nx, selectedX) && isTopCorner(ny, selectedY);
		}
		private static boolean isLeftBottomCorner(int nx, int ny,	int selectedX, int selectedY) {
			return isLeftCorner(nx, selectedX) && isBottomCorner(ny, selectedY);
		}
		private static boolean isRightBottomCorner(int nx, int ny,	int selectedX, int selectedY) {
			return isRightCorner(nx, selectedX) && isBottomCorner(ny, selectedY);
		}		
		
		private static boolean isLeftCorner(int nx, int selectedX) {
			return selectedX == 0;
		}
		
		private static boolean isTopCorner(int ny, int selectedY) {
			return selectedY == 0;
		}
		
		private static boolean isRightCorner(int nx, int selectedX) {
			return selectedX == nx - 1;
		}
		
		private static boolean isBottomCorner(int ny, int selectedY) {
			return selectedY == ny - 1;
		}

		public void drawChessLabels(Canvas c, int nx, int ny, List<String> labels, Paint paint) {
			float x, y;
			float dx = mw / nx;
			float dy = mh / ny;
			
			LabelsDrawer labelsDrawer = new LabelsDrawer(c, paint, labels);
			
			for (int i = 0; i < ny; i++) {
				for (int j = 0; j < nx; j++) {
					x = mx + j * dx;
					y = my + i * dy;
					labelsDrawer.next(x + 0.5f * dx, y + 0.5f * dy);
				}
			}					
			
			labelsDrawer.close();			
		}

		public void drawStripesXText(Canvas c, int n, Paint paint,
				List<String> labels) {			
			float dx = mw / n;	
			float x = mx;
			float y = my + mh * 0.5f; 
			
			LabelsDrawer labelsDrawer = new LabelsDrawer(c, paint, labels);
							
			for (int i = 0; i < n; i++) {
				labelsDrawer.next(x + 0.5f * dx, y);
				x += dx;				
			}	
			
			labelsDrawer.close();
		}
		
		public void drawStripesYText(Canvas c, int n, Paint paint,
				List<String> labels) {			
			float dy = mh / n;	
			float y = my;
			float x = mx + mw * 0.5f; 
			
			LabelsDrawer labelsDrawer = new LabelsDrawer(c, paint, labels);
							
			for (int i = 0; i < n; i++) {
				labelsDrawer.next(x, y + 0.5f * dy);
				y += dy;
			}	
			
			labelsDrawer.close();
		}

		public boolean contains(PointF f) {			
			return contains(f.x, f.y);
		}

		public float getCenterX() {			
			return mx + mw * 0.5f;
		}	
		
		public float getCenterY() {			
			return my + mh * 0.5f;
		}
		
		public float getWidth() {			
			return mw;
		}
		
		public float getHeight() {			
			return mh;
		}

		public void drawText(Canvas c, String text, Paint paint) {
			paint.setStrokeWidth(1);
			paint.setStyle(Style.FILL_AND_STROKE);					
			c.drawText(text, getCenterX(), getCenterY() + 10, paint);			
			paint.setStrokeWidth(ViewUtils.getDefaultStrokeWidth());						
		}		
	}
		
	public static class TapBoard {
		private Rect mRect;		
		int mnx, mny;
				
		public TapBoard(Rect rect, int nx, int ny) {
			mRect = rect;
			mnx = nx; mny = ny;			
		}
		
		public Rect getRect() {
			return mRect;
		}
		
		public void setRect(float x, float y, float w, float h) {
			mRect.setRect(x, y, w, h);
		}
		
		public void draw(Canvas c, Paint p, int selectedX, int selectedY) {
			mRect.drawRim(c, p);
			mRect.drawChessLines(c, mnx, mny, p);			
			p.setStyle(Style.FILL);
			float dw = mRect.mw / mnx;
			float dh = mRect.mh / mny;
			float x = mRect.mx + selectedX * dw;
			float y = mRect.my + selectedY * dh;
			
			c.drawRect(x, y, x + dw, y + dh, p);			
		}
		
		
		public void draw(Canvas c, Paint p, SparseArray<PointF> presses, int size, ColorParam colors) {
			if (colors.getBkgColor() != Color.TRANSPARENT) {
				p.setColor(colors.getBkgColor());
				mRect.draw(c, p);			
			}					
			
			p.setColor(colors.getSndColor());
			mRect.drawRim(c, p);
			mRect.drawChessLines(c, mnx, mny, p);
			p.setStyle(Style.FILL);
			
			float dw = mRect.mw / mnx;
			float dh = mRect.mh / mny;			
			p.setColor(colors.getFstColor());			
				
			for (int i = 0; i < size; i++) {
				PointF point = presses.get(i);
				if (point != null) {
					int selectedX = ViewUtils.getCell(mnx, mRect.relativeX(point.x));
					int selectedY = ViewUtils.getCell(mny, mRect.relativeY(point.y));
					
					float x = mRect.mx + selectedX * dw;
					float y = mRect.my + selectedY * dh;
					
					c.drawRect(x, y, x + dw, y + dh, p);
				}
			}
		}

		public void draw(Canvas c, Paint p, boolean[] isOns,
				ColorParam colors) {
			
			p.setColor(colors.getSndColor());
			mRect.drawRim(c, p);
			mRect.drawChessLines(c, mnx, mny, p);
			p.setStyle(Style.FILL);
			
			float dw = mRect.mw / mnx;
			float dh = mRect.mh / mny;			
			p.setColor(colors.getFstColor());	
			
			int k = 0;
			int offset = 3;
			for (int i = 0; i < mnx; i++) {
				for (int j = 0; j < mny; j++) {
					if (k < isOns.length) {
						if (isOns[k]) {
							float x = mRect.mx + i * dw;
							float y = mRect.my + j * dh;
							
							
							c.drawRect(x+ offset, y + offset, x + dw - offset, y + dh - offset, p);							
						}
						k++;
					} else {
						return;
					}					
				}				
			}				
		}
	}	
	
	public static class Press {
		public int x, y, finger;
		
		public Press(int X, int Y, int Finger) {
			x = X;
			y = Y;
			finger = Finger;
		}
	}
	
	public static class TapPress {
		private List<Press> mPress  = new ArrayList<Press>();
		
		public TapPress() {			
		}
		
		public int getCount() {
			return mPress.size();
		}
		
		public void press(int x, int y, int finger) {
			mPress.add(new Press(x, y, finger));			
		}
		
		public void release(int finger) {
			Iterator<Press> it = mPress.iterator();
			while (it.hasNext()) {
				Press p = it.next();
				if (p.finger == finger) {
					it.remove();
				}
			}
		}
		
		public List<Press> getPressed() {
			return mPress;		
		}

		public Press getPressByFinger(int index) {
			for (Press p : mPress) {
				if (p.finger == index) {
					return p;
				}
			}
			
			return null;
		}		
	}
	
	public static class LabelsDrawer {
		private Canvas mc;
		private Paint mp;
		private int count = 0;
		private int size;
		private List<String> mLabels;
		
		public LabelsDrawer(Canvas c, Paint p, List<String> labels) {
			mc = c; 
			mp = p;
			mLabels = labels;
			size = labels.size();
			mp.setStrokeWidth(1);
			mp.setStyle(Style.FILL_AND_STROKE);
		}
		
		public void next(float x, float y) {
			if (count < size) {
				mc.drawText(mLabels.get(count), x, y, mp);				
				count++;
			}
		}
		
		public void close() {
			mp.setStrokeWidth(ViewUtils.getDefaultStrokeWidth());
		}
		
		public boolean hasNext() {
			return count < size;
		}
				
	}
}

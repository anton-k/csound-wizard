package com.example.proglayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proglayout.model.Model;

public class Utils {
	public static File csdFromFile(Context ctx, String fileText) {
		return createTempFile(ctx, fileText);
	}

	
	public static File csdFromResource(Context ctx, int resId) {
		return createTempFile(ctx, getResourceFileAsString(ctx, resId));		
	}
	
	public static File createTempFile(Context ctx, String csd) {
		File f = null;
		
		try {
			f = File.createTempFile("temp", ".csd", ctx.getCacheDir());
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(csd.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return f;
	}
	
	public static String getResourceFileAsString(Context ctx, int resId) {
		return readFile(ctx.getResources().openRawResource(resId));
	}
	
	public static String readFile(InputStream is) {
		StringBuilder str = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		String line;
		
		try {
			while ((line = r.readLine()) != null) {
				str.append(line).append("\n");
			}
		} catch (IOException ios) {

		}
		
		return str.toString();				
	}
	
	public static Model getModel(Fragment x) {
		return getModel(x.getActivity());		
	}
	
	public static Model getModel(Activity x) {
		return ((App) x.getApplication()).getModel();		
	}
	
	public static Settings getSettings(Fragment x) {
		return getSettings(x.getActivity());		
	}
	
	public static Settings getSettings(Activity x) {
		return ((App) x.getApplication()).getModel().getSettings();		
	}
	
	public interface EditInputDialog {
		public void apply(String text);
	}
	
	public interface ConfirmActionDialog {
		public void apply();		
	}
	
	public static void confirmActionDialog(Context ctx, String title, String message, final ConfirmActionDialog action) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		TextView tv = new TextView(ctx);		
		tv.setText(message);
		tv.setTextSize(22);
		int n = 30;
		tv.setPadding(n, n, n, n);
		builder.setView(tv);
		
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	action.apply();
		    }
		});
		
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});
		
		AlertDialog dialog = builder.show();			
	}
	
	public static void editInputDialog(Context ctx, final EditInputDialog action) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(R.string.action_new_playlist);

		// Set up the input
		final EditText input = new EditText(ctx);
		builder.setView(input);	
		
		// Set up the buttons
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	action.apply(input.getText().toString());
		    }
		});
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});
		
		
		AlertDialog dialog = builder.show();
		
		input.requestFocus();
		dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}
	
	public static String getUi(InputStream xml) {
		try {
			XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();
			xpp.setInput(xml, "UTF-8");
			skipToTag(xpp, "wizard");				
			return xpp.nextText();			
		} catch (Exception e) {
			return "";
		}
	}
	
	private static void skipToTag(XmlPullParser xpp, String tagName) throws Exception {
		int event = xpp.getEventType();
		while (event != XmlPullParser.END_DOCUMENT
				&& !tagName.equals(xpp.getName())) {
			event = xpp.next();
		}
	}
	
	public static String addSuffix(String x, String suffix) {
		return x + "." + suffix;
	}
	
}


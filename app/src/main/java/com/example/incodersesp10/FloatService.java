package com.example.incodersesp10;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FloatService extends Service
{
	public static void ShowFloat(Context context)
	{
		if (Instance == null)
		{
			Intent intent = new Intent(context, FloatService.class);
			context.startService(intent);
		}
	}
	
	public static void HideFloat()
	{
		// Intent intent = new Intent(context, FloatService.class);
		// context.stopService(intent);
		
		if (Instance != null)
		{
			Instance.Hide();
		}
	}
	
	//-----------------------
	
	@SuppressLint("StaticFieldLeak")
	private static FloatService Instance;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Instance = this;
		
		SetFloatView();	
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	public void Hide()
	{
		Instance = null;
		
		manager.removeView(mFloatLayout);
		this.stopSelf();
		this.onDestroy();
	}
	
	
	WindowManager manager;
	
	LinearLayout mFloatLayout;
	ImageView settings;
	ImageView search;
	
	LayoutParams params;

	AnimationF animation;
	
	private void SetFloatView()
	{
		// Generate floating window from layout file
		LayoutInflater inflater = LayoutInflater.from(getApplication());
		mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_view, null);
		
		// Add floating window to system service
		params = getParams();
		manager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
		manager.addView(mFloatLayout, params);
		
	
	}
	
	// Modify the position of the Player while dragging it
	
	
	
	
	private LayoutParams getParams()
	{
		LayoutParams wmParams = new LayoutParams();
		wmParams.type = LayoutParams.TYPE_SYSTEM_OVERLAY;   			// Set window type
		wmParams.format = PixelFormat.RGBA_8888;   			//Set the picture format, the effect is transparent background
		wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL; 	// Set the floating window to be out of focus (realize the operation of other visible windows except the floating window)
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;		// Adjust the docking position displayed in the floating window to the top on the left

// Using the upper left corner of the screen as the origin, set the initial values ​​of x and y (10, 10), relative to gravity		wmParams.x = 0;
		wmParams.y = 0;

		// Set floating window length and width data
		wmParams.width = 2280;
		wmParams.height = 1080;
		
		return wmParams;
	}
}


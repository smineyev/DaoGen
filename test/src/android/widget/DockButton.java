package android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

public class DockButton extends Button
{
	public static final String TAG = DockButton.class.getSimpleName();
	
	
	boolean isExpandAnimation = false;
	float 	expandValue = 0.0f;
	float 	expandSpeed = 0.2f;
	
	protected Drawable	background;
	
	public DockButton(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}	
	
	public DockButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public DockButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public boolean onPreDraw()
	{
		// TODO Auto-generated method stub
		
		return super.onPreDraw();
	}
	
	@Override
	public void setBackgroundDrawable(Drawable d)
	{
		Log.d(TAG, "setBackgroundDrawable "+d);
	
		background = d;
		// TODO Auto-generated method stub
		super.setBackgroundDrawable(d);
	}
	
	@Override
	public boolean performClick()
	{
		// TODO Auto-generated method stub
		
		Log.d(TAG, "performClick isExpandAnimation="+isExpandAnimation);
		isExpandAnimation = true;
		
		return super.performClick();
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO Auto-generated method stub
		int[] data = getDrawableState();
		
		Log.d(TAG, "onDraw "+data.length);
		
		// pressed state
		if (data.length == 3)
		{
			canvas.save();
			//canvas.translate(0.0f, -getHeight()/4);
			//canvas.scale(1.0f, 0.5f);
			//background.setLevel(2);
			//getBackground().
			
		}
		
		
		
		
		super.onDraw(canvas);
		canvas.restore();
	}
}

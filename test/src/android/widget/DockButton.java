package android.widget;


import com.test.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class DockButton extends Button
{
	public static final String TAG = DockButton.class.getSimpleName();
	
	
	protected NinePatchDrawable	badgeBack;
	
	protected Paint badgePaint = new Paint();
	protected Rect sourceBadgeRect = new Rect();
	protected Rect scaleBadgeRect = new Rect();
	protected Bitmap back;
	protected Paint paint = new Paint();
	protected Rect sourceRect = new Rect();
	protected Rect originalRect = new Rect();
	protected Rect parentRect = new Rect();
	protected Rect scaleRect = new Rect();
	protected boolean downState = false;

	
	protected int		badgeValue = 9;
	protected String	badgeValueAsString = "9";
	protected boolean	badgeVisibility = true;
	
	public DockButton(Context context)
	{
		super(context);
		init();
	}	
	
	public DockButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}
	public DockButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	
	@Override
	public boolean onPreDraw()
	{
		// TODO Auto-generated method stub
		
		return super.onPreDraw();
	}
	
	private void init()
	{

		back = BitmapFactory.decodeResource(getResources(),R.drawable.btn_dock_pressed);
		sourceRect.set(0,0, back.getWidth(), back.getHeight());
		
		badgeBack = (NinePatchDrawable) getResources().getDrawable(R.drawable.bg_badge_dock_button);
		sourceBadgeRect.set(0,0, badgeBack.getIntrinsicWidth(), badgeBack.getIntrinsicHeight());
		setBackgroundResource(R.color.Transparent);
		
		badgePaint.setColor(Color.BLACK);
		//badgePaint.setAntiAlias(true);

		badgePaint.setTextAlign(Align.CENTER);
		badgePaint.setTextSize(12.0f);
		badgePaint.setStyle(Paint.Style.FILL);
		badgePaint.setTypeface(Typeface.DEFAULT_BOLD);



	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		
		parentRect.set(left, top, right, bottom);
		originalRect.set(0,0,getWidth(), getHeight());

		
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				startExpandingAnimation();
				downState = true;
				break;
			case MotionEvent.ACTION_OUTSIDE:
			case MotionEvent.ACTION_UP:
				downState = false;
				DockButton.this.postInvalidate();
				break;
				
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		
		if (scale > 0.0f)
		{
			int offset = (int)(originalRect.height()*scale/2);
			scaleRect.set(0, originalRect.height()/2-offset, originalRect.right, originalRect.height()/2+offset);
			canvas.drawBitmap(back,  
				sourceRect, scaleRect, paint);
		} else if (downState)
		{
			canvas.drawBitmap(back,  
				sourceRect, originalRect, paint);			
		}
		super.onDraw(canvas);
		
		if (hasBadge())
		{

			int textWidth = (int)badgePaint.measureText(badgeValueAsString);
			// semi-transparent part = 8, design offset = 5
			int badgeWidth = 8+8+5+5+textWidth;
			int offset = originalRect.width()/2-badgeWidth/2;
			scaleBadgeRect.set(originalRect.width()/2-badgeWidth/2, 0, 
								originalRect.width()/2+badgeWidth/2,sourceBadgeRect.height());
			badgeBack.setBounds(scaleBadgeRect);
			badgeBack.draw(canvas);
			//canvas.drawRect(r, badgePaint)
			canvas.drawText(badgeValueAsString, originalRect.width()/2, badgePaint.getTextSize()+7, badgePaint);
			//canvas.drawLine(0,0, 20, 20, badgePaint);
			//canvas.drawRect(scaleBadgeRect, badgePaint);
		}
	}
	
	public void startExpandingAnimation() {
		if (aniThread == null || !aniThread.isAlive()) {
			aniThread = new AnimationThread(0,200,.1f,1.0f, new AccelerateInterpolator());
			aniThread.start();
		}
	}
	
	
	public void setBadge(int value)
	{
		badgeValue = value;
		badgeValueAsString = Integer.toString(badgeValue);
	}
	
	public boolean hasBadge()
	{
		return badgeValue >=0;
	}
	
	public boolean isBadgeVisible()
	{
		return badgeVisibility;
	}
	
	public void setBadgeVisiblity(boolean value)
	{
		badgeVisibility = value;
	}
	
	float scale = 0.0f;
	AnimationThread aniThread;

	private class AnimationThread extends Thread {

		long startTime;
		long duration;
		final long DELAY = 1000/30;  // 1/30th of a sec
		
		float fromValue;
		float toValue;
		
		boolean stop = false;
		
		public AnimationThread(long startTime, long duration, float fromValue, float toValue, android.view.animation.Interpolator interp) {
			this.startTime = startTime;
			this.duration = duration;
			this.interpolator = interp;
			this.fromValue = fromValue;
			this.toValue = toValue;
			scale = 0.0f;
		}
		
		android.view.animation.Interpolator interpolator;
		
		@Override
		public void run() {
			if (startTime == 0) {
				startTime = AnimationUtils.currentAnimationTimeMillis();
			}
			while (!stop) {
				long currentTime = AnimationUtils.currentAnimationTimeMillis();
				float normalTime = normalizedTime(currentTime);
				if (normalTime > 1) {  // we are done next time
					stop = true;					
				}
				float intTime = interpolator.getInterpolation(Math.min(1, normalTime));
				transform(intTime);
				try {
					// run at some frame rate
					Thread.sleep(Math.max(0, DELAY - (AnimationUtils.currentAnimationTimeMillis()-currentTime)));
				} catch (InterruptedException e) {
					stop = true;
					return;
				}
			}
			scale = 0.0f;
			DockButton.this.postInvalidate();
		}

		// ranges from 0 to 1, moves depending on how interpolator and duration are set
		private void transform(float intTime) {
			float t = fromValue + intTime*(toValue-fromValue);
			scale = t;
			DockButton.this.postInvalidate();
			
		}

		private float normalizedTime(long currentTime) {
			long elapseTime = currentTime-startTime;				
			return (float)elapseTime/(float)duration;
		}
		
	}

}

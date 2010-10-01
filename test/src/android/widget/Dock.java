package android.widget;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

public class Dock extends LinearLayout
{
	protected Rect originalRect = new Rect();
	protected Rect originalRect2 = new Rect();
	protected Paint badgePaint = new Paint();
	
	protected Vector<DockButton>	buttons = new Vector<DockButton>();

	public Dock(Context context)
	{
		super(context);
		init();
	}

	public Dock(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}
	
	void init()
	{
		badgePaint.setColor(Color.BLACK);
		//badgePaint.setAntiAlias(true);

		badgePaint.setTextAlign(Align.CENTER);
		badgePaint.setTextSize(12.0f);
		badgePaint.setStyle(Paint.Style.FILL);
		badgePaint.setTypeface(Typeface.DEFAULT_BOLD);
		
	}
	
	@Override
	protected void onFinishInflate()
	{
		// TODO Auto-generated method stub
		super.onFinishInflate();
		
		for (int i =0; i<getChildCount(); i++)
		{
			View v = getChildAt(i);
			if (v instanceof DockButton)
			{
				buttons.add((DockButton)v);
			}
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		
		//originalRect2.set(0,0,getWidth(), getHeight());
		//originalRect.set(getPaddingLeft(),getPaddingTop(),getWidth()-getPaddingLeft()-getPaddingRight(), getHeight()-getPaddingTop()-getPaddingBottom());

	}
	
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime)
	{
		// TODO Auto-generated method stub
		boolean value =  super.drawChild(canvas, child, drawingTime);
		if (child instanceof DockButton)
		{
			DockButton button = (DockButton)child;
			// TODO hardcode
			if (button.hasBadge() && button.isBadgeVisible())
			{
				button.drawBadge(canvas, button.getLeft(), button.getTop()-6);
				
			}
		}
		return value;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		/*for (DockButton button : buttons)
		{
			if (button.hasBadge())
			{
				button.drawBadge(canvas, button.getLeft(), button.getTop());
			}
		}*/
		
		//badgePaint.setColor(0xFF00FFFF);
		//canvas.drawRect(originalRect2, badgePaint);				
		//badgePaint.setColor(0xFF0000FF);
		//canvas.drawRect(originalRect, badgePaint);	
		
	}
	
	public void setButtonText(int idx, String text)
	{
		buttons.get(idx).setText(text);
	}

	public void setButtonOnClickListener(int idx, OnClickListener listener)
	{
		buttons.get(idx).setOnClickListener(listener);
	}	
	
	public DockButton getButton(int idx)
	{
		return buttons.get(idx);
	}
	
	public void setBadgeValue(int idx, int value)
	{
		buttons.get(idx).setBadge(value);
	}

	public void setBadgeVisibility(int idx, boolean value)
	{
		buttons.get(idx).setBadgeVisiblity(value);
	}
}

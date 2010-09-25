package android.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyGridView extends GridView {

	private static final String TAG = "MyGridView";



	public MyGridView(Context context) {
		this(context, null, 0);
	}

	public MyGridView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);
		return false;
	}

	
	
//	public boolean scrollYByIfNeeded(int dy) {
//		if (dy < 0 &&  mFirstPosition > 0 || 
//				dy >0 && (mFirstPosition + getChildCount()) < mItemCount) {
//			scrollBy(0, dy);
//			return true;
//		} else {
//			Log.w(TAG, "top or bottom reached");
//			return false;
//		}
//	}

}

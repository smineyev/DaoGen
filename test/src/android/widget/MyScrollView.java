package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import com.test.R;

public class MyScrollView extends ScrollView {

	/**
     * Sentinel value for no current active pointer.
     * Used by {@link #activePointerId}.
     */
    private static final int INVALID_POINTER = -1;
	
	private MyGridView myGridView2;

	
    private int touchSlop;
    private int minimumVelocity;
    private int maximumVelocity;	
    
    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    private int activePointerId = INVALID_POINTER;

	
    /**
     * Position of the last motion event.
     */
    private float lastMotionY;
	
	/**
     * True if the user is currently dragging this ScrollView around. This is
     * not the same as 'is being flinged', which can be checked by
     * mScroller.isFinished() (flinging begins when the user lifts his finger).
     */
    private boolean isBeingDragged = false;

    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker velocityTracker;	

	public MyScrollView(Context context) {
		this(context, null, 0);
		// TODO Auto-generated constructor stub
	}
	public MyScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}
	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initScrollView(context);
	}
	
    private void initScrollView(Context context) {
        setFocusable(true);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setWillNotDraw(false);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop();
        minimumVelocity = configuration.getScaledMinimumFlingVelocity();
        maximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }
	
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		myGridView2 = (MyGridView) findViewById(R.id.gridview2);
	}
    
    
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		myGridView2.scrollBy(0, 10);

		int[] p = new int[2];
		this.getLocationInWindow(p);  
		int scrollViewAbsoluteY = p[1];

		View appTextView = findViewById(R.id.appsTxtView);

		
		
		p = new int[2];
		appTextView.getLocationInWindow(p);  
		int headerY = p[1];  
		
//		Log.w("app", ""+scrollViewAbsoluteY+" ? "+p[1]);
		 

		if (headerY < scrollViewAbsoluteY) {
			scrollBy(0, headerY-scrollViewAbsoluteY);
		}
		
		
//		return onTouchEventImpl(ev, headerY <= scrollViewAbsoluteY);
		return  headerY > scrollViewAbsoluteY ? super.onTouchEvent(ev) : onTouchEventImpl(ev, true);
			
//			myGridView2.scrollBy(0, 10);

	}
	
	
	public boolean onTouchEventImpl(MotionEvent ev, boolean scrollGrid) {

		if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getEdgeFlags() != 0) {
		    // Don't handle edge touches immediately -- they may actually belong to one of our
		    // descendants.
		    return false;
		}
		
		if (velocityTracker == null) {
		    velocityTracker = VelocityTracker.obtain();
		}
		velocityTracker.addMovement(ev);
		
		final int action = ev.getAction();
		
		switch (action & MotionEvent.ACTION_MASK) {
		    case MotionEvent.ACTION_DOWN: {
		        final float y = ev.getY();
		        if (!(isBeingDragged = inChild((int) ev.getX(), (int) y))) {
		            return false;
		        }
		        
		        /*
		         * If being flinged and user touches, stop the fling. isFinished
		         * will be false if being flinged.
		         */
		//	                if (!mScroller.isFinished()) {
		//	                    mScroller.abortAnimation();
		//	                }
		
		        // Remember where the motion event started
		        lastMotionY = y;
		        activePointerId = ev.getPointerId(0);
		        break;
		    }
		    case MotionEvent.ACTION_MOVE:
//		        if (mIsBeingDragged) {
		            // Scroll to follow the motion event
		    			activePointerId = ev.getPointerId(0);
		                final int activePointerIndex = ev.findPointerIndex(activePointerId);
		                final float y = ev.getY(activePointerIndex);
		                final int deltaY = (int) (lastMotionY - y);
		                lastMotionY = y;
		
		                if (Math.abs(deltaY) < touchSlop) {
		                	break;
		                }
		                
		                if (scrollGrid) {
Log.w("scrollGrid", "scrollGrid");
//		                	myGridView2.scrollYByIfNeeded(deltaY);
//							myGridView2.dispatchTouchEvent(ev);
		                } else  {
Log.w("scroll", "scroll");
		                	scrollBy(0, deltaY);
		                }	
//		            }
		            break;
	        case MotionEvent.ACTION_UP: 
	            if (isBeingDragged) {
	                velocityTracker.computeCurrentVelocity(1000, maximumVelocity);
	                int initialVelocity = (int) velocityTracker.getYVelocity(activePointerId);
	
	                if (getChildCount() > 0 && Math.abs(initialVelocity) > minimumVelocity) {
Log.w("fling", "fling");	             
						if (scrollGrid) {
							myGridView2.dispatchTouchEvent(ev);
						} else {
							fling(-initialVelocity);
						}
	                }
	
	                activePointerId = INVALID_POINTER;
	                isBeingDragged = false;
	
	                if (velocityTracker != null) {
	                    velocityTracker.recycle();
	                    velocityTracker = null;
	                }
	            }
	            break;
	        case MotionEvent.ACTION_CANCEL:
	            if (isBeingDragged && getChildCount() > 0) {
	                activePointerId = INVALID_POINTER;
	                isBeingDragged = false;
	                if (velocityTracker != null) {
	                    velocityTracker.recycle();
	                    velocityTracker = null;
	                }
	            }
	            break;
	        case MotionEvent.ACTION_POINTER_UP:
Log.w("onSecondaryPointerUp", "onSecondaryPointerUp");	        	
	            onSecondaryPointerUp(ev);
	            break;
	    }
	    return true;
	}	
		
	private void onSecondaryPointerUp(MotionEvent ev) {
	    final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
	            MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	    final int pointerId = ev.getPointerId(pointerIndex);
	    if (pointerId == activePointerId) {
	        // This was our active pointer going up. Choose a new
	    // active pointer and adjust accordingly.
	    // TODO: Make this decision more intelligent.
	        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	        lastMotionY = ev.getY(newPointerIndex);
	        activePointerId = ev.getPointerId(newPointerIndex);
	        if (velocityTracker != null) {
	            velocityTracker.clear();
	        }
	    }
	}


    private boolean inChild(int x, int y) {
        if (getChildCount() > 0) {
            final int scrollY = getScrollY();
            final View child = getChildAt(0);
            return !(y < child.getTop() - scrollY
                    || y >= child.getBottom() - scrollY
                    || x < child.getLeft()
                    || x >= child.getRight());
        }
        return false;
    }

}

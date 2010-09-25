package com.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;

public class Test extends Activity {
	
	public static View firstView = null;
	public static View lastView = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollview);
        scrollView.setFillViewport(true);
        
        GridView gridview1 = (GridView) findViewById(R.id.gridview1);
        gridview1.setAdapter(new ImageAdapter1(this));
        
        GridView gridview2 = (GridView) findViewById(R.id.gridview2);
        gridview2.setAdapter(new ImageAdapter2(this));
    }
    
    public class ImageAdapter1 extends BaseAdapter {
        private Context mContext;

        public ImageAdapter1(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

    }
    
    public class ImageAdapter2 extends BaseAdapter {
    	private Context mContext;
    	
    	public ImageAdapter2(Context c) {
    		mContext = c;
    	}
    	
    	public int getCount() {
    		return mThumbIds.length;
    	}
    	
    	public Object getItem(int position) {
    		return null;
    	}
    	
    	public long getItemId(int position) {
    		return 0;
    	}
    	
    	// create a new ImageView for each item referenced by the Adapter
    	public View getView(int position, View convertView, ViewGroup parent) {
    		ImageView imageView;
    		if (convertView == null) {  // if it's not recycled, initialize some attributes
    			imageView = new ImageView(mContext);
    			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
    			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    			imageView.setPadding(8, 8, 8, 8);
    		} else {
    			imageView = (ImageView) convertView;
    		}
    		
    		if (position == 0) {
    			firstView = imageView;
    		} else {
    			if (position == getCount() - 1) {
    				lastView = imageView;
    			} else {
	    			if (firstView == convertView) {
	    				firstView = null;
	    			} else if (lastView == convertView) {
	    				lastView = null;
	    			}

    			}
    		} 
    		
    		imageView.setImageResource(mThumbIds[position]);
    		return imageView;
    	}
    	
    }
    
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.icon1, R.drawable.icon2,
            R.drawable.icon3, R.drawable.icon4,
            R.drawable.icon5, R.drawable.icon6,
            R.drawable.icon7, R.drawable.icon8,
            R.drawable.icon1, R.drawable.icon2,
            R.drawable.icon3, R.drawable.icon4,
            R.drawable.icon5, R.drawable.icon6,
            R.drawable.icon7, R.drawable.icon8                
    };
    
}
package com.example.growcanvas;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class GrowDrawActivity extends Activity {

	private FrameLayout fram;
	private ShapeView shape;
	private LinearLayout menu;
	private SeekBar seekSpread;
	private SeekBar seekLifeTime;
	private SeekBar seekShapeSize;
	
	private static int shapeMode;


	private static final int WC = FrameLayout.LayoutParams.WRAP_CONTENT;
	private static FrameLayout.LayoutParams param;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grow_draw);
		fram = (FrameLayout) findViewById(R.id.framLayout);
		fram.setOnTouchListener(onTouchView);

		menu = (LinearLayout) findViewById(R.id.menulayout);

		seekSpread = (SeekBar)findViewById(R.id.seekBar_spread);
		seekLifeTime = (SeekBar)findViewById(R.id.seekBar_lifeTime);
		seekShapeSize = (SeekBar)findViewById(R.id.seekBar_sizes);
		seekSpread.setOnSeekBarChangeListener(seekBarChange);
		seekLifeTime.setOnSeekBarChangeListener(seekBarChange);
		seekShapeSize.setOnSeekBarChangeListener(seekBarChange);
	}

	private OnTouchListener onTouchView = new OnTouchListener() {
		private float x;
		private float y;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			x = event.getX();
			y = event.getY();

			if (fram.getChildCount()>0) {
				fram.removeAllViews();

			}

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				addShapeInLayout(x, y);
				break;
			case MotionEvent.ACTION_UP:
//		    	  System.gc();
				break;
			}
			return true;
		}
	};

	private OnSeekBarChangeListener seekBarChange = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
	    	
			if (seekSpread.isPressed()) {
				ShapeView.setTransRandom(seekSpread.getProgress());
				shape.invalidate();
			}
			else if (seekLifeTime.isPressed()) {
				ShapeView.setAnimDuration(seekLifeTime.getProgress());
			}
			else if (seekShapeSize.isPressed()) {
				ShapeView.setCircleR(seekShapeSize.getProgress());
			}
			
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			
		    if(seekBar.getProgress() == 0) {
		    	seekBar.setProgress(1);

		    }
		}
	};

	private void addShapeInLayout(float x, float y) {
		shape = new ShapeView(GrowDrawActivity.this);
		shape.invalidate();
		param = new FrameLayout.LayoutParams(WC, WC);
		param.setMargins((int)x - ShapeView.getCircleR()*3, (int)y - ShapeView.getCircleR()*3, 0, 0);
		fram.addView(shape, param);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.grow_draw, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			int visiblity = 0;
			if (menu.getVisibility() != View.VISIBLE) {
				visiblity = View.VISIBLE;
			}
			else {
				visiblity = View.GONE;
			}
			menu.setVisibility(visiblity);
		}
		if (id == R.id.action_add_circle) {
			shapeMode =ShapeView.MODE_CIRCLE;
		}
		else if (id == R.id.action_add_star) {
			shapeMode =ShapeView.MODE_STAR;
		}
		else if (id == R.id.action_add_rect) {
			shapeMode =ShapeView.MODE_RECT;
		}
		else if (id == R.id.action_add_path_star) {
			shapeMode =ShapeView.MODE_PATH_STAR;
		}
		else if (id == R.id.action_add_triangle) {
			shapeMode =ShapeView.MODE_TRIANGLE;
		}
		ShapeView.setShapeMode(shapeMode);
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void finalize() throws Throwable {
	    try {
	        super.finalize();
	      } finally {
	    	  shape = null;
	      }
	}




}

package com.example.methodsamples.surfaceview;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SimpleSurfaceViewActivity extends Activity {

    public class ShapeGridChart {

	private final int sx;
	private final int sy;
	private final int sr;
	private final int at;
	private int cx;
	private int cy;
	private int cr;
	private long st;
	private int count;

	public ShapeGridChart(int startX, int startY, int startR,
		long startTime, int animTime, int count) {
	    this.sx = startX;
	    this.sy = startY;
	    this.sr = startR;
	    this.cr = sr;
	    this.st = startTime;
	    this.at = animTime;
	    this.count = count;
	}
    }

    public class SimpleSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private Thread thread;
	private SurfaceHolder holder;
	private ArrayList<ShapeGridChart> sList = new ArrayList<ShapeGridChart>();

	public SimpleSurfaceView(Context context) {
	    super(context);
	    holder = getHolder();
	    holder.addCallback(this);
	    holder.setFixedSize(getWidth(), getHeight());
	}

	private Paint setPaint(Paint paint) {
	    paint.setStyle(Style.STROKE);
	    paint.setStrokeWidth(15);
	    paint.setStrokeCap(Cap.ROUND);
	    paint.setStrokeJoin(Join.ROUND);
	    return paint;
	}

	private Paint setInPaint(Paint paint) {
	    paint.setColor(Color.WHITE);
	    paint.setStrokeWidth(5);
	    paint.setMaskFilter(null);
	    return paint;
	}

	private Paint setGrowPaint(Paint paint) {
	    paint.setColor(Color.GREEN);
	    paint.setStrokeWidth(20);
	    paint.setMaskFilter(new BlurMaskFilter(10, Blur.NORMAL));
	    return paint;
	}

	@Override
	public void run() {
	    Canvas canvas;
	    Paint paint = new Paint();
	    paint = setPaint(paint);
	    while (thread != null) {
		canvas = holder.lockCanvas();
		if (canvas == null) {
		    continue;
		}
		canvas.drawColor(0, PorterDuff.Mode.CLEAR);
		for (int i = 0; i < sList.size(); i++) {
		    paint = setGrowPaint(paint);
		    canvas.drawCircle(sList.get(i).sx, sList.get(i).sy,sList.get(i).cr, paint);
		    paint = setInPaint(paint);
		    canvas.drawCircle(sList.get(i).sx, sList.get(i).sy,sList.get(i).cr, paint);

		    if (System.currentTimeMillis() - sList.get(i).st >= (long) sList.get(i).at / sList.get(i).sr) {
			sList.get(i).st = System.currentTimeMillis();
			sList.get(i).cr -= 5;
			    if (sList.get(i).cr <= 0) {
				sList.get(i).count -= 1;
				sList.get(i).cr = sList.get(i).sr;
			    }
		    }
		    if (sList.get(i).count == 0) {
			sList.remove(i);
		    }
		}
		holder.unlockCanvasAndPost(canvas);
	    }
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
	    thread = new Thread(this);
	    thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	    thread = null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    switch (event.getAction()) {
	    case MotionEvent.ACTION_DOWN:
		break;
	    case MotionEvent.ACTION_MOVE:
		sList.add(new ShapeGridChart((int) event.getX(), (int) event.getY(), (int) (event.getSize() * 500), System.currentTimeMillis(), 1000, 3));
		break;
	    case MotionEvent.ACTION_UP:
		System.gc();
		break;
	    }
	    return true;
	}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	SimpleSurfaceView view = new SimpleSurfaceView(this);
	setContentView(view);

    }
}

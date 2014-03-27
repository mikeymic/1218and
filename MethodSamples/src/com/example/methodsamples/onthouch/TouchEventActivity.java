package com.example.methodsamples.onthouch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class TouchEventActivity extends Activity {
    
    private MyView view;

    public class MyView extends View {

	private float x;
	private float y;
	private Paint paint;
	
	public void setX(float x) {
	    this.x = x;
	}

	public void setY(float y) {
	    this.y = y;
	}

	public MyView(Context context) {
	    super(context);
	    paint = new Paint();
	    paint.setColor(Color.BLUE);
	    paint.setStyle(Style.STROKE);
	    paint.setStrokeJoin(Join.ROUND);
	    paint.setStrokeCap(Cap.ROUND);
	    paint.setStrokeWidth(50);
	}

	@Override
	protected void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
	    canvas.drawPoint(x, y-100, paint);
	}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	view = new MyView(this);
	setContentView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	switch (event.getAction()) {
	case MotionEvent.ACTION_DOWN:
	    break;
	case MotionEvent.ACTION_MOVE:
	    view.setX(event.getX());
	    view.setY(event.getY());
	    view.invalidate();
	    break;
	case MotionEvent.ACTION_UP:
	    break;
	}
	
	return true;
    }
    

}

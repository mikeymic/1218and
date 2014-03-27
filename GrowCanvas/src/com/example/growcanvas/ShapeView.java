package com.example.growcanvas;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class ShapeView extends View {

	private static int shapeSize = 50;
	private static int blankSize = 30;
	private static int circleXY = shapeSize*2 + blankSize;
	

	private int [] growColor = {
			Color.BLUE,
			Color.CYAN,
			Color.GREEN,
			Color.MAGENTA,
			Color.RED,
			Color.YELLOW,
	};
	private int colorIndex;
	private static Paint paint;

	private static ScaleAnimation scale;
	private static TranslateAnimation trans;
	private static AnimationSet anim;

	private static int transRandom = 1;

	private static int bmpPath = R.drawable.light002;
	private static Bitmap bitmap;
	private static Bitmap bitmapGrow;
	private Bitmap bitmapAll;

	private static int shapeMode;
	public static final int MODE_CIRCLE = 0;
	public static final int MODE_STAR = 1;
	public static final int MODE_RECT = 2;
	public static final int MODE_PATH_STAR = 3;
	public static final int MODE_TRIANGLE = 4;

	private static Random rnd = new Random();
	private static int randomX;
	private static int randomY;

	private static int repeatCount = 4;
	private static long animDuration = 1500;
	private RectF rect;

	public static long getAnimDuration() {
		return animDuration;
	}
	
	public ShapeView(Context context) {
		super(context);
		colorIndex = rnd.nextInt(growColor.length);
		setPaint();
		setAnimation(createAnimation());
	}
	


	
	public static int getCircleR() {
		return shapeSize;
	}

	
	public static int getTransRandom() {
		return transRandom;
	}
	
	public static void setShapeMode(int shapeMode) {
		ShapeView.shapeMode = shapeMode;
	}
	
	public void setColor(int color) {
		this.colorIndex = color;
	}
	
	public static void setAnimDuration(long animDuraion) {
		ShapeView.animDuration = animDuraion;
	}

	public static void setCircleR(int circleR) {
		ShapeView.shapeSize = circleR;
	}

	public static void setTransRandom(int transRandom) {
		ShapeView.transRandom = transRandom;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (shapeMode == MODE_CIRCLE) {
			createCircle(canvas);
		}
		else if(shapeMode == MODE_STAR) {
			createStar(canvas);
		}
		else if(shapeMode == MODE_RECT) {
			createRect(canvas);
		}
		else if(shapeMode == MODE_PATH_STAR) {
			createPathStar(canvas);
		}
		else if(shapeMode == MODE_TRIANGLE) {
			createTriangle(canvas);
		}
	}

	private void setPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeCap(Cap.BUTT);
		paint.setStrokeJoin(Join.ROUND);
	}
	private void setDrawPaint() {
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(5);
		paint.setStyle(Style.STROKE);
		paint.setMaskFilter(new BlurMaskFilter(1, Blur.SOLID));
	}
	private void setDrawGrowPaint() {
		paint.setColor(growColor[colorIndex]);
		paint.setStrokeWidth(20);
		paint.setStyle(Style.STROKE);
		paint.setMaskFilter(new BlurMaskFilter(15, Blur.NORMAL));
	}
	private void setStarPaint() {
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(7);
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setMaskFilter(new BlurMaskFilter(2, Blur.NORMAL));
	}
	private void setStarGrowPaint() {
		paint.setColor(growColor[colorIndex]);
		paint.setStrokeWidth(25);
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setMaskFilter(new BlurMaskFilter(10, Blur.NORMAL));
	}
	private void setStarCirclePaint() {
		paint.setStrokeWidth(20);
		paint.setMaskFilter(new BlurMaskFilter(5, Blur.NORMAL));
	}

	private void createCircle(Canvas canvas) {
		setDrawGrowPaint();
		canvas.drawCircle(circleXY, circleXY, shapeSize, paint);
		setDrawPaint();
		canvas.drawCircle(circleXY, circleXY, shapeSize, paint);
	}
	private void createStar(Canvas canvas) {
	    int[] offsetXY = { 0, 0 };
		Resources res = this.getContext().getResources();
		bitmap = BitmapFactory.decodeResource(res, bmpPath);
		setStarGrowPaint();
		bitmapGrow = bitmap.extractAlpha(paint, offsetXY);
		bitmapAll = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas cv = new Canvas(bitmapAll);
	    cv.drawBitmap(bitmapGrow, offsetXY[0], offsetXY[1], paint);
	    cv.drawBitmap(bitmap, 0, 0, null);
		canvas.drawBitmap(bitmapAll, 0, 0, null);
		bitmap = null;
		bitmapGrow = null;
		bitmapAll = null;
	    cv = null;
	}
	private void createRect(Canvas canvas){
		rect = new RectF(shapeSize + blankSize, shapeSize + blankSize, shapeSize*3, shapeSize*3);
		setDrawGrowPaint();
		canvas.drawRect(rect, paint);
		setDrawPaint();
		canvas.drawRect(rect, paint);
	}
	private void createPathStar(Canvas canvas) {
		Path path = new Path();
		Path circle = new Path();

		rect = new RectF(shapeSize+blankSize, blankSize, shapeSize+blankSize+1, shapeSize*2+blankSize);
		path.addRoundRect(rect, 180,0, Path.Direction.CW);
		rect = new RectF(blankSize, shapeSize+blankSize, shapeSize*2+blankSize, shapeSize+blankSize+1);
		path.addRoundRect(rect, 180,0, Path.Direction.CW);

		circle.addCircle(shapeSize+blankSize, shapeSize+blankSize, shapeSize/4, Path.Direction.CW);
		path.addPath(circle);

		setStarGrowPaint();
		canvas.drawPath(path, paint);

		setStarPaint();
		canvas.drawPath(path, paint);
		setStarCirclePaint();
		canvas.drawPath(circle, paint);
	}
	private void createTriangle(Canvas canvas) {
		Path path = new Path();
		path.moveTo(shapeSize*2+blankSize, shapeSize + blankSize);
		path.lineTo(shapeSize-8+blankSize,shapeSize*2 + blankSize*2);
		path.lineTo(shapeSize*3+8+blankSize, shapeSize*2 + blankSize*2);
		path.close();
		setDrawGrowPaint();
		canvas.drawPath(path,paint);
		setDrawPaint();
		canvas.drawPath(path,paint);
	}

	private Animation createAnimation() {
		randomX = rnd.nextInt(transRandom) - (transRandom/2);
		randomY = rnd.nextInt(transRandom) - (transRandom/2);
		trans = new TranslateAnimation(0, randomX, 0,randomY);
		trans.setRepeatCount(repeatCount);
		trans.setDuration(animDuration);
		scale = new ScaleAnimation(1, 0, 1, 0, circleXY, circleXY);
		scale.setRepeatCount(repeatCount);
		scale.setDuration(animDuration);
		anim = new AnimationSet(true);
		anim.addAnimation(scale);
		anim.addAnimation(trans);
		return anim;
	}

	@Override
	public void setAnimation(Animation animation) {
		super.setAnimation(animation);
	}





}

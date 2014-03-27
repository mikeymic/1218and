package com.example.methodsamples.glsurfaceview.camera;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class CameraGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private float red;
    private float green;
    private float blue;
    private Cube cube = new Cube();

    private float slideX;
    private float slideY;
    

    public CameraGLSurfaceView(Context context) {
	super(context);
	setRenderer(this);
	
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	gl.glEnable(GL10.GL_DEPTH_TEST);
	gl.glDepthFunc(GL10.GL_LEQUAL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
	gl.glViewport(0, 0, width, height);
	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glLoadIdentity();
	GLU.gluPerspective(gl, 45f, (float) width / height, 1f, 50f);
	
    }

    @Override
    public void onDrawFrame(GL10 gl) {
	setColor(200, 160, 100);
	gl.glClearColor(red, green, blue, 1.0f);
	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glLoadIdentity();
	gl.glTranslatef(0, 0, -3f);
	setupCamera(gl);
	cube.draw(gl);
    }

    public void setColor(int r, int g, int b) {
	int max = 255;
	red = (float) r / max;
	green = (float) g / max;
	blue = (float) b / max;
    }
    
    /*とりあえず、onDrawFrame内で記述すること
     * どれか一つでも欠けると変になる（細かいことは後で調べる） 
     */
    public  void setupCamera(GL10 gl) {
	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glLoadIdentity();
	GLU.gluPerspective(gl, 45f, (float) getWidth() / getHeight(), 1f, 50f);
	GLU.gluLookAt(gl, -slideX, slideY, 5.0f, -slideX, slideY, 0, 0, 1.0f, 0);
	/*eyeとcenterを同じ値にするとスライド移動する
	*/
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	
    }

    
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
	
	
	switch (event.getAction()) {
	case MotionEvent.ACTION_DOWN:
	    break;
	case MotionEvent.ACTION_MOVE:
	    float x = event.getRawX();
	    float y = event.getRawY();
	    slideX = (x - getWidth()/2) /100;
	    slideY = (y - getHeight()/2) /100;
	    /*
	     * 横幅800と仮定した場合、真ん中は400
	     * getXが400の時0の値になればいいから、画面の横幅を/2すればいい（暫定）
	     */
	    
	    break;
	case MotionEvent.ACTION_UP:
	    break;

	default:
	    break;
	}
	
	
	return true;
    }
    
    

}

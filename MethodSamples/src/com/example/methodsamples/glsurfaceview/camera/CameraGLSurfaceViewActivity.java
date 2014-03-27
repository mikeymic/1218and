package com.example.methodsamples.glsurfaceview.camera;


import android.app.Activity;
import android.os.Bundle;

public class CameraGLSurfaceViewActivity extends Activity {

    public CameraGLSurfaceView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	view = new CameraGLSurfaceView(this);
	setContentView(view);
    }

    @Override
    protected void onPause() {
	super.onPause();
	view.onPause();
    }

    @Override
    protected void onResume() {
	super.onResume();
	view.onResume();
    }

}

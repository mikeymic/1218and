package com.example.methodsamples;

import hw.util.activities.launcher.ActivitiesLauncherList;

import com.example.methodsamples.glsurfaceview.camera.CameraGLSurfaceViewActivity;
import com.example.methodsamples.onthouch.TouchEventActivity;
import com.example.methodsamples.surfaceview.SimpleSurfaceViewActivity;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivitiesLauncherList list = new ActivitiesLauncherList(this);
		list.addActivity("SurfaceView Sample1","座標を保持したクラスをSurfaceViewで描画する（座標を使ったAnimation）", SimpleSurfaceViewActivity.class);
		list.addActivity("GLSurfaceView Sample1 [カメラの使用]","スライド移動", CameraGLSurfaceViewActivity.class);
		list.addActivity("Touch Event Sample","複数のタッチポイントを認識させる", TouchEventActivity.class);
		
	}
}

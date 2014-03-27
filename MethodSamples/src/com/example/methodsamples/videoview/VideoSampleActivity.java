package com.example.methodsamples.videoview;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.VideoView;

public class VideoSampleActivity extends Activity {

    public class MyVieoView extends VideoView {

	public MyVieoView(Context context) {
	    super(context);
	}

    }

    private int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private int MP = LinearLayout.LayoutParams.MATCH_PARENT;
    
    private int playButtonId = 0x10000;
    private int pauseButtonId = 0x10000;
    private int stopButtonId = 0x10000;
    
    private MyVieoView view;
    
    private View setView() {
	Button playButton = new Button(this);
	playButton.setBackgroundResource(android.R.drawable.ic_media_play);
	playButton.setOnClickListener(onClickButton);
	playButton.setId(playButtonId);
	
	Button pauseButton = new Button(this);
	pauseButton.setBackgroundResource(android.R.drawable.ic_media_play);
	pauseButton.setOnClickListener(onClickButton);
	pauseButton.setId(pauseButtonId);
	
	Button stopButton = new Button(this);
	stopButton.setBackgroundResource(android.R.drawable.ic_media_play);
	stopButton.setOnClickListener(onClickButton);
	stopButton.setId(stopButtonId);
	
	LinearLayout hol = new LinearLayout(this);
	hol.setOrientation(LinearLayout.HORIZONTAL);

	LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, WC, 1);
	
	hol.addView(playButton, param);
	hol.addView(pauseButton, param);
	hol.addView(stopButton, param);
	
	view = new MyVieoView(this);
	param = new LinearLayout.LayoutParams(MP, 0, 1);

	LinearLayout root = new LinearLayout(this);
	root.addView(view, param);
	root.addView(hol, param);
	return root;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(setView());
    }
    
    private OnClickListener onClickButton = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            int id = v.getId();
            
            if (id == playButtonId) {
        	view.setVideoPath(Environment.getExternalStorageDirectory().toString() + "/Movies/video.mp4");
		view.start();
	    }
            else if (id == pauseButtonId) {
        	view.pause();
            }
            else if (id == stopButtonId) {
        	view.stopPlayback();
            }
        }
    };


}

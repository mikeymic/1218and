package com.example.airsword;

import java.io.IOException;
import java.security.spec.MGF1ParameterSpec;
import java.util.Random;

import com.example.airsword.AirSwordListener.OnAirSwordListener;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;

public class AirSwordActivity extends Activity {

    private AirSwordListener asListener;
    private MediaPlayer mPlayer;
    private boolean mflg = false;

    public class Sound {

	// プールする音源の最大数
	public static final int MAX_SOUNDS_COUNT = 6;

	// サウンドプールの定義
	SoundPool soundPool;
	// サウンド格納配列
	int[] sounds = new int[MAX_SOUNDS_COUNT];

	public Sound(Context context) {
	// インスタンス生成
	// 第一引数　プールする音源の最大数
	// 第二引数　ストリームのタイプ通jy等はSTREAM_MUSICでOK
	// 第三引数　サンプリングレートのクオリティ（デフォルトは０）
	soundPool = new SoundPool(MAX_SOUNDS_COUNT, AudioManager.STREAM_MUSIC,0);

	// 音声ファイルのロード
	// 第一引数　コンテキスト
	// 第二引数　リソースID
	// 第三引数　プライオリティ（今は何も効果がないが将来の互換性のために１にする。）
	sounds[0] = soundPool.load(context, R.raw.saber7, 1);
	sounds[1] = soundPool.load(context, R.raw.saber8, 1);
	sounds[2] = soundPool.load(context, R.raw.saber11, 1);
	sounds[3] = soundPool.load(context, R.raw.saber12, 1);
	sounds[4] = soundPool.load(context, R.raw.saber13, 1);
	sounds[5] = soundPool.load(context, R.raw.saber14, 1);
//	sounds[0] = soundPool.load(context, R.raw.onsei1, 1);
//	sounds[1] = soundPool.load(context, R.raw.onsei2, 1);
//	sounds[2] = soundPool.load(context,R.raw.onsei3,1);
//	sounds[3]= soundPool.load(context,R.raw.onsei4,1);

	}

	public void doplay(int soundCount,Float speed) {

	// 再生します
	// 第一引数　サウンドID（ロードメソッドの戻り値を使う。）
	// 第二引数　左スピーカのボリューム（0.0から1.0の間）
	// 第三引数　→スピーカのボリューム（0.0から1.0の間）
	// 第四引数　プライオリティ（０は優先度が低い？とりあえず0にしておく）
	// 第五引数　ループ回数（0ならループなし、-1なら無限ループ）
	// 第六引数　再生速度（0.5から2.0の値で0.5なら半分のスピード2.0は倍速）
	soundPool.play(sounds[soundCount], 1.0F, 1.0F, 0, 0, speed);

	}

	}
    
    
    private Sound sound;
    private Random rnd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_air_sword);
	rnd = new Random();
	
	if (mPlayer == null) {
	    mPlayer = createMediaPlayer();
	    sound = new Sound(this);
	}
	
	//加速検知リスナーの生成
	asListener = new AirSwordListener(this);
	asListener.setAirSwordListener(new OnAirSwordListener() {
	    
	    @Override
	    public void onAccelero() {
//		mPlayer.start();
		int count = rnd.nextInt(sound.MAX_SOUNDS_COUNT);
		
		sound.doplay(count, 1.0f);
	    }
	});
	
    }
    
    @Override
    protected void onPause() {
	super.onPause();
	asListener.onPause();
    }
    
    @Override
    protected void onDestroy() {
	super.onDestroy();
	mPlayer.release();
    }

    @Override
    protected void onResume() {
	super.onResume();
	asListener.onResume();
    }

    public void onPlay(View v) {
	if (mPlayer.isPlaying()) {
	    mPlayer.stop();
	    try {
		mPlayer.prepare();
	    } catch (IllegalStateException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	    }
	}
	else {
	    mPlayer.start();
	    
	}
    }
    
    private MediaPlayer createMediaPlayer() {
	return MediaPlayer.create(this, R.raw.theme);
    }



}

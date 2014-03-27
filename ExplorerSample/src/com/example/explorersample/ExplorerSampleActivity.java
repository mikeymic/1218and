package com.example.explorersample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExplorerSampleActivity extends Activity {


	private File file;
	private ArrayList<String> names;
	private ArrayAdapter<String> adapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explorer_sample);
		
		listView = (ListView) findViewById(R.id.listView);
		names = new ArrayList<String>();
		
		file = Environment.getRootDirectory();
		File[] fileList = file.listFiles();//Fileに含まれるファイルとディレクトリの一覧の取得
		
		if (file.isFile()) {
		names.add("[FILE]" + file.getName());
	} else if (file.isDirectory()) {
		names.add("[FOLDER]" + file.getName());
	}
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onClickList);
	}
	
	private OnItemClickListener onClickList = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			if(getMount_sd() != null){
				   File file = new File(getMount_sd(), "data");

				   // SDカードに保存する処理

				}else{
				   // SDカードのマウント先が見つからない場合の処理
				}
			
		}
	};
	


	private String getMount_sd() {
		   List<String> mountList = new ArrayList<String>();
		   String mount_sdcard = null;

		   Scanner scanner = null;
		   try {
		      // システム設定ファイルにアクセス
		      File vold_fstab = new File("/system/etc/vold.fstab");
		      scanner = new Scanner(new FileInputStream(vold_fstab));
		      // 一行ずつ読み込む
		      while (scanner.hasNextLine()) {
		         String line = scanner.nextLine();
		         // dev_mountまたはfuse_mountで始まる行の
		         if (line.startsWith("dev_mount") || line.startsWith("fuse_mount")) {	            	
		            // 半角スペースではなくタブで区切られている機種もあるらしいので修正して
		            // 半角スペース区切り３つめ（path）を取得
		            String path = line.replaceAll("\t", " ").split(" ")[2];
		            // 取得したpathを重複しないようにリストに登録
		            if (!mountList.contains(path)){
		               mountList.add(path);
		            }
		         }
		      }
		   } catch (FileNotFoundException e) {
		      throw new RuntimeException(e);
		   } finally {
		      if (scanner != null) {
		         scanner.close();
		      }
		   }

		   // Environment.isExternalStorageRemovable()はGINGERBREAD以降しか使えない
		   if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){	    	
		      // getExternalStorageDirectory()が罠であれば、そのpathをリストから除外
		      if (!Environment.isExternalStorageRemovable()) {   // 注1
		         mountList.remove(Environment.getExternalStorageDirectory().getPath());
		      }
		   }

		   // マウントされていないpathは除外
		   for (int i = 0; i < mountList.size(); i++) {
		      if (!isMounted(mountList.get(i))){
		         mountList.remove(i--);
		      }
		   }

		   // 除外されずに残ったものがSDカードのマウント先
		   if(mountList.size() > 0){
		      mount_sdcard = mountList.get(0);
		   }
			    
		   // マウント先をreturn（全て除外された場合はnullをreturn）
		   return mount_sdcard;
		}

		// 引数に渡したpathがマウントされているかどうかチェックするメソッド
		public boolean isMounted(String path) {
		   boolean isMounted = false;

		   Scanner scanner = null;
		   try {
		      // マウントポイントを取得する
		      File mounts = new File("/proc/mounts");   // 注2
		      scanner = new Scanner(new FileInputStream(mounts));
		      // マウントポイントに該当するパスがあるかチェックする
		      while (scanner.hasNextLine()) {
		         if (scanner.nextLine().contains(path)) {
		            // 該当するパスがあればマウントされているってこと
		            isMounted = true;
		            break;
		         }
		      }
		   } catch (FileNotFoundException e) {
		      throw new RuntimeException(e);
		   } finally {
		      if (scanner != null) {
		      scanner.close();
		      }
		   }

		   // マウント状態をreturn
		   return isMounted;
		}

}

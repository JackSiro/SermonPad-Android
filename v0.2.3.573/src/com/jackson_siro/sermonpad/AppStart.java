package com.jackson_siro.sermonpad;

import java.io.File;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppStart extends Activity implements AnimationListener{

	private TextView mytext;
	private ImageView myimage;
	
	public static final String SETTING_INFOS = "SETTING_Infos", USERNAME = "USERNAME",  LOG_TAG = "Splash";
	
	private long ms=0, splashTime=5000;
	private boolean splashActive = true, paused=false;
	RelativeLayout MyNote;
	
   @SuppressLint("NewApi") @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.splash);
      SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
	    String username = settings.getString(USERNAME, "");
	
		isExternalStoragepresent();
		createDirIfNotExits("AppSmata");
		
      mytext = (TextView) findViewById(R.id.text);
      myimage = (ImageView) findViewById(R.id.image);
      
      Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
      myimage.startAnimation(animation1);
      
      Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade1);
      mytext.startAnimation(animation2);
		
		Thread mythread = new Thread() {
				public void run() {
					try {
						while (splashActive && ms < splashTime) {
							if(!paused)
								ms=ms+100;
							sleep(100);
						}
					} catch(Exception e) {}
					finally {
						Intent intent = new Intent(AppStart.this, BbListSermon.class);
						startActivity(intent);
						finish();  						 
					}
				}
			};
			mythread.start();
			
   }
   
	private boolean isExternalStoragepresent(){
		
		boolean mExternalStorageAvailable= false;
		boolean mExternalStorageWritable= false;
		
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)){
			mExternalStorageAvailable = mExternalStorageWritable = true; 
			
		} else {
			if(!((mExternalStorageAvailable) && (mExternalStorageWritable))){
				//Toast.makeText(getBaseContext(), "SD card not present", Toast.LENGTH_LONG).show();
			} return (mExternalStorageAvailable) && (mExternalStorageWritable);
		}
		return mExternalStorageWritable;
		
	}
		
	public static boolean createDirIfNotExits (String path){
		
		boolean ret =true;
		File file = new File(Environment.getExternalStorageDirectory(),path);
		if (!file.exists()){
			if (!file.mkdirs()){
				Log.e("SermonPad::", "Problem Creating SermonPad Folder");
				ret = false;
			}
		}
		return ret;
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	} 
	
}

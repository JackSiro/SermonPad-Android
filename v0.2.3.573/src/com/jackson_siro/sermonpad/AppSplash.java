package com.jackson_siro.sermonpad;

import java.io.File;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.appcompat.*;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import com.jackson_siro.sermonpad.*;

public class AppSplash extends Activity {
	NotificationCompat.Builder notification;
	PendingIntent pIntent;
	NotificationManager manager;
	Intent resultIntent;
	TaskStackBuilder stackBuilder;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		createDirIfNotExits("AppSmata/SermonPad");		
		setFirstUse();
				
		startActivity(new Intent(AppSplash.this, AppStart.class));
		finish();
	}
	
	public void setFirstUse() {
		if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("sp_first_use", false)) {
			Toast.makeText(this, "Welcome to SermonPad", Toast.LENGTH_SHORT).show();
		    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		    localEditor.putBoolean("sp_first_use", true);
		    localEditor.putLong("sp_first_data", System.currentTimeMillis());
		    localEditor.commit();
		}
		
	  }
	
	@SuppressWarnings("unused")
	private boolean isExternalStoragepresent(){
		
		boolean mExternalStorageAvailable= false;
		boolean mExternalStorageWritable= false;
		
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)){
			mExternalStorageAvailable = mExternalStorageWritable = true; 
			
		} else {
			if(!((mExternalStorageAvailable) && (mExternalStorageWritable))){
			} return (mExternalStorageAvailable) && (mExternalStorageWritable);
		}
		return mExternalStorageWritable;
		
	}
	
	public static boolean createDirIfNotExits (String path){
		
		boolean ret =true;
		File file = new File(Environment.getExternalStorageDirectory(),path);
		if (!file.exists()){
			if (!file.mkdirs()){
				Log.e("AppSmata::", "Problem Creating AppSmata Folder");
				ret = false;
			}
		}
		return ret;
	}
	
	
	
}
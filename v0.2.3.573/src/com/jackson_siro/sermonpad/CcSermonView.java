package com.jackson_siro.sermonpad;

import com.jackson_siro.sermonpad.tools.*;
import com.jackson_siro.sermonpad.adaptor.SimpleGestureFilter;
import com.jackson_siro.sermonpad.adaptor.SimpleGestureFilter.SimpleGestureListener;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class CcSermonView extends ActionBarActivity implements SimpleGestureListener {
	
	public static final String VSB_SETTINGS = "Sp_Settings", FONT_SIZE = "font_size", CURRENT_SERMON = "12";
	
	TableLayout MyTopbar, MyToolbar;
	TextView sDetails, sContent, Extra;
	Sermon selectedNote;
	SQLiteHelper db;
	
	private SimpleGestureFilter detector;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sermon_note);
		db = new SQLiteHelper(getApplicationContext());
		
		ScrollView ScrollQuote = (ScrollView) findViewById(R.id.ScrollSermon);
		getLayoutInflater().inflate(R.layout.scrollable, ScrollQuote);
		
		ShowToolBar();

		sDetails = (TextView) findViewById(R.id.sermondet);
		sContent = (TextView) findViewById(R.id.content);
		 		
		detector = new SimpleGestureFilter(this,this);
		
		Intent intent = getIntent();
		int qid = intent.getIntExtra("sermon_view", -1);
		selectedNote = db.readNote(qid);
		SharedPreferences settings = getSharedPreferences(VSB_SETTINGS, 12);		
        String noteid = Integer.toString(qid);
	    settings.edit().putString(CURRENT_SERMON, noteid).commit(); 
	    
	    changeStatusBarColor();
		initializeViews();
	}
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent me){
        
         this.detector.onTouchEvent(me);
       return super.dispatchTouchEvent(me);
    }
    @Override
     public void onSwipe(int direction) {
      String str = "";
            
      switch (direction) {
      
      case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
      	  
	      break;
      case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
      		
	      break;
      }
       
     }
      
     @Override
     public void onDoubleTap() {
    	 MyTopbar = (TableLayout) findViewById(R.id.myTopbar);
     	MyToolbar = (TableLayout) findViewById(R.id.myToolbar);
     	
    	 if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("sp_show_toolbar", false)) {
  			SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
  	 	    localEditor.putBoolean("sp_show_toolbar", true);
  	 	    localEditor.commit();
  	 	    Toast.makeText(this, "Toolbar Hidden", Toast.LENGTH_SHORT).show();
  	 	    MyTopbar.setVisibility(View.GONE);
  	 	    MyToolbar.setVisibility(View.GONE);
  			
  		} else  {
  			SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
  	 	    localEditor.putBoolean("sp_show_toolbar", false);
  	 	    localEditor.commit();
  	 	    Toast.makeText(this, "Toolbar Reshown", Toast.LENGTH_SHORT).show();
  	 	    MyTopbar.setVisibility(View.VISIBLE);
	 	    MyToolbar.setVisibility(View.VISIBLE);
  		} 
     }
     
     public void KeepScreenOn(){
    	sContent = (TextView) findViewById(R.id.content);
 		
 		if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("keep_screen_on", false)) {
 			//getWindow().setFlags(this.getWindow().getFlags() & )
 		} else {
 			
 		}
 	}
     
     public void ShowToolBar(){
    	 MyTopbar = (TableLayout) findViewById(R.id.myTopbar);
     	MyToolbar = (TableLayout) findViewById(R.id.myToolbar);
     	if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("sp_show_toolbar", false)) {
  			MyTopbar.setVisibility(View.VISIBLE);
  	 	    MyToolbar.setVisibility(View.VISIBLE);
  		} else {
  			MyTopbar.setVisibility(View.GONE);
  	 	    MyToolbar.setVisibility(View.GONE);
  		}
  	}

 	public void initializeViews() {
 		SharedPreferences settings = getSharedPreferences(VSB_SETTINGS, 25);
        String font_size = settings.getString(FONT_SIZE, "25");
        		
 		setTitle(selectedNote.getTitle());
 		sDetails.setText(selectedNote.getPreacher() + " | " + selectedNote.getPlace() + " | " + selectedNote.getDate());
 		sContent.setText(selectedNote.getContent());
 		
 		int myFonts = Integer.parseInt(font_size);
 		sContent.setTextSize(myFonts);
         
 	}
 	
 	public void Previous (View view){
 		SharedPreferences settings = getSharedPreferences(VSB_SETTINGS, 12);
        String current_note = settings.getString(CURRENT_SERMON, "12");
        int qid = (Integer.parseInt(current_note));
 	    
 		db = new SQLiteHelper(getApplicationContext());
 		selectedNote = db.readPrevNote(qid);
 		initializeViews();
 		
 		String noteid = Integer.toString(selectedNote.getSermonid());
 	    settings.edit().putString(CURRENT_SERMON, noteid).commit();

 	}

 	public void Next (View view){
 		SharedPreferences settings = getSharedPreferences(VSB_SETTINGS, 12);
        String current_note = settings.getString(CURRENT_SERMON, "12");
        int qid = (Integer.parseInt(current_note));
 	    
 		db = new SQLiteHelper(getApplicationContext());
		
 		/*
 		try {
			
		} catch ( e) {
			e.printStackTrace();
			
		} */
 		
 		selectedNote = db.readNextNote(qid);
 		initializeViews();
 		
 		String noteid = Integer.toString(selectedNote.getSermonid());
 	    settings.edit().putString(CURRENT_SERMON, noteid).commit();
 	}
 	
	public void Minus (View view){
		SharedPreferences settings = getSharedPreferences(VSB_SETTINGS, 25);
        String font_size = settings.getString(FONT_SIZE, "25");
        
        int newFonts = (Integer.parseInt(font_size)-2);
        String New_Fonts = Integer.toString(newFonts);
	    settings.edit().putString(FONT_SIZE, New_Fonts).commit();
	    		
		sContent.setTextSize(newFonts);
		
	}
	
	public void Plus (View view){
		SharedPreferences settings = getSharedPreferences(VSB_SETTINGS, 25);
        String font_size = settings.getString(FONT_SIZE, "25");
        
        int newFonts = (Integer.parseInt(font_size)+2);
        String New_Fonts = Integer.toString(newFonts);
	    settings.edit().putString(FONT_SIZE, New_Fonts).commit();	    
	    		
		sContent.setTextSize(newFonts);
		
	}
	
	public void Edit (View view){
		Intent intent = new Intent(CcSermonView.this, CcSermonEdit.class);
		intent.putExtra("sermon_edit", selectedNote.getSermonid());
		startActivityForResult(intent, 1);
	}

	public void Delete (View view){
		AlertDialog.Builder builder = new AlertDialog.Builder(CcSermonView.this);
		builder.setTitle("Just a minute...");
		builder.setMessage("Are you sure you want to delete this note: " + selectedNote.getTitle() + "? This action is not reversable.");
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getApplicationContext(), "God bless you", Toast.LENGTH_LONG).show();
				
			}
		});

		builder.setPositiveButton("Amen", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getApplicationContext(), "You have deleted the note " + selectedNote.getTitle(), Toast.LENGTH_LONG).show();
				db.deleteNote(selectedNote);
        		finish();
			}
		});
		
		builder.show(); 
		
	}

	public void Share (View view){
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("text/plain");
		share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		
		String thetitle = selectedNote.getTitle();
		String TheContent = selectedNote.getContent();
		String Sermon = TheContent.replace("$", System.getProperty("line.separator"));
		
		share.putExtra(Intent.EXTRA_SUBJECT, thetitle);
        share.putExtra(Intent.EXTRA_TEXT, Sermon + "\n\nVia SermonPad Android App");
		startActivity(Intent.createChooser(share, "Share this Sermon"));
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.own_note, menu);
        
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	case R.id.editnote:
        		Intent intent = new Intent(CcSermonView.this, CcSermonEdit.class);
        		intent.putExtra("sermon_edit", selectedNote.getSermonid());
        		startActivityForResult(intent, 1);
	            return true;
        	
	        case R.id.delete:
	        	AlertDialog.Builder builder = new AlertDialog.Builder(CcSermonView.this);
	    		builder.setTitle("Just a minute...");
	    		builder.setMessage("Are you sure you want to delete this note: " + selectedNote.getTitle() + "? This action is not reversable.");
	    		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(DialogInterface arg0, int arg1) {
	    				Toast.makeText(getApplicationContext(), "God bless you", Toast.LENGTH_LONG).show();
	    				
	    			}
	    		});

	    		builder.setPositiveButton("Amen", new DialogInterface.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(DialogInterface arg0, int arg1) {
	    				Toast.makeText(getApplicationContext(), "You have deleted the note " + selectedNote.getTitle(), Toast.LENGTH_LONG).show();
	    				db.deleteNote(selectedNote);
	            		finish();
	    			}
	    		});
	    		
	    		builder.show(); 
	            return true;
	                       	
            default:
                return false;
        }
	}

	// Making notification bar transparent
    @SuppressLint("NewApi")
	private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.brown));
        }
    }
}

package com.jackson_siro.sermonpad;

import java.text.SimpleDateFormat;
import com.jackson_siro.sermonpad.tools.*;
import java.util.Date;

import com.jackson_siro.sermonpad.adaptor.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CcSermonNew extends ActionBarActivity{
	
	public static int numTitle = 1;	
	public static String curDate = "", curText = "";	
    private EditText mTitle, mBody;
    private TextView mDate, mPreacher, mPlace;
    private Long mRowId;

    private Cursor note;

	SQLiteHelper db = new SQLiteHelper(this);
      
	@SuppressLint("SimpleDateFormat")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.sermon_edit);
        
        mTitle = (EditText) findViewById(R.id.title);
        mPreacher = (EditText) findViewById(R.id.preacher);
        mPlace = (EditText) findViewById(R.id.place);
        mBody = (EditText) findViewById(R.id.content);

        long msTime = System.currentTimeMillis();  
        Date curDateTime = new Date(msTime);
 	
        SimpleDateFormat formatter = new SimpleDateFormat("dd'/'MM'/'yyyy");  
        curDate = formatter.format(curDateTime);        
        
        setTitle("Save new Note | " + curDate);

        /*
        mRowId = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(NotesDbAdapter.KEY_ROWID);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = extras != null ? extras.getLong(NotesDbAdapter.KEY_ROWID)
                                    : null;
        }*/

        //populateFields();
    
    }
	
	public void AddMoreExtraInfo(){
		AlertDialog.Builder builder = new AlertDialog.Builder(CcSermonNew.this);
		builder.setTitle("Just a minute...");
		builder.setMessage("Are you sure you want to delete this note? This action is not reversable.");
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getApplicationContext(), "God bless you", Toast.LENGTH_LONG).show();
				
			}
		});

		builder.setPositiveButton("Amen", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getApplicationContext(), "Thank You", Toast.LENGTH_LONG).show();
				
        		finish();
			}
		});
		
		builder.show(); //To show the AlertDialog
	}
	
	  @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	       //AddNewNote();
	       // outState.putSerializable(NotesDbAdapter.KEY_ROWID, mRowId);
	    }
	    
	    @Override
	    protected void onPause() {
	        super.onPause();
	        //AddNewNote();
	    }
	    
	    @Override
	    protected void onResume() {
	        super.onResume();
	        //populateFields();
	    }
	    
	    public void AddNewNote() {
	    	String title = mTitle.getText().toString();
	    	String preacher = mPreacher.getText().toString();
	    	String place = mPlace.getText().toString();
	    	String body = mBody.getText().toString();
	    	
	        if(title.length() == 0){
	        	if(body.length() == 0){
	        		Toast.makeText(getApplicationContext(), "Nothing to save!", Toast.LENGTH_SHORT).show();
	    			Log.e("saveState","failed to create note");
	        	} else {
	        		db.createNote(new Sermon(curDate, title, preacher, place, body, "", 1)); 
	        		Toast.makeText(getApplicationContext(), "A new note has been created.", Toast.LENGTH_SHORT).show();
	    			Log.e("saveState","A new note has been created");
	        	}
	        } else {
	        	db.createNote(new Sermon(curDate, title, preacher, place, body, "", 1));
        		Toast.makeText(getApplicationContext(), "A new note has been created.", Toast.LENGTH_SHORT).show();
    			Log.e("saveState","A new note has been created");
	        }
	        
		}
	    
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.own_save, menu);
			return true;		
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		    case R.id.savethis:
           	 	AddNewNote();
           	 	finish();
                
            case R.id.cancel:
            	finish();
                return true;

		    default:
		    	return super.onOptionsItemSelected(item);
		    }
		}
	    
	  


}

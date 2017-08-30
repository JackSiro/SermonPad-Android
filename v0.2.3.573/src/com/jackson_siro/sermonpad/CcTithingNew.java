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
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CcTithingNew extends ActionBarActivity{
	
	public static int numAmount = 1;	
	public static String curDate = "", curText = "";	
    private EditText mAmount, mSource, mPlace;
    private Cursor note;

	SQLiteHelper db = new SQLiteHelper(this);
      
	@SuppressLint("SimpleDateFormat")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tithing_new);
        
        mAmount = (EditText) findViewById(R.id.amount);
        mSource = (EditText) findViewById(R.id.source);
        mPlace = (EditText) findViewById(R.id.place);

        setTitle("New Tithing Item | " + curDate);

    }
	
	public void AddMoreExtraInfo(){
		AlertDialog.Builder builder = new AlertDialog.Builder(CcTithingNew.this);
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
	    
	    @SuppressLint("SimpleDateFormat")
		public void AddNewNote() {
	        long msTime = System.currentTimeMillis();  
	        Date curDateTime = new Date(msTime);
	        
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
	        curDate = formatter.format(curDateTime);        
	        
	        
	    	
	    	int amount = Integer.parseInt(mAmount.getText().toString());
	    	String source = mSource.getText().toString();
	    	String place = mPlace.getText().toString();
	    	
	        if(amount < 0){
	        	if(amount < 0){
	        		Toast.makeText(getApplicationContext(), "Nothing to save!", Toast.LENGTH_SHORT).show();
	    			Log.e("saveState","failed to create note");
	        	} else {
	        		db.createTithing(new Tithing(curDate, (amount/10), source, place, 1)); 
	        		tithingAmount(amount/10);
	        		Toast.makeText(getApplicationContext(), "A new note has been created.", Toast.LENGTH_SHORT).show();
	    			Log.e("saveState","A new note has been created");
	        	}
	        } else {
	        	db.createTithing(new Tithing(curDate, (amount/10), source, place, 1)); 
	        	tithingAmount(amount/10);
	        	Toast.makeText(getApplicationContext(), "A new note has been created.", Toast.LENGTH_SHORT).show();
    			Log.e("saveState","A new note has been created");
	        }
	        
		}
	    
	    public void tithingAmount(int amount){
	    	SharedPreferences vSettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	        int currentTithes = Integer.parseInt(vSettings.getString("sp_current_tithes", "0"));
	        
	    	String myTithe = Integer.toString(currentTithes + amount);
	    	SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		    localEditor.putString("sp_current_tithes", myTithe);
		    localEditor.commit();
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

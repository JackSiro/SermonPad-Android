package com.jackson_siro.sermonpad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.jackson_siro.sermonpad.tools.*;

@SuppressWarnings("deprecation")
public class CcSermonEdit extends ActionBarActivity {
	
	public static final String VSB_SETTINGS = "Sp_Settings", FONT_SIZE = "font_size";
	
	private String noteTitle, noteContent;	
	EditText title, preacher, place, content;
	Sermon selectedNote;
	SQLiteHelper db;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sermon_edit);
		
		title = (EditText) findViewById(R.id.title);
		preacher = (EditText) findViewById(R.id.preacher);
		place = (EditText) findViewById(R.id.place);
		content = (EditText) findViewById(R.id.content);
				
		Intent intent = getIntent();
		int qid = intent.getIntExtra("sermon_edit", -1);

		db = new SQLiteHelper(getApplicationContext());
		selectedNote = db.readNote(qid);
		initializeViews();
	}
	
	public void initializeViews() {
		//SharedPreferences settings = getSharedPreferences(VSB_SETTINGS, 25);
        //String font_size = settings.getString(FONT_SIZE, "25");

		setTitle(selectedNote.getTitle());
		title.setText(selectedNote.getTitle());
		preacher.setText(selectedNote.getPreacher());
		place.setText(selectedNote.getPlace());
		content.setText(selectedNote.getContent());
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.own_save, menu);
        
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.savethis:
            	UpdateNote();
                return true;
                
            case R.id.delete:
            	DeleteThis();				
                return true;
            case R.id.cancel:
            	finish();
            	
            default:
                return false;
        }
	}
	
	public void UpdateNote() {
		
		selectedNote.setDate(selectedNote.getDate());
		selectedNote.setTitle(((EditText) findViewById(R.id.title)).getText().toString());
		selectedNote.setPlace(((EditText) findViewById(R.id.place)).getText().toString());
		selectedNote.setPreacher(((EditText) findViewById(R.id.preacher)).getText().toString());
		selectedNote.setContent(((EditText) findViewById(R.id.content)).getText().toString());
		selectedNote.setExtra(selectedNote.getExtra());
		selectedNote.setState(selectedNote.getState());
		
		db.updateNote(selectedNote); 
		Toast.makeText(getApplicationContext(), "The note: " + selectedNote.getTitle() + " has been updated", Toast.LENGTH_SHORT).show();
		
		finish();
	}
	
	private void DeleteThis(){
		AlertDialog.Builder builder = new AlertDialog.Builder(CcSermonEdit.this);
		builder.setTitle("Just a minute...");
		builder.setMessage("Are you sure you want to delete the note: " + selectedNote.getTitle() + "? This action is not reversable.");
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getApplicationContext(), "God bless you", Toast.LENGTH_LONG).show();
				
			}
		});

		builder.setPositiveButton("Amen", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getApplicationContext(), "You have deleted the note: " + selectedNote.getTitle(), Toast.LENGTH_LONG).show();
				db.deleteNote(selectedNote);
				startActivity(new Intent(CcSermonEdit.this, BbListSermon.class));
        		finish();
			}
		});
		
		builder.show(); //To show the AlertDialog
	}
}

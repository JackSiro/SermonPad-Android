package com.jackson_siro.sermonpad;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import com.jackson_siro.sermonpad.tools.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.jackson_siro.sermonpad.adaptor.*;
import com.jackson_siro.sermonpad.tools.*;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

public class BbListTrashbin extends ActionBarActivity {
	ListView list;		
	private String[] My_Text;
	private String[] My_Texti;
	private String[] My_Textii;
	private String[] My_Textiii;
	private String[] My_Textiv;
	
	SQLiteHelper db = new SQLiteHelper(this);
	List<Sermon> mylist;
	ArrayAdapter<String> myAdapter;

	ArrayList<HashMap<String, String>> mySermons;
    private static final String TAG = "Sermons";
	private static final int DELETE_ID = Menu.FIRST, mNoteNumber = 1;

	private void FillWithData(){
		
		mylist = db.getTrashList();
		List<String> listId = new ArrayList<String>();
		List<String> listDate = new ArrayList<String>();
		List<String> listTitle = new ArrayList<String>();
		List<String> listCont = new ArrayList<String>();
		List<String> listExtra = new ArrayList<String>();
		
		for (int i = 0; i < mylist.size(); i++) {
			listId.add(i, Integer.toString(mylist.get(i).getSermonid()));
			listDate.add(i, mylist.get(i).getDate());
			listTitle.add(i, mylist.get(i).getTitle());
			listCont.add(i, mylist.get(i).getContent());
			listExtra.add(i, mylist.get(i).getPreacher());
		}
		
		My_Text = listId.toArray(new String[listId.size()]);		
		for (String string : My_Text) {	System.out.println(string);	}
		
		My_Texti = listDate.toArray(new String[listDate.size()]);		
		for (String stringi : My_Texti) {	System.out.println(stringi);	}
		
		My_Textii = listTitle.toArray(new String[listTitle.size()]);		
		for (String stringii : My_Textii) {	System.out.println(stringii);	}

		My_Textiii = listCont.toArray(new String[listCont.size()]);		
		for (String stringiii : My_Textiii) {	System.out.println(stringiii);	}

		My_Textiv = listExtra.toArray(new String[listExtra.size()]);		
		for (String stringiv : My_Textiv) {	System.out.println(stringiv);	}

		CustomListItem adapter = new CustomListItem(BbListTrashbin.this, 
				My_Text, My_Texti, My_Textii, My_Textiii, My_Textiv);
		
		list=(ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
		
		registerForContextMenu(list);
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
    			final String clickedOne = My_Text[+ position];
    	        final String clickedItem = My_Textii[+ position];
    	        final Sermon clickedSermon = db.readNote(Integer.parseInt(clickedOne));
    	    	
    			AlertDialog.Builder builder = new AlertDialog.Builder(BbListTrashbin.this);
				builder.setTitle(clickedItem);
				builder.setMessage("Choose what you want to do with this note. You may restore or delete this note completely!");
				builder.setNegativeButton("Restore", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getApplicationContext(), clickedItem + " restored!", Toast.LENGTH_LONG).show();
						db.restoreNote(clickedSermon);
						FillWithData();	
						
					}
				});

				builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getApplicationContext(), clickedItem + " deleted completely!", Toast.LENGTH_LONG).show();
						db.trashNote(clickedSermon);
						FillWithData();	
								
					}
				});
				
				builder.show(); 
            }
        });	
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trashed);
		changeStatusBarColor();
		FillWithData();
				
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        
		FillWithData();	
				        
    } 
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trashlist, menu);
        
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		AlertDialog.Builder builder = new AlertDialog.Builder(BbListTrashbin.this);
		
        switch (item.getItemId()) {
        
	        case R.id.clear:
	        	builder.setTitle("Just a minute...");
				builder.setMessage("Are you sure you want to restore all notes?");
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getApplicationContext(), "God bless you", Toast.LENGTH_LONG).show();
					}
				});

				builder.setPositiveButton("Amen", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getApplicationContext(), "You have restored all the notes", Toast.LENGTH_LONG).show();
						db.restoreNotes();
						FillWithData();	
								
					}
				});
				
				builder.show(); 
			    return true;
			    
	        case R.id.trash:
	        	builder.setTitle("Just a minute...");
				builder.setMessage("Are you sure you want to delete all notes completely? This action is not reversable.");
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getApplicationContext(), "God bless you", Toast.LENGTH_LONG).show();
					}
				});

				builder.setPositiveButton("Amen", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getApplicationContext(), "You have deleted all the notes", Toast.LENGTH_LONG).show();
						db.deleteTrashedNotes();
						FillWithData();	
								
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

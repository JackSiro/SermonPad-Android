package com.jackson_siro.sermonpad;

import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.ContextMenu;
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

import com.jackson_siro.sermonpad.adaptor.CustomListItem;
import com.jackson_siro.sermonpad.tools.*;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

public class BbListSermon extends ActionBarActivity implements OnMenuItemClickListener {
	ListView list;		
	private String[] My_Text, My_Texti, My_Textii, My_Textiii, My_Textiv;
	
	SQLiteHelper db = new SQLiteHelper(this);
	List<Sermon> mylist;
	ArrayAdapter<String> myAdapter;

	ArrayList<HashMap<String, String>> mySermons;

	private static final int ACTIVITY_CREATE=0, ACTIVITY_EDIT=1, DELETE_ID = Menu.FIRST, mNoteNumber = 1;
	public static final String TAG = "Sermons", SP_SETTINGS = "Sp_Settings", SP_TABLEOD = "DESC";
	
	private void FillWithData(String TBL_COLUMN, String TBL_ORDER){
		
		mylist = db.getSermonList(TBL_COLUMN, TBL_ORDER);
		List<String> listSermonid = new ArrayList<String>();
		List<String> listDate = new ArrayList<String>();
		List<String> listTitle = new ArrayList<String>();
		List<String> listCont = new ArrayList<String>();
		List<String> listExtra = new ArrayList<String>();
		
		for (int i = 0; i < mylist.size(); i++) {
			listSermonid.add(i, Integer.toString(mylist.get(i).getSermonid()));
			listDate.add(i, mylist.get(i).getDate());
			listTitle.add(i, mylist.get(i).getTitle());
			listCont.add(i, mylist.get(i).getContent());
			listExtra.add(i, mylist.get(i).getPreacher());
		}
		
		My_Text = listSermonid.toArray(new String[listSermonid.size()]);		
		for (String string : My_Text) {	System.out.println(string);	}
		
		My_Texti = listDate.toArray(new String[listDate.size()]);		
		for (String stringi : My_Texti) {	System.out.println(stringi);	}
		
		My_Textii = listTitle.toArray(new String[listTitle.size()]);		
		for (String stringii : My_Textii) {	System.out.println(stringii);	}

		My_Textiii = listCont.toArray(new String[listCont.size()]);		
		for (String stringiii : My_Textiii) {	System.out.println(stringiii);	}

		My_Textiv = listExtra.toArray(new String[listExtra.size()]);		
		for (String stringiv : My_Textiv) {	System.out.println(stringiv);	}

		CustomListItem adapter = new CustomListItem(BbListSermon.this, 
				My_Text, My_Texti, My_Textii, My_Textiii, My_Textiv);
		
		list=(ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
		
		registerForContextMenu(list);
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
    			Intent intent = new Intent(BbListSermon.this, CcSermonView.class);
    			intent.putExtra("sermon_view", Integer.parseInt(My_Text[+ position]));
    			startActivityForResult(intent, 1);
        		
            }
        });	
		
		SharedPreferences settings = getSharedPreferences(SP_SETTINGS, 25);
        settings.edit().putString(SQLiteHelper.S_ID, TBL_COLUMN).commit();
        settings.edit().putString(SP_TABLEOD, TBL_ORDER).commit();

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_sermons);
		SharedPreferences settings = getSharedPreferences(SP_SETTINGS, 25);
        String table_column = settings.getString(SQLiteHelper.S_ID, SQLiteHelper.S_ID);
        String table_order = settings.getString(SP_TABLEOD, "DESC");
        changeStatusBarColor();
		FillWithData(table_column, table_order);
				
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	menu.setHeaderTitle(R.string.select_action);
    	menu.setHeaderIcon(R.drawable.ic_launcher);
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_context, menu);
        
    };
    	

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        
        AdapterContextMenuInfo menuinfo = (AdapterContextMenuInfo)item.getMenuInfo();
		final String selectedOne = My_Text[menuinfo.position];
        final String selectedItem = My_Textii[menuinfo.position];
        final Sermon selectedSermon = db.readNote(Integer.parseInt(selectedOne));
    	
        switch (item.getItemId()) {
        	case R.id.view_note:
        		Intent intent = new Intent(BbListSermon.this, CcSermonView.class);
    			intent.putExtra("sermon_view", Integer.parseInt(selectedOne));
    			startActivityForResult(intent, 1);
    			return true;
            
			case R.id.edit_note:
				Intent intent_i = new Intent(BbListSermon.this, CcSermonEdit.class);
				intent_i.putExtra("sermon_edit", Integer.parseInt(selectedOne));
				startActivityForResult(intent_i, 1);
			    return true;
			case R.id.delete_note:
				AlertDialog.Builder builder = new AlertDialog.Builder(BbListSermon.this);
				builder.setTitle("Just a minute...");
				builder.setMessage("Are you sure you want to delete this note: " + selectedItem + "? You can still restore the note from Trashed items.");
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getApplicationContext(), "God bless you", Toast.LENGTH_LONG).show();
						
					}
				});

				builder.setPositiveButton("Amen", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getApplicationContext(), "You have deleted the note " + selectedItem, Toast.LENGTH_LONG).show();
						db.deleteNote(selectedSermon);
						
						SharedPreferences settings = getSharedPreferences(SP_SETTINGS, 25);
				        String table_column = settings.getString(SQLiteHelper.S_ID, SQLiteHelper.S_ID);
				        String table_order = settings.getString(SP_TABLEOD, "DESC");
				        
						FillWithData(table_column, table_order);	
								
					}
				});
				
				builder.show(); 
			    return true;
    
        
        default:
            return false;
    }
    }

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        SharedPreferences settings = getSharedPreferences(SP_SETTINGS, 25);
        String table_column = settings.getString(SQLiteHelper.S_ID, SQLiteHelper.S_ID);
        String table_order = settings.getString(SP_TABLEOD, "DESC");
        
		FillWithData(table_column, table_order);	
				        
    } 
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sermon_list, menu);
        
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		
        switch (item.getItemId()) {

        	case R.id.tithing:
        		startActivity(new Intent(BbListSermon.this, BbLogin.class));
        		return true;
           
	        case R.id.delete:
	        	AlertDialog.Builder builder = new AlertDialog.Builder(BbListSermon.this);
				builder.setTitle("Just a minute...");
				builder.setMessage("Are you sure you want to delete all notes? You can still restore them from Trashed items later.");
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getApplicationContext(), "God bless you", Toast.LENGTH_LONG).show();
						
					}
				});

				builder.setPositiveButton("Amen", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(getApplicationContext(), "You have deleted all the notes!", Toast.LENGTH_LONG).show();
						db.deleteAllNotes();
						
						SharedPreferences settings = getSharedPreferences(SP_SETTINGS, 25);
				        String table_column = settings.getString(SQLiteHelper.S_ID, SQLiteHelper.S_ID);
				        String table_order = settings.getString(SP_TABLEOD, "DESC");				        
						FillWithData(table_column, table_order);	
								
					}
				});
				
				builder.show(); 
			    return true;
	        
            default:
                return false;
        }
    }
	
	public void NewSermon (View view){		
		startActivityForResult(new Intent(this, CcSermonNew.class), ACTIVITY_CREATE); 
	}
	
	public void SortList (View view){
		PopupMenu popupMenu = new PopupMenu(BbListSermon.this, view);
		popupMenu.setOnMenuItemClickListener(BbListSermon.this);
		popupMenu.inflate(R.menu.sorting_menu);
		popupMenu.show();
	
	}
	
	public void Trashbin (View view){
		startActivity(new Intent(BbListSermon.this, BbListTrashbin.class));
	}
	
	public void Settings (View view){
		//startActivity(new Intent(BbListSermon.this, SetApp.class));
	}
       
	@Override
	public boolean onMenuItemClick(MenuItem arg0) {
		switch (arg0.getItemId()) {
		
    	case R.id.menu_datei:
    		FillWithData(SQLiteHelper.S_DATE, "ASC");
			return true;

    	case R.id.menu_dateii:
    		FillWithData(SQLiteHelper.S_DATE, "DESC");
			return true;

    	case R.id.menu_titlei:
    		FillWithData(SQLiteHelper.S_TITLE, "ASC");
			return true;

    	case R.id.menu_titleii:
    		FillWithData(SQLiteHelper.S_TITLE, "DESC");
			return true;

    	case R.id.menu_placei:
    		FillWithData(SQLiteHelper.S_PLACE, "ASC");
			return true;

    	case R.id.menu_placeii:
    		FillWithData(SQLiteHelper.S_PLACE, "DESC");
			return true;

    	case R.id.menu_preacheri:
    		FillWithData(SQLiteHelper.S_PREACHER, "ASC");
			return true;

    	case R.id.menu_preacherii:
    		FillWithData(SQLiteHelper.S_PREACHER, "DESC");
			return true;

    	case R.id.menu_ascending:
    		FillWithData(SQLiteHelper.S_ID, "ASC");
			return true;

    	case R.id.menu_default:
    		FillWithData(SQLiteHelper.S_ID, "DESC");
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

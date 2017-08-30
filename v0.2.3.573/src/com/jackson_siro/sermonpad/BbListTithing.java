package com.jackson_siro.sermonpad;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.jackson_siro.sermonpad.tools.*;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.jackson_siro.sermonpad.adaptor.*;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;


public class BbListTithing extends ActionBarActivity implements OnMenuItemClickListener {
	ListView list;		
	private String[] My_Texti, My_Textii, My_Textiii, My_Textiv;
	
	SQLiteHelper db = new SQLiteHelper(this);
	List<Tithing> mylist;
	ArrayAdapter<String> myAdapter;

	ArrayList<HashMap<String, String>> myTithings;

	private static final int ACTIVITY_CREATE=0;
	public static final String TAG = "Tithings", SP_SETTINGS = "Sp_Settings", SP_TABLEOD = "DESC";
	TextView CurTithes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_tithing);
		CurTithes = (TextView)findViewById(R.id.tithingnow);
		
		changeStatusBarColor();
		
		FillWithData();				
	}
	
	private void FillWithData(){		
		mylist = db.getTithingList();
		List<String> listTithsid = new ArrayList<String>();
		List<String> listPosted = new ArrayList<String>();
		List<String> listAmount = new ArrayList<String>();
		List<String> listSource = new ArrayList<String>();
		
		for (int i = 0; i < mylist.size(); i++) {
			listTithsid.add(i, Integer.toString(mylist.get(i).getTithingid()));
			listPosted.add(i, mylist.get(i).getPosted());
			listAmount.add(i, Integer.toString(mylist.get(i).getAmount()) + "/=");
			listSource.add(i, mylist.get(i).getSource());
		}
		
		My_Texti = listTithsid.toArray(new String[listTithsid.size()]);		
		for (String stringi : My_Texti) {	System.out.println(stringi);	}

		My_Textii = listAmount.toArray(new String[listAmount.size()]);		
		for (String stringii : My_Textii) {	System.out.println(stringii);	}

		My_Textiii = listPosted.toArray(new String[listPosted.size()]);		
		for (String stringiii : My_Textiii) {	System.out.println(stringiii);	}
		
		My_Textiv = listSource.toArray(new String[listSource.size()]);		
		for (String stringiv : My_Textiv) {	System.out.println(stringiv);	}

		CustomTithingList adapter = new CustomTithingList(BbListTithing.this, 
				My_Texti, My_Textii, My_Textiii, My_Textiv);
		
		list=(ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
		
		SharedPreferences vSettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String currentTithes = vSettings.getString("sp_current_tithes", "0");
        CurTithes.setText("Tithing @ " + currentTithes + "/=");        
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        FillWithData();
    } 
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tithing_list, menu);
        
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		
        switch (item.getItemId()) {

	        case R.id.tithing:
	        	startActivityForResult(new Intent(this, CcTithingNew.class), ACTIVITY_CREATE); 
	            return true;
	            
	        case R.id.refresh:
	        	FillWithData();
	            return true;
	          
	        case R.id.delete:
	        	AlertDialog.Builder builder = new AlertDialog.Builder(BbListTithing.this);
				builder.setTitle("Just a minute...");
				builder.setMessage("This means you are paying your tithes right away? Are you sure you want to pay your tithes?");
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
						db.deleteAllTithings();
						unsetTithingAmount();
						FillWithData();	
								
					}
				});
				
				builder.show(); 
			    return true;
	           
            default:
                return false;
        }
    }
	
	public void unsetTithingAmount(){
    	SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
	    localEditor.putString("sp_current_tithes", "0");
	    localEditor.commit();
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

	@Override
	public boolean onMenuItemClick(MenuItem arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}

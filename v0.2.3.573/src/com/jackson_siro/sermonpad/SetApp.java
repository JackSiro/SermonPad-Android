package com.jackson_siro.sermonpad;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jackson_siro.sermonpad.adaptor.CustomList;

import android.annotation.SuppressLint;
import android.content.Intent;


public class SetApp extends ActionBarActivity {
	ListView list;
	String[] mytext = {"item_i", "item_ii", "item_iii", "item_iv"} ;
		
	private String[] mytexti;
	private String[] mytextii;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_sermons);
		mytexti = getResources().getStringArray(R.array.SetAppList);
		mytextii = getResources().getStringArray(R.array.SetAppListDesc);
		
		changeStatusBarColor();
		CustomList adapter = new CustomList(SetApp.this, mytext, mytexti, mytextii);
		list=(ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SetApp.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
            	if (mytext[+ position].equals("item_i")) {
        			Intent intent = new Intent(getBaseContext(), Settings.class);
        			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        			startActivity(intent);
        		}
        		
        		else if (mytext[+ position].equals("item_ii")) {
        			Intent intent = new Intent(getBaseContext(), Settings_I.class);
        			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        			startActivity(intent);
        		}
        		
        		else if (mytext[+ position].equals("item_iii")) {
        			Intent intent = new Intent(getBaseContext(), Settings_II.class);
        			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        			startActivity(intent);
        		}
            	
        		else if (mytext[+ position].equals("item_iv")) {
        			Toast.makeText(SetApp.this, R.string.sorry_text, Toast.LENGTH_SHORT).show();
        		}
        		
            }
        });		
				
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

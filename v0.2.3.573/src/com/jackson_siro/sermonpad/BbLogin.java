package com.jackson_siro.sermonpad;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class BbLogin extends ActionBarActivity {
	
	TextView Instruct;
	EditText PinNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bb_login);
		changeStatusBarColor();
		
		Instruct = (TextView) findViewById(R.id.instruct);
		PinNumber = (EditText) findViewById(R.id.tithespin);
		PinNumber.addTextChangedListener(pinWatcher);
		
		if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("sp_has_set_pin", false)) {
			Instruct.setText(R.string.please_set_pin);
			
		}
		
	}

    private final TextWatcher pinWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //textView.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 4) LogMeInNow();
        }

    };
    
    public void LogMeIn (View view){
    	
    	
    }
    
	public void LogMeInNow(){
		SharedPreferences vSettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String MyPin = PinNumber.getText().toString();
		
		if (MyPin.length() == 4) {
			if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("sp_has_set_pin", false)) {
				SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
			    localEditor.putBoolean("sp_has_set_pin", true);
			    localEditor.putString("sp_users_pin", MyPin);
			    localEditor.putLong("sp_has_set_pin_time", System.currentTimeMillis());
			    localEditor.commit();
			    startActivity(new Intent(BbLogin.this, BbListTithing.class));
			} else {
				String saved_pin = vSettings.getString("sp_users_pin", "0000");
		        if (MyPin.equals(saved_pin)) {
		        	startActivity(new Intent(BbLogin.this, BbListTithing.class));
		        } else {
		        	Instruct.setText(R.string.incorrect_pin);		        	
		        }
			}
		} else {
			Instruct.setText(R.string.very_short_pin);
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

package com.jackson_siro.sermonpad.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.jackson_siro.sermonpad.*;

public class AppStart extends AppCompatActivity {

    long ms = 0, splashTime = 1000;
    boolean splashActive = true, paused=false;
    SharedPreferences.Editor localEditor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_splash);
        localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        setFirstUse();

        Thread mythread = new Thread() {
            public void run() {
                try {
                    while (splashActive && ms < splashTime) {
                        if(!paused) ms=ms+100;
                        sleep(100);
                    }
                } catch(Exception e) {}
                finally { nextActivity(); }
            }
        };
        mythread.start();
    }

    public void setFirstUse() {
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("as_first_use", false)) {
            localEditor.putBoolean("as_first_use", true);
            localEditor.putLong("as_first_data", System.currentTimeMillis());
            localEditor.commit();
        }
    }

    public void nextActivity(){
        startActivity(new Intent(this, CcMain.class));
    }
}
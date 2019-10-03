package com.jackson_siro.sermonpad.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;
import com.jackson_siro.sermonpad.R;
import com.jackson_siro.sermonpad.SQLite.*;

public class CcAccount extends AppCompatActivity {

    SharedPreferences.Editor localEditor;
    Toolbar toolbar;
    FloatingActionButton fabbtn;
    TextView mGenderView, mLocationView, mDobirthView, mClassView, mHandleView;
    int gender = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cc_account);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppSQLite db = new AppSQLite(this, AppDatabase.DB_NAME, null, AppDatabase.DB_VERSION);

        mGenderView = (TextView) findViewById(R.id.gender);
        mLocationView = (TextView) findViewById(R.id.location);
        mDobirthView = (TextView) findViewById(R.id.dobirth);
        mClassView = (TextView) findViewById(R.id.sclass);
        mHandleView = (TextView) findViewById(R.id.handle);

        gender = PreferenceManager.getDefaultSharedPreferences(this).getInt("as_user_gender", 1);
        toolbar.setTitle(getOpt("as_user_firstname") + " " + getOpt("as_user_lastname"));
        setTitle(getOpt("as_user_firstname") + " " + getOpt("as_user_lastname"));

        String[] localities = getOpt("as_user_location").split("-");
        mGenderView.setText(gender == 1 ? R.string.string_male : R.string.string_female);
        mLocationView.setText(localities[1]);
        mDobirthView.setText(getOpt("as_user_dobirth"));
        //mClassView.setText(db.idToTitle(getOpt("as_user_class"), AppSQLite.C_TABLE));
        mHandleView.setText(getOpt("as_user_handle"));

        fabbtn = (FloatingActionButton) findViewById(R.id.fab);
        /*fabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public String getOpt(String optTag){
        return PreferenceManager.getDefaultSharedPreferences(this).getString(optTag, optTag);
    }

}

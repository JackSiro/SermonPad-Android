package com.jackson_siro.sermonpad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jackson_siro.sermonpad.R;
import com.jackson_siro.sermonpad.SQLite.*;

public class DdNoteInfo extends AppCompatActivity {
    AppSQLite db = new AppSQLite(this, AppDatabase.DB_NAME, null, AppDatabase.DB_VERSION);
    TextView mDetails, mDescription;
    FloatingActionButton mFabGet;
    Toolbar toolbar;
    SermonItem selectedPaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dd_online_view);
        toolbar = findViewById(R.id.toolbar);
        mFabGet = findViewById(R.id.fabbtn);
        mDetails = findViewById(R.id.details);
        mDescription = findViewById(R.id.description);

        setSupportActionBar(toolbar);

        mFabGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadSong();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int rid = intent.getIntExtra("exam_paper", -1);

        selectedPaper = db.readSermon(rid);
        setTitle(selectedPaper.getRtitle());
        /*mDetails.setText(
                db.idToTitle( selectedPaper.getRunit(), AppSQLite.U_TABLE)  + ", " +
                        db.idToTitle( selectedPaper.getRclass(), AppSQLite.C_TABLE)
        );*/
        mDescription.setText(selectedPaper.getRtitle() + "\n" + selectedPaper.getRcontent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dd_online_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                downloadSong();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void downloadSong(){
        //db.downloadSermon(selectedPaper);
        Toast.makeText(this, "The Paper: " + selectedPaper.getRtitle() + " download to your collection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

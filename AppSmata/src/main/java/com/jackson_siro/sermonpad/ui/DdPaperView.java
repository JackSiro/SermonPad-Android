package com.jackson_siro.sermonpad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jackson_siro.sermonpad.R;
import com.jackson_siro.sermonpad.SQLite.*;

public class DdPaperView extends AppCompatActivity {
    AppSQLite db = new AppSQLite(this, AppDatabase.DB_NAME, null, AppDatabase.DB_VERSION);
    TextView mDetails, mDescription;
    FloatingActionButton fabbtn;
    SermonItem selectedPaper;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dd_paper_view);
        toolbar = findViewById(R.id.toolbar);
        fabbtn = findViewById(R.id.fabbtn);
        mDetails = findViewById(R.id.details);
        mDescription = findViewById(R.id.description);

        setSupportActionBar(toolbar);

        fabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int rid = intent.getIntExtra("exam_paper", -1);

        /*selectedPaper = db.readExamPaper(rid);
        setTitle(selectedPaper.getRtitle());
        mDetails.setText(
                db.idToTitle( selectedPaper.getRunit(), AppSQLite.U_TABLE)  + ", " +
                db.idToTitle( selectedPaper.getRclass(), AppSQLite.C_TABLE)
        );
        mDescription.setText(selectedPaper.getRtitle() + "\n" + selectedPaper.getRcontent());*/

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}

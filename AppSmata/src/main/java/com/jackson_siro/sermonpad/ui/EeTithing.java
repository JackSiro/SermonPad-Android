package com.jackson_siro.sermonpad.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jackson_siro.sermonpad.*;
import com.jackson_siro.sermonpad.SQLite.*;
import com.jackson_siro.sermonpad.tools.*;
import com.jackson_siro.sermonpad.ui.Adapters.*;

import java.util.ArrayList;

public class EeTithing extends AppCompatActivity {
    AppFunctions asf = new AppFunctions();
    AppSQLite db = new AppSQLite(this, AppDatabase.DB_NAME, null, AppDatabase.DB_VERSION);
    FloatingActionButton mFabdone, mFabadd;
    ThitheItem selectedNote;

    TextView mTithingat;
    EditText mAmount, mSource, mPlace;
    int TithingAmount;
    String TithingSource, TithingPlace;
    LinearLayout mFormCount, mFormContent;
    ListView mFormList;
    ImageButton btnKeyshow, btnKeyhide, btnClear;
    Button mProceed;
    Animation animMoveup, animMovedown;
    ArrayList<String> NoteLines;
    AdapterNote noteAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ee_tithing);
        toolbar = findViewById(R.id.toolbar);
        mFormCount = findViewById(R.id.form_count);
        mFormList = findViewById(R.id.list_items);
        mFormContent = findViewById(R.id.form_content);

        mTithingat = findViewById(R.id.tithingat);
        mAmount = findViewById(R.id.tamount);
        mSource = findViewById(R.id.tsource);
        mPlace = findViewById(R.id.tplace);

        btnKeyshow = findViewById(R.id.btnkeyshow);
        btnKeyhide = findViewById(R.id.btnkeyhide);
        btnClear = findViewById(R.id.btnclear);

        mFabdone = findViewById(R.id.fabdone);
        mFabadd = findViewById(R.id.fabadd);

        animMoveup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up);
        animMovedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_down);

        NoteLines = new ArrayList<>();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPlace.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    if ( Integer.parseInt(mAmount.getText().toString()) > 1){
                        mFabadd.show();
                        mFabdone.hide();
                        saveTithing();
                    } else {
                        //Snackbar.make(getWindow().getDecorView(), R.string.nothing_toadd, Snackbar.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });

        btnKeyhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppFunctions.hideTheKeyboard(EeTithing.this);
                mFormContent.setVisibility(View.GONE);
                btnKeyhide.setVisibility(View.GONE);
                btnKeyshow.setVisibility(View.VISIBLE);
                mFabadd.show();
                mFabdone.hide();
            }
        });

        btnKeyshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppFunctions.showKeyboard(EeTithing.this, mAmount);
                btnKeyshow.setVisibility(View.GONE);
                btnKeyhide.setVisibility(View.VISIBLE);
                mAmount.requestFocus();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmount.setText("");
                mSource.setText("");
                mPlace.setText("");
            }
        });

        mFabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFormContent.setVisibility(View.VISIBLE);
                mFormContent.startAnimation(animMoveup);
                keyboardShow();
            }
        });

        mFabdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( Integer.parseInt(mAmount.getText().toString()) > 1){
                    mFabadd.show();
                    mFabdone.hide();
                    saveTithing();
                } else {
                    Snackbar.make(view, R.string.nothing_toadd, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mFormList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mAmount.addTextChangedListener(noteWatcher);
        mSource.addTextChangedListener(noteWatcher);
        mPlace.addTextChangedListener(noteWatcher);
    }

    public void keyboardShow(){
        btnKeyshow.setVisibility(View.GONE);
        btnKeyhide.setVisibility(View.VISIBLE);
        mAmount.requestFocus();
        AppFunctions.showKeyboard(EeTithing.this, mAmount);
    }

    private final TextWatcher noteWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        public void afterTextChanged(Editable newtext) {
            if (newtext.length() > 2){
                mFabadd.hide();
                mFabdone.show();
            }
        }

    };

    @Override
    public void onBackPressed() {
        finish();
    }

    private void saveTithing(){
        TithingAmount = Integer.parseInt(mAmount.getText().toString());
        TithingSource = mSource.getText().toString();
        TithingPlace = mPlace.getText().toString();

        mAmount.setText("");
        mSource.setText("");
        mPlace.setText("");

        db.createThitheItem(new ThitheItem(TithingAmount, TithingSource, TithingPlace, "1", "" + System.currentTimeMillis(), "", ""));

        mFormCount.startAnimation(animMovedown);
        mFormCount.setVisibility(View.VISIBLE);
        mFormContent.setVisibility(View.VISIBLE);
        mFormContent.startAnimation(animMoveup);
        mFabdone.startAnimation(animMoveup);

        mFabdone.hide();
        mFabadd.show();
        mAmount.requestFocus();

        /*final AppListNote noteAppend = new AppListNote(TithingContent, "Just now");
        noteAdapter.add(noteAppend);
        mFormList.setSelection(mFormList.getCount() - 1);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ee_tithing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.action_discard:
                finish();
                break;
            case R.id.action_archive:
                Snackbar.make(this.findViewById(R.id.action_archive), R.string.feature_unavailable, Snackbar.LENGTH_LONG)
                        .show();
                break;
            case R.id.action_delete:
                DeleteThis();
                break;
            case R.id.action_infor:

                break;
        }*/

        return super.onOptionsItemSelected(item);
    }

}

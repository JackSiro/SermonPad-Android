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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jackson_siro.sermonpad.*;
import com.jackson_siro.sermonpad.SQLite.*;
import com.jackson_siro.sermonpad.tools.*;
import com.jackson_siro.sermonpad.ui.Adapters.*;

import java.util.ArrayList;

public class DdNotePad extends AppCompatActivity {
    AppFunctions asf = new AppFunctions();
    AppSQLite db = new AppSQLite(this, AppDatabase.DB_NAME, null, AppDatabase.DB_VERSION);
    FloatingActionButton mFabdone, mFabsave, mFabwrite;
    int note_action, sermon_item;
    SermonItem selectedNote;

    EditText mTitle, mPreacher, mPlace, mContent;
    String SermonTitle, SermonPreacher, SermonPlace, SermonContent, SubNote;
    ScrollView mFormTitle;
    LinearLayout mFormContent;
    TableRow mFormRecorder;
    ListView mFormList;
    ImageButton btnKeyshow, btnKeyhide, btnUndo, btnRedo, btnBold, btnItalic, btnUnderline, btnCopy, btnSelect, btnFormat, btnFile, btnPaste, btnClear;
    Button mProceed;
    Animation animMoveup, animMovedown;
    ArrayList<String> NoteLines;
    AdapterNote noteAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dd_note_pad);
        toolbar = findViewById(R.id.toolbar);
        mFormTitle = findViewById(R.id.form_title);
        mFormRecorder = findViewById(R.id.form_recorder);
        mFormList = findViewById(R.id.list_items);
        mFormContent = findViewById(R.id.form_content);

        mTitle = findViewById(R.id.ntitle);
        mPreacher = findViewById(R.id.npreacher);
        mPlace = findViewById(R.id.nplace);
        mContent = findViewById(R.id.ncontent);

        btnKeyshow = findViewById(R.id.btnkeyshow);
        btnKeyhide = findViewById(R.id.btnkeyhide);
        btnUndo = findViewById(R.id.btnundo);
        btnRedo = findViewById(R.id.btnredo);
        btnBold = findViewById(R.id.btnbold);
        btnItalic = findViewById(R.id.btnitalic);
        btnUnderline = findViewById(R.id.btnunderline);
        btnCopy = findViewById(R.id.btncopy);
        btnSelect = findViewById(R.id.btnselect);
        btnFile = findViewById(R.id.btnfile);
        btnPaste = findViewById(R.id.btnpaste);
        btnClear = findViewById(R.id.btnclear);
        btnFormat = findViewById(R.id.btnformat);

        mProceed = findViewById(R.id.proceed_btn);
        mFabdone = findViewById(R.id.fabdone);
        mFabsave = findViewById(R.id.fabsave);
        mFabwrite = findViewById(R.id.fabwrite);

        animMoveup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up);
        animMovedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_down);

        NoteLines = new ArrayList<>();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { saveNewNote(); }
        });

        mPlace.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    saveNewNote();
                    return true;
                }
                return false;
            }
        });

        mContent.addTextChangedListener(noteWatcher);

        btnKeyhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppFunctions.hideTheKeyboard(DdNotePad.this);
                mContent.setVisibility(View.GONE);
                btnKeyhide.setVisibility(View.GONE);
                btnKeyshow.setVisibility(View.VISIBLE);

                mFabwrite.hide();
                mFabsave.hide();
                mFabdone.show();
            }
        });

        btnKeyshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppFunctions.showKeyboard(DdNotePad.this, mContent);
                mContent.setVisibility(View.VISIBLE);
                btnKeyshow.setVisibility(View.GONE);
                btnKeyhide.setVisibility(View.VISIBLE);
                mContent.requestFocus();
            }
        });

        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.feature_unavailable, Snackbar.LENGTH_LONG).show();
            }
        });

        btnRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.feature_unavailable, Snackbar.LENGTH_LONG).show();
            }
        });

        btnBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.feature_unavailable, Snackbar.LENGTH_LONG).show();
            }
        });

        btnItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.feature_unavailable, Snackbar.LENGTH_LONG).show();
            }
        });

        btnUnderline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.feature_unavailable, Snackbar.LENGTH_LONG).show();
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.feature_unavailable, Snackbar.LENGTH_LONG).show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.feature_unavailable, Snackbar.LENGTH_LONG).show();
            }
        });

        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubNote = mContent.getText().toString();
                mContent.setText("");
                if (TextUtils.isEmpty(SubNote))mContent.setText(asf.pasteText(DdNotePad.this));
                else mContent.setText((SubNote + "\n" + asf.pasteText(DdNotePad.this)));
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContent.getText().toString().length() > 5) {
                    Snackbar.make(view, R.string.sub_note_cleared, Snackbar.LENGTH_LONG).show();
                    mContent.setText("");
                } else {
                    Snackbar.make(view, R.string.nothing_toclear, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.feature_unavailable, Snackbar.LENGTH_LONG).show();
            }
        });

        mFabwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyboardShow();
            }
        });

        mFabdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( mContent.getText().toString().length() > 2){
                    mFabwrite.hide();
                    mFabdone.hide();
                    mFabsave.show();
                    saveSubNote();
                } else {
                    Snackbar.make(view, R.string.nothing_toadd, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mFabsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabdone.hide();
                mFabsave.hide();
                mFabwrite.show();
            }
        });

        mFormList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        Intent intent = getIntent();
        note_action = intent.getIntExtra("editor_act", -1);
        sermon_item = intent.getIntExtra("sermon_item", -1);
        getNoteView();
    }

    public void keyboardShow(){
        mContent.setVisibility(View.VISIBLE);
        btnKeyshow.setVisibility(View.GONE);
        btnKeyhide.setVisibility(View.VISIBLE);
        mContent.requestFocus();
        AppFunctions.showKeyboard(DdNotePad.this, mContent);
    }

    private final TextWatcher noteWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        public void afterTextChanged(Editable newtext) {
            if (newtext.length() > 2){
                mFabwrite.hide();
                mFabsave.hide();
                mFabdone.show();
            }
        }

    };

    @Override
    public void onBackPressed() {
        finish();
    }

    public void getNoteView(){
        switch (note_action) {
            case 2:
                openThisSermon(sermon_item);
                break;

            default:
                setTitle(R.string.save_new_note);
                break;
        }

        noteAdapter = new AdapterNote(this);
        mFormList.setAdapter(noteAdapter);
    }

    private void openThisSermon(int semonid){
        selectedNote = db.readSermon(semonid);

        SermonTitle = selectedNote.getRtitle();
        SermonPreacher = selectedNote.getRpreacher();
        SermonPlace = selectedNote.getRplace();
        SermonContent = selectedNote.getRcontent();
        sermonView(SermonContent);

        if (SermonTitle.length() > 5) {
            setTitle(SermonTitle);
            toolbar.setSubtitle(((SermonPreacher.length() > 3) ? SermonPreacher : "") +
                    ((SermonPlace.length() > 3) ? ", " + SermonPlace : ""));
        }
        else setTitle(R.string.untitle_sermon);

        mTitle.setText(SermonTitle);
        mPreacher.setText(SermonPreacher);
        mPlace.setText(SermonPlace);

        mFormTitle.startAnimation(animMovedown);
        mFormTitle.setVisibility(View.GONE);
        mFormList.setVisibility(View.VISIBLE);

        mFabwrite.show();
        mFabwrite.startAnimation(animMoveup);
        mFabsave.hide();
        mFabdone.hide();
    }

    public void sermonView(String sermon_text){
        if (TextUtils.isEmpty(sermon_text)) {

        } else {
            if (sermon_text.contains("//")) {
                String[] NEW_CONTENT = sermon_text.split("//");
                for (String strContent : NEW_CONTENT) {
                    String[] texts = strContent.split("%%");
                    final AppListNote noteAppend = new AppListNote(texts[0], "Just now");
                    noteAdapter.add(noteAppend);
                    mFormList.setSelection(mFormList.getCount() - 1);
                }
            } else {

            }
        }
    }

    public void saveNewNote(){
        SermonTitle = mTitle.getText().toString();
        SermonPreacher = mPreacher.getText().toString();
        SermonPlace = mPlace.getText().toString();

        mTitle.setText("");
        mPreacher.setText("");
        mPlace.setText("");
        if (SermonTitle.length() > 5) {
            setTitle(SermonTitle);
            toolbar.setSubtitle(((SermonPreacher.length() > 3) ? SermonPreacher : "") +
                    ((SermonPlace.length() > 3) ? ", " + SermonPlace : ""));
        }
        else setTitle(R.string.untitle_sermon);

        db.createSermon(new SermonItem(SermonTitle, SermonPreacher, SermonPlace,
                "", "", "", "1", "" + System.currentTimeMillis(), "", ""));

        mFormTitle.startAnimation(animMovedown);
        mFormTitle.setVisibility(View.GONE);
        mFormContent.setVisibility(View.VISIBLE);
        mFormContent.startAnimation(animMoveup);
        mFabdone.startAnimation(animMoveup);

        mFabsave.hide();
        mFabdone.show();
        mContent.requestFocus();
        keyboardShow();
        selectedNote = db.readSermon(db.lastInsertId());
    }

    public void saveSubNote(){
        SermonTitle = mTitle.getText().toString();
        SermonPreacher = mPreacher.getText().toString();
        SermonPlace = mPlace.getText().toString();
        SermonContent = mContent.getText().toString();

        selectedNote.setRupdated(""+System.currentTimeMillis());
        selectedNote.setRtitle(SermonTitle);
        selectedNote.setRpreacher(SermonPreacher);
        selectedNote.setRplace(SermonPlace);
        selectedNote.setRcontent(SermonContent);

        db.updateSermon(selectedNote);
        mContent.setText("");
        final AppListNote noteAppend = new AppListNote(SermonContent, "Just now");
        noteAdapter.add(noteAppend);
        mFormList.setSelection(mFormList.getCount() - 1);
    }

    private void DeleteThis(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Just a minute...");
        builder.setMessage("Are you sure you want to delete the note: " + selectedNote.getRtitle() + "? This action is not reversable.");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getApplicationContext(), "You have deleted the note: " + selectedNote.getRtitle(), Toast.LENGTH_LONG).show();
                db.deleteSermon(selectedNote);
                startActivity(new Intent(DdNotePad.this, CcMain.class));
                finish();
            }
        });

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.dd_note_pad, menu);
        MenuItem itemDiscard = menu.findItem(R.id.action_discard);
        MenuItem itemDelete = menu.findItem(R.id.action_delete);
        MenuItem itemArchive = menu.findItem(R.id.action_archive);

       switch (note_action) {
            case 2:
                itemDelete.setVisible(true);
                itemArchive.setVisible(true);
                break;

            default:
                itemDiscard.setVisible(true);
                break;
        }
        //this.invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        }

        return super.onOptionsItemSelected(item);
    }

}

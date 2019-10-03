package com.jackson_siro.sermonpad.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.app.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jackson_siro.sermonpad.R;
import com.jackson_siro.sermonpad.SQLite.*;
import com.jackson_siro.sermonpad.ui.Adapters.*;

public class BbSignup extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "0711474787:1397"
    };

    private UserSignupTask mAuthTask = null;

    EditText mFirstnameView, mLastnameView, mHandleView, mPasswordView;
    TextView mDobirthView;
    Spinner mLocationView, mClassView;
    View mProgressView, mSignupFormView;
    Button mSignupButton, mSigninButton;
    DatePicker mDatePickerView;

    int gender = 1;
    private String[] My_Text, My_Texti;

    private DatePickerDialog datePickerDialog;
    SharedPreferences.Editor localEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bb_signup);
        localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();

        mFirstnameView = findViewById(R.id.firstname);
        mLastnameView = findViewById(R.id.lastname);
        mLocationView = findViewById(R.id.location);
        mDobirthView = findViewById(R.id.dobirth);
        mDatePickerView = findViewById(R.id.dpResult);
        mClassView = findViewById(R.id.sclass);
        mHandleView = findViewById(R.id.handle);
        mPasswordView = findViewById(R.id.password);
        mSignupButton = findViewById(R.id.signup_btn);
        mSigninButton = findViewById(R.id.signin_btn);
        mSignupFormView = findViewById(R.id.main_form);
        mProgressView = findViewById(R.id.main_progress);

        FillSpinnerWithData();

        mDobirthView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(BbSignup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        mDobirthView.setText(year + "/" + (month + 1) + "/" + day);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptSignup();
                    return true;
                }
                return false;
            }
        });

        mSignupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
            }
        });

        mSigninButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignin();
            }
        });
    }

    public void onGenderSelect(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.radioFemale:
                if (checked) gender = 2;
                break;
            default:
                gender = 1;
                break;
        }
    }

    private void FillSpinnerWithData(){

    }

    private void gotoSignin(){
        startActivity(new Intent(this, BbSignin.class));
    }

    private void attemptSignup() {
        if (mAuthTask != null) return;
        mFirstnameView.setError(null);
        mLastnameView.setError(null);
        mHandleView.setError(null);
        mPasswordView.setError(null);

        String firstname = mFirstnameView.getText().toString();
        String lastname = mLastnameView.getText().toString();
        String location = mLocationView.getSelectedItem().toString();
        String dobirth = mDobirthView.getText().toString();
        String sclass = My_Text[Integer.parseInt(mClassView.getSelectedItem().toString())];
        String handle = mHandleView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(firstname)) {
            mFirstnameView.setError(getString(R.string.error_field_required));
            focusView = mFirstnameView;
            cancel = true;
        } else if (!isNameValid(firstname)) {
            mFirstnameView.setError(getString(R.string.error_invalid_input));
            focusView = mFirstnameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(lastname)) {
            mLastnameView.setError(getString(R.string.error_field_required));
            focusView = mLastnameView;
            cancel = true;
        } else if (!isNameValid(lastname)) {
            mLastnameView.setError(getString(R.string.error_invalid_input));
            focusView = mLastnameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(handle)) {
            mHandleView.setError(getString(R.string.error_field_required));
            focusView = mHandleView;
            cancel = true;
        } else if (!isHandleValid(handle)) {
            mHandleView.setError(getString(R.string.error_invalid_mobile));
            focusView = mHandleView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserSignupTask(firstname, lastname, location, dobirth, sclass, handle, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isNameValid(String handle) {
        return handle.length() > 2;
    }

    private boolean isHandleValid(String handle) {
        return handle.length() > 9;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignupFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) { }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) { }

    public class UserSignupTask extends AsyncTask<Void, Void, Boolean> {

        private final String mFirstname, mLastname, mLocation, mDobirth, mClass, mHandle, mPassword;

        UserSignupTask(String firstname, String lastname, String location, String dobirth, String sclass, String handle, String password) {
            mFirstname = firstname;
            mLastname = lastname;
            mLocation = location;
            mDobirth = dobirth;
            mClass = sclass;
            mHandle = handle;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mHandle)) {
                    return pieces[1].equals(mPassword);
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                setLoggedIn(mFirstname, mLastname, mLocation, mDobirth, mClass, mHandle);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void setLoggedIn(String mFirstname, String mLastname, String mLocation, String mDobirth, String mClass, String mHandle){
        localEditor.putString("as_user_firstname", mFirstname);
        localEditor.putString("as_user_lastname", mLastname);
        localEditor.putString("as_user_location", mLocation);
        localEditor.putString("as_user_dobirth", mDobirth);
        localEditor.putString("as_user_class", mClass);
        localEditor.putString("as_user_handle", mHandle);
        localEditor.putInt("as_user_gender", gender);
        localEditor.putBoolean("as_signed_in", true);
        localEditor.commit();
        startActivity(new Intent(this, CcMain.class));
        finish();
    }
}
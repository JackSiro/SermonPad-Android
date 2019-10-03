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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jackson_siro.sermonpad.R;

public class BbSignin extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "0711474787:1397", "0712345678:0987"
    };
    
    private UserSigninTask mAuthTask = null;

    private EditText mHandleView, mPasswordView;
    private View mProgressView, mSigninFormView;
    private Button mSigninButton, mSignupButton;
    SharedPreferences.Editor localEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bb_signin);

        localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();

        mHandleView = findViewById(R.id.handle);
        mPasswordView = findViewById(R.id.password);
        mSigninButton = findViewById(R.id.signin_btn);
        mSignupButton = findViewById(R.id.signup_btn);
        mSigninFormView = findViewById(R.id.main_form);
        mProgressView = findViewById(R.id.main_progress);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptSignin();
                return true;
            }
            return false;
            }
        });

        mSigninButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignin();
            }
        });

        mSignupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignup();
            }
        });
    }

    private void gotoSignup(){
        startActivity(new Intent(this, BbSignup.class));
    }

    private void attemptSignin() {
        if (mAuthTask != null) return;
        mHandleView.setError(null);
        mPasswordView.setError(null);

        String handle = mHandleView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
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
            mAuthTask = new UserSigninTask(handle, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isHandleValid(String handle) {
        return handle.length() > 9;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSigninFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSigninFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSigninFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSigninFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    public class UserSigninTask extends AsyncTask<Void, Void, Boolean> {

        private final String mHandle, mPassword;

        UserSigninTask(String handle, String password) {
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
                setLoggedIn(mHandle);
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

    private void setLoggedIn(String mHandle){
        localEditor.putString("as_user_firstname", "Demo");
        localEditor.putString("as_user_lastname", "User");
        localEditor.putString("as_user_location", "Nairobi");
        localEditor.putString("as_user_dobirth", "2007/07/07");
        localEditor.putString("as_user_class", "2");
        localEditor.putString("as_user_handle", mHandle);
        localEditor.putInt("as_user_gender", 1);
        localEditor.putBoolean("as_signed_in", true);
        localEditor.commit();
        startActivity(new Intent(this, CcMain.class));
        finish();
    }
}


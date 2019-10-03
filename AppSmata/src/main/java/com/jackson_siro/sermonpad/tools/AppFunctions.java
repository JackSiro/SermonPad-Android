package com.jackson_siro.sermonpad.tools;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jackson_siro.sermonpad.R;
import com.jackson_siro.sermonpad.ui.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppFunctions extends Activity {

    public String getOpt(String optTag){
        return PreferenceManager.getDefaultSharedPreferences(this).getString(optTag, optTag);
    }

    public String datetimeStr(String dateValue){
        long timediff = System.currentTimeMillis() - Long.parseLong(dateValue);
        int seconds = ((int)timediff) / 1000;
        Date strDateTime = new Date(Long.parseLong(dateValue));
        String finaldatetime = "";
        /*if (seconds < 86400) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            finaldatetime = formatter.format(strDateTime);
        } else */

        if (seconds < 172800) {
            finaldatetime = "Yesterday";
        } else if (seconds < 604800) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            finaldatetime = formatter.format(strDateTime);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            finaldatetime = formatter.format(strDateTime);
        }
        return finaldatetime;
    }

    public String datetimeNow(){
        long msTime = System.currentTimeMillis();
        Date curDateTime = new Date(msTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(curDateTime);
    }

    public String datetimeAgo(String dateValue){
        long msTime = System.currentTimeMillis();
        Date curDateTime = new Date(msTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'M':'s");
        return formatter.format(curDateTime);
    }

    public final boolean isInternetOn() {
        ConnectivityManager connec =  (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            return true;
        } else if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            return false;
        }
        return false;
    }

    public void setLoggedIn(String userAccountInfo){
        String[] strUser = userAccountInfo.split(":");
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        localEditor.putString("as_user_firstname", strUser[0]);
        localEditor.putString("as_user_lastname", strUser[0]);
        localEditor.putString("as_user_location", strUser[0]);
        localEditor.putString("as_user_dobirth", strUser[0]);
        localEditor.putString("as_user_class", strUser[0]);
        localEditor.putString("as_user_handle", strUser[0]);
        localEditor.putInt("as_user_gender", Integer.parseInt(strUser[0]));
        localEditor.putBoolean("as_signed_in", true);
        localEditor.commit();
        startActivity(new Intent(this, CcMain.class));
        finish();
    }

    public static void showKeyboard(Activity activity, View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public boolean containsThis(String myString, String characterToFind) {
        return myString.contains(characterToFind);
    }

    public static void hideKeyboard(Activity activity, View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            imm.showSoftInput(view, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static void hideTheKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void copyText(String textToCopy){
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", textToCopy);
        clipboard.setPrimaryClip(clip);
    }

    public String pasteText(Activity activity){
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip()) {
            ClipData data = clipboard.getPrimaryClip();
            ClipDescription description = clipboard.getPrimaryClipDescription();
            if (data != null && description != null && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                return String.valueOf(data.getItemAt(0).getText());
        }
        return null;
    }

}

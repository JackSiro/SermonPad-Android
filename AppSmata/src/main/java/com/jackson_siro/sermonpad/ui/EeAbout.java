package com.jackson_siro.sermonpad.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jackson_siro.sermonpad.*;

public class EeAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ee_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        TextView thisApplication = findViewById(R.id.thisapp);
        String appname = thisApplication.getText().toString();
        setTitle(getString(R.string.about_semonpad, appname));
        toolbar.setSubtitle(R.string.app_name);
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            toolbar.setSubtitle(appname + " v" + pInfo.versionCode);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView aboutTitle1 = findViewById(R.id.abouttitle1);
        aboutTitle1.setText(getString(R.string.dear_appuser, appname));

        CardView appOneCard = findViewById(R.id.appone);
        appOneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppOneDialog();
            }
        });

        CardView appTwoCard = findViewById(R.id.apptwo);
        appTwoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppTwoDialog();
            }
        });
    }

    public void AppOneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.appone_install);
        builder.setMessage(R.string.appone_description);

        builder.setNegativeButton(R.string.install_later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        builder.setPositiveButton(R.string.install_now, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                installAppone();
            }
        });

        builder.show();
    }

    public void installAppone(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.jackson_siro.visongbook")));
    }

    public void AppTwoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.apptwo_install);
        builder.setMessage(R.string.apptwo_description);

        builder.setNegativeButton(R.string.install_later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        builder.setPositiveButton(R.string.install_now, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                installApptwo();
            }
        });

        builder.show();
    }

    public void installApptwo(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.jackson_siro.qotday")));
    }
}

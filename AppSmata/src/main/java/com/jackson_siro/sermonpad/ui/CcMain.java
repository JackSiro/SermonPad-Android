package com.jackson_siro.sermonpad.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jackson_siro.sermonpad.R;
import com.jackson_siro.sermonpad.ui.Adapters.*;

public class CcMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    View navHeader;
    Toolbar toolbar;
    SharedPreferences.Editor localEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cc_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHeader = navigationView.getHeaderView(0);

        Fragment fragment = new CcMainFragHome();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.cc_main_frame, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cc_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                onSearchRequested();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;

        switch (item.getItemId()){
            case R.id.nav_draft:
                toolbar.setTitle(R.string.app_drafts);
                fragment = new CcMainFragDrafts();
                break;

            case R.id.nav_archive:
                toolbar.setTitle(R.string.app_archive);
                fragment = new CcMainFragArchive();
                break;

            case R.id.nav_trash:
                toolbar.setTitle(R.string.app_trashbin);
                fragment = new CcMainFragTrash();
                break;

            case R.id.nav_tithes:
                toolbar.setTitle(R.string.app_name);
                fragment = new CcMainFragHome();
                startActivity(new Intent(this, EeTithing.class));
                break;

            case R.id.nav_account:
                toolbar.setTitle(R.string.app_name);
                fragment = new CcMainFragHome();
                break;

            case R.id.nav_help:
                toolbar.setTitle(R.string.app_name);
                fragment = new CcMainFragHome();
                break;

            case R.id.nav_manage:
                toolbar.setTitle(R.string.app_name);
                fragment = new CcMainFragHome();
                startActivity(new Intent(this, EeSettings.class));
                break;

            case R.id.nav_about:
                toolbar.setTitle(R.string.app_name);
                fragment = new CcMainFragHome();
                startActivity(new Intent(this, EeAbout.class));
                break;

            case R.id.nav_signout:
                toolbar.setTitle(R.string.app_name);
                fragment = new CcMainFragHome();
                //signoutDialog();
                break;

            default:
                toolbar.setTitle(R.string.app_name);
                fragment = new CcMainFragHome();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.cc_main_frame, fragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }

    public void signoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CcMain.this);
        builder.setTitle(R.string.just_amin);
        builder.setMessage(R.string.sign_you_out);

        builder.setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                SignInActivity();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        builder.show();
    }

    public void SignInActivity()
    {
        localEditor.putString("as_user_firstname", "");
        localEditor.putString("as_user_lastname", "");
        localEditor.putString("as_user_location", "");
        localEditor.putString("as_user_dobirth", "");
        localEditor.putString("as_user_class", "");
        localEditor.putString("as_user_handle", "");
        localEditor.putBoolean("as_signed_in", false);
        localEditor.commit();
        startActivity(new Intent(this, BbSignin.class));
        finish();
    }

}

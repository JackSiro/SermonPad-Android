package com.jackson_siro.sermonpad.SQLite;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class AppProvider extends ContentProvider {
    AppDatabase mAppDatabase = null;
    public static final String AUTHORITY = "com.jackson_siro.sermonpad.SQLite.AppProvider";
    public static final String DBSTR = AppDatabase.DB_NAME;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DBSTR );
    private static final int SUGGESTIONS_ITEM = 1, SEARCH_ITEM = 2, GET_ITEM = 3;
    UriMatcher mUriMatcher = buildUriMatcher();

    private UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Suggestion items of Search Dialog is provided by this uri
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY,SUGGESTIONS_ITEM);

        // This URI is invoked, when user presses "Go" in the Keyboard of Search Dialog
        // Listview items of SearchableActivity is provided by this uri
        // See android:searchSuggestIntentData="content://in.wptrafficanalyzer.searchdialogdemo.provider/countries" of searchable.xml
        uriMatcher.addURI(AUTHORITY, DBSTR, SEARCH_ITEM);

        // This URI is invoked, when user selects a suggestion from search dialog or an item from the listview
        // Item details for ItemActivity is provided by this uri
        // See, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID in ItemDB.java
        uriMatcher.addURI(AUTHORITY, DBSTR + "/#", GET_ITEM);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        mAppDatabase = new AppDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor c = null;
        switch(mUriMatcher.match(uri)){
            case SUGGESTIONS_ITEM :
                c = mAppDatabase.getItems(selectionArgs);
                break;
            case SEARCH_ITEM :
                c = mAppDatabase.getItems(selectionArgs);
                break;
            case GET_ITEM :
                String id = uri.getLastPathSegment();
                c = mAppDatabase.getItem(id);
        }

        return c;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
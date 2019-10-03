package com.jackson_siro.sermonpad.SQLite;

import java.util.HashMap;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

public class AppDatabase {

    private AppSQLite appDB;
    public static final int DB_VERSION = 1;

    public static final String DB_NAME = "SermonPadDB", C_TABLE = "ExamClasses", U_TABLE = "ExamUnits", P_TABLE = "ExamPapers", Q_TABLE = "ExamQuestions";
    public static final String R_ID = "rid", R_TITLE = "rtitle", R_CONTENT = "rcontent", R_ICON = "ricon";

    private HashMap<String, String> mAliasMap;

    public AppDatabase(Context context){
        appDB = new AppSQLite(context, DB_NAME, null, DB_VERSION);

        mAliasMap = new HashMap<String, String>();
        mAliasMap.put("_ID", R_ID + " AS " + "_id" );
        //mAliasMap.put( SearchManager.SUGGEST_COLUMN_ICON_1, R_ICON + " AS " + SearchManager.SUGGEST_COLUMN_ICON_1);
        mAliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_1, R_TITLE + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        mAliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_2, R_CONTENT + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_2);
        mAliasMap.put( SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, R_ID + " AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID );

    }

    public Cursor getItems(String[] selectionArgs){
        String selection = R_TITLE + " LIKE ? ";
        if(selectionArgs!=null)
            selectionArgs[0] = "%"+selectionArgs[0] + "%";

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setProjectionMap(mAliasMap);
        queryBuilder.setTables(P_TABLE);

        Cursor c = queryBuilder.query(
                appDB.getReadableDatabase(),
                new String[] { "_ID",
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_TEXT_2,
                        //SearchManager.SUGGEST_COLUMN_ICON_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
                },
                selection,
                selectionArgs,
                null,
                null,
                R_TITLE + " ASC ","10"
        );
        return c;

    }

    public Cursor getItem(String id){

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(P_TABLE);

        Cursor c = queryBuilder.query(appDB.getReadableDatabase(),
                new String[] {
                        R_ID,
                        //R_ICON,
                        R_TITLE,
                        R_CONTENT
                } ,
                R_ID + " = ?", new String[] { id } , null, null, null ,"1"
        );

        return c;
    }

}
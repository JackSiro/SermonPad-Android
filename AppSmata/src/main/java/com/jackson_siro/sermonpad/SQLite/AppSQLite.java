package com.jackson_siro.sermonpad.SQLite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AppSQLite extends SQLiteOpenHelper{
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "SermonPadDB", S_TABLE = "SermonItems", T_TABLE = "ThitheItems";
    public static final String R_ID = "rid", R_TITLE = "rtitle", R_PREACHER = "rpreacher", R_PLACE = "rplace", R_CONTENT = "rcontent", R_AUDIOS = "raudios", R_STATE = "rstate", R_CREATED = "rcreated", R_UPDATED = "rupdated", R_ACCESSED = "raccessed", R_FILES = "rfiles", R_EXTRA = "rextra", R_AMOUNT = "ramount", R_SOURCE = "rsource";

    public static final String[] S_COLUMNS = { R_ID, R_TITLE, R_PREACHER, R_PLACE, R_CONTENT, R_AUDIOS, R_FILES, R_STATE, R_CREATED, R_UPDATED, R_ACCESSED };

    public static final String[] T_COLUMNS = { R_ID, R_AMOUNT, R_SOURCE, R_PLACE, R_STATE, R_CREATED, R_UPDATED, R_ACCESSED };

    public AppSQLite(Context context, String name, CursorFactory factory, int version ) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + S_TABLE + "(" + R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + R_TITLE + " VARCHAR, " +  R_PREACHER + " VARCHAR, " + R_PLACE + " VARCHAR, " + R_CONTENT + " VARCHAR, " + R_AUDIOS + " VARCHAR, " + R_FILES + " VARCHAR, " + R_STATE + " VARCHAR, " + R_CREATED + " VARCHAR, " + R_UPDATED + " VARCHAR, " + R_ACCESSED + " VARCHAR)");

        db.execSQL("CREATE TABLE " + T_TABLE + "(" + R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + R_AMOUNT + " VARCHAR, " + R_SOURCE + " VARCHAR, "  + R_PLACE + " VARCHAR, " + R_STATE + " VARCHAR, " + R_CREATED + " VARCHAR, " + R_UPDATED + " VARCHAR, " + R_ACCESSED + " VARCHAR)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + S_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + T_TABLE);

        this.onCreate(db);
    }

    public String idToTitle(String rID, String rTable) {
        String query = "SELECT " + R_TITLE + " FROM " + rTable + " WHERE " + R_ID + "=" + rID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) cursor.moveToFirst();
        db.close();
        return cursor.getString(0);
    }

    //PAPERS MANAGEMENT	BEGIN
    public void createSermon(SermonItem esermon) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(R_TITLE, esermon.getRtitle());
        values.put(R_PREACHER, esermon.getRpreacher());
        values.put(R_PLACE, esermon.getRplace());
        values.put(R_CONTENT, esermon.getRcontent());
        values.put(R_AUDIOS, esermon.getRaudios());
        values.put(R_FILES, esermon.getRfiles());
        values.put(R_STATE, esermon.getRstate());
        values.put(R_CREATED, esermon.getRcreated());
        values.put(R_UPDATED, esermon.getRupdated());
        values.put(R_ACCESSED, esermon.getRaccessed());

        db.insert(S_TABLE, null, values);
        db.close();
    }

    public int lastInsertId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(S_TABLE, S_COLUMNS, null, null, null, null, null);
        cursor.moveToLast();
        return Integer.parseInt(cursor.getString(0));
    }

    public SermonItem readSermon(int rid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(S_TABLE, S_COLUMNS, R_ID + " = ?", new String[] { String.valueOf(rid) }, null, null, null, null);

        if (cursor != null) cursor.moveToFirst();

        SermonItem esermon = new SermonItem();
        esermon.setRid(Integer.parseInt(cursor.getString(0)));
        esermon.setRtitle(cursor.getString(1));
        esermon.setRpreacher(cursor.getString(2));
        esermon.setRplace(cursor.getString(3));
        esermon.setRcontent(cursor.getString(4));
        esermon.setRaudios(cursor.getString(5));
        esermon.setRfiles(cursor.getString(6));
        esermon.setRstate(cursor.getString(7));
        esermon.setRcreated(cursor.getString(8));
        esermon.setRupdated(cursor.getString(9));
        esermon.setRaccessed(cursor.getString(10));

        db.close();
        return esermon;
    }

    public List<SermonItem> listSermon(int rstate) {
        List<SermonItem> sermonList = new LinkedList<>();
        String query = "SELECT * FROM " + S_TABLE + " WHERE " + R_STATE + "=" + rstate + " ORDER BY " + R_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        SermonItem esermon = null;
        if (cursor.moveToFirst()) {
            do {
                esermon = new SermonItem();
                esermon.setRid(Integer.parseInt(cursor.getString(0)));
                esermon.setRtitle(cursor.getString(1));
                esermon.setRpreacher(cursor.getString(2));
                esermon.setRplace(cursor.getString(3));
                esermon.setRcontent(cursor.getString(4));
                esermon.setRaudios(cursor.getString(5));
                esermon.setRfiles(cursor.getString(6));
                esermon.setRstate(cursor.getString(7));
                esermon.setRcreated(cursor.getString(8));
                esermon.setRupdated(cursor.getString(9));
                esermon.setRaccessed(cursor.getString(10));

                sermonList.add(esermon);
            } while (cursor.moveToNext());
        }
        db.close();
        return sermonList;
    }

    public void updateSermon(SermonItem esermon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(R_TITLE, esermon.getRtitle());
        values.put(R_PREACHER, esermon.getRpreacher());
        values.put(R_PLACE, esermon.getRplace());
        values.put(R_CONTENT, esermon.getRcontent());
        values.put(R_AUDIOS, esermon.getRaudios());
        values.put(R_FILES, esermon.getRfiles());
        values.put(R_STATE, esermon.getRstate());
        values.put(R_CREATED, esermon.getRcreated());
        values.put(R_UPDATED, esermon.getRupdated());
        values.put(R_ACCESSED, esermon.getRaccessed());

        db.update(S_TABLE, values, R_ID + " = ?", new String[] { String.valueOf(esermon.getRid()) });
        db.close();
    }

    public void draftSermon(SermonItem esermon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(R_STATE, 2);

        db.update(S_TABLE, values, R_ID + " = ?", new String[] { String.valueOf(esermon.getRid()) });
        db.close();
    }

    public void archiveSermon(SermonItem esermon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(R_STATE, 3);

        db.update(S_TABLE, values, R_ID + " = ?", new String[] { String.valueOf(esermon.getRid()) });
        db.close();
    }

    public void deleteSermon(SermonItem esermon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(R_STATE, 4);

        db.update(S_TABLE, values, R_ID + " = ?", new String[] { String.valueOf(esermon.getRid()) });
        db.close();
    }

    public void deleteSermons(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(S_TABLE, null, null);
        db.close();
    }

    public void trashSermon(SermonItem esermon) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(S_TABLE, R_ID + " = ?", new String[] { String.valueOf(esermon.getRid()) });
        db.close();
    }

    public void trashSermons(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(S_TABLE, null, null);
        db.close();
    }

    //PAPERS MANAGEMENT	END

    //THITHES MANAGEMENT BEGIN
    public void createThitheItem(ThitheItem epreacher) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(R_AMOUNT, epreacher.getRamount());
        values.put(R_SOURCE, epreacher.getRsource());
        values.put(R_PLACE, epreacher.getRplace());
        values.put(R_STATE, epreacher.getRstate());
        values.put(R_CREATED, epreacher.getRcreated());
        values.put(R_UPDATED, epreacher.getRupdated());
        values.put(R_ACCESSED, epreacher.getRaccessed());

        db.insert(T_TABLE, null, values);
        db.close();
    }

    public ThitheItem readThitheItem(int rid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(T_TABLE, T_COLUMNS, R_ID + " = ?", new String[] { String.valueOf(rid) }, null, null, null, null);

        if (cursor != null) cursor.moveToFirst();

        ThitheItem epreacher = new ThitheItem();
        epreacher.setRid(Integer.parseInt(cursor.getString(0)));
        epreacher.setRamount(Integer.parseInt(cursor.getString(1)));
        epreacher.setRsource(cursor.getString(2));
        epreacher.setRplace(cursor.getString(3));
        epreacher.setRstate(cursor.getString(4));
        epreacher.setRcreated(cursor.getString(5));
        epreacher.setRupdated(cursor.getString(6));
        epreacher.setRaccessed(cursor.getString(7));

        db.close();
        return epreacher;
    }

    public List<ThitheItem> listThitheItem(int rstate) {
        List<ThitheItem> preacherlist = new LinkedList<>();
        String query = "SELECT * FROM " + T_TABLE + " WHERE " + R_STATE + "=" + rstate + " ORDER BY " + R_ID + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ThitheItem epreacher = null;
        if (cursor.moveToFirst()) {
            do {
                epreacher = new ThitheItem();
                epreacher.setRid(Integer.parseInt(cursor.getString(0)));
                epreacher.setRamount(Integer.parseInt(cursor.getString(1)));
                epreacher.setRsource(cursor.getString(2));
                epreacher.setRplace(cursor.getString(3));
                epreacher.setRstate(cursor.getString(4));
                epreacher.setRcreated(cursor.getString(5));
                epreacher.setRupdated(cursor.getString(6));
                epreacher.setRaccessed(cursor.getString(7));

                preacherlist.add(epreacher);
            } while (cursor.moveToNext());
        }
        db.close();
        return preacherlist;
    }

    public void updateThitheItem(ThitheItem epreacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(R_AMOUNT, epreacher.getRamount());
        values.put(R_SOURCE, epreacher.getRsource());
        values.put(R_PLACE, epreacher.getRplace());
        values.put(R_STATE, epreacher.getRstate());
        values.put(R_CREATED, epreacher.getRcreated());
        values.put(R_UPDATED, epreacher.getRupdated());
        values.put(R_ACCESSED, epreacher.getRaccessed());

        db.update(T_TABLE, values, R_ID + " = ?", new String[] { String.valueOf(epreacher.getRid()) });
        db.close();
    }

    //THITHES MANAGEMENT END

}

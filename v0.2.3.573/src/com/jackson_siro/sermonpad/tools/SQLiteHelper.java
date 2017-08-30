package com.jackson_siro.sermonpad.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import com.jackson_siro.sermonpad.*;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class SQLiteHelper extends SQLiteOpenHelper {

	// database version
	public static final int DB_VERSION = 1;
	// database name
	public static final String DB_NAME = "SermonPad", S_TABLE = "MySermonPad", S_ID = "sermonid", S_TITLE = "sermon_title", S_PREACHER = "sermon_preacher", S_PLACE = "sermon_place", S_CONTENT = "sermon_content", S_DATE = "sermon_date", S_EXTRA = "sermon_extra", S_STATE = "sermon_state";
	public static final String T_TABLE = "MyTithings", T_ID = "titheid", T_POSTED = "tithe_posted", T_SOURCE = "tithe_source", T_PLACE = "tithe_place", T_AMOUNT = "tithe_amount", T_STATE = "tithe_state";
	public static final String[] S_COLUMNS = { S_ID, S_DATE, S_TITLE, S_PREACHER, S_PLACE, S_CONTENT, S_EXTRA, S_STATE};
	public static final String[] T_COLUMNS = { T_ID, T_POSTED, T_SOURCE, T_PLACE, T_AMOUNT, T_STATE};

	public SQLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + S_TABLE + "(" + S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  S_DATE + " VARCHAR, " + S_TITLE + " VARCHAR, " +  S_PREACHER + " VARCHAR, " + S_PLACE + " VARCHAR, " + S_CONTENT + " VARCHAR, " + S_EXTRA + " VARCHAR, " + S_STATE + " INTEGER)");
		db.execSQL("CREATE TABLE " + T_TABLE + "(" + T_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  T_POSTED + " VARCHAR, " +  T_SOURCE + " VARCHAR, " + T_PLACE + " VARCHAR, " + T_AMOUNT + " INTEGER, " + T_STATE + " INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// drop SermonPad table if already exists
		db.execSQL("DROP TABLE IF EXISTS " + S_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + T_TABLE);
		this.onCreate(db);
	}

	public void createNote(Sermon note) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(S_DATE, note.getDate());
		values.put(S_TITLE, note.getTitle());
		values.put(S_PLACE, note.getPlace());
		values.put(S_PREACHER, note.getPreacher());
		values.put(S_CONTENT, note.getContent());
		values.put(S_EXTRA, note.getExtra());
		values.put(S_STATE, note.getState());

		db.insert(S_TABLE, null, values);
		db.close();
	}

	public Sermon readNote(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(S_TABLE, 
				S_COLUMNS, S_ID +"= ?", new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Sermon note = new Sermon();
		note.setSermonid(Integer.parseInt(cursor.getString(0)));
		note.setDate(cursor.getString(1));
		note.setTitle(cursor.getString(2));
		note.setPlace(cursor.getString(3));
		note.setPreacher(cursor.getString(4));
		note.setContent(cursor.getString(5));
		note.setExtra(cursor.getString(6));
		note.setState(cursor.getInt(7));

		return note;
	}
	
	public Sermon readNextNote(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(S_TABLE, S_COLUMNS, S_ID + "> ? ORDER BY " + S_ID + " DESC LIMIT 1", 
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}
		
		Sermon note = new Sermon();
		note.setSermonid(Integer.parseInt(cursor.getString(0)));
		note.setDate(cursor.getString(1));
		note.setTitle(cursor.getString(2));
		note.setPlace(cursor.getString(3));
		note.setPreacher(cursor.getString(4));
		note.setContent(cursor.getString(5));
		note.setExtra(cursor.getString(6));
		note.setState(cursor.getInt(7));

		return note;
	}


	public Sermon readPrevNote(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(S_TABLE, S_COLUMNS, S_ID +" < ? ORDER BY " + S_ID + " DESC LIMIT 1", 
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Sermon note = new Sermon();
		note.setSermonid(Integer.parseInt(cursor.getString(0)));
		note.setDate(cursor.getString(1));
		note.setTitle(cursor.getString(2));
		note.setPlace(cursor.getString(3));
		note.setPreacher(cursor.getString(4));
		note.setContent(cursor.getString(5));
		note.setExtra(cursor.getString(6));
		note.setState(cursor.getInt(7));

		return note;
	}

	public List<Sermon> getSermonList(String TABLE_COLM, String LIST_ORDER) {
		List<Sermon> SermonPad = new LinkedList<Sermon>();
		String query = "SELECT  * FROM " + S_TABLE + " WHERE " + S_STATE + 
				" ='1' ORDER BY " + TABLE_COLM + " " + LIST_ORDER + "";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Sermon note = null;
		if (cursor.moveToFirst()) {
			do {
				note = new Sermon();
				note.setSermonid(Integer.parseInt(cursor.getString(0)));
				note.setDate(dateStr(cursor.getString(1)));
				note.setTitle(cursor.getString(2));
				note.setPreacher(cursor.getString(4) + ", " + cursor.getString(3));
				note.setContent(cursor.getString(5));
				// Add note to SermonPad
				SermonPad.add(note);
			} while (cursor.moveToNext());
		}
		return SermonPad;
	}

	public List<Sermon> getTrashList() {
		List<Sermon> SermonPad = new LinkedList<Sermon>();
		String query = "SELECT  * FROM " + S_TABLE + " WHERE " + S_STATE + " ='0' ORDER BY " + S_ID + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Sermon note = null;
		if (cursor.moveToFirst()) {
			do {
				note = new Sermon();
				note.setSermonid(Integer.parseInt(cursor.getString(0)));
				note.setDate(dateStr(cursor.getString(1)));
				note.setTitle(cursor.getString(2));
				note.setPreacher(cursor.getString(4) + ", " + cursor.getString(3));
				note.setContent(cursor.getString(5));
				// Add note to SermonPad
				SermonPad.add(note);
			} while (cursor.moveToNext());
		}
		return SermonPad;
	}

	public int updateNote(Sermon note) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(S_DATE, note.getDate());
		values.put(S_TITLE, note.getTitle());
		values.put(S_PLACE, note.getPlace());
		values.put(S_PREACHER, note.getPreacher());
		values.put(S_CONTENT, note.getContent());
		values.put(S_EXTRA, note.getExtra());
		values.put(S_STATE, note.getState());
		
		int i = db.update(S_TABLE, values, S_ID + " = ?", new String[] { String.valueOf(note.getSermonid()) });

		db.close();
		return i;
	}

	public int deleteNote(Sermon note) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(S_STATE, "0");
		int i = db.update(S_TABLE, values, S_ID + " = ?", new String[] { String.valueOf(note.getSermonid()) });
		db.close();
		return i;
	}

	public int deleteAllNotes() {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(S_STATE, "0");
		int i = db.update(S_TABLE, values, S_STATE + " = 1", null);
		db.close();
		return i;
	}

	public int restoreNotes() {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(S_STATE, "1");
		int i = db.update(S_TABLE, values, S_STATE + " = 0", null);
		db.close();
		return i;
	}

	public int restoreNote(Sermon note) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(S_STATE, "1");
		int i = db.update(S_TABLE, values, S_ID + " = ?", new String[] { String.valueOf(note.getSermonid()) });
		db.close();
		return i;
	}
	
	public void trashNote(Sermon note) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(S_TABLE, S_ID + " = ?", new String[] { String.valueOf(note.getSermonid()) });
		db.close();
	}

	public void deleteTrashedNotes(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(S_TABLE, S_STATE + " = 0", null);
		db.close();
	}
	
	//TITHING

	public void createTithing(Tithing tithe) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(T_POSTED, tithe.getPosted());
		values.put(T_PLACE, tithe.getPlace());
		values.put(T_SOURCE, tithe.getSource());
		values.put(T_AMOUNT, tithe.getAmount());
		values.put(T_STATE, tithe.getState());

		db.insert(T_TABLE, null, values);
		db.close();
	}

	public Tithing readTithing(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(T_TABLE, 
				T_COLUMNS, T_ID + "= ?", new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Tithing tithe = new Tithing();
		tithe.setTithingid(Integer.parseInt(cursor.getString(0)));
		tithe.setPosted(cursor.getString(1));
		tithe.setSource(cursor.getString(2));
		tithe.setPlace(cursor.getString(3));
		tithe.setAmount(cursor.getInt(4));
		tithe.setState(cursor.getInt(5));
		//T_POSTED, T_SOURCE, T_PLACE, T_AMOUNT, T_STATE
		return tithe;
	}
		
	public String dateStr(String mystr) {
		String[] MyDate = TextUtils.split(mystr, "/");
		return MyDate[0] + "/" + MyDate[1] + " " + MyDate[2];
	}
	
	public String dateStrs(String mystr) {
		String[] MyDate = TextUtils.split(mystr, "/");
		return MyDate[0] + "/" + MyDate[1] + "/" + MyDate[2];
	}

	public String hrMins(String mystr) {
		String[] MyTime = TextUtils.split(mystr, ":");
		return MyTime[0] + ":" + MyTime[1];
	}
	
	@SuppressLint("SimpleDateFormat")
	public String tithesDate(String myDate) {
		String[] MyDate = TextUtils.split(myDate, " ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		String MyDateStr =  dateStrs(MyDate[0]);
		
        Date curDateTime = new Date(System.currentTimeMillis());
        String curDate = format.format(curDateTime);
        Date date1 = null, date2 = null;

        try {
        	date1 = format.parse(myDate);
        	date2 = format.parse(curDate);

            long diff = date2.getTime() - date1.getTime();
            int timediff = (int)(diff / 1000);
            
            switch (timediff) {
            	case 60: MyDateStr = "Just now";
            	case 3600: MyDateStr = timediff/60 + " mins ago";
            	case 86400: MyDateStr = "Today " + hrMins(MyDate[1]);
            	case 172800: MyDateStr = "Yesterday " + hrMins(MyDate[1]);
            }
            /*
			if (timediff < 172800) {
				MyDateStr = "Yesterday " + hrMins(MyDate[1]);
			}
			else if (timediff < 86400) {
				MyDateStr = "Today " + hrMins(MyDate[1]);
			}
			else if (timediff < 3600) {
				MyDateStr = timediff/60 + " mins ago";
			}
			else if (timediff < 60) MyDateStr = "Just now";
            */
        } catch (Exception e) {
            e.printStackTrace();
            MyDateStr =  dateStrs(MyDate[0]);
        }
		//return MyDate[0] + "/" + MyDate[1] + " " + MyDate[2];
        return MyDateStr;
	}
	
	@SuppressLint("SimpleDateFormat")
	public String datesFormatter(String myDate) {
		String[] MyDate = TextUtils.split(myDate, "/");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
          
        Date curDateTime = new Date(System.currentTimeMillis());
        String curDate = format.format(curDateTime);
        Date date1 = null, date2 = null;

        try {
        	date1 = format.parse(myDate);
        	date2 = format.parse(curDate);

            long diff = date1.getTime() - date2.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }
		return MyDate[0] + "/" + MyDate[1] + " " + MyDate[2];
	}
	
	public List<Tithing> getTithingList() {
		List<Tithing> TithesList = new LinkedList<Tithing>();
		String query = "SELECT  * FROM " + T_TABLE + " WHERE " + T_STATE + 
				" ='1' ORDER BY " + T_ID + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Tithing tithe = null;
		if (cursor.moveToFirst()) {
			do {
				tithe = new Tithing();
				tithe.setTithingid(Integer.parseInt(cursor.getString(0)));
				tithe.setPosted(tithesDate(cursor.getString(1)));
				tithe.setSource(cursor.getString(2) + "; " + cursor.getString(3));
				tithe.setAmount(cursor.getInt(4));
				
				TithesList.add(tithe);
			} while (cursor.moveToNext());
		}
		return TithesList;
	}

	public int updateTithing(Tithing tithe) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(T_POSTED, tithe.getPosted());
		values.put(T_PLACE, tithe.getPlace());
		values.put(T_SOURCE, tithe.getSource());
		values.put(T_AMOUNT, tithe.getAmount());
		values.put(T_STATE, tithe.getState());
		
		int i = db.update(T_TABLE, values, T_ID + " = ?", new String[] { String.valueOf(tithe.getTithingid()) });

		db.close();
		return i;
	}

	public int deleteTithing(Tithing tithe) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(T_STATE, "0");
		int i = db.update(T_TABLE, values, T_ID + " = ?", new String[] { String.valueOf(tithe.getTithingid()) });
		db.close();
		return i;
	}

	public int deleteAllTithings() {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(T_STATE, "0");
		int i = db.update(T_TABLE, values, T_STATE + " = 1", null);
		db.close();
		return i;
	}

	public int restoreTithings() {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(T_STATE, "1");
		int i = db.update(T_TABLE, values, T_STATE + " = 0", null);
		db.close();
		return i;
	}

	public int restoreTithing(Tithing tithe) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(T_STATE, "1");
		int i = db.update(T_TABLE, values, T_ID + " = ?", new String[] { String.valueOf(tithe.getTithingid()) });
		db.close();
		return i;
	}
	
	public void trashTithing(Tithing tithe) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(T_TABLE, T_ID + " = ?", new String[] { String.valueOf(tithe.getTithingid()) });
		db.close();
	}

	public void deleteTrashedTithings(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(T_TABLE, T_STATE + " = 0", null);
		db.close();
	}
}

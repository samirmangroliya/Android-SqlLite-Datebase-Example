package com.samir.spinner;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "mydb";
	private static final String TABLE_NAME = "mytable";
	private static final String _id = "_id";
	private static final String name = "name";

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableQuery = "create table " + TABLE_NAME + "(" + _id
				+ " INTEGER PRIMARY KEY," + name + " TEXT)";
		db.execSQL(createTableQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("drop table if exists " + TABLE_NAME);

		onCreate(db);
	}

	public void insertData(String label) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(name, label);
		db.insert(TABLE_NAME, null, values);
		db.close();
	}

	public Set<String> getAllData() {
		Set<String> set = new HashSet<String>();

		String selectQuery = "select * from " + TABLE_NAME;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				set.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return set;
	}
}
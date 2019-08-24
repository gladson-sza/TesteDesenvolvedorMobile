package br.com.cybersociety.testedesenvolvedormobile.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static final String DB_NAME = "db_movies";
    public static final String TABLE_MOVIE = "Movie";
    public static final String TABLE_USER = "User";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

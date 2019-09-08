package br.com.cybersociety.testedesenvolvedormobile.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static final String DB_NAME = "db_mymovies";
    public static final String TABLE_MOVIE = "Movie";
    public static final String TABLE_USER = "User";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlMovie = "CREATE TABLE IF NOT EXISTS " + TABLE_MOVIE +
                " (id LONG PRIMARY KEY NOT NULL," +
                " adult CHAR NOT NULL," +
                " originalLanguage VARCHAR(20) NOT NULL," +
                " originalTitle VARCHAR(100) NOT NULL," +
                " title VARCHAR(100) NOT NULL," +
                " overview VARCHAR(2000) NOT NULL," +
                " popularity DOUBLE NOT NULL," +
                " rating DOUBLE NOT NULL," +
                " releasedDate CHAR(10) NOT NULL);";

        String sqlUser = "CREATE TABLE IF NOT EXISTS " + TABLE_USER +
                " (id INT NOT NULL PRIMARY KEY," +
                " name VARCHAR(50)," +
                " photo BLOB);";

        String createUser = "INSERT INTO User (id, name, photo) VALUES(0, '', null);"; // Só haverá um usuário neste banco.

        try {
            db.execSQL(sqlMovie);
            db.execSQL(sqlUser);
            db.execSQL(createUser);
        } catch (Exception e) {
            Log.i("INFO BD", "Falha na criação da Tabela do Banco de Dados: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

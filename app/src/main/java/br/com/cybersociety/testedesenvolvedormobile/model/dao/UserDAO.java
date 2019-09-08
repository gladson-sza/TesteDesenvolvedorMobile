package br.com.cybersociety.testedesenvolvedormobile.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.cybersociety.testedesenvolvedormobile.helper.DBHelper;
import br.com.cybersociety.testedesenvolvedormobile.model.entities.User;

public class UserDAO implements IUserDAO {

    private static final int DEFAULT_USER_ID = 0;

    private SQLiteDatabase writer;
    private SQLiteDatabase reader;

    public UserDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        writer = dbHelper.getWritableDatabase();
        reader = dbHelper.getReadableDatabase();
    }

    @Override
    public void update(User user) {
        String[] args = {String.valueOf(DEFAULT_USER_ID)};

        ContentValues cv = new ContentValues();
        cv.put("id", DEFAULT_USER_ID);
        cv.put("name", user.getName());
        cv.put("photo", user.getPhoto());

        writer.update(DBHelper.TABLE_USER, cv, "id=?", args);
    }

    @Override
    public User getUser() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABLE_USER
                + " WHERE id=" + DEFAULT_USER_ID + ";";

        Cursor c = reader.rawQuery(sql, null);

        while (c.moveToNext()) {

            User user = new User();

            user.setName(c.getString(c.getColumnIndex("name")));
            user.setPhoto(c.getBlob(c.getColumnIndex("photo")));

            list.add(user);
        }

        if (list.size() > 0) return list.get(0);
        else return  null;
    }
}

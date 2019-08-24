package br.com.cybersociety.testedesenvolvedormobile.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.cybersociety.testedesenvolvedormobile.helper.DBHelper;
import br.com.cybersociety.testedesenvolvedormobile.model.entities.Movie;

public class MovieDAO implements IMovieDAO {
    private SQLiteDatabase writer;
    private SQLiteDatabase reader;

    public MovieDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        writer = dbHelper.getWritableDatabase();
        reader = dbHelper.getReadableDatabase();
    }


    @Override
    public void save(Movie movie) {

    }

    @Override
    public void delete(Movie movie) {

    }

    @Override
    public void update(Movie movie) {

    }

    @Override
    public List<Movie> listAll() {
        return null;
    }
}

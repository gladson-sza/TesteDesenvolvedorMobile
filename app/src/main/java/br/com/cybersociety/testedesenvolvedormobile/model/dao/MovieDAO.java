package br.com.cybersociety.testedesenvolvedormobile.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        ContentValues cv = new ContentValues();
        cv.put("title", movie.getId());

        if (movie.getAdult()) {
            cv.put("adult", "y");
        } else {
            cv.put("adult", "n");
        }

        cv.put("originalLanguage", movie.getOriginalLanguage());
        cv.put("title", movie.getTitle());
        cv.put("originalTitle", movie.getOriginalTitle());
        cv.put("overview", movie.getOverview());
        cv.put("popularity", movie.getPopularity());
        cv.put("rating", movie.getRating());
        cv.put("releasedDate", new SimpleDateFormat("dd/MM/yyyy").format(movie.getReleasedDate()));

        writer.insert(DBHelper.TABLE_MOVIE, null, cv);
    }

    @Override
    public void saveCollection(List<Movie> movies) {
        for (Movie m : movies) {
            save(m);
        }
    }

    @Override
    public void delete(Movie movie) {
        String[] args = {String.valueOf(movie.getId())};
        writer.delete(DBHelper.TABLE_MOVIE, "registration=?", args);
    }

    @Override
    public void update(Movie movie) {

        String[] args = {String.valueOf(movie.getId())};

        ContentValues cv = new ContentValues();
        cv.put("title", movie.getId());

        if (movie.getAdult()) {
            cv.put("adult", "y");
        } else {
            cv.put("adult", "n");
        }

        cv.put("originalLanguage", movie.getOriginalLanguage());
        cv.put("title", movie.getTitle());
        cv.put("originalTitle", movie.getOriginalTitle());
        cv.put("overview", movie.getOverview());
        cv.put("popularity", movie.getPopularity());
        cv.put("rating", movie.getRating());
        cv.put("releasedDate", new SimpleDateFormat("dd/MM/yyyy").format(movie.getReleasedDate()));

        writer.update(DBHelper.TABLE_MOVIE, cv, "id=?", args);
    }

    @Override
    public List<Movie> listAll() {
        List<Movie> list = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABLE_MOVIE + " ;";
        Cursor c = reader.rawQuery(sql, null);

        while (c.moveToNext()) {

            Movie movie = new Movie();

            // Verifique se é um filme adulto
            char adult = c.getString(c.getColumnIndex("id")).charAt(0);
            if (adult == 'y') {
                movie.setAdult(true);
            } else {
                movie.setAdult(false);
            }

            movie.setId(c.getLong(c.getColumnIndex("id")));
            movie.setOriginalLanguage(c.getString(c.getColumnIndex("originalLanguage")));
            movie.setTitle(c.getString(c.getColumnIndex("title")));
            movie.setOriginalTitle(c.getString(c.getColumnIndex("originalTitle")));
            movie.setOverview(c.getString(c.getColumnIndex("overview")));
            movie.setPopularity(c.getDouble(c.getColumnIndex("popularity")));
            movie.setRating(c.getDouble(c.getColumnIndex("rating")));

            try {
                movie.setReleasedDate(new SimpleDateFormat("dd/MM/yyyy").parse(c.getString(c.getColumnIndex("releasedDate"))));
            } catch (ParseException e) {
                Log.d("BD INFO", "Erro ao obter a data");
            }

            list.add(movie);
        }

        return list;
    }
}

package br.com.cybersociety.testedesenvolvedormobile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.cybersociety.testedesenvolvedormobile.R;
import br.com.cybersociety.testedesenvolvedormobile.model.entities.Movie;

public class MovieInformationActivity extends AppCompatActivity {

    private Toolbar toolbarTitle;
    private TextView textViewInfoAdult;
    private TextView textViewInfoOriginalTitle;
    private TextView textViewInfoDate;
    private TextView textViewInfoOverview;
    private TextView textViewInfoPopularity;
    private TextView textViewInfoRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Bundle data = getIntent().getExtras();
        Movie movie = new Movie();

        // Recupera as informações que foram passadas
        try {
            movie.setAdult(data.getBoolean("adult"));
            movie.setTitle(data.getString("title"));
            movie.setOriginalTitle(data.getString("originalTitle"));
            movie.setOverview(data.getString("overview"));
            movie.setPopularity(data.getDouble("popularity"));
            movie.setRating(data.getDouble("rating"));
            movie.setReleasedDate(sdf.parse(data.getString("releasedDate")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Inicializa as informações.
        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setTitle(movie.getTitle());
        setSupportActionBar(toolbarTitle);

        textViewInfoAdult = findViewById(R.id.textViewInfoAdult);
        textViewInfoOriginalTitle = findViewById(R.id.textViewInfoOriginalTitle);
        textViewInfoDate = findViewById(R.id.textViewInfoDate);
        textViewInfoOverview = findViewById(R.id.textViewInfoOverview);
        textViewInfoPopularity = findViewById(R.id.textViewInfoPopularity);
        textViewInfoRating = findViewById(R.id.textViewInfoRating);

        if (!movie.getAdult()) textViewInfoAdult.setVisibility(View.INVISIBLE);

        textViewInfoOriginalTitle.setText(movie.getOriginalTitle());
        textViewInfoDate.setText(sdf.format(movie.getReleasedDate()));
        textViewInfoOverview.setText(movie.getOverview());
        textViewInfoPopularity.setText(String.format("%.2f", movie.getPopularity()));
        textViewInfoRating.setText(String.format("%.2f", movie.getRating()));
    }
}

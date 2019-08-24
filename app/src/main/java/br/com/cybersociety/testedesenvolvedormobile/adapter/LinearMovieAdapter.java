package br.com.cybersociety.testedesenvolvedormobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.cybersociety.testedesenvolvedormobile.R;
import br.com.cybersociety.testedesenvolvedormobile.model.entities.Movie;

public class LinearMovieAdapter extends RecyclerView.Adapter<LinearMovieAdapter.MyViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public LinearMovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemList =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.movie_linear_adapter, viewGroup, false);

        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearMovieAdapter.MyViewHolder myViewHolder, int i) {
        Movie movie = movieList.get(i);

        if (movie.getTitle().length() > 37) {
            myViewHolder.textViewLinearMovieTitle.setText(movie.getTitle().substring(0, 35) + "..." );
        } else {
            myViewHolder.textViewLinearMovieTitle.setText(movie.getTitle());
        }

        myViewHolder.textViewLinearMovieTitle.setText(movie.getTitle());
        myViewHolder.textViewLinearLikesCount.setText(movie.getPopularity().toString());
        myViewHolder.textViewLinearStarsCount.setText(movie.getRating().toString());

    }

    @Override
    public int getItemCount() { return movieList.size(); }

    /**
     * Classe interna para inicialização dos componentes do adapter.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewLinearMovieThumb;
        private TextView textViewLinearMovieTitle;
        private TextView textViewLinearLikesCount;
        private TextView textViewLinearStarsCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewLinearMovieThumb = itemView.findViewById(R.id.imageViewLinearMovieThumb);
            textViewLinearMovieTitle = itemView.findViewById(R.id.textViewLinearMovieTitle);
            textViewLinearLikesCount = itemView.findViewById(R.id.textViewLinearLikesCount);
            textViewLinearStarsCount = itemView.findViewById(R.id.textViewLinearStarsCount);
        }
    }

}

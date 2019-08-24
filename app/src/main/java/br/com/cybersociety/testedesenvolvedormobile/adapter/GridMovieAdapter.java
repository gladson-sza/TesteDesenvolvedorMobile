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

/**
 * Adapter para as informações reduzidas que aparecerão no RecyclerView.
 */
public class GridMovieAdapter extends RecyclerView.Adapter<GridMovieAdapter.MyViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public GridMovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemList =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.movie_grid_adapter, viewGroup, false);

        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Movie movie = movieList.get(i);


        if (movie.getTitle().length() > 37) {
            myViewHolder.textViewGridMovieTitle.setText(movie.getTitle().substring(0, 35) + "..." );
        } else {
            myViewHolder.textViewGridMovieTitle.setText(movie.getTitle());
        }


        myViewHolder.textViewGridLikesCount.setText(String.format("%.1f", movie.getPopularity()));
        myViewHolder.textViewGridStarsCount.setText(String.format("%.1f", movie.getRating()));

    }

    @Override
    public int getItemCount() { return movieList.size(); }

    /**
     * Classe interna para inicialização dos componentes do adapter.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewGridMovieThumb;
        private TextView textViewGridMovieTitle;
        private TextView textViewGridLikesCount;
        private TextView textViewGridStarsCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewGridMovieThumb = itemView.findViewById(R.id.imageViewGridMovieThumb);
            textViewGridMovieTitle = itemView.findViewById(R.id.textViewGridMovieTitle);
            textViewGridLikesCount = itemView.findViewById(R.id.textViewGridLikesCount);
            textViewGridStarsCount = itemView.findViewById(R.id.textViewGridStarsCount);
        }
    }

}

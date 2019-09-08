package br.com.cybersociety.testedesenvolvedormobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.cybersociety.testedesenvolvedormobile.R;
import br.com.cybersociety.testedesenvolvedormobile.activity.MainActivity;
import br.com.cybersociety.testedesenvolvedormobile.activity.MovieInformationActivity;
import br.com.cybersociety.testedesenvolvedormobile.adapter.GridMovieAdapter;
import br.com.cybersociety.testedesenvolvedormobile.adapter.LinearMovieAdapter;
import br.com.cybersociety.testedesenvolvedormobile.helper.RecyclerItemClickListener;
import br.com.cybersociety.testedesenvolvedormobile.model.dao.MovieDAO;
import br.com.cybersociety.testedesenvolvedormobile.model.entities.Movie;

public class MovieFragment extends Fragment {

    private static final int LINEAR_LAYOUT = 0;
    private static final int GRID_LAYOUT = 1;

    private static int current_layout;

    private RecyclerView recyclerView;
    private MaterialSearchView searchView;

    private List<Movie> movies = new ArrayList<>();
    private List<Movie> moviesSearch = new ArrayList<>();
    private List<Movie> currentListView = new ArrayList<>();

    private GridMovieAdapter gridMovieAdapter;
    private LinearMovieAdapter linearMovieAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menu_change_layout:
                changeLayout();
                // Realiza as trocas entre os ícones com base no layout
                if (current_layout == LINEAR_LAYOUT) item.setIcon(R.drawable.ic_grid_white_24dp);
                else item.setIcon(R.drawable.ic_view_list_white_24dp);
                break;
            case R.id.menu_search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);

        if (current_layout == LINEAR_LAYOUT) menu.getItem(1).setIcon(R.drawable.ic_grid_white_24dp);
        else menu.getItem(1).setIcon(R.drawable.ic_view_list_white_24dp);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        createLayout(view);

        movies.clear(); // Limpa para garantir que não haverá duplicidade antes de chamar

        if (hasConnection()) {
            loadPupularMovies();
        } else {
            Toast.makeText(getContext(), "Sem conexão disponível, carregando filmes previamente baixados...", Toast.LENGTH_LONG).show();
            loadStorageMovies();
        }

        return view;
    }

    /**
     * Carrega três páginas de feed com filmes
     */
    private void loadPupularMovies() {
        String page = "https://api.themoviedb.org/3/movie/popular?api_key=d1f80db1bf861c571beeeb21b32e5ca6&language=pt-BR&page=";
        new MyTaskList().execute(page + 1);
        new MyTaskList().execute(page + 2);
        new MyTaskList().execute(page + 3);
    }

    /**
     * Método que carrega os filmes armazenados no banco de dados
     */
    private void loadStorageMovies() {
        MovieDAO movieDAO = new MovieDAO(getContext());
        currentListView = movieDAO.listAll();

        if (current_layout == LINEAR_LAYOUT) recyclerView.setAdapter(new LinearMovieAdapter(currentListView, getActivity()));
        else recyclerView.setAdapter(new GridMovieAdapter(currentListView, getActivity()));

        linearMovieAdapter.notifyDataSetChanged();
        gridMovieAdapter.notifyDataSetChanged();
    }

    private boolean hasConnection() {
        // Verifica a conexão com a internet e carrega os filmes já salvos se for necessário
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isAvailable())
            return true;
        else return false;
    }

    private void createLayout(View view) {

        currentListView = movies;

        linearMovieAdapter = new LinearMovieAdapter(currentListView, getActivity());
        gridMovieAdapter = new GridMovieAdapter(currentListView, getActivity());

        recyclerView = view.findViewById(R.id.list);

        searchView = getActivity().findViewById(R.id.search_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Movie movie = currentListView.get(position);

                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                                    Intent intent = new Intent(getActivity(), MovieInformationActivity.class);

                                    intent.putExtra("adult", movie.getAdult());
                                    intent.putExtra("title", movie.getTitle());
                                    intent.putExtra("originalTitle", movie.getOriginalTitle());
                                    intent.putExtra("rating", movie.getRating());
                                    intent.putExtra("popularity", movie.getPopularity());
                                    intent.putExtra("overview", movie.getOverview());
                                    intent.putExtra("releasedDate", sdf.format(movie.getReleasedDate()));

                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        })
        );

        // Verifica qual é o layout atual.
        if (current_layout == LINEAR_LAYOUT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(linearMovieAdapter);
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setAdapter(gridMovieAdapter);
        }

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (hasConnection()) {
                    MyTaskSearch myTaskSearch = new MyTaskSearch();
                    myTaskSearch.execute("https://api.themoviedb.org/3/search/movie?api_key=d1f80db1bf861c571beeeb21b32e5ca6&language=pt-BR&query=" + query + "&page=1&include_adult=false");
                    return true;
                } else {
                    Toast.makeText(getActivity(), "Não foi possível realizar a busca, sem conexão.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                MainActivity ma = (MainActivity) getActivity();

                currentListView.clear();
                linearMovieAdapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(linearMovieAdapter);

                ma.hideBottomNavigation();
                setHasOptionsMenu(false);

            }

            @Override
            public void onSearchViewClosed() {
                MainActivity ma = (MainActivity) getActivity();

                if (hasConnection()) loadPupularMovies();
                else loadStorageMovies();

                ma.showBottomNavigation();
                setHasOptionsMenu(true);
            }
        });


    }

    // Evento que modifica o layout
    private void changeLayout() {

        if (current_layout == LINEAR_LAYOUT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setAdapter(gridMovieAdapter);
            gridMovieAdapter.notifyDataSetChanged();
            current_layout = GRID_LAYOUT;

        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(linearMovieAdapter);
            current_layout = LINEAR_LAYOUT;

        }

        if (!hasConnection()) loadStorageMovies();

    }

    /**
     * Classe interna para realizar pesquisa
     */
    private class MyTaskSearch extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            StringBuffer buffer = new StringBuffer();

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                inputStream = connection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            long id;
            boolean adult;
            String originalTitle;
            String title;
            String originalLanguage;
            String overview;
            double popularity;
            double rating;


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate;

            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONArray keyValue = jsonObject.getJSONArray("results");

                moviesSearch.clear(); // Limpa para garantir que não haverá duplicidade

                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject jsonObjectValue = keyValue.getJSONObject(i);

                    id = jsonObjectValue.getLong("id");
                    adult = jsonObjectValue.getBoolean("adult");
                    originalLanguage = jsonObjectValue.getString("original_language");
                    originalTitle = jsonObjectValue.getString("original_title");
                    title = jsonObjectValue.getString("title");
                    overview = jsonObjectValue.getString("overview");
                    popularity = jsonObjectValue.getDouble("popularity");
                    rating = jsonObjectValue.getDouble("vote_average");
                    releaseDate = sdf.parse(jsonObjectValue.getString("release_date"));

                    Movie movie = new Movie(id, adult, originalLanguage, originalTitle, title, overview, popularity, rating, releaseDate);
                    moviesSearch.add(movie);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            currentListView = moviesSearch;
            recyclerView.setAdapter(new LinearMovieAdapter(currentListView, getActivity()));
            linearMovieAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Classe interna para listar os filmes populares.
     */
    private class MyTaskList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = new StringBuffer();

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                inputStream = connection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            long id;
            boolean adult;
            String originalTitle;
            String title;
            String originalLanguage;
            String overview;
            double popularity;
            double rating;


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate;

            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONArray keyValue = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject jsonObjectValue = keyValue.getJSONObject(i);

                    id = jsonObjectValue.getLong("id");
                    adult = jsonObjectValue.getBoolean("adult");
                    originalLanguage = jsonObjectValue.getString("original_language");
                    originalTitle = jsonObjectValue.getString("original_title");
                    title = jsonObjectValue.getString("title");
                    overview = jsonObjectValue.getString("overview");
                    popularity = jsonObjectValue.getDouble("popularity");
                    rating = jsonObjectValue.getDouble("vote_average");
                    releaseDate = sdf.parse(jsonObjectValue.getString("release_date"));

                    Movie movie = new Movie(id, adult, originalLanguage, originalTitle, title, overview, popularity, rating, releaseDate);
                    movies.add(movie);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            currentListView = movies;
            linearMovieAdapter.notifyDataSetChanged();
            gridMovieAdapter.notifyDataSetChanged();

            MovieDAO movieDAO = new MovieDAO(getActivity());
            movieDAO.saveCollection(movies);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

        }
    }
}

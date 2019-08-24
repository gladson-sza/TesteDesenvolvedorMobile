package br.com.cybersociety.testedesenvolvedormobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.cybersociety.testedesenvolvedormobile.R;
import br.com.cybersociety.testedesenvolvedormobile.adapter.GridMovieAdapter;
import br.com.cybersociety.testedesenvolvedormobile.adapter.LinearMovieAdapter;
import br.com.cybersociety.testedesenvolvedormobile.entities.Movie;
import br.com.cybersociety.testedesenvolvedormobile.fragment.DummyContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MovieFragment extends Fragment {

    private static final int LINEAR_LAYOUT = 0;
    private static final int GRID_LAYOUT = 1;

    private int current_layout;

    private RecyclerView recyclerView;
    private OnListFragmentInteractionListener mListener;

    private List<Movie> movies = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());

        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (current_layout == LINEAR_LAYOUT) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new LinearMovieAdapter(movies, getActivity()));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                recyclerView.setAdapter(new GridMovieAdapter(movies, getActivity()));
            }

        }
        return view;
    }

    public void changeLayout() {

        if (current_layout == LINEAR_LAYOUT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setAdapter(new GridMovieAdapter(movies, getActivity()));
            current_layout = GRID_LAYOUT;
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new LinearMovieAdapter(movies, getActivity()));
            current_layout = LINEAR_LAYOUT;
        }
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}

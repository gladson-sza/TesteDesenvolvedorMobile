package br.com.cybersociety.testedesenvolvedormobile.model.dao;

import java.util.List;

import br.com.cybersociety.testedesenvolvedormobile.model.entities.Movie;

public interface IMovieDAO {

    void save(Movie movie);
    void saveCollection(List<Movie> movies);
    void delete(Movie movie);
    void update(Movie movie);

    List<Movie> listAll();
}

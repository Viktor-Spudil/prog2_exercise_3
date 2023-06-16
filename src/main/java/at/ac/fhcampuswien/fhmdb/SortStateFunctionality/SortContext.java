package at.ac.fhcampuswien.fhmdb.SortStateFunctionality;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class SortContext {
    private SortState sortState;
    private List<Movie> movieList;

    public SortContext() {
        sortState = new UnsortedState();
        movieList = new ArrayList<>();
    }

    public void setMovies(List<Movie> movies) {
        this.movieList = movies;
    }

    public List<Movie> sortMovies(List<Movie> moviesList) {
        sortState.sortMovies(moviesList);
        return moviesList;
    }

    public void transitionTo (SortState state) {
        sortState = state;
    }

    public List<Movie> sortMoviesAscending(List<Movie> moviesList) {
        transitionTo(new AscendingState());
        return sortMovies(moviesList);
    }

    public List<Movie> sortMoviesDescending(List<Movie> moviesList) {
        transitionTo(new DescendingState());
        return sortMovies(moviesList);
    }
}

package at.ac.fhcampuswien.fhmdb.SortStateFunctionality;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;
import java.util.List;

public class UnsortedState implements SortState{

    @Override
    public List<Movie> sortMovies(List<Movie> movieList) {
        return movieList;
    }
}

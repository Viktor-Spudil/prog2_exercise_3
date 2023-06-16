package at.ac.fhcampuswien.fhmdb.SortStateFunctionality;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;
import java.util.List;

public class UnsortedState implements SortState{

    private SortContext context;

    @Override
    public void setSortContext(SortContext context) {
        this.context = context;
    }
    @Override
    public List<Movie> sortMovies(List<Movie> movieList) {
        return movieList;
    }
}

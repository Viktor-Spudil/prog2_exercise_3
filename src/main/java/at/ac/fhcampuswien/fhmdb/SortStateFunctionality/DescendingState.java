package at.ac.fhcampuswien.fhmdb.SortStateFunctionality;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;
import java.util.List;

public class DescendingState implements SortState {


    private SortContext context;

    @Override
    public void setSortContext(SortContext context) {
        this.context = context;
    }

    @Override
    public List<Movie> sortMovies(List<Movie> moviesList) {
        moviesList.sort(Comparator.comparing(Movie::getTitle).reversed());
        this.context.transitionTo(this);
        return moviesList;
    }

}



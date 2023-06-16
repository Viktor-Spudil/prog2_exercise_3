package at.ac.fhcampuswien.fhmdb.SortStateFunctionality;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;
import java.util.List;

public class AscendingState implements SortState {



    @Override
    public List<Movie> sortMovies(List<Movie> moviesList) {
        moviesList.sort(Comparator.comparing(Movie::getTitle));
        return moviesList;
    }


}

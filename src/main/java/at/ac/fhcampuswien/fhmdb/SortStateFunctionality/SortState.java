package at.ac.fhcampuswien.fhmdb.SortStateFunctionality;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.List;

public interface SortState {


    List<Movie> sortMovies(List<Movie> moviesList);


}

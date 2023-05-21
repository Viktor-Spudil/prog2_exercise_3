package at.ac.fhcampuswien.fhmdb.ApplicationTier.controllers;

@FunctionalInterface
public interface ClickEventHandler<Movie> {
    void onClick(Movie movie);
}

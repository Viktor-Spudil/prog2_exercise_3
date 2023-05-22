package at.ac.fhcampuswien.fhmdb.ApplicationTier.controllers;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;

import java.sql.SQLException;

@FunctionalInterface
public interface ClickEventHandler<Movie> {
    void onClick(Movie movie, HomeController controller);
}

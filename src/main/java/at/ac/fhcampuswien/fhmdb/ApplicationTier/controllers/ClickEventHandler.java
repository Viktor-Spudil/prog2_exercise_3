package at.ac.fhcampuswien.fhmdb.ApplicationTier.controllers;

import java.sql.SQLException;

@FunctionalInterface
public interface ClickEventHandler<Movie> {
    void onClick(Movie movie, HomeController controller) throws SQLException;
}

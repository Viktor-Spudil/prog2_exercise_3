package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    public final String title;
    public final String description;
    public final List<Genre> genres;
    public Integer releaseYear;
    public List<String> mainCast;
    public List<String> directors;


    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = null;
        this.mainCast = new ArrayList<>();
        this.directors = new ArrayList<>();
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Movie other)) {
            return false;
        }
        return this.title.equals(other.title) && this.description.equals(other.description) && this.genres.equals(other.genres);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public List<String> getMainCast() {
        return mainCast;
    }
    public List<String> getDirectors() {
        return directors;
    }
}

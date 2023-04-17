package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    public final String id;
    public final String title;
    public final List<Genre> genres;
    public Integer releaseYear;
    public final String description;
    public final String imgUrl;
    public final Integer lengthInMinutes;
    public List<String> directors;
    public List<String> writers;
    public List<String> mainCast;
    public Double rating;





    public Movie(String id, String title, List<Genre> genres, Integer releaseYear, String description, String imgUrl, Integer lengthInMinutes, List<String> directors, List<String> writers, Double rating) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = new ArrayList<>();
        this.rating = rating;
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

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public List<Genre> getGenres() {
        return this.genres;
    }

    public Integer getReleaseYear() {
        return this.releaseYear;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public Integer getLengthInMinutes() {
        return this.lengthInMinutes;
    }

    public List<String> getDirectors() {
        return this.directors;
    }

    public List<String> getWriters() {
        return this.writers;
    }

    public List<String> getMainCast() {
        return this.mainCast;
    }

    public Double getRating() {
        return this.rating;
    }
}

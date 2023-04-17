package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    // === 1. CLASS VARIABLES ===
    // === 2. OBJECT VARIABLES ===
    public final String id;
    public final String title;
    public final List<Genre> genres;
    public final Integer releaseYear;
    public final String description;
    public final String imgUrl;
    public final Integer lengthInMinutes;
    public final List<String> directors;
    public final List<String> writers;
    public final List<String> mainCast;
    public final Double rating;


    // === 3. CONSTRUCTORS ===
    // --- 3.1 STATIC BLOCKS ---
    // --- 3.2 INSTANCE INITIALIZER ---
    // --- 3.3 REAL CONSTRUCTORS ---
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


    // === 4. STATIC METHODS ===
    // === 5. SETTER AND GETTER ===
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


    // === 6. MISCELLANEOUS OBJECT METHODS ===
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


    // === 7. MAIN ===
}

package at.ac.fhcampuswien.fhmdb.DataTier.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity {
    // === 0. NOTES ===
    // === 1. CLASS VARIABLES ===
    // === 2. OBJECT VARIABLES ===
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField()
    private String movieId;

    @DatabaseField()
    private String title;

    @DatabaseField()
    private String genres;

    @DatabaseField()
    private Integer releaseYear;

    @DatabaseField()
    private String description;

    @DatabaseField()
    private String imgUrl;

    @DatabaseField()
    private Integer lengthInMinutes;

    @DatabaseField()
    private Double rating;


    // === 3. CONSTRUCTORS ===
    // --- 3.1 STATIC BLOCKS ---
    // --- 3.2 INSTANCE INITIALIZER ---
    // --- 3.3 REAL CONSTRUCTORS ---
    // === 4. STATIC METHODS ===
    // === 5. GETTER AND SETTER ===
    // === 6. MISCELLANEOUS OBJECT METHODS ===
    // === 7. MAIN ===
}

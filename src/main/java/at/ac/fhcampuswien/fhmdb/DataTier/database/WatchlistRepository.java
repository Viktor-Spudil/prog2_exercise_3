package at.ac.fhcampuswien.fhmdb.DataTier.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    // === 1. CLASS VARIABLES ===
    // === 2. OBJECT VARIABLES ===
    private Dao<WatchlistMovieEntity, Long> dao;


    // === 3. CONSTRUCTORS ===
    // --- 3.1 STATIC BLOCKS ---
    // --- 3.2 INSTANCE INITIALIZER ---
    // --- 3.3 REAL CONSTRUCTORS ---
    public WatchlistRepository() {
        this.dao = DatabaseManager.getInstance().getWatchlistDao();
    }


    // === 4. STATIC METHODS ===
    // === 5. SETTER AND GETTER ===
    // === 6. MISCELLANEOUS OBJECT METHODS ===
    public void addToWatchlist(Movie movie) throws SQLException {
        WatchlistMovieEntity watchListMovieEntry = movieToWatchlistMovieEntity(movie);
        dao.createIfNotExists(watchListMovieEntry);
    }

    public void removeFromWatchlist(Movie movie) throws SQLException {
        WatchlistMovieEntity watchlistMovieEntry = movieToWatchlistMovieEntity(movie);
        dao.delete(watchlistMovieEntry);
    }

    public List<WatchlistMovieEntity> readWatchlist() throws SQLException {
        return dao.queryForAll();
    }

    private WatchlistMovieEntity movieToWatchlistMovieEntity(Movie movie) {
        return new WatchlistMovieEntity(movie.getMovieId(), movie.getTitle(),
                WatchlistMovieEntity.genresToString(movie.getGenres()), movie.getReleaseYear(),
                movie.getDescription(), movie.getImgUrl(), movie.getLengthInMinutes(), movie.getRating());
    }


    // === 7. MAIN ===
}

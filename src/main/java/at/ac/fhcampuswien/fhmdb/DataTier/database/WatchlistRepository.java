package at.ac.fhcampuswien.fhmdb.DataTier.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    // === 1. CLASS VARIABLES ===
    // === 2. OBJECT VARIABLES ===
    private Dao<WatchlistMovieEntity, Long> accountDao;


    // === 3. CONSTRUCTORS ===
    // --- 3.1 STATIC BLOCKS ---
    // --- 3.2 INSTANCE INITIALIZER ---
    // --- 3.3 REAL CONSTRUCTORS ---
    public WatchlistRepository() {
        this.accountDao = DatabaseManager.getInstance().getAccountDao();
    }


    // === 4. STATIC METHODS ===
    // === 5. SETTER AND GETTER ===
    // === 6. MISCELLANEOUS OBJECT METHODS ===
    public void addToWatchlist(Movie movie) throws SQLException {
        WatchlistMovieEntity watchListMovieEntry = movieToWatchlistMovieEntity(movie);
        accountDao.create(watchListMovieEntry);
    }

    public void removeFromWatchlist(Movie movie) throws SQLException {
        WatchlistMovieEntity watchlistMovieEntry = movieToWatchlistMovieEntity(movie);
        accountDao.delete(watchlistMovieEntry);
    }

    public List<WatchlistMovieEntity> readWatchlist() throws SQLException {
        return accountDao.queryForAll();
    }

    private WatchlistMovieEntity movieToWatchlistMovieEntity(Movie movie) {
        String movieGenres = "new String()";

        for (int i = 0; i < (movie.getGenres().size() - 1); i++) {
            movieGenres = movieGenres + movie.getGenres().get(i).toString() + ",";
        }

        return new WatchlistMovieEntity(movie.getMovieId(), movie.getTitle(), movieGenres, movie.getReleaseYear(),
                movie.getDescription(), movie.getImgUrl(), movie.getLengthInMinutes(), movie.getRating());
    }


    // === 7. MAIN ===
}

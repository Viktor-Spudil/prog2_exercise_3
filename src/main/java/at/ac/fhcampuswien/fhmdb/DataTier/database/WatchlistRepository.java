package at.ac.fhcampuswien.fhmdb.DataTier.database;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {
    // === 1. CLASS VARIABLES ===
    // === 2. OBJECT VARIABLES ===
    private Dao<WatchlistMovieEntity, Long> dao;


    // === 3. CONSTRUCTORS ===
    // --- 3.1 STATIC BLOCKS ---
    // --- 3.2 INSTANCE INITIALIZER ---
    // --- 3.3 REAL CONSTRUCTORS ---
    public WatchlistRepository() throws DatabaseException {
        this.dao = DatabaseManager.getInstance().getWatchlistDao();
    }


    // === 4. STATIC METHODS ===
    public static WatchlistMovieEntity movieToWatchlistMovieEntity(Movie movie) {
        return new WatchlistMovieEntity(movie.getMovieId(), movie.getTitle(),
                WatchlistMovieEntity.genresToString(movie.getGenres()), movie.getReleaseYear(),
                movie.getDescription(), movie.getImgUrl(), movie.getLengthInMinutes(), movie.getRating());
    }

    public static Movie WatchlistMovieEntityToMovie(WatchlistMovieEntity wme) {
        String[] genreStringArray = wme.getGenres().split(", ");
        ArrayList<Genre> genresArray = new ArrayList<>();

        for (String genreString : genreStringArray) {
            genresArray.add(Genre.valueOf(genreString));
        }

        return new Movie(wme.getMovieId(), wme.getTitle(), genresArray, wme.getReleaseYear(), wme.getDescription(),
                wme.getImgUrl(), wme.getLengthInMinutes(), null, null, wme.getRating());
    }


    // === 5. SETTER AND GETTER ===
    // === 6. MISCELLANEOUS OBJECT METHODS ===
    public void addToWatchlist(Movie movie) throws DatabaseException {
        WatchlistMovieEntity watchListMovieEntry = movieToWatchlistMovieEntity(movie);
        //dao.createIfNotExists(watchListMovieEntry);

        List<WatchlistMovieEntity> query = null;
        try {
            query = dao.queryBuilder().where().eq("title", movie.getTitle()).query();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to query on addToWatchlist", e);
        }
        try {
            if (query.isEmpty()) {
                dao.create(watchListMovieEntry);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Add to Database failed", e);
        }

    }

    public void removeFromWatchlist(Movie movie) throws DatabaseException {
        //WatchlistMovieEntity watchlistMovieEntry = movieToWatchlistMovieEntity(movie);
        //dao.delete(watchlistMovieEntry);

        DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("title", movie.getTitle());
        } catch (SQLException e) {
            throw new DatabaseException("Failed to query on removeFromWatchlist", e);
        }
        try {
            deleteBuilder.delete();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete from Database",e);
        }
    }

    public List<WatchlistMovieEntity> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to query watchlist members", e);
        }
    }


    // === 7. MAIN ===
}

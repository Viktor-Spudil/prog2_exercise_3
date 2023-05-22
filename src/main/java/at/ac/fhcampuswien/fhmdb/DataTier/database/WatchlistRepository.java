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
    public WatchlistRepository() {
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

        try {
            List<WatchlistMovieEntity> query = dao.queryBuilder().where().eq("title", movie.getTitle()).query();
            if (query.isEmpty()) {
                dao.create(watchListMovieEntry);
            }
        } catch (SQLException s) {
            throw new DatabaseException(s);
        }
    }

    public void removeFromWatchlist(Movie movie) throws DatabaseException {
        //WatchlistMovieEntity watchlistMovieEntry = movieToWatchlistMovieEntity(movie);
        //dao.delete(watchlistMovieEntry);

        try {
            DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().eq("title", movie.getTitle());
            deleteBuilder.delete();
        } catch (SQLException s) {
            throw new DatabaseException(s);
        }

    }

    public List<WatchlistMovieEntity> getAll() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException s) {
            throw new DatabaseException(s);
        }
    }


    // === 7. MAIN ===
}

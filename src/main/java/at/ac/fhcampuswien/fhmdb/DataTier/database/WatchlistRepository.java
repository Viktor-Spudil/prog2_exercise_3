package at.ac.fhcampuswien.fhmdb.DataTier.database;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.Observer.Observable;
import at.ac.fhcampuswien.fhmdb.Observer.Observer;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class WatchlistRepository implements Observable {
    // === 1. CLASS VARIABLES ===
    // === 2. OBJECT VARIABLES ===
    private Dao<WatchlistMovieEntity, Long> dao;
    private static WatchlistRepository instance;
    private ArrayList<Observer> observerList = new ArrayList<>();


    // === 3. CONSTRUCTORS ===
    // --- 3.1 STATIC BLOCKS ---
    // --- 3.2 INSTANCE INITIALIZER ---
    // --- 3.3 REAL CONSTRUCTORS ---
    private WatchlistRepository() {
        this.dao = DatabaseManager.getInstance().getWatchlistDao();
    }

    public static WatchlistRepository getInstance() {
        if (instance == null) {
            instance = new WatchlistRepository();
        }
        return instance;
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
                notifyObservers("Movie successfully added to watchlist");
            } else {
                notifyObservers("Movie already on watchlist");
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

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observerList) {
            observer.showWatchlistWindow(message);
        }
    }

    @Override
    public void subscribe(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        this.observerList.remove(observer);
    }


    // === 7. MAIN ===
}

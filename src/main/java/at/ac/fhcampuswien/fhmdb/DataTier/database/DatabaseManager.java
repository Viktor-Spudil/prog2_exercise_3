package at.ac.fhcampuswien.fhmdb.DataTier.database;


import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseManager {
    // === 1. CLASS VARIABLES ===
    private static final DatabaseManager INSTANCE = new DatabaseManager();


    // === 2. OBJECT VARIABLES ===
    //private boolean connectionInitialized = false;
    private String DB_URL = "jdbc:h2:file: ./db/watchlistdb";
    Dao<WatchlistMovieEntity, Long> dao;
    private JdbcConnectionSource connectionSource;
    private String username;
    private String password;


    // === 3. CONSTRUCTORS ===
    // --- 3.1 STATIC BLOCKS ---
    // --- 3.2 INSTANCE INITIALIZER ---
    // --- 3.3 REAL CONSTRUCTORS ---
    private DatabaseManager() {};


    // === 4. STATIC METHODS ===
    public static DatabaseManager getInstance() {
        return INSTANCE;
    }


    // === 5. SETTER AND GETTER ===
    public JdbcConnectionSource getConnectionSource() {
        return this.connectionSource;
    }

    public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return this.dao;
    }


    // === 6. MISCELLANEOUS OBJECT METHODS ===
    public void initializeConnection(String username, String password) throws DatabaseException {
        this.username = username;
        this.password = password;

        try {
            createConnectionSource(username, password);
            dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        } catch (SQLException s) {
            throw new DatabaseException(s);
        }

    }

    private void createConnectionSource(String username, String password) throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL, username, password);
    }

    private void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }


    // === 7. MAIN ===
}

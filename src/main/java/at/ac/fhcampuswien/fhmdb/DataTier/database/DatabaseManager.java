package at.ac.fhcampuswien.fhmdb.DataTier.database;


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
    Dao<WatchlistMovieEntity, Long> accountDao;
    private JdbcConnectionSource connectionSource;


    // === 3. CONSTRUCTORS ===
    // --- 3.1 STATIC BLOCKS ---
    // --- 3.2 INSTANCE INITIALIZER ---
    // --- 3.3 REAL CONSTRUCTORS ---
    private DatabaseManager() {};


    // === 4. STATIC METHODS ===
    // === 5. SETTER AND GETTER ===
    public static DatabaseManager getInstance() {
        return INSTANCE;
    }

    public Dao<WatchlistMovieEntity, Long> getAccountDao() {
        return this.accountDao;
    }


    // === 6. MISCELLANEOUS OBJECT METHODS ===
    public void initializeConnection(String username, String password) throws SQLException {
        createConnectionSource(username, password);
        accountDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
        createTables();
    }

    private void createConnectionSource(String username, String password) throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL, username, password);
    }

    private void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }


    // === 7. MAIN ===
}

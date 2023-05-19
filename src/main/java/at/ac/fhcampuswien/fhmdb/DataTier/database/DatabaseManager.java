package at.ac.fhcampuswien.fhmdb.DataTier.database;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.sql.SQLException;


public class DatabaseManager {
    // === 1. CLASS VARIABLES ===
    public static final String DB_URL = "jdbc:h2:file: ./db/watchlistdb";
    private static JdbcConnectionSource connectionSource;


    // === 2. OBJECT VARIABLES ===
    Dao<WatchlistMovieEntity, Long> dao;


    // === 3. CONSTRUCTORS ===
    // --- 3.1 STATIC BLOCKS ---
    // --- 3.2 INSTANCE INITIALIZER ---
    // --- 3.3 REAL CONSTRUCTORS ---
    // === 4. STATIC METHODS ===
    // === 5. SETTER AND GETTER ===
    // === 6. MISCELLANEOUS OBJECT METHODS ===
    private static void createConnectionSource(String username, String password) throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL, username, password);
    }


    // === 7. MAIN ===
}

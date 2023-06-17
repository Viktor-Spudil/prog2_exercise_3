package at.ac.fhcampuswien.fhmdb.DataTier;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.util.List;


public class MovieAPI {
    // === 0. NOTES ===
    // === 1. CLASS VARIABLES ===
    // === 2. OBJECT VARIABLES ===
    private final OkHttpClient client;
    private final Gson gson = new Gson();
    //private final String BASE_URL = "https://prog2.fh-campuswien.ac.at/movies";
    private final String BASE_URL = "http://localhost:8080/movies";


    // === 3. CONSTRUCTORS ===
    // --- 3.1 STATIC BLOCKS ---
    // --- 3.2 INSTANCE INITIALIZER ---
    // --- 3.3 REAL CONSTRUCTORS ---
    public MovieAPI() {
        client = new OkHttpClient();
    }
    // === 4. STATIC METHODS ===
    // === 5. GETTER AND SETTER ===
    // === 6. MISCELLANEOUS OBJECT METHODS ===

    public List<Movie> synchronousGETMoviesList(String searchQuery, Object genre, String releasedYear, String ratingFrom) throws MovieApiException {
        List<Movie> moviesList;
        String url;
        MovieApiUrlBuilder builder = new MovieApiUrlBuilder(BASE_URL);

        url = builder.query(searchQuery).genre(genre).releaseYear(releasedYear).ratingFrom(ratingFrom).build();
        System.out.println(url);

        // Make request and receive response
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Call call = client.newCall(request);
            Response response = call.execute();

            // Parse response
            String jsonString = response.body().string();
            TypeToken<List<Movie>> collectionType = new TypeToken<>() {};
            moviesList = gson.fromJson(jsonString, collectionType); //reflections
        } catch (IOException i) {
            throw new MovieApiException(i);
        }

        return moviesList;
    }


    // === 7. MAIN ===
}

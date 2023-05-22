package at.ac.fhcampuswien.fhmdb.DataTier;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import      at.ac.fhcampuswien.fhmdb.models.Movie;

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
    private String buildURL(String searchQuery, Object genre, String releasedYear, String ratingFrom) throws MovieApiException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();


        // Build request URL
        if (!(searchQuery == null) && !searchQuery.isBlank()) {
            urlBuilder.addQueryParameter("query", searchQuery);
        }
        if (!(genre == null) && !genre.toString().equals("No filter")) {
            urlBuilder.addQueryParameter("genre", genre.toString());
        }
        if (!(releasedYear == null) && !releasedYear.isBlank()) {
            urlBuilder.addQueryParameter("releaseYear", releasedYear);
        }
        if (!(ratingFrom == null) && !ratingFrom.isBlank()) {
            urlBuilder.addQueryParameter("ratingFrom", ratingFrom);
        }

        return urlBuilder.build().toString();
    }

    public List<Movie> synchronousGETMoviesList(String searchQuery, Object genre, String releasedYear, String ratingFrom) throws MovieApiException {
        List<Movie> moviesList;
        String url;

        url = buildURL(searchQuery, genre, releasedYear, ratingFrom);
        System.out.println(url);

        // Make request and receive response
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new MovieApiException(e);
        }

        // Parse response
        String jsonString = null;
        try {
            jsonString = response.body().string();
        } catch (IOException e) {
            throw new MovieApiException(e);
        }
        TypeToken<List<Movie>> collectionType = new TypeToken<>() {};
        moviesList = gson.fromJson(jsonString, collectionType); //reflections

        return moviesList;
    }


    // === 7. MAIN ===
}

package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class MovieAPI {
    // === 0. NOTES ===
    // === 1. CLASS VARIABLES ===
    // === 2. OBJECT VARIABLES ===
    private final OkHttpClient client;
    private final Gson gson = new Gson();


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
    public List<Movie> synchronousGETMoviesList(String BASE_URL, String searchQuery, Object genre, String releasedYear, String ratingFrom) throws IOException {
        List<Movie> moviesList;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        String url;

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
        url = urlBuilder.build().toString();
        System.out.println(url);

        // Make request and receive response
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        // Parse response
        String jsonString = response.body().string();
        TypeToken<List<Movie>> collectionType = new TypeToken<List<Movie>>() {};
        moviesList = gson.fromJson(jsonString, collectionType);

        return moviesList;
    }


    // === 7. MAIN ===
}

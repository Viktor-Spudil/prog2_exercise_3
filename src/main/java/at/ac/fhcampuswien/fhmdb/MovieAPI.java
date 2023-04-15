package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call;

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
    public List<Movie> synchronousGETMoviesList(String url) throws IOException {
        List<Movie> moviesList;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        String jsonString = response.body().string();
        TypeToken<List<Movie>> collectionType = new TypeToken<List<Movie>>() {};
        moviesList = gson.fromJson(jsonString, collectionType);

        return moviesList;
    }


    // === 7. MAIN ===
}

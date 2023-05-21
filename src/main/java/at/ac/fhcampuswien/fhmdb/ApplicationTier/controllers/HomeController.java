package at.ac.fhcampuswien.fhmdb.ApplicationTier.controllers;

import at.ac.fhcampuswien.fhmdb.DataTier.MovieAPI;
import at.ac.fhcampuswien.fhmdb.DataTier.database.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.DataTier.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.PresentationTier.MovieCell;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class HomeController implements Initializable {

    @FXML
    public JFXButton searchBtn;
    @FXML
    public TextField searchField;
    @FXML
    public JFXListView movieListView;
    @FXML
    public JFXComboBox genreComboBox;
    @FXML
    public TextField releaseYearField;
    @FXML
    public TextField ratingField;
    @FXML
    public JFXButton sortBtn;
    @FXML
    public Button homeButton;
    @FXML
    public Button watchlistButton;
    public List<Movie> allMovies;
    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    protected ObservableList<Movie> watchlist = FXCollections.observableArrayList();
    protected SortedState sortedState;
    private MovieAPI movieAPI = new MovieAPI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeLayout();
    }

    public void initializeState() throws IOException {
        allMovies = movieAPI.synchronousGETMoviesList(null, null, null, null);
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;
    }

    public void initializeLayout() {
        loadHomeView();

        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");
    }

    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            sortMovies(SortedState.ASCENDING);
        } else if (sortedState == SortedState.ASCENDING) {
            sortMovies(SortedState.DESCENDING);
        }
    }

    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending
    public void sortMovies(SortedState sortDirection) {
        if (sortDirection == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }

    public void applyAllFilters(String searchQuery, Object genre, String releasedYear, String ratingFrom) throws IOException {
        List<Movie> filteredMovies;

        filteredMovies = movieAPI.synchronousGETMoviesList(searchQuery, genre, releasedYear, ratingFrom);

        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }

    public void searchBtnClicked(ActionEvent actionEvent) throws IOException {
        String searchQuery = searchField.getText().trim().toLowerCase();
        Object genre = genreComboBox.getSelectionModel().getSelectedItem();
        String releasedYear = releaseYearField.getText();
        String ratingFrom = ratingField.getText();

        applyAllFilters(searchQuery, genre, releasedYear, ratingFrom);
        sortMovies(sortedState);
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }

    // Functionalities for movie management
    public void loadHomeView() {
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> {
            MovieCell cell = new MovieCell();

            // Access the watchlist button from the cell
            Button watchlistButton = cell.getWatchlistButton();
            watchlistButton.setText("Watchlist");

            // Assign a new functionality to the watchlist button
            watchlistButton.setOnAction(event -> {
                Movie selectedMovie = cell.getItem();
                addToWatchlist(selectedMovie);
            });
            return cell;
        });
    }
    public void homeViewBtnClicked(ActionEvent actionEvent) {loadHomeView();}

    public void loadWatchlistView() {
        movieListView.setItems(watchlist);
        movieListView.setCellFactory(movieListView -> {
            MovieCell cell = new MovieCell();

            // Access the watchlist button from the cell
            Button watchlistButton = cell.getWatchlistButton();
            watchlistButton.setText("Remove");

            // Assign a new functionality to the watchlist button
            watchlistButton.setOnAction(event -> {
                Movie selectedMovie = cell.getItem();
                removeFromWatchlist(selectedMovie);
            });
            return cell;
        });
    }
    public void watchlistBtnClicked(ActionEvent actionEvent) {loadWatchlistView();}

    private void addToWatchlist(Movie selectedMovie) {
        if (!watchlist.contains(selectedMovie)) {
            watchlist.add(selectedMovie);
            // Add code to store movie in the database or perform other operations
        }
    }

    public void removeFromWatchlist(Movie selectedMovie) {
        if (watchlist.contains(selectedMovie)) {
            watchlist.remove(selectedMovie);
        }
    }
}

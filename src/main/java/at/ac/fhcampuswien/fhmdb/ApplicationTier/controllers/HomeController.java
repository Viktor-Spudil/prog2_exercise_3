package at.ac.fhcampuswien.fhmdb.ApplicationTier.controllers;

import at.ac.fhcampuswien.fhmdb.DataTier.MovieAPI;
import at.ac.fhcampuswien.fhmdb.DataTier.database.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.DataTier.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.DataTier.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.PresentationTier.MovieCell;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.models.ViewState;
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
    private List<Movie> homeviewlist;
    private List<Movie> watchlist = new ArrayList<>();
    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    protected SortedState sortedState;
    private ViewState viewState;
    private MovieAPI movieAPI = new MovieAPI();
    public static WatchlistRepository watchlistRepository;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException s) {
            s.printStackTrace();
        }
        initializeLayout();
    }

    public void initializeState() throws IOException, SQLException {
        homeviewlist = movieAPI.synchronousGETMoviesList(null, null, null, null);
        sortedState = SortedState.NONE;

        // Initialize Database and create watchlist repository
        DatabaseManager.getInstance().initializeConnection("username", "password");
        watchlistRepository = new WatchlistRepository();
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
        if (viewState == ViewState.WATCHLIST) {
            return;
        }
        homeviewlist.clear();

        homeviewlist = movieAPI.synchronousGETMoviesList(searchQuery, genre, releasedYear, ratingFrom);

        observableMovies.clear();
        observableMovies.addAll(homeviewlist);
    }

    public void loadHomeView() {
        observableMovies.clear();
        observableMovies.addAll(homeviewlist);
        movieListView.setItems(observableMovies);

        movieListView.setCellFactory(movieListView -> {
            MovieCell cell = new MovieCell(onAddToWatchlistClicked, this);

            cell.getWatchlistButton().setText("Watchlist");

            return cell;
        });

        viewState = ViewState.HOMEVIEW;
    }

    public void loadWatchlistView() {
        watchlist.clear();

        try {
            watchlist = watchlistMovieEntityListToMovielist(watchlistRepository.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        observableMovies.clear();
        observableMovies.addAll(watchlist);
        movieListView.setItems(observableMovies);

        movieListView.setCellFactory(movieListView -> {
            MovieCell cell = new MovieCell(onRemoveFromWatchlistClicked, this);

            cell.getWatchlistButton().setText("Remove");

            return cell;
        });

        viewState = ViewState.WATCHLIST;
    }

    List<Movie> watchlistMovieEntityListToMovielist(List<WatchlistMovieEntity> watchlistMovieEntityList) {
        ArrayList<Movie> movieList = new ArrayList<>();

        for (WatchlistMovieEntity wme : watchlistMovieEntityList) {
            movieList.add(WatchlistRepository.WatchlistMovieEntityToMovie(wme));
        }

        return movieList;
    }

    private final ClickEventHandler onAddToWatchlistClicked = (clickedItem, controller) ->
    {
        HomeController.watchlistRepository.addToWatchlist((Movie) clickedItem);
    };

    private final ClickEventHandler onRemoveFromWatchlistClicked = (clickedItem, controller) ->
    {
        HomeController.watchlistRepository.removeFromWatchlist((Movie) clickedItem);
        controller.loadWatchlistView();
    };

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

    public void homeViewBtnClicked(ActionEvent actionEvent) {
        loadHomeView();
    }

    public void watchlistBtnClicked(ActionEvent actionEvent) {
        loadWatchlistView();
    }
}

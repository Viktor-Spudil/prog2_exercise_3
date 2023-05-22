package at.ac.fhcampuswien.fhmdb.PresentationTier;

import at.ac.fhcampuswien.fhmdb.ApplicationTier.controllers.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final Label releaseYear = new Label();
    private final Label rating = new Label();
    private final Button showDetails = new Button();
    private final Button watchlistButton = new Button();
    private final VBox layout = new VBox();
    private final HBox buttonContainer = new HBox(showDetails, watchlistButton);


    public MovieCell(ClickEventHandler watchlistButtonClicked) {
        super();
        watchlistButton.setOnMouseClicked(mouseEvent -> {
            try {
                watchlistButtonClicked.onClick(getItem());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        buttonContainer.setSpacing(10);
        layout.getChildren().addAll(title, detail, genre, releaseYear, rating, buttonContainer);
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setFillWidth(true);
        VBox.setVgrow(detail, Priority.ALWAYS);
    }

    public Button getWatchlistButton() {
        return watchlistButton;
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setGraphic(null);
            setText(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );

            String genres = movie.getGenres()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
            genre.setText(genres);

            releaseYear.setText("Release Year: " + movie.getReleaseYear());
            rating.setText("Rating: " + movie.getRating());
            showDetails.minHeight(10);
            showDetails.minWidth(70);
            showDetails.setText("Show Details");
            watchlistButton.minHeight(10);
            watchlistButton.minWidth(50);


            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            genre.getStyleClass().add("text-white");
            genre.setStyle("-fx-font-style: italic");
            releaseYear.getStyleClass().add("text-white");
            rating.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(layout);
        }
    }
}


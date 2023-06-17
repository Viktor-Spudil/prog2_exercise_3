package at.ac.fhcampuswien.fhmdb.DataTier;

public class MovieApiUrlBuilder {

    //http://localhost:8080/movies
    // query=man
    // genre=ACTION
    // releaseYear=1998
    // ratingFrom=6

    //http://localhost:8080/movies
    // ?query=ad
    // &genre=ACTION
    // &releaseYear=1998
    // &ratingFrom=7

    private String baseUrl;
    private String query;
    private String genre;
    private String releaseYear;
    private String ratingFrom;


    MovieApiUrlBuilder (String baseUrl){
        this.baseUrl = baseUrl;
    }

    public String build() {
        String url = baseUrl;
        int value = 0;

        if(query != null && !query.isEmpty()) {
            url += value == 0 ? "?query=" + query : "&query=" + query;
            value++;
        }
        if(genre != null && !genre.isEmpty()) {
            url += value == 0 ? "?genre=" + genre : "&genre=" + genre;
            value++;
        }
        if(releaseYear != null && !releaseYear.isEmpty()) {
            url += value == 0 ? "?releaseYear=" + releaseYear : "&releaseYear=" + releaseYear;
            value++;
        }
        if(ratingFrom != null && !ratingFrom.isEmpty()) {
            url += value == 0 ? "?ratingFrom=" + ratingFrom : "&ratingFrom=" + ratingFrom;
            value++;
        }
        return url;
    }

    public MovieApiUrlBuilder query(String query) {
        this.query = query;
        return this;
    }
    public MovieApiUrlBuilder genre(Object genre) {
        if(genre != null) {
            this.genre = genre.toString();
        } else {
            this.genre = "";
        }
        return this;
    }
    public MovieApiUrlBuilder releaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }
    public MovieApiUrlBuilder ratingFrom(String ratingFrom) {
        this.ratingFrom = ratingFrom;
        return this;
    }





}

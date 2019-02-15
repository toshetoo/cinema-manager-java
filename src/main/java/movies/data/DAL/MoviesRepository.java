package movies.data.DAL;

import movies.data.db.DataProvider;
import movies.models.Movie;

import java.util.List;
import java.util.UUID;

/**
 * Repository class that provide extended methods for searching of {@link Movie} in the DB collection
 */
public class MoviesRepository {

    /**
     * @return A collection of all {@link Movie}
     */
    public List<Movie> getAllMovies() {
        return DataProvider.getMovies();
    }

    /**
     * @param movieId the ID of the movie we want to find
     * @return A {@link Movie} with the corresponding ID or NULL if the movie with the same ID does not exist
     */
    public Movie getMovieById(UUID movieId) {
        return DataProvider.getMovies().stream().filter(m -> m.getMovieId() == movieId).findFirst().orElse(null);
    }
}


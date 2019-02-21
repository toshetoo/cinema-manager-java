package movies.data.DAL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class MoviesRepositoryTest {

    private MoviesRepository repo;

    @Before
    public void setUp() {
        this.repo = new MoviesRepository();
    }

    @Test
    public void testGetAllMovies() {
        Assert.assertNotNull("Should return a list of movies that is not null", repo.getAllMovies());
        Assert.assertEquals("Should return a 9 movies", 9, repo.getAllMovies().size());
    }

    @Test
    public void testGetMovieByCorrectId() {
        UUID id = this.repo.getAllMovies().get(0).getMovieId();

        Assert.assertNotNull("With correct UUID should return a movie that is not null", repo.getMovieById(id));
    }

    @Test
    public void testGetMovieByIncorrectId() {
        Assert.assertNull("With incorrect UUID should return null", repo.getMovieById(UUID.randomUUID()));
    }
}

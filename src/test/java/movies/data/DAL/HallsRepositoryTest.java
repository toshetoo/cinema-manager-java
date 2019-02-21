package movies.data.DAL;

import movies.data.db.DataProvider;
import movies.models.Reservation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class HallsRepositoryTest {

    private HallsRepository repo;
    private Reservation r;

    @Before
    public void setUp() {
        this.repo = new HallsRepository();

        UUID id = UUID.randomUUID();
        r = Mockito.mock(Reservation.class);
        when(r.getReservedOn()).thenReturn(LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 0, 0 ,0));
        when(r.getCount()).thenReturn(10);
        when(r.getReservedForMovie()).thenReturn(DataProvider.getMovies().get(0).getMovieId());
        when(r.getId()).thenReturn(id);
        when(r.getHallId()).thenReturn(DataProvider.getHalls().get(5).getHallId());
    }

    @Test
    public void testGetAllHalls() {
        Assert.assertNotNull("Should return a list of halls that is not null", repo.getAllHalls());
        Assert.assertEquals("Should return 9 halls", 9, repo.getAllHalls().size());
    }

    @Test
    public void testAllHallsShouldHaveMovies() {
        Assert.assertTrue("All halls should have assigned movies", repo.getAllHalls().stream().allMatch(h -> h.getCurrentPlayingMovie() != null));
    }

    @Test
    public void testAllHallsShouldHaveSeats() {
        Assert.assertTrue("All halls should have assigned seats", repo.getAllHalls().stream().allMatch(h -> h.getSeats() != null));
    }

    @Test
    public void testAllHallsShouldHaveEqualSeatsAsHallSize() {
        Assert.assertTrue("Number of seats should be the same as hall size", repo.getAllHalls().stream().allMatch(h -> h.getSeats().size() == h.getCapacity()));
    }

    @Test
    public void testGetHallByCorrectId() {
        UUID id = this.repo.getAllHalls().get(0).getHallId();

        Assert.assertNotNull("With correct UUID should return a hall that is not null", repo.getHallById(id));
    }

    @Test
    public void testGetHallByIncorrectId() {
        Assert.assertNull("With incorrect UUID should return null", repo.getHallById(UUID.randomUUID()));
    }

    @Test
    public void testGetFreeHalls() {
        UUID id = this.repo.getAllHalls().get(0).getHallId();
        when(r.getHallId()).thenReturn(id);
        Assert.assertEquals("Initially, all hall should be free", 9, this.repo.getFreeHalls().size());

        ReservationsRepository repo = new ReservationsRepository();
        try {
            repo.createReservation(r);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals("Should  have only 8 free halls", 8, this.repo.getFreeHalls().size());
        repo.deleteReservation(r);

        Assert.assertEquals("Finally, all hall should be free", 9, this.repo.getFreeHalls().size());
    }

    @Test
    public void testGetFreeHallSeats() {
        UUID id = this.repo.getAllHalls().get(8).getHallId();
        when(r.getCount()).thenReturn(5);
        when(r.getHallId()).thenReturn(id);
        Assert.assertEquals("Initially, all hall seats should be free", 10, this.repo.getFreeHallSeats(id).size());

        ReservationsRepository repo = new ReservationsRepository();
        try {
            repo.createReservation(r);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals("Should  have only 5 free seats", 5, this.repo.getFreeHallSeats(id).size());
        repo.deleteReservation(r);

        Assert.assertEquals("Finally, all hall seats should be free", 10, this.repo.getFreeHallSeats(id).size());
    }

    @Test
    public void testMarkSeatsAsTaken() {
        UUID id = this.repo.getAllHalls().get(5).getHallId();
        when(r.getCount()).thenReturn(5);
        when(r.getHallId()).thenReturn(id);
        Assert.assertEquals("Initially, all hall seats should be free", 10, this.repo.getFreeHallSeats(id).size());

       this.repo.markSeatsAsTaken(r.getHallId(), r.getCount());

        Assert.assertEquals("Should  have only 5 free seats", 5, this.repo.getFreeHallSeats(id).size());

        this.repo.markSeatsAsFree(r.getHallId(), r.getCount());
    }

    @Test
    public void testMarkSeatsAsFree() {
        UUID id = this.repo.getAllHalls().get(5).getHallId();
        when(r.getCount()).thenReturn(5);
        when(r.getHallId()).thenReturn(id);
        Assert.assertEquals("Initially, all hall seats should be free", 10, this.repo.getFreeHallSeats(id).size());

        this.repo.markSeatsAsTaken(r.getHallId(), r.getCount());

        Assert.assertEquals("Should  have only 5 free seats", 5, this.repo.getFreeHallSeats(id).size());

        this.repo.markSeatsAsFree(r.getHallId(), r.getCount());

        Assert.assertEquals("Finally, all hall seats should be free", 10, this.repo.getFreeHallSeats(id).size());
    }
}

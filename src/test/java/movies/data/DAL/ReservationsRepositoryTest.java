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

public class ReservationsRepositoryTest {

    private ReservationsRepository repo;
    private Reservation r;

    @Before
    public void setUp() {
        this.repo = new ReservationsRepository();

        UUID id = UUID.randomUUID();
        r = Mockito.mock(Reservation.class);
        when(r.getReservedOn()).thenReturn(LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 0, 0 ,0));
        when(r.getCount()).thenReturn(3);
        when(r.getReservedForMovie()).thenReturn(DataProvider.getMovies().get(0).getMovieId());
        when(r.getId()).thenReturn(id);
        when(r.getHallId()).thenReturn(DataProvider.getHalls().get(0).getHallId());
    }

    @Test
    public void testGetAllReservations() {
        Assert.assertNotNull("Should return a list of reservations that is not null", repo.getAllReservations());
    }

    @Test
    public void testGetReservationByIdWithCorrectId() {
        UUID id = UUID.randomUUID();
        Reservation r = Mockito.mock(Reservation.class);
        when(r.getId()).thenReturn(id);
        DataProvider.addReservation(r);

        Assert.assertNotNull("Reservation with correct id should not be null", repo.getReservationById(id));

        DataProvider.removeReservation(r);
    }

    @Test
    public void testGetReservationByIdWithIncorrectId() {
        Assert.assertNull("Reservation with incorrect id should be null", repo.getReservationById(UUID.randomUUID()));
    }

    @Test
    public void testCreateReservation() {
        when(r.getHallId()).thenReturn(DataProvider.getHalls().get(7).getHallId());
        try {
            repo.createReservation(r);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals("Reservation with a correct date should be created", 1, repo.getAllReservations().size());
        repo.deleteReservation(r);
    }

    @Test(expected = Exception.class)
    public void testCreateReservationWithMorePeople() throws Exception {
        when(r.getCount()).thenReturn(11);
        repo.createReservation(r);
        repo.deleteReservation(r);
    }

    @Test(expected = Exception.class)
    public void testCreateReservationWithWrongTime() throws Exception {
        when(r.getHallId()).thenReturn(DataProvider.getHalls().get(2).getHallId());
        when(r.getReservedOn()).thenReturn(LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 23, 59 ,59));
        repo.createReservation(r);
        repo.deleteReservation(r);
    }

    @Test
    public void testDeleteReservation() {
        when(r.getHallId()).thenReturn(DataProvider.getHalls().get(3).getHallId());
        DataProvider.addReservation(r);

        Assert.assertEquals("There should be one reservation initially", 1, repo.getAllReservations().size());

        repo.deleteReservation(r);

        Assert.assertEquals("There should be no reservations", 0, repo.getAllReservations().size());
    }
}

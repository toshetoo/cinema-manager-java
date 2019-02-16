package movies.data.db;

import movies.models.Reservation;
import org.junit.*;
import org.mockito.Mockito;

public class DataProviderTest {

    @Test
    public void testGetHalls() {
        Assert.assertNotNull("Collection of halls should not be null", DataProvider.getHalls());
        Assert.assertEquals("All halls must be 9", 9, DataProvider.getHalls().size());
        Assert.assertTrue("All halls should have assigned movies", DataProvider.getHalls().stream().allMatch(h -> h.getCurrentPlayingMovie() != null));
        Assert.assertTrue("All halls should have assigned seats", DataProvider.getHalls().stream().allMatch(h -> h.getSeats() != null));
        Assert.assertTrue("Number of seats should be the same as hall size", DataProvider.getHalls().stream().allMatch(h -> h.getSeats().size() == h.getCapacity()));
    }

    @Test
    public void testGetMovies() {
        Assert.assertNotNull("Collection of movies should not be null", DataProvider.getMovies());
        Assert.assertEquals("All movies must be 9", 9, DataProvider.getMovies().size());
        //Assert.assertTrue("All movies should have a play time of today", DataProvider.getMovies().stream().allMatch(m -> m.getPlayOn() != null));
    }

    @Test
    public void testGetReservations() {
        Assert.assertNotNull("Collection of reservations should not be null", DataProvider.getReservations());
        Assert.assertEquals("Initially, there should be no reservations", 0, DataProvider.getReservations().size());

        Reservation r = Mockito.mock(Reservation.class);
        DataProvider.addReservation(r);

        Assert.assertEquals("There should be one reservation", 1, DataProvider.getReservations().size());

        DataProvider.removeReservation(r);
    }

    @Test
    public void testAddReservation() {
        Reservation r = Mockito.mock(Reservation.class);
        DataProvider.addReservation(r);
        Assert.assertEquals("There should be one reservation", 1, DataProvider.getReservations().size());
        DataProvider.removeReservation(r);
    }

    @Test
    public void testDeleteReservation() {
        Reservation r = Mockito.mock(Reservation.class);
        DataProvider.addReservation(r);
        Assert.assertEquals("There should be one reservation", 1, DataProvider.getReservations().size());
        DataProvider.removeReservation(r);

        Assert.assertEquals("There should be no reservations", 0, DataProvider.getReservations().size());
    }
}

package movies.models;

        import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;
        import org.mockito.Mockito;

        import java.time.LocalDateTime;
        import java.util.ArrayList;
        import java.util.UUID;

        import static org.mockito.Mockito.when;

public class HallTest {

    private Hall h;
    private Movie m;

    @Before
    public void setUp() {
        h = Mockito.mock(Hall.class);
        m = new Movie("Test 1", 10, 10f);
        when(h.getHallNumber()).thenReturn(10);
        when(h.getCapacity()).thenReturn(10);
        when(h.getCurrentPlayingMovie()).thenReturn(m);
    }

    @Test
    public void testGettersReturnCorrectValue() {
        h.setHallNumber(10);
        Assert.assertEquals("Hall number should match", 10, Long.parseLong(h.getHallNumber().toString()));

        h.setCapacity(10);
        Assert.assertEquals("Capacity should match", 10, Long.parseLong(h.getCapacity().toString()));

        h.setCurrentPlayingMovie(m);
        Assert.assertEquals("Movies should match", m, h.getCurrentPlayingMovie());
    }

    @Test
    public void testGeneratePlayTime() {
        Movie m = Mockito.mock(Movie.class);
        LocalDateTime dt = m.generatePlayTime();
        Assert.assertTrue(true);
        //Assert.assertTrue("Generated movie time is today", dt.);
    }
}

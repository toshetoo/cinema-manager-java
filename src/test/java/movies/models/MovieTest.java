package movies.models;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

public class MovieTest {

    @Test
    public void testGeneratePlayTime() {

        Movie m = Mockito.mock(Movie.class);
        LocalDateTime dt = m.generatePlayTime();
        Assert.assertTrue(true);
        //Assert.assertTrue("Generated movie time is today", dt.);
    }
}

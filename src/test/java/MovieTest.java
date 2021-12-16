/**
 * NOTE: This Test class is already complete and should be used as reference for the other test cases
 */

import models.Movie;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MovieTest {
    /**
     * This test is for the 'Getters'
     */
    @Test
    public void testGetters() {
        String testName = "Test Name";
        String testDirector = "Test Director";
        String genre = "Test Genre";
        int quantity = 12;

        Movie movie = new Movie(testName, testDirector, genre, quantity);

        Assert.assertEquals(movie.getTitle(), testName);
        Assert.assertEquals(movie.getDirector(), testDirector);
        Assert.assertEquals(movie.getGenre(), genre);
        Assert.assertEquals(movie.getQuantity(), quantity);
    }

    /**
     * This test is for the 'toString' override
     */
    @Test
    public void testToString() {
        String testName = "Test Name";
        String testDirector = "Test Director";
        String testGenre = "Test Genre";

        // This is the expected format of how the movie is returned after 'toString' is called
        String unformattedExpectedString = "MOVIE TITLE: '%s' directed by '%s' Genre: '%s'";

        Movie movie = new Movie(testName, testDirector, testGenre, 12);

        Assert.assertEquals(movie.toString(), String.format(unformattedExpectedString, testName, testDirector, testGenre));
    }

    /**
     * This test is for the 'toStringGenre' method
     */
    @Test
    public void testToStringGenre() {
        String testName = "Test Name";
        String testDirector = "Test Director";
        String testGenre = "Test Genre";

        // This is the expected format of how the movie is returned after 'toStringGenre' is called
        String unformattedExpectedString = "%s";

        Movie movie = new Movie(testName, testDirector, testGenre, 12);

        Assert.assertEquals(movie.toStringGenre(), String.format(unformattedExpectedString, testGenre));
    }

    /**
     * This test is for the 'equals' override
     */
    @Test
    public void testEquals() {
        Movie movie = new Movie("TITLE1", "DIRECTOR1", "GENRE1", 12);
        Movie wrongMovie = new Movie("WRONGTITLE", "WRONGDIRECTOR", "WRONGGENRE", 12);
        Movie duplicateMovie = new Movie("TITLE1", "DIRECTOR1", "GENRE1", 12);

        Assert.assertFalse(movie.equals(wrongMovie));
        Assert.assertTrue(movie.equals(duplicateMovie));
    }
}
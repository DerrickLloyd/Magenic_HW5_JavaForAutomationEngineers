import models.Movie;
import models.MovieDatabase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class MovieDatabaseTest {
    /**
     * This test is for adding and deleting of movies
     */
    @Test
    public void testAddDeleteMovie() {
        MovieDatabase database = new MovieDatabase();
        Movie movieOne = new Movie("MovieOne", "Steven Spielberg", "Thriller", 1);

        database.addMovie(movieOne);
        Assert.assertTrue(database.getMovieArchive().contains(movieOne));
        database.deleteMovie(movieOne);
        Assert.assertFalse(database.getMovieArchive().contains(movieOne));
    }

    /**
     * This test is for adding and deleting of movielist
     */
    @Test
    public void testAddGetDeleteMovielist() {
        MovieDatabase database = new MovieDatabase();
        String movieListName = "MovieListOne";
        database.createMovielist(movieListName);
        Assert.assertTrue(database.getMovielists().containsKey(movieListName));
        Assert.assertNotNull(database.getMovielist(movieListName));
        database.deleteMovielist(movieListName);
        Assert.assertFalse(database.getMovielists().containsKey(movieListName));
        Assert.assertNull(database.getMovielist(movieListName));
    }

    /**
     * This test is for adding and deleting of movies from a movielist
     */
    @Test
    public void testAddDeleteMovieFromMovielist() {
        // Creates the MovieDatabase instance and the testing variables
        MovieDatabase database = new MovieDatabase();
        String movielistName = "MovielistOne";
        Movie movieOne = new Movie("MovieOne", "Director1", "Romance", 12);
        Movie movieTwo = new Movie("MovieTwo", "Director2", "Comedy", 87);

        // Creates the database and verifies the database is added correctly
        database.createMovielist(movielistName);
        Assert.assertTrue(database.getMovielists().containsKey(movielistName));

        // Adds the first movie to the movielist and asserts the movie was added
        database.addMovieToMovielist(movieOne, movielistName);
        Assert.assertTrue(database.getMovielists().get(movielistName).contains(movieOne));

        // Adds the second movie to the movielist and asserts the movie was added
        database.addMovieToMovielist(movieTwo, movielistName);
        Assert.assertTrue(database.getMovielists().get(movielistName).contains(movieTwo));

        // Deleted the second movie from the movielist
        database.addMovieToMovielist(movieTwo, movielistName);

        // Deletes the same movie a second time, ensure the code handles it correctly by not throwing an error
        database.deleteMovieFromMovielist(movieTwo, movielistName);

        // Verifies the first movie is still in the movielist and the second movie is not in the database
        Assert.assertTrue(database.getMovielists().get(movielistName).contains(movieOne));
        Assert.assertFalse(database.getMovielists().get(movielistName).contains(movieTwo));
    }

    /**
     * This test is for finding movies by genre
     */
    @Test
    public void testFindingMoviesByGenre() {
        // Creates the MovieDatabase instance and the testing variables
        MovieDatabase database = new MovieDatabase();
        Movie movieOne = new Movie("MovieOne", "Director1", "Romance", 12);
        Movie movieTwo = new Movie("MovieTwo", "Director2", "Comedy", 87);

        // Adds both movies to the database
        database.addMovie(movieOne);
        database.addMovie(movieTwo);

        // Finds all movies by movie name that match the first movie and verify that the first movie is found
        List<Movie> foundMovies = database.findMoviesByGenre("Romance");
        Assert.assertTrue(foundMovies.contains(movieOne));

        // Verify second movie is not found since it has different genre
        Assert.assertFalse(foundMovies.contains(movieTwo));
    }

    /**
     * This test is for computing the quantity of the movie after rent
     */
    @Test
    public void testNewQuantityAfterRent() {
        // Creates the MovieDatabase instance and the testing variables
        MovieDatabase database = new MovieDatabase();
        Movie movieOne = new Movie("MovieOne", "Director1", "Romance", 12);

        // Adds movie to the database
        database.addMovie(movieOne);

        //we will rent 12 copies of the movie
        int newQuantity = movieOne.getQuantity() - 12;

        //assert that the new quantity after the rent is correct
        Assert.assertEquals(database.newQuantityAfterBeingRented("MovieOne", 12), newQuantity);
    }
}
package models;

import java.util.*;

public class MovieDatabase {
    /**
     * A unique list of every movie in the models.Movie Database
     */
    private List<Movie> movieArchive;

    /**
     * The movielists are a map of movielist name to a list of movies
     */
    private Map<String, List<Movie>> movielists;

    /**
     * Creates an instance of the MovieDatabase class
     */
    public MovieDatabase() {
        // A TreeSet is a type of set, a unique element array
        movieArchive = new ArrayList<Movie>();
        // A Hashmap is a type of map, key to value collection
        movielists = new HashMap<String, List<Movie>>();
    }

    /**
     * Gets the movie archive
     * @return The movie archive
     */
    public List<Movie> getMovieArchive() {
        return movieArchive;
    }

    /**
     * Gets the movielists
     * @return The movielists
     */
    public Map<String, List<Movie>> getMovielists() {
        return movielists;
    }

    /**
     * Sets the Movie archive
     * @param movieArchive The new movie archive
     */
    protected void setMovieArchive(List<Movie> movieArchive) {
        this.movieArchive = movieArchive;
    }

    /**
     * Sets the movielists
     * @param movielists
     */
    protected void setMovielists(Map<String, List<Movie>> movielists) {
        this.movielists = movielists;
    }

    /**
     * Adds the given movie to the movie archive
     * @param newMovie The movie to be added
     */
    public void addMovie(Movie newMovie) {
        movieArchive.add(newMovie);
    }

    /**
     * Deletes the given movie from all movielists and the movie archive
     * @param movieToBeDeleted The movie to be deleted
     */
    public void deleteMovie(Movie movieToBeDeleted) {
        // First we must check if the movie is in our archive
        if (movieArchive.contains(movieToBeDeleted)) {
            // Cycles through each movie in the archive to find the matching movie
            for (Movie movie : movieArchive) {
                // Checks if the current movie is equal to the movie that should be deleted
                if (movie.equals(movieToBeDeleted)) {
                    // Two things must be done when we find the movie in the archives
                    // 1. Remove that movie from ALL movie list as the movie is no longer in the database
                    // 2. Remove the movie from the archive

                    // Cycles through each movie list name from the movie lists map
                    for (String movielistName : movielists.keySet()) {
                        // Calls the deleteMovieFromMovielist method which will check every movie in the movie list for deletion
                        deleteMovieFromMovielist(movie, movielistName);
                    }

                    // Removes the movie from the archives
                    movieArchive.remove(movie);

                    break;
                }
            }
        }
    }

    /**
     * Creates a new movie list with the given name
     * @param movielistName The movie list name
     * @throws IllegalArgumentException if a movie list already exists with the given name
     */
    public void createMovielist(String movielistName) throws IllegalArgumentException {
        // Checks if the movie lists already has a key for the given movielist name
        if (movielists.containsKey(movielistName)) {
            throw new IllegalArgumentException("Movie list Name already exists!");
        }

        // Puts the movie list name into the movie lists and maps it to a new empty list of movies
        movielists.put(movielistName, new ArrayList<Movie>());
    }

    /**
     * Gets the list of movies for the given movie list name
     * @param movielistName The movie list name
     * @return The list of movies for the movie list if that movie list exists, and null otherwise
     */
    public List<Movie> getMovielist(String movielistName) {
        if(movielists.containsKey(movielistName)){
            List<Movie> listOfMovielistMovies = movielists.get(movielistName);
            return listOfMovielistMovies;
        }
        // Returns null if no movies are found for the given movie list name
        else
            return null;
    }

    /**
     * Gets All the movielists name
     * @return The movielists name
     */
    public String[] getAllMovielist() {
        MovieStore movie = new MovieStore();
        // gets all the keys of movielists and add in into an ArrayList
        List<String> keys = new ArrayList<>();
        for ( String key : movielists.keySet() ) {
            keys.add(key);
        }

        // convert List to String[]
        String[] movielistNames = keys.toArray(new String[keys.size()]);

        return movielistNames;
    }

    /**
     * Deletes the movie list with the given movie list name
     * @param movielistName The movie list name
     */
    public void deleteMovielist(String movielistName) {
        // Checks if the movie lists contain a movie list with the given name
        if (movielists.containsKey(movielistName)) {
            // Removes the movie list of the given name
            movielists.remove(movielistName);
        }
    }

    /**
     * Adds a movie to the movie list
     * @param newMovie The new movie
     * @param movielistName The movie list name
     */
    public void addMovieToMovielist(Movie newMovie, String movielistName) {
        // Checks if the movie list name is in the movie lists
        if (movielists.containsKey(movielistName)) {
            // Gets the list of movies from the movie lists map
            List<Movie> listOfMovielistMovies = movielists.get(movielistName);

            if (!listOfMovielistMovies.contains(newMovie)){
                movielists.get(movielistName).add(newMovie);
            }
        }
    }

    /**
     * Deletes the given movie from the movie list that matches the given movie list name
     * @param movieToBeDeleted The movie to be deleted
     * @param movielistName The movie list name where the movie should be deleted
     */
    public void deleteMovieFromMovielist(Movie movieToBeDeleted, String movielistName) {
        // Checks if the movie list name is in the movie lists
        if (movielists.containsKey(movielistName)) {
            // Gets the list of movies from the movie lists map
            List<Movie> listOfMovielistMovies = movielists.get(movielistName);

            // Checks if the list of movies contain the movie to be deleted
            if (listOfMovielistMovies.contains(movieToBeDeleted)) {
                for (int index = listOfMovielistMovies.size() - 1; index >= 0; index--) {
                    // Checks if the current movie at the current index is the movie that should be deleted
                    if (movieToBeDeleted.equals(listOfMovielistMovies.get(index))) {
                        // Deleted the movie at the current index
                        movielists.get(movielistName).remove(index);
                    }
                }
            }
        }
    }

    /**
     * Returns a List of models.Movie object from archive
     * @return The List of models.Movie object
     */
    public List<Movie> returnAllMovies() {
        List<Movie> listOfMovies = new ArrayList<Movie>();
        for (Movie movie : movieArchive) {
            listOfMovies.add(movie);
        }
        return listOfMovies;
    }

    /**
     * Returns a List of models.Movie object from archive, and an empty list if no movie are found
     * @return The List of models.Movie object, and an empty list if no movies are found
     */
    public List<Movie> returnAllMoviesFromMovieListName(String movieListName) {
        List<Movie> listOfMovies = movielists.get(movieListName);
        return listOfMovies;
    }

    /**
     * Returns the new quantity of the movie after being rented
     * @param movieTitle
     * @param rent
     * @return newQuantity of the movie after being rented
     */
    public int newQuantityAfterBeingRented(String movieTitle, int rent) {
        int newQuantity = 0;
        for (Movie movie : movieArchive) {
            if (movie.getTitle().contains(movieTitle)) {
                movie.setQuantity(movie.getQuantity()-rent);
                newQuantity = movie.getQuantity();
            }
        }
        return newQuantity;
    }

    /**
     * The method returns the list of movies by specific genre
     * @param chosenGenre
     * @return the list of movies by specific genre
     */
    public List<Movie> findMoviesByGenre(String chosenGenre) {
        List<Movie> movieListWithSpecificGenre = new ArrayList<Movie>();
        for (Movie movie : movieArchive) {
            if (movie.getGenre().equals(chosenGenre)) {
                movieListWithSpecificGenre.add(movie);
            }
        }
        return movieListWithSpecificGenre;
    }

    /**
     * The main method of the Movie Database class
     * @param args The array of arguments
     */
    public static void main(String[] args) {
        MovieDatabase db = new MovieDatabase();

        db.addMovie(new Movie("Eternals", "Chloé Zhao", "Action", 5));
        db.addMovie(new Movie("No Time to Die", "Cary Joji Fukunaga", "Adventure", 8));
        db.addMovie(new Movie("Dune", "Denis Villeneuve", "Action", 9));
        System.out.println(db.getMovieArchive().size());
        int NewQuantity = db.newQuantityAfterBeingRented("Eternals",2);
        System.out.println("New quantity of the movie Eternals " + NewQuantity);


        System.out.println(db.movieArchive.contains(new Movie("Eternals", "Chloé Zhao", "Action", 6)));
        db.deleteMovie(new Movie("Eternals", "Chloé Zhao", "Action", 7));
        System.out.println(db.movieArchive.contains(new Movie("Eternals", "Chloé Zhao", "Action", 8)));
    }
}

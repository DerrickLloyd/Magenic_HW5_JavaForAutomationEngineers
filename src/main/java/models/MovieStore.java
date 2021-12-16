package models;

import resources.OptionStrings;
import utils.UserHelperMethods;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is a console application for the Movie Store
 */
public class MovieStore {
    /**
     * The movie database instance
     */
    private MovieDatabase movieDatabase;

    /**
     * The movie queue for movies to be played
     */
    private List<Movie> movieQueue;

    /**
     * The scanner reference to read input from the user
     */
    private Scanner scanner;

    /**
     * Creates an instance of the Movie Store class
     */
    public MovieStore() {
        // Creates a new MovieDatabase
        this.movieDatabase = new MovieDatabase();
        // Sets the movieQueue to an empty list
        movieQueue = new ArrayList<Movie>();
        // Sets the scanner to listing to System.in
        this.scanner = new Scanner(System.in);
    }

    /**
     * Gets the Movie Database
     * @return The movie database
     */
    public MovieDatabase getMovieDatabase() {
        return movieDatabase;
    }

    /**
     * Sets the Movie database
     * @param movieDatabase The new movie database
     */
    protected void setMovieDatabase(MovieDatabase movieDatabase) {
        this.movieDatabase = movieDatabase;
    }

    /**
     * Displays the main menu and handles the user choice
     */
    private void mainMenu() {
        // Displays the main menu options and gets the users choice
        int userChoice = UserHelperMethods.displayOptionsAndWaitForValidOption(OptionStrings.MAIN_MENU_OPTIONS);
        String userChoiceText = OptionStrings.MAIN_MENU_OPTIONS[userChoice];

        // Checks for each of the options and handles accordingly
        if (userChoiceText.equals("Play")) {
            // Navigates the user of to the 'Play Movie Trailer' menu
            playMovieTrailerMenu();
        } else if (userChoiceText.equals("Rent A Movie")) {
            // Navigates the user of to the 'Rent a Movie' menu
            RentAMovieMenu();
        } else if (userChoiceText.equals("Display All Movies by Genre")) {
            // Navigates the user of to the 'Display All Movies by Genre' menu
            SearchForMovieByGenreMenu();
        }else if (userChoiceText.equals("Manage Movie Movielists")) {
            // Navigates the user of to the 'Manage Movie lists' menu
            manageMovielistsMenu();
        }else if (userChoiceText.equals("Manage Movies")) {
            // Navigates the user of to the 'Manage Movies' menu
            manageMoviesMenu();
        } else {
            // Ends the program
            System.out.println("ENDING PROGRAM...");
            System.out.println("Thank you for using Movie Store!");
        }
    }

    /**
     * Displays the play movie trailer menu and handles the user choice
     */
    private void playMovieTrailerMenu() {
        // Displays the play menu options and gets the users choice
        int userChoice = UserHelperMethods.displayOptionsAndWaitForValidOption(OptionStrings.PLAY_OPTIONS);
        String userChoiceText = OptionStrings.PLAY_OPTIONS[userChoice];

        // Checks for each of the options and handles accordingly
        if (userChoiceText.equals("Play Movie Trailer")) {
            // Takes the user input for the movie name then plays the movie trailer
            System.out.println("Which movie trailer would you like to play: ");
            Movie movieTrailerToPlay = displayAllMovies();
            playMovieTrailer(movieTrailerToPlay);
        }

        // Waits for any playing songs to finish and returns to the Main Menu
        System.out.println("Returning to Main Menu...");
        mainMenu();
    }

    /**
     * Displays the rent a movie and handles the user choice
     */
    private void RentAMovieMenu() {

        // Gets the movie title that the user wants to rent
        System.out.println("Which movie would you like to rent: ");
        Movie movieTitleToRent = displayAllMovies();


        if (movieTitleToRent != null) {
            System.out.println("Available Quantity to rent for the movie: '" + movieTitleToRent.getTitle() + "' is " + movieTitleToRent.getQuantity() + " copies");

            int movieQuantityToRent = rentMovieQuantity(movieTitleToRent);
            rentMovie(movieTitleToRent.getTitle(), movieQuantityToRent);
            System.out.println("Returning to Main Menu...");
            mainMenu();
        } else {
            System.out.println("Returning to Main Menu...");
            mainMenu();
        }
    }

    /**
     * Displays all the movies by genre
     */
    private void SearchForMovieByGenreMenu() {

        System.out.println("Which genre would you like to display: ");
        List<Movie> movieTitleByGenre = displayAllMovieGenre();

        if (movieTitleByGenre != null) {
            displayAllMoviesWithSpecificGenre(movieTitleByGenre);

            System.out.println("Returning to Main Menu...");
            mainMenu();
        } else {
            System.out.println("Returning to Main Menu...");
            mainMenu();
        }
    }

    /**
     * Displays the manage movielists menu and handles the user choice
     */
    private void manageMovielistsMenu() {
        // Displays the manage movielists menu options and gets the users choice
        int userChoice = UserHelperMethods.displayOptionsAndWaitForValidOption(OptionStrings.MANAGE_MOVIELISTS_OPTIONS);
        String userChoiceText = OptionStrings.MANAGE_MOVIELISTS_OPTIONS[userChoice];

        // Checks for each of the options and handles accordingly
        if (userChoiceText.equals("Create Movie Movielist")) {
            // Takes the user input for the movielist name then tries to create a new movielist
            String movielistName = UserHelperMethods.getInputFromPrompt("What should the movielist be called:");
            tryCreateMovielist(movielistName);
        } else if (userChoiceText.equals("Show Movie Movielists")) {
            // Prints our every movielist to the user
            if(movieDatabase.getMovielists().size()!=0) {
                UserHelperMethods.printMap(movieDatabase.getMovielists());
            }
            else{
                System.out.println("No Movie Lists Available");
            }
        } else if (userChoiceText.equals("Add Movie to Movielist")) {
            // Takes the user input for the movielist name and Movie then adds the movie to the movielist
            String movielistName = getMovieListFromChoices("Movie List Name (choose from the choices below) :");
            String movieName = UserHelperMethods.getInputFromPrompt("Movie Name:");
            String director = UserHelperMethods.getInputFromPrompt("Director of the movie " + movieName + ":");
            String genre = getGenreFromChoices("Genre of the movie " + movieName + " (choose from the choices below) :");
            int quantity = UserHelperMethods.getIntegerInputFromPrompt("Quantity of the movie " + movieName + ":");
            Movie movieToAdd = new Movie(movieName, director, genre, quantity);
            movieDatabase.addMovieToMovielist(movieToAdd, movielistName);
        } else if (userChoiceText.equals("Delete Movie from Movielist")) {
            // Takes the user input for the movielist name and movie name then deletes that movie from the movielist
            String movielistName = getMovieListFromChoices("Movie List Name (choose from the choices below) :");
            Movie movieToRemove = displayAllMoviesFromMovielist(movielistName);
            movieDatabase.deleteMovieFromMovielist(movieToRemove, movielistName);
            System.out.println(""+movieToRemove.getTitle() +" is now deleted in "+movielistName);
        } else if (userChoiceText.equals("Delete Movielist")) {
            // Takes the user input for the movielist name then deletes that movielist
            String movielistName = getMovieListFromChoices("Movie List Name (choose from the choices below) :");
            movieDatabase.deleteMovielist(movielistName);
            System.out.println(""+movielistName +" movie list is now deleted");
        }

        // Returns to the Main Menu
        System.out.println("Returning to Main Menu...");
        mainMenu();
    }

    /**
     * Displays the manage movies menu and handles the user choice
     */
    private void manageMoviesMenu() {
        // Displays the manage movies menu options
        int userChoice = UserHelperMethods.displayOptionsAndWaitForValidOption(OptionStrings.MANAGE_MOVIES_OPTIONS);
        String userChoiceText = OptionStrings.MANAGE_MOVIES_OPTIONS[userChoice];

        // Checks for each of the options and handles accordingly
        if (userChoiceText.equals("Show Movie Library")) {
            // Prints out every movie to the user
            List<Movie> movies = new ArrayList<>(movieDatabase.getMovieArchive());
            UserHelperMethods.printMoviesWithQuantity(movies);
        } else if (userChoiceText.equals("Add Movie")) {
            // Takes the user input for the Movie then adds the movie
            String movieTitle = UserHelperMethods.getInputFromPrompt("Movie Name:");
            String director = UserHelperMethods.getInputFromPrompt("Director of the movie " + movieTitle + ":");
            String genre = getGenreFromChoices("Genre of the movie " + movieTitle + " (choose from the choices below) :");
            int quantity = UserHelperMethods.getIntegerInputFromPrompt("Quantity of the movie " + movieTitle + ":");
            Movie movieToAdd = new Movie(movieTitle, director,genre, quantity);
            movieDatabase.addMovie(movieToAdd);
            System.out.println("Movie Successfully Added: " + movieTitle + " | Director: "+director + " | Genre: "+genre+" | Quantity: " +quantity);
        } else if (userChoiceText.equals("Delete Movie")) {
            // Takes the user input for the movie name then deletes the movie
            System.out.println("What movie would you like to delete:");
            Movie movieToDelete = displayAllMovies();
            if(movieToDelete!=null) {
                movieDatabase.deleteMovie(movieToDelete);
                System.out.println("Deleted Movie: " + movieToDelete);
            }
        }

        // Returns to the Main Menu
        System.out.println("Returning to Main Menu...");
        mainMenu();
    }

    /**
     * displays all the movies
     * @return The movie object
     */
    private Movie displayAllMovies() {
        // Finds all movies that match the name in the database
        List<Movie> foundMovies = movieDatabase.returnAllMovies();

        // If no movies were found display a message to the user, and return null
        if (foundMovies.isEmpty()) {
            System.out.println("There are currently no movies to display, returning to main menu:");
            return null;
        }

        // If there are movies available, the displayOptionsAndWaitForValidOption method to have the user select from the movies
        else {
            int selectedMovieIndex = UserHelperMethods.displayOptionsAndWaitForValidOption(foundMovies);
            foundMovies = movieDatabase.returnAllMovies();
            int endOfList = foundMovies.size();
            // if the selectedMovieIndex is equal to endOfList, this means the user wants to go back to main menu
            if(selectedMovieIndex==endOfList){
                return null;
            }
            else {
                return foundMovies.get(selectedMovieIndex);
            }
        }
    }

    /**
     * Gets all the movies from the movielist provided
     * @return The movie object
     */
    private Movie displayAllMoviesFromMovielist(String movieListName) {
        // Finds all movies that match the name in the database
        List<Movie> foundMovies = movieDatabase.returnAllMoviesFromMovieListName(movieListName);

        // If no movies were found display a message to the user, and return null
        if (foundMovies.isEmpty()) {
            System.out.println("There are currently no movies to display, returning to main menu:");
            return null;
        }

        // If there are movies available, the displayOptionsAndWaitForValidOption method to have the user select from the movies
        else {
            int selectedMovieIndex = UserHelperMethods.displayOptionsAndWaitForValidOption(foundMovies);
            foundMovies = movieDatabase.returnAllMoviesFromMovieListName(movieListName);
            int endOfList = foundMovies.size();
            // if the selectedMovieIndex is equal to endOfList, this means the user wants to go back to main menu
            if(selectedMovieIndex==endOfList){
                return null;
            }
            else
                return foundMovies.get(selectedMovieIndex);
        }
    }

    /**
     * Displays all the movies by genre
     * @return the movie list
     */
    private List<Movie> displayAllMovieGenre() {
        // Finds all movies that match the name in the database
        List<Movie> foundMovies = movieDatabase.returnAllMovies();

        // If no movies were found display a message to the user, and return null
        if (foundMovies.isEmpty()) {
            System.out.println("There are currently no movies to display, returning to main menu:");
            return null;
        }

        // If multiple movies match call the displayOptionsAndWaitForValidOption method to have the user select from the movies
        else {
            int selectedMovieIndex = UserHelperMethods.displayGenreOptionsAndWaitForValidOption(foundMovies);
            int endOfList = foundMovies.size();

            // gets the genre of movies the user wants to display
            String chosenGenre = getChosenGenre(foundMovies, selectedMovieIndex);
            List<Movie> movieListWithSpecificGenre = movieDatabase.findMoviesByGenre(chosenGenre);

            if(movieListWithSpecificGenre.size()!=0) {
                System.out.println("List of movies with genre: " + chosenGenre);
            }
            return movieListWithSpecificGenre;

        }
    }

    /**
     * Gets the chosen genre from the user
     * @param genreOptions list of all the genre options
     * @param chosenOption chosen genre
     * @return the String[] of the list of genres
     */
    private String getChosenGenre(List<Movie> genreOptions, int chosenOption) {
        // Creates an array of all the movie text
        String[] genreText = new String[genreOptions.size()];

        // Fills the movieText array with the string versions of each movie
        for (int i = 0; i < genreOptions.size(); i++) {
            genreText[i] = genreOptions.get(i).toStringGenre();
        }

        // convert String[] to List
        List<String> genreList = Arrays.asList(genreText);

        // remove duplicate genre
        genreList = genreList.stream().distinct().collect(Collectors.toList());

        //convert back the List to String[]
        genreText = genreList.toArray(new String[0]);

        return genreText[chosenOption];
    }


    /**
     * Rent the movie
     * @return The movie object
     */
    private int rentMovieQuantity(Movie movieToRent) {
        boolean wrongUserInput = false;
        int quantity = 0;
        // checks if the movie quantity is not 0
        if (movieToRent.getQuantity() != 0) {
            do {
                try {
                    String stringQuantity = UserHelperMethods.getInputFromPrompt("How many movie copies would you like to rent?");
                    quantity = Integer.parseInt(stringQuantity);
                } catch (NumberFormatException e) {
                    // no strings and chars allowed
                    System.out.println("Only Numbers please");
                }

                // checks if the quantity the user requested is more than the available quantity of the movie
                if (quantity > movieToRent.getQuantity()) {
                    System.out.println("You ordered over " + movieToRent.getQuantity());
                    wrongUserInput = true;
                    // checks if the quantity the user requested is less than 0
                } else if (quantity < 0) {
                    System.out.println("No Negative Number Please!");
                    wrongUserInput = true;
                    // checks if the quantity the user requested is 0
                } else if (quantity == 0) {
                    System.out.println("You did not rent anything!");
                    wrongUserInput = true;
                } else {
                    wrongUserInput = false;
                }


            } while (wrongUserInput);
        } else {
            return quantity;
        }

        return quantity;
    }


    /**
     * Plays the given movie trailer
     * @param movieTrailer The movie trailer to be played
     */
    private void playMovieTrailer(Movie movieTrailer) {

        // Checks if the movie trailer is not null
        if (movieTrailer != null) {
            System.out.println("NOW PLAYING: " + movieTrailer);
        }
    }

    /**
     * Displays all the list of movies with the given genre provided
     * @param movieGenre the genre of movie the user chooses
     */
    private void displayAllMoviesWithSpecificGenre(List<Movie> movieGenre) {

        // Checks if the movie genre is not null
        for(Movie movie: movieGenre) {
            if (movieGenre != null) {
                System.out.println("Title: " + movie.getTitle() + " | Director: "+ movie.getDirector()+" | Genre: "+movie.getGenre());
            }
        }
    }

    /**
     * Actual renting of the movie
     * @param movieTitle what movie title is requested by the user
     * @param quantityToRent how many movie copies the user wants to rent
     */
    private void rentMovie(String movieTitle, int quantityToRent) {

        if (quantityToRent!=0) {
            int newQuantity = movieDatabase.newQuantityAfterBeingRented(movieTitle, quantityToRent);
            System.out.println("New quantity for movie " + movieTitle + " is " + newQuantity + " left");
            System.out.println("Thanks for renting the movie!");
        }
        else{
            System.out.println("No more available stocks for movie " + movieTitle);
        }
    }

    /**
     * Tries to create a movielist with the given name, and catches the database exception if the movielist already exists
     * @param movielistName
     */
    private void tryCreateMovielist(String movielistName) {
        // Tries to create the movielist
        try {
            movieDatabase.createMovielist(movielistName);
            System.out.println("New Movielist Created: " + movielistName);
        }

        // Catches the IllegalArgumentException and displays a friendly error to the user
        catch (IllegalArgumentException exception) {
            System.out.println("A movielist already exists with the name: " + movielistName + ". A unique name is required.");
        }
    }

    /**
     * Gets the integer input from a prompt to choose from the genre choices
     * @param prompt The prompt for the user
     * @return The entered text from the prompt
     */
    public static String getGenreFromChoices(String prompt) {
        // Initializes the scanner
        System.out.println(prompt);
        int userChoice = UserHelperMethods.displayOptionsAndWaitForValidOption(OptionStrings.GENRE_OPTIONS);
        String userChoiceText = OptionStrings.GENRE_OPTIONS[userChoice];

        return userChoiceText;
    }

    /**
     * Gets the integer input from a prompt to choose from the movies in the movie list
     * @param prompt The prompt for the user
     * @return The entered text from the prompt
     */
    public String getMovieListFromChoices(String prompt) {
        // Initializes the scanner
        String[] MovieListNames = movieDatabase.getAllMovielist();

        // if there is no movie list available, go back to Main Menu.
        if(MovieListNames.length==0){
            System.out.println("No Movie List Available! Back to Main Menu");
            mainMenu();
        }

        System.out.println(prompt);
        int userChoice = UserHelperMethods.displayOptionsAndWaitForValidOption(MovieListNames);
        String userChoiceText = MovieListNames[userChoice];

        return userChoiceText;
    }

    /**
     * The main method and console app
     * @param args The list of arguments
     */
    public static void main(String[] args) {
        // Initializes the Movie console app
        MovieStore movie = new MovieStore();

        String movieTitle1 = "The Fault in Our Stars";

        // Playlist name
        String marvelPlaylist = "Marvel Movies";

        // Originally Added Movies
        movie.movieDatabase.addMovie(new Movie("The Fault in Our Stars", "Josh Boone", "Romance", 20));
        movie.movieDatabase.addMovie(new Movie("Now You See Me", "Louis Leterrier", "Thriller", 12));
        movie.movieDatabase.addMovie(new Movie("Eternals", "Chloé Zhao", "Action", 5));
        movie.movieDatabase.addMovie(new Movie("No Time to Die", "Cary Joji Fukunaga", "Adventure", 8));
        movie.movieDatabase.addMovie(new Movie("Dune", "Denis Villeneuve", "Action", 9));

        // Creates the movielist
        movie.movieDatabase.createMovielist(marvelPlaylist);

        // Adds movies to the movielist, some of which are brand new to the Movie Store
        movie.movieDatabase.addMovieToMovielist(new Movie("Venom", "Ruben Fleischer", "Action", 2), marvelPlaylist);
        movie.movieDatabase.addMovieToMovielist(new Movie("Thor", "Kenneth Branagh", "Action", 4), marvelPlaylist);
        movie.movieDatabase.addMovieToMovielist(new Movie("The Amazing Spider-Man", "Marc Webb", "Action", 5), marvelPlaylist);
        movie.movieDatabase.addMovieToMovielist(new Movie("Guardians of the Galaxy", "James Gunn", "Action",6 ), marvelPlaylist);
        movie.movieDatabase.addMovieToMovielist(new Movie("Doctor Strange", "Scott Derrickson", "Action", 8), marvelPlaylist);

        // Starts the application by opening the main menu
        movie.mainMenu();
    }
}

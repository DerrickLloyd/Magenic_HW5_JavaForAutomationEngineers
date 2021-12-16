package models;

public class Movie extends Show {

    /**
     * Creates an instance of the Movie class that inherits 'Show' Parent Class
     * @param title The movie title
     * @param director The director's name
     * @param genre The genre of the movie
     * @param quantity The available quantity of the movie to be rented
     */
    public Movie(String title, String director, String genre, int quantity) {
        super(title, director, genre, quantity);
    }

    /**
     * Overrides the toString() method and returns the title, director, and genre of the movie in String format
     * @return String format of title, director, and genre of the movie
     */
    @Override
    public String toString() {
        return "MOVIE TITLE: '" + getTitle() + "' directed by '" + getDirector() + "' Genre: '" + getGenre() + "'";
    }

    /**
     * Returns the String format for the movie genre
     * @return String format for the movie genre
     */
    public String toStringGenre() {
        return "" + getGenre();
    }

    /**
     * Determines if this models.Movie obj is equal to the provided object
     * @param obj The compared object
     * @return True if the Movies are the same, and false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // Checks if 'obj' is null or can't be assigned to a 'models.Movie' class
        if (obj == null || !Movie.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        // Casts the 'obj' to a 'models.Movie' object
        final Movie otherMovie = (Movie)obj;

        // Checks if the movie title and director are the same
        if (this.getTitle().equals(otherMovie.getTitle()) && this.getDirector().equals(otherMovie.getDirector())) {
            return true;
        }

        // Returns false, as a catch all
        return false;
    }

    /**
     * The main method
     * @param args The array of arguments
     */
    public static void main(String[] args) {
        Movie movieOne = new Movie("Captain Marvel", "Anna Boden","Action", 10);
        Movie movieTwo = new Movie("Iron Man", "Jon Favreau","Action", 12);

        // Using the 'toString' method
        System.out.println(movieOne);
        System.out.println(movieTwo);

        // Using the 'equals' method
        System.out.println("Movies are equal: " + movieOne.equals(movieTwo));

        // Updating movieTwo to the same values as movieOne
        movieTwo.setTitle("Captain Marvel");
        movieTwo.setDirector("Anna Boden");
        movieTwo.setGenre("Action");

        // Prints out movieTwo and checks if the movies are now equal
        System.out.println(movieTwo);
        System.out.println("Movies are equal: " + (movieOne.equals(movieTwo) && movieTwo.equals(movieOne)));
    }
}

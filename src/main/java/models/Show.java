package models;

public abstract class Show {

    /**
     * this is the variable for the title of the movie
     */
    private String title;
    /**
     * this is the variable for the director of the movie
     */
    private String director;
    /**
     * this is the variable for the genre of the movie
     */
    private String genre;
    /**
     * this is the variable for the quantity of the movie for rent
     */
    private int quantity;

    /**
     * This is the Show Parent Class
     * @param title The movie title
     * @param director The director's name
     * @param genre The genre of the movie
     * @param quantity The available quantity of the movie to be rented
     */
    public Show(String title, String director, String genre, int quantity) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.quantity = quantity;
    }

    /**
     * Gets the title
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the director
     * @return The director
     */
    public String getDirector() {
        return director;
    }

    /**
     * Gets the show genre
     * @return The show genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets the show quantity left
     * @return The show genre
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the title
     * @param title The title of the show
     */
    protected void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the director
     * @param director The director of the show
     */
    protected void setDirector(String director) {
        this.director = director;
    }

    /**
     * Sets the show genre
     * @param genre The genre of the show
     */
    protected void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Sets the show quantity for rent
     * @param quantity The quantity of the show
     */
    protected void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
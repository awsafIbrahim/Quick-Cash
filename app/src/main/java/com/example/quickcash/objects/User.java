package com.example.quickcash.objects;

/**
 * Represents a user in the app.
 */
public abstract class User {

    protected String id;
    protected String name;
    protected String email;
    protected Coordinates coordinates;
    protected float rating;
    protected int numberOfRatings;

    /**
     * Default constructor only used for reflection by Firebase Database.
     */
    public User() {}

    /**
     * Parameterized constructor for a user.
     * @param id    Unique ID assigned by Firebase Authenticator.
     * @param name  Name chosen by user on sign-up.
     * @param email Email of the user, used to sign up.
     */
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.coordinates = null;
        this.rating = -1.0f;
        this.numberOfRatings = 0;
    }

    /**
     * Gets the user's unique ID.
     * @return The user's ID.
     */
    public String getID() {
        return this.id;
    }

    /**
     * Gets the user's username.
     * @return Username.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the user's email.
     * @return The user's email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the user's coordinates.
     * @return A Coordinate object representing the user's coordinates.
     */
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /**
     * Sets the user's current coordinates.
     * @param coordinates   Coordinate object to be set as the user's current coordinates.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Checks if the user has received any ratings.
     * @return  true if the user has at least one rating; false otherwise.
     */
    public boolean hasRating() {
        return this.numberOfRatings > 0;
    }

    /**
     * Gets the number of ratings the user has.
     * @return The number of ratings the user has.
     */
    public int getNumberOfRatings() {
        return this.numberOfRatings;
    }

    /**
     * Gets the user's current rating.
     * @return  The user's current rating.
     */
    public float getRating() {
        return this.rating;
    }

    /**
     * Adds a rating to the user's current average rating.
     * @param rating    The rating to add to the user's rating.
     */
    public void addRating(float rating) {
        if (hasRating()) {
            this.rating = ((this.rating * this.numberOfRatings) + rating) / (this.numberOfRatings + 1);
        } else {
            this.rating = rating;
        }
        this.numberOfRatings++;
    }

}

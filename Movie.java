// -------------------------------------------------------
	// Assignment 2
	// Question: Movie class
	// Written by: Huu Khoa Kevin Tran 40283037 && Pritthiraj Dey 40273416
	// For COMP 249 Section WW && QQ – Winter 2024
	// --------------------------------------------------------

	// Date of submission: Wednesday, March 27th 2024
	
	/*
	 The Movie class represents individual movie entities. 
	 It encapsulates attributes such as year, title, duration, genre, rating, score, director, and three actors. 
	 This class facilitates the creation, retrieval, and manipulation of movie data within the system. 
	 Additionally, it overrides methods like equals() to enable comparison between movie objects and toString() to provide a formatted representation of movie details. 
	 Overall, the Movie class serves as the fundamental building block for handling movie information within the application.
	 */
// -------------------------------------------------------
package assignment2;

import java.io.*;
import java.util.*;

/**
The Movie class represents individual movie entities. 
It encapsulates attributes such as year, title, duration, genre, rating, score, director, and three actors. 
This class facilitates the creation, retrieval, and manipulation of movie data within the system. 
Additionally, it overrides methods like equals() to enable comparison between movie objects and toString() to provide a formatted representation of movie details. 
Overall, the Movie class serves as the fundamental building block for handling movie information within the application.
*/
public class Movie implements Serializable{
	// The 10 attributes of the Movie class
	private int year;
	private String title;
	private int duration;
	private String genre;
	private String rating;
	private double score;
	private String director;
	private String actor1;
	private String actor2;
	private String actor3;
	
	
	public Movie() {
		
	}
	
	/**
     * Constructs a Movie object with the specified attributes.
     *
     * @param year     The year the movie was released.
     * @param title    The title of the movie.
     * @param duration The duration of the movie in minutes.
     * @param genre    The genre of the movie.
     * @param rating   The rating of the movie.
     * @param score    The score of the movie.
     * @param director The director of the movie.
     * @param actor1   The first actor in the movie.
     * @param actor2   The second actor in the movie.
     * @param actor3   The third actor in the movie.
     */
	public Movie(int year, String title, int duration, String genre, String rating, double score, String director, String actor1, String actor2, String actor3) {
		this.year = year;
		this.title = title;
		this.duration = duration;
		this.genre = genre;
		this.rating = rating;
		this.score = score;
		this.director = director;
		this.actor1 = actor1;
		this.actor2 = actor2;
		this.actor3 = actor3;
		
	}

	// Getters and setters for each attributes
    /**
     * Retrieves the year of the movie.
     *
     * @return The year of the movie.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the movie.
     *
     * @param year The year of the movie to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Retrieves the title of the movie.
     *
     * @return The title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title The title of the movie to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the duration of the movie in minutes.
     *
     * @return The duration of the movie in minutes.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the movie in minutes.
     *
     * @param duration The duration of the movie to set.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Retrieves the genre of the movie.
     *
     * @return The genre of the movie.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the movie.
     *
     * @param genre The genre of the movie to set.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Retrieves the rating of the movie.
     *
     * @return The rating of the movie.
     */
    public String getRating() {
        return rating;
    }

    /**
     * Sets the rating of the movie.
     *
     * @param rating The rating of the movie to set.
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Retrieves the score of the movie.
     *
     * @return The score of the movie.
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the score of the movie.
     *
     * @param score The score of the movie to set.
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Retrieves the director of the movie.
     *
     * @return The director of the movie.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Sets the director of the movie.
     *
     * @param director The director of the movie to set.
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Retrieves the first actor in the movie.
     *
     * @return The first actor in the movie.
     */
    public String getActor1() {
        return actor1;
    }

    /**
     * Sets the first actor in the movie.
     *
     * @param actor1 The first actor in the movie to set.
     */
    public void setActor1(String actor1) {
        this.actor1 = actor1;
    }

    /**
     * Retrieves the second actor in the movie.
     *
     * @return The second actor in the movie.
     */
    public String getActor2() {
        return actor2;
    }

    /**
     * Sets the second actor in the movie.
     *
     * @param actor2 The second actor in the movie to set.
     */
    public void setActor2(String actor2) {
        this.actor2 = actor2;
    }

    /**
     * Retrieves the third actor in the movie.
     *
     * @return The third actor in the movie.
     */
    public String getActor3() {
        return actor3;
    }

    /**
     * Sets the third actor in the movie.
     *
     * @param actor3 The third actor in the movie to set.
     */
    public void setActor3(String actor3) {
        this.actor3 = actor3;
    }
	
	// Override equals method to compare Movie objects
	/**
     * Checks if this Movie object is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		else {
			Movie movie = (Movie) obj;
			
			return (this.year == movie.year && this.title == movie.title && 
					this.duration == movie.duration && this.genre == movie.genre && 
					this.rating == movie.rating && this.score == movie.score && 
					this.director == movie.director && this.actor1 == movie.actor1 && 
					this.actor2 == movie.actor2 && this.actor3 == movie.actor3);
		}
	}
	
	// Override toString method to provide a string representation of the Movie object
	/**
     * Generates a string representation of the Movie object.
     *
     * @return A string representation of the Movie object.
     */
	public String toString() {
		return "Year: "+year+"\nTItle: "+title+
				"\nDuration: "+duration+"\nGenre: "+genre+
				"\nRating: "+rating+"\nScore: "+score+
				"\nDirector: "+director+"\nActor 1: "+actor1+
				"\n Actor 2: "+actor2+"\nActor 3: "+actor3;
	}

}

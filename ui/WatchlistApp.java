package ui;

import model.EventLog;
import model.Event;
import model.Movie;
import model.Watchlist;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;





// Watchlist application
//NOTE: some code used in this class is taken from the TellerApp ui class as mentioned in the Phase 1 description
public class WatchlistApp {
    private static Watchlist watchlist;
    private static Scanner scanner = new Scanner(System.in);
    private static final String JSON_STORE = "./data/watchlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
//    private static Watchlist watchlist = new Watchlist();
//    private static Scanner scanner = new Scanner(System.in);

    // EFFECTS: runs the watchlist application
    public WatchlistApp() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        watchlist = new Watchlist();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWatchlist();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    @SuppressWarnings("methodlength")

    public void runWatchlist() {

        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("Select action");
            System.out.println("1. Add a movie to your watchlist");
            System.out.println("2. View movie titles in your watchlist");
            System.out.println("3. View details of a movie");
            System.out.println("4. Search movies by genre");
            System.out.println("5. Search movies by actor");
            System.out.println("6. Remove a movie from your watchlist");
            System.out.println("7. Save watchlist to file");
            System.out.println("8. Load watchlist from file");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addMovieToWatchlist();
                    break;
                case 2:
                    viewWatchlistTitles();
                    break;
                case 3:
                    viewMovieDetails();
                    break;
                case 4:
                    searchMoviesByGenre();
                    break;
                case 5:
                    searchMoviesByActor();
                    break;
                case 6:
                    removeMovieFromWatchlist();
                    break;
                case 7:
                    saveWatchlistToFile();
                    break;
                case 8:
                    loadWatchlistFromFile();
                    break;
                case 0:
                    keepGoing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void addMovieToWatchlist() {
        System.out.println("Enter movie details:");
        System.out.print("Title: ");
        String title = scanner.nextLine();

        // takes movie genre(s)
        System.out.print("Genre: ");
        String genreInput = scanner.nextLine();
        List<String> genres = Arrays.asList(genreInput.split(", "));

        // takes director
        System.out.print("Director: ");
        String director = scanner.nextLine();

        // takes lead actor(s)
        System.out.print("Lead Actors (comma-separated): ");
        String leadActorsInput = scanner.nextLine();
        List<String> leadActors = Arrays.asList(leadActorsInput.split(", "));

        // takes supporting actor(s)
        System.out.print("Supporting Actors (comma-separated): ");
        String supportingActorsInput = scanner.nextLine();
        List<String> supportingActors = Arrays.asList(supportingActorsInput.split(", "));

        // takes movie release year
        System.out.print("Release Year: ");
        int year = scanner.nextInt();


        scanner.nextLine();


        // Creates a new Movie object with the details provided above
        Movie movie = new Movie(title, genres, director, leadActors, supportingActors, year);


        watchlist.addMovie(movie);
        System.out.println("Movie added to your watchlist.");
    }

    //
    // EFFECTS: displays titles of movies in the watchlist
    public void viewWatchlistTitles() {
        List<String> titles = watchlist.getWatchlistTitles();
        System.out.println("Movies in your watchlist:");
        for (String title : titles) {
            System.out.println(title);
        }
    }

    //
    // EFFECTS: takes movie title and returns the details
    private static void viewMovieDetails() {
        System.out.print("Enter the title of the movie: ");
        String title = scanner.nextLine();

        List<Movie> matchingMovies = watchlist.getMoviesByTitle(title);

        if (matchingMovies.isEmpty()) {
            System.out.println("No movies found in the watchlist with the specified title.");
        } else {
            System.out.println("Movie details for the movie with the title '" + title + "':");
            for (Movie movie : matchingMovies) {
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Genre: " + String.join(", ", movie.getGenre()));
                System.out.println("Director: " + movie.getDirector());
                System.out.println("Lead Actors: " + String.join(", ", movie.getLeadActors()));
                System.out.println("Supporting Actors: " + String.join(", ", movie.getSupportingActors()));
                System.out.println("Release Year: " + movie.getYear());
                System.out.println("--------");
            }
        }
    }


    //
    // EFFECTS: takes a genre and returns list of movie titles of all the movies in the watchlist of that genre
    private static void searchMoviesByGenre() {
        System.out.print("Enter the genre: ");
        String genre = scanner.nextLine();

        List<String> matchingTitle = watchlist.filterByGenre(genre);


        if (matchingTitle.isEmpty()) {
            System.out.println("No movies found in the watchlist with a similar genre.");
        } else {
            System.out.println("Movie titles with a similar genre:");
            for (String title : matchingTitle) {
                System.out.println(title);
            }
        }
    }


    //
    // EFFECTS: takes an actor's name and returns a list of movie titles of all the movies in the
    // watchlist starring that actor
    private static void searchMoviesByActor() {
        System.out.print("Enter actor name: ");
        String actorName = scanner.nextLine();

        List<String> matchingTitle = watchlist.filterByActor(actorName);


        if (matchingTitle.isEmpty()) {
            System.out.println("No movies found in the watchlist that star this actor");
        } else {
            System.out.println("Movie titles which star this actor");
            for (String title : matchingTitle) {
                System.out.println(title);
            }
        }

    }

    //MODIFIES: this
    //EFFECTS: takes a movie title and removes the movie from the watchlist

    private void removeMovieFromWatchlist() {
        System.out.print("Enter the title of the movie to remove: ");
        String title = scanner.nextLine();
        watchlist.removeMovie(title);
        System.out.println("Movie removed from your watchlist.");
    }


    // EFFECTS: loads workroom from file
    private void saveWatchlistToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(watchlist);
            jsonWriter.close();
            System.out.println("Saved your watchlist to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Error: Unable to save your watchlist to " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWatchlistFromFile() {
        try {
            watchlist = jsonReader.read();
            System.out.println("Loaded your watchlist from " + JSON_STORE);
        } catch (Exception e) {
            System.out.println("Error: Unable to load your watchlist from " + JSON_STORE);
        }
    }


}


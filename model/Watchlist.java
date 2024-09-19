package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;


//Watchlist Class: Creates a Watchlist object
public class Watchlist implements Writable {
    private List<Movie> movies;

//EFFECTS: Contrusts an empty ArrayList of Movie object.
    public Watchlist() {
        movies = new ArrayList<>();
    }

    /*
    * MODIFIES: this
    * EFFECTS: adds the movie to the watchlist(movies)
    */
    public void addMovie(Movie movie) {
        movies.add(movie);
        EventLog.getInstance().logEvent(new Event(movie.getTitle() + " added to the Watchlist"));
    }


    /*REQUIRES: no movies with duplicate titles
    * MODIFIES: this
    * EFFECTS: removes the movies with the given title from the watchlist
    */

    public void removeMovie(String title) {
        Movie movieToRemove = null;
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                movieToRemove = movie;
            }
        }
        if (movieToRemove != null) {
            movies.remove(movieToRemove);
        }
        EventLog.getInstance().logEvent(new Event(title + " removed to the Watchlist"));
    }

    /* EFFECTS: returns a list of string with the titles of all movies in the watchlist
    *
    */

    public List<String> getWatchlistTitles() {
        List<String> titles = new ArrayList<>();
        for (Movie movie : movies) {
            titles.add(movie.getTitle());
        }
        return titles;

    }

    /* REQUIRES: movie is in the watchlist
    * EFFECTS: Takes the movie title and returns all the details of the movie
    */

    public Movie getDetails(String title) {
        for (Movie movie: movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        System.out.println("Movie not found in Watchlist");
        return null;

    }

    /*
    *EFFECTS: takes the genre title and returns a list of all movie titles in the watchlist of the given genre.
     */

    public List<String> filterByGenre(String genre) {
        List<String> genreList = new ArrayList<>();
        for (Movie movie: movies) {
            if (movie.isGenre(movie, genre)) {
                genreList.add(movie.getTitle());
            }
        }
        return genreList;
    }

    /*
     *EFFECTS: takes the actor name and returns a list of all movie titles in the watchlist starring the given actor.
     */
    public List<String> filterByActor(String genre) {
        List<String> actorList = new ArrayList<>();
        for (Movie movie: movies) {
            if (movie.isActor(movie, genre)) {
                actorList.add(movie.getTitle());
            }
        }
        return actorList;
    }

    /*
     *EFFECTS: takes a movie title and returns the movie if it is in the watchlist
     */
    public List<Movie> getMoviesByTitle(String title) {
        List<Movie> matchingMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                matchingMovies.add(movie);
            }
        }
        return matchingMovies;
    }

    // EFFECTS: returns the watchlist as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("movies", moviesToJson());
        return json;
    }

    // EFFECTS: returns movies in the watchlist as a JSON array
    private JSONArray moviesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Movie movie : movies) {
            jsonArray.put(movie.toJson());
        }
        return jsonArray;
    }




}

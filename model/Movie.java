package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

//Movie Class: Creates a Movie object
public class Movie {
    private String title;                  // title of the movie
    private List<String> genre;            //genre(s) of the movie
    private String director;               //director of the movie
    private List<String> leadActors;       //lead actors in the movie
    private List<String> supportingActors; //supporting actors in the movie
    private int year;                      //release year of the movie
    //Watchlist watchList = new Watchlist();



    /*
    * REQUIRES: title has a non-zero length;
    *           year is a positive integer
    * Effects: Constructs a movie with the given title, genre(s), director
    *          lead actor(s), supporting actor(s) and year
     */
    public Movie(String title, List<String> genre, String director, List<String> leadActors,
                 List<String> supportingActors, int year) {
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.leadActors = leadActors;
        this.supportingActors = supportingActors;
        this.year = year;
    }



    // GET METHODS
    public String getTitle() {
        return title;
    }



    public List<String> getGenre() {
        return genre;
    }



    public String getDirector() {
        return director;
    }


    public List<String> getLeadActors() {
        return leadActors;
    }


    public List<String> getSupportingActors() {
        return supportingActors;
    }



    public int getYear() {
        return year;
    }



    // SET METHODS: Not used in this phase of the project
    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setLeadActors(List<String> leadActors) {
        this.leadActors = leadActors;
    }

    public void setSupportingActors(List<String> supportingActors) {
        this.supportingActors = supportingActors;
    }

    public void setYear(int year) {
        this.year = year;
    }


    /*
    *EFFECTS: returns true if the given movie is of the given genre else false
    */
    public boolean isGenre(Movie movie, String givenGenre) {
        List<String> genres = movie.getGenre();
        for (String genre : genres) {
            if (genre.equals(givenGenre)) {
                return true;
            }
        }
        return false;
    }

//    public boolean isGenre(String givenGenre) {
//        return genre == givenGenre;
//    }

    /*
     *EFFECTS: returns true if the given movie is of the given genre else false
     */
    public boolean isActor(Movie movie, String actorName) {
        List<String> allActors = new ArrayList<>();
        allActors.addAll(movie.getLeadActors());
        allActors.addAll(movie.getSupportingActors());

        for (String actor : allActors) {
            if (actor.equals(actorName)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the movie as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("genre", genresToJson());
        json.put("director", director);
        json.put("leadActors", actorsToJson(leadActors));
        json.put("supportingActors", actorsToJson(supportingActors));
        json.put("year", year);
        return json;
    }

    // EFFECTS: converts a list of strings to a JSON array
    private JSONArray actorsToJson(List<String> actors) {
        JSONArray jsonArray = new JSONArray();
        for (String actor : actors) {
            jsonArray.put(actor);
        }
        return jsonArray;
    }

    // EFFECTS: converts the list of genres to a JSON array
    private JSONArray genresToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String genre : genre) {
            jsonArray.put(genre);
        }
        return jsonArray;
    }


}






package persistence;

import model.Watchlist;
import model.Movie;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
//import java.util.Scanner;
import java.util.*;
import java.util.stream.Stream;

// Represents a reader that reads watchlist from JSON data stored in a file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the watchlist from the file and returns it
    // throws IOException if an error occurs reading data from the file
    public Watchlist read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWatchlist(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses the watchlist from the JSON object and returns it
    private Watchlist parseWatchlist(JSONObject jsonObject) {
        Watchlist watchlist = new Watchlist();
        JSONArray moviesArray = jsonObject.getJSONArray("movies");

        for (Object movieObj : moviesArray) {
            JSONObject movieJson = (JSONObject) movieObj;
            addMovie(watchlist, movieJson);
        }

        return watchlist;
    }

    // MODIFIES: watchlist
    // EFFECTS: parses thingies from JSON object and adds them to watchlist
    private void addMovie(Watchlist watchlist, JSONObject movieJson) {
        String title = movieJson.getString("title");

        JSONArray genreArray = movieJson.getJSONArray("genre");
        List<String> genreList = new ArrayList<>();
        for (Object genre : genreArray) {
            genreList.add((String) genre);
        }

        String director = movieJson.getString("director");

        JSONArray leadActorsArray = movieJson.getJSONArray("leadActors");
        List<String> leadActorsList = new ArrayList<>();
        for (Object actor : leadActorsArray) {
            leadActorsList.add((String) actor);
        }


        JSONArray supportingActorsArray = movieJson.getJSONArray("supportingActors");
        List<String> supportingActorsList = new ArrayList<>();
        for (Object actor : supportingActorsArray) {
            supportingActorsList.add((String) actor);
        }
        int year = movieJson.getInt("year");

        // Create a new Movie object
        Movie movie = new Movie(title, genreList, director, leadActorsList, supportingActorsList, year);

        // Add the movie to the Watchlist
        watchlist.addMovie(movie);
    }
}

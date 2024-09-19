package persistence;

import model.Watchlist;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of a watchlist to a file
public class JsonWriter {
    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer;

    // EFFECTS: constructs a writer to write to the given destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the file for writing; throws IOException if the file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of a watchlist to the file
    public void write(Watchlist watchlist) {
        JSONObject json = watchlist.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}


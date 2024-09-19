package persistence;

import org.json.JSONObject;

// Represents an interface for objects that can be written as JSON
public interface Writable {
    // EFFECTS: returns this object as a JSON object
    JSONObject toJson();
}


package info.nsupdate.tboox.tboox.models;

/* Created by rbmarliere on 12/14/16. */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import info.nsupdate.tboox.tboox.utils.Model;

public class Review extends Model implements Serializable
{
    private String uuid;
    private String collection_id;
    private String rating;
    private String description;
    private String created_at;

    public Review(JSONObject json) {
        try {
            this.uuid = (String) json.get("uuid");
            this.collection_id = (String) json.get("collection_id");
            this.rating = (String) json.get("rating");
            this.description = (String) json.get("description");
            this.created_at = (String) json.get("created_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getCollection_id() {
        return collection_id;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated_at() {
        return created_at;
    }
}

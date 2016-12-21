package info.nsupdate.tboox.tboox.models;

/* Created by rbmarliere on 12/14/16. */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import info.nsupdate.tboox.tboox.utils.Model;

public class Collection extends Model implements Serializable
{
    private String uuid;
    private String book_id;
    private String created_at;

    public Collection(JSONObject json) {
        try {
            this.uuid = (String) json.get("uuid");
            this.book_id = (String) json.get("book_id");
            this.created_at = (String) json.get("created_at");
        } catch (JSONException e) {
           e.printStackTrace();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getCreated_at() {
        return created_at;
    }
}

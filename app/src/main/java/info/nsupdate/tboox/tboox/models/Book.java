package info.nsupdate.tboox.tboox.models;

/* Created by rbmarliere on 12/14/16. */

import org.json.JSONObject;

import java.io.Serializable;

import info.nsupdate.tboox.tboox.utils.Model;

public class Book extends Model implements Serializable
{
    private String uuid;
    private String title;
    private String synopsis;
    private String created_at;

    public Book(JSONObject jsonObject) {

    }

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getCreated_at() {
        return created_at;
    }
}

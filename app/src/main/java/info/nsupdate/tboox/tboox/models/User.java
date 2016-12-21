package info.nsupdate.tboox.tboox.models;

/* Created by rbmarliere on 12/14/16. */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import info.nsupdate.tboox.tboox.utils.Model;

public class User extends Model implements Serializable
{
    private String uuid;
    private String name;
    private String created_at;

    public User(JSONObject json) {
        try {
            this.uuid = (String) json.get("uuid");
            this.name = (String) json.get("name");
            this.created_at = (String) json.get("created_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getCreated_at() {
        return created_at;
    }
}

package info.nsupdate.tboox.tboox.models;

/* Created by rbmarliere on 12/14/16. */

import java.io.Serializable;

import info.nsupdate.tboox.tboox.utils.Model;

public class Collection extends Model implements Serializable
{
    private String uuid;
    private String book_id;
    private String user_id;
    private String created_at;
}

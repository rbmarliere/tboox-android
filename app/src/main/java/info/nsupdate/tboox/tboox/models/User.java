package info.nsupdate.tboox.tboox.models;

/* Created by nemo on 12/14/16. */

import java.io.Serializable;

import info.nsupdate.tboox.tboox.utils.Model;

public class User extends Model implements Serializable
{
    private String uuid;
    private String name;
    private String created_at;
}

package info.nsupdate.tboox.tboox.utils;

/* Created by rbmarliere on 12/13/16 */

import android.content.Context;

import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONObject;

public class Services
{
    private static HTTPSConnector conn = new HTTPSConnector();

    public static void delete(Context context, ResponseHandlerInterface handler, String path)
    {
        conn.request(context, HTTPSConnector.method.conn_method_DELETE, path, null, handler);
    }

    public static void get(Context context, ResponseHandlerInterface handler, String path)
    {
        conn.request(context, HTTPSConnector.method.conn_method_GET, path, null, handler);
    }

    public static void post(Context context, ResponseHandlerInterface handler, String path, JSONObject parameters)
    {
        conn.request(context, HTTPSConnector.method.conn_method_POST, path, parameters, handler);
    }
}

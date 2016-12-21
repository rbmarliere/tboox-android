package info.nsupdate.tboox.tboox.utils;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/* Created by rbmarliere on 12/21/16. */

public class APIHandler extends JsonHttpResponseHandler
{
    public void handle_data(JSONObject response) {
        // override this
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        System.out.println("received data : ");
        System.out.println(response.toString());
        handle_data(response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        System.out.println(errorResponse.toString());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        System.out.println(responseString);
    }
}

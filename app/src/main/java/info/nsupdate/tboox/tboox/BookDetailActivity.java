package info.nsupdate.tboox.tboox;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.nsupdate.tboox.tboox.models.Book;
import info.nsupdate.tboox.tboox.models.Collection;
import info.nsupdate.tboox.tboox.utils.APIHandler;
import info.nsupdate.tboox.tboox.utils.Services;

public class BookDetailActivity extends AppCompatActivity
{
    private Collection collection;
    private Book book;

    public BookDetailActivity()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        book = (Book) i.getSerializableExtra("book");
        collection = (Collection) i.getSerializableExtra("collection");
        if (collection != null) {
            findViewById(R.id.review_description).setVisibility(View.VISIBLE);
            findViewById(R.id.review_rating).setVisibility(View.VISIBLE);
            findViewById(R.id.saveReview).setVisibility(View.VISIBLE);
        }

        EditText title = (EditText) findViewById(R.id.title);
        EditText uuid = (EditText) findViewById(R.id.uuid);
        EditText synopsis = (EditText) findViewById(R.id.synopsis);

        title.setText(book.getTitle());
        uuid.setText(book.getUuid());
        synopsis.setText(book.getSynopsis());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void saveReview(View v) {
        final Context c = v.getContext();

        APIHandler handler = new APIHandler() {
            @Override
            public void handle_data(JSONObject response) {
                try {
                    if (response.get("status").toString() == "0")
                        Toast.makeText(c, "Resenha criada com sucesso", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(c, response.get("message").toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        JSONObject object = new JSONObject();
        JSONObject parameters = new JSONObject();
        try {
            object.put("collection_id", collection.getUuid());
            object.put("rating", ((EditText) findViewById(R.id.review_rating)).getText().toString());
            object.put("description", ((EditText) findViewById(R.id.review_description)).getText().toString());
            parameters.put("object", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Services.post(c, handler, "/review", parameters);
    }
}

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.nsupdate.tboox.tboox.models.Book;
import info.nsupdate.tboox.tboox.utils.APIHandler;
import info.nsupdate.tboox.tboox.utils.Services;

public class BookDetailActivity extends AppCompatActivity
{
    public BookDetailActivity()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Intent i = getIntent();
        Book b = (Book) i.getSerializableExtra("book");

        EditText title = (EditText) findViewById(R.id.title);
        EditText uuid = (EditText) findViewById(R.id.uuid);
        EditText synopsis = (EditText) findViewById(R.id.synopsis);

        title.setText(b.getTitle());
        uuid.setText(b.getUuid());
        synopsis.setText(b.getSynopsis());
    }

}

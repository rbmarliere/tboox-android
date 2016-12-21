package info.nsupdate.tboox.tboox.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.nsupdate.tboox.tboox.R;
import info.nsupdate.tboox.tboox.models.Book;
import info.nsupdate.tboox.tboox.utils.APIHandler;
import info.nsupdate.tboox.tboox.utils.Services;

/* Created by rbmarliere on 12/20/16. */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>
{
    public interface BookAdapterListener {
        void didClickBook(Book book);
    }

    private ArrayList<Book> books;
    private BookAdapterListener listener;

    public BookAdapter(ArrayList<Book> books, BookAdapterListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_book_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        vh.relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = (int)v.getTag();
                if (listener != null)
                    listener.didClickBook(books.get(position));
            }
        });

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book b = books.get(position);
        holder.title.setText(b.getTitle());
        holder.uuid.setText(b.getUuid());
        holder.synopsis.setText(b.getSynopsis());
        //holder.created_at.setText(b.getCreated_at());
        holder.relativeLayout.setTag(position);
        final String uuid = b.getUuid();
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context c = v.getContext();
                APIHandler handler = new APIHandler() {
                    @Override
                    public void handle_data(JSONObject response) {
                        try {
                            if (response.get("status").toString() == "0")
                                Toast.makeText(c, "Livro inserido na coleção", Toast.LENGTH_SHORT).show();
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
                    object.put("book_id", uuid);
                    parameters.put("object", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Services.post(c, handler, "/collection", parameters);
            }
        });
    }

    @Override
    public int getItemCount() {return books.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView uuid;
        public TextView synopsis;
        public TextView created_at;
        public CardView cardView;
        public RelativeLayout relativeLayout;
        public Button btnAdd;

        public ViewHolder(View v) {
            super(v);

            this.title = (TextView) v.findViewById(R.id.title);
            this.uuid = (TextView) v.findViewById(R.id.uuid);
            this.synopsis = (TextView) v.findViewById(R.id.synopsis);
            this.created_at = (TextView) v.findViewById(R.id.created_at);
            this.relativeLayout = (RelativeLayout) v.findViewById(R.id.relLayout);
            this.cardView = (CardView) v.findViewById(R.id.cardview);
            this.btnAdd = (Button) v.findViewById(R.id.btnAdd);

            v.setClickable(true);
        }
    }
}

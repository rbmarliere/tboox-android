package info.nsupdate.tboox.tboox.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import info.nsupdate.tboox.tboox.R;
import info.nsupdate.tboox.tboox.models.Book;

/* Created by rbmarliere on 12/20/16. */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>
{
    private ArrayList<Book> books;

    public BookAdapter(ArrayList<Book> books) {this.books = books;}

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_book_list, parent, false);
        ViewHolder vh = new ViewHolder(v);


        vh.relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book b = books.get(position);
        holder.title.setText(b.getTitle());
        //holder.uuid.setText(b.getUuid());
        holder.synopsis.setText(b.getSynopsis());
        //holder.created_at.setText(b.getCreated_at());
    }

    @Override
    public int getItemCount() {return books.size();}

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView uuid;
        public TextView synopsis;
        public TextView created_at;
        public CardView cardView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View v) {
            super(v);
            this.title = (TextView) v.findViewById(R.id.title);
            //this.uuid = (TextView) v.findViewById(R.id.uuid);
            this.synopsis = (TextView) v.findViewById(R.id.synopsis);
            //this.created_at = (TextView) v.findViewById(R.id.created_at);
            this.relativeLayout = (RelativeLayout) v.findViewById(R.id.relLayout);
            this.cardView = (CardView) v.findViewById(R.id.cardview);
            v.setClickable(true);
        }
    }
}

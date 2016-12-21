package info.nsupdate.tboox.tboox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import info.nsupdate.tboox.tboox.R;
import info.nsupdate.tboox.tboox.models.Collection;

/* Created by rbmarliere on 12/21/16. */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder>
{
    private ArrayList<Collection> collection;

    public CollectionAdapter(ArrayList<Collection> collection) {
        this.collection = collection;
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_collection_list, parent, false);
        CollectionAdapter.ViewHolder vh = new CollectionAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(CollectionAdapter.ViewHolder holder, int position) {
        Collection c = collection.get(position);
        holder.book_id.setText(c.getBook_id());
        holder.uuid.setText(c.getUuid());
        holder.created_at.setText(c.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView uuid;
        public TextView book_id;
        public TextView created_at;

        public ViewHolder(View v) {
            super(v);
            this.uuid = (TextView) v.findViewById(R.id.uuid);
            this.book_id = (TextView) v.findViewById(R.id.book_id);
            this.created_at = (TextView) v.findViewById(R.id.created_at);
        }
    }
}

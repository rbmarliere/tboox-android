package info.nsupdate.tboox.tboox.adapters;

import android.content.Context;
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
import info.nsupdate.tboox.tboox.models.Collection;
import info.nsupdate.tboox.tboox.utils.APIHandler;
import info.nsupdate.tboox.tboox.utils.Services;
import layout.CollectionListFragment;

/* Created by rbmarliere on 12/21/16. */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> implements View.OnClickListener
{
    public interface CollectionAdapterListener {
        void didClickCollection(Collection collection);
    }

    private ArrayList<Collection> collection;
    private CollectionAdapterListener listener;

    public CollectionAdapter(ArrayList<Collection> collection, CollectionAdapterListener listener) {
        this.collection = collection;
        this.listener = listener;
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_collection_list, parent, false);

        CollectionAdapter.ViewHolder vh = new CollectionAdapter.ViewHolder(v);
        vh.relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = (int)v.getTag();
                if (listener != null)
                    listener.didClickCollection(collection.get(position));
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(CollectionAdapter.ViewHolder holder, int position) {
        Collection c = collection.get(position);

        holder.book_id.setText(c.getBook_id());
        holder.uuid.setText(c.getUuid());
        holder.created_at.setText(c.getCreated_at());

        holder.relativeLayout.setTag(position);

        holder.btnDel.setOnClickListener(this);
        holder.btnDel.setTag(position);
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    @Override
    public void onClick(View view) {
        final Collection c = collection.get((int) view.getTag());
        final Context context = view.getContext();
        APIHandler handler = new APIHandler() {
            @Override
            public void handle_data(JSONObject response) {
                try {
                    if (response.get("status").toString().equals("0")) {
                        Toast.makeText(context, "Livro removido da coleção", Toast.LENGTH_SHORT).show();
                        collection.remove(c);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Services.delete(context, handler, "/collection/" + c.getUuid());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView uuid;
        public TextView book_id;
        public TextView created_at;
        public RelativeLayout relativeLayout;
        public Button btnDel;

        public ViewHolder(View v) {
            super(v);

            this.uuid = (TextView) v.findViewById(R.id.uuid);
            this.book_id = (TextView) v.findViewById(R.id.book_id);
            this.created_at = (TextView) v.findViewById(R.id.created_at);
            this.relativeLayout = (RelativeLayout) v.findViewById(R.id.relLayout);
            this.btnDel = (Button) v.findViewById(R.id.btnDel);

            v.setClickable(true);
        }
    }
}

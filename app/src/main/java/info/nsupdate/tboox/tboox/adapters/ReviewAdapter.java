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
import info.nsupdate.tboox.tboox.models.Review;

/* Created by rbmarliere on 12/21/16. */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>
{
    private ArrayList<Review> reviews;

    public ReviewAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_timeline_list, parent, false);
        ReviewAdapter.ViewHolder vh = new ReviewAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        Review r = reviews.get(position);
        holder.uuid.setText(r.getUuid());
        holder.collection_id.setText(r.getCollection_id());
        holder.rating.setText(r.getRating());
        holder.description.setText(r.getDescription());
        holder.created_at.setText(r.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView uuid;
        public TextView collection_id;
        public TextView rating;
        public TextView description;
        public TextView created_at;
        public CardView cardView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View v) {
            super(v);
            this.uuid = (TextView) v.findViewById(R.id.uuid);
            this.collection_id = (TextView) v.findViewById(R.id.collection_id);
            this.rating = (TextView) v.findViewById(R.id.rating);
            this.description = (TextView) v.findViewById(R.id.description);
            this.created_at = (TextView) v.findViewById(R.id.created_at);
            this.relativeLayout = (RelativeLayout) v.findViewById(R.id.rellayout_timeline);
            this.cardView = (CardView) v.findViewById(R.id.cardview_timeline);

            v.setClickable(true);
        }
    }
}

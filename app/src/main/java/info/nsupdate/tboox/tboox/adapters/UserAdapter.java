package info.nsupdate.tboox.tboox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import info.nsupdate.tboox.tboox.R;
import info.nsupdate.tboox.tboox.models.User;

/* Created by rbmarliere on 12/21/16. */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    private ArrayList<User> users;

    public UserAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_user_list, parent, false);
        UserAdapter.ViewHolder vh = new UserAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User u = users.get(position);
        holder.uuid.setText(u.getUuid());
        holder.name.setText(u.getName());
        holder.created_at.setText(u.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView uuid;
        public TextView name;
        public TextView created_at;

        public ViewHolder(View v) {
            super(v);
            this.uuid = (TextView) v.findViewById(R.id.uuid);
            this.name = (TextView) v.findViewById(R.id.name);
            this.created_at = (TextView) v.findViewById(R.id.created_at);
        }
    }
}

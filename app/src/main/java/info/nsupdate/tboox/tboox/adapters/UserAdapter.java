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
import info.nsupdate.tboox.tboox.models.User;
import info.nsupdate.tboox.tboox.utils.APIHandler;
import info.nsupdate.tboox.tboox.utils.Services;

/* Created by rbmarliere on 12/21/16. */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    public interface UserAdapterListener {
        void subscribe(User user);
    }

    private ArrayList<User> users;
    private UserAdapter.UserAdapterListener listener;

    public UserAdapter(ArrayList<User> users, UserAdapterListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_user_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        vh.btnSubscribe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = (int)v.getTag();
                if (listener != null)
                    listener.subscribe(users.get(position));
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User u = users.get(position);
        holder.uuid.setText(u.getUuid());
        holder.name.setText(u.getName());
        //holder.created_at.setText(u.getCreated_at());
        holder.btnSubscribe.setTag(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView uuid;
        public TextView name;
        public TextView created_at;
        public RelativeLayout relativeLayout;
        public Button btnSubscribe;

        public ViewHolder(View v) {
            super(v);

            this.uuid = (TextView) v.findViewById(R.id.uuid);
            this.name = (TextView) v.findViewById(R.id.name);
            this.created_at = (TextView) v.findViewById(R.id.created_at);
            this.relativeLayout = (RelativeLayout) v.findViewById(R.id.relLayout);
            this.btnSubscribe = (Button) v.findViewById(R.id.btnSubscribe);
        }
    }
}

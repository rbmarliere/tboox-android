package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.nsupdate.tboox.tboox.BookDetailActivity;
import info.nsupdate.tboox.tboox.R;
import info.nsupdate.tboox.tboox.adapters.BookAdapter;
import info.nsupdate.tboox.tboox.adapters.CollectionAdapter;
import info.nsupdate.tboox.tboox.models.Book;
import info.nsupdate.tboox.tboox.models.Collection;
import info.nsupdate.tboox.tboox.utils.APIHandler;
import info.nsupdate.tboox.tboox.utils.Services;

public class CollectionListFragment extends android.support.v4.app.Fragment implements CollectionAdapter.CollectionAdapterListener
{
    private CollectionListFragment.OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public CollectionListFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        APIHandler handler = new APIHandler() {
            @Override
            public void handle_data(JSONObject response) {
                try {
                    JSONArray data_array = response.getJSONArray("data");

                    ArrayList<Collection> collection = new ArrayList<>();
                    for (int i = 0; i < data_array.length(); i++)
                        collection.add(new Collection(data_array.getJSONObject(i)));

                    mAdapter = new CollectionAdapter(collection, CollectionListFragment.this);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
            }
        };

        Services.get(this.getContext(), handler, "/collection");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_collection_list, container, false);

        mLayoutManager = new LinearLayoutManager(this.getContext());

        mRecyclerView = (RecyclerView) v.findViewById(R.id.collection_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return v;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
            mListener.onFragmentInteraction(uri);
    }

    @Override
    public void didClickCollection(Collection collection) {
        final String uuid = collection.getBook_id();
        APIHandler handler = new APIHandler() {
            @Override
            public void handle_data(JSONObject response) {
                Intent i = new Intent(getContext(), BookDetailActivity.class);
                i.putExtra("book", new Book(response));
                startActivity(i);
            }
        };

        Services.get(this.getContext(), handler, "/book/" + uuid);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

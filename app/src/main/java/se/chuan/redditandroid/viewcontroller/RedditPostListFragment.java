package se.chuan.redditandroid.viewcontroller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import se.chuan.redditandroid.R;
import se.chuan.redditandroid.model.RedditPost;
import se.chuan.redditandroid.util.DataFetchAsyncTask;
import se.chuan.redditandroid.util.DataFetchTaskResult;

/**
 * Created by suchuan on 2017-04-25.
 */

public class RedditPostListFragment extends ListFragment implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener{

    ArrayList<RedditPost> postItems = new ArrayList<>();


    private class RedditPostFetchTask extends DataFetchAsyncTask {

        @Override
        protected void onPostExecute(DataFetchTaskResult<ArrayList<RedditPost>> result){

            if(result.getError() != null ){
                // Fetching Data Error
                result.getError().printStackTrace();
                Context context = getActivity().getApplicationContext();
                Toast toast = Toast.makeText(context, "Failed loading data", Toast.LENGTH_SHORT);
                toast.show();
            }else{
                // Update ListView
                postItems.addAll(result.getResult());
                RedditItemAdapter adpter = (RedditItemAdapter) getListView().getAdapter();
                adpter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setListAdapter(new RedditItemAdapter(postItems));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        getListView().setOnScrollListener(this);
        getListView().setOnItemClickListener(this);
        new RedditPostFetchTask().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RedditPost item = postItems.get(position);
        Uri redditPostUri = Uri.parse(item.getUrl());
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, redditPostUri);
        startActivity(browserIntent);
    }

    // Instead of loading data once user reached bottom, we load data when user reaching 3/4 of listView
    // This idea is borrowed from iOS mail & Youtube app for smooth infinite scrolling.
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int cellsLeft = view.getCount() / 4;
        if (view.getLastVisiblePosition() >= view.getCount() - cellsLeft) {
            String after = postItems.get(postItems.size() -1).getName();
            new RedditPostFetchTask().execute(after);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    // ListView CellView Adapter
    private class RedditItemAdapter extends ArrayAdapter<RedditPost>{

        public RedditItemAdapter(ArrayList<RedditPost> items){
            super(getActivity(),0,items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            convertView = getActivity().getLayoutInflater().inflate(R.layout.reddit_post_item,null);
            RedditPost item = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.post_item_titleTextView);
            TextView subRedditTextView = (TextView) convertView.findViewById(R.id.post_item_subRedditTextView);
            TextView scoreTextView = (TextView) convertView.findViewById(R.id.post_item_scoreTextView);

            titleTextView.setText(item.getName());
            subRedditTextView.setText(item.getSubReddit());
            scoreTextView.setText(String.valueOf(item.getScore()));

            return convertView;
        }

    }


}

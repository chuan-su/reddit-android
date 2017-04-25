package se.chuan.redditandroid.controller;

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

import java.util.ArrayList;

import se.chuan.redditandroid.R;
import se.chuan.redditandroid.model.RedditPost;
import se.chuan.redditandroid.util.DataFetchAsyncTask;

/**
 * Created by suchuan on 2017-04-25.
 */

public class RedditPostListFragment extends ListFragment {

    ArrayList<RedditPost> postItems = new ArrayList<>();

    private class RedditPostFetchTask extends DataFetchAsyncTask {

        @Override
        protected void onPostExecute(ArrayList<RedditPost> items){
            postItems.addAll(items);

            RedditItemAdapter adpter = (RedditItemAdapter) getListView().getAdapter();

            if(adpter == null)
                setListAdapter(new RedditItemAdapter(postItems));

            adpter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view.getLastVisiblePosition() >= view.getCount() - 5) {
                    new RedditPostFetchTask().execute();
                }
//                if (scrollState == SCROLL_STATE_IDLE) {
//
//                }
            }
        });
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RedditPost item = postItems.get(position);
                Uri redditPostUri = Uri.parse(item.getUrl());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, redditPostUri);
                startActivity(browserIntent);
            }
        });
        new RedditPostFetchTask().execute();
    }

    private class RedditItemAdapter extends ArrayAdapter<RedditPost>{

        public RedditItemAdapter(ArrayList<RedditPost> items){
            super(getActivity(),0,items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            convertView = getActivity().getLayoutInflater().inflate(R.layout.post_item,null);
            RedditPost item = getItem(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.post_item_titleTextView);
            titleTextView.setText(item.getName());
            TextView scoreTextView = (TextView) convertView.findViewById(R.id.post_item_scoreTextView);
            scoreTextView.setText(String.valueOf(item.getScore()));
            return convertView;
        }

    }


}

package se.chuan.redditandroid.util;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import se.chuan.redditandroid.model.RedditPost;

/**
 * Created by suchuan on 2017-04-25.
 */

public class DataFetchAsyncTask extends AsyncTask<String,Integer,ArrayList<RedditPost>> {

    @Override
    protected ArrayList<RedditPost> doInBackground(String... params) {
        if(params.length > 0)
            return new DataFetcher(params[0]).fetchPostItems();
        else
            return new DataFetcher().fetchPostItems();
    }
}

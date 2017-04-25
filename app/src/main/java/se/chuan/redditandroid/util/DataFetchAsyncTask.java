package se.chuan.redditandroid.util;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.util.ArrayList;

import se.chuan.redditandroid.model.RedditPost;

/**
 * Created by suchuan on 2017-04-25.
 */

public class DataFetchAsyncTask extends AsyncTask<String,Integer,DataFetchTaskResult<ArrayList<RedditPost>>> {

    @Override
    protected DataFetchTaskResult<ArrayList<RedditPost>> doInBackground(String... params) {

        DataFetchTaskResult<ArrayList<RedditPost>> result;
        switch (params.length){
            case 0:  result = new DataFetcher().fetchPostItems();
                break;
            default: result = new DataFetcher(params[0]).fetchPostItems();
                break;
        }
        return result;
    }
}

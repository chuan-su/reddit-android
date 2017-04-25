package se.chuan.redditandroid.util;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import se.chuan.redditandroid.model.RedditPost;

/**
 * Created by suchuan on 2017-04-25.
 */

public class DataFetcher{
    public static final String ENDPOINT = "https://www.reddit.com/r/gaming/top.json";

    String urlString;

    public DataFetcher(){
        this.urlString = Uri.parse(ENDPOINT).toString();
    }
    public DataFetcher(String after){
        this.urlString = Uri.parse(ENDPOINT)
                .buildUpon()
                .appendQueryParameter("after",after)
                .build()
                .toString();
    }

    public DataFetchTaskResult<ArrayList<RedditPost>> fetchPostItems() {
        ArrayList<RedditPost> items = new ArrayList<>();
        try {
            String responseText = new String(fetchData());
            JSONObject responseBody = new JSONObject(responseText);
            JSONArray postsJsonArray = responseBody.getJSONObject("data").getJSONArray("children");

            for(int i = 0; i< postsJsonArray.length();i++){
                JSONObject postJsonObj = postsJsonArray.getJSONObject(i).getJSONObject("data");
                RedditPost redditPost = new RedditPost();

                redditPost.setName(postJsonObj.getString("name"));
                redditPost.setSubReddit(postJsonObj.getString("subreddit"));
                redditPost.setScore(postJsonObj.getInt("score"));
                redditPost.setUrl(postJsonObj.getString("url"));

                items.add(redditPost);
            }
            return new DataFetchTaskResult<>(items);
        }
        catch (IOException | JSONException e){
            return new DataFetchTaskResult<>(e);
        }
    }

    private byte[] fetchData() throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            if (urlConnection .getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(urlConnection.getResponseMessage());
            }
            byte[] buffer = new byte[1024];
            int length;
            while((length = in.read(buffer)) > 0){
                out.write(buffer,0,length);
            }
            out.close();
            return out.toByteArray();

        }finally {
            urlConnection.disconnect();
        }
    }

}

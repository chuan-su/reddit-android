package se.chuan.redditandroid;

import org.junit.Test;

import java.util.ArrayList;

import se.chuan.redditandroid.model.RedditPost;
import se.chuan.redditandroid.util.DataFetcher;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

/**
 * Created by suchuan on 2017-04-25.
 */

public class DataFetcherTest {

    @Test
    public void fetchPostItemsTest(){
        ArrayList<RedditPost> posts = new DataFetcher().fetchPostItems();
        RedditPost post = posts.get(0);
        assertEquals(posts.size(),25);
        assertNotNull(post.getName());
        assertNotNull(post.getSubReddit());
        assertNotNull(post.getUrl());
        // post score initial value is zero if not specified
        assertNotSame(post.getScore(),0);
    }
}

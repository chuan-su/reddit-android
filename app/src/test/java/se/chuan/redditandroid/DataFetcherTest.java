package se.chuan.redditandroid;

import org.junit.Test;

import java.util.ArrayList;

import se.chuan.redditandroid.model.RedditPost;
import se.chuan.redditandroid.util.DataFetchTaskResult;
import se.chuan.redditandroid.util.DataFetcher;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;

/**
 * Created by suchuan on 2017-04-25.
 */

public class DataFetcherTest {

    @Test
    public void fetchPostItemsTest(){
        DataFetchTaskResult<ArrayList<RedditPost>> result = new DataFetcher().fetchPostItems();
        assertNull(result.getError());
        RedditPost post = result.getResult().get(0);
        assertNotNull(post.getName());
        assertNotNull(post.getSubReddit());
        assertNotNull(post.getUrl());
        assertNotSame(post.getScore(),0); // post score initial value is zero if not specified
    }
}

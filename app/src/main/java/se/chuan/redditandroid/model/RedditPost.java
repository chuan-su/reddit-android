package se.chuan.redditandroid.model;

/**
 * Created by suchuan on 2017-04-25.
 */

public class RedditPost {

    private String name;
    private String subReddit;
    private String url;
    private int score;

    public void setName(String name){
        this.name = name;
    }

    public void setSubReddit(String subReddit){
        this.subReddit = subReddit;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getName(){
        return name;
    }

    public String getSubReddit(){ return subReddit; }

    public int getScore(){
        return score;
    }

    public String getUrl(){
        return url;
    }

}

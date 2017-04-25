package se.chuan.redditandroid.util;

/**
 * Created by suchuan on 2017-04-25.
 */

public class DataFetchTaskResult<T> {

    private  T result;
    private  Exception error;

    public DataFetchTaskResult(T result){
        this.result = result;
    }

    public DataFetchTaskResult(Exception e){
        this.error = e;
    }

    public T getResult(){
        return result;
    }

    public Exception getError(){
        return error;
    }
}

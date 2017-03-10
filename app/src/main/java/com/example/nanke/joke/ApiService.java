package com.example.nanke.joke;



import com.example.nanke.joke.JavaBean.MyJoke;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zt on 2017/3/10.
 */

public interface ApiService {
    /**
     * 来福笑话接口
     * @return List<MyJoke>
     */
    @GET("xiaohua.json")
    Observable<List<MyJoke>> getData();
}

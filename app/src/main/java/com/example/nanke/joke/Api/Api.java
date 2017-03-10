package com.example.nanke.joke.Api;

import com.example.nanke.joke.JavaBean.MyJoke;

import java.util.List;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by apple on 2017/3/5.
 */

public interface Api {
    @GET("xiaohua.json")
    Call<List<MyJoke>> getData();
}

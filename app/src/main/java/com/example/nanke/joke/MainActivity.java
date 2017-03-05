package com.example.nanke.joke;


import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.nanke.joke.Api.Api;
import com.example.nanke.joke.JavaBean.MyJoke;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<MyJoke> jokes = new ArrayList<>();
    private MyAdpter adpter;
    private MaterialRefreshLayout materialRefreshLayout;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ReFresh();
    }

    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);

        System.out.println("获取的数据长度" + jokes.size());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adpter = new MyAdpter(jokes);
        recyclerView.setAdapter(adpter);
    }

    public void ReFresh() {

        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        adpter.refreshData(jokes);
                        materialRefreshLayout.finishRefresh();
                    }
                }, 2000);

            }
        });
    }

    public void getData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.laifudao.com/open/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        api.getData().enqueue(new retrofit2.Callback<List<MyJoke>>() {
            @Override
            public void onResponse(retrofit2.Call<List<MyJoke>> call, retrofit2.Response<List<MyJoke>> response) {
                jokes = response.body();
            }

            @Override
            public void onFailure(retrofit2.Call<List<MyJoke>> call, Throwable t) {
                System.out.println("失败" + t.toString());
            }
        });

    }

}

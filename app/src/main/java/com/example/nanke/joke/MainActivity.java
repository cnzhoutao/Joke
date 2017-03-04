package com.example.nanke.joke;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
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


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MyJoke> jokes = new ArrayList<>();
    private OkHttpClient okHttpClient;
    private MyAdpter adpter;
    private MaterialRefreshLayout materialRefreshLayout;
    private final String url = "http://api.laifudao.com/open/xiaohua.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        getData(url);
        adpter = new MyAdpter(jokes);
       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adpter);

        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(url);
                        adpter.refreshData(jokes);
                        materialRefreshLayout.finishRefresh();
                    }
                }, 2000);

            }
        });
    }

    public void getData(String url) {

        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("网络请求失败" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("网络请求成功");
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    jokes = pharseJson(json);
                }
            }
        });

    }

    public List<MyJoke> pharseJson(final String json) {
        final List<MyJoke> list1 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        MyJoke joke = new MyJoke();
                        joke.setTitle(jsonObject.getString("title"));
                        joke.setContent(jsonObject.getString("content"));
                        joke.setUrl(jsonObject.getString("url"));
                        joke.setPoster(jsonObject.getString("poster"));
                        System.out.println(jsonObject.getString("title"));
                        list1.add(joke);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return list1;
    }
}

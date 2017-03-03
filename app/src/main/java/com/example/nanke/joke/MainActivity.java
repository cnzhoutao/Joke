package com.example.nanke.joke;


import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.nanke.joke.JavaBean.MyJoke;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MyJoke> jokes;
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            jokes = (List<MyJoke>) msg.obj;
            MyAdpter adpter = new MyAdpter(jokes);
            StaggeredGridLayoutManager layoutManager =
                    new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adpter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        getData("http://api.laifudao.com/open/xiaohua.json");
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
                    pharseJson(json);
                }
            }
        });
    }

    public void pharseJson(String json) {
      
    }



}

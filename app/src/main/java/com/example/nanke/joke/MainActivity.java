package com.example.nanke.joke;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.nanke.joke.JavaBean.MyJoke;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MyJoke> jokes = new ArrayList<>();

    private MyAdpter adpter;
    private MaterialRefreshLayout materialRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        getData();


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


        HttpMethods.getInstance().getJoke(new Observer<List<MyJoke>>() {
            Disposable d;
            @Override
            public void onSubscribe(Disposable d) {
                this.d=d;
            }

            @Override
            public void onNext(List<MyJoke> myJokes) {
                jokes=myJokes;
                adpter = new MyAdpter(myJokes);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adpter);
                adpter.notifyDataSetChanged();
                Log.d("MAIN", "获取数据完成" + myJokes.size());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MAIN", "error" + e.toString());
                d.dispose();
            }

            @Override
            public void onComplete() {
                Log.d("MAIN", "onComplete");
                d.dispose();
            }
        });

    }


}

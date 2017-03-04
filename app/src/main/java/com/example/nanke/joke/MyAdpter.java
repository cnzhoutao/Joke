package com.example.nanke.joke;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nanke.joke.JavaBean.MyJoke;

import java.util.List;

/**
 * Created by zt on 2017/3/3.
 */

public class MyAdpter extends RecyclerView.Adapter<MyAdpter.ViewHolder> {
    private List<MyJoke> mData;

    public MyAdpter(List<MyJoke> list) {
        this.mData = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyJoke joke = mData.get(position);
        holder.title.setText(joke.getTitle());
       // holder.content.setText(joke.getContent());
        Spanned sp = Html.fromHtml( joke.getContent() );
        holder.content.setText(sp);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    /**
     * 下拉刷新，清除原有数据，添加新数据
     *
     * @param newData
     */

    public void refreshData(List<MyJoke> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyItemRangeChanged(0, mData.size());
    }

}

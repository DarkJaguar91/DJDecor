package com.darkjaguar.djdecor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.darkjaguar.dj_decor.header.DJDecorRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.recycler)
    protected DJDecorRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.getRecyclerView().setLayoutManager(layoutManager);
        adapter = new SimpleAdapter();
        recyclerView.getRecyclerView().setAdapter(adapter);

        recyclerView.getSwipeRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.getSwipeRefreshLayout().setRefreshing(false);
                    }
                }, 2500);
            }
        });
//        layoutManager.scrollToPosition(adapter.getItemCount() - 1);

    }

    @OnClick(R.id.loaderz)
    public void onClickerize() {
        startActivity(new Intent(this, MainActivity.class));
    }
}

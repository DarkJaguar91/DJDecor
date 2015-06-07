package com.darkjaguar.djdecor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.darkjaguar.dj_decor.header.DJHeaderDecor;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.recycler)
    protected RecyclerView recyclerView;
    @InjectView(R.id.orientation_button)
    Button btnOrient;
    @InjectView(R.id.direction_button)
    Button btnDirect;
    private LinearLayoutManager layoutManager;
    private DJHeaderDecor djHeaderDecor;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SimpleAdapter();
        recyclerView.setAdapter(adapter);
        djHeaderDecor = new DJHeaderDecor(adapter);
        recyclerView.addItemDecoration(djHeaderDecor);

        btnOrient.setText("Vertical");
        btnDirect.setText("Normal");
    }

    @OnClick(R.id.direction_button)
    public void onDirectionChanged() {
        btnDirect.setText(layoutManager.getReverseLayout() ? "Reversed" : "Normal");
        layoutManager.setReverseLayout(!layoutManager.getReverseLayout());
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.orientation_button)
    public void onOrientChanged() {
        djHeaderDecor.clearCache();
        btnOrient.setText(layoutManager.getOrientation() == RecyclerView.VERTICAL ? "Horizontal" : "Vertical");
        layoutManager.setOrientation(layoutManager.getOrientation() == RecyclerView.VERTICAL ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
        adapter.notifyDataSetChanged();
    }
}

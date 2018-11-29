package com.covetus.absaudit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectMainLocationActivity extends Activity implements Listener {


    public static RecyclerView rvTop;
    public static RecyclerView rvBottom;

    @BindView(R.id.tvEmptyListTop)
    TextView tvEmptyListTop;
    @BindView(R.id.tvEmptyListBottom)
    TextView tvEmptyListBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_main_location);
        ButterKnife.bind(this);
        rvTop = (RecyclerView)findViewById(R.id.rvTop);
        rvBottom = (RecyclerView)findViewById(R.id.rvBottom);

        initTopRecyclerView();
        initBottomRecyclerView();

        tvEmptyListTop.setVisibility(View.GONE);
        tvEmptyListBottom.setVisibility(View.GONE);
    }

    private void initTopRecyclerView() {
        rvBottom.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));

        List<String> topList = new ArrayList<>();
        topList.add("CarA");
        topList.add("CarB");
        topList.add("CarC");
        topList.add("CarD");
        DragListAdapter topListAdapter = new DragListAdapter(topList, this,"0");
        rvBottom.setAdapter(topListAdapter);
        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
        rvBottom.setOnDragListener(topListAdapter.getDragInstance());

    }

    private void initBottomRecyclerView() {
        rvTop.setLayoutManager(new GridLayoutManager(this,2));
        List<String> bottomList = new ArrayList<>();
        DragListAdapter bottomListAdapter = new DragListAdapter(bottomList, this,"1");
        rvTop.setAdapter(bottomListAdapter);
        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        rvTop.setOnDragListener(bottomListAdapter.getDragInstance());


      /*  rvBottom.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this,rvBottom,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                            String title1 = ((TextView) rvBottom.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.text)).getText().toString();
                            Toast.makeText(getApplicationContext(), title1, Toast.LENGTH_SHORT).show();

                                //remove
                               *//* ListTowAdapter adapterSource = (ListTowAdapter) rvBottom.getAdapter();
                                String list = adapterSource.getList().get(position);
                                List<String> listSource = adapterSource.getList();
                                listSource.remove(position);
                                adapterSource.updateList(listSource);
                                adapterSource.notifyDataSetChanged();
                                //add
                                ListAdapter adapterTarget = (ListAdapter)rvTop.getAdapter();
                                List<String> customListTarget = adapterTarget.getList();
                                customListTarget.add(title1);
                                adapterTarget.updateList(customListTarget);
                                adapterTarget.notifyDataSetChanged();*//*






                            }
                            @Override
                            public void onLongItemClick(View view, int position) {
                            }
                        }
                )
        );*/


    }

    public static void getRemove(int position){
        String title1 = ((TextView) rvBottom.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.text)).getText().toString();
        DragListAdapter adapterSource = (DragListAdapter) rvBottom.getAdapter();
        String list = adapterSource.getList().get(position);
        List<String> listSource = adapterSource.getList();
        listSource.remove(position);
        adapterSource.updateList(listSource);
        adapterSource.notifyDataSetChanged();
        //add
        DragListAdapter adapterTarget = (DragListAdapter)rvTop.getAdapter();
        List<String> customListTarget = adapterTarget.getList();
        customListTarget.add(title1);
        adapterTarget.updateList(customListTarget);
        adapterTarget.notifyDataSetChanged();

    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        //tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        //rvTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        //tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        //rvBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }
}

package com.covetus.absaudit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ABS_CUSTOM_VIEW.TextViewRegular;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.AuditMainLocation;
import ABS_GET_SET.SelectedLocation;
import ABS_HELPER.DatabaseHelper;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectMainLocationActivity extends Activity implements Listener {

    public static RecyclerView rvTop;
    public static RecyclerView rvBottom;
    public static String mStrDelete = "0";
    public static HashMap<String, String> meMap = new HashMap<String, String>();
    public static HashMap<String, String> meMapDesc = new HashMap<String, String>();
    public static HashMap<String, String> meMapServerId = new HashMap<String, String>();
    public static HashMap<String, String> meMapLocalId = new HashMap<String, String>();
    @BindView(R.id.tvEmptyListTop)
    TextView tvEmptyListTop;
    @BindView(R.id.tvEmptyListBottom)
    TextView tvEmptyListBottom;
    @BindView(R.id.mLayoutDelete)
    RelativeLayout mLayoutDelete;

    @BindView(R.id.mLayoutNext)
    RelativeLayout mLayoutNext;


    public static TextViewRegular mTxtLocationDesc;

    public static TextViewSemiBold mTextNormal;

    ArrayList<AuditMainLocation> mListArry = new ArrayList<>();
    DatabaseHelper db;


    @OnClick(R.id.mLayoutNext)
    public void mLayoutGoNext() {
    DragListAdapter adapterSource = (DragListAdapter) rvTop.getAdapter();
    List<String> listSource = adapterSource.getList();
    if(listSource.size()>0){

        for(int i = 0;i<listSource.size();i++){
            SelectedLocation selectedLocation = new SelectedLocation();
            selectedLocation.setmStrAuditId("73");
            selectedLocation.setmStrUserId("44");
            selectedLocation.setmStrMainLocationLocalId(meMapLocalId.get(listSource.get(i)));
            selectedLocation.setmStrMainLocationServerId(meMapServerId.get(listSource.get(i)));
            selectedLocation.setmStrMainLocationTitle(listSource.get(i));
            selectedLocation.setmStrMainLocationCount(meMap.get(listSource.get(i)));
            //insert
            db.insert_tb_selected_main_location(selectedLocation);
        }
        Intent intent = new Intent(SelectMainLocationActivity.this,LocationSubFolder.class);
        startActivity(intent);
        
    }




    }


    @OnClick(R.id.mLayoutDelete)
    public void mLayoutDelete() {
        DragListAdapter adapterSource = (DragListAdapter) rvTop.getAdapter();
        List<String> listSource = adapterSource.getList();
        if (listSource.size() > 0) {
            if (mStrDelete.equals("0")) {
                mTextNormal.setText("Done");
                mStrDelete = "1";
                adapterSource.notifyDataSetChanged();
            } else {
                mTextNormal.setText("Delete");
                mStrDelete = "0";
                adapterSource.notifyDataSetChanged();
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_main_location);
        ButterKnife.bind(this);
        db = new DatabaseHelper(SelectMainLocationActivity.this);
        rvTop = (RecyclerView) findViewById(R.id.rvTop);
        rvBottom = (RecyclerView) findViewById(R.id.rvBottom);
        mTextNormal = (TextViewSemiBold) findViewById(R.id.mTextNormal);
        mTxtLocationDesc = (TextViewRegular) findViewById(R.id.mTxtLocationDesc);
        initTopRecyclerView();
        initBottomRecyclerView();
        tvEmptyListTop.setVisibility(View.GONE);
        tvEmptyListBottom.setVisibility(View.GONE);
    }

    private void initTopRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(SelectMainLocationActivity.this);
        mListArry = db.get_all_tb_audit_main_location();
        List<String> topList = new ArrayList<>();
        for (int i = 0; i<mListArry.size();i++){
        topList.add(mListArry.get(i).getmStrLocationTitle());
        meMap.put(mListArry.get(i).getmStrLocationTitle(), "0");
        meMapDesc.put(mListArry.get(i).getmStrLocationTitle(),mListArry.get(i).getmStrLocationDesc());
        meMapServerId.put(mListArry.get(i).getmStrLocationTitle(),mListArry.get(i).getmStrLocationServerId());
        meMapLocalId.put(mListArry.get(i).getmStrLocationTitle(),mListArry.get(i).getmStrId());
        }
        rvBottom.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
        DragListAdapter topListAdapter = new DragListAdapter(topList, this, "0");
        rvBottom.setAdapter(topListAdapter);
        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
        rvBottom.setOnDragListener(topListAdapter.getDragInstance());

    }

    private void initBottomRecyclerView() {
        rvTop.setLayoutManager(new GridLayoutManager(this, 2));
        List<String> bottomList = new ArrayList<>();
        DragListAdapter bottomListAdapter = new DragListAdapter(bottomList, this, "1");
        rvTop.setAdapter(bottomListAdapter);
        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        rvTop.setOnDragListener(bottomListAdapter.getDragInstance());
    }

    public static void getRemove(int position) {
        String title1 = ((TextView) rvTop.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.text)).getText().toString();
        DragListAdapter adapterSource = (DragListAdapter) rvTop.getAdapter();
        String list = adapterSource.getList().get(position);
        List<String> listSource = adapterSource.getList();
        SelectMainLocationActivity.meMap.put(listSource.get(position), "0");
        listSource.remove(position);
        adapterSource.updateList(listSource);
        adapterSource.notifyDataSetChanged();


        //add
        //mStrDelete = "0";
        DragListAdapter adapterTarget = (DragListAdapter) rvBottom.getAdapter();
        List<String> customListTarget = adapterTarget.getList();
        customListTarget.add(title1);
        //countList.add("0");
        adapterTarget.updateList(customListTarget);
        adapterTarget.notifyDataSetChanged();

        DragListAdapter adapterSo = (DragListAdapter) rvTop.getAdapter();
        List<String> listSo = adapterSo.getList();
        System.out.println("<><><>" + listSo.size());
        if (listSo.size() == 0) {
            mTextNormal.setText("Delete");
            mStrDelete = "0";
        }


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

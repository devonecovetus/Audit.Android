package com.covetus.absaudit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ABS_ADAPTER.AgentList;
import ABS_ADAPTER.LayerListAdapter;
import ABS_CUSTOM_VIEW.TextViewRegular;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.LayerList;
import ABS_GET_SET.MainLocationSubFolder;
import ABS_GET_SET.SideMenu;
import ABS_HELPER.DatabaseHelper;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityLayerList extends Activity {

    ArrayList<LayerList> mListItems = new ArrayList<LayerList>();
    @BindView(R.id.mTxtFolderTitle)
    TextViewSemiBold mTxtFolderTitle;
    @BindView(R.id.mListView)
    ListView mListView;
    @BindView(R.id.mLayoutAddMore)
    RelativeLayout mLayoutAddMore;
    @BindView(R.id.mTxtMainLocationTitle)
    TextViewRegular mTxtMainLocationTitle;
    DatabaseHelper databaseHelper;
    String mSubFolderId;
    LayerListAdapter layerListAdapter;

    @OnClick(R.id.mLayoutAddMore)
    public void mAddQuantity() {
        LayerList layerList = new LayerList();
        layerList.setmStrUserId(PreferenceManager.getFormiiId(ActivityLayerList.this));
        layerList.setmStrAuditId(mListItems.get(0).getmStrAuditId());
        layerList.setmStrLayerDesc(mListItems.get(0).getmStrMainLocationTitle()+" Name");
        layerList.setmStrLayerTitle(mListItems.get(0).getmStrMainLocationTitle()+" "+(mListItems.size()+1));
        layerList.setmStrMainLocationId(mListItems.get(0).getmStrMainLocationId());
        layerList.setmStrMainLocationTitle(mListItems.get(0).getmStrMainLocationTitle());
        layerList.setmStrSubFolderTitle(mListItems.get(0).getmStrSubFolderTitle());
        layerList.setmStrSubFolderId(mListItems.get(0).getmStrSubFolderId());
        databaseHelper.insert_tb_sub_folder_explation_list(layerList);
        databaseHelper.update_tb_location_sub_folder_count(mSubFolderId);
        databaseHelper.update_tb_selected_main_location_count(mListItems.get(0).getmStrMainLocationId());
        mListItems.clear();
        mListItems = databaseHelper.get_all_tb_sub_folder_explation_list(mSubFolderId);
        System.out.println("<><><>"+mListItems.size());
        layerListAdapter = new LayerListAdapter(ActivityLayerList.this,mListItems);
        mListView.setAdapter(layerListAdapter);
        mTxtMainLocationTitle.setText(mListItems.get(0).getmStrMainLocationTitle());
        mTxtFolderTitle.setText(mListItems.get(0).getmStrSubFolderTitle()+"("+mListItems.size()+" "+mListItems.get(0).getmStrMainLocationTitle()+")");
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layer_list);
        ButterKnife.bind(this);
        databaseHelper = new DatabaseHelper(ActivityLayerList.this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
        mSubFolderId = bundle.getString("mSubFolderId");
        }
        mListItems = databaseHelper.get_all_tb_sub_folder_explation_list(mSubFolderId);
        layerListAdapter = new LayerListAdapter(ActivityLayerList.this,mListItems);
        mListView.setAdapter(layerListAdapter);
        mTxtMainLocationTitle.setText(mListItems.get(0).getmStrMainLocationTitle());
        mTxtFolderTitle.setText(mListItems.get(0).getmStrSubFolderTitle()+"("+mListItems.size()+" "+mListItems.get(0).getmStrMainLocationTitle()+")");
    }



}
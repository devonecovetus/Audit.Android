package com.covetus.absaudit;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin1 on 12/10/18.
 */

public class ActivityTabHostMain extends FragmentActivity {
    private FragmentTabHost mTabHost;
    ArrayList<Integer> mArrayListActive = new ArrayList<>();
    ArrayList<Integer> mArrayListDeactive = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    String mStrCurrentTab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost_main);
       /* fo getting cuurenttab value*/
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
         mStrCurrentTab = bundle.getString("mStrCurrentTab");
        }
        inStart();
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(createTabView(R.mipmap.ic_tabbar_imagefile)),
                FragmentDashboard.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(createTabView(R.mipmap.ic_tabbar_report)),
                FragmentChatList.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator(createTabView(R.mipmap.ic_tabbar_report)),
                FragmentDashboard.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator(createTabView(R.mipmap.ic_tabbar_report)),
                FragmentDashboard.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab5").setIndicator(createTabView(R.mipmap.ic_tabbar_report)),
                FragmentAuditList.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab6").setIndicator(createTabView(R.mipmap.ic_tabbar_report)),
                FragmentSetting.class, null);


        TabWidget widget = mTabHost.getTabWidget();
        for (int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);
            ImageView img = v.findViewById(R.id.mImgTab);
            if (i == 0) {
                img.setImageResource(mArrayListActive.get(i));
            }
            else {
                img.setImageResource(mArrayListDeactive.get(i));
            }
        }

        /*change tab background on click*/
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                TabWidget widget = mTabHost.getTabWidget();
                for (int i = 0; i < widget.getChildCount(); i++) {
                    View v = widget.getChildAt(i);
                    ImageView img = v.findViewById(R.id.mImgTab);
                    if (i == mTabHost.getCurrentTab()) {
                        img.setImageResource(mArrayListActive.get(i));
                    }
                    else {
                        img.setImageResource(mArrayListDeactive.get(i));
                    }
                }
            }
        });
        mTabHost.setCurrentTab(Integer.parseInt(mStrCurrentTab));

    }


    private View createTabView(Integer integer) {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_tabhost_tab, null);
        ImageView mImgTab = (ImageView) view.findViewById(R.id.mImgTab);
        mImgTab.setImageResource(integer);
        return view;
    }

    /*icon add in bottom tab bar*/
    void inStart(){


        mArrayListActive.add(R.mipmap.ic_tabbar_home_active);
        mArrayListActive.add(R.mipmap.ic_tabbar_chat_active);
        mArrayListActive.add(R.mipmap.ic_tabbar_download_active);
        mArrayListActive.add(R.mipmap.ic_tabbar_media_active);
        mArrayListActive.add(R.mipmap.ic_tabbar_folder_active);
        mArrayListActive.add(R.mipmap.ic_tabbar_setting_active);

        mArrayListDeactive.add(R.mipmap.ic_tabbar_home);
        mArrayListDeactive.add(R.mipmap.ic_tabbar_chat);
        mArrayListDeactive.add(R.mipmap.ic_tabbar_download);
        mArrayListDeactive.add(R.mipmap.ic_tabbar_media);
        mArrayListDeactive.add(R.mipmap.ic_tabbar_folder);
        mArrayListDeactive.add(R.mipmap.ic_tabbar_setting);

    }

    /*Double click for closing app*/
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.mText_back_again, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }



}
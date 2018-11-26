package com.covetus.absaudit;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import ABS_ADAPTER.AgentList;
import ABS_ADAPTER.ChatList;
import ABS_GET_SET.SideMenu;
import ABS_HELPER.CommonUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityAgentList extends Activity {

    ArrayList<SideMenu> mListItems = new ArrayList<>();
    AgentList agentList;
    @BindView(R.id.mListChat)
    ListView mListChat;
    @BindView(R.id.mImgBack)
    ImageView mImgBack;

    /*click for going back*/
    @OnClick(R.id.mImgBack)
    void mClose() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        SideMenu sideMenu = new SideMenu();
        sideMenu.setmSideMenuIconActive(R.mipmap.ic_actionbar_menu);
        sideMenu.setmSideMenuTitle(getString(R.string.mTextMenu_Account));
        SideMenu sideMenu31 = new SideMenu();
        sideMenu31.setmSideMenuIconActive(R.mipmap.ic_actionbar_menu);
        sideMenu31.setmSideMenuTitle(getString(R.string.mTextMenu_Account));
        SideMenu sideMenu3 = new SideMenu();
        sideMenu3.setmSideMenuIconActive(R.mipmap.ic_actionbar_menu);
        sideMenu3.setmSideMenuTitle(getString(R.string.mTextMenu_Account));
        SideMenu sideMenu4 = new SideMenu();
        sideMenu4.setmSideMenuIconActive(R.mipmap.ic_actionbar_menu);
        sideMenu4.setmSideMenuTitle(getString(R.string.mTextMenu_Account));
        SideMenu sideMenu5 = new SideMenu();
        sideMenu5.setmSideMenuIconActive(R.mipmap.ic_actionbar_menu);
        sideMenu5.setmSideMenuTitle(getString(R.string.mTextMenu_Account));
        SideMenu sideMenu6 = new SideMenu();
        sideMenu6.setmSideMenuIconActive(R.mipmap.ic_actionbar_menu);
        sideMenu6.setmSideMenuTitle(getString(R.string.mTextMenu_Account));
        SideMenu sideMenu7 = new SideMenu();
        sideMenu7.setmSideMenuIconActive(R.mipmap.ic_actionbar_menu);
        sideMenu7.setmSideMenuTitle(getString(R.string.mTextMenu_Account));

        mListItems.add(sideMenu);
        mListItems.add(sideMenu31);
        mListItems.add(sideMenu3);
        mListItems.add(sideMenu4);
        mListItems.add(sideMenu5);
        mListItems.add(sideMenu6);
        mListItems.add(sideMenu7);

        agentList = new AgentList(ActivityAgentList.this, mListItems);
        mListChat.setAdapter(agentList);
    }
}
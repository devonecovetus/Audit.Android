package com.covetus.absaudit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

import ABS_ADAPTER.AgentList;
import ABS_ADAPTER.ChatList;
import ABS_ADAPTER.DashboardSideMenu;
import ABS_GET_SET.SideMenu;
import ABS_HELPER.CommonUtils;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentChatList extends Fragment {



    ArrayList<SideMenu> mListItems = new ArrayList<>();
    ChatList chatList;
    @BindView(R.id.mListChat)
    ListView mListChat;

    @BindView(R.id.mImgAddNewChat)
    ImageView mImgAddNewChat;

    @OnClick(R.id.mImgAddNewChat)
    void mAddNewChat() {
        CommonUtils.OnClick(getActivity(), mImgAddNewChat);
        Intent intent = new Intent(getActivity(), ActivityAgentList.class);
        startActivity(intent);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
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

        chatList = new ChatList(getActivity(), mListItems);
        mListChat.setAdapter(chatList);

    }


}

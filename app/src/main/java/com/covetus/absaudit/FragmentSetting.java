package com.covetus.absaudit;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

import ABS_ADAPTER.DashboardSideMenu;
import ABS_GET_SET.SideMenu;
import ABS_HELPER.CommonUtils;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentSetting extends Fragment {


    @BindView(R.id.mImgMainToggale)
    ImageView mImgMainToggale;
    @BindView(R.id.mImgMessage)
    ImageView mImgMessage;
    @BindView(R.id.mImgAudit)
    ImageView mImgAudit;
    @BindView(R.id.mImgReport)
    ImageView mImgReport;
    @BindView(R.id.mLayoutContactUs)
    LinearLayout mLayoutContactUs;
    @BindView(R.id.mLayoutChangePassword)
    LinearLayout mLayoutChangePassword;
    @BindView(R.id.mLayoutLogout)
    RelativeLayout mLayoutLogout;

    int main = 0, audit = 0, message = 0, report = 0;


    /* click for contact us */
    @OnClick(R.id.mLayoutContactUs)
    void mGoForContactUs() {
        Intent intent = new Intent(getActivity(), ActivityContactUs.class);
        startActivity(intent);
    }

    /* click for change password */
    @OnClick(R.id.mLayoutChangePassword)
    void mGoForChangePassword() {
        Intent intent = new Intent(getActivity(), ActivityResetPassword.class);
        startActivity(intent);
    }

    /* click for logout */
    @OnClick(R.id.mLayoutLogout)
    void mGoForLogOut() {
        new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.mText_logout)
                .setIcon(R.mipmap.ic_app_icon)
                .setMessage(R.string.mTextAlert_logout)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PreferenceManager.cleanData(getActivity());
                        Intent intent = new Intent(getActivity(),ActivityLogin.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    /* click for push notification indicator */
    @OnClick(R.id.mImgMainToggale)
    void mMainSwitch() {
        if (main == 0) {
            main = 1;
            audit = 1;
            message = 1;
            report = 1;
            mImgMainToggale.setImageResource(R.drawable.toggle_on);
            mImgAudit.setImageResource(R.drawable.toggle_on);
            mImgMessage.setImageResource(R.drawable.toggle_on);
            mImgReport.setImageResource(R.drawable.toggle_on);
        } else {
            main = 0;
            audit = 0;
            message = 0;
            report = 0;
            mImgMainToggale.setImageResource(R.drawable.toggle_off);
            mImgAudit.setImageResource(R.drawable.toggle_off);
            mImgMessage.setImageResource(R.drawable.toggle_off);
            mImgReport.setImageResource(R.drawable.toggle_off);
        }
    }

    /* click for auditor notification indicator */
    @OnClick(R.id.mImgAudit)
    void mAuditSwitch() {
        if (audit == 0) {
            audit = 1;
            mImgAudit.setImageResource(R.drawable.toggle_on);
        } else {
            audit = 0;
            mImgAudit.setImageResource(R.drawable.toggle_off);
        }
    }

    /* click for message notification indicator */
    @OnClick(R.id.mImgMessage)
    void mMessageSwitch() {
        if (message == 0) {
            message = 1;
            mImgMessage.setImageResource(R.drawable.toggle_on);
        } else {
            message = 0;
            mImgMessage.setImageResource(R.drawable.toggle_off);
        }

    }

    /* click for report notification indicator */
    @OnClick(R.id.mImgReport)
    void mReportSwitch() {
        if (report == 0) {
            report = 1;
            mImgReport.setImageResource(R.drawable.toggle_on);
        } else {
            report = 0;
            mImgReport.setImageResource(R.drawable.toggle_off);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}

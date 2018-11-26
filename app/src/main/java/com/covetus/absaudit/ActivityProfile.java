package com.covetus.absaudit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ABS_CUSTOM_VIEW.EditTextRegular;
import ABS_CUSTOM_VIEW.TextViewBold;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_HELPER.AppController;
import ABS_HELPER.CommonUtils;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ABS_HELPER.CommonUtils.hidePDialog;
import static ABS_HELPER.CommonUtils.mStrBaseUrlImage;

public class ActivityProfile extends Activity {

    @BindView(R.id.mTxtUserName)
    TextViewBold mTxtUserName;
    @BindView(R.id.mTxtUserEmail)
    TextViewBold mTxtUserEmail;
    @BindView(R.id.mLayoutUpdateDetails)
    LinearLayout mLayoutUpdateDetails;
    @BindView(R.id.mImageBack)
    ImageView mImageBack;
    @BindView(R.id.mImgUserProfile)
    ImageView mImgUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        ButterKnife.bind(this);

    }

  /*update profile image when image is updated*/
    @Override
    protected void onResume() {
        mTxtUserName.setText(PreferenceManager.getFormiiFullName(ActivityProfile.this));
        mTxtUserEmail.setText(PreferenceManager.getFormiiEmail(ActivityProfile.this));
        Glide.with(ActivityProfile.this).load(mStrBaseUrlImage + PreferenceManager.getFormiiProfileimg(ActivityProfile.this)).asBitmap().centerCrop().placeholder(R.drawable.placeholder_user_profile).into(new BitmapImageViewTarget(mImgUserProfile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(ActivityProfile.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mImgUserProfile.setImageDrawable(circularBitmapDrawable);
            }
        });
        super.onResume();
    }

    /* click for going to update profile screen */
    @OnClick(R.id.mLayoutUpdateDetails)
    public void mLayoutGoToUpdateProfile() {
        //CommonUtils.OnClick(ActivityProfile.this, mLayoutUpdateDetails);
        Intent intent = new Intent(ActivityProfile.this,ActivityUpdateProfile.class);
        startActivity(intent);
        //finish();
    }

    /* click for going back */
    @OnClick(R.id.mImageBack)
    public void mGoBack() {
       finish();
    }
}

package com.covetus.absaudit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ABS_CUSTOM_VIEW.EditTextRegular;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_HELPER.AppController;
import ABS_HELPER.CommonUtils;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ABS_HELPER.CommonUtils.hidePDialog;

public class ActivitySplash extends Activity {


    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        /*delay splash for 3 sec*/
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (PreferenceManager.getFormiiIsLogin(getApplicationContext()).equals("1")){
                    Intent i = new Intent(ActivitySplash.this, ActivityTabHostMain.class);
                    i.putExtra("mStrCurrentTab","0");
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(ActivitySplash.this, ActivityLogin.class);
                    startActivity(i);
                    finish();
                }





            }
        }, SPLASH_TIME_OUT);
    }

    }




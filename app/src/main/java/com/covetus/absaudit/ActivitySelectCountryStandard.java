package com.covetus.absaudit;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ABS_ADAPTER.DocumentList;
import ABS_CUSTOM_VIEW.TextViewBold;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.Audit;
import ABS_HELPER.AppController;
import ABS_HELPER.CommonUtils;
import ABS_HELPER.DatabaseHelper;
import ABS_HELPER.PreferenceManager;
import ABS_HELPER.RecyclerItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ABS_HELPER.CommonUtils.hasPermissions;
import static ABS_HELPER.CommonUtils.hidePDialog;
import static ABS_HELPER.CommonUtils.mShowAlert;
import static ABS_HELPER.CommonUtils.mStrDownloadPath;
import static ABS_HELPER.CommonUtils.show;

public class ActivitySelectCountryStandard extends Activity {



    @BindView(R.id.mImageBack)
    ImageView mImageBack;
    @BindView(R.id.mLayoutDone)
    RelativeLayout mLayoutDone;
    @BindView(R.id.mSpinnerCountryStandard)
    Spinner mSpinnerCountryStandard;
    @BindView(R.id.mSpinnerLanguageStandard)
    Spinner mSpinnerLanguageStandard;
    HashMap<String,ArrayList<String>> meMap=new HashMap<String, ArrayList<String>>();
    HashMap<String,String> hmLanguage=new HashMap<String,String>();
    ArrayList<String> mListCountry = new ArrayList<>();
    ArrayList<String> mListCountryId = new ArrayList<>();
    String mStrCountryId,mStrLanguageCode;

    String mAuditId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country_language);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
        mAuditId = bundle.getString("mAuditId");
        }
        CommonUtils.show(ActivitySelectCountryStandard.this);
        mToGetDetail();





        mSpinnerCountryStandard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                String mSelectedTitle = mListCountry.get(pos);
                mStrCountryId = mListCountryId.get(pos);
                if(mSelectedTitle.equals("Select Country Standard")){
                    String[] mStringArray = new String[1];
                    mStringArray[0] = "Select Language";
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ActivitySelectCountryStandard.this,R.layout.spinner_item,mStringArray
                    );
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    mSpinnerLanguageStandard.setAdapter(spinnerArrayAdapter);

                }else {
                    ArrayList<String> listLanguage =  meMap.get(mSelectedTitle);
                    listLanguage.add(0,"Select Language");
                    String[] mStringArray = new String[listLanguage.size()];
                    mStringArray = listLanguage.toArray(mStringArray);
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ActivitySelectCountryStandard.this,R.layout.spinner_item,mStringArray
                    );
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    mSpinnerLanguageStandard.setAdapter(spinnerArrayAdapter);

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        mSpinnerLanguageStandard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {

            String mSelectedTitle = mSpinnerLanguageStandard.getSelectedItem().toString();
            if(mSelectedTitle.equals("Select Language")){
                mStrLanguageCode = "0";

            }else {
                mStrLanguageCode = hmLanguage.get(mSelectedTitle);
            }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }

    /*click for going back*/
    @OnClick(R.id.mLayoutDone)
    public void mGoNext() {
    CommonUtils.OnClick(ActivitySelectCountryStandard.this, mLayoutDone);
    System.out.println("<><><>"+mStrCountryId);
    System.out.println("<><><>"+mStrLanguageCode);
    if(mStrCountryId.equals("0")){
    CommonUtils.mShowAlert("Please select country standard",ActivitySelectCountryStandard.this);
    return;
    }else if(mStrLanguageCode.equals("0")){
    CommonUtils.mShowAlert("Please select language",ActivitySelectCountryStandard.this);
    return;
    }
    CommonUtils.show(ActivitySelectCountryStandard.this);



    }



    /*click for going back*/
    @OnClick(R.id.mImageBack)
    public void mGoBack() {
        finish();
    }


    /*api cal to get audit detail*/
    void mToGetDetail() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonUtils.mStrBaseUrl + "getCountryStandard",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("status");
                            if (mStrStatus.equals("1")) {
                                JSONObject jsonObject = response.getJSONObject("response");
                                JSONArray jsonArray = jsonObject.getJSONArray("standard");
                                for (int i =0;i<jsonArray.length();i++){
                                 ArrayList<String> mListLanguage = new ArrayList<>();
                                 JSONObject jsonArrayObject = jsonArray.getJSONObject(i);
                                 String mStrCountry = jsonArrayObject.getString("country");
                                 String mStrCountryId = jsonArrayObject.getString("country_id");
                                 mListCountry.add(mStrCountry);
                                 mListCountryId.add(mStrCountryId);
                                 JSONArray arrayLanguage = jsonArrayObject.getJSONArray("language");
                                 for (int j =0;j<arrayLanguage.length();j++){
                                 JSONObject jsonArrayObjLanguage = arrayLanguage.getJSONObject(j);
                                 String mStrLanguageId = jsonArrayObjLanguage.getString("id");
                                 String mStrLanguageTitle = jsonArrayObjLanguage.getString("title");
                                 String mStrLanguageLang = jsonArrayObjLanguage.getString("lang");
                                 mListLanguage.add(mStrLanguageTitle);
                                 hmLanguage.put(mStrLanguageTitle,mStrLanguageLang);
                                 }
                                 meMap.put(mStrCountry,mListLanguage);
                                 }

                                mListCountry.add(0,"Select Country Standard");
                                mListCountryId.add(0,"0");
                                String[] mStringArray = new String[mListCountry.size()];
                                mStringArray = mListCountry.toArray(mStringArray);
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ActivitySelectCountryStandard.this,R.layout.spinner_item,mStringArray
                                );
                                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                                mSpinnerCountryStandard.setAdapter(spinnerArrayAdapter);
                            } else if (mStrStatus.equals("2")) {
                                CommonUtils.showSessionExp(ActivitySelectCountryStandard.this);
                            } else {
                                mShowAlert(response.getString("message"), ActivitySelectCountryStandard.this);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        Toast.makeText(ActivitySelectCountryStandard.this, getString(R.string.mTextFile_error_something_went_wrong) + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getFormiiId(ActivitySelectCountryStandard.this));
                params.put("auth_token", PreferenceManager.getFormiiAuthToken(ActivitySelectCountryStandard.this));params.put("id", PreferenceManager.getFormiiId(ActivitySelectCountryStandard.this));
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }
}
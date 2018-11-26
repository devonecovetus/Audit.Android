package com.covetus.absaudit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
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
import ABS_HELPER.AppController;
import ABS_HELPER.CommonUtils;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ABS_HELPER.CommonUtils.hidePDialog;

public class ActivityResetPassword extends Activity {


    @BindView(R.id.mEditNewPassword)
    EditTextRegular mEditNewPassword;
    @BindView(R.id.mEditConfirmPassword)
    EditTextRegular mEditConfirmPassword;
    @BindView(R.id.mEditOldPassword)
    EditTextRegular mEditOldPassword;
    @BindView(R.id.mLayoutSignIn)
    RelativeLayout mLayoutSignIn;
    @BindView(R.id.mImageBack)
    ImageView mImageBack;
    String mStrNewPassword, mStrConfirmPassword, mStrOldPassword;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);

    }

    /* click for going back */
    @OnClick(R.id.mImageBack)
    public void mGoBack() {
        CommonUtils.closeKeyBoard(ActivityResetPassword.this);
        finish();
    }

    /* click to submit change password*/
    @OnClick(R.id.mLayoutSignIn)
    public void mGoForSubmit() {
        CommonUtils.closeKeyBoard(ActivityResetPassword.this);
        CommonUtils.OnClick(ActivityResetPassword.this, mLayoutSignIn);
        mStrNewPassword = mEditNewPassword.getText().toString();
        mStrConfirmPassword = mEditConfirmPassword.getText().toString();
        mStrOldPassword = mEditOldPassword.getText().toString();
        /*validation for reset password*/
        if(mStrOldPassword.length() <= 0) {
            CommonUtils.mShowAlert("Please enter your current password.", ActivityResetPassword.this);
            return;
        }else if (mStrNewPassword.length() <= 0) {
            CommonUtils.mShowAlert("Please enter your new password.", ActivityResetPassword.this);
            return;
        }else if (mStrConfirmPassword.length() <= 0) {
            CommonUtils.mShowAlert("Please re-enter your new password.", ActivityResetPassword.this);
            return;
        }else if (mStrNewPassword.length() < 6) {
            CommonUtils.mShowAlert("Password must contain at least 6 characters.", ActivityResetPassword.this);
            return;
        }else if (mStrConfirmPassword.length() < 6) {
            CommonUtils.mShowAlert("Password must contain at least 6 characters.", ActivityResetPassword.this);
            return;
        }else if (!mStrConfirmPassword.equals(mStrNewPassword)) {
            CommonUtils.mShowAlert("New and confirm password does not match.", ActivityResetPassword.this);
            return;
        }
        CommonUtils.show(ActivityResetPassword.this);
        mToDoForget();
        }

        /*api call for change password*/
    void mToDoForget() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonUtils.mStrBaseUrl + "changePassword",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("status");
                            if (mStrStatus.equals("1")) {
                                CommonUtils.mShowAlert(response.getString("message"), ActivityResetPassword.this);
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        PreferenceManager.cleanData(ActivityResetPassword.this);
                                        Intent intent = new Intent(ActivityResetPassword.this, ActivityLogin.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, SPLASH_TIME_OUT);


                            }else if(mStrStatus.equals("2")) {
                                CommonUtils.showSessionExp(ActivityResetPassword.this);
                            } else {
                                CommonUtils.mShowAlert(response.getString("message"), ActivityResetPassword.this);
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
                        Toast.makeText(ActivityResetPassword.this, getString(R.string.mTextFile_error_something_went_wrong), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("current_password", mStrOldPassword);
                params.put("new_password", mStrNewPassword);
                params.put("confirm_password", mStrConfirmPassword);
                params.put("id", PreferenceManager.getFormiiId(ActivityResetPassword.this));
                params.put("auth_token", PreferenceManager.getFormiiAuthToken(ActivityResetPassword.this));
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }

    /*close keyboard on backpress and finish the activity*/
    @Override
    public void onBackPressed() {
        CommonUtils.closeKeyBoard(ActivityResetPassword.this);
        finish();
        super.onBackPressed();
    }
}

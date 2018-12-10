package com.covetus.absaudit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ABS_ADAPTER.AudittList;
import ABS_GET_SET.Audit;
import ABS_GET_SET.Message;
import ABS_HELPER.AppController;
import ABS_HELPER.CommonUtils;
import ABS_HELPER.DatabaseHelper;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import static ABS_HELPER.CommonUtils.hidePDialog;
import static ABS_HELPER.CommonUtils.show;

public class FragmentAuditList extends Fragment {

    public static ArrayList<Audit> mListItems = new ArrayList<>();
    public static AudittList audittList;
    public static ListView mListChat;
    public static DatabaseHelper db;
    public static Activity activity;

    public static void reload() {
        mListItems.clear();
        db = new DatabaseHelper(activity);
        /* database data into list */
        mListItems = db.get_all_tb_list_audit(PreferenceManager.getFormiiId(activity), "0");
        if (mListItems.size() > 0) {
            audittList = new AudittList(activity, mListItems);
            mListChat.setAdapter(audittList);
        } else {
            audittList.notifyDataSetChanged();
            CommonUtils.mShowAlert(activity.getString(R.string.mTextFile_error_no_record_found), activity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audit_list, container, false);
        ButterKnife.bind(this, view);
        mListChat = (ListView) view.findViewById(R.id.mListChat);
        activity = getActivity();
        /* database intilization */
        db = new DatabaseHelper(getActivity());

        if (PreferenceManager.getFormiiCheckIsFirstTime(getActivity()).equals("1")) {
            PreferenceManager.setFormiiCheckIsFirstTime(getActivity(), "0");
            audittList = new AudittList(getActivity(), mListItems);
            mListChat.setAdapter(audittList);
            show(getActivity());
            mToGetAuditList();
        } else {
               /* database data into list */
            mListItems = db.get_all_tb_list_audit(PreferenceManager.getFormiiId(getActivity()), "0");
            if (mListItems.size() > 0) {
                audittList = new AudittList(getActivity(), mListItems);
                mListChat.setAdapter(audittList);
            } else {
                CommonUtils.mShowAlert(getString(R.string.mTextFile_error_no_record_found), getActivity());
            }
        }
        return view;
    }

    /*api call for getting audit list*/
    void mToGetAuditList() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonUtils.mStrBaseUrl + "getauditHistory",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        mListItems.clear();
                        hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("status");
                            if (mStrStatus.equals("1")) {
                                JSONObject jsonObject = response.getJSONObject("response");
                                JSONArray jsonArray = jsonObject.getJSONArray("history");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Audit audit = new Audit();
                                    JSONObject mAgentObj = jsonArray.getJSONObject(i);
                                    JSONObject jAuditDetail = mAgentObj.getJSONObject("auditDetails");
                                    audit.setmUserId(PreferenceManager.getFormiiId(getActivity()));
                                    audit.setmAuditId(jAuditDetail.getString("id"));
                                    audit.setmTitle(jAuditDetail.getString("title"));
                                    audit.setmDueDate(jAuditDetail.getString("end_date"));
                                    JSONObject jAgentDetail = mAgentObj.getJSONObject("agentDetails");
                                    audit.setmAssignBy(jAgentDetail.getString("fulname"));
                                    audit.setmStatus("0");
                                    mListItems.add(audit);
                                    db.insert_tb_list_audit(audit);
                                }
                                audittList.notifyDataSetChanged();
                            } else if (mStrStatus.equals("2")) {
                                CommonUtils.showSessionExp(getActivity());
                            } else {
                                CommonUtils.mShowAlert(response.getString("message"), getActivity());
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
                        Toast.makeText(getActivity(), getString(R.string.mTextFile_error_something_went_wrong), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getFormiiId(getActivity()));
                params.put("auth_token", PreferenceManager.getFormiiAuthToken(getActivity()));
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }
}
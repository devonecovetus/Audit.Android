package com.covetus.absaudit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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

import ABS_ADAPTER.ChatList;
import ABS_CUSTOM_VIEW.EditTextRegular;
import ABS_GET_SET.Message;
import ABS_HELPER.AppController;
import ABS_HELPER.CommonUtils;
import ABS_HELPER.DatabaseHelper;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ABS_HELPER.CommonUtils.checkInternetConnection;
import static ABS_HELPER.CommonUtils.hidePDialog;
import static ABS_HELPER.CommonUtils.mShowAlert;
import static ABS_HELPER.CommonUtils.show;


public class FragmentChatList extends Fragment {

    ArrayList<Message> mListItems = new ArrayList<>();
    ArrayList<Message> mListSearchItems = new ArrayList<>();
    ChatList chatList;
    int textlength = 0;
    DatabaseHelper db;
    int mChatId;

    @BindView(R.id.mEditChatSearch)
    EditTextRegular mEditChatSearch;
    @BindView(R.id.mListChat)
    ListView mListChat;
    @BindView(R.id.mImgAddNewChat)
    ImageView mImgAddNewChat;

    @OnClick(R.id.mImgAddNewChat)
    void mAddNewChat() {
        CommonUtils.OnClick(getActivity(), mImgAddNewChat);
         /* Internet Connectivity Check */
        if (checkInternetConnection(getActivity())) {
            Intent intent = new Intent(getActivity(), ActivityAgentList.class);
            startActivity(intent);
        } else {
            mShowAlert(getString(R.string.mTextFile_alert_no_internet), getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, view);
        db = new DatabaseHelper(getActivity());
        if (checkInternetConnection(getActivity())) {
            db.delete_tb_chat_user_list();
            db.delete_tb_chat_msg_list();
        }
        mListChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message messageChat = mListSearchItems.get(position);
                String mRoleType = messageChat.getmUserRole();
                System.out.println("<><>mChatRoomId" + messageChat.getmStrChatRoomId());
                if (mRoleType.equals("Admin")) {
                    Intent intent = new Intent(getActivity(), ActivityAuditorToAdminChat.class);
                    intent.putExtra("mChatRoomId", messageChat.getmStrChatRoomId());
                    intent.putExtra("mChatUserId", messageChat.getmChatUserId());
                    intent.putExtra("mChatUserName", messageChat.getmUserName());
                    intent.putExtra("mChatUserRole", messageChat.getmUserRole());
                    intent.putExtra("mChatUserPhoto", messageChat.getmUserPhoto());
                    intent.putExtra("mChatUserEmail", messageChat.getmUserEmail());
                    intent.putExtra("mChatUserPhone", messageChat.getmUserPhone());
                    startActivity(intent);
                } else if (mRoleType.equals("Agent")) {
                    Intent intent = new Intent(getActivity(), ActivityAuditorToAgentChat.class);
                    intent.putExtra("mChatRoomId", messageChat.getmStrChatRoomId());
                    intent.putExtra("mChatUserId", messageChat.getmChatUserId());
                    intent.putExtra("mChatUserName", messageChat.getmUserName());
                    intent.putExtra("mChatUserRole", messageChat.getmUserRole());
                    intent.putExtra("mChatUserPhoto", messageChat.getmUserPhoto());
                    intent.putExtra("mChatUserEmail", messageChat.getmUserEmail());
                    intent.putExtra("mChatUserPhone", messageChat.getmUserPhone());
                    startActivity(intent);
                }
            }
        });

           /* Search Box */
        mEditChatSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int arg1, int arg2, int arg3) {
                textlength = mEditChatSearch.getText().length();
                mListSearchItems.clear();
                if (textlength == 0) {
//                    isLoadMore = true;
                    mListSearchItems.addAll(mListItems);
                } else {
//                    isLoadMore = false;
                    for (int i = 0; i < mListItems.size(); i++) {
                        if (textlength <= mListItems.get(i).getmUserName().length()) {
                            if (mListItems.get(i).getmUserName().toLowerCase().trim().contains(
                                    mEditChatSearch.getText().toString().toLowerCase().trim())) {
                                mListSearchItems.add(mListItems.get(i));
                            }
                        }
                    }
                }
                chatList = new ChatList(getActivity(), mListSearchItems);
                mListChat.setAdapter(chatList);
                chatList.notifyDataSetChanged();
            }
        });
        return view;
    }

    /*api call for getting chat list*/
    void mToGetChatList() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonUtils.mStrBaseUrl + "getChatUsersList",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        mListItems.clear();
                        mListSearchItems.clear();
                        hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("status");
                            if (mStrStatus.equals("1")) {
                                JSONArray jsonArray = response.getJSONArray("response");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Message messageChat = new Message();
                                    JSONObject mAgentObj = jsonArray.getJSONObject(i);
                                    messageChat.setmStrUserId(PreferenceManager.getFormiiId(getActivity()));
                                    messageChat.setmUserRole(mAgentObj.getString("role"));
                                    messageChat.setmChatUserId(mAgentObj.getString("user_id"));
                                    messageChat.setmUserName(mAgentObj.getString("username"));
                                    messageChat.setmUserPhoto(mAgentObj.getString("photo"));
                                    messageChat.setmUserLastMsg(mAgentObj.getString("msg"));
                                    messageChat.setmUserMsgDate(mAgentObj.getString("time"));
                                    messageChat.setmUserMsgFrom(mAgentObj.getString("from_id"));
                                    messageChat.setmUserMsgTo(mAgentObj.getString("to_id"));
                                    messageChat.setmUserEmail(mAgentObj.getString("email"));
                                    messageChat.setmUserPhone(mAgentObj.getString("phone"));
                                    mChatId = db.insert_tb_chat_user_list(messageChat);
                                    messageChat.setmStrChatRoomId(mChatId);
                                    mListItems.add(messageChat);
                                    mListSearchItems.add(messageChat);
                                    System.out.println("<><><mChatId" + mChatId);
                                }
                                chatList.notifyDataSetChanged();
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

    @Override
    public void onResume() {
        super.onResume();
        db = new DatabaseHelper(getActivity());

         /* Internet Connectivity Check */
        if (checkInternetConnection(getActivity())) {
            show(getActivity());
            mToGetChatList();
            db.delete_tb_chat_user_list();
            chatList = new ChatList(getActivity(), mListSearchItems);
            mListChat.setAdapter(chatList);
        } else {
             /* database data into list */
            mListItems.clear();
            mListSearchItems.clear();

            mListSearchItems = db.get_all_tb_chat_user_list(PreferenceManager.getFormiiId(getActivity()));
            mListItems = db.get_all_tb_chat_user_list(PreferenceManager.getFormiiId(getActivity()));

            System.out.println("<><>mListItems" + mListSearchItems.size());
            if (mListSearchItems.size() > 0) {
                chatList = new ChatList(getActivity(), mListSearchItems);
                mListChat.setAdapter(chatList);
            } else {
                CommonUtils.mShowAlert(getString(R.string.mTextFile_error_no_record_found), getActivity());
            }
           /* mShowAlert(getString(R.string.mTextFile_alert_no_internet), getActivity());*/
        }

    }
}

package com.covetus.absaudit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ABS_ADAPTER.AudittList;
import ABS_ADAPTER.DraftList;
import ABS_GET_SET.Audit;
import ABS_HELPER.CommonUtils;
import ABS_HELPER.DatabaseHelper;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentDraft extends Fragment {


    public static ArrayList<Audit> mListItems = new ArrayList<>();
    public static DraftList audittList;
    public static ListView mListChat;
    public static DatabaseHelper db;

    public static Activity activity;

    public static void reload() {
        mListItems.clear();
        db = new DatabaseHelper(activity);
        /* database data into list */
        mListItems = db.get_all_tb_list_audit(PreferenceManager.getFormiiId(activity), "1");
        if (mListItems.size() > 0) {
            audittList = new DraftList(activity, mListItems);
            mListChat.setAdapter(audittList);
        } else {
            audittList.notifyDataSetChanged();
            CommonUtils.mShowAlert(activity.getString(R.string.mTextFile_error_no_record_found), activity);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_draft_list, container, false);
        ButterKnife.bind(this, view);
        mListChat = (ListView) view.findViewById(R.id.mListChat);
        activity = getActivity();
        /* database intilization */
        db = new DatabaseHelper(getActivity());

        /* database data into list */
        mListItems = db.get_all_tb_list_audit(PreferenceManager.getFormiiId(getActivity()), "1");
        if (mListItems.size() > 0) {
            audittList = new DraftList(getActivity(), mListItems);
            mListChat.setAdapter(audittList);
        } else {
            CommonUtils.mShowAlert(getString(R.string.mTextFile_error_no_record_found), getActivity());
        }


       /* mListChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String mAuditId = mListItems.get(position).getmAuditId();
                if(db.get_data_count_tb_selected_main_location(mAuditId)>0){
                Intent intent = new Intent(getActivity(),SelectMainLocationActivity.class);
                intent.putExtra("mAuditId",mAuditId);
                startActivity(intent);
                }else {
                Intent intent = new Intent(getActivity(),ActivitySelectCountryStandard.class);
                intent.putExtra("mAuditId",mAuditId);
                startActivity(intent);
                }
            }

        });*/


        return view;
    }


}

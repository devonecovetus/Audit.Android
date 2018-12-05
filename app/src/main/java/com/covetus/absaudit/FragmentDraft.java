package com.covetus.absaudit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ABS_ADAPTER.AudittList;
import ABS_GET_SET.Audit;
import ABS_HELPER.CommonUtils;
import ABS_HELPER.DatabaseHelper;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentDraft extends Fragment {


    ArrayList<Audit> mListItems = new ArrayList<>();
    AudittList audittList;
    @BindView(R.id.mListChat)
    ListView mListChat;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_draft_list, container, false);
        ButterKnife.bind(this, view);
        /* database intilization */
        db = new DatabaseHelper(getActivity());
        /* database data into list */
        mListItems = db.get_all_tb_list_audit(PreferenceManager.getFormiiId(getActivity()),"1");
        if (mListItems.size() > 0) {
            audittList = new AudittList(getActivity(), mListItems);
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

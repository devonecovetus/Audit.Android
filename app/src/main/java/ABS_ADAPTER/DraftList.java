/**
 * Created by admin1 on 27/2/18.
 */
package ABS_ADAPTER;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.covetus.absaudit.ActivitySelectCountryStandard;
import com.covetus.absaudit.R;
import com.covetus.absaudit.SelectMainLocationActivity;

import java.util.ArrayList;

import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.Audit;
import ABS_HELPER.DatabaseHelper;


public class DraftList extends BaseAdapter {

    Activity context;
    private ArrayList<Audit> mListItems = new ArrayList<>();


    public DraftList(Activity context, ArrayList<Audit> mListItems) {
        this.mListItems = mListItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.item_draft_list, null);
            holder = new ViewHolder();
            holder.mTxtAuditDate = (TextViewSemiBold) convertView.findViewById(R.id.mTxtAuditDate);
            holder.mTxtAuditTitle = (TextViewSemiBold) convertView.findViewById(R.id.mTxtAuditTitle);
            holder.mTxtAgentName = (TextViewSemiBold) convertView.findViewById(R.id.mTxtAgentName);
            holder.mLayoutViewAudit = (ImageView) convertView.findViewById(R.id.mLayoutViewAudit);
            holder.mLayoutUploadAudit = (ImageView) convertView.findViewById(R.id.mLayoutUploadAudit);
            holder.mLayoutMain = (RelativeLayout) convertView.findViewById(R.id.mLayoutMain);

            final Audit audit = mListItems.get(position);
            holder.mTxtAuditDate.setText(audit.getmDueDate());
            holder.mTxtAuditTitle.setText(audit.getmTitle());
            holder.mTxtAgentName.setText(audit.getmAssignBy());
            if (audit.getmStatus().equals("0")) {
                holder.mLayoutMain.setBackground(context.getResources().getDrawable(R.drawable.red_border));
            } else {
                holder.mLayoutMain.setBackground(context.getResources().getDrawable(R.drawable.yellow_border));
            }
            holder.mLayoutViewAudit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper db = new DatabaseHelper(context);
                    String mAuditId = audit.getmAuditId();
                    if (db.get_data_count_tb_selected_main_location(mAuditId) > 0) {
                        Intent intent = new Intent(context, SelectMainLocationActivity.class);
                        intent.putExtra("mAuditId", mAuditId);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ActivitySelectCountryStandard.class);
                        intent.putExtra("mAuditId", mAuditId);
                        context.startActivity(intent);
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    private class ViewHolder {
        TextViewSemiBold mTxtAuditDate;
        TextViewSemiBold mTxtAuditTitle;
        TextViewSemiBold mTxtAgentName;
        ImageView mLayoutViewAudit,mLayoutUploadAudit;
        RelativeLayout mLayoutMain;
    }


}
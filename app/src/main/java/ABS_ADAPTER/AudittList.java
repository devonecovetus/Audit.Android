/**
 * Created by admin1 on 27/2/18.
 */
package ABS_ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.covetus.absaudit.R;

import java.util.ArrayList;

import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.Audit;
import ABS_GET_SET.SideMenu;


public class AudittList extends BaseAdapter {

    private ArrayList<Audit> mListItems = new ArrayList<>();
    Context context;
    int selectedPos;


    public AudittList(Context context, ArrayList<Audit> mListItems) {
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
            convertView = mInflater.inflate(R.layout.item_audit_list, null);
            holder = new ViewHolder();
            holder.mTxtAuditDate = (TextViewSemiBold) convertView.findViewById(R.id.mTxtAuditDate);
            holder.mTxtAuditTitle = (TextViewSemiBold) convertView.findViewById(R.id.mTxtAuditTitle);
            holder.mTxtAgentName = (TextViewSemiBold) convertView.findViewById(R.id.mTxtAgentName);
            //holder.mStatusLine = (RelativeLayout) convertView.findViewById(R.id.mStatusLine);
            holder.mLayoutMain = (RelativeLayout) convertView.findViewById(R.id.mLayoutMain);
            Audit audit = mListItems.get(position);
            holder.mTxtAuditDate.setText(audit.getmDueDate());
            holder.mTxtAuditTitle.setText(audit.getmTitle());
            holder.mTxtAgentName.setText(audit.getmAssignBy());


            //holder.mStatusLine.setLayoutParams(new RelativeLayout.LayoutParams(30, parent.getHeight()));



            convertView.setTag(holder);
            //System.out.println("<><><><>"+holder.mLayoutMain.getMeasuredHeight());
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }


    private class ViewHolder {
        TextViewSemiBold mTxtAuditDate;
        TextViewSemiBold mTxtAuditTitle;
        TextViewSemiBold mTxtAgentName;
        //RelativeLayout mStatusLine;
        RelativeLayout mLayoutMain;
    }


}
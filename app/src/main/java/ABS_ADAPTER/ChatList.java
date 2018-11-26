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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.covetus.absaudit.R;

import java.util.ArrayList;

import ABS_CUSTOM_VIEW.TextViewRegular;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.SideMenu;


public class ChatList extends BaseAdapter {

    private ArrayList<SideMenu> mListItems = new ArrayList<>();
    Context context;
    int selectedPos;


    public ChatList(Context context, ArrayList<SideMenu> mListItems) {
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
            convertView = mInflater.inflate(R.layout.item_chat_list, null);
            holder = new ViewHolder();
            holder.mImgChatUser = (ImageView)convertView.findViewById(R.id.mImgChatUser);
            holder.mTextUserName = (TextViewSemiBold) convertView.findViewById(R.id.mTextUserName);
            holder.mTextUserLastMsg = (TextViewRegular) convertView.findViewById(R.id.mTextUserLastMsg);
            holder.mTextUserLastMsgDate = (TextViewRegular) convertView.findViewById(R.id.mTextUserLastMsgDate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }


    private class ViewHolder {

        ImageView mImgChatUser;
        TextViewSemiBold mTextUserName;
        TextViewRegular mTextUserLastMsg;
        TextViewRegular mTextUserLastMsgDate;

    }

    public void mSetSelection(int i){
    selectedPos = i;
    notifyDataSetChanged();
    }
}
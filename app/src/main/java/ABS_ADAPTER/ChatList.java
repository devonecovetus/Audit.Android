/**
 * Created by admin1 on 27/2/18.
 */
package ABS_ADAPTER;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.covetus.absaudit.R;

import java.util.ArrayList;

import ABS_CUSTOM_VIEW.TextViewRegular;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.Message;

import static ABS_HELPER.CommonUtils.covertTimeToText;


public class ChatList extends BaseAdapter {

    Context context;
    int selectedPos;
    private ArrayList<Message> mListItems = new ArrayList<>();


    public ChatList(Context context, ArrayList<Message> mListItems) {
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
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.item_chat_list, null);
            holder = new ViewHolder();
            holder.mImgChatUser = (ImageView) convertView.findViewById(R.id.mImgChatUser);
            holder.mTextUserName = (TextViewSemiBold) convertView.findViewById(R.id.mTextUserName);
            holder.mTextUserLastMsg = (TextViewRegular) convertView.findViewById(R.id.mTextUserLastMsg);
            holder.mTextUserLastMsgDate = (TextViewRegular) convertView.findViewById(R.id.mTextUserLastMsgDate);
            Message messageChat = mListItems.get(position);
            System.out.println("<><><name" + messageChat.getmUserName());
            System.out.println("<><><pic" + messageChat.getmUserPhoto());
            holder.mTextUserLastMsg.setText(messageChat.getmUserLastMsg());
            holder.mTextUserName.setText(messageChat.getmUserName());
            holder.mTextUserLastMsgDate.setText(covertTimeToText(messageChat.getmUserMsgDate()));
            Glide.with(context).load(messageChat.getmUserPhoto()).asBitmap().centerCrop().placeholder(R.drawable.placeholder_user_profile).into(new BitmapImageViewTarget(holder.mImgChatUser) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.mImgChatUser.setImageDrawable(circularBitmapDrawable);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    public void mSetSelection(int i) {
        selectedPos = i;
        notifyDataSetChanged();
    }

    private class ViewHolder {

        ImageView mImgChatUser;
        TextViewSemiBold mTextUserName;
        TextViewRegular mTextUserLastMsg;
        TextViewRegular mTextUserLastMsgDate;

    }
}
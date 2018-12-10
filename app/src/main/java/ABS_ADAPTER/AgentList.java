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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.covetus.absaudit.R;

import java.util.ArrayList;

import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.Agent;


public class AgentList extends BaseAdapter {

    Context context;
    boolean[] separatorAtIndex;
    private ArrayList<Agent> mListItems = new ArrayList<>();

    public AgentList(Context context, ArrayList<Agent> mListItems) {
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
            convertView = mInflater.inflate(R.layout.item_agent_list, null);
            holder = new ViewHolder();
            holder.mImgChatUser = (ImageView) convertView.findViewById(R.id.mImgChatUser);
            holder.mTextUserName = (TextViewSemiBold) convertView.findViewById(R.id.mTextUserName);
            holder.mHeaderLayout = (LinearLayout) convertView.findViewById(R.id.mHeaderLayout);
            holder.mTextHeader = (TextViewSemiBold) convertView.findViewById(R.id.mTextHeader);
            holder.mSectionLayout = (RelativeLayout) convertView.findViewById(R.id.mSectionLayout);
            convertView.setTag(holder);
             /*For adding header*/
            assignSeparatorPositions(mListItems);

            final Agent agent = mListItems.get(position);
            if (separatorAtIndex[position]) {
                holder.mTextHeader.setText("" + agent.getmAgentName().charAt(0));
                holder.mHeaderLayout.setVisibility(View.VISIBLE);
            } else {
                holder.mHeaderLayout.setVisibility(View.GONE);
            }

            Glide.with(context).load(agent.getmAgentphoto()).asBitmap().centerCrop().placeholder(R.drawable.placeholder_user_profile).into(new BitmapImageViewTarget(holder.mImgChatUser) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.mImgChatUser.setImageDrawable(circularBitmapDrawable);
                }
            });

            holder.mTextUserName.setText(agent.getmAgentName());

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    /*For adding header*/
    private void assignSeparatorPositions(ArrayList<Agent> items) {
        separatorAtIndex = new boolean[items.size()];
        char currentChar = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getmAgentName().charAt(0) != currentChar) {
                separatorAtIndex[i] = true;
            } else {
                separatorAtIndex[i] = false;
            }
            currentChar = items.get(i).getmAgentName().charAt(0);
        }
    }

    private class ViewHolder {
        ImageView mImgChatUser;
        TextViewSemiBold mTextUserName, mTextHeader;
        LinearLayout mHeaderLayout;
        RelativeLayout mSectionLayout;

    }
}


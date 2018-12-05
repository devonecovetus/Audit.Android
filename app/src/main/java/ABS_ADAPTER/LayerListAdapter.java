package ABS_ADAPTER;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.covetus.absaudit.ActivitySelectCountryStandard;
import com.covetus.absaudit.LocationSubFolder;
import com.covetus.absaudit.R;
import com.covetus.absaudit.SelectMainLocationActivity;

import java.util.ArrayList;

import ABS_CUSTOM_VIEW.EditTextSemiBold;
import ABS_CUSTOM_VIEW.TextViewBold;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.Audit;
import ABS_GET_SET.LayerList;
import ABS_HELPER.DatabaseHelper;

/**
 * Created by admin1 on 4/12/18.
 */

public class LayerListAdapter extends BaseAdapter {

    private ArrayList<LayerList> mListItems = new ArrayList<>();
    Activity context;


    public LayerListAdapter(Activity context, ArrayList<LayerList> mListItems) {
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
            convertView = mInflater.inflate(R.layout.item_layer_list, null);
            holder = new ViewHolder();
            holder.mTxtTitle = (TextViewSemiBold) convertView.findViewById(R.id.mTxtTitle);
            holder.mTxtDecs = (TextViewSemiBold) convertView.findViewById(R.id.mTxtDecs);
            holder.mImgUpdateData = (ImageView) convertView.findViewById(R.id.mImgUpdateData);
            final LayerList layerList = mListItems.get(position);
            holder.mTxtTitle.setText(layerList.getmStrLayerTitle());
            holder.mTxtDecs.setText(layerList.getmStrLayerDesc());

            holder.mImgUpdateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_update_layer_title_desc);
                    final EditTextSemiBold mEditLayerTitle = (EditTextSemiBold)dialog.findViewById(R.id.mEditLayerTitle);
                    final EditTextSemiBold mEditLayerDescription = (EditTextSemiBold)dialog.findViewById(R.id.mEditLayerDescription);
                    TextViewBold mTxtUpdateButton = (TextViewBold)dialog.findViewById(R.id.mTxtUpdateButton);
                    mEditLayerTitle.setText(holder.mTxtTitle.getText());
                    mEditLayerDescription.setText(holder.mTxtDecs.getText());
                    mTxtUpdateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        String mTitle =  mEditLayerTitle.getText().toString();
                        String mDescription =  mEditLayerDescription.getText().toString();
                        holder.mTxtTitle.setText(mTitle);
                        holder.mTxtDecs.setText(mDescription);
                        LayerList layerUpdateList = new LayerList();
                        layerUpdateList.setmStrLayerTitle(mTitle);
                        layerUpdateList.setmStrLayerDesc(mDescription);
                        layerUpdateList.setmStrId(layerList.getmStrId());
                        databaseHelper.update_tb_sub_folder_explation_list(layerUpdateList);
                        dialog.dismiss();
                        }
                    });

                    dialog.show();


                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }


    private class ViewHolder {
        TextViewSemiBold mTxtTitle;
        TextViewSemiBold mTxtDecs;
        ImageView mImgUpdateData;
    }


}

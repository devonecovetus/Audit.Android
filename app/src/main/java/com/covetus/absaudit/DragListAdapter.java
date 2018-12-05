package com.covetus.absaudit;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ABS_CUSTOM_VIEW.TextViewBold;
import ABS_CUSTOM_VIEW.TextViewRegular;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_HELPER.DatabaseHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

class DragListAdapter extends RecyclerView.Adapter<DragListAdapter.ListViewHolder>
        implements View.OnTouchListener, View.OnLongClickListener {

    private List<String> list;
    private Listener listener;
    String mStatus;
    String mAudit;
    Activity context;

    DragListAdapter(Activity context, List<String> list, Listener listener, String mStr, String mAudit) {
        this.list = list;
        this.listener = listener;
        this.mStatus = mStr;
        this.context = context;
        this.mAudit = mAudit;

    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_location_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        holder.text.setText(list.get(position));
        if (SelectMainLocationActivity.meMap.get(list.get(position)).equals("0")) {
            holder.count.setVisibility(View.GONE);
            holder.count.setText(SelectMainLocationActivity.meMap.get(list.get(position)));
        } else {
            holder.count.setVisibility(View.VISIBLE);
            holder.count.setText(SelectMainLocationActivity.meMap.get(list.get(position)));
        }

        holder.mTextPlus.setText("+");
        holder.mTextMin.setText("-");
        holder.frameLayout.setTag(position);

        if (mStatus.equals("1")) {
            holder.mCountButtonLayout.setVisibility(View.VISIBLE);
            holder.frameLayout.setEnabled(false);
        } else {
            //holder.count.setText("0");
            holder.mCountButtonLayout.setVisibility(View.GONE);
            holder.frameLayout.setEnabled(true);
        }

        if (SelectMainLocationActivity.mStrDelete.equals("1") && mStatus.equals("1")) {
            holder.mImgRemove.setVisibility(View.VISIBLE);
        } else {
            holder.mImgRemove.setVisibility(View.GONE);
        }
// db.update_tb_list_audit(mAuditId,"1");
        holder.mImgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                if (!db.isExistNotification(SelectMainLocationActivity.meMapLocalId.get(list.get(position)))) {
                    SelectMainLocationActivity.getRemove(position);
                }else {
                    final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setContentView(R.layout.dialog_confirmation_delete_data_exp);
                    TextViewRegular mTxtMsg = (TextViewRegular)dialog.findViewById(R.id.mTxtMsg);
                    RelativeLayout mConfirm = (RelativeLayout)dialog.findViewById(R.id.mConfirm);
                    RelativeLayout mCancel = (RelativeLayout)dialog.findViewById(R.id.mCancel);
                    mTxtMsg.setText("This will delete "+list.get(position)+" Folder and Sub folders including associated questions,Do you want to proceed ?");

                    mCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    mConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseHelper dbase = new DatabaseHelper(context);
                            dbase.delete_tb_selected_main_location(SelectMainLocationActivity.meMapLocalId.get(list.get(position)));
                            dbase.delete_tb_location_sub_folder(SelectMainLocationActivity.meMapLocalId.get(list.get(position)));
                            dbase.delete_tb_sub_folder_explation_list_all(SelectMainLocationActivity.meMapLocalId.get(list.get(position)));
                            SelectMainLocationActivity.getRemove(position);
                            if(SelectMainLocationActivity.getCount()==0){
                            dbase.update_tb_list_audit(mAudit,"0");
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });

        holder.mLayoutAddCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.count.getText().length() <= 0) {
                    holder.count.setVisibility(View.VISIBLE);
                    holder.count.setText("0");
                    int a = Integer.parseInt(holder.count.getText().toString());
                    holder.count.setText(a + 1 + "");
                    SelectMainLocationActivity.meMap.put(list.get(position), a + 1 + "");
                } else {
                    holder.count.setVisibility(View.VISIBLE);
                    int a = Integer.parseInt(holder.count.getText().toString());
                    holder.count.setText(a + 1 + "");
                    SelectMainLocationActivity.meMap.put(list.get(position), a + 1 + "");
                }

            }
        });

        holder.mLayoutMinCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(context);
                if (!db.isExistNotification(SelectMainLocationActivity.meMapLocalId.get(list.get(position)))) {
                    int a = Integer.parseInt(holder.count.getText().toString());
                    if (a != 0) {
                        holder.count.setText(a - 1 + "");
                        SelectMainLocationActivity.meMap.put(list.get(position), a - 1 + "");
                    } else {
                        holder.count.setVisibility(View.GONE);
                    }


                } else {
                    int existing = db.get_existing_location_count(SelectMainLocationActivity.meMapLocalId.get(list.get(position)));
                    final int a = Integer.parseInt(holder.count.getText().toString());
                    if (a == existing) {
                        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.setContentView(R.layout.dialog_confirmation_delete_data_exp);
                        TextViewRegular mTxtMsg = (TextViewRegular)dialog.findViewById(R.id.mTxtMsg);
                        RelativeLayout mConfirm = (RelativeLayout)dialog.findViewById(R.id.mConfirm);
                        RelativeLayout mCancel = (RelativeLayout)dialog.findViewById(R.id.mCancel);
                        mTxtMsg.setText("This will delete "+list.get(position)+" Folder and Sub folders including associated questions,Do you want to proceed ?");

                        mCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        mConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseHelper dbase = new DatabaseHelper(context);
                                dbase.delete_tb_selected_main_location(SelectMainLocationActivity.meMapLocalId.get(list.get(position)));
                                dbase.delete_tb_location_sub_folder(SelectMainLocationActivity.meMapLocalId.get(list.get(position)));
                                dbase.delete_tb_sub_folder_explation_list_all(SelectMainLocationActivity.meMapLocalId.get(list.get(position)));
                                if (a != 0) {
                                    holder.count.setText(a - 1 + "");
                                    SelectMainLocationActivity.meMap.put(list.get(position), a - 1 + "");
                                } else {
                                    holder.count.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
                            }
                        });
                        dialog.show();



                        //alert and delete from selected location and sub folder
                    } else {
                        if (a != 0) {
                            holder.count.setText(a - 1 + "");
                            SelectMainLocationActivity.meMap.put(list.get(position), a - 1 + "");
                        } else {
                            holder.count.setVisibility(View.GONE);
                        }

                    }

                }


            }
        });

        holder.frameLayout.setOnLongClickListener(this);
        holder.frameLayout.setOnDragListener(new DragListener(listener));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                } else {
                    v.startDrag(data, shadowBuilder, v, 0);
                }
                return true;
        }
        return false;
    }

    List<String> getList() {
        return list;
    }

    void updateList(List<String> list) {
        this.list = list;
    }

    DragListener getDragInstance() {
        if (listener != null) {
            return new DragListener(listener);
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!");
            return null;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(data, shadowBuilder, view, 0);
        } else {
            view.startDrag(data, shadowBuilder, view, 0);
        }

        return true;
    }


    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.frame_layout_item)
        RelativeLayout frameLayout;
        @BindView(R.id.mLayoutAddCount)
        RelativeLayout mLayoutAddCount;
        @BindView(R.id.mLayoutMinCount)
        RelativeLayout mLayoutMinCount;
        @BindView(R.id.mTextPlus)
        TextViewBold mTextPlus;
        @BindView(R.id.mTextMin)
        TextViewBold mTextMin;
        @BindView(R.id.mCountButtonLayout)
        LinearLayout mCountButtonLayout;
        @BindView(R.id.mImgRemove)
        ImageView mImgRemove;


        ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

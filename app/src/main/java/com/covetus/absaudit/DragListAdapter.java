package com.covetus.absaudit;

import android.content.ClipData;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ABS_CUSTOM_VIEW.TextViewBold;
import butterknife.BindView;
import butterknife.ButterKnife;

class DragListAdapter extends RecyclerView.Adapter<DragListAdapter.ListViewHolder>
        implements View.OnTouchListener, View.OnLongClickListener {

    private List<String> list;
    private Listener listener;
    String mStatus;

    DragListAdapter(List<String> list, Listener listener, String mStr) {
        this.list = list;
        this.listener = listener;
        this.mStatus = mStr;
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
        holder.count.setText("0");
        holder.mTextPlus.setText("+");
        holder.mTextMin.setText("-");
        holder.frameLayout.setTag(position);
        if(mStatus.equals("1")){
        holder.mCountButtonLayout.setVisibility(View.VISIBLE);
        holder.frameLayout.setEnabled(false);
        }else {
        holder.mCountButtonLayout.setVisibility(View.GONE);
        holder.frameLayout.setEnabled(true);
        }

        holder.mLayoutAddCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(holder.count.getText().toString());
                holder.count.setText(a+1+"");
            }
        });

        holder.mLayoutMinCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(holder.count.getText().toString());
                holder.count.setText(a-1+"");

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

        ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

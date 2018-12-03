package com.covetus.absaudit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ABS_CUSTOM_VIEW.EditTextSemiBold;
import ABS_CUSTOM_VIEW.TextViewBold;
import ABS_CUSTOM_VIEW.TextViewRegular;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.AuditMainLocation;
import ABS_GET_SET.MainLocationSubFolder;
import ABS_GET_SET.SelectedLocation;
import ABS_HELPER.DatabaseHelper;
import ABS_HELPER.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationSubFolder extends Activity {

    @BindView(R.id.mLayoutAddLocation)
    LinearLayout mLayoutAddLocation;
    @BindView(R.id.mImageBack)
    ImageView mImageBack;


    @OnClick(R.id.mImageBack)
    public void mImageBack() {


    }

    DatabaseHelper db;
    ArrayList<SelectedLocation> mAuditList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_location_divide_sub_folder);
        ButterKnife.bind(this);
        db = new DatabaseHelper(LocationSubFolder.this);
        mAuditList = db.get_all_tb_selected_main_location();


        for (int i = 0; i < mAuditList.size(); i++) {
            final SelectedLocation selectedLocation = mAuditList.get(i);
            LayoutInflater mInflater = LayoutInflater.from(LocationSubFolder.this);
            View convertView = mInflater.inflate(R.layout.item_selected_location, null);
            ImageView mImgAddSubFolder = (ImageView) convertView.findViewById(R.id.mImgAddSubFolder);
            final TextViewSemiBold mTxtMainLocationCount = (TextViewSemiBold) convertView.findViewById(R.id.mTxtMainLocationCount);
            final TextViewSemiBold mTxtMainLocation = (TextViewSemiBold) convertView.findViewById(R.id.mTxtMainLocation);
            mTxtMainLocationCount.setText(selectedLocation.getmStrMainLocationCount());
            mTxtMainLocation.setText(selectedLocation.getmStrMainLocationTitle());
            final LinearLayout mLayoutForSubFolder = (LinearLayout) convertView.findViewById(R.id.mLayoutForSubFolder);
            mImgAddSubFolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = 0;
                    for (int index = 0; index < ((ViewGroup) mLayoutForSubFolder).getChildCount(); ++index) {
                        View nextChild = ((ViewGroup) mLayoutForSubFolder).getChildAt(index);
                        TextViewSemiBold mTxtCount = (TextViewSemiBold) nextChild.findViewById(R.id.mTxtCount);
                        a = a + Integer.parseInt(mTxtCount.getText().toString());
                    }
                    int newCount = Integer.parseInt(mTxtMainLocationCount.getText().toString()) - a;
                    if (newCount > 0) {
                        mAddUpdateSubFolder(mLayoutForSubFolder, mTxtMainLocationCount, newCount,selectedLocation.getmStrId());
                    }
                }
            });
            ArrayList<MainLocationSubFolder> mAuditList = db.get_all_tb_location_sub_folder(selectedLocation.getmStrId());
            if(mAuditList.size()>0){
            for(int j = 0;j<mAuditList.size();j++){
            mLayoutForSubFolder.setVisibility(View.VISIBLE);
            LayoutInflater msubInflater = LayoutInflater.from(LocationSubFolder.this);
            View subConvertView = msubInflater.inflate(R.layout.item_selected_sub_folder, null);
            final TextViewSemiBold mTxtSubGroupFolder = (TextViewSemiBold) subConvertView.findViewById(R.id.mTxtSubGroupFolder);
            final TextViewSemiBold mTxtCount = (TextViewSemiBold) subConvertView.findViewById(R.id.mTxtCount);
            final TextViewSemiBold mTxtFolderId = (TextViewSemiBold) subConvertView.findViewById(R.id.mTxtFolderId);
            ImageView mImgUpdateSubGroup = (ImageView) subConvertView.findViewById(R.id.mImgUpdateSubGroup);
            mTxtSubGroupFolder.setText(mAuditList.get(j).getmStrSubFolderName());
            mTxtCount.setText(mAuditList.get(j).getmStrSubFolderCont());
            mTxtFolderId.setText(mAuditList.get(j).getmStrId());
                mImgUpdateSubGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //update
                        int a = 0;
                        for (int index = 0; index < ((ViewGroup) mLayoutForSubFolder).getChildCount(); ++index) {
                            View nextChild = ((ViewGroup) mLayoutForSubFolder).getChildAt(index);
                            TextViewSemiBold mTxtCount = (TextViewSemiBold) nextChild.findViewById(R.id.mTxtCount);
                            a = a + Integer.parseInt(mTxtCount.getText().toString());
                        }
                        int newCount = Integer.parseInt(mTxtMainLocationCount.getText().toString()) - a;
                        updateDialog(mTxtSubGroupFolder, mTxtCount, Integer.parseInt(mTxtCount.getText().toString()) + newCount,mTxtFolderId.getText().toString());
                    }
                });
                mLayoutForSubFolder.addView(subConvertView);
            }
            }
            ///////////////////////////////////////////////////////////////




            mLayoutAddLocation.addView(convertView);
        }

    }


    public void mAddUpdateSubFolder(final LinearLayout linearLayout, final TextViewSemiBold mTxtMainLocationCount, final int Count,final String mLocationId) {
        final Dialog dialog = new Dialog(LocationSubFolder.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_update_sub_folder);
        final EditTextSemiBold mDiaEditFolderName = (EditTextSemiBold) dialog.findViewById(R.id.mEditFolderName);
        final TextViewSemiBold mDiaTxtCount = (TextViewSemiBold) dialog.findViewById(R.id.mTxtCount);
        ImageView mImgCountDown = (ImageView) dialog.findViewById(R.id.mImgCountDown);
        ImageView mImgCountUp = (ImageView) dialog.findViewById(R.id.mImgCountUp);
        TextViewBold mTxtAddButton = (TextViewBold) dialog.findViewById(R.id.mTxtAddButton);
        mDiaTxtCount.setText("0");
        mImgCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(mDiaTxtCount.getText().toString());
                if (a > 0) {
                    mDiaTxtCount.setText(a - 1 + "");
                } else {

                }

            }
        });
        mImgCountUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(mDiaTxtCount.getText().toString());
                if (a < Count) {
                    mDiaTxtCount.setText(a + 1 + "");
                }
            }
        });

        mTxtAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDiaTxtCount.getText().equals("0") || mDiaEditFolderName.getText().toString().length()<=0) {

                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    LayoutInflater mInflater = LayoutInflater.from(LocationSubFolder.this);
                    View convertView = mInflater.inflate(R.layout.item_selected_sub_folder, null);
                    final TextViewSemiBold mTxtSubGroupFolder = (TextViewSemiBold) convertView.findViewById(R.id.mTxtSubGroupFolder);
                    final TextViewSemiBold mTxtCount = (TextViewSemiBold) convertView.findViewById(R.id.mTxtCount);
                    final TextViewSemiBold mTxtFolderId = (TextViewSemiBold) convertView.findViewById(R.id.mTxtFolderId);
                    ImageView mImgUpdateSubGroup = (ImageView) convertView.findViewById(R.id.mImgUpdateSubGroup);
                    mTxtSubGroupFolder.setText(mDiaEditFolderName.getText());
                    mTxtCount.setText(mDiaTxtCount.getText());
                    mImgUpdateSubGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //update
                            int a = 0;
                            for (int index = 0; index < ((ViewGroup) linearLayout).getChildCount(); ++index) {
                                View nextChild = ((ViewGroup) linearLayout).getChildAt(index);
                                TextViewSemiBold mTxtCount = (TextViewSemiBold) nextChild.findViewById(R.id.mTxtCount);
                                a = a + Integer.parseInt(mTxtCount.getText().toString());
                            }
                            int newCount = Integer.parseInt(mTxtMainLocationCount.getText().toString()) - a;
                            updateDialog(mTxtSubGroupFolder, mTxtCount, Integer.parseInt(mTxtCount.getText().toString()) + newCount,mTxtFolderId.getText().toString());
                        }
                    });
                    //insert
                    MainLocationSubFolder mainLocationSubFolder = new MainLocationSubFolder();
                    mainLocationSubFolder.setmStrAuditId("73");
                    mainLocationSubFolder.setmStrUserId("44");
                    mainLocationSubFolder.setmStrMainLocationId(mLocationId);
                    mainLocationSubFolder.setmStrSubFolderName(mDiaEditFolderName.getText().toString());
                    mainLocationSubFolder.setmStrSubFolderCont(mDiaTxtCount.getText().toString());
                    int Id = db.insert_tb_location_sub_folder(mainLocationSubFolder);
                    mTxtFolderId.setText(Id+"");
                    linearLayout.addView(convertView);
                    dialog.dismiss();
                }


            }
        });
        dialog.show();
    }

    void updateDialog(final TextViewSemiBold mDiaTxtSubGroupFolder, final TextViewSemiBold mDiaTxtCount, final int Count,final String mIdForUpdate) {
        final Dialog dialog = new Dialog(LocationSubFolder.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_update_sub_folder);
        final EditTextSemiBold mEditFolderName = (EditTextSemiBold) dialog.findViewById(R.id.mEditFolderName);
        final TextViewSemiBold mTxtCount = (TextViewSemiBold) dialog.findViewById(R.id.mTxtCount);
        ImageView mImgCountDown = (ImageView) dialog.findViewById(R.id.mImgCountDown);
        ImageView mImgCountUp = (ImageView) dialog.findViewById(R.id.mImgCountUp);
        TextViewBold mTxtAddButton = (TextViewBold) dialog.findViewById(R.id.mTxtAddButton);
        mTxtCount.setText(mDiaTxtCount.getText());
        mEditFolderName.setText(mDiaTxtSubGroupFolder.getText());

        mImgCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(mTxtCount.getText().toString());
                if (a > 0) {
                    mTxtCount.setText(a - 1 + "");
                }

            }
        });

        mImgCountUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(mTxtCount.getText().toString());
                if (a < Count) {
                    mTxtCount.setText(a + 1 + "");
                }
            }
        });


        mTxtAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTxtCount.getText().equals("0") || mEditFolderName.getText().toString().length()<=0) {
                } else {
                    mDiaTxtSubGroupFolder.setText(mEditFolderName.getText());
                    mDiaTxtCount.setText(mTxtCount.getText());
                    //update
                    MainLocationSubFolder mainLocationSubFolder = new MainLocationSubFolder();
                    mainLocationSubFolder.setmStrId(mIdForUpdate);
                    mainLocationSubFolder.setmStrSubFolderCont(mTxtCount.getText().toString());
                    mainLocationSubFolder.setmStrSubFolderName(mEditFolderName.getText().toString());
                    db.update_tb_location_sub_folder(mainLocationSubFolder);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }


}

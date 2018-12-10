package com.covetus.absaudit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static ABS_HELPER.CommonUtils.checkInternetConnection;
import static ABS_HELPER.CommonUtils.hidePDialog;
import static ABS_HELPER.CommonUtils.mChatImgUrl;
import static ABS_HELPER.CommonUtils.mShowAlert;
import static ABS_HELPER.CommonUtils.mStrChatFileDownloadPath;
import static ABS_HELPER.CommonUtils.show;
import static ABS_HELPER.UploadHelper.getFileExt;

/**
 * Created by admin18 on 5/11/18.
 */

public class ActivityImageVideoView extends Activity {
    @BindView(R.id.imgPreview)
    ImageView imgPreview;
    @BindView(R.id.videoPreview)
    VideoView vidPreview;
    @BindView(R.id.mDocWebview)
    WebView mDocWebview;
    private String filePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        ButterKnife.bind(this);
        Intent i = getIntent();
        filePath = i.getStringExtra("filePath");
        System.out.println("<><><>filePath" + filePath);
        String isImage = i.getStringExtra("isImage");

        if (filePath != null) {
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(), R.string.mtextFile_no_filepath, Toast.LENGTH_LONG).show();
        }
    }


    private void previewMedia(String isImage) {

        try {
            if (isImage.contains("1")) {
                imgPreview.setVisibility(View.VISIBLE);
                vidPreview.setVisibility(View.GONE);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                if (filePath.contains(".png") || filePath.contains(".jpg") || filePath.contains(".jpeg")) {
                    Glide.with(getApplicationContext()).load(mChatImgUrl + filePath).into(imgPreview);
                } else {
                    byte[] imageByteArray = Base64.decode(filePath, Base64.DEFAULT);
                    Glide.with(getApplicationContext()).load(imageByteArray).asBitmap().into(imgPreview);
                }

            } else if (isImage.contains("2")) {
                if (filePath.contains(".mp4")) {
                    imgPreview.setVisibility(View.GONE);
                    vidPreview.setVisibility(View.VISIBLE);
                    show(ActivityImageVideoView.this);
                    vidPreview.setVideoPath(mChatImgUrl + filePath);
                    vidPreview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer arg0) {
                            hidePDialog();
                            vidPreview.start();
                        }
                    });
                } else {
                    imgPreview.setVisibility(View.GONE);
                    vidPreview.setVisibility(View.VISIBLE);
                    show(ActivityImageVideoView.this);
                    vidPreview.setVideoPath(filePath);
                    vidPreview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer arg0) {
                            hidePDialog();
                            vidPreview.start();
                        }
                    });
                }
            } else if (isImage.contains("3")) {
                if (filePath.contains(".pdf") || filePath.contains(".doc") || filePath.contains(".docx") || filePath.contains(".xlsx") || filePath.contains(".xls")) {
                    String mDoc = mChatImgUrl + filePath;
                    setmDocWebview(mDoc);
                }
            } else if (isImage.contains("4")) {
                System.out.println("<><><>filePath...." + getFileExt(filePath));
                if (getFileExt(filePath).contains("pdf") || getFileExt(filePath).contains("doc") || getFileExt(filePath).contains("docx") || getFileExt(filePath).contains("xlsx") || getFileExt(filePath).contains("xls")) {
                    openLocalDoc(filePath);
                }
            } else if (isImage.contains("5")) {
                File logfile = new File(mStrChatFileDownloadPath + getString(R.string.mTextFile_mediaFolder) + filePath);
                if (logfile.exists()) {
                    imgPreview.setVisibility(View.VISIBLE);
                    vidPreview.setVisibility(View.GONE);
                    Glide.with(this).load(logfile).into(imgPreview);
                } else {
                    mShowAlert(getString(R.string.mTextFile_file_not_download), ActivityImageVideoView.this);
                }


            } else if (isImage.contains("6")) {
                File logfile = new File(mStrChatFileDownloadPath + getString(R.string.mTextFile_mediaFolder) + filePath);
                if (logfile.exists()) {
                    Uri uri = Uri.parse("file://" + logfile.getAbsolutePath());
                    System.out.println("<><>uri" + uri.toString());
                    imgPreview.setVisibility(View.GONE);
                    vidPreview.setVisibility(View.VISIBLE);
                    show(ActivityImageVideoView.this);
                    vidPreview.setVideoURI(uri);
                    vidPreview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer arg0) {
                     /*   hidePDialog();*/
                            vidPreview.start();
                        }
                    });
                } else {
                    mShowAlert(getString(R.string.mTextFile_file_not_download), ActivityImageVideoView.this);
                }

            } else if (isImage.contains("7")) {
                File logfile = new File(mStrChatFileDownloadPath + getString(R.string.mTextFile_filefolder) + filePath);
                if (logfile.exists()) {
                    if (filePath.contains(".pdf")) {
                        Intent intentPDF = new Intent(Intent.ACTION_VIEW);
                        intentPDF.setDataAndType(Uri.fromFile(logfile), "application/pdf");
                        startActivity(intentPDF);
                    } else if (filePath.contains(".docx") || filePath.contains(".doc")) {
                        Intent intentTxt = new Intent(Intent.ACTION_VIEW);
                        intentTxt.setDataAndType(Uri.fromFile(logfile), "text/plain");
                        startActivity(intentTxt);
                    } else if (filePath.contains(".xls") || filePath.contains(".xlsx")) {
                        Intent intentExcel = new Intent(Intent.ACTION_VIEW);
                        intentExcel.setDataAndType(Uri.fromFile(logfile), "application/vnd.ms-excel");
                        startActivity(intentExcel);
                    }
                } else {
                    mShowAlert(getString(R.string.mTextFile_file_not_download), ActivityImageVideoView.this);
                }

            }
        } catch (Exception e) {
            Toast.makeText(ActivityImageVideoView.this, R.string.mTextFile_no_handler, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    protected void openFile(String mType) {
        hidePDialog();
        try {
            System.out.println("<><>mType" + mType);
            if (mType.contains(".pdf")) {
                File logfile = new File(mStrChatFileDownloadPath + getString(R.string.mTextFile_filefolder) + filePath);
                Intent intentPDF = new Intent(Intent.ACTION_VIEW);
                intentPDF.setDataAndType(Uri.fromFile(logfile), "application/pdf");
                startActivity(intentPDF);
            } else if (mType.contains(".docx") || mType.contains(".doc")) {
                File logfile = new File(mStrChatFileDownloadPath + getString(R.string.mTextFile_filefolder) + filePath);
                Intent intentTxt = new Intent(Intent.ACTION_VIEW);
                intentTxt.setDataAndType(Uri.fromFile(logfile), "text/plain");
                startActivity(intentTxt);
            } else if (mType.contains(".xls") || mType.contains(".xlsx")) {
                File logfile = new File(mStrChatFileDownloadPath + getString(R.string.mTextFile_filefolder) + filePath);
                Intent intentExcel = new Intent(Intent.ACTION_VIEW);
                intentExcel.setDataAndType(Uri.fromFile(logfile), "application/vnd.ms-excel");
                startActivity(intentExcel);
            } else if (mType.contains(".mp4")) {
                File logfile = new File(mStrChatFileDownloadPath + getString(R.string.mTextFile_mediaFolder) + filePath);
                Uri uri = Uri.parse("file://" + logfile.getAbsolutePath());
                System.out.println("<><>uri" + uri.toString());
                imgPreview.setVisibility(View.GONE);
                vidPreview.setVisibility(View.VISIBLE);
                show(ActivityImageVideoView.this);
                vidPreview.setVideoURI(uri);
                vidPreview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    public void onPrepared(MediaPlayer arg0) {
                     /*   hidePDialog();*/
                        vidPreview.start();
                    }
                });
            } else if (mType.contains(".png") || mType.contains(".jpg") || mType.contains(".jpeg")) {
                File logfile = new File(mStrChatFileDownloadPath + getString(R.string.mTextFile_mediaFolder) + filePath);
                imgPreview.setVisibility(View.VISIBLE);
                vidPreview.setVisibility(View.GONE);
                Glide.with(this).load(logfile).into(imgPreview);
            }

        } catch (Exception e) {
            Toast.makeText(ActivityImageVideoView.this, R.string.mTextFile_no_handler, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void setmDocWebview(String mDoc) {
        mDocWebview.setVisibility(View.VISIBLE);
        imgPreview.setVisibility(View.GONE);
        vidPreview.setVisibility(View.GONE);
        final ProgressDialog pDialog = new ProgressDialog(ActivityImageVideoView.this);
        pDialog.setTitle(getString(R.string.app_name));
        pDialog.setIcon(R.drawable.ic_access4mii_logo);
        pDialog.setMessage(getString(R.string.mTextFile_loading));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        mDocWebview.getSettings().setJavaScriptEnabled(true);
        mDocWebview.getSettings().setSupportZoom(true);
        mDocWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                System.out.println("<><><>filurl" + url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });
        mDocWebview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + mDoc);
        /*mDocWebview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);*/
    }


    public void openLocalDoc(String mDoc) {
        try {
            System.out.println("<><><>filePath...." + getFileExt(filePath));
            if (getFileExt(filePath).contains("pdf")) {
                Intent intentPDF = new Intent(Intent.ACTION_VIEW);
                intentPDF.setDataAndType(Uri.parse(mDoc), "application/pdf");
                startActivity(intentPDF);
            } else if (getFileExt(filePath).contains("docx") || getFileExt(filePath).contains("doc")) {
                Intent intentTxt = new Intent(Intent.ACTION_VIEW);
                intentTxt.setDataAndType(Uri.parse(mDoc), "text/plain");
                startActivity(intentTxt);
            } else if (getFileExt(filePath).contains("xls") || getFileExt(filePath).contains("xlsx")) {
                Intent intentExcel = new Intent(Intent.ACTION_VIEW);
                intentExcel.setDataAndType(Uri.parse(mDoc), "application/vnd.ms-excel");
                startActivity(intentExcel);
            }
        } catch (Exception e) {
            mShowAlert(getString(R.string.mTextFile_cannot_open), ActivityImageVideoView.this);
            e.printStackTrace();
        }

    }
}
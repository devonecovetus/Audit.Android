package ABS_ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.covetus.absaudit.ActivityImageVideoView;
import com.covetus.absaudit.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.List;

import ABS_CUSTOM_VIEW.TextViewRegular;
import ABS_CUSTOM_VIEW.TextViewSemiBold;
import ABS_GET_SET.MessageChat;
import ABS_HELPER.PreferenceManager;

import static ABS_HELPER.CommonUtils.checkInternetConnection;
import static ABS_HELPER.CommonUtils.mChatImgUrl;
import static ABS_HELPER.CommonUtils.mStrChatFileDownloadPath;
import static ABS_HELPER.CommonUtils.mStrDownloadPath;
import static ABS_HELPER.UploadHelper.isValid;

/**
 * Created by admin18 on 29/11/18.
 */

public class MessageChatList extends RecyclerView.Adapter<MessageChatList.ViewHolder> {
    List<MessageChat> mMsgChatArrayList;
    Context mContext;


    public MessageChatList(Context context, List<MessageChat> mMsgChatList) {
        mMsgChatArrayList = mMsgChatList;
        mContext = context;
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mMsgChatArrayList.get(position).getType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
       /* Check view type*/
        switch (viewType) {
            case MessageChat.TYPE_MESSAGE_RECEIVE:
                layout = R.layout.item_received_msg;
                break;
            case MessageChat.TYPE_MESSAGE_SENT:
                layout = R.layout.item_sent_msg;
                break;
            case MessageChat.TYPE_MEDIA_RECEIVE_MESSAGE:
                layout = R.layout.item_received_msg;
                break;
            case MessageChat.TYPE_MEDIA_SENT_MESSAGE:
                layout = R.layout.item_sent_msg;
                break;
            case MessageChat.TYPE_VIDEO_RECEIVE_MESSAGE:
                layout = R.layout.item_received_msg;
                break;
            case MessageChat.TYPE_VIDEO_SENT_MESSAGE:
                layout = R.layout.item_sent_msg;
                break;
            case MessageChat.TYPE_DOCUMENT_RECEIVE_MESSAGE:
                layout = R.layout.item_received_msg;
                break;
            case MessageChat.TYPE_DOCUMENT_SENT_MESSAGE:
                layout = R.layout.item_sent_msg;
                break;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final MessageChat messageChat = mMsgChatArrayList.get(position);
        /*load user image*/
        Glide.with(mContext).load(messageChat.getmImage()).asBitmap().centerCrop().placeholder(R.drawable.placeholder_user_profile).into(new BitmapImageViewTarget(viewHolder.mImageUser) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                viewHolder.mImageUser.setImageDrawable(circularBitmapDrawable);
            }
        });

        //for hiding nd viewing progessbar when img or video is uploading
        if (PreferenceManager.getFormiiProgreessView(mContext).equals("0")) {
            viewHolder.progressBar.setVisibility(View.GONE);
        } else {
            viewHolder.progressBar.setVisibility(View.VISIBLE);
        }

        if (messageChat.getType() == 2 || messageChat.getType() == 1) {
            //image
            viewHolder.mImgBody.setVisibility(View.VISIBLE);
            viewHolder.mImgPlay.setVisibility(View.GONE);
            viewHolder.mTextMsgBody.setVisibility(View.GONE);
            viewHolder.mTextMsgTime.setText(messageChat.getmCreatedAt());
            if (messageChat.getMessage().contains(".png") || messageChat.getMessage().contains(".jpg") || messageChat.getMessage().contains(".jpeg")) {
                Glide.with(mContext).load(mChatImgUrl + messageChat.getMessage()).placeholder(R.mipmap.ic_pic_placeholder).into(viewHolder.mImgBody);
            } else {
                //load locally when send
                try {
                    byte[] imageByteArray = Base64.decode(messageChat.getMessage(), Base64.DEFAULT);
                    Glide.with(mContext).load(imageByteArray).placeholder(R.mipmap.ic_pic_placeholder).into(viewHolder.mImgBody);
                } catch (Exception e) {
                    e.toString();
                }
            }

            viewHolder.mImgBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkInternetConnection(mContext)) {
                        launchUploadActivity("1", messageChat.getMessage());
                        downloadFile(messageChat.getMessage(), mChatImgUrl + messageChat.getMessage(), "Media", messageChat.getType());
                    } else {
                        launchUploadActivity("5", messageChat.getMessage());
                    }
                }
            });

        } else if (messageChat.getType() == 4 || messageChat.getType() == 3) {
            //video
            viewHolder.mImgBody.setVisibility(View.VISIBLE);
            viewHolder.mTextMsgBody.setVisibility(View.GONE);
            viewHolder.mTextMsgTime.setText(messageChat.getmCreatedAt());
            String url = mChatImgUrl + messageChat.getMessage();
            if (isValid(url)) {
                viewHolder.mImgPlay.setVisibility(View.VISIBLE);
                if (messageChat.getMessage().contains(".mp4")) {
                    Glide.with(mContext).load(mChatImgUrl + messageChat.getMessage()).asBitmap().placeholder(R.mipmap.ic_pic_placeholder).into(viewHolder.mImgBody);
                } else {
                    //load locally when send
                    Glide.with(mContext).load(Uri.fromFile(new File(messageChat.getMessage()))).asBitmap().placeholder(R.mipmap.ic_pic_placeholder).into(viewHolder.mImgBody);
                }

                viewHolder.mImgPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkInternetConnection(mContext)) {
                            launchUploadActivity("2", messageChat.getMessage());
                            downloadFile(messageChat.getMessage(), mChatImgUrl + messageChat.getMessage(), "Media", messageChat.getType());
                        } else {
                            //load from locally
                            launchUploadActivity("6", messageChat.getMessage());
                        }
                    }
                });
            } else {
                viewHolder.mImgPlay.setVisibility(View.GONE);
                Glide.with(mContext).load(R.drawable.ic_novideo).into(viewHolder.mImgBody);
            }
        } else if (messageChat.getType() == 7 || messageChat.getType() == 6) {
            System.out.println("<><><>docMsg-" + messageChat.getMessage());
            //document
            viewHolder.mImgBody.setVisibility(View.VISIBLE);
            viewHolder.mTextMsgBody.setVisibility(View.GONE);
            viewHolder.mTextMsgTime.setText(messageChat.getmCreatedAt());
            String url = mChatImgUrl + messageChat.getMessage();
            if (isValid(url)) {
                viewHolder.mImgPlay.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.drawable.ic_forward).into(viewHolder.mImgPlay);
                Glide.with(mContext).load(R.mipmap.ic_doc_placeholder).into(viewHolder.mImgBody);
                viewHolder.mImgPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkInternetConnection(mContext)) {
                            launchUploadActivity("3", messageChat.getMessage());
                            downloadFile(messageChat.getMessage(), mChatImgUrl + messageChat.getMessage(), "File", messageChat.getType());
                        } else {
                            //load from locally
                            launchUploadActivity("7", messageChat.getMessage());
                        }
                    }
                });
            } else {
                viewHolder.mImgPlay.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.drawable.ic_forward).into(viewHolder.mImgPlay);
                Glide.with(mContext).load(R.mipmap.ic_doc_placeholder).into(viewHolder.mImgBody);
                viewHolder.mImgPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //load from locally
                        launchUploadActivity("4", messageChat.getMessage());
                    }
                });
            }
        } else {
            //text
            viewHolder.mImgBody.setVisibility(View.GONE);
            viewHolder.mImgPlay.setVisibility(View.GONE);
            viewHolder.mTextMsgBody.setVisibility(View.VISIBLE);
            viewHolder.mTextMsgBody.setText(messageChat.getMessage());
            viewHolder.mTextMsgTime.setText(messageChat.getmCreatedAt());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgChatArrayList.size();
    }

    private void launchUploadActivity(String isImage, String message) {
        try {
            Intent i = new Intent(mContext, ActivityImageVideoView.class);
            i.putExtra("filePath", message);
            i.putExtra("isImage", isImage);
            mContext.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*downloading file*/
    private void downloadFile(String mFileName, String urlString, String mFolder, int msgType) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            String PATH = mStrChatFileDownloadPath + mFolder;

            //for hidden folder
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, mFileName);
            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();
            byte[] buffer = new byte[4096];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();
            System.out.println("<><><outputFile" + outputFile.getPath());
            Toast.makeText(mContext, " A new file is downloaded successfully", Toast.LENGTH_LONG).show();
            if (msgType == 2 || msgType == 4 || msgType == 7) {
                //copying to new folder
                String mfilePath = mStrDownloadPath + mFolder;
                File root = new File(mfilePath);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File mNewFile = new File(root, mFileName);
                System.out.println("<><><file" + mNewFile.getPath());
                FileOutputStream out = new FileOutputStream(mNewFile);
                out.flush();
                out.close();
                copyFile(outputFile, mNewFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextViewSemiBold mTextMsgBody;
        ImageView mImgBody, mImgPlay, mImageUser;
        ProgressBar progressBar;
        TextViewRegular mTextMsgTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextMsgBody = (TextViewSemiBold) itemView.findViewById(R.id.mTextMsgBody);
            mTextMsgTime = (TextViewRegular) itemView.findViewById(R.id.mTextMsgTime);
            mImgBody = (ImageView) itemView.findViewById(R.id.mImgBody);
            mImgPlay = (ImageView) itemView.findViewById(R.id.mImgPlay);
            mImageUser = (ImageView) itemView.findViewById(R.id.mImageUser);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
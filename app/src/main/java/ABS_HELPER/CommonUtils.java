package ABS_HELPER;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.bumptech.glide.Glide;
import com.covetus.absaudit.ActivityAuditDetail;
import com.covetus.absaudit.ActivityLogin;
import com.covetus.absaudit.ActivityResetPassword;
import com.covetus.absaudit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ABS_CUSTOM_VIEW.TextViewSemiBold;

/**
 * Created by covetus on 9/10/18.
 */

public class CommonUtils {
    public static final String FOLDER = "ABS";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String SHARED_PREF = "audit_firebase";
    public static final String TOPIC_GLOBAL = "global";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static ProgressDialog pDialog;
    public static String mStrBaseUrl = "http://dev.covetus.com/audit/api/v1/";
    public static String mStrBaseUrlImage = "http://dev.covetus.com/audit/";
    public static String mStrDownloadPath = Environment.getExternalStorageDirectory().toString() + "/ABS/";
    public static String mStrChatFileDownloadPath = Environment.getExternalStorageDirectory().toString() + "/.ABSAudit/";
    public static String CHAT_SERVER_URL = "http://dev.covetus.com:8090/";
    public static String mChatImgUrl = "http://dev.covetus.com/audit/storage/app/public/profilePic/chatfile/";

    /*alert msg*/
    public static void mShowAlert(String str, Activity context) {
        TSnackbar snackbar = TSnackbar.make(context.findViewById(android.R.id.content), str, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(context.getResources().getColor(R.color.colorPrimary));
        View snackbarView = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        params.setMargins(0, 0, 0, 70);
        params.width = FrameLayout.LayoutParams.FILL_PARENT;
        snackbarView.setLayoutParams(params);
        snackbarView.setPadding(0, 0, 0, 0);
        snackbarView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextSize(18);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextColor(context.getResources().getColor(R.color.white));
        snackbar.show();
    }

    /*click animation*/
    public static void OnClick(Activity activity, View view) {
        Animation myAnim = AnimationUtils.loadAnimation(activity, R.anim.click);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);
    }

    /*hide loader*/
    public static void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    /*show loader*/
    public static void show(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pDialog.setContentView(R.layout.item_progress_box);
        ImageView imgProgress = (ImageView) pDialog.findViewById(R.id.imgProgress);
        Glide.with(context).load(R.drawable.ic_loader).asGif().into(imgProgress);
    }

    /*auth token expire*/
    public static void showSessionExp(final Activity context) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_session_exp);
        dialog.setCancelable(false);
        TextViewSemiBold mCancel = (TextViewSemiBold) dialog.findViewById(R.id.mCancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.cleanData(context);
                Intent intent = new Intent(context, ActivityLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                context.finish();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    /*keyboard close*/
    public static void closeKeyBoard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*permission check*/
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /* CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
    public static boolean checkInternetConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
        if (activeNetworkInfo != null) { // connected to the internet
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

    public static void getOpenEmail(Context mContext, String mStrEmail) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:" + mStrEmail));
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            mContext.startActivity(Intent.createChooser(intent, "Send Email"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getCallNumber(Context mContext, String mStrPhone) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mStrPhone));
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mContext.startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static String covertTimeToText(String date) {
        String strDate = null;
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(date));
            Calendar calendar = Calendar.getInstance();
            long now = calendar.getTimeInMillis();
            long time = cal.getTimeInMillis();
            long diff = now - time;
            int seconds = (int) (diff / 1000) % 60;
            int minutes = (int) ((diff / (1000 * 60)) % 60);
            int hours = (int) ((diff / (1000 * 60 * 60)) % 24);
            int days = (int) (diff / (1000 * 60 * 60 * 24));
            if (days > 0) {
                strDate = days + " days ago";
            } else if (hours > 0) {
                strDate = hours + " hours ago";
            } else if (minutes > 0) {
                strDate = minutes + " minutes ago";
            } else if (seconds > 0) {
                strDate = seconds + " seconds ago";
            }
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
        return strDate;
    }



    /* yyyy-MM-dd HH:mm:ss a*/
    public static String getTimeformat(String mDate) {
        String newTimeStr = null;
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = input.parse(mDate);
            newTimeStr = output.format(d);
            System.out.println("<><><>ddffTime: " + newTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newTimeStr;

    }

    public static String getFormattedDate( String mStrDate) {
        long smsTimeInMilis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(mStrDate);
            smsTimeInMilis = mDate.getTime();
            System.out.println("Date in milli :: " + smsTimeInMilis);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EE, MMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today, " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday, " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy, h:mm aa", smsTime).toString();
        }
    }


}

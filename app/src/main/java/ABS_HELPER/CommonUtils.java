package ABS_HELPER;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
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

import ABS_CUSTOM_VIEW.TextViewSemiBold;

/**
 * Created by covetus on 9/10/18.
 */

public class CommonUtils {
    public static ProgressDialog pDialog;
    public static String mStrBaseUrl = "http://dev.covetus.com/audit/api/v1/";
    public static String mStrBaseUrlImage = "http://dev.covetus.com/audit/";
    public static String mStrDownloadPath = Environment.getExternalStorageDirectory().toString() + "/Audit/";

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
        textView.setTextSize(25);
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
}

package com.canteenautomation.canteeen.sohel;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;
import com.canteenautomation.canteeen.ItemListActivity;
import com.canteenautomation.canteeen.R;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class S {
    static Boolean condition = false;
    static String notificationTitle = "";
    static String msg = "";
    static String quiz_id = "";
    static String quiz_time = "";
    static String quiz_topicId = "";
    static String category_name = "";
    static String subject_name = "";
    static String topic_name = "";
    static String point = "";
    static String user_name = "";
    static String userProfilePic = "";
    static String host_user_id = "";
    static String defender_user_id = "";
    static String tableId = "";
    static String player_key = "";
    static String message = "";
    static Handler handler;
    static Runnable myRunnable;
    static Dialog dialog;

    public static void I(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
    }

    public static void I_finish(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
    }

    public static void I_clear(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
    }

    public static void E(String msg) {
        if (true)
            Log.e("Log.E By Sohel", msg);
    }

    private static void askForPermission(Activity context, String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
            }
        } else {
            /*   Toast.makeText(context, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
             */
        }
    }


    /* public static void dialogForAvailableStatus(Context cx) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(cx);
        dialog.setCancelable(false);
        dialog.setTitle("Not Available");
        dialog.setMessage(R.string.city_available_status);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = dialog.create();
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }
*/
    public static Dialog initProgressDialog(Context c) {

        Dialog dialog = new Dialog(c);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static void T(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    public static Map<String, String> getParams() {
        HashMap<String, String> params = new HashMap<>();
   /*     params.put("user_id", UserDataHelper.getInstance().getList().get(0).getUserId());
        params.put("user_imei_number", UserDataHelper.getInstance().getList().get(0).getUserImeiNo());*/
        return params;
    }

    /*public static void Snack(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        snackbar.show();
    }*/

    public static void share(Context c, String subject, String shareBody) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        c.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void shareFile(Context c, File file, String subject, String shareBody) {
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        if (file.exists()) {
            intentShareFile.setType("application/image");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file));
            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, subject);
            intentShareFile.putExtra(Intent.EXTRA_TEXT, shareBody);
            c.startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }
    }



}
package com.manish.openweather.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.manish.openweather.application.OpenWeatherApplication;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.ContentValues.TAG;
import static com.manish.openweather.utility.Constant.LOADING;

/**
 * Created by manishp on 3/7/16.
 */

public class Utility {


    private static ProgressDialog sProgressDialog;
    public static boolean isConnectedToInternet;


    /**
     * Gets the string resource using the application context.
     */
    public static String getString(int id) {
        return OpenWeatherApplication.getInstance().getString(id);
    }

    public static void showProgressDialog() {
        if (null != sProgressDialog && sProgressDialog.isShowing()) {
            sProgressDialog.hide();
            sProgressDialog = null;
        }

        Context context = OpenWeatherApplication.getInstance().getLiveContext();
        if (null != context) {
            sProgressDialog = new ProgressDialog(context);

            sProgressDialog.setMessage(LOADING);
            sProgressDialog.setCancelable(false);

            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    sProgressDialog.show();
                }
            } else {
                sProgressDialog.show();
            }
        }

    }

    public static void hideProgressBar() {
        try {
            if (null != sProgressDialog && sProgressDialog.isShowing()) {

                Context context = sProgressDialog.getContext();

                if (context instanceof Activity) {

                    if (!((Activity) context).isFinishing()) {

                        sProgressDialog.dismiss();
                        sProgressDialog = null;
                    }
                } else {

                    sProgressDialog.dismiss();
                    sProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "Simple ignore the exception", e);
        }

    }

    public static void showWarningDialog(String title, String message) {
        Context context = OpenWeatherApplication.getInstance().getLiveContext();

        if (null != context) {
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(title)
                    .setContentText(message)
                    .setConfirmText(context.getString(android.R.string.ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();

                        }
                    })
                    .show();
        }
    }

    public static boolean isConnectedToInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);

                if (networkInfo != null && networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo networkInfo : info) {
                        if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network",
                                    "NETWORKNAME: " + networkInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        Log.v("network", "not connected to internet");
        return false;
    }
}

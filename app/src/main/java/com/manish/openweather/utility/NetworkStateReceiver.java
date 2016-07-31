package com.manish.openweather.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Utility.isConnectedToInternet = Utility.isConnectedToInternet(context);
    }
}

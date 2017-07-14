package io.uscool.fuelfriend.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;

/**
 * Created by ujjawal on 11/7/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, DownloadService.class);
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        i.putExtra("receiver", receiver);
        context.startService(i);
    }
}

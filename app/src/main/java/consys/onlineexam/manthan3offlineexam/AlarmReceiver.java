package consys.onlineexam.manthan3offlineexam;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int MY_NOTIFICATION_ID = 1;
    Intent in;
    Notification mBuilder;
    PendingIntent pendingIntent;

    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, InspirationalQuoteService.class));
    }
}

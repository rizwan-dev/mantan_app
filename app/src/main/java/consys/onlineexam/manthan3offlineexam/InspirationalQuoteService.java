package consys.onlineexam.manthan3offlineexam;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.manthanCoach;
import java.io.File;
import java.util.Calendar;

public class InspirationalQuoteService extends IntentService {
    public static final String TAG = "InspirationalQuoteAlarm";
    private static int notification_ID = 1;
    NotificationQuoteDetailRead Rfile = new NotificationQuoteDetailRead();
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    File readFil = new File(this.read_file_path);
    String read_file_path = AppConstant.jsonDailyQuotes;

    public InspirationalQuoteService() {
        super("InspirationalQuoteService");
        Log.i(TAG, "Created");
    }

    protected void onHandleIntent(Intent intent) {
        String dailyData1 = this.Rfile.readQuotedetail(this.readFil);
        if (dailyData1 != null && !dailyData1.trim().equals("")) {
            String[] quoteList = dailyData1.split("#-");
            if (quoteList != null) {
                Calendar nightTime = Calendar.getInstance();
                nightTime.set(11, 23);
                nightTime.set(12, 0);
                nightTime.set(13, 0);
                Log.i(TAG, "night   :" + nightTime.getTimeInMillis() + " Hours " + nightTime.getTime().getHours() + " Minutes:" + nightTime.getTime().getMinutes() + " Seconds:" + nightTime.getTime().getSeconds());
                Calendar morningTime = Calendar.getInstance();
                morningTime.set(11, 8);
                morningTime.set(12, 31);
                morningTime.set(13, 0);
                Log.i(TAG, "current :" + Calendar.getInstance().getTimeInMillis() + " Hours " + Calendar.getInstance().getTime().getHours() + " Minutes:" + Calendar.getInstance().getTime().getMinutes() + " Seconds:" + Calendar.getInstance().getTime().getSeconds());
                Log.i(TAG, "morning :" + morningTime.getTimeInMillis() + " Hours " + morningTime.getTime().getHours() + " Minutes:" + morningTime.getTime().getMinutes() + " Seconds:" + morningTime.getTime().getSeconds());
                if (nightTime.getTimeInMillis() > System.currentTimeMillis() && morningTime.getTimeInMillis() < System.currentTimeMillis()) {
                    Log.i(TAG, "Alarm Service has started.");
                    Context context = getApplicationContext();
                    this.notificationManager = (NotificationManager) context.getSystemService("notification");
                    this.pendingIntent = PendingIntent.getActivity(context, 0, new Intent(this, WelcomeActivity.class), 268435456);
                    Resources res = getResources();
                    Builder builder = new Builder(this);
                    manthanCoach app = (manthanCoach) getApplicationContext();
                    BigTextStyle style = new BigTextStyle();
                    style.setBigContentTitle("Get Set Go.. ");
                    style.bigText(quoteList[app.getNotificationCount() % quoteList.length]);
                    builder.setStyle(style);
                    builder.setContentIntent(this.pendingIntent).setSmallIcon(C0539R.drawable.notificcationlogo).setLargeIcon(BitmapFactory.decodeResource(res, C0539R.drawable.notificcationlogo)).setTicker("Success is waiting").setAutoCancel(true).setVibrate(new long[]{1000}).setContentTitle("Get Set Go.. ");
                    this.notificationManager = (NotificationManager) getSystemService("notification");
                    this.notificationManager.notify(notification_ID, builder.build());
                    Log.i(TAG, "Notifications sent.");
                    app.incrementCount();
                }
            }
        }
    }
}

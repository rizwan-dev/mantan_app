package consys.onlineexam.manthan3offlineexam;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import consys.onlineexam.helper.AppConstant;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.achartengine.chart.TimeChart;

public class CommonNotification extends Service implements TaskListener {
    static final String ACTION = "NotifyServiceAction";
    static final int RQS_STOP_SERVICE = 1;
    static final String STOP_SERVICE_BROADCAST_KEY = "StopServiceBroadcastKey";
    NotificationManager Manager;
    ServiceReceiver nServiceReceiver;

    class MyTimerTask extends TimerTask {
        MyTimerTask() {
        }

        public void run() {
            gettips(AppConstant.tip_date);
        }

        private void gettips(String date) {
            CommonNotification.this.Manager = (NotificationManager) CommonNotification.this.getSystemService("notification");
            CommonNotification.this.set("IBPS", C0539R.drawable.updates, AppConstant.notification, 91);
        }
    }

    public class ServiceReceiver extends BroadcastReceiver {
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getIntExtra(CommonNotification.STOP_SERVICE_BROADCAST_KEY, 0) == 1) {
                CommonNotification.this.stopSelf();
                ((NotificationManager) CommonNotification.this.getSystemService("notification")).cancelAll();
            }
        }
    }

    public void onCreate() {
        System.out.println("In service of tip notification");
        this.nServiceReceiver = new ServiceReceiver();
        super.onCreate();
    }

    @SuppressLint({"NewApi"})
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("In service of tip notification");
        new Timer().schedule(new MyTimerTask(), 100, TimeChart.DAY);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        unregisterReceiver(this.nServiceReceiver);
        super.onDestroy();
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void set(String t, int imageid, String str, int id) {
        Uri soundUri = RingtoneManager.getDefaultUri(2);
        Intent intent = null;
        if (t.equalsIgnoreCase("Tip of the day")) {
            intent = new Intent(this, TipsActivity.class);
        } else if (t.equalsIgnoreCase("JEE news")) {
            intent = new Intent(this, JEENewsActivity.class);
        } else if (t.equalsIgnoreCase("Updates")) {
            intent = new Intent(this, UpdateActivity.class);
        } else if (t.equalsIgnoreCase("IBPS")) {
            intent = new Intent(this, MainMenuActivity.class);
        } else if (t.equalsIgnoreCase("Live Test")) {
            intent = new Intent(this, SelectSubjectActivity.class);
            intent.putExtra("id", id - 50);
        }
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        System.out.println("in notifications");
        Notification mNotification = new Builder(this).setContentTitle(t).setContentText(str).setSmallIcon(imageid).setContentIntent(pIntent).setSound(soundUri).addAction(C0539R.drawable.check, "View", pIntent).addAction(id, "Remind", pIntent).build();
        System.out.println("in notifications");
        mNotification.flags |= 16;
        this.Manager.notify(id, mNotification);
        System.out.println("in notifications");
    }

    public void onTaskCompleted(HashMap hm) {
    }
}

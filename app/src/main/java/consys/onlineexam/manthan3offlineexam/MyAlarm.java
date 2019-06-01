package consys.onlineexam.manthan3offlineexam;

import android.app.Application;

public class MyAlarm extends Application {
    public int notificationCount;

    public void onCreate() {
        super.onCreate();
        this.notificationCount = 0;
    }

    public void incrementCount() {
        this.notificationCount++;
    }

    public int getNotificationCount() {
        return this.notificationCount;
    }
}

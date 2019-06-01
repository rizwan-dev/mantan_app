package consys.onlineexam.helper;

import android.app.Application;

public class manthanCoach extends Application {
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

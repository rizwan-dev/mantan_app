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
import consys.onlineexam.helper.AppHelper;
import consys.onlineexam.helper.ExamModel;
import consys.onlineexam.helper.NewsModel;
import consys.onlineexam.helper.TipModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.achartengine.chart.TimeChart;

public class TipNotifyService extends Service implements TaskListener {
    static final String ACTION = "NotifyServiceAction";
    static final int RQS_STOP_SERVICE = 1;
    static final String STOP_SERVICE_BROADCAST_KEY = "StopServiceBroadcastKey";
    NotificationManager notificationManager;
    TipServiceReceiver notifyServiceReceiver;

    class MyTimerTask extends TimerTask {
        MyTimerTask() {
        }

        public void run() {
            if (AppConstant.flag_n) {
                TipNotifyService.this.notificationManager = (NotificationManager) TipNotifyService.this.getSystemService("notification");
                TipNotifyService.this.set("JEE news", C0539R.drawable.news, AppConstant.notification, 10);
                return;
            }
            gettips(AppConstant.tip_date);
        }

        private void gettips(String date) {
            try {
                System.out.println("In getflags of tip notifications");
                HashMap hm1 = new HashMap();
                hm1.put("method", "checkflags");
                HashMap r = AppHelper.getValues(hm1, TipNotifyService.this);
                if (r != null) {
                    HashMap hm = new HashMap();
                    hm.put("method", "gettips");
                    hm.put("tip_id", r.get("tipid"));
                    hm.put("exam_id", r.get("updateid"));
                    hm.put("news_id", r.get("newsid"));
                    hm.put("newstype", "notify");
                    hm.put("livetest_id", r.get("livetestid"));
                    hm.put("version_id", r.get("versionid"));
                    hm.put("ads_id", r.get("adsid"));
                    hm.put("chapterid", Integer.valueOf(65));
                    hm.put("examtype", "Live_Exam");
                    hm.put("imei", CommonActivity.getimei(TipNotifyService.this));
                    HashMap res = AppHelper.getValues(hm, TipNotifyService.this);
                    System.out.println("Tipnotify");
                    boolean b = ((Boolean) res.get("flag1")).booleanValue();
                    if (b) {
                        System.out.println("Tipnotify" + b);
                        TipNotifyService.this.notificationManager = (NotificationManager) TipNotifyService.this.getSystemService("notification");
                        setnewsnotification(res);
                        settipnotification(res);
                        setupdatenotification(res);
                        setlivetestnotification(res);
                        setmodifier(res);
                        setVersion(res);
                        insertads(res);
                    }
                }
            } catch (Exception e) {
            }
        }

        private void setVersion(HashMap res) {
            try {
                HashMap hl = (HashMap) res.get("expiry");
                boolean b = ((Boolean) res.get("expiryflag")).booleanValue();
                System.out.println("Expiry Flag" + b);
                if (b) {
                    hl.put("method", "insertstatus");
                    hl.put("status", "inactive");
                    hl.put("msg", "Subscription expired");
                    AppHelper.getValues(hl, TipNotifyService.this);
                }
            } catch (Exception e) {
            }
        }

        private void insertads(HashMap res) {
            try {
                HashMap hl = (HashMap) res.get("adhashmap");
                hl.put("method", "insertads");
                AppHelper.getValues(hl, TipNotifyService.this);
            } catch (Exception e) {
            }
        }

        private void setmodifier(HashMap res) {
            try {
                HashMap hl = (HashMap) res.get("modifyhashmap");
                hl.put("method", "modifiydata");
                AppHelper.getValues(hl, TipNotifyService.this);
            } catch (Exception e) {
            }
        }

        private void setlivetestnotification(HashMap res) {
            try {
                ArrayList<ExamModel> elist = (ArrayList) ((HashMap) res.get("livetesthashmap")).get("elist");
                for (int i = 0; i < elist.size(); i++) {
                    ExamModel m = (ExamModel) elist.get(i);
                    int id = m.getExam_id();
                    TipNotifyService.this.set("Live Test", C0539R.drawable.livetest, "Live Test on" + m.getExam_Date() + " at" + m.getStart_Time(), id + 50);
                }
            } catch (Exception e) {
            }
        }

        private void settipnotification(HashMap res) {
            try {
                AppConstant.tiplist = (ArrayList) ((HashMap) res.get("tiphashmap")).get("tiplist");
                for (int i = 0; i < AppConstant.tiplist.size(); i++) {
                    System.out.println("tip");
                    TipNotifyService.this.set("Tip of the day", C0539R.drawable.tipsicon, ((TipModel) AppConstant.tiplist.get(i)).getTip_content(), i + 5);
                }
            } catch (Exception e) {
            }
        }

        private void setnewsnotification(HashMap res) {
            try {
                System.out.println("news");
                AppConstant.newslist = (ArrayList) ((HashMap) res.get("newshashmap")).get("newslist");
                System.out.println("news");
                for (int i = 0; i < AppConstant.newslist.size(); i++) {
                    System.out.println("news");
                    TipNotifyService.this.set("JEE news", C0539R.drawable.news, ((NewsModel) AppConstant.newslist.get(i)).getHeader(), i + 10);
                }
            } catch (Exception e) {
            }
        }

        private void setupdatenotification(HashMap res) {
            try {
                AppConstant.updatelist = (ArrayList) ((HashMap) res.get("updatehashmap")).get("examlist");
                if (AppConstant.updatelist.size() > 0) {
                    System.out.println("upadtes");
                    TipNotifyService.this.set("Updates", C0539R.drawable.updates, AppConstant.updatelist.size() + " new exam upadates available", 15);
                }
            } catch (Exception e) {
            }
        }
    }

    public class TipServiceReceiver extends BroadcastReceiver {
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getIntExtra(TipNotifyService.STOP_SERVICE_BROADCAST_KEY, 0) == 1) {
                TipNotifyService.this.stopSelf();
                ((NotificationManager) TipNotifyService.this.getSystemService("notification")).cancelAll();
            }
        }
    }

    public void onCreate() {
        System.out.println("In service of tip notification");
        this.notifyServiceReceiver = new TipServiceReceiver();
        super.onCreate();
        new QuoteDownloadFileServer().DataOnSite(AppConstant.serverURLForQuotes, AppConstant.jsonDailyQuotes);
        new QuoteDownloadFileServer().DataOnSite(AppConstant.serverURLForNews, AppConstant.news_data);
    }

    @SuppressLint({"NewApi"})
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("In service of tip notification");
        new Timer().schedule(new MyTimerTask(), 300, TimeChart.DAY);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        unregisterReceiver(this.notifyServiceReceiver);
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
        } else if (t.equalsIgnoreCase("Live Test")) {
            intent = new Intent(this, SelectSubjectActivity.class);
            intent.putExtra("id", id - 50);
        }
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        System.out.println("in notifications");
        Notification mNotification = new Builder(this).setContentTitle(t).setContentText(str).setSmallIcon(imageid).setContentIntent(pIntent).setSound(soundUri).addAction(C0539R.drawable.check, "View", pIntent).addAction(id, "Remind", pIntent).build();
        System.out.println("in notifications");
        mNotification.flags |= 16;
        this.notificationManager.notify(id, mNotification);
        System.out.println("in notifications");
    }

    public void onTaskCompleted(HashMap hm) {
    }
}

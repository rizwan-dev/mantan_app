package consys.onlineexam.manthan3offlineexam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.AppHelper;
import consys.onlineexam.helper.ServerConnection;
import consys.onlineexam.helper.StorageUtils;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import org.apache.commons.io.FileSystemUtils;

public class WelcomeActivity extends Activity implements TaskListener {
    boolean isDataFetchedFromDB = false;
    BroadcastReceiver mIntentReceiver;

    class CustomDialogExit extends Dialog implements OnClickListener {
        /* renamed from: c */
        public Activity f17c;
        /* renamed from: d */
        public Dialog f18d;
        public Button no;
        public Button yes;

        public CustomDialogExit(Activity a) {
            super(a);
            this.f17c = a;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(1);
            setContentView(C0539R.layout.customdiealog);
            this.yes = (Button) findViewById(C0539R.id.btnexit_yes);
            this.no = (Button) findViewById(C0539R.id.btnexit_no);
            this.yes.setOnClickListener(this);
            this.no.setOnClickListener(this);
            setCanceledOnTouchOutside(false);
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case C0539R.id.btnexit_yes:
                    WelcomeActivity.this.finish();
                    return;
                case C0539R.id.btnexit_no:
                    dismiss();
                    WelcomeActivity.this.checkdb();
                    return;
                default:
                    return;
            }
        }

        public void onBackPressed() {
        }
    }

    @SuppressLint({"NewApi"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_welcome);
        System.out.println(getApplicationContext().getFilesDir());
        AppConstant.obj = this;
        if (getIntent().getBooleanExtra("EXIT", false)) {
            new CustomDialogExit(this).show();
        } else {
            AppConstant.back_flag = false;
            checkdb();
        }
        if (!isMyServiceRunning(InspirationalQuoteService.class)) {
            setNotifications();
        }
    }

    public void checkdb() {
        new HashMap().put("method", "createdatabase");
        MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(this);
        HashMap j = new HashMap();
        m.execute(new HashMap[]{req});
    }

    @SuppressLint({"NewApi"})
    public String getpath() {
        try {
            File d = new File(StorageUtils.getpath());
            if ((FileSystemUtils.freeSpaceKb(d.getAbsolutePath()) / 1024) / 1024 <= 80) {
                CommonActivity.toast(this, "Insufficient memory storage please try to make some free space");
            } else if (d.canWrite()) {
                return new String(d.getAbsolutePath());
            } else {
                CommonActivity.toast(this, "Memory does not have write access");
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void onTaskCompleted(HashMap hm) {
        if (this.isDataFetchedFromDB) {
            HashMap res = hm;
            System.out.println(hm);
            Boolean b = (Boolean) res.get("flag");
            AppConstant.elist = (ArrayList) res.get("elist");
            startActivity(new Intent(this, HomePage.class));
            return;
        }
        try {
            if (((Boolean) hm.get("flag")).booleanValue()) {
                AppConstant.back_flag = true;
                getURL(this);
                callService();
                if (CommonActivity.readString().equalsIgnoreCase("R")) {
                    System.out.println("IMEI NOT SAME");
                    AppConstant.selected_exam1 = "Practice Exam";
                    AppConstant.selected_exam = "Practice Exam";
                    HashMap req = new HashMap();
                    req.put("method", "getsubjectlist");
                    req.put("subject", AppConstant.selected_exam1);
                    req.put("chapterid", Integer.valueOf(255));
                    AppConstant.selected_subject = "Practice Exam";
                    AppConstant.selected_chapter = "Practice Exam";
                    MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(this);
                    HashMap j = new HashMap();
                    m.execute(new HashMap[]{req});
                    this.isDataFetchedFromDB = true;
                    return;
                }
                startActivity(new Intent(this, RegisterActivity.class));
            }
        } catch (Exception e) {
            System.out.println("Exception in WelcomeActivity");
        }
    }

    public void callService() {
        if (ServerConnection.isConnectingToInternet(this) && !isMyServiceRunning(TipNotifyService.class)) {
            startService(new Intent(this, TipNotifyService.class));
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        for (RunningServiceInfo service : ((ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean getURL(Context xt) {
        System.out.println("Brefore getting url method");
        HashMap h = new HashMap();
        h.put("method", "setserverconfig");
        HashMap res = AppHelper.getValues(h, xt);
        System.out.println("after");
        if (res == null) {
            return false;
        }
        AppConstant.server_url = (String) res.get("ip");
        AppConstant.Server_Phone_No = (String) res.get("sender");
        AppConstant.Server_SMS_Receiver = (String) res.get("rec");
        AppConstant.contact1 = (String) res.get("contact1");
        AppConstant.contact2 = (String) res.get("contact2");
        AppConstant.contact3 = (String) res.get("contact3");
        return true;
    }

    public void onBackPressed() {
    }

    public void setNotifications() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 8);
        calendar.set(12, 35);
        calendar.set(13, 0);
        ((AlarmManager) getSystemService("alarm")).set(0, calendar.getTimeInMillis(), PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), 0));
    }
}

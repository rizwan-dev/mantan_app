package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import com.anjlab.android.iab.v3.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.sun.mail.imap.IMAPStore;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.AppHelper;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import consys.onlineexam.manthan3offlineexam.wifiSocket.CreatingWiFiHotspot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainMenuActivity extends Activity implements TaskListener {
    public static int test_flag = 1;
    AdRequest adRequest;
    Integer[] imageId = new Integer[]{Integer.valueOf(C0539R.drawable.one1), Integer.valueOf(C0539R.drawable.two), Integer.valueOf(C0539R.drawable.livetest), Integer.valueOf(C0539R.drawable.stat), Integer.valueOf(C0539R.drawable.news)};
    ListView list_sel_sub;
    private AdView mAdView;
    BroadcastReceiver mIntentReceiver;
    String[] sub = new String[]{"Chapterwise Exam", "Practice Exam", "Live Exam", "My Statistics", "News"};
    String subject;

    protected void onStart() {
        super.onStart();
        if (this.mAdView != null) {
            this.mAdView.loadAd(this.adRequest);
        }
    }

    public void onPause() {
        if (this.mAdView != null) {
            this.mAdView.pause();
        }
        super.onPause();
    }

    public void onDestroy() {
        if (this.mAdView != null) {
            this.mAdView.destroy();
        }
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        if (this.mAdView != null) {
            this.mAdView.resume();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.mainmenu);
        Animation vanish = AnimationUtils.loadAnimation(this, C0539R.anim.vanish);
        AppConstant.obj = this;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        AppConstant.obj = this;
        getAds();
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    public void show(final View v) {
        ((Button) findViewById(v.getId())).startAnimation(AnimationUtils.loadAnimation(this, C0539R.anim.anim_rotate));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (v.getId() == C0539R.id.btnchapter_wise) {
                    MainMenuActivity.test_flag = 1;
                    AppConstant.selected_exam1 = "Topicwise Exam";
                    AppConstant.selected_exam = "Topicwise Exam";
                    HashMap req = new HashMap();
                    req.put("method", "getsubjectlist");
                    req.put("subject", AppConstant.selected_exam1);
                    MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(MainMenuActivity.this);
                    HashMap j = new HashMap();
                    m.execute(new HashMap[]{req});
                }
                if (v.getId() == C0539R.id.btnnews) {
                    MainMenuActivity.this.startActivity(new Intent(MainMenuActivity.this, NewsPaidUser.class));
                }
                if (v.getId() == C0539R.id.btnpractice_exam) {
                    MainMenuActivity.test_flag = 2;
                    AppConstant.selected_exam1 = "Practice Exam";
                    AppConstant.selected_exam = "Practice Exam";
                    req = new HashMap();
                    req.put("method", "getsubjectlist");
                    req.put("subject", AppConstant.selected_exam1);
                    req.put("chapterid", Integer.valueOf(255));
                    AppConstant.selected_subject = "Practice Exam";
                    AppConstant.selected_chapter = "Practice Exam";
                    m = new MyAsynchTaskExecutor(MainMenuActivity.this);
                    j = new HashMap();
                    m.execute(new HashMap[]{req});
                }
                if (v.getId() == C0539R.id.btn_stat) {
                    MainMenuActivity.this.startActivity(new Intent(MainMenuActivity.this, MyStatActivity.class));
                }
                if (v.getId() == C0539R.id.btnlive_exam) {
                    MainMenuActivity.this.startActivity(new Intent(MainMenuActivity.this, CreatingWiFiHotspot.class));
                }
                if (v.getId() == C0539R.id.btnOurProducts) {
                    MainMenuActivity.this.startActivity(new Intent(MainMenuActivity.this, AboutActivity.class));
                }
            }
        }, 1500);
    }

    private void animate() {
    }

    public void getAds() {
        HashMap hm = new HashMap();
        hm.put("method", "getLocalAds");
        AppConstant.adlist = (ArrayList) AppHelper.getValues(hm, this).get("adlist");
        Collections.shuffle(AppConstant.adlist);
    }

    public void onTaskCompleted(HashMap hm) {
        HashMap res = hm;
        try {
            System.out.println(hm);
            if (!((Boolean) res.get("flag")).booleanValue()) {
                try {
                    CommonActivity.toast(this, (String) hm.get("msg"));
                } catch (Exception e) {
                    CommonActivity.toast(this, "Connection Problem");
                }
            } else if (test_flag == 1) {
                System.out.println();
                AppConstant.sublist = (ArrayList) res.get("sublist");
                AppConstant.sub_exam_list_map = (HashMap) res.get("hm_exam_list");
                System.out.println(hm);
                startActivity(new Intent(this, TestSubActivity.class));
            } else if (test_flag > 1) {
                AppConstant.elist = (ArrayList) res.get("elist");
                if (AppConstant.elist.size() > 0) {
                    startActivity(new Intent(this, TestListActivity.class));
                }
            }
        } catch (Exception e2) {
            System.out.println("Exception in SelectSubjectActivity" + e2);
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(67108864);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    public void deleteSMS(Context context, String message, String number) {
        try {
            CommonActivity.toast(context, "In delete msg" + number);
            Uri uriSms = Uri.parse("content://sms/ ");
            Cursor c = context.getContentResolver().query(uriSms, new String[]{"_id", "thread_id", IMAPStore.ID_ADDRESS, "person", IMAPStore.ID_DATE, "body"}, null, null, null);
            if (c != null && c.moveToFirst()) {
                do {
                    long id = c.getLong(0);
                    long threadId = c.getLong(1);
                    String address = c.getString(2);
                    String body = c.getString(5);
                    if (address.equals(number)) {
                        CommonActivity.toast(context, "In delete msg");
                        context.getContentResolver().delete(Uri.parse("content://sms/" + id), null, null);
                    }
                } while (c.moveToNext());
            }
        } catch (Exception e) {
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0539R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == C0539R.id.aboutus) {
            startActivity(new Intent(this, AboutActivity.class));
        }
        if (item.getItemId() == C0539R.id.shareMenu) {
            Intent sharemenu = new Intent("android.intent.action.SEND");
            sharemenu.setType("text/plain");
            sharemenu.addFlags(AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END);
            sharemenu.putExtra("android.intent.extra.SUBJECT", "Consistent System");
            sharemenu.putExtra("android.intent.extra.TEXT", "market://details?id=consys.onlineexam.manthanofflineeaxam");
            startActivity(Intent.createChooser(sharemenu, "Share link!"));
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void insertflag(int id2) {
        HashMap hm = new HashMap();
        hm.put("method", "insertflag");
        hm.put("id", Integer.valueOf(id2));
        hm.put(Constants.RESPONSE_TYPE, "livetest");
        AppHelper.getValues(hm, this);
    }

    private Intent createShareIntent() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", "Shared from the ActionBar widget.");
        return Intent.createChooser(intent, "Share");
    }

    public void getpro(View v) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=consys.onlineexam.jeefree")));
        } catch (Exception e) {
        }
    }
}

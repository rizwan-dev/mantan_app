package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.SolutionModel;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.util.ArrayList;
import java.util.HashMap;

public class StudySolutionActivity extends Activity implements OnTouchListener, TaskListener {
    AdRequest adRequest;
    String data = "";
    private AdView mAdView;
    WebView solutionview;

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
        setContentView(C0539R.layout.activity_study_solution);
        ((TextView) findViewById(C0539R.id.titlechapter)).setText(AppConstant.study_chapter);
        this.solutionview = (WebView) findViewById(C0539R.id.webviewsolution);
        this.solutionview.getSettings().setAllowFileAccess(true);
        this.solutionview.getSettings().setJavaScriptEnabled(true);
        this.solutionview.getSettings().setBuiltInZoomControls(true);
        String base = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        setSolutions();
        setAdvertise();
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    private void setAdvertise() {
        TextView tx1 = (TextView) findViewById(C0539R.id.studmads2);
        TextView tx2 = (TextView) findViewById(C0539R.id.studmads3);
        TextView tx3 = (TextView) findViewById(C0539R.id.studmads4);
        TextView tx4 = (TextView) findViewById(C0539R.id.studmads5);
        CommonActivity.setAds(0, (TextView) findViewById(C0539R.id.studmads1));
        CommonActivity.setAds(3, tx1);
        CommonActivity.setAds(5, tx2);
        CommonActivity.setAds(7, tx3);
        CommonActivity.setAds(9, tx4);
        ViewFlipper mFlipper = (ViewFlipper) findViewById(C0539R.id.viewFlipperstudym);
        mFlipper.startFlipping();
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(this, C0539R.anim.push_up_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, C0539R.anim.push_up_in));
    }

    public void onBackPressed() {
        startActivity(new Intent(this, StudyChapterActivity.class));
    }

    private void setSolutions() {
        HashMap hm = new HashMap();
        hm.put("method", "getsolutions");
        hm.put("chapter", AppConstant.study_chapter);
        new MyAsynchTaskExecutor(this).execute(new HashMap[]{hm, null, null});
    }

    public void onTaskCompleted(HashMap hm) {
        try {
            if (((Boolean) hm.get("flag")).booleanValue()) {
                ArrayList<SolutionModel> sollist = (ArrayList) hm.get("sollist");
                for (int i = 0; i < sollist.size(); i++) {
                    this.data += "</br>" + ((SolutionModel) sollist.get(i)).getSolution();
                }
                String html = CommonActivity.getHTML(this.data);
                System.out.println(html);
                this.solutionview.loadDataWithBaseURL("", html, "text/html", "utf-8", "");
                System.out.println("sollist");
                return;
            }
            CommonActivity.toast(this, "Connection Problem");
        } catch (Exception e) {
            System.out.println("Exception in StudySolutionActivity" + e);
        }
    }

    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case 0:
                float x1 = touchevent.getX();
                float y1 = touchevent.getY();
                break;
            case 1:
                float x2 = touchevent.getX();
                float y2 = touchevent.getY();
                if (0.0f < x2) {
                    startActivity(new Intent(this, StudyChapterActivity.class));
                }
                if (0.0f > x2) {
                    startActivity(new Intent(this, MainMenuActivity.class));
                    break;
                }
                break;
        }
        return false;
    }

    public boolean onTouch(View v, MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case 0:
                float x1 = touchevent.getX();
                float y1 = touchevent.getY();
                break;
            case 1:
                float x2 = touchevent.getX();
                float y2 = touchevent.getY();
                if (0.0f < x2) {
                    startActivity(new Intent(this, StudyChapterActivity.class));
                }
                if (0.0f > x2) {
                    startActivity(new Intent(this, MainMenuActivity.class));
                    break;
                }
                break;
        }
        return false;
    }
}

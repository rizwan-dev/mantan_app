package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import java.util.HashMap;

public class MyStatActivity extends Activity implements TaskListener {
    AdRequest adRequest;
    Animation animRotate;
    Button btnlive;
    private AdView mAdView;

    /* renamed from: consys.onlineexam.manthan3offlineexam.MyStatActivity$1 */
    class C05311 implements Runnable {
        C05311() {
        }

        public void run() {
            MyStatActivity.this.startActivity(new Intent(MyStatActivity.this, ShowStatActivity.class));
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.MyStatActivity$2 */
    class C05322 implements Runnable {
        C05322() {
        }

        public void run() {
            MyStatActivity.this.startActivity(new Intent(MyStatActivity.this, ShowStatActivity.class));
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.MyStatActivity$3 */
    class C05333 implements Runnable {
        C05333() {
        }

        public void run() {
            MyStatActivity.this.startActivity(new Intent(MyStatActivity.this, ShowStatActivity.class));
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.MyStatActivity$4 */
    class C05344 implements Runnable {
        C05344() {
        }

        public void run() {
            MyStatActivity.this.startActivity(new Intent(MyStatActivity.this, ShowStatActivity.class));
        }
    }

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_mystat);
        this.animRotate = AnimationUtils.loadAnimation(this, C0539R.anim.anim_rotate);
        this.btnlive = (Button) findViewById(C0539R.id.btnstatlive);
        setAdvertise();
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    private void setAdvertise() {
        TextView tx1 = (TextView) findViewById(C0539R.id.studmads2);
        TextView tx2 = (TextView) findViewById(C0539R.id.studmads3);
        TextView tx3 = (TextView) findViewById(C0539R.id.studmads4);
        TextView tx4 = (TextView) findViewById(C0539R.id.studmads5);
        CommonActivity.setAds(1, (TextView) findViewById(C0539R.id.studmads1));
        CommonActivity.setAds(2, tx1);
        CommonActivity.setAds(3, tx2);
        CommonActivity.setAds(4, tx3);
        CommonActivity.setAds(5, tx4);
        ViewFlipper mFlipper = (ViewFlipper) findViewById(C0539R.id.viewFlipperstudym);
        mFlipper.startFlipping();
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(this, C0539R.anim.push_up_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, C0539R.anim.push_up_in));
    }

    public void setStatTopic(View v) {
        v.startAnimation(this.animRotate);
        AppConstant.selected_exam1 = "Topicwise Exam";
        AppConstant.selected_exam = "Topicwise Exam";
        new Handler().postDelayed(new C05311(), 1500);
    }

    public void setStatPract(View v) {
        v.startAnimation(this.animRotate);
        AppConstant.selected_exam1 = "Practice Exam";
        AppConstant.selected_exam = "Practice Exam";
        new Handler().postDelayed(new C05322(), 1500);
    }

    public void setStatLive(View v) {
        v.startAnimation(this.animRotate);
        AppConstant.selected_exam1 = "Live Exam";
        AppConstant.selected_exam = "Live Exam";
        new Handler().postDelayed(new C05333(), 1500);
    }

    public void setStatReport(View v) {
        v.startAnimation(this.animRotate);
        AppConstant.selected_exam1 = "Topicwise Exam or Practice Exam or Live Exam";
        AppConstant.selected_exam = "Report";
        new Handler().postDelayed(new C05344(), 1500);
    }

    public void onTaskCompleted(HashMap hm) {
        try {
            if (!((Boolean) hm.get("flag")).booleanValue()) {
            }
        } catch (Exception e) {
            System.out.println("Exception in MyStatActivity" + e);
        }
    }
}

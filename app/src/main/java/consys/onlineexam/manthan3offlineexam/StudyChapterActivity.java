package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.util.ArrayList;
import java.util.HashMap;

public class StudyChapterActivity extends Activity implements OnTouchListener, TaskListener {
    AdRequest adRequest;
    ListView list;
    private AdView mAdView;
    String[] subarray;

    /* renamed from: consys.onlineexam.manthan3offlineexam.StudyChapterActivity$1 */
    class C05411 implements OnItemClickListener {
        C05411() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            int itemPosition = position;
            AppConstant.study_chapter = (String) StudyChapterActivity.this.list.getItemAtPosition(position);
            StudyChapterActivity.this.startActivity(new Intent(StudyChapterActivity.this, StudySolutionActivity.class));
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_study_chapter);
        this.list = (ListView) findViewById(C0539R.id.study_chapter_list);
        ((TextView) findViewById(C0539R.id.titlesubject)).setText("Study Material-" + AppConstant.study_subject);
        setStudyChapter();
        setAdvertise();
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    public void onBackPressed() {
        startActivity(new Intent(this, StudyMaterialActivity.class));
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

    private void setStudyChapter() {
        HashMap hm = new HashMap();
        hm.put("method", "getstudychapter");
        hm.put("subject", AppConstant.study_subject);
        new MyAsynchTaskExecutor(this).execute(new HashMap[]{hm, null, null});
    }

    public void onTaskCompleted(HashMap hm) {
        try {
            if (((Boolean) hm.get("flag")).booleanValue()) {
                ArrayList<String> sublist = (ArrayList) hm.get("chaplist");
                this.subarray = new String[sublist.size()];
                for (int i = 0; i < sublist.size(); i++) {
                    this.subarray[i] = (String) sublist.get(i);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(this, 17367043, 16908308, this.subarray);
                this.list.setAdapter(new StudySubjectCustomList(this, this.subarray));
                Animation animationSet = new AnimationSet(true);
                Animation animation = new AlphaAnimation(0.0f, 1.0f);
                animation.setDuration(500);
                animationSet.addAnimation(animation);
                animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
                animation.setDuration(100);
                animationSet.addAnimation(animation);
                this.list.setLayoutAnimation(new LayoutAnimationController(animationSet, 0.5f));
                this.list.setOnItemClickListener(new C05411());
                return;
            }
            CommonActivity.toast(this, "Connection Problem");
        } catch (Exception e) {
            System.out.println("Exception in StudyChapterActivity");
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

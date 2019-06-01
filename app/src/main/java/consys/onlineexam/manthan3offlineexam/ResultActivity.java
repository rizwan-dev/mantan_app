package consys.onlineexam.manthan3offlineexam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ResultActivity extends CommonActivity implements TaskListener {
    AdRequest adRequest;
    boolean closTest;
    private AdView mAdView;
    TextView txtAchiveMarks;
    TextView txtChapter;
    TextView txtPercentage;
    TextView txtStudent;
    TextView txtSubject;
    TextView txtTest;
    TextView txtTotalMarks;
    TextView txtsolvedque;
    TextView txttotalquestion;

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
        setContentView(C0539R.layout.activity_result);
        if (getIntent().hasExtra("id")) {
            this.txtSubject = (TextView) findViewById(C0539R.id.txt_subject);
            this.txtChapter = (TextView) findViewById(C0539R.id.txt_chapter);
            this.txtTest = (TextView) findViewById(C0539R.id.txt_test);
            this.txtTotalMarks = (TextView) findViewById(C0539R.id.txt_totalmarks);
            this.txtAchiveMarks = (TextView) findViewById(C0539R.id.txt_optainmarks);
            this.txtPercentage = (TextView) findViewById(C0539R.id.txt_per);
            this.txttotalquestion = (TextView) findViewById(C0539R.id.txt_totalquestion);
            this.txtsolvedque = (TextView) findViewById(C0539R.id.txt_solvedquestion);
            this.txtSubject.setText(" " + AppConstant.selected_subject);
            this.txtChapter.setText(" " + AppConstant.selected_chapter);
            this.txtTest.setText(" " + AppConstant.selected_test);
            this.txttotalquestion.setText(" " + AppConstant.total_questions);
            this.txtsolvedque.setText(" " + AppConstant.solved_question);
            this.txtTotalMarks.setText(" " + AppConstant.totalMarks);
            this.txtAchiveMarks.setText(" " + AppConstant.obtainedMarks);
            AppConstant.marks_percentage = AppConstant.obtainedMarks / (AppConstant.totalMarks / 100.0f);
            this.txtPercentage.setText(" " + String.format("%.02f", new Object[]{Float.valueOf(AppConstant.marks_percentage)}) + " %");
            this.closTest = false;
            if (AppConstant.r_flag) {
                saveResult();
                AppConstant.r_flag = false;
            }
        } else {
            Button b = (Button) findViewById(C0539R.id.btnreview);
            b.setEnabled(false);
            b.setVisibility(8);
        }
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    private void saveResult() {
        HashMap hm = new HashMap();
        hm.put("method", "saveresult");
        hm.put("studid", Integer.valueOf(AppConstant.student.getId()));
        hm.put("examid", Integer.valueOf(AppConstant.selected_test_Id));
        hm.put("totalmarks", Float.valueOf(AppConstant.totalMarks));
        hm.put("obtainedmarks", Float.valueOf(AppConstant.obtainedMarks));
        hm.put("marksper", Float.valueOf(AppConstant.marks_percentage));
        new MyAsynchTaskExecutor(this).execute(new HashMap[]{hm});
    }

    public void onTaskCompleted(HashMap hm) {
        try {
            if (this.closTest) {
                HashMap res = hm;
                System.out.println(hm);
                Boolean b = (Boolean) res.get("flag");
                AppConstant.elist = (ArrayList) res.get("elist");
                if (AppConstant.elist.size() > 0) {
                    startActivity(new Intent(this, TestListActivity.class));
                } else {
                    Toast.makeText(this, "No Practice Test Available", 1).show();
                }
            } else if (((Boolean) hm.get("flag")).booleanValue()) {
                Log.d("Result Saved Status", "Result Saved");
            } else {
                Log.d("Result Saved Status", "Result Not Saved");
                CommonActivity.toast(this, "Connection Problem");
            }
        } catch (Exception e) {
            System.out.println("Exception in ResultActivity");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0539R.menu.resultmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == C0539R.id.logout) {
            onerror(null);
        } else if (item.getItemId() == C0539R.id.aboutus2) {
            startActivity(new Intent(this, AboutActivity.class));
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void onerror(View v) {
        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        saveBitmap(v1.getDrawingCache());
        sendMail(AppConstant.DB_PATH + "/myibpsresult.png");
    }

    public void saveBitmap(Bitmap bitmap) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(AppConstant.DB_PATH + "/myibpsresult.png"));
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e2) {
            Log.e("GREC", e2.getMessage(), e2);
        }
    }

    public void sendMail(String path) {
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{AppConstant.server_mail});
        emailIntent.putExtra("android.intent.extra.SUBJECT", "manthan");
        emailIntent.putExtra("android.intent.extra.TEXT", "Hey see my result of " + AppConstant.selected_test);
        emailIntent.setType("image/png");
        emailIntent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + path));
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void resultreview(View v) {
        AppConstant.resultview = true;
        AppConstant.qcount = 0;
        startActivity(new Intent(this, ExamActivity.class));
    }

    public void onBackPressed() {
    }

    public void mainmenu(View v) {
        logout();
        AppConstant.selected_exam = "Practice Exam";
        AppConstant.selected_exam1 = "Practice Exam";
        HashMap req = new HashMap();
        req.put("method", "getsubjectlist");
        req.put("subject", AppConstant.selected_exam1);
        req.put("chapterid", Integer.valueOf(255));
        AppConstant.selected_subject = "Practice Exam";
        AppConstant.selected_chapter = "Practice Exam";
        MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(this);
        HashMap j = new HashMap();
        m.execute(new HashMap[]{req});
        this.closTest = true;
    }
}

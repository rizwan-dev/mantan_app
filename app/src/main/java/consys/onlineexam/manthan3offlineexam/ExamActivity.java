package consys.onlineexam.manthan3offlineexam;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.support.v4.internal.view.SupportMenu;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.AppHelper;
import consys.onlineexam.helper.ExamModel;
import consys.onlineexam.helper.OptionModel;
import consys.onlineexam.helper.QuestionModel;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import example.EventDataSQLHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint({"NewApi"})
public class ExamActivity extends Activity implements TaskListener, OnClickListener {
    public static int difftime;
    static long storeMillies;
    public static CountDownTimer timer;
    String ButtonClear = "0";
    boolean UIFlag = true;
    AdRequest adRequest;
    Button btn;
    int btname = 0;
    Button btnresult;
    Button butSubmit;
    int buttonwidth = 0;
    int currentQuestion = 0;
    int height = 0;
    LinearLayout layout;
    private AdView mAdView;
    int marg = 0;
    long millis = 0;
    boolean onPause;
    boolean onPauseComplte;
    ArrayList qdlist;
    int qno;
    String quetype;
    WebView queview;
    RadioGroup rdgrp;
    int selected_button = 0;
    TextView txtimedisplay;
    TextView txtquedisplay;
    int width = 0;

    /* renamed from: consys.onlineexam.manthan3offlineexam.ExamActivity$1 */
    class C05211 implements OnClickListener {
        C05211() {
        }

        public void onClick(View v) {
            new CustomDialogSubmitClass3(ExamActivity.this).show();
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.ExamActivity$2 */
    class C05222 implements OnLongClickListener {
        C05222() {
        }

        public boolean onLongClick(View v) {
            return true;
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.ExamActivity$3 */
    class C05233 implements OnClickListener {
        C05233() {
        }

        public void onClick(View v) {
            if (AppConstant.resultview) {
                new CustomDialogSubmitClass1(ExamActivity.this).show();
            } else {
                new CustomDialogSubmitClass(ExamActivity.this).show();
            }
        }
    }

    static class AsynchTaskExecutor extends AsyncTask<HashMap, HashMap, HashMap> {
        ProgressDialog pro;
        HashMap result;
        public ExamActivity task;

        public AsynchTaskExecutor(ExamActivity t) {
            this.task = t;
            this.pro = new ProgressDialog(t);
            this.pro.setMessage("Please wait...");
            this.pro.setProgressStyle(0);
            this.pro.setIndeterminate(true);
            this.pro.show();
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected HashMap doInBackground(HashMap... args) {
            this.task.getResult(0);
            HashMap res = AppHelper.getValues(args[0], this.task);
            return null;
        }

        protected void onPostExecute(HashMap r) {
            this.pro.dismiss();
            this.task.viewresult(null);
            super.onPostExecute(this.result);
        }
    }

    class CustomDialogExit extends Dialog implements OnClickListener {
        /* renamed from: c */
        public Activity f8c;
        /* renamed from: d */
        public Dialog f9d;
        public Button no;
        public Button yes;

        public CustomDialogExit(Activity a) {
            super(a);
            this.f8c = a;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(1);
            setContentView(C0539R.layout.customdiealog);
            this.yes = (Button) findViewById(C0539R.id.btnexit_yes);
            this.no = (Button) findViewById(C0539R.id.btnexit_no);
            this.yes.setOnClickListener(this);
            this.no.setOnClickListener(this);
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case C0539R.id.btnexit_yes:
                    ExamActivity.this.startActivity(new Intent(ExamActivity.this, MainMenuActivity.class));
                    return;
                case C0539R.id.btnexit_no:
                    dismiss();
                    return;
                default:
                    return;
            }
        }
    }

    class CustomDialogSubmitClass1 extends Dialog implements OnClickListener {
        /* renamed from: c */
        public Activity f10c;
        /* renamed from: d */
        public Dialog f11d;
        public Button no;
        public Button yes;

        public CustomDialogSubmitClass1(Activity a) {
            super(a);
            this.f10c = a;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(1);
            setContentView(C0539R.layout.custom_dialog_review_submit);
            this.yes = (Button) findViewById(C0539R.id.btn_yes);
            this.no = (Button) findViewById(C0539R.id.btn_no);
            this.yes.setOnClickListener(this);
            this.no.setOnClickListener(this);
            setCanceledOnTouchOutside(false);
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case C0539R.id.btn_yes:
                    ExamActivity.this.submitresult();
                    return;
                case C0539R.id.btn_no:
                    dismiss();
                    return;
                default:
                    return;
            }
        }
    }

    class CustomDialogSubmitClass3 extends Dialog implements OnClickListener {
        /* renamed from: c */
        public Activity f12c;
        /* renamed from: d */
        public Dialog f13d;
        public Button no;
        public Button yes;

        public CustomDialogSubmitClass3(Activity a) {
            super(a);
            this.f12c = a;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(1);
            setContentView(C0539R.layout.custom_dialog_submit);
            ((TextView) findViewById(C0539R.id.txt_dia)).setText("Do you Want To Pause Test?");
            this.yes = (Button) findViewById(C0539R.id.btn_yes);
            this.no = (Button) findViewById(C0539R.id.btn_no);
            this.yes.setOnClickListener(this);
            this.no.setOnClickListener(this);
            setCanceledOnTouchOutside(false);
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case C0539R.id.btn_yes:
                    ExamActivity.this.onPause = true;
                    HashMap req = new HashMap();
                    req.put("method", "pauseclick");
                    req.put(EventDataSQLHelper.TIME, Long.valueOf(ExamActivity.this.getMillis()));
                    req.put("qid", Integer.valueOf(ExamActivity.this.getCurrentQuestionId()));
                    req.put("tid", Integer.valueOf(AppConstant.selected_test_Id));
                    new MyAsynchTaskExecutor(ExamActivity.this).execute(new HashMap[]{req});
                    return;
                case C0539R.id.btn_no:
                    dismiss();
                    return;
                default:
                    return;
            }
        }
    }

    class CustomDialogSubmitClass extends Dialog implements OnClickListener {
        /* renamed from: c */
        public Activity f14c;
        /* renamed from: d */
        public Dialog f15d;
        public Button no;
        public Button yes;

        public CustomDialogSubmitClass(Activity a) {
            super(a);
            this.f14c = a;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(1);
            setContentView(C0539R.layout.custom_dialog_submit);
            this.yes = (Button) findViewById(C0539R.id.btn_yes);
            this.no = (Button) findViewById(C0539R.id.btn_no);
            this.yes.setOnClickListener(this);
            this.no.setOnClickListener(this);
            setCanceledOnTouchOutside(false);
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case C0539R.id.btn_yes:
                    ExamActivity.this.submitresult();
                    return;
                case C0539R.id.btn_no:
                    dismiss();
                    return;
                default:
                    return;
            }
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        public void onFinish() {
            ExamActivity.this.submitresult();
        }

        public void onTick(long millisUntilFinished) {
            ExamActivity.this.millis = millisUntilFinished;
            int seconds = ((int) (ExamActivity.this.millis / 1000)) % 60;
            int minutes = (int) ((ExamActivity.this.millis / 60000) % 60);
            int hours = (int) ((ExamActivity.this.millis / 3600000) % 24);
            ExamActivity.this.txtimedisplay.setText(String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)}));
            ExamActivity.storeMillies = ExamActivity.this.millis;
        }
    }

    protected void onStart() {
        super.onStart();
        try {
            difftime = System.getInt(getContentResolver(), "screen_off_timeout");
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }
        System.putInt(getContentResolver(), "screen_off_timeout", 20000000);
    }

    public void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        try {
            if (difftime == 20000000) {
                System.putInt(getContentResolver(), "screen_off_timeout", 15000);
            } else {
                System.putInt(getContentResolver(), "screen_off_timeout", difftime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_exam);
        Button next = (Button) findViewById(C0539R.id.btnnext);
        Button prev = (Button) findViewById(C0539R.id.btnprev);
        AppConstant.obj = this;
        this.onPause = false;
        this.onPauseComplte = false;
        this.txtimedisplay = (TextView) findViewById(C0539R.id.txt_time);
        this.txtquedisplay = (TextView) findViewById(C0539R.id.txtquedisplay);
        this.layout = (LinearLayout) findViewById(C0539R.id.textViedsggkj);
        this.queview = (WebView) findViewById(C0539R.id.webviewsolution);
        this.queview.getSettings().setAllowFileAccess(true);
        this.queview.getSettings().setJavaScriptEnabled(true);
        this.queview.getSettings().setBuiltInZoomControls(true);
        String base = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        View mCustomView = LayoutInflater.from(this).inflate(C0539R.layout.custom_actionbar, null);
        ((TextView) mCustomView.findViewById(C0539R.id.title_text)).setText(C0539R.string.app_name);
        TextView pausbtn = (TextView) mCustomView.findViewById(C0539R.id.imageButtonPause);
        pausbtn.setOnClickListener(new C05211());
        ExamModel exm = new ExamModel();
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        int q = ((Integer) AppConstant.qlist.get(AppConstant.qcount)).intValue();
        if (AppConstant.resultview) {
            pausbtn.setVisibility(4);
        } else {
            long time = (long) AppConstant.exam_time;
            if (ShowQuestionsActivity.examSummary) {
                ShowQuestionsActivity.examSummary = false;
                time = storeMillies;
            } else if (exm.getMillis() == 0) {
                time = (1000 * time) * 60;
            } else {
                time = exm.getMillis();
            }
            timer = new MyCountDownTimer(time, 1000);
            timer.start();
        }
        setQuestion(q);
        this.queview.setOnLongClickListener(new C05222());
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCurrentQuestionId() {
        return this.currentQuestion;
    }

    public void setQuestion(int qid) {
        this.currentQuestion = qid;
        HashMap req = new HashMap();
        if (AppConstant.selected_exam1.equalsIgnoreCase("Live_exam")) {
            req.put("method", "getlivequedetails");
        } else {
            req.put("method", "getquedetails");
            req.put("table", "question");
            req.put("table1", "answer");
        }
        req.put("qid", Integer.valueOf(qid));
        MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(this);
        HashMap j = new HashMap();
        m.execute(new HashMap[]{req});
    }

    public void next(View v) {
        if (this.UIFlag) {
            if (!AppConstant.resultview) {
                try {
                    if (this.selected_button == 0) {
                        this.selected_button = ((Integer) AppConstant.user_ans.get(AppConstant.qcount)).intValue();
                    }
                } catch (Exception e) {
                }
            }
            if (AppConstant.qcount < AppConstant.qlist.size() - 1) {
                try {
                    AppConstant.user_ans.set(AppConstant.qcount, Integer.valueOf(this.selected_button));
                    this.selected_button = 0;
                    AppConstant.qcount++;
                    int q = ((Integer) AppConstant.qlist.get(AppConstant.qcount)).intValue();
                    this.UIFlag = false;
                    setQuestion(q);
                    return;
                } catch (Exception e2) {
                    return;
                }
            }
            AppConstant.user_ans.set(AppConstant.qcount, Integer.valueOf(this.selected_button));
            CommonActivity.toast(this, "This is last Question");
            this.selected_button = 0;
            if (AppConstant.resultview) {
                new CustomDialogSubmitClass1(this).show();
            } else {
                new CustomDialogSubmitClass(this).show();
            }
        }
    }

    public void prev(View v) {
        if (this.UIFlag) {
            if (!AppConstant.resultview) {
                try {
                    if (this.selected_button == 0) {
                        this.selected_button = ((Integer) AppConstant.user_ans.get(AppConstant.qcount)).intValue();
                    }
                } catch (Exception e) {
                }
            }
            if (AppConstant.qcount > 0) {
                AppConstant.user_ans.set(AppConstant.qcount, Integer.valueOf(this.selected_button));
                this.selected_button = 0;
                AppConstant.qcount--;
                int q = ((Integer) AppConstant.qlist.get(AppConstant.qcount)).intValue();
                this.UIFlag = false;
                setQuestion(q);
                return;
            }
            CommonActivity.toast(this, "This is First Question");
            AppConstant.qcount++;
        }
    }

    private void getResult(int qcount) {
        int ctr = 0;
        float obtain = 0.0f;
        for (int i = 0; i < AppConstant.qlist.size(); i++) {
            int user = ((Integer) AppConstant.user_ans.get(i)).intValue();
            String e = AppConstant.correct_ans.get(i).toString();
            if (user != 0) {
                ctr++;
                int correct = getAnswInt(e);
                System.out.println("user " + user + " correct " + e);
                if (user == correct) {
                    obtain += 2.0f;
                } else {
                    System.out.println("before" + obtain + "Negative " + AppConstant.negative_marks);
                    obtain -= AppConstant.negative_marks;
                    System.out.println("after" + obtain);
                }
            }
        }
        AppConstant.obtainedMarks = obtain;
        AppConstant.solved_question = ctr;
    }

    private int getAnswInt(String e) {
        try {
            e = e.trim();
            if (e.equalsIgnoreCase("a") || e.equalsIgnoreCase("1") || e.equalsIgnoreCase("I")) {
                return 1;
            }
            if (e.equalsIgnoreCase("b") || e.equalsIgnoreCase("2") || e.equalsIgnoreCase("II")) {
                return 2;
            }
            if (e.equalsIgnoreCase("c") || e.equalsIgnoreCase("3") || e.equalsIgnoreCase("III")) {
                return 3;
            }
            if (e.equalsIgnoreCase("d") || e.equalsIgnoreCase("4") || e.equalsIgnoreCase("IV")) {
                return 4;
            }
            if (e.equalsIgnoreCase("e") || e.equalsIgnoreCase("5") || e.equalsIgnoreCase("V")) {
                return 5;
            }
            return Integer.parseInt(e.trim());
        } catch (Exception e2) {
            System.out.println("Exception in converting correct anse");
            return 0;
        }
    }

    public void submitresult() {
        if (AppConstant.resultview) {
            viewresult(null);
            return;
        }
        timer.cancel();
        AsynchTaskExecutor a = new AsynchTaskExecutor(this);
        HashMap res = new HashMap();
        res.put("method", "saveMyScore");
        res.put("tid", Integer.valueOf(AppConstant.selected_test_Id));
        res.put("qid", AppConstant.qlist);
        a.execute(new HashMap[]{res});
    }

    public void submit(View v) {
        submitresult();
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }

    public long getMillis() {
        return this.millis;
    }

    public void onTaskCompleted(HashMap hm) {
        HashMap res = hm;
        if (this.onPause) {
            timer.cancel();
            new CommonActivity().logout();
            HashMap req = new HashMap();
            req.put("method", "getsubjectlist");
            req.put("subject", AppConstant.selected_exam1);
            req.put("chapterid", Integer.valueOf(255));
            AppConstant.selected_subject = "Practice Exam";
            AppConstant.selected_chapter = "Practice Exam";
            MyAsynchTaskExecutor myAsynchTaskExecutor = new MyAsynchTaskExecutor(this);
            HashMap j = new HashMap();
            myAsynchTaskExecutor.execute(new HashMap[]{req});
            this.onPauseComplte = true;
            this.onPause = false;
        } else if (this.onPauseComplte) {
            if (((Boolean) res.get("flag")).booleanValue()) {
                AppConstant.elist = (ArrayList) res.get("elist");
                if (AppConstant.elist.size() > 0) {
                    this.onPauseComplte = false;
                    startActivity(new Intent(this, TestListActivity.class));
                    return;
                }
                return;
            }
            Toast.makeText(this, "No Practice Test Available", 1).show();
        } else {
            Boolean b = (Boolean) res.get("flag");
            AssetManager mgr = getApplicationContext().getAssets();
            if (b.booleanValue()) {
                this.qdlist = (ArrayList) res.get("queoptlist");
                QuestionModel q = (QuestionModel) this.qdlist.get(0);
                System.out.println("Correct ANS AT UI" + q.getCurrect_ans());
                AppConstant.correct_ans.set(AppConstant.qcount, q.getCurrect_ans());
                String d = "";
                try {
                    if (!(q.getDirection() == null || q.getDirection().equalsIgnoreCase(""))) {
                        d = q.getDirection() + "</br>";
                    }
                } catch (Exception e) {
                    System.out.println("Exception oF Direction");
                }
                try {
                    int i;
                    int p = AppConstant.qcount + 1;
                    String questionFromDatabase = "";
                    if (q.getQuestion() != null) {
                        questionFromDatabase = URLDecoder.decode(q.getQuestion());
                    }
                    String data = ("Que No." + p + ":</br>" + d + questionFromDatabase + "</br>").replaceAll("&nbsp;", "");
                    this.txtquedisplay.setText((AppConstant.qcount + 1) + "/" + AppConstant.total_questions);
                    if (this.layout.getChildCount() > 0) {
                        this.layout.removeAllViews();
                    }
                    int user = ((Integer) AppConstant.user_ans.get(AppConstant.qcount)).intValue();
                    String c = (String) AppConstant.correct_ans.get(AppConstant.qcount);
                    int correct = getAnswInt(c);
                    System.out.println("DATA BEFORE OPTION" + c + ":" + getAnswInt(c));
                    data = data + "<table>";
                    for (i = 1; i < this.qdlist.size(); i++) {
                        String optionHTML = URLDecoder.decode(((OptionModel) this.qdlist.get(i)).getOption()).replaceAll("<td[^>]+>", "").replaceAll("</td>", "").replaceAll("<p[^>]+>", "").replaceAll("</p>", "").replaceAll("&nbsp;", "");
                        if (optionHTML == null) {
                            if (!optionHTML.equalsIgnoreCase("")) {
                            }
                        }
                        String str = optionHTML;
                        if (str.contains("<img")) {
                            optionHTML = optionHTML.replaceAll("<span[^>]+>", "").replaceAll("</span>", "");
                            str = "<br/>" + str + "<br/>";
                            System.out.println("Option At ExamActivity " + str);
                        }
                        int[] iArr = new int[5];
                        data = data + "<br/><br/>" + "<tr><td valign=\"middle\">" + new int[]{1, 2, 3, 4, 5}[i - 1] + ")</td><td valign=\"middle\">" + str + "</td></tr>";
                    }
                    data = data + "</table>";
                    try {
                        if (AppConstant.resultview) {
                            System.out.println("DATA AFTER OPTION" + AppConstant.resultview + q.getSolution());
                            if (q.getSolution() != null && q.getSolution().length() > 1) {
                                data = data + "</br></br>Solution:</br></br>" + q.getSolution();
                            }
                            if (((q.getURL().length() > 1 ? 1 : 0) & (q.getURL() != null ? 1 : 0)) != 0) {
                                data = data + "</br></br>Reference URL:</br></br>" + q.getURL();
                            }
                        }
                    } catch (Exception j2) {
                        System.out.println("Exception in RESULR REVIEW" + j2);
                    }
                    String html = CommonActivity.getHTML(data + "<br><br><p align=right><font size='1'>" + q.getQue_id() + "</font></p>");
                    this.queview.getSettings().setJavaScriptEnabled(true);
                    this.queview.loadDataWithBaseURL("", html, "text/html", "utf-8", "");
                    AppConstant.attended_string += AppConstant.qcount + ",";
                    this.selected_button = user;
                    System.out.println(this.selected_button);
                    this.butSubmit = new Button(this);
                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                    this.height = displaymetrics.heightPixels;
                    this.width = displaymetrics.widthPixels;
                    this.layout.setMinimumWidth(this.width);
                    this.marg = 10;
                    this.btname = 64;
                    this.buttonwidth = this.width / 7;
                    ShapeDrawable bg1 = new ShapeDrawable(new RoundRectShape(new float[]{10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f}, null, null));
                    bg1.setColorFilter(-16711936, Mode.MULTIPLY);
                    bg1.getPaint().setColor(Color.rgb(0, 104, 139));
                    this.butSubmit.setPadding(2, 5, 2, 0);
                    LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, (int) (((double) this.buttonwidth) / 1.5d));
                    layoutParams.setMargins(8, 0, 0, 0);
                    this.butSubmit.setTextSize((float) (this.buttonwidth / 6));
                    if (AppConstant.resultview) {
                        this.butSubmit.setText(" Close ");
                    } else {
                        this.butSubmit.setText(" Submit ");
                    }
                    this.butSubmit.setTextColor(-1);
                    this.butSubmit.setBackgroundDrawable(bg1);
                    this.butSubmit.setLayoutParams(layoutParams);
                    this.butSubmit.setOnClickListener(new C05233());
                    this.layout.addView(this.butSubmit);
                    for (i = 1; i < this.qdlist.size(); i++) {
                        String o = ((OptionModel) this.qdlist.get(i)).getOption();
                        if (o == null) {
                            if (!o.equalsIgnoreCase("")) {
                                continue;
                            }
                        }
                        View button = new Button(this);
                        int[] iArr2 = new int[5];
                        button.setText("" + new int[]{1, 2, 3, 4, 5}[i - 1]);
                        button.setTextSize((float) (this.buttonwidth / 5));
                        button.setId(i);
                        ShapeDrawable bg = new ShapeDrawable(new RoundRectShape(new float[]{100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f}, null, null));
                        bg.getPaint().setColor(-1);
                        button.setBackgroundResource(C0539R.drawable.circleshapebutton);
                        button.setOnClickListener(this);
                        if (AppConstant.resultview) {
                            if (user == i) {
                                bg.setColorFilter(SupportMenu.CATEGORY_MASK, Mode.MULTIPLY);
                            }
                            if (correct == i) {
                                bg.setColorFilter(Color.rgb(34, 139, 34), Mode.MULTIPLY);
                            }
                            button.setEnabled(false);
                        } else if (user == i) {
                            bg.setColorFilter(Color.rgb(0, 191, 255), Mode.MULTIPLY);
                            bg.getPaint().setColor(Color.rgb(0, 191, 255));
                        }
                        layoutParams = new LinearLayout.LayoutParams(this.buttonwidth - 4, (int) (((double) this.buttonwidth) / 1.5d));
                        if (i == 1) {
                            layoutParams.setMargins(this.marg, 0, 0, 0);
                        } else {
                            try {
                                layoutParams.setMargins(8, 0, 0, 0);
                            } catch (Exception e2) {
                                System.out.println("Exception of BUTTONS");
                            }
                        }
                        button.setBackgroundDrawable(bg);
                        button.setPadding(0, 3, 0, 0);
                        button.setTextColor(-16777216);
                        button.setLayoutParams(layoutParams);
                        this.layout.addView(button);
                    }
                    this.UIFlag = true;
                    return;
                } catch (Exception e3) {
                    return;
                }
            }
            try {
                CommonActivity.toast(this, (String) res.get("msg"));
            } catch (Exception e4) {
                CommonActivity.toast(this, "Connection Problem");
            }
        }
    }

    public void viewresult(View v) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("id", true);
        startActivity(intent);
    }

    private Animation startBlicking(View v) {
        v.setBackgroundResource(17301508);
        v.getBackground().setColorFilter(Color.rgb(0, 191, 255), Mode.MULTIPLY);
        return null;
    }

    public void onBackPressed() {
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0539R.menu.exam, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == C0539R.id.screenshot) {
            onerror(null);
        }
        if (item.getItemId() == C0539R.id.showquestiond) {
            startActivity(new Intent(this, ShowQuestionsActivity.class));
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void onerror(View v) {
        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        saveBitmap(v1.getDrawingCache());
        sendMail(AppConstant.DB_PATH + "/screenshot.png");
    }

    public void sendMail(String path) {
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{AppConstant.server_mail});
        emailIntent.putExtra("android.intent.extra.SUBJECT", "manthan ERROR Mail");
        emailIntent.putExtra("android.intent.extra.TEXT", "This is an autogenerated mail from manthan app");
        emailIntent.setType("image/png");
        emailIntent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + path));
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void saveBitmap(Bitmap bitmap) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(AppConstant.DB_PATH + "/screenshot.png"));
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e2) {
            Log.e("GREC", e2.getMessage(), e2);
        }
    }

    public void onClick(View v) {
        if (!AppConstant.resultview) {
            RoundRectShape rect = new RoundRectShape(new float[]{100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f}, null, null);
            ShapeDrawable bg = new ShapeDrawable(rect);
            bg.setColorFilter(Color.rgb(0, 191, 255), Mode.MULTIPLY);
            bg.getPaint().setColor(Color.rgb(0, 191, 255));
            ((Button) findViewById(v.getId())).setBackgroundDrawable(bg);
            this.selected_button = v.getId();
            AppConstant.answered_string += AppConstant.qcount + ",";
            int user = ((Integer) AppConstant.user_ans.get(AppConstant.qcount)).intValue();
            if (user != this.selected_button) {
                Integer valueOf = Integer.valueOf(0);
                valueOf = Integer.valueOf(user);
                if (this.ButtonClear.length() > 1) {
                    valueOf = Integer.valueOf(Integer.parseInt(this.ButtonClear.split(",")[1]));
                }
                if (valueOf.intValue() > 0) {
                    RoundRectShape rect1 = new RoundRectShape(new float[]{100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 100.0f}, null, null);
                    ShapeDrawable bg1 = new ShapeDrawable(rect);
                    bg1.getPaint().setColor(-1);
                    ((Button) findViewById(valueOf.intValue())).setBackgroundDrawable(bg1);
                }
            }
        }
        next(null);
    }
}

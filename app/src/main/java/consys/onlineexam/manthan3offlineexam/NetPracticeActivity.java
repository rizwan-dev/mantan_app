package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.ExamModel;
import consys.onlineexam.helper.StudentModel;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.util.ArrayList;
import java.util.HashMap;

public class NetPracticeActivity extends Activity implements TaskListener {
    public static String value = new String();
    HashMap hm = new HashMap();
    HashMap result = null;
    TextView txtadd;
    TextView txtips;
    TextView txtsubject;

    /* renamed from: consys.onlineexam.manthan3offlineexam.NetPracticeActivity$2 */
    class C05362 implements OnClickListener {
        C05362() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            NetPracticeActivity.this.getname("Wrong user name \n Please enter correct user name");
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_net_practice);
        setAdvertise();
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

    public void showtest(View v) {
        if (v.getId() == C0539R.id.txttest1) {
            AppConstant.selected_test_Id = 51;
            getvalue();
        } else if (v.getId() == C0539R.id.txttest2) {
            AppConstant.selected_test_Id = 52;
            getvalue();
        }
    }

    public void getvalue() {
        AppConstant.student = new StudentModel();
        AppConstant.student.setName(value);
        AppConstant.negative_marks = 0.25f;
        HashMap req = new HashMap();
        req.put("method", "getquestionlist");
        req.put("examid", Integer.valueOf(AppConstant.selected_test_Id));
        req.put("table", "question");
        MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(this);
        HashMap j = new HashMap();
        m.execute(new HashMap[]{req});
    }

    public void onTaskCompleted(HashMap hm) {
        HashMap res = hm;
        try {
            if (((Boolean) res.get("flag")).booleanValue()) {
                AppConstant.qlist = (ArrayList) res.get("qlist");
                ExamModel e = (ExamModel) res.get("model");
                AppConstant.correct_ans = new ArrayList();
                AppConstant.user_ans = new ArrayList();
                AppConstant.selected_subject = "Sample Test";
                AppConstant.selected_test = "Sample Test";
                AppConstant.selected_exam1 = "Sample Test";
                AppConstant.selected_chapter = "Sample Test";
                AppConstant.total_questions = AppConstant.qlist.size();
                AppConstant.totalMarks = 40.0f;
                AppConstant.exam_time = 30.0f;
                AppConstant.resultview = false;
                AppConstant.solved_question = 0;
                if (AppConstant.qlist.size() > 0) {
                    AppConstant.total_questions = AppConstant.qlist.size();
                    CommonActivity.setMapDefault();
                    AppConstant.qcount = 0;
                    AppConstant.answered_string = new String(",");
                    AppConstant.attended_string = new String(",");
                    Intent intent = new Intent(this, ExamActivity.class);
                    AppConstant.r_flag = true;
                    startActivity(intent);
                    return;
                }
                return;
            }
            CommonActivity.toast(this, "Please Select Test");
        } catch (Exception e2) {
            System.out.println("Exception in NetPracticeActivity" + e2);
        }
    }

    public String getname(String msg) {
        Builder alert = new Builder(this);
        value = new String();
        alert.setTitle("User Name");
        alert.setMessage(msg);
        final EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Ok", new OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                NetPracticeActivity.value = input.getText().toString();
                if (NetPracticeActivity.value.equalsIgnoreCase(" ")) {
                    NetPracticeActivity.this.getname("Wrong user name \n Please enter correct user name");
                } else {
                    NetPracticeActivity.this.getvalue();
                }
            }
        });
        alert.setNegativeButton("Cancel", new C05362());
        alert.show();
        return value;
    }
}

package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.sun.mail.imap.IMAPStore;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.ExamModel;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import example.EventDataSQLHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TestListActivity extends Activity implements TaskListener {
    int Attempts;
    int Myattempts;
    ListView list_sel_sub;
    boolean setAnswerandPauseTest;
    String[] sub;

    /* renamed from: consys.onlineexam.manthan3offlineexam.TestListActivity$1 */
    class C05431 implements OnItemClickListener {
        C05431() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            float size = (float) TestListActivity.this.sub.length;
            ExamModel e = (ExamModel) AppConstant.elist.get(position);
            String checkFree = e.getIs_free();
            TestListActivity.this.Attempts = e.getAttempts();
            TestListActivity.this.Myattempts = e.getMyAttempts();
            float pos = size;
            pos = (size / 100.0f) * 100.0f;
            if (position < 3) {
                TestListActivity.this.gett(position);
            } else if (!CommonActivity.checkRegistration(TestListActivity.this)) {
                Intent intent = new Intent(TestListActivity.this, MainActivity.class);
                intent.putExtra("positionName", position);
                TestListActivity.this.startActivity(intent);
            } else if (TestListActivity.this.Attempts > TestListActivity.this.Myattempts) {
                TestListActivity.this.gett(position);
            } else {
                AppConstant.resultview = true;
                AppConstant.qcount = 0;
                TestListActivity.this.gett(position);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_list_tests);
        this.list_sel_sub = (ListView) findViewById(C0539R.id.listseltests);
        ((TextView) findViewById(C0539R.id.txtopt)).setText("सराव प्रश्नपत्रिका");
        getTests();
        this.setAnswerandPauseTest = false;
        new ArrayList().addAll(Arrays.asList(this.sub));
        this.list_sel_sub.setAdapter(new ListTestCustomList(this, this.sub));
        this.list_sel_sub.setOnItemClickListener(new C05431());
    }

    private void getTests() {
        this.sub = new String[AppConstant.elist.size()];
        for (int i = 0; i < AppConstant.elist.size(); i++) {
            this.sub[i] = ((ExamModel) AppConstant.elist.get(i)).getExam_name();
        }
    }

    public void onTaskCompleted(HashMap hm) {
        if (this.setAnswerandPauseTest) {
            ExamModel exm = new ExamModel();
            if (exm.getMillis() != 0) {
                int currntQueIndex = AppConstant.qlist.indexOf(Integer.valueOf(exm.getCurrentQuestion()));
                if (exm.getCurrentQuestion() != 0) {
                    AppConstant.qcount = currntQueIndex;
                }
                if (exm.getMillis() != 0) {
                    AppConstant.exam_time = (((float) exm.getMillis()) / 1000.0f) / 60.0f;
                }
                startActivity(new Intent(this, ExamActivity.class));
            } else if (AppConstant.resultview) {
                startActivity(new Intent(this, ExamActivity.class));
            } else {
                startActivity(new Intent(this, AgreementActivity.class));
            }
            this.setAnswerandPauseTest = false;
            return;
        }
        HashMap res = hm;
        try {
            if (((Boolean) res.get("flag")).booleanValue()) {
                AppConstant.qlist = (ArrayList) res.get("qlist");
                if (AppConstant.qlist.size() > 0) {
                    AppConstant.total_questions = AppConstant.qlist.size();
                    AppConstant.correct_ans = new ArrayList();
                    AppConstant.user_ans = new ArrayList();
                    CommonActivity.setMapDefault();
                    AppConstant.qcount = new Integer(0).intValue();
                    AppConstant.r_flag = true;
                    AppConstant.answered_string = new String(",");
                    AppConstant.attended_string = new String(",");
                    this.setAnswerandPauseTest = true;
                    HashMap req = new HashMap();
                    ExamModel e1 = new ExamModel();
                    req.put("method", "setPausedAndResumeCount");
                    req.put("tid", Integer.valueOf(AppConstant.selected_test_Id));
                    MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(this);
                    HashMap j = new HashMap();
                    m.execute(new HashMap[]{req});
                    return;
                }
                CommonActivity.toast(this, "Selected Test does not have any questions");
                return;
            }
            try {
                CommonActivity.toast(this, (String) res.get("msg"));
            } catch (Exception e) {
                CommonActivity.toast(this, "Connection Problem");
            }
        } catch (Exception e2) {
            try {
                CommonActivity.toast(this, (String) hm.get("msg"));
            } catch (Exception e3) {
                CommonActivity.toast(this, "Connection Problem");
            }
            System.out.println("Exception in TestListActivity" + e2);
        }
    }

    public void gett(int position) {
        ExamModel e = (ExamModel) AppConstant.elist.get(position);
        AppConstant.selected_test = new String(e.getExam_name());
        AppConstant.selected_test_Id = new Integer(e.getExam_id()).intValue();
        AppConstant.totalMarks = new Float((float) e.getMarks()).floatValue();
        System.out.println("hglkjdf" + e.getNegative());
        AppConstant.negative_marks = new Float(e.getNegative()).floatValue();
        AppConstant.exam_time = (float) Long.parseLong(e.getExam_duration());
        AppConstant.image_dir = new String(e.getImage_dir());
        HashMap req = new HashMap();
        if (AppConstant.selected_exam1.equalsIgnoreCase("Topicwise Exam")) {
            req.put("method", "getquestionlist");
            req.put("examid", Integer.valueOf(e.getExam_id()));
            req.put("table", "question");
        } else if (AppConstant.selected_exam1.equalsIgnoreCase("Practice Exam")) {
            req.put("method", "getquestionlist");
            req.put("examid", Integer.valueOf(e.getExam_id()));
            req.put("table", "practice_exam");
        } else {
            req.put(EventDataSQLHelper.TIME, e.getStart_Time());
            req.put(IMAPStore.ID_DATE, e.getExam_Date());
            req.put("method", "getlivequestionlist");
            req.put("examid", Integer.valueOf(e.getExam_id()));
        }
        MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(this);
        HashMap j = new HashMap();
        m.execute(new HashMap[]{req});
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomePage.class));
    }
}

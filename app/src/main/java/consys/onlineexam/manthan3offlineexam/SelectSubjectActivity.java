package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.anjlab.android.iab.v3.Constants;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.AppHelper;
import consys.onlineexam.helper.ServerConnection;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SelectSubjectActivity extends Activity implements TaskListener {
    private static final Context This = null;
    Integer[] imageId = new Integer[]{Integer.valueOf(C0539R.drawable.one1), Integer.valueOf(C0539R.drawable.two), Integer.valueOf(C0539R.drawable.livetest), Integer.valueOf(C0539R.drawable.stat)};
    Intent intent;
    private ArrayAdapter<String> listAdapter;
    ListView list_sel_sub;
    String[] sub = new String[]{"Topicwise Exam", "Practice Exam", "Live Exam", "My Statistics"};
    String subject;
    int test_flag = 1;

    /* renamed from: consys.onlineexam.manthan3offlineexam.SelectSubjectActivity$1 */
    class C05401 implements OnItemClickListener {
        C05401() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            HashMap req;
            MyAsynchTaskExecutor m;
            HashMap j;
            if (position == 0) {
                SelectSubjectActivity.this.test_flag = 1;
                AppConstant.selected_exam1 = "Topicwise Exam";
                AppConstant.selected_exam = "Topicwise Exam";
                req = new HashMap();
                req.put("method", "getsubjectlist");
                req.put("subject", AppConstant.selected_exam1);
                m = new MyAsynchTaskExecutor(SelectSubjectActivity.this);
                j = new HashMap();
                m.execute(new HashMap[]{req});
            } else if (position == 1) {
                SelectSubjectActivity.this.test_flag = 2;
                AppConstant.selected_exam1 = "Practice Exam";
                AppConstant.selected_exam = "Practice Exam";
                req = new HashMap();
                req.put("method", "getsubjectlist");
                req.put("subject", AppConstant.selected_exam1);
                req.put("chapterid", Integer.valueOf(8));
                AppConstant.selected_subject = "Practice Exam";
                AppConstant.selected_chapter = "Practice Exam";
                m = new MyAsynchTaskExecutor(SelectSubjectActivity.this);
                j = new HashMap();
                m.execute(new HashMap[]{req});
            } else if (position == 2) {
                if (ServerConnection.isConnectingToInternet(SelectSubjectActivity.this)) {
                    SelectSubjectActivity.this.test_flag = 2;
                    AppConstant.selected_exam1 = "Live_Exam";
                    AppConstant.selected_exam = "Live Test";
                    AppConstant.selected_subject = "Live Exam";
                    AppConstant.selected_chapter = "Live Test";
                    req = new HashMap();
                    req.put("method", "getlivetestlist");
                    req.put("examtype", AppConstant.selected_exam1);
                    req.put("chapterid", Integer.valueOf(7));
                    m = new MyAsynchTaskExecutor(SelectSubjectActivity.this);
                    j = new HashMap();
                    m.execute(new HashMap[]{req});
                    return;
                }
                CommonActivity.toast(SelectSubjectActivity.this, "Please Check Your Internet Connection & Try Again");
            } else if (position == 3) {
                SelectSubjectActivity.this.startActivity(new Intent(SelectSubjectActivity.this, MyStatActivity.class));
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_select_subject);
        this.list_sel_sub = (ListView) findViewById(C0539R.id.listselsub);
        this.intent = getIntent();
        if (this.intent.hasExtra("id")) {
            if (CommonActivity.checkRegistration(this)) {
                System.out.println("In SubjectSelct");
                insertflag(this.intent.getIntExtra("id", 0));
            } else {
                CommonActivity.toast(this, "Please register to continue using app");
                startActivity(new Intent(this, RegisterActivity.class));
            }
        }
        new ArrayList().addAll(Arrays.asList(this.sub));
        this.list_sel_sub.setAdapter(new SelectSubCustomList(this, this.sub, this.imageId));
        this.list_sel_sub.setOnItemClickListener(new C05401());
    }

    public void onTaskCompleted(HashMap hm) {
        HashMap res = hm;
        try {
            if (!((Boolean) res.get("flag")).booleanValue()) {
                try {
                    CommonActivity.toast(this, (String) hm.get("msg"));
                } catch (Exception e) {
                    CommonActivity.toast(this, "Connection Problem");
                }
            } else if (this.test_flag == 1) {
                System.out.println();
                AppConstant.sublist = (ArrayList) res.get("sublist");
                AppConstant.sub_exam_list_map = (HashMap) res.get("hm_exam_list");
                startActivity(new Intent(this, TestSubActivity.class));
            } else if (this.test_flag > 1) {
                AppConstant.elist = (ArrayList) res.get("elist");
                if (AppConstant.elist.size() > 0) {
                    startActivity(new Intent(this, TestListActivity.class));
                }
            }
        } catch (Exception e2) {
            System.out.println("Exception in SelectSubjectActivity" + e2);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0539R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == C0539R.id.mainmenuitem) {
            AppConstant.qcount = 1;
            AppConstant.exam_time = 0.0f;
            AppConstant.marks_percentage = 0.0f;
            AppConstant.obtainedMarks = 0.0f;
            AppConstant.qlist = new ArrayList();
            AppConstant.solved_question = 0;
            AppConstant.TIMEOUT = 0;
            AppConstant.total_questions = 0;
            AppConstant.elist = new ArrayList();
            startActivity(new Intent(this, MainMenuActivity.class));
        } else if (item.getItemId() == C0539R.id.exit) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.setFlags(67108864);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        } else if (item.getItemId() == C0539R.id.updates) {
            startActivity(new Intent(this, UpdateActivity.class));
        } else if (item.getItemId() == C0539R.id.aboutus1) {
            startActivity(new Intent(this, AboutActivity.class));
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

    public void onBackPressed() {
        if (!this.intent.hasExtra("result")) {
            super.onBackPressed();
        }
    }
}

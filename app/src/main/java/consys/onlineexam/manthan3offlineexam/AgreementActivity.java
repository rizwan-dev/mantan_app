package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.util.ArrayList;
import java.util.HashMap;

public class AgreementActivity extends Activity implements TaskListener {
    Button agree;
    Button notAgree;

    /* renamed from: consys.onlineexam.manthan3offlineexam.AgreementActivity$1 */
    class C05191 implements OnClickListener {
        C05191() {
        }

        public void onClick(View v) {
            AgreementActivity.this.startActivity(new Intent(AgreementActivity.this, ExamActivity.class));
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.AgreementActivity$2 */
    class C05202 implements OnClickListener {
        C05202() {
        }

        public void onClick(View v) {
            AppConstant.selected_exam1 = "Practice Exam";
            AppConstant.selected_exam = "Practice Exam";
            HashMap req = new HashMap();
            req.put("method", "getsubjectlist");
            req.put("subject", AppConstant.selected_exam1);
            req.put("chapterid", Integer.valueOf(255));
            AppConstant.selected_subject = "Practice Exam";
            AppConstant.selected_chapter = "Practice Exam";
            MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(AgreementActivity.this);
            HashMap j = new HashMap();
            m.execute(new HashMap[]{req});
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_agreed);
        this.agree = (Button) findViewById(C0539R.id.agreeBtn1);
        this.notAgree = (Button) findViewById(C0539R.id.notAgreeButton1);
        this.agree.setOnClickListener(new C05191());
        this.notAgree.setOnClickListener(new C05202());
    }

    public void onTaskCompleted(HashMap hm) {
        HashMap res = hm;
        System.out.println(hm);
        Boolean b = (Boolean) res.get("flag");
        AppConstant.elist = (ArrayList) res.get("elist");
        if (!b.booleanValue()) {
            Toast.makeText(this, "No Practice Test Available", 1).show();
        } else if (AppConstant.elist.size() > 0) {
            startActivity(new Intent(this, TestListActivity.class));
        }
    }
}

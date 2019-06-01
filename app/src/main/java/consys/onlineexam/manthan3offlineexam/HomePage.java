package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import consys.onlineexam.helper.AppConstant;

public class HomePage extends Activity {
    TextView enquiry;
    TextView liveTest;
    TextView ourProduct;
    TextView practiceExam;

    /* renamed from: consys.onlineexam.manthan3offlineexam.HomePage$1 */
    class C05241 implements OnClickListener {
        C05241() {
        }

        public void onClick(View v) {
            if (AppConstant.elist.size() > 0) {
                HomePage.this.startActivity(new Intent(HomePage.this, TestListActivity.class));
                return;
            }
            Toast.makeText(HomePage.this, "No Practice Test Available", 1).show();
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.HomePage$2 */
    class C05252 implements OnClickListener {
        C05252() {
        }

        public void onClick(View v) {
            HomePage.this.startActivity(new Intent(HomePage.this, LiveExamSchedule.class));
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.HomePage$3 */
    class C05263 implements OnClickListener {
        C05263() {
        }

        public void onClick(View v) {
            HomePage.this.startActivity(new Intent(HomePage.this, AboutActivity.class));
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.HomePage$4 */
    class C05274 implements OnClickListener {
        C05274() {
        }

        public void onClick(View v) {
            HomePage.this.startActivity(new Intent(HomePage.this, EnquiryForm.class));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_home);
        this.practiceExam = (TextView) findViewById(C0539R.id.practicetesttextview);
        this.liveTest = (TextView) findViewById(C0539R.id.liveexamTextView);
        this.ourProduct = (TextView) findViewById(C0539R.id.ourProductTextvi);
        this.enquiry = (TextView) findViewById(C0539R.id.enquiryTextv);
        this.practiceExam.setOnClickListener(new C05241());
        this.liveTest.setOnClickListener(new C05252());
        this.ourProduct.setOnClickListener(new C05263());
        this.enquiry.setOnClickListener(new C05274());
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(67108864);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}

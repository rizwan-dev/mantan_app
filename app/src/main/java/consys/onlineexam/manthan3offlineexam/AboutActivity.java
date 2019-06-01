package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.HashMap;

public class AboutActivity extends Activity implements TaskListener {
    AdRequest adRequest;
    private AdView mAdView;
    TextView txtabt;
    TextView txtcontact1;
    TextView txtcontact2;

    protected void onStart() {
        super.onStart();
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void gotoPlayStore(View c) {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_about);
    }

    public void onTaskCompleted(HashMap hm) {
    }
}

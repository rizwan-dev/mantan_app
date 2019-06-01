package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.AppHelper;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.util.ArrayList;
import java.util.HashMap;

public class UpdateActivity extends Activity implements TaskListener {
    private int _progress = 0;
    private ProgressDialog _progressDialog;
    private Handler _progressHandler;
    AdRequest adRequest;
    private AdView mAdView;
    TextView txtdispl;
    public boolean update_flag = false;

    /* renamed from: consys.onlineexam.manthan3offlineexam.UpdateActivity$1 */
    class C05441 extends Handler {
        C05441() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (UpdateActivity.this._progress >= 100) {
                UpdateActivity.this._progressDialog.dismiss();
                return;
            }
            UpdateActivity.this._progress = UpdateActivity.this._progress + 1;
            UpdateActivity.this._progressDialog.incrementProgressBy(1);
            UpdateActivity.this._progressHandler.sendEmptyMessageDelayed(0, 100);
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.UpdateActivity$2 */
    class C05452 implements OnClickListener {
        C05452() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            Toast.makeText(UpdateActivity.this.getBaseContext(), "Hide clicked!", 0).show();
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.UpdateActivity$3 */
    class C05463 implements OnClickListener {
        C05463() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            Toast.makeText(UpdateActivity.this.getBaseContext(), "Cancel clicked!", 0).show();
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
        setContentView(C0539R.layout.activity_updates);
        this.txtdispl = (TextView) findViewById(C0539R.id.txtdisplyupdates);
        if (!CommonActivity.checkRegistration(this)) {
            CommonActivity.toast(this, "Please register to continue using app");
            startActivity(new Intent(this, RegisterActivity.class));
        }
        if (AppConstant.updatelist.size() > 0) {
            this.txtdispl.setText(AppConstant.updatelist.size() + " new test available click update button  to update news tests ");
        }
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    public void runpro() {
        this._progressHandler = new C05441();
        showDialog(1);
        this._progress = 0;
        this._progressDialog.setProgress(0);
        this._progressHandler.sendEmptyMessage(0);
    }

    protected Dialog onCreateDialog(int i) {
        this._progressDialog = new ProgressDialog(this);
        this._progressDialog.setIcon(C0539R.drawable.ic_launcher);
        this._progressDialog.setTitle("Downloading updates...");
        this._progressDialog.setProgressStyle(1);
        this._progressDialog.setButton(-1, "Hide", new C05452());
        this._progressDialog.setButton(-2, "Cancel", new C05463());
        return this._progressDialog;
    }

    public void checkupdates(View v) {
        try {
            System.out.println("In getflags of tip notifications");
            HashMap hm1 = new HashMap();
            hm1.put("method", "checkflags");
            HashMap r = AppHelper.getValues(hm1, this);
            if (r != null) {
                HashMap hm = new HashMap();
                hm.put("method", "checkupdates");
                hm.put("exam_id", r.get("updateid"));
                this.update_flag = false;
                new MyAsynchTaskExecutor(this).execute(new HashMap[]{hm, null, null});
            }
        } catch (Exception e) {
        }
    }

    public void doupdates(View v) {
        if (AppConstant.updatelist.size() > 0) {
            HashMap hm = new HashMap();
            hm.put("method", "doupdates");
            hm.put("examlist", AppConstant.updatelist);
            this.update_flag = true;
            new MyAsynchTaskExecutor(this).execute(new HashMap[]{hm, null, null});
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainMenuActivity.class));
    }

    public void onTaskCompleted(HashMap hm) {
        try {
            if (!((Boolean) hm.get("flag")).booleanValue()) {
                this.txtdispl.setText("No new updates ");
                try {
                    CommonActivity.toast(this, (String) hm.get("msg"));
                } catch (Exception e) {
                    CommonActivity.toast(this, "Connection Problem");
                }
            } else if (this.update_flag) {
                this.txtdispl.setText("Updates done successfully ");
                startActivity(new Intent(this, MainMenuActivity.class));
            } else {
                AppConstant.updatelist = (ArrayList) hm.get("examlist");
                if (AppConstant.updatelist.size() > 0) {
                    int s = AppConstant.updatelist.size();
                    if (s > 0) {
                        this.txtdispl.setText(s + " new test available click update button  to update news tests ");
                    } else {
                        ((Button) findViewById(C0539R.id.btnupdate)).setVisibility(8);
                    }
                }
            }
        } catch (Exception e2) {
            System.out.println("Exception in UpdatesActiivty" + e2);
        }
    }
}

package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.anjlab.android.iab.v3.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.TipModel;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.util.HashMap;
import org.apache.commons.io.IOUtils;

public class TipsActivity extends Activity implements TaskListener {
    AdRequest adRequest;
    HashMap hm = new HashMap();
    ImageView imgView;
    LinearLayout layout;
    private AdView mAdView;
    HashMap result = null;
    TextView txtadd;
    TextView txtips;
    TextView txtsubject;

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
        setContentView(C0539R.layout.activity_tips);
        this.txtadd = (TextView) findViewById(C0539R.id.tipsads);
        CommonActivity.setAds(8, this.txtadd);
        this.txtips = (TextView) findViewById(C0539R.id.txttip);
        String str = "";
        for (int i = 0; i < AppConstant.tiplist.size(); i++) {
            TipModel t = (TipModel) AppConstant.tiplist.get(i);
            str = str + "\n\n" + t.getDisplay_time() + IOUtils.LINE_SEPARATOR_UNIX + t.getTip_content();
        }
        this.txtips.setText(str);
        this.layout = (LinearLayout) findViewById(C0539R.id.tips_layout);
        setTips();
        setAdvertise();
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainMenuActivity.class));
    }

    public void setTips() {
        int id = ((TipModel) AppConstant.tiplist.get(AppConstant.tiplist.size() - 1)).getTip_id();
        this.hm.put("method", "insertflag");
        this.hm.put("id", Integer.valueOf(id));
        this.hm.put(Constants.RESPONSE_TYPE, "tip");
        MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(this);
        HashMap j = new HashMap();
        m.execute(new HashMap[]{this.hm, null, null});
    }

    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
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

    public void onTaskCompleted(HashMap hm) {
        try {
            TextView txt_tips = new TextView(this);
            this.result = hm;
            Boolean b = (Boolean) this.result.get("flag");
            System.out.println(" tips flag =  " + b);
            if (b.booleanValue()) {
                String str = (String) this.result.get("tip");
            } else {
                CommonActivity.toast(this, "Connection Problem");
            }
        } catch (Exception e) {
            System.out.println("Exception in TipsActiivty" + e);
        }
    }
}

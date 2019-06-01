package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.util.HashMap;
import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class ShowStatActivity extends Activity implements TaskListener {
    AdRequest adRequest;
    int at;
    int average;
    int excellent;
    int good;
    private AdView mAdView;
    private String[] mMonth = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    int poor;
    int satisfactory;
    public TextView txtattend;
    public TextView txtaverage;
    public TextView txtexcellent;
    public TextView txtgood;
    public TextView txtpoor;
    public TextView txtsatisfactory;
    public TextView txtverygood;
    int verygood;

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_show_stat);
        ((TextView) findViewById(C0539R.id.titleshowmystat)).setText(AppConstant.selected_exam);
        this.txtattend = (TextView) findViewById(C0539R.id.txtattendtest);
        this.txtpoor = (TextView) findViewById(C0539R.id.txtpoor);
        this.txtaverage = (TextView) findViewById(C0539R.id.txtaverage);
        this.txtsatisfactory = (TextView) findViewById(C0539R.id.txtsatisfy);
        this.txtgood = (TextView) findViewById(C0539R.id.txtgood);
        this.txtverygood = (TextView) findViewById(C0539R.id.txtverygood);
        this.txtexcellent = (TextView) findViewById(C0539R.id.txtexcellent);
        getStat();
        setAdvertise();
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    private void setAdvertise() {
        TextView tx1 = (TextView) findViewById(C0539R.id.studmads2);
        TextView tx2 = (TextView) findViewById(C0539R.id.studmads3);
        TextView tx3 = (TextView) findViewById(C0539R.id.studmads4);
        TextView tx4 = (TextView) findViewById(C0539R.id.studmads5);
        CommonActivity.setAds(1, (TextView) findViewById(C0539R.id.studmads1));
        CommonActivity.setAds(2, tx1);
        CommonActivity.setAds(3, tx2);
        CommonActivity.setAds(4, tx3);
        CommonActivity.setAds(5, tx4);
        ViewFlipper mFlipper = (ViewFlipper) findViewById(C0539R.id.viewFlipperstudym);
        mFlipper.startFlipping();
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(this, C0539R.anim.push_up_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, C0539R.anim.push_up_in));
    }

    public void openChart(View v) {
        int i;
        String[] code = new String[]{"Poor", "Average", "Satisfactory", "Good", "Very good", "Excellent"};
        double[] distribution = new double[]{(double) this.poor, (double) this.average, (double) this.satisfactory, (double) this.good, (double) this.verygood, (double) this.excellent};
        int[] colors = new int[]{Color.rgb(255, 182, 193), -65281, -16776961, Color.rgb(51, 102, 153), Color.rgb(255, 153, 0), -16711936};
        CategorySeries distributionSeries = new CategorySeries(AppConstant.selected_exam);
        for (i = 0; i < distribution.length; i++) {
            distributionSeries.add(code[i], distribution[i]);
        }
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (i = 0; i < distribution.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }
        defaultRenderer.setChartTitle(AppConstant.selected_exam);
        defaultRenderer.setChartTitleTextSize(25.0f);
        defaultRenderer.setLabelsColor(Color.rgb(61, 89, 171));
        defaultRenderer.setZoomButtonsVisible(true);
        startActivity(ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries, defaultRenderer, AppConstant.selected_exam));
    }

    private void getStat() {
        HashMap hm = new HashMap();
        hm.put("method", "getstatitics");
        hm.put("examtype", AppConstant.selected_exam1);
        MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(this);
        HashMap j = new HashMap();
        m.execute(new HashMap[]{hm, null, null});
    }

    public void onTaskCompleted(HashMap hm) {
        try {
            if (((Boolean) hm.get("flag")).booleanValue()) {
                this.at = ((Integer) hm.get("attend")).intValue();
                this.poor = ((Integer) hm.get("poor")).intValue();
                this.average = ((Integer) hm.get("avgerage")).intValue();
                this.satisfactory = ((Integer) hm.get("satisfactory")).intValue();
                this.good = ((Integer) hm.get("good")).intValue();
                this.verygood = ((Integer) hm.get("verygood")).intValue();
                this.excellent = ((Integer) hm.get("excellent")).intValue();
                this.txtattend.setText(": " + this.at);
                this.txtpoor.setText(": " + this.poor);
                this.txtaverage.setText(": " + this.average);
                this.txtsatisfactory.setText(": " + this.satisfactory);
                this.txtgood.setText(": " + this.good);
                this.txtverygood.setText(": " + this.verygood);
                this.txtexcellent.setText(": " + this.excellent);
            }
        } catch (Exception e) {
            System.out.println("Exception in show stat" + e);
        }
    }
}

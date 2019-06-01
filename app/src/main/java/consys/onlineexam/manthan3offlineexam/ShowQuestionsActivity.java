package consys.onlineexam.manthan3offlineexam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.InputDeviceCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import consys.onlineexam.helper.AppConstant;
import java.util.HashMap;

public class ShowQuestionsActivity extends Activity implements TaskListener, OnClickListener {
    static boolean examSummary = false;
    RelativeLayout layout;
    ProgressDialog pro;

    @SuppressLint({"NewApi"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_show_questions);
        this.layout = (RelativeLayout) findViewById(C0539R.id.layoutquestions);
        setQuestionButtons();
    }

    private void setQuestionButtons() {
        LayoutParams layoutParams;
        this.pro = new ProgressDialog(this);
        this.pro.setMessage("Please wait...");
        this.pro.setProgressStyle(0);
        this.pro.setIndeterminate(true);
        this.pro.show();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int x = 180;
        int y = 5;
        int count_sel = 0;
        int count_t = 0;
        int i = 0;
        while (i < AppConstant.qlist.size()) {
            if (y > 340) {
                y = 5;
                x += 70;
            }
            Button btnTag = new Button(this);
            layoutParams = new RelativeLayout.LayoutParams(90, 70);
            layoutParams.leftMargin = y;
            layoutParams.topMargin = x;
            btnTag.setText(Integer.valueOf(i + 1).toString());
            btnTag.setTextSize(12.0f);
            btnTag.setId(i);
            btnTag.setBackgroundResource(17301508);
            btnTag.getBackground().setColorFilter(SupportMenu.CATEGORY_MASK, Mode.MULTIPLY);
            if (AppConstant.attended_string.contains("," + i + ",") && ((Integer) AppConstant.user_ans.get(i)).intValue() == 0) {
                btnTag.setBackgroundResource(17301508);
                btnTag.getBackground().setColorFilter(InputDeviceCompat.SOURCE_ANY, Mode.MULTIPLY);
                count_t++;
            }
            if (AppConstant.answered_string.contains("," + i + ",")) {
                btnTag.setBackgroundResource(17301508);
                btnTag.getBackground().setColorFilter(-16711936, Mode.MULTIPLY);
                count_sel++;
            }
            btnTag.setLayoutParams(layoutParams);
            btnTag.setOnClickListener(this);
            this.layout.addView(btnTag);
            y += 95;
            i++;
        }
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(60, 50);
        lp1.leftMargin = 5;
        lp1.topMargin = 10;
        View textView = new TextView(this);
        textView.setLayoutParams(lp1);
        textView.setBackgroundResource(17301508);
        textView.getBackground().setColorFilter(SupportMenu.CATEGORY_MASK, Mode.MULTIPLY);
        this.layout.addView(textView);
        textView = new TextView(this);
        layoutParams = new RelativeLayout.LayoutParams(200, 40);
        layoutParams.leftMargin = 65;
        layoutParams.topMargin = 15;
        textView.setText("Total :" + AppConstant.qlist.size());
        textView.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(60, 50);
        lp2.leftMargin = 5;
        lp2.topMargin = 70;
        textView = new TextView(this);
        textView.setLayoutParams(lp2);
        textView.setBackgroundResource(17301508);
        textView.getBackground().setColorFilter(InputDeviceCompat.SOURCE_ANY, Mode.MULTIPLY);
        this.layout.addView(textView);
        TextView Traverses = new TextView(this);
        layoutParams = new RelativeLayout.LayoutParams(200, 40);
        layoutParams.leftMargin = 65;
        layoutParams.topMargin = 75;
        Traverses.setText("Skipped :" + count_t);
        Traverses.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(60, 50);
        lp3.leftMargin = 5;
        lp3.topMargin = TransportMediator.KEYCODE_MEDIA_RECORD;
        TextView attcolor = new TextView(this);
        attcolor.setLayoutParams(lp3);
        attcolor.setBackgroundResource(17301508);
        attcolor.getBackground().setColorFilter(-16711936, Mode.MULTIPLY);
        this.layout.addView(attcolor);
        TextView att = new TextView(this);
        layoutParams = new RelativeLayout.LayoutParams(200, 40);
        layoutParams.leftMargin = 65;
        layoutParams.topMargin = 135;
        att.setText("Attended :" + count_sel);
        att.setLayoutParams(layoutParams);
        this.layout.addView(textView);
        this.layout.addView(Traverses);
        this.layout.addView(att);
        this.pro.dismiss();
    }

    public void onTaskCompleted(HashMap hm) {
        try {
            if (!((Boolean) hm.get("flag")).booleanValue()) {
            }
        } catch (Exception e) {
        }
    }

    public void onClick(View v) {
        AppConstant.qcount = Integer.valueOf(v.getId()).intValue();
        examSummary = true;
        startActivity(new Intent(this, ExamActivity.class));
    }
}

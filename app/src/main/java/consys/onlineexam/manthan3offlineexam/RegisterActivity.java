package consys.onlineexam.manthan3offlineexam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

@SuppressLint({"DefaultLocale"})
public class RegisterActivity extends Activity implements TaskListener {
    AdRequest adRequest;
    final CharSequence[] items = new CharSequence[]{" Internet ", " SMS "};
    int keyDel;
    AlertDialog levelDialog;
    private AdView mAdView;
    BroadcastReceiver mIntentReceiver;
    public ProgressDialog pro;
    boolean registrationSuccessful = false;
    public int select_radio = 3;
    EditText txtarea;
    EditText txtcity;
    EditText txtemail;
    EditText txtmono;
    EditText txtname;
    EditText txtpincode;
    EditText txtstate;
    public boolean uflag = false;
    public String umsg = null;

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
        setContentView(C0539R.layout.register);
        this.txtname = (EditText) findViewById(C0539R.id.txtnameNew);
        this.txtemail = (EditText) findViewById(C0539R.id.txtemailNew);
        this.txtmono = (EditText) findViewById(C0539R.id.txtmonoNew);
        this.txtarea = (EditText) findViewById(C0539R.id.txtareaNew);
        this.txtcity = (EditText) findViewById(C0539R.id.txtcityNew);
        this.txtstate = (EditText) findViewById(C0539R.id.txtstateNew);
        this.txtpincode = (EditText) findViewById(C0539R.id.txtpincodeNew);
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    public void register(View v) {
        if (v.getId() == C0539R.id.reset) {
            this.txtname.setText("");
            this.txtemail.setText("");
            this.txtmono.setText("");
            this.txtarea.setText("");
            this.txtcity.setText("");
            this.txtstate.setText("");
            this.txtpincode.setText("");
        }
        if (v.getId() == C0539R.id.btnregister && validate()) {
            doOpt();
        }
    }

    public void doOpt() {
        String data = "3R?" + this.txtname.getText().toString() + "?" + this.txtmono.getText().toString() + "?" + CommonActivity.getimei(this) + "?" + "1234-1234-1234" + "?" + this.txtemail.getText().toString() + "?" + this.txtarea.getText().toString() + "?" + this.txtcity.getText().toString() + "?" + this.txtstate.getText().toString() + "?" + this.txtpincode.getText().toString();
        this.pro = new ProgressDialog(this);
        this.pro.setMessage("Please wait...");
        this.pro.setProgressStyle(0);
        this.pro.setIndeterminate(true);
        this.pro.setCancelable(true);
        this.pro.show();
        HashMap hm = new HashMap();
        hm.put("method", "saveRegister");
        hm.put("fname", this.txtname.getText().toString());
        hm.put("email", this.txtemail.getText().toString());
        hm.put("phone", this.txtmono.getText().toString());
        hm.put("area", this.txtarea.getText().toString());
        hm.put("city", this.txtcity.getText().toString());
        hm.put("state", this.txtstate.getText().toString());
        hm.put("pincode", this.txtpincode.getText().toString());
        hm.put("dataS", data);
        new MyAsynchTaskExecutor(this).execute(new HashMap[]{hm});
    }

    protected void NetworkOperation(String message) {
        List<NameValuePair> qparams = new ArrayList();
        qparams.add(new BasicNameValuePair("msg", message));
        System.out.println(qparams);
        String url = "http://www.consistentsystem.com/IBPSSMSReceiver.php?" + URLEncodedUtils.format(qparams, "UTF-8");
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    stringBuilder.append(line + IOUtils.LINE_SEPARATOR_UNIX);
                } else {
                    System.out.println("Response from server is" + stringBuilder);
                    verifyMsg(stringBuilder.toString());
                    return;
                }
            }
        } catch (Exception e) {
            CommonActivity.toast(this, "Please check your internet connection and try again !");
            Log.e("SMS-GATEWAY", "HTTP REQ FAILED:" + url);
            e.printStackTrace();
        }
    }

    @SuppressLint({"DefaultLocale"})
    public boolean validate() {
        System.out.println("in register");
        if (this.txtname.getText().toString().equalsIgnoreCase("")) {
            CommonActivity.toast(this, "Enter name");
            this.txtname.requestFocus();
            return false;
        } else if (!numberCheck(this.txtname.getText().toString())) {
            CommonActivity.toast(this, "name should be alphabatical");
            this.txtname.setText("");
            this.txtname.requestFocus();
            return false;
        } else if (this.txtname.getText().toString().contains("?")) {
            CommonActivity.toast(this, "Enter correct name");
            this.txtname.setText("");
            this.txtname.requestFocus();
            return false;
        } else if (this.txtmono.getText().toString().equalsIgnoreCase("")) {
            CommonActivity.toast(this, "Please Enter a Mobile Number");
            this.txtmono.requestFocus();
            return false;
        } else if (alphaCheck(this.txtmono.getText().toString().trim().charAt(0)) && numberCheck(this.txtmono.getText().toString()) && !isMobNo(this.txtmono.getText().toString().trim())) {
            CommonActivity.toast(this, "Please enter a valid Mobile Number");
            this.txtmono.setText("");
            this.txtmono.requestFocus();
            return false;
        } else if (this.txtmono.getText().toString().trim().length() < 10) {
            CommonActivity.toast(this, "Please enter a valid Mobile Number");
            this.txtmono.setText("");
            this.txtmono.requestFocus();
            return false;
        } else if (this.txtmono.getText().toString().trim().length() > 10) {
            CommonActivity.toast(this, "Please enter correct mo no");
            this.txtmono.setText("");
            this.txtmono.requestFocus();
            return false;
        } else if (this.txtarea.getText().toString().equalsIgnoreCase("")) {
            CommonActivity.toast(this, "Enter area");
            this.txtarea.setText("");
            this.txtarea.requestFocus();
            return false;
        } else if (this.txtarea.getText().toString().contains("?")) {
            CommonActivity.toast(this, "Enter correct area");
            this.txtarea.setText("");
            this.txtarea.requestFocus();
            return false;
        } else if (!alphaCheck(this.txtarea.getText().toString().trim().charAt(0))) {
            CommonActivity.toast(this, "First Character should be alphabate in area");
            this.txtarea.setText("");
            this.txtarea.requestFocus();
            return false;
        } else if (this.txtcity.getText().toString().equalsIgnoreCase("")) {
            CommonActivity.toast(this, "Enter city");
            this.txtcity.requestFocus();
            return false;
        } else if (!alphaCheck(this.txtcity.getText().toString().trim().charAt(0))) {
            CommonActivity.toast(this, "First Character should be alphabate in city");
            this.txtcity.setText("");
            this.txtcity.requestFocus();
            return false;
        } else if (this.txtcity.getText().toString().contains("?")) {
            CommonActivity.toast(this, "Enter correct city");
            this.txtcity.setText("");
            this.txtcity.requestFocus();
            return false;
        } else if (this.txtstate.getText().toString().equalsIgnoreCase("")) {
            CommonActivity.toast(this, "Enter State");
            this.txtstate.requestFocus();
            return false;
        } else if (this.txtstate.getText().toString().contains("?")) {
            CommonActivity.toast(this, "Enter correct State");
            this.txtstate.setText("");
            this.txtstate.requestFocus();
            return false;
        } else if (alphaCheck(this.txtstate.getText().toString().trim().charAt(0))) {
            return true;
        } else {
            CommonActivity.toast(this, "First Character should be alphabate in state");
            this.txtstate.setText("");
            this.txtstate.requestFocus();
            return false;
        }
    }

    public static boolean isMobNo(String str) {
        if (Pattern.compile("\\d{10}").matcher(str).find()) {
            return true;
        }
        return false;
    }

    public boolean numberCheck(String str) {
        if (str.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
            return true;
        }
        return false;
    }

    public boolean alphaCheck(char g) {
        char f = g;
        if ((f >= 'A' && f <= 'Z') || (f >= 'a' && f <= 'z')) {
            return true;
        }
        System.out.println("should start with an character");
        return false;
    }

    public boolean alphaNumCheck(String st) {
        st = st.replaceAll("-", "");
        for (int i = 0; i < st.length(); i++) {
            int f = st.charAt(i);
            System.out.println("first char is" + f);
            if ((f < 65 || f > 90) && ((f < 97 || f > 122) && (f < 48 || f > 57))) {
                return false;
            }
            System.out.println("should start with an character");
        }
        return true;
    }

    public void onTaskCompleted(HashMap hm) {
        if (this.registrationSuccessful) {
            HashMap res = hm;
            System.out.println(hm);
            if (((Boolean) res.get("flag")).booleanValue()) {
                AppConstant.elist = (ArrayList) res.get("elist");
                startActivity(new Intent(this, HomePage.class));
                return;
            }
            Toast.makeText(this, "No Practice Test Available", 1).show();
            return;
        }
        try {
            if (((Boolean) hm.get("flag")).booleanValue()) {
                verifyMsg(null);
            }
        } catch (Exception e) {
            System.out.println("Exception in WelcomeActivity");
        }
    }

    public void verifyMsg(String serverString) {
        this.pro.dismiss();
        CommonActivity.toast(this, "Thank You\nWish You All The Best");
        CommonActivity.SendSMS("Thank you for using Manthan App-" + this.txtname.getText().toString() + "-" + this.txtmono.getText().toString() + "-" + this.txtarea.getText().toString() + "-" + this.txtcity.getText().toString() + "-" + this.txtstate.getText().toString());
        AppConstant.selected_exam1 = "Practice Exam";
        HashMap req = new HashMap();
        req.put("method", "getsubjectlist");
        req.put("subject", AppConstant.selected_exam1);
        req.put("chapterid", Integer.valueOf(255));
        AppConstant.selected_subject = "Practice Exam";
        AppConstant.selected_chapter = "Practice Exam";
        MyAsynchTaskExecutor m = new MyAsynchTaskExecutor(this);
        HashMap j = new HashMap();
        m.execute(new HashMap[]{req});
        CommonActivity.writeString("R");
        this.registrationSuccessful = true;
    }
}

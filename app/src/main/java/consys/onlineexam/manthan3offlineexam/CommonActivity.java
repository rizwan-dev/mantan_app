package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.nearby.messages.Strategy;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.AppHelper;
import consys.onlineexam.helper.ExamModel;
import consys.onlineexam.helper.SecureManager;
import consys.onlineexam.helper.TelephonyInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class CommonActivity extends Activity {
    Context ctx = this;

    class CustomDialogClass1 extends Dialog implements OnClickListener {
        /* renamed from: c */
        public Activity f4c;
        /* renamed from: d */
        public Dialog f5d;
        public Button no;
        public Button yes;

        public CustomDialogClass1(Activity a) {
            super(a);
            this.f4c = a;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(1);
            setContentView(C0539R.layout.custom_diolog_menu);
            this.yes = (Button) findViewById(C0539R.id.btn_exit1_yes);
            this.no = (Button) findViewById(C0539R.id.btn_exit1_no);
            this.yes.setOnClickListener(this);
            this.no.setOnClickListener(this);
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case C0539R.id.btn_exit1_yes:
                    CommonActivity.this.startActivity(new Intent(CommonActivity.this, MainMenuActivity.class));
                    break;
                case C0539R.id.btn_exit1_no:
                    dismiss();
                    break;
            }
            dismiss();
        }
    }

    class CustomDialogClass extends Dialog implements OnClickListener {
        /* renamed from: c */
        public Activity f6c;
        /* renamed from: d */
        public Dialog f7d;
        public Button no;
        public Button yes;

        public CustomDialogClass(Activity a) {
            super(a);
            this.f6c = a;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(1);
            setContentView(C0539R.layout.custom_dialog_back_button);
            this.yes = (Button) findViewById(C0539R.id.btn_yes);
            this.no = (Button) findViewById(C0539R.id.btn_no);
            this.yes.setOnClickListener(this);
            this.no.setOnClickListener(this);
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case C0539R.id.btn_yes:
                    CommonActivity.this.logout();
                    break;
                case C0539R.id.btn_no:
                    dismiss();
                    break;
            }
            dismiss();
        }
    }

    public static class MyAsynchTaskExecutor extends AsyncTask<HashMap, HashMap, HashMap> {
        ProgressDialog pro;
        HashMap result;
        public TaskListener task;

        public MyAsynchTaskExecutor(TaskListener t) {
            this.task = t;
            if (!(this.task instanceof MainMenuActivity)) {
                this.pro = new ProgressDialog((Context) t);
                this.pro.setMessage("Please wait...");
                this.pro.setProgressStyle(0);
                this.pro.setIndeterminate(true);
                this.pro.show();
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected HashMap doInBackground(HashMap... args) {
            this.result = AppHelper.getValues(args[0], (Context) this.task);
            return null;
        }

        protected void onPostExecute(HashMap r) {
            if (!(this.task instanceof MainMenuActivity)) {
                this.pro.dismiss();
            }
            this.task.onTaskCompleted(this.result);
            super.onPostExecute(this.result);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void toast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Strategy.TTL_SECONDS_DEFAULT).show();
    }

    public static void SendSMS(String data) {
        SmsManager smsManager = SmsManager.getDefault();
        System.out.println(AppConstant.Server_Phone_No);
        smsManager.sendTextMessage(AppConstant.Server_Phone_No, null, data, null, null);
    }

    public void onBackPressed() {
        new CustomDialogClass(this).show();
    }

    public void logout() {
        ExamModel ex = new ExamModel();
        ex.setMillis(0);
        ex.setCurrentQuestion(0);
        AppConstant.qcount = 1;
        AppConstant.exam_time = 0.0f;
        AppConstant.marks_percentage = 0.0f;
        AppConstant.obtainedMarks = 0.0f;
        AppConstant.qlist.clear();
        AppConstant.elist.clear();
        AppConstant.qlist = new ArrayList();
        AppConstant.resultview = false;
        AppConstant.solved_question = 0;
        AppConstant.TIMEOUT = 0;
        AppConstant.total_questions = 0;
        AppConstant.elist = new ArrayList();
        AppConstant.user_ans = new ArrayList();
    }

    public static void setMapDefault() {
        for (int i = 0; i < AppConstant.qlist.size(); i++) {
            new ArrayList().add(Integer.valueOf(0));
            AppConstant.user_ans.add(Integer.valueOf(0));
            AppConstant.correct_ans.add(Integer.valueOf(0));
        }
    }

    public static void setAds(int i, View v) {
        String str = new String();
        if (AppConstant.adlist.size() > 0) {
            Collections.shuffle(AppConstant.adlist);
            try {
                if (i >= AppConstant.adlist.size()) {
                    while (i > AppConstant.adlist.size() - 1) {
                        i--;
                    }
                    str = (String) AppConstant.adlist.get(i);
                } else {
                    str = (String) AppConstant.adlist.get(i);
                }
                TextView tx = (TextView) v;
                tx.setText(str);
                if (str.length() <= 40) {
                    Animation animation = new AlphaAnimation(1.0f, 0.0f);
                    animation.setDuration(500);
                    animation.setInterpolator(new LinearInterpolator());
                    animation.setRepeatCount(-1);
                    animation.setRepeatMode(2);
                    tx.startAnimation(animation);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getHTML(String data) {
        try {
            if (AppConstant.selected_exam1.equalsIgnoreCase("Live_Exam")) {
                return data;
            }
            System.out.println("Image path" + AppConstant.image_dir);
            if (AppConstant.image_dir == null) {
                AppConstant.image_dir = "inner";
            }
            if (AppConstant.image_dir.equalsIgnoreCase("inner")) {
                data = data.replaceAll("src=\"", "src='file:///android_asset/manthan/");
            } else {
                data = data.replaceAll("src=\"", "src='file://" + AppConstant.DB_PATH + "system_manthan/");
            }
            return "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" ?>" + "<html" + "<head><meta http-equiv=\\\"content-type\\\" content=\\\"text/html; charset=utf-8\\\" /><style>body{\tline-height: 0%;\t}\nhtml{\tline-height: 0%;\t}\np{\tline-height: 150%;\t}\ntd{\tline-height: 150%;\t}\nimg{margin-left: 2px; margin-right: 2px; margin-top: 2px; margin-bottom: 7px;}\n@font-face {font-family: 'DV-TTYogesh';src: local('DV-TTYogesh'), url('file:///android_asset/font/DVYG0BTT.TTF')     format('truetype'),\t\t\t\turl('file:///android_asset/font/DVYG0ITT.TTF')     format('truetype'),\t\t\t\turl('file:///android_asset/font/DVYG0NTT.TTF')     format('truetype'),\t\t\t\turl('file:///android_asset/font/DVYG0XTT.TTF')     format('truetype'),\t\t\t\turl('file:///android_asset/font/DVYG0BTT.TTF')     format('truetype'),\t\t\t\turl('file:///android_asset/font/DVYG3XTT.TTF')     format('truetype'),\t\t\t\turl('file:///android_asset/font/DVYG3ITT.TTF')     format('truetype'),\t\t\t\turl('file:///android_asset/font/DVYG3NTT.TTF')     format('truetype');font-weight: normal;    font-style: normal;    }\t@font-face {    font-family: 'Kruti-Dev-030';        src: local('Kruti-Dev-030'),                 url('file:///android_asset/font/K30.TTF')     format('truetype');      }@font-face {    font-family: 'SHREE-DEV-0708';        src: local('SHREE-DEV-0708'),                 url('file:///android_asset/font/SHREL708.TTF')     format('truetype');      }body {font-family: 'DV-TTYogesh,Kruti-Dev-030,SHREE-DEV-0708,Verdana, Arial, sans-serif;}</style></head>" + "<body style='word-wrap:break-word;'>" + data.replaceAll(".jpg\"", ".jpg'").replaceAll(".JPG\"", ".JPG'").replaceAll(".png\"", ".png'").replaceAll(".PNG\"", ".PNG'").replaceAll(".bmp\"", ".bmp'").replaceAll(".BMP\"", ".BMP'").replaceAll(".gif\"", ".gif'") + "</body></html>";
        } catch (Exception e) {
            System.out.println("Exception in converting to HTML" + e.getMessage());
            return data;
        }
    }

    public static String get_9_imei(Context xt) {
        String system_imei = getimei(xt);
        String first_9_imei = "";
        int i = 3;
        while (i < system_imei.length() && i != 12) {
            first_9_imei = first_9_imei + system_imei.charAt(i);
            i++;
        }
        return first_9_imei;
    }

    public static String getimei(Object obj) {
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance((Context) obj);
        String imeiSIM1 = telephonyInfo.getImeiSIM1();
        String imeiSIM2 = telephonyInfo.getImeiSIM2();
        boolean isSIM1Ready = telephonyInfo.isSIM1Ready();
        boolean isSIM2Ready = telephonyInfo.isSIM2Ready();
        boolean isDualSIM = telephonyInfo.isDualSIM();
        return imeiSIM1 + "3rdMar";
    }

    public static void writeStringAsFile(String fileContents) {
        try {
            FileWriter out = new FileWriter(new File(AppConstant.file_name));
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
        }
    }

    public static void writeString(String fileContents) {
        try {
            FileWriter out = new FileWriter(new File(AppConstant.file));
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
        }
    }

    public static String readString() {
        BufferedReader bufferedReader;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(AppConstant.file)), 8192);
            while (true) {
                try {
                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }
                    stringBuilder.append(line);
                } catch (FileNotFoundException e) {
                    bufferedReader = in;
                } catch (IOException e2) {
                    bufferedReader = in;
                }
            }
        } catch (FileNotFoundException e3) {
        } catch (IOException e4) {
        }
        return stringBuilder.toString();
    }

    public static String readFileAsString() {
        BufferedReader bufferedReader;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(AppConstant.file_name)), 8192);
            while (true) {
                try {
                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }
                    stringBuilder.append(line);
                } catch (FileNotFoundException e) {
                    bufferedReader = in;
                } catch (IOException e2) {
                    bufferedReader = in;
                }
            }
        } catch (FileNotFoundException e3) {
        } catch (IOException e4) {
        }
        return stringBuilder.toString();
    }

    public static boolean checkRegistration(Context xt) {
        String imei = SecureManager.getHASH(getimei(xt));
        String filedata = SecureManager.decrypt(AppConstant.key, readFileAsString());
        if (filedata == null || !filedata.equalsIgnoreCase(imei)) {
            return false;
        }
        return true;
    }

    public static void addContact(Context ctx, String displayname, String homenumber, String mobilenumber, String worknumber, String homeemail, String workemail, String companyname, String jobtitle) {
        String DisplayName = displayname;
        String MobileNumber = mobilenumber;
        String HomeNumber = homenumber;
        String WorkNumber = worknumber;
        String homeemailID = homeemail;
        String workemailID = workemail;
        String company = companyname;
        String jobTitle = jobtitle;
        ArrayList<ContentProviderOperation> contentProviderOperation = new ArrayList();
        contentProviderOperation.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI).withValue("account_type", null).withValue("account_name", null).build());
        if (DisplayName != null) {
            contentProviderOperation.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/name").withValue("data1", DisplayName).build());
        }
        if (MobileNumber != null) {
            contentProviderOperation.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/phone_v2").withValue("data1", MobileNumber).withValue("data2", Integer.valueOf(2)).build());
        }
        if (HomeNumber != null) {
            contentProviderOperation.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/phone_v2").withValue("data1", HomeNumber).withValue("data2", Integer.valueOf(1)).build());
        }
        if (WorkNumber != null) {
            contentProviderOperation.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/phone_v2").withValue("data1", WorkNumber).withValue("data2", Integer.valueOf(3)).build());
        }
        if (workemailID != null) {
            contentProviderOperation.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/email_v2").withValue("data1", workemailID).withValue("data2", Integer.valueOf(2)).build());
        }
        if (homeemailID != null) {
            contentProviderOperation.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/email_v2").withValue("data1", homeemailID).withValue("data2", Integer.valueOf(1)).build());
        }
        if (!(company.equals("") || jobTitle.equals(""))) {
            contentProviderOperation.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/organization").withValue("data1", company).withValue("data2", Integer.valueOf(1)).withValue("data4", jobTitle).withValue("data2", Integer.valueOf(1)).build());
        }
        try {
            ctx.getContentResolver().applyBatch("com.android.contacts", contentProviderOperation);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ctx, "Exception: " + e.getMessage(), 0).show();
        }
    }

    public static String getCurrentTimeStamp() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

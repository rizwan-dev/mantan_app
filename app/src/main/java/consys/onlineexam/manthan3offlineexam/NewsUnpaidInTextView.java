package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

public class NewsUnpaidInTextView extends Activity {
    private String HTML;
    public String HTML1;
    AdRequest adRequest;
    BufferedReader bufferedReader = null;
    private AdView mAdView;
    Button newsReload;
    private TextView outtext;
    String read_news_file_path = AppConstant.news_data;

    /* renamed from: consys.onlineexam.manthan3offlineexam.NewsUnpaidInTextView$1 */
    class C05381 implements OnClickListener {
        C05381() {
        }

        public void onClick(View v) {
            NewsUnpaidInTextView.this.refreshButton();
            System.out.println("return value new page");
            NewsUnpaidInTextView.this.outtext.setText(Html.fromHtml(NewsUnpaidInTextView.this.HTML));
        }
    }

    public class MyAsynchTask1 extends AsyncTask<NewsUnpaidInTextView, NewsUnpaidInTextView, String> {
        ProgressDialog prog;

        protected void onPreExecute() {
            this.prog = new ProgressDialog(NewsUnpaidInTextView.this);
            this.prog.setMessage("Updating the Jobs list..");
            this.prog.setIndeterminate(false);
            this.prog.setProgressStyle(0);
            this.prog.setCancelable(false);
            this.prog.show();
            super.onPreExecute();
        }

        protected String doInBackground(NewsUnpaidInTextView... params) {
            NewsUnpaidInTextView.this.downloadFromServer();
            this.prog.dismiss();
            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("done");
            if (NewsUnpaidInTextView.this.HTML != null) {
                NewsUnpaidInTextView.this.outtext.setText(Html.fromHtml(NewsUnpaidInTextView.this.HTML));
            }
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

    public void onResume() {
        super.onResume();
        if (this.mAdView != null) {
            this.mAdView.resume();
        }
    }

    public void onDestroy() {
        if (this.mAdView != null) {
            this.mAdView.destroy();
        }
        super.onDestroy();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.newstextview);
        this.outtext = (TextView) findViewById(C0539R.id.NewsTextV1);
        this.newsReload = (Button) findViewById(C0539R.id.imageReload);
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
        this.newsReload.setOnClickListener(new C05381());
        try {
            getHTML();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.HTML != null) {
            this.outtext.setText(Html.fromHtml(this.HTML));
        }
    }

    private String getHTML() {
        try {
            this.bufferedReader = new BufferedReader(new FileReader(new File(this.read_news_file_path)));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        StringBuffer result = new StringBuffer();
        while (true) {
            try {
                String inputLine = this.bufferedReader.readLine();
                if (inputLine == null) {
                    break;
                }
                result.append(inputLine);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        this.bufferedReader.close();
        String FreeTitle = "";
        int i = 0;
        StringBuffer result2 = result;
        while (result2 != null) {
            if (result2.indexOf("<h4>", i) != -1) {
                FreeTitle = FreeTitle + "<br/>" + "<hr/>" + result2.substring(result2.indexOf("<h4>", i), result2.indexOf("</h4>", i) + 5);
                result = new StringBuffer(result2.substring(result2.indexOf("</h4>", i) + 5));
            } else {
                result = null;
            }
            i = 0;
            result2 = result;
        }
        this.HTML = FreeTitle;
        return FreeTitle;
    }

    public void refreshButton() {
        new MyAsynchTask1().execute(new NewsUnpaidInTextView[0]);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public String downloadFromServer() {
        /*
        r19 = this;
        r14 = 0;
        r4 = "";
        r2 = "";
        r6 = consys.onlineexam.helper.AppConstant.serverURLForNews;
        r15 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x00f6, ProtocolException -> 0x0083, IOException -> 0x0088 }
        r15.<init>(r6);	 Catch:{ MalformedURLException -> 0x00f6, ProtocolException -> 0x0083, IOException -> 0x0088 }
        r16 = r15.openConnection();	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        r10 = new java.io.BufferedReader;	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        r17 = new java.io.InputStreamReader;	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        r18 = r16.getInputStream();	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        r17.<init>(r18);	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        r0 = r17;
        r10.<init>(r0);	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
    L_0x0020:
        r11 = r10.readLine();	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        if (r11 == 0) goto L_0x003c;
    L_0x0026:
        r17 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        r17.<init>();	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        r0 = r17;
        r17 = r0.append(r4);	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        r0 = r17;
        r17 = r0.append(r11);	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        r4 = r17.toString();	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        goto L_0x0020;
    L_0x003c:
        r10.close();	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        r3 = new java.io.FileWriter;	 Catch:{ IOException -> 0x0078, MalformedURLException -> 0x007d, ProtocolException -> 0x00f3 }
        r17 = new java.io.File;	 Catch:{ IOException -> 0x0078, MalformedURLException -> 0x007d, ProtocolException -> 0x00f3 }
        r18 = consys.onlineexam.helper.AppConstant.news_data;	 Catch:{ IOException -> 0x0078, MalformedURLException -> 0x007d, ProtocolException -> 0x00f3 }
        r17.<init>(r18);	 Catch:{ IOException -> 0x0078, MalformedURLException -> 0x007d, ProtocolException -> 0x00f3 }
        r0 = r17;
        r3.<init>(r0);	 Catch:{ IOException -> 0x0078, MalformedURLException -> 0x007d, ProtocolException -> 0x00f3 }
        r17 = r4.toString();	 Catch:{ IOException -> 0x0078, MalformedURLException -> 0x007d, ProtocolException -> 0x00f3 }
        r0 = r17;
        r3.write(r0);	 Catch:{ IOException -> 0x0078, MalformedURLException -> 0x007d, ProtocolException -> 0x00f3 }
        r3.flush();	 Catch:{ IOException -> 0x0078, MalformedURLException -> 0x007d, ProtocolException -> 0x00f3 }
        r3.close();	 Catch:{ IOException -> 0x0078, MalformedURLException -> 0x007d, ProtocolException -> 0x00f3 }
    L_0x005c:
        r14 = r15;
    L_0x005d:
        r17 = consys.onlineexam.manthan3offlineexam.CommonActivity.checkRegistration(r19);
        if (r17 == 0) goto L_0x008d;
    L_0x0063:
        r12 = new android.content.Intent;
        r17 = consys.onlineexam.manthan3offlineexam.NewsPaidUser.class;
        r0 = r19;
        r1 = r17;
        r12.<init>(r0, r1);
        r0 = r19;
        r0.startActivity(r12);
    L_0x0073:
        r0 = r19;
        r0.HTML = r2;
        return r2;
    L_0x0078:
        r7 = move-exception;
        r7.printStackTrace();	 Catch:{ MalformedURLException -> 0x007d, ProtocolException -> 0x00f3, IOException -> 0x00f0 }
        goto L_0x005c;
    L_0x007d:
        r7 = move-exception;
        r14 = r15;
    L_0x007f:
        r7.printStackTrace();
        goto L_0x005d;
    L_0x0083:
        r7 = move-exception;
    L_0x0084:
        r7.printStackTrace();
        goto L_0x005d;
    L_0x0088:
        r7 = move-exception;
    L_0x0089:
        r7.printStackTrace();
        goto L_0x005d;
    L_0x008d:
        r9 = 0;
        r5 = r4;
    L_0x008f:
        if (r5 == 0) goto L_0x00f8;
    L_0x0091:
        r17 = "<h4>";
        r0 = r17;
        r17 = r5.indexOf(r0, r9);
        r18 = -1;
        r0 = r17;
        r1 = r18;
        if (r0 == r1) goto L_0x00ee;
    L_0x00a1:
        r17 = "<h4>";
        r0 = r17;
        r13 = r5.indexOf(r0, r9);
        r17 = "</h4>";
        r0 = r17;
        r17 = r5.indexOf(r0, r9);
        r8 = r17 + 5;
        r17 = new java.lang.StringBuilder;
        r17.<init>();
        r0 = r17;
        r17 = r0.append(r2);
        r18 = "<br/>";
        r17 = r17.append(r18);
        r18 = "<hr/>";
        r17 = r17.append(r18);
        r18 = r5.substring(r13, r8);
        r17 = r17.append(r18);
        r2 = r17.toString();
        r17 = "</h4>";
        r0 = r17;
        r17 = r5.indexOf(r0, r9);
        r9 = r17 + 5;
        r4 = new java.lang.String;
        r17 = r5.substring(r9);
        r0 = r17;
        r4.<init>(r0);
    L_0x00eb:
        r9 = 0;
        r5 = r4;
        goto L_0x008f;
    L_0x00ee:
        r4 = 0;
        goto L_0x00eb;
    L_0x00f0:
        r7 = move-exception;
        r14 = r15;
        goto L_0x0089;
    L_0x00f3:
        r7 = move-exception;
        r14 = r15;
        goto L_0x0084;
    L_0x00f6:
        r7 = move-exception;
        goto L_0x007f;
    L_0x00f8:
        r4 = r5;
        goto L_0x0073;
        */
        throw new UnsupportedOperationException("Method not decompiled: consys.onlineexam.manthan3offlineexam.NewsUnpaidInTextView.downloadFromServer():java.lang.String");
    }
}

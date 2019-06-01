package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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

public class NewsPaidUser extends Activity {
    AdRequest adRequest;
    BufferedReader bufferedReader = null;
    private AdView mAdView;
    File newReadFile = new File(this.read_news_file_path);
    Button newsReload;
    WebView newsWeb;
    ProgressDialog progBar;
    String read_news_file_path = AppConstant.news_data;

    /* renamed from: consys.onlineexam.manthan3offlineexam.NewsPaidUser$1 */
    class C05371 implements OnClickListener {
        C05371() {
        }

        public void onClick(View v) {
            NewsPaidUser.this.refreshButtonWeb();
            System.out.println("return value new page");
        }
    }

    public class MyAsynchTask11 extends AsyncTask<NewsUnpaidInTextView, NewsUnpaidInTextView, String> {
        ProgressDialog prog;

        protected void onPreExecute() {
            this.prog = new ProgressDialog(NewsPaidUser.this);
            this.prog.setMessage("Updating the Jobs list..");
            this.prog.setIndeterminate(false);
            this.prog.setProgressStyle(0);
            this.prog.setCancelable(false);
            this.prog.show();
            super.onPreExecute();
        }

        protected String doInBackground(NewsUnpaidInTextView... params) {
            NewsPaidUser.this.downloadFromServer();
            this.prog.dismiss();
            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("done");
            NewsPaidUser newsPaidUser = new NewsPaidUser();
        }
    }

    public class UserP extends WebViewClient {
        ProgressDialog pd = null;

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            this.pd = new ProgressDialog(NewsPaidUser.this);
            this.pd.setTitle("Please wait");
            this.pd.setMessage("Page is loading..");
            this.pd.show();
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            this.pd.dismiss();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
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
        getWindow().requestFeature(2);
        setContentView(C0539R.layout.loading_news);
        this.newsWeb = (WebView) findViewById(C0539R.id.newsWebView);
        if (new File(this.read_news_file_path).exists()) {
            this.newsWeb.loadUrl("file://" + this.read_news_file_path);
        }
        this.newsWeb.getSettings().setBuiltInZoomControls(true);
        this.newsWeb.setWebViewClient(new UserP());
        this.newsReload = (Button) findViewById(C0539R.id.imageReload);
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
        this.newsReload.setOnClickListener(new C05371());
    }

    public String DisplayNewsPaid() {
        try {
            this.bufferedReader = new BufferedReader(new FileReader(new File(this.read_news_file_path)));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        String dataNewsDisplayUnpaid = null;
        while (true) {
            try {
                String inputLine = this.bufferedReader.readLine();
                if (inputLine == null) {
                    break;
                }
                dataNewsDisplayUnpaid = dataNewsDisplayUnpaid + inputLine;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        this.bufferedReader.close();
        return dataNewsDisplayUnpaid;
    }

    public void refreshButtonWeb() {
        new MyAsynchTask11().execute(new NewsUnpaidInTextView[0]);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public String downloadFromServer() {
        /*
        r11 = this;
        r6 = 0;
        r1 = "";
        r2 = consys.onlineexam.helper.AppConstant.serverURLForNews;
        r7 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x006d, ProtocolException -> 0x005d, IOException -> 0x0062 }
        r7.<init>(r2);	 Catch:{ MalformedURLException -> 0x006d, ProtocolException -> 0x005d, IOException -> 0x0062 }
        r8 = r7.openConnection();	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        r4 = new java.io.BufferedReader;	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        r9 = new java.io.InputStreamReader;	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        r10 = r8.getInputStream();	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        r9.<init>(r10);	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        r4.<init>(r9);	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
    L_0x001c:
        r5 = r4.readLine();	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        if (r5 == 0) goto L_0x0034;
    L_0x0022:
        r9 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        r9.<init>();	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        r9 = r9.append(r1);	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        r9 = r9.append(r5);	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        r1 = r9.toString();	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        goto L_0x001c;
    L_0x0034:
        r4.close();	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        r0 = new java.io.FileWriter;	 Catch:{ IOException -> 0x0052, MalformedURLException -> 0x0057, ProtocolException -> 0x006a }
        r9 = new java.io.File;	 Catch:{ IOException -> 0x0052, MalformedURLException -> 0x0057, ProtocolException -> 0x006a }
        r10 = consys.onlineexam.helper.AppConstant.news_data;	 Catch:{ IOException -> 0x0052, MalformedURLException -> 0x0057, ProtocolException -> 0x006a }
        r9.<init>(r10);	 Catch:{ IOException -> 0x0052, MalformedURLException -> 0x0057, ProtocolException -> 0x006a }
        r0.<init>(r9);	 Catch:{ IOException -> 0x0052, MalformedURLException -> 0x0057, ProtocolException -> 0x006a }
        r9 = r1.toString();	 Catch:{ IOException -> 0x0052, MalformedURLException -> 0x0057, ProtocolException -> 0x006a }
        r0.write(r9);	 Catch:{ IOException -> 0x0052, MalformedURLException -> 0x0057, ProtocolException -> 0x006a }
        r0.flush();	 Catch:{ IOException -> 0x0052, MalformedURLException -> 0x0057, ProtocolException -> 0x006a }
        r0.close();	 Catch:{ IOException -> 0x0052, MalformedURLException -> 0x0057, ProtocolException -> 0x006a }
    L_0x0050:
        r6 = r7;
    L_0x0051:
        return r1;
    L_0x0052:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ MalformedURLException -> 0x0057, ProtocolException -> 0x006a, IOException -> 0x0067 }
        goto L_0x0050;
    L_0x0057:
        r3 = move-exception;
        r6 = r7;
    L_0x0059:
        r3.printStackTrace();
        goto L_0x0051;
    L_0x005d:
        r3 = move-exception;
    L_0x005e:
        r3.printStackTrace();
        goto L_0x0051;
    L_0x0062:
        r3 = move-exception;
    L_0x0063:
        r3.printStackTrace();
        goto L_0x0051;
    L_0x0067:
        r3 = move-exception;
        r6 = r7;
        goto L_0x0063;
    L_0x006a:
        r3 = move-exception;
        r6 = r7;
        goto L_0x005e;
    L_0x006d:
        r3 = move-exception;
        goto L_0x0059;
        */
        throw new UnsupportedOperationException("Method not decompiled: consys.onlineexam.manthan3offlineexam.NewsPaidUser.downloadFromServer():java.lang.String");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == 0) {
            switch (keyCode) {
                case 4:
                    if (this.newsWeb.canGoBack()) {
                        this.newsWeb.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

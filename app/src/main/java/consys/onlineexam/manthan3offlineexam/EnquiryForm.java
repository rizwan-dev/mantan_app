package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

public class EnquiryForm extends Activity {
    WebView mWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_enquiry);
        TextView textvcontct2 = (TextView) findViewById(C0539R.id.textvcontct2);
        ((TextView) findViewById(C0539R.id.textVcolor)).setText(Html.fromHtml("<font color='#EE0000'>1. मंथन राज्यस्तरीय प्रज्ञाशोध परीक्षा\n</font><br>    ( इ. 2 री ते 8 वी- मराठी, English, सेमी- इंग्रजी)\n<br>\n<br><font color='#EE0000'>2. प्रज्ञाशोध, शिष्यवृत्ती परीक्षा- मार्गदर्शक  सराव &  संच\n</font><br>    (मराठी, English, सेमी- इंग्रजी)\n<br>\n<br><font color='#EE0000'>3. साप्ताहीक ज्ञानवंत –\n</font><br>(माहेवार स्पर्धा परीक्षा प्रश्नपत्रिका संच व अभ्यासक्रमावर आधारीत व्यवसाय.)\n<br>\n<br><font color='#EE0000'>4. Android Apps -\n<br>For E-Learning and Smart Study software for 1st to 4th standard.\n</font><br>(स्पर्धा परीक्षा, अभ्यासक्रमावर आधारीत)"));
    }
}

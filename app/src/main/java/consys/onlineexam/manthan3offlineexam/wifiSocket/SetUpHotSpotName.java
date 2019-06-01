package consys.onlineexam.manthan3offlineexam.wifiSocket;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetUpHotSpotName extends Activity {
    static WifiCheckerCode wifiAp;
    EditText hotspotName;
    private WifiManager wifi;
    Button wificreatebutton;

    /* renamed from: consys.onlineexam.manthan3offlineexam.wifiSocket.SetUpHotSpotName$1 */
    class C05521 implements OnClickListener {
        C05521() {
        }

        public void onClick(View v) {
            SetUpHotSpotName.wifiAp.toggleWiFiAP(SetUpHotSpotName.this.wifi, SetUpHotSpotName.this, SetUpHotSpotName.this.hotspotName.getText().toString());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_set_up_hot_spot_name);
        wifiAp = new WifiCheckerCode();
        this.wifi = (WifiManager) getSystemService("wifi");
        this.hotspotName = (EditText) findViewById(C0539R.id.editHotspotName);
        this.wificreatebutton = (Button) findViewById(C0539R.id.CreateMyhotspot);
        this.wificreatebutton.setOnClickListener(new C05521());
    }
}

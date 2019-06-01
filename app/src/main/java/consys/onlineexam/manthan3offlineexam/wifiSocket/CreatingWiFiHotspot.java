package consys.onlineexam.manthan3offlineexam.wifiSocket;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CreatingWiFiHotspot extends Activity {
    static Button btnWifiToggle;
    static Button btnscan;
    static WifiCheckerCode wifiAp;
    boolean wasAPEnabled = false;
    private WifiManager wifi;

    /* renamed from: consys.onlineexam.manthan3offlineexam.wifiSocket.CreatingWiFiHotspot$1 */
    class C05471 implements OnClickListener {
        C05471() {
        }

        public void onClick(View v) {
            CreatingWiFiHotspot.this.startActivity(new Intent(CreatingWiFiHotspot.this, SearchWifi.class));
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.wifiSocket.CreatingWiFiHotspot$2 */
    class C05482 implements OnClickListener {
        C05482() {
        }

        public void onClick(View v) {
            if (CreatingWiFiHotspot.this.wifi.isWifiEnabled()) {
                CreatingWiFiHotspot.this.wifi.disconnect();
            }
            boolean wifiApIsOn = CreatingWiFiHotspot.wifiAp.getWifiAPState() == CreatingWiFiHotspot.wifiAp.WIFI_AP_STATE_ENABLED || CreatingWiFiHotspot.wifiAp.getWifiAPState() == CreatingWiFiHotspot.wifiAp.WIFI_AP_STATE_ENABLING;
            if (!wifiApIsOn) {
                CreatingWiFiHotspot.wifiAp.toggleWiFiAP(CreatingWiFiHotspot.this.wifi, CreatingWiFiHotspot.this, null);
            }
            CreatingWiFiHotspot.this.startActivity(new Intent(CreatingWiFiHotspot.this, SetUpHotSpotName.class));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_creating_wi_fi_hotspot);
        btnWifiToggle = (Button) findViewById(C0539R.id.btnWifiToggle);
        btnscan = (Button) findViewById(C0539R.id.btnscan);
        wifiAp = new WifiCheckerCode();
        this.wifi = (WifiManager) getSystemService("wifi");
        btnscan.setOnClickListener(new C05471());
        btnWifiToggle.setOnClickListener(new C05482());
        getWindow().addFlags(4194434);
    }
}

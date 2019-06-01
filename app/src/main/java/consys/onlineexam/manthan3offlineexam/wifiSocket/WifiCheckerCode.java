package consys.onlineexam.manthan3offlineexam.wifiSocket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import consys.onlineexam.helper.AppConstant;

public class WifiCheckerCode extends Activity {
    private static int WIFI_AP_STATE_DISABLED = 1;
    private static int WIFI_AP_STATE_DISABLING = 0;
    private static int WIFI_AP_STATE_FAILED = 4;
    private static final int WIFI_AP_STATE_UNKNOWN = -1;
    private static int constant = 0;
    private String TAG = "WifiAP";
    public int WIFI_AP_STATE_ENABLED = 3;
    public int WIFI_AP_STATE_ENABLING = 2;
    private final String[] WIFI_STATE_TEXTSTATE = new String[]{"DISABLING", "DISABLED", "ENABLING", "ENABLED", "FAILED"};
    private boolean alwaysEnableWifi = true;
    String hotspotName;
    private int stateWifiWasIn = -1;
    private WifiManager wifi;

    public class SetWifiAPTask extends AsyncTask<String, String, String> {
        boolean WiFiHotspotVerifed = false;
        Context context1 = null;
        /* renamed from: d */
        ProgressDialog f19d;
        boolean mFinish;
        boolean mMode;

        public SetWifiAPTask(boolean mode, boolean finish, Context context) {
            this.mMode = mode;
            this.mFinish = finish;
            this.context1 = context.getApplicationContext();
            this.f19d = new ProgressDialog(context);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.f19d.setTitle("Turning WiFi AP " + (this.mMode ? "on" : "off") + "...");
            this.f19d.setMessage("...please wait a moment.");
            this.f19d.show();
        }

        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            try {
                this.f19d.dismiss();
                this.WiFiHotspotVerifed = true;
            } catch (Exception e) {
            }
            if (this.mFinish) {
                WifiCheckerCode.this.finish();
            }
            if (this.mMode && this.WiFiHotspotVerifed) {
                Intent homepage = new Intent(this.context1, SocketConnectorServer.class);
                homepage.setFlags(268435456);
                this.context1.startActivity(homepage);
            }
        }

        protected String doInBackground(String... params) {
            WifiCheckerCode.this.setWifiApEnabled(this.mMode);
            return null;
        }
    }

    public void toggleWiFiAP(WifiManager wifihandler, Context context, String name) {
        boolean wifiApIsOn;
        boolean z = true;
        if (this.wifi == null) {
            this.wifi = wifihandler;
        }
        if (name != null) {
            this.hotspotName = name;
        }
        if (getWifiAPState() == this.WIFI_AP_STATE_ENABLED || getWifiAPState() == this.WIFI_AP_STATE_ENABLING) {
            wifiApIsOn = true;
        } else {
            wifiApIsOn = false;
        }
        if (wifiApIsOn) {
        }
        if (wifiApIsOn) {
            z = false;
        }
        new SetWifiAPTask(z, false, context).execute(new String[0]);
    }

    private int setWifiApEnabled(boolean enabled) {
        int loopMax;
        Log.d(this.TAG, "*** setWifiApEnabled CALLED **** " + enabled);
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = AppConstant.WiFIName + this.hotspotName;
        config.allowedKeyManagement.set(0);
        if (enabled && this.stateWifiWasIn == -1) {
            this.stateWifiWasIn = this.wifi.getWifiState();
        }
        if (enabled && this.wifi.getConnectionInfo() != null) {
            Log.d(this.TAG, "disable wifi: calling");
            this.wifi.setWifiEnabled(false);
            loopMax = 20;
            while (loopMax > 0 && this.wifi.getWifiState() != 1) {
                Log.d(this.TAG, "disable wifi: waiting, pass: " + (10 - loopMax));
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {
                }
            }
            Log.d(this.TAG, "disable wifi: done, pass: " + (10 - loopMax));
        }
        int state = -1;
        try {
            Log.d(this.TAG, (enabled ? "enabling" : "disabling") + " wifi ap: calling");
            this.wifi.setWifiEnabled(false);
            this.wifi.getClass().getMethod("setWifiApEnabled", new Class[]{WifiConfiguration.class, Boolean.TYPE}).invoke(this.wifi, new Object[]{config, Boolean.valueOf(enabled)});
            state = ((Integer) this.wifi.getClass().getMethod("getWifiApState", new Class[0]).invoke(this.wifi, new Object[0])).intValue();
        } catch (Exception e2) {
        }
        if (!enabled) {
            loopMax = 20;
            while (loopMax > 0 && (getWifiAPState() == WIFI_AP_STATE_DISABLING || getWifiAPState() == this.WIFI_AP_STATE_ENABLED || getWifiAPState() == WIFI_AP_STATE_FAILED)) {
                Log.d(this.TAG, (enabled ? "enabling" : "disabling") + " wifi ap: waiting, pass: " + (10 - loopMax));
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e3) {
                }
            }
            Log.d(this.TAG, (enabled ? "enabling" : "disabling") + " wifi ap: done, pass: " + (10 - loopMax));
            if (this.stateWifiWasIn == 3 || this.stateWifiWasIn == 2 || this.stateWifiWasIn == 4 || this.alwaysEnableWifi) {
                Log.d(this.TAG, "enable wifi: calling");
                this.wifi.setWifiEnabled(true);
            }
            this.stateWifiWasIn = -1;
        } else if (enabled) {
            loopMax = 10;
            while (loopMax > 0 && (getWifiAPState() == this.WIFI_AP_STATE_ENABLING || getWifiAPState() == WIFI_AP_STATE_DISABLED || getWifiAPState() == WIFI_AP_STATE_FAILED)) {
                Log.d(this.TAG, (enabled ? "enabling" : "disabling") + " wifi ap: waiting, pass: " + (10 - loopMax));
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e4) {
                }
            }
            Log.d(this.TAG, (enabled ? "enabling" : "disabling") + " wifi ap: done, pass: " + (10 - loopMax));
        }
        return state;
    }

    public int getWifiAPState() {
        String str;
        int state = -1;
        try {
            state = ((Integer) this.wifi.getClass().getMethod("getWifiApState", new Class[0]).invoke(this.wifi, new Object[0])).intValue();
        } catch (Exception e) {
        }
        if (state >= 10) {
            constant = 10;
        }
        WIFI_AP_STATE_DISABLING = constant + 0;
        WIFI_AP_STATE_DISABLED = constant + 1;
        this.WIFI_AP_STATE_ENABLING = constant + 2;
        this.WIFI_AP_STATE_ENABLED = constant + 3;
        WIFI_AP_STATE_FAILED = constant + 4;
        String str2 = this.TAG;
        StringBuilder append = new StringBuilder().append("getWifiAPState.state ");
        if (state == -1) {
            str = "UNKNOWN";
        } else {
            str = this.WIFI_STATE_TEXTSTATE[state - constant];
        }
        Log.d(str2, append.append(str).toString());
        return state;
    }
}

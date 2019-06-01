package consys.onlineexam.manthan3offlineexam.wifiSocket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.DatabaseHelper;
import consys.onlineexam.helper.RegisterModel;
import consys.onlineexam.manthan3offlineexam.TaskListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchWifi extends Activity implements TaskListener {
    public static String emailId;
    public static String fullName;
    public static String phoneNo;
    String ITEM_KEY = "key";
    SimpleAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist = new ArrayList();
    int itemPlace = 0;
    ListView lv;
    HashMap res = new HashMap();
    List<ScanResult> results;
    int size = 0;
    WifiManager wifi;

    /* renamed from: consys.onlineexam.manthan3offlineexam.wifiSocket.SearchWifi$1 */
    class C05491 extends BroadcastReceiver {
        C05491() {
        }

        public void onReceive(Context c, Intent intent) {
            SearchWifi.this.results = SearchWifi.this.wifi.getScanResults();
            SearchWifi.this.size = SearchWifi.this.results.size();
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.wifiSocket.SearchWifi$2 */
    class C05512 implements OnItemClickListener {
        C05512() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            String selctitem = ((ScanResult) SearchWifi.this.results.get(SearchWifi.this.itemPlace)).SSID;
            WifiConfiguration wc = new WifiConfiguration();
            wc.SSID = "\"" + AppConstant.WiFIName + "\"";
            wc.allowedKeyManagement.set(0);
            int netId = SearchWifi.this.wifi.addNetwork(wc);
            if (selctitem.equalsIgnoreCase(AppConstant.WiFIName)) {
                SearchWifi.this.wifi.disconnect();
                boolean bwifi = SearchWifi.this.wifi.enableNetwork(netId, true);
                final ProgressDialog pb = new ProgressDialog(SearchWifi.this);
                pb.setTitle("waiting for server");
                pb.show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        pb.dismiss();
                        Intent socktClient = new Intent(SearchWifi.this, SocketConnectionClient.class);
                        socktClient.putExtra(SearchWifi.fullName, SearchWifi.fullName);
                        socktClient.putExtra(SearchWifi.emailId, SearchWifi.emailId);
                        socktClient.putExtra(SearchWifi.phoneNo, SearchWifi.phoneNo);
                        SearchWifi.this.startActivity(socktClient);
                    }
                }, 8000);
            }
        }
    }

    public class OnSearchEvent extends AsyncTask<SearchWifi, String, String> {
        ProgressDialog pb;
        public TaskListener task;

        public OnSearchEvent(TaskListener t) {
            this.task = t;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pb = new ProgressDialog(SearchWifi.this);
            this.pb.setTitle("Searching Server");
            this.pb.setMessage("Please wait...");
            this.pb.show();
        }

        protected String doInBackground(SearchWifi... params) {
            if (!SearchWifi.this.wifi.isWifiEnabled()) {
                SearchWifi.this.wifi.setWifiEnabled(true);
            }
            SearchWifi.this.arraylist.clear();
            SearchWifi.this.wifi.startScan();
            try {
                SearchWifi.this.results = SearchWifi.this.wifi.getScanResults();
                SearchWifi.this.size = SearchWifi.this.results.size();
                for (int i = 0; i < SearchWifi.this.size; i++) {
                    String wifiNmae = ((ScanResult) SearchWifi.this.results.get(i)).SSID;
                    System.out.println(wifiNmae);
                    if (wifiNmae.substring(0, 9).contains(AppConstant.WiFIName)) {
                        HashMap<String, String> item = new HashMap();
                        item.put(SearchWifi.this.ITEM_KEY, ((ScanResult) SearchWifi.this.results.get(i)).SSID + "  " + ((ScanResult) SearchWifi.this.results.get(i)).capabilities);
                        SearchWifi.this.arraylist.add(item);
                        SearchWifi.this.itemPlace = i;
                    }
                }
            } catch (Exception e) {
            }
            SearchWifi.this.res = new DatabaseHelper(SearchWifi.this).getRegisterDetails(null);
            SearchWifi.this.res.get("regmodel");
            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            this.pb.dismiss();
            this.task.onTaskCompleted(SearchWifi.this.res);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_search_wifi);
        this.lv = (ListView) findViewById(C0539R.id.list);
        this.wifi = (WifiManager) getSystemService("wifi");
        registerReceiver(new C05491(), new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        new OnSearchEvent(this).execute(new SearchWifi[]{this});
        this.adapter = new SimpleAdapter(this, this.arraylist, C0539R.layout.activity_search_wifi_custom, new String[]{this.ITEM_KEY}, new int[]{C0539R.id.list_value});
        this.lv.setAdapter(this.adapter);
        this.lv.setOnItemClickListener(new C05512());
    }

    public void onTaskCompleted(HashMap hm) {
        HashMap res = hm;
        if (((Boolean) res.get("flag")).booleanValue()) {
            RegisterModel rs = new RegisterModel();
            rs = (RegisterModel) res.get("regmodel");
            fullName = rs.getFull_name();
            emailId = rs.getEmail();
            phoneNo = rs.getPhone();
        }
    }
}

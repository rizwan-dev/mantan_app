package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.BillingProcessor.IBillingHandler;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.AppHelper;
import consys.onlineexam.helper.RegisterModel;
import consys.onlineexam.helper.SecureManager;
import consys.onlineexam.helper.ServerConnection;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements TaskListener {
    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjr8UTk45fJncvlFySfBUgDUFr97lTHUOXzskBZF8KioNt52iE96tx4StaDWOgTCzb3coulTC1Sf0ehGB4y9HeRzGpfe1muTG1hMqM3066mvcw71WsqiK+V/+9FUQrqEsOe/ESFNd59iHLP44DJP20iMWZHGpc6xbY9cNtK1YWY2x/hlfNst/jV2sLqYHsT6HGfQrNfaBVW9wrATeHzkYnTUGJwBh3GQaDVz6eMU7MllJC0H1bHbwgS7gdBVgXw+zm6Kh4WbIv15Ne//ghwld0tN7ZyUiuFp0wmMaFdd7au+Bva3kIU9BEwHT3+Z+p0qf35DIt6Q43XO7CFTtMItLDQIDAQAB";
    private static final String LOG_TAG = "consys";
    private static final String PRODUCT_ID = "mtse3rdmar";
    AdRequest adRequest;
    boolean afterRegistrationMainPage;
    private BillingProcessor bp;
    int key_flag = 0;
    private AdView mAdView;
    int positionName;
    private boolean readyToPurchase = false;

    /* renamed from: consys.onlineexam.manthan3offlineexam.MainActivity$3 */
    class C05293 implements OnClickListener {
        C05293() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
        }
    }

    /* renamed from: consys.onlineexam.manthan3offlineexam.MainActivity$1 */
    class C08431 implements IBillingHandler {
        C08431() {
        }

        public void onProductPurchased(String productId, TransactionDetails tr) {
            Toast.makeText(MainActivity.this, "Transaction Details Are:\nOrder Id:" + tr.orderId + "\nProduct Id:" + tr.productId + "\nPurchase Token" + tr.purchaseToken + "\nPurchase Info" + tr.purchaseInfo + "\nPurchase Time:" + tr.purchaseTime + "", 200000).show();
        }

        public void onBillingError(int errorCode, Throwable error) {
            MainActivity.this.showToast("Product Not Purchased!!: " + Integer.toString(errorCode));
        }

        public void onBillingInitialized() {
            MainActivity.this.readyToPurchase = true;
            MainActivity.this.onClick(null);
        }

        public void onPurchaseHistoryRestored() {
            for (String sku : MainActivity.this.bp.listOwnedProducts()) {
                Log.d(MainActivity.LOG_TAG, "Owned Managed Product: " + sku);
            }
            for (String sku2 : MainActivity.this.bp.listOwnedSubscriptions()) {
                Log.d(MainActivity.LOG_TAG, "Owned Subscription: " + sku2);
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

    public void onDestroy() {
        if (this.bp != null) {
            this.bp.release();
        }
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
        setContentView(C0539R.layout.activity_main);
        this.afterRegistrationMainPage = false;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                this.positionName = 1;
            } else {
                this.positionName = extras.getInt("positionName");
            }
        } else {
            this.positionName = ((Integer) savedInstanceState.getSerializable("positionName")).intValue();
        }
        this.bp = new BillingProcessor(this, LICENSE_KEY, new C08431());
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Data is" + data);
        if (!this.bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, 1).show();
    }

    public void onClick(View v) {
        if (v == null) {
            System.out.println("Registration in MainActivity");
            if (this.bp.isPurchased(PRODUCT_ID)) {
                System.out.println("Registration in MainActivity true");
                HashMap req = new HashMap();
                req.put("method", "getexams");
                req.put("chapterid", Integer.valueOf(AppConstant.selected_test_Id));
                req.put("examtype", AppConstant.selected_exam1);
                new MyAsynchTaskExecutor(this).execute(new HashMap[]{req});
            }
        } else if (this.readyToPurchase) {
            switch (v.getId()) {
                case C0539R.id.subscribeButton:
                    boolean b = this.bp.purchase(this, PRODUCT_ID);
                    if (this.bp.isPurchased(PRODUCT_ID)) {
                        HashMap hm = new HashMap();
                        hm.put("method", "getRegisterDetails");
                        hm = AppHelper.getValues(hm, this);
                        if (((Boolean) hm.get("flag")).booleanValue()) {
                            System.out.println(hm);
                            this.key_flag = 2;
                            RegisterModel r = (RegisterModel) hm.get("regmodel");
                            String data = "3R?" + r.getFull_name() + "?" + r.getPhone() + "?" + CommonActivity.getimei(this) + "?INAPPBILLING?" + r.getEmail() + "?" + r.getEmail() + "?" + r.getArea() + "?" + r.getCity() + "?" + r.getState() + "?" + r.getPincode();
                            hm.put("method", "onlineRegister");
                            hm.put("message", data);
                            new MyAsynchTaskExecutor(this).execute(new HashMap[]{hm});
                            return;
                        }
                        return;
                    }
                    return;
                case C0539R.id.btnkey:
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Product Key");
                    alert.setMessage("Enter product key");
                    final EditText input = new EditText(this);
                    alert.setView(input);
                    input.setRawInputType(3);
                    alert.setPositiveButton("OK", new OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (ServerConnection.isConnectingToInternet(MainActivity.this)) {
                                String value = input.getText().toString();
                                if (value == null || value.equalsIgnoreCase("")) {
                                    Toast.makeText(MainActivity.this, "Please Enter Key", 1).show();
                                    return;
                                }
                                MainActivity.this.key_flag = 1;
                                HashMap hm = new HashMap();
                                hm.put("method", "getRegisterDetails");
                                hm = AppHelper.getValues(hm, MainActivity.this);
                                System.out.println(hm);
                                if (((Boolean) hm.get("flag")).booleanValue()) {
                                    System.out.println(hm);
                                    RegisterModel r = (RegisterModel) hm.get("regmodel");
                                    String data = "3R?" + r.getFull_name() + "?" + r.getPhone() + "?" + CommonActivity.getimei(MainActivity.this).replace("3rdMar", "").trim() + "?" + value + "?" + r.getEmail() + "?" + r.getArea() + "?" + r.getCity() + "?" + r.getState() + "?" + r.getPincode();
                                    hm.put("method", "onlineRegister");
                                    hm.put("message", data);
                                    new MyAsynchTaskExecutor(MainActivity.this).execute(new HashMap[]{hm});
                                    return;
                                }
                                return;
                            }
                            Toast.makeText(MainActivity.this, "Please Turn On Internet Connection", 1).show();
                        }
                    });
                    alert.setNegativeButton("Cancel", new C05293());
                    alert.show();
                    return;
                case C0539R.id.subsDetailsButton:
                    SkuDetails subs = this.bp.getPurchaseListingDetails(PRODUCT_ID);
                    showToast(subs != null ? subs.toString() : "Failed to load subscription details");
                    return;
                case C0539R.id.updateSubscriptionsButton:
                    if (this.bp.loadOwnedPurchasesFromGoogle()) {
                        showToast("Subscriptions updated.");
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void onTaskCompleted(HashMap hm) {
        HashMap res = hm;
        try {
            System.out.println(hm.toString() + this.key_flag);
            Boolean b = (Boolean) res.get("flag");
            if (this.key_flag == 0) {
                if (b.booleanValue()) {
                    AppConstant.elist = (ArrayList) res.get("elist");
                    if (AppConstant.elist.size() > 0) {
                        startActivity(new Intent(this, TestListActivity.class));
                        return;
                    }
                    CommonActivity.toast(this, "Selected Chapter does not have any exam");
                    return;
                }
                try {
                    CommonActivity.toast(this, (String) hm.get("msg"));
                } catch (Exception e) {
                    CommonActivity.toast(this, "Connection Problem");
                }
            } else if (this.key_flag == 1) {
                String serverString = (String) hm.get("value");
                System.out.println("Before split " + serverString);
                String[] sp = serverString.split("\\?");
                String imei_frm_server = sp[1];
                String status = sp[2];
                String msg = sp[3];
                if (!CommonActivity.get_9_imei(this).equalsIgnoreCase(imei_frm_server)) {
                    CommonActivity.toast(this, "Registration failed,imei does not match");
                } else if (status.equalsIgnoreCase("active")) {
                    CommonActivity.toast(this, "Thank You\nWish You Best");
                    CommonActivity.writeStringAsFile(SecureManager.encrypt(AppConstant.key, SecureManager.getHASH(CommonActivity.getimei(this))));
                    CommonActivity.SendSMS("Registration Successful on 3R?" + CommonActivity.getimei(this));
                    this.afterRegistrationMainPage = true;
                    this.key_flag = 5;
                    req = new HashMap();
                    req.put("method", "getsubjectlist");
                    req.put("subject", AppConstant.selected_exam1);
                    req.put("chapterid", Integer.valueOf(255));
                    AppConstant.selected_subject = "Practice Exam";
                    AppConstant.selected_chapter = "Practice Exam";
                    m = new MyAsynchTaskExecutor(this);
                    j = new HashMap();
                    m.execute(new HashMap[]{req});
                } else {
                    CommonActivity.toast(this, status + ":" + msg.replaceAll("\\+", " "));
                }
            } else if (this.key_flag == 2) {
                CommonActivity.writeStringAsFile(SecureManager.encrypt(AppConstant.key, SecureManager.getHASH(CommonActivity.getimei(this))));
                this.afterRegistrationMainPage = true;
                req = new HashMap();
                req.put("method", "getsubjectlist");
                req.put("subject", AppConstant.selected_exam1);
                this.key_flag = 5;
                req.put("chapterid", Integer.valueOf(255));
                AppConstant.selected_subject = "Practice Exam";
                AppConstant.selected_chapter = "Practice Exam";
                m = new MyAsynchTaskExecutor(this);
                j = new HashMap();
                m.execute(new HashMap[]{req});
            } else if (this.afterRegistrationMainPage) {
                System.out.println(hm);
                AppConstant.elist = (ArrayList) res.get("elist");
                if (!b.booleanValue()) {
                    Toast.makeText(this, "No Practice Test Available", 1).show();
                } else if (AppConstant.elist.size() > 0) {
                    startActivity(new Intent(this, TestListActivity.class));
                }
            }
        } catch (Exception e2) {
            System.out.println("Exception in TestSubActivity" + e2);
        }
    }
}

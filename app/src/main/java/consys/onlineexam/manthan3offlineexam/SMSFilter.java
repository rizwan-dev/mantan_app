package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.anjlab.android.iab.v3.Constants;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.AppHelper;
import consys.onlineexam.manthan3offlineexam.CommonNotification.ServiceReceiver;
import java.util.HashMap;
import org.apache.commons.io.IOUtils;

public class SMSFilter extends Activity {
    NotificationManager Manager;
    Context mContext;
    ServiceReceiver nServiceReceiver;

    public void init(Context context) {
        this.mContext = context;
    }

    public void filterSms(String pNumber, String msg) {
        System.out.println(pNumber + AppConstant.Server_SMS_Receiver + msg);
        try {
            String one;
            String two;
            if (pNumber.charAt(0) != '+') {
                one = pNumber.substring(3, pNumber.length());
                two = AppConstant.Server_SMS_Receiver.substring(3, AppConstant.Server_SMS_Receiver.length());
            } else {
                one = pNumber;
                two = AppConstant.Server_SMS_Receiver;
            }
            if (one.equalsIgnoreCase(two)) {
                String body = msg.replace(IOUtils.LINE_SEPARATOR_UNIX, "");
                char ch = body.charAt(0);
                if (ch == 'R') {
                    if (AppConstant.obj instanceof RegisterActivity) {
                        AppConstant.obj.verifyMsg(body);
                        return;
                    } else {
                        verifyMsg(body);
                        return;
                    }
                } else if (ch == 'U') {
                    String st = body.split("\\?")[1];
                    hm = new HashMap();
                    hm.put("method", "insertserverconfig");
                    hm.put(Constants.RESPONSE_TYPE, "server_url");
                    hm.put("value", st);
                    AppHelper.getValues(hm, (Context) AppConstant.obj);
                    return;
                } else if (ch == 'C') {
                    String type = body.split("\\?")[1];
                    value = body.split("\\?")[2];
                    hm = new HashMap();
                    hm.put("method", "insertserverconfig");
                    hm.put(Constants.RESPONSE_TYPE, type);
                    hm.put("value", value);
                    AppHelper.getValues(hm, (Context) AppConstant.obj);
                    return;
                } else if (ch == 'S') {
                    value = body.split("\\?")[1];
                    hm = new HashMap();
                    hm.put("method", "insertserverconfig");
                    hm.put(Constants.RESPONSE_TYPE, "server_reg_sender");
                    hm.put("value", value);
                    AppHelper.getValues(hm, (Context) AppConstant.obj);
                    return;
                } else if (ch == 'M') {
                    System.out.println(body);
                    value = body.split("\\?")[1];
                    hm = new HashMap();
                    hm.put("method", "insertserverconfig");
                    hm.put(Constants.RESPONSE_TYPE, "server_reg_receiver");
                    hm.put("value", value);
                    AppHelper.getValues(hm, (Context) AppConstant.obj);
                    return;
                } else if (ch == 'N') {
                    value = body.split("\\?")[1];
                    System.out.println("sfghkh");
                    CommonActivity.toast((Context) AppConstant.obj, value);
                    AppConstant.notification = value;
                    AppConstant.flag_n = new Boolean(true).booleanValue();
                    System.out.println("sfghkh" + AppConstant.notification);
                    start(value);
                    return;
                } else {
                    return;
                }
            }
            System.out.println("NOT SERVER SMS");
        } catch (Exception e) {
            System.out.println("Exception in SMS Filter" + e.getMessage());
        }
    }

    public void start(String v) {
        try {
            System.out.println("IN START");
            set("hello", C0539R.drawable.check, "fss", 564);
            this.mContext.startService(new Intent(this.mContext, CommonNotification.class));
        } catch (Exception e) {
            System.out.println("Exception calling this" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void set(String t, int imageid, String str, int id) {
        NotificationManager mgr = (NotificationManager) getSystemService("notification");
        Notification note = new Notification(C0539R.drawable.check, "Android Example Status message!", System.currentTimeMillis());
        note.setLatestEventInfo(this, "Android Example Notification Title", "This is the android example notification message", PendingIntent.getActivity(this, 0, new Intent(this, MainMenuActivity.class), 0));
        mgr.notify(35, note);
    }

    public static void verifyMsg(String serverString) {
        if (serverString.length() > 2) {
            System.out.println("Before split " + serverString);
            String[] sp = serverString.split("\\?");
            String imei_frm_server = sp[1];
            String status = sp[2];
            String msg = sp[3];
            if (CommonActivity.get_9_imei((Context) AppConstant.obj).equalsIgnoreCase(imei_frm_server)) {
                System.out.println("Updating app_status");
                HashMap hm = new HashMap();
                hm.put("method", "insertstatus");
                hm.put("status", status);
                hm.put("msg", msg);
                AppHelper.getValues(hm, (Context) AppConstant.obj);
            }
        }
    }
}

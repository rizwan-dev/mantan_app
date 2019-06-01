package consys.onlineexam.manthan3offlineexam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import consys.onlineexam.helper.AppConstant;

public class SmsReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Object[] pdus = (Object[]) extras.get("pdus");
            for (Object obj : pdus) {
                SmsMessage SMessage = SmsMessage.createFromPdu((byte[]) obj);
                String sender = SMessage.getOriginatingAddress();
                String body = SMessage.getMessageBody().toString();
                Intent in = new Intent("SmsMessage.intent.MAIN").putExtra("get_msg", sender + ":" + body);
                System.out.println("Hello" + body);
                if (body.charAt(0) == 'I' || body.charAt(0) == 'i') {
                    body = body.substring(1, body.length());
                    SMSFilter s = new SMSFilter();
                    s.init((Context) AppConstant.obj);
                    s.filterSms(sender, body);
                }
                context.sendBroadcast(in);
            }
        }
    }
}

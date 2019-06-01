package consys.onlineexam.helper;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class RegisterHelper {
    public static String NetworkOperation(String message) {
        try {
            List<NameValuePair> qparams = new ArrayList();
            qparams.add(new BasicNameValuePair("msg", message));
            System.out.println(qparams);
            String url = "http://consistentsystem.com/AppGateway/ManthanReg.php?" + URLEncodedUtils.format(qparams, "UTF-8");
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            System.out.println("AT Register Helper URl");
            try {
                HttpEntity entity = httpclient.execute(httpget).getEntity();
                if (entity != null) {
                    String result = convertStreamToString(entity.getContent());
                    System.out.println(result);
                    return result;
                }
            } catch (Exception e) {
                Log.e("SMS-GATEWAY", "HTTP REQ FAILED:" + url);
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    sb.append(line + IOUtils.LINE_SEPARATOR_UNIX);
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                try {
                    is.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    is.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                }
                throw th;
            }
        }
        is.close();
        return sb.toString();
    }
}

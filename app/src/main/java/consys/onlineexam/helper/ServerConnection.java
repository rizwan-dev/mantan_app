package consys.onlineexam.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class ServerConnection implements Serializable {
    public static HashMap POST(HashMap hm, Context ct) {
        HashMap result = new HashMap();
        if (isConnectingToInternet(ct)) {
            try {
                System.out.println("Before URL" + AppConstant.server_url);
                URL url = new URL(AppConstant.server_url);
                System.out.println(url.getPath());
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                out.writeObject(hm);
                out.close();
                ObjectInputStream sin = new ObjectInputStream(connection.getInputStream());
                result = (HashMap) sin.readObject();
                System.out.println("Response from server" + result);
                sin.close();
                return result;
            } catch (MalformedURLException e) {
                System.out.println("Exception" + e);
                new HashMap().put("flag", Boolean.valueOf(false));
                return hm;
            } catch (IOException e2) {
                System.out.println("Exception" + e2);
                new HashMap().put("flag", Boolean.valueOf(false));
                return hm;
            } catch (ClassNotFoundException e3) {
                System.out.println("Exception" + e3);
                new HashMap().put("flag", Boolean.valueOf(false));
                return hm;
            } catch (Exception e4) {
                System.out.println("Exception" + e4);
                new HashMap().put("flag", Boolean.valueOf(false));
                return hm;
            }
        }
        System.out.println("Internet conection not available");
        HashMap hm1 = new HashMap();
        hm1.put("flag", Boolean.valueOf(false));
        hm1.put("msg", "Internet Connection not available");
        return hm;
    }

    public static boolean isConnectingToInternet(Context ct) {
        ConnectivityManager connectivity = (ConnectivityManager) ct.getSystemService("connectivity");
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo state : info) {
                    if (state.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
    }
}

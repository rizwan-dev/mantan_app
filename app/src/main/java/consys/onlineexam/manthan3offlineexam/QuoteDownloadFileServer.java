package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class QuoteDownloadFileServer extends Activity implements TaskListener {
    BufferedReader bufferedReader = null;
    String dwnload_file_path = "http://consistentsystem.com/AppGateway/compt/NewsOpni.html";
    String localFile = "";

    class MyAsynchTask extends AsyncTask<QuoteDownloadFileServer, HashMap, String> {
        /* renamed from: t */
        TaskListener f16t = null;

        public MyAsynchTask(TaskListener t) {
            this.f16t = t;
        }

        protected String doInBackground(QuoteDownloadFileServer... params) {
            QuoteDownloadFileServer.this.downloadFromServer(params[0].dwnload_file_path, params[0].localFile);
            return null;
        }

        protected void onPostExecute(String result) {
            this.f16t.onTaskCompleted(null);
            super.onPostExecute(null);
        }
    }

    public void DataOnSite(String serverFile, String localFile) {
        this.dwnload_file_path = serverFile;
        this.localFile = localFile;
        new MyAsynchTask(this).execute(new QuoteDownloadFileServer[]{this});
    }

    public String downloadFromServer(String serverFile, String localFile) {
        String dataOnNewsServer = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL(serverFile).openConnection().getInputStream()));
            while (true) {
                String inputLine = in.readLine();
                if (inputLine == null) {
                    break;
                }
                dataOnNewsServer = dataOnNewsServer + inputLine;
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        try {
            FileWriter NewsData = new FileWriter(new File(localFile));
            NewsData.write(dataOnNewsServer.toString());
            NewsData.flush();
            NewsData.close();
        } catch (IOException e32) {
            e32.printStackTrace();
        }
        return dataOnNewsServer;
    }

    public void onTaskCompleted(HashMap hm) {
    }
}

package consys.onlineexam.manthan3offlineexam;

import consys.onlineexam.helper.AppConstant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

public class NotificationQuoteDetailRead {
    BufferedReader bufferedReader = null;
    String dailyData1 = "";
    String inputLine;
    String read_file_path = AppConstant.jsonDailyQuotes;

    public String readQuotedetail(File readFil) {
        if (readFil.exists()) {
            try {
                this.bufferedReader = new BufferedReader(new FileReader(readFil));
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
            if (this.bufferedReader != null) {
                while (true) {
                    try {
                        String readLine = this.bufferedReader.readLine();
                        this.inputLine = readLine;
                        if (readLine == null) {
                            break;
                        }
                        this.dailyData1 += this.inputLine;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                this.bufferedReader.close();
                return this.dailyData1;
            }
        }
        return null;
    }
}

package consys.onlineexam.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Downloder {
    public static void download(int folderid, HashMap res, ArrayList<Integer> qlist) {
        try {
            String path = AppConstant.DB_PATH + "system_manthan";
            File d = new File(path);
            if (!d.exists()) {
                d.mkdir();
            }
            path = path + "/" + folderid;
            File fl = new File(path);
            if (fl.exists()) {
                fl.mkdir();
                for (int i = 0; i < qlist.size(); i++) {
                    try {
                        int qid = ((Integer) qlist.get(i)).intValue();
                        String qpath = "/" + qid;
                        File f1 = new File(path + qpath);
                        if (f1.exists()) {
                            f1.mkdir();
                            String[] filelist = (String[]) res.get(Integer.valueOf(qid));
                            for (String img : filelist) {
                                try {
                                    InputStream in = new BufferedInputStream(new URL(AppConstant.server_url + folderid + "//" + qid + "//" + img).openStream(), 20000);
                                    if (in != null) {
                                        System.out.println("hsdkjfhdkg");
                                        FileOutputStream output = new FileOutputStream(path + qpath + "/" + img);
                                        byte[] buffer = new byte[9000];
                                        while (true) {
                                            int bytesRead = in.read(buffer, 0, buffer.length);
                                            if (bytesRead < 0) {
                                                break;
                                            }
                                            output.write(buffer, 0, bytesRead);
                                        }
                                        System.out.println("write");
                                        output.close();
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                    } catch (Exception e2) {
                    }
                }
            }
        } catch (Exception e3) {
        }
    }
}

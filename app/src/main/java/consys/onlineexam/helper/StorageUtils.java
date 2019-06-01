package consys.onlineexam.helper;

import android.annotation.SuppressLint;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

@SuppressLint({"NewApi"})
public class StorageUtils {
    private static final String TAG = "StorageUtils";

    public static class StorageInfo {
        public final int number;
        public final String path;
        public final boolean readonly;
        public final boolean removable;

        StorageInfo(String path, boolean readonly, boolean removable, int number) {
            this.path = path;
            this.readonly = readonly;
            this.removable = removable;
            this.number = number;
        }

        public String getDisplayName() {
            StringBuilder res = new StringBuilder();
            if (!this.removable) {
                res.append("Internal SD card");
            } else if (this.number > 1) {
                res.append("SD card " + this.number);
            } else {
                res.append("SD card");
            }
            if (this.readonly) {
                res.append(" (Read only)");
            }
            return res.toString();
        }
    }

    public static String getpathCorrect() {
        String st = getpath();
        if (st != null) {
            File d = new File(st);
            try {
                if (d.canWrite()) {
                    return d.getAbsolutePath();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "/data/data/consys.onlineexam.manthanofflineexam";
    }

    public static String getExternal() {
        List list = getStorageList();
        for (int i = 0; i < list.size(); i++) {
            StorageInfo s = (StorageInfo) list.get(i);
            if (!s.readonly && s.removable) {
                return s.path;
            }
        }
        return null;
    }

    public static String getpath() {
        String str = getExternal();
        String str1 = getInternal();
        if (str != null) {
            return new File(str).getAbsolutePath();
        }
        if (str1 != null) {
            return new File(str1).getAbsolutePath();
        }
        return null;
    }

    public static String getInternal() {
        List list = getStorageList();
        for (int i = 0; i < list.size(); i++) {
            StorageInfo s = (StorageInfo) list.get(i);
            if (!s.readonly && !s.removable) {
                return s.path;
            }
        }
        return null;
    }

    public static List<StorageInfo> getStorageList() {
        FileNotFoundException ex;
        IOException ex2;
        Throwable th;
        List<StorageInfo> list = new ArrayList();
        String def_path = Environment.getExternalStorageDirectory().getPath();
        String def_path_state = Environment.getExternalStorageState();
        boolean def_path_available = def_path_state.equals("mounted") || def_path_state.equals("mounted_ro");
        boolean def_path_readonly = Environment.getExternalStorageState().equals("mounted_ro");
        HashSet<String> paths = new HashSet();
        int cur_removable_number = 1;
        if (def_path_available) {
            int i =1;
            paths.add(def_path);

                cur_removable_number = 1 + 1;

            list.add(0, new StorageInfo(def_path, def_path_readonly, true, i));
        }
        BufferedReader buf_reader = null;
        try {
            BufferedReader buf_reader2 = new BufferedReader(new FileReader("/proc/mounts"));
            int cur_removable_number2 = cur_removable_number;
            while (true) {
                try {
                    String line = buf_reader2.readLine();
                    if (line == null) {
                        break;
                    } else if (line.contains("vfat") || line.contains("/mnt")) {
                        StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
                        String unused = stringTokenizer.nextToken();
                        String mount_point = stringTokenizer.nextToken();
                        if (paths.contains(mount_point)) {
                            continue;
                        } else {
                            unused = stringTokenizer.nextToken();
                            boolean readonly = Arrays.asList(stringTokenizer.nextToken().split(",")).contains("ro");
                            if (!line.contains("/dev/block/vold") || line.contains("/mnt/secure") || line.contains("/mnt/asec") || line.contains("/mnt/obb") || line.contains("/dev/mapper") || line.contains("tmpfs")) {
                                cur_removable_number = cur_removable_number2;
                            } else {
                                paths.add(mount_point);
                                cur_removable_number = cur_removable_number2 + 1;
                                try {
                                    list.add(new StorageInfo(mount_point, readonly, true, cur_removable_number2));
                                }  catch (Throwable th2) {
                                    th = th2;
                                    buf_reader = buf_reader2;
                                }
                            }
                            cur_removable_number2 = cur_removable_number;
                        }
                    }
                } catch (FileNotFoundException e3) {
                    ex = e3;
                    buf_reader = buf_reader2;
                    cur_removable_number = cur_removable_number2;
                } catch (IOException e4) {
                    ex2 = e4;
                    buf_reader = buf_reader2;
                    cur_removable_number = cur_removable_number2;
                } catch (Throwable th3) {
                    th = th3;
                    buf_reader = buf_reader2;
                    cur_removable_number = cur_removable_number2;
                }
            }
            if (buf_reader2 != null) {
                try {
                    buf_reader2.close();
                    buf_reader = buf_reader2;
                    cur_removable_number = cur_removable_number2;
                } catch (IOException e5) {
                    buf_reader = buf_reader2;
                    cur_removable_number = cur_removable_number2;
                }
            } else {
                cur_removable_number = cur_removable_number2;
            }
        } catch (FileNotFoundException e6) {
            ex = e6;
            try {
                ex.printStackTrace();
                if (buf_reader != null) {
                    try {
                        buf_reader.close();
                    } catch (IOException e7) {
                    }
                }
                return list;
            } catch (Throwable th4) {
                th = th4;
                if (buf_reader != null) {
                    try {
                        buf_reader.close();
                    } catch (IOException e8) {
                    }
                }

                throw th;
            }
        } catch (IOException e9) {
            ex2 = e9;
            ex2.printStackTrace();
            if (buf_reader != null) {
                try {
                    buf_reader.close();
                } catch (IOException e10) {
                }
            }
            return list;
        }
        return list;
    }
}

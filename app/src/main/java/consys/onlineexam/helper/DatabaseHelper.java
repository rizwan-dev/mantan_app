package consys.onlineexam.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.anjlab.android.iab.v3.Constants;
import consys.onlineexam.manthan3offlineexam.CommonActivity;
import example.EventDataSQLHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.IOUtils;

@SuppressLint({"SimpleDateFormat"})
public class DatabaseHelper extends SQLiteOpenHelper {
    private final Context myContext;
    private SQLiteDatabase myDataBase;
    boolean review_flag;

    public DatabaseHelper(Context context) {
        super(context, AppConstant.MY_PATH, null, 1);
        this.myContext = context;
    }

    public boolean createDataBase(Context xt) throws IOException {
        if (checkDataBase()) {
            try {
                InputStream in = this.myContext.getAssets().open("version.txt");
                String str1 = "";
                String str2 = "";
                while (true) {
                    int c = in.read();
                    if (c == -1) {
                        break;
                    }
                    str1 = str1 + ((char) c);
                }
                File file = new File(AppConstant.version_file_name);
                if (file.exists()) {
                    System.out.println("Version file exists reading it...");
                    BufferedReader br = new BufferedReader(new FileReader(file), 8192);
                    StringBuilder stringBuilder = new StringBuilder();
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        stringBuilder.append(line);
                    }
                    str2 = stringBuilder.toString();
                }
                if (str1 == null || str2 == null) {
                    return do_copy();
                }
                System.out.println("not null" + str1 + str2);
                if (str1.equalsIgnoreCase(str2)) {
                    System.out.println("Two versions are same");
                    return true;
                }
                System.out.println("Two versions are not same");
                return do_copy();
            } catch (Exception e) {
                return true;
            }
        }
        System.out.println("In Else for copying data");
        return do_copy();
    }

    public boolean do_copy() {
        this.myDataBase = getReadableDatabase();
        try {
            copyDataBase();
            write_version();
            this.myDataBase.close();
            AppConstant.back_flag = true;
            return true;
        } catch (IOException e) {
            this.myDataBase.close();
            return false;
        }
    }

    public void write_version() {
        try {
            InputStream in = this.myContext.getAssets().open("version.txt");
            String str1 = "";
            String str2 = "";
            while (true) {
                int c = in.read();
                if (c == -1) {
                    break;
                }
                str1 = str1 + ((char) c);
            }
            File file = new File(AppConstant.version_file_name);
            System.out.println("Writing version" + str1);
            FileWriter fw;
            if (file.exists()) {
                fw = new FileWriter(file);
                fw.write(str1);
                fw.close();
                return;
            }
            file.createNewFile();
            fw = new FileWriter(file);
            fw.write(str1);
            fw.close();
        } catch (Exception e) {
        }
    }

    private boolean checkDataBase() {
        try {
            String myPath = AppConstant.MY_PATH;
            System.out.println(myPath);
            this.myDataBase = SQLiteDatabase.openDatabase(myPath, null, 1);
        } catch (SQLiteException e) {
        }
        if (this.myDataBase != null) {
            this.myDataBase.close();
        }
        if (this.myDataBase != null) {
            return true;
        }
        return false;
    }

    private void copyDataBase() throws IOException {
        File f = new File(AppConstant.DB_PATH + "android_LangINconfig3");
        System.out.println(f.getAbsolutePath());
        if (!f.exists()) {
            f.mkdir();
            System.out.println("Directory created" + f.getAbsolutePath());
        }
        String files = copyFileOrDir("android_LangINconfig3");
    }

    public void openDataBase() throws SQLException {
        this.myDataBase = SQLiteDatabase.openDatabase(AppConstant.MY_PATH, null, 1);
    }

    public synchronized void close() {
        if (this.myDataBase != null) {
            this.myDataBase.close();
        }
        super.close();
    }

    private String copyFileOrDir(String path) {
        AssetManager assetManager = this.myContext.getAssets();
        StringBuilder files = new StringBuilder();
        try {
            String[] assets = assetManager.list(path);
            if (assets.length == 0) {
                System.out.println("File IS" + path);
                files.append(IOUtils.LINE_SEPARATOR_UNIX + path);
                copyFile(path);
            } else {
                String fullPath = AppConstant.DB_PATH + path;
                File dir = new File(fullPath);
                if (!dir.exists()) {
                    files.append("\n\t\tNew " + fullPath);
                    dir.mkdir();
                }
                for (int i = 0; i < assets.length; i++) {
                    files.append("\n\tFoleder" + assets[i]);
                    files.append(copyFileOrDir(path + "/" + assets[i]));
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
        return files.toString();
    }

    private void copyFile(String filename) {
        OutputStream outputStream;
        Exception e;
        try {
            InputStream in = this.myContext.getAssets().open(filename);
            if (filename.contains("manthandb.mp4")) {
                filename = filename.replaceAll("manthandb.mp4", "manthandb.sqlite");
            }
            System.out.println("FILE NAME OF DATABASE" + filename);
            String newFileName = AppConstant.DB_PATH + filename;
            File f = new File(newFileName);
            if (f.exists()) {
                f.delete();
            }
            OutputStream out = new FileOutputStream(newFileName);
            try {
                byte[] buffer = new byte[65536];
                while (true) {
                    int read = in.read(buffer);
                    if (read != -1) {
                        out.write(buffer, 0, read);
                    } else {
                        in.close();
                        out.flush();
                        out.close();
                        outputStream = out;
                        return;
                    }
                }
            } catch (Exception e2) {
                e = e2;
                outputStream = out;
                Log.e("tag", e.getMessage());
            }
        } catch (Exception e3) {
            e = e3;
            Log.e("tag", e.getMessage());
        }
    }

    public HashMap getQuestionList(HashMap req) {
        HashMap res = new HashMap();
        try {
            String sql;
            String table = (String) req.get("table");
            this.myDataBase = getReadableDatabase();
            ArrayList<Integer> qlist = new ArrayList();
            int eid = ((Integer) req.get("examid")).intValue();
            if (table.equalsIgnoreCase("practice_exam")) {
                sql = "select p.Que_id from practice_exam p, question q where p.que_id=q.que_id and p.exam_id=" + eid + " order by q.subject_id";
            } else {
                sql = "SELECT Que_id FROM " + table + " where Exam_id='" + eid + "'";
            }
            Cursor c = this.myDataBase.rawQuery(sql, null);
            if (c != null) {
                while (c.moveToNext()) {
                    qlist.add(Integer.valueOf(c.getInt(0)));
                }
                c.close();
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(true));
                res.put("qlist", qlist);
            } else {
                c.close();
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(false));
            }
        } catch (Exception e) {
            this.myDataBase.close();
            Log.d("Exception in getQuestionLsitList", e.toString());
        }
        return res;
    }

    public HashMap getSubjectList(HashMap hm) {
        String sub = (String) hm.get("subject");
        System.out.println("DATABASE PATH" + AppConstant.MY_PATH);
        HashMap res = new HashMap();
        HashMap<String, ArrayList<ChapterModel>> map_test = new HashMap();
        ArrayList<String> sublist = new ArrayList();
        ArrayList<Integer> subid = new ArrayList();
        String sql = "";
        if (sub.equalsIgnoreCase("Topicwise Exam")) {
            try {
                this.myDataBase = getReadableDatabase();
                System.out.println("In getsubjectlist" + this.myDataBase.getPath());
                Cursor c = this.myDataBase.rawQuery("select * from subject", null);
                if (c != null) {
                    while (c.moveToNext()) {
                        String s = c.getString(1).trim();
                        System.out.println(s);
                        if (!(s.equalsIgnoreCase("Test Series") || s.equalsIgnoreCase("Live test"))) {
                            sublist.add(s);
                            subid.add(Integer.valueOf(c.getInt(0)));
                        }
                        Log.d("Subject is", c.getString(1));
                    }
                }
                for (int i = 0; i < subid.size(); i++) {
                    Cursor cursor = this.myDataBase.rawQuery("SELECT * from chapter where Subject_id='" + subid.get(i) + "'", null);
                    if (cursor != null) {
                        ArrayList<ChapterModel> chlist = new ArrayList();
                        while (cursor.moveToNext()) {
                            ChapterModel ch = new ChapterModel();
                            ch.setChapter_id(cursor.getInt(0));
                            ch.setChapter_name(cursor.getString(1).trim());
                            chlist.add(ch);
                        }
                        map_test.put(((String) sublist.get(i)).toString(), chlist);
                    }
                    cursor.close();
                }
                res.put("sublist", sublist);
                res.put("hm_exam_list", map_test);
                res.put("flag", Boolean.valueOf(true));
                c.close();
                this.myDataBase.close();
                return res;
            } catch (Exception e) {
                this.myDataBase.close();
                Log.d("Exception in getSubjectList", e.toString());
                e.printStackTrace();
                return res;
            }
        }
        HashMap r = new HashMap();
        r.put("chapterid", hm.get("chapterid"));
        r.put("examtype", "Practice Exam");
        return getExamList(r);
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public HashMap getExamList(HashMap req) {
        HashMap res = new HashMap();
        try {
            this.myDataBase = getReadableDatabase();
            ArrayList<ExamModel> elist = new ArrayList();
            String et = (String) req.get("examtype");
            Cursor c = this.myDataBase.rawQuery("select * from exam_details where Chapter_id='" + ((Integer) req.get("chapterid")).intValue() + "' and Exam_Type='" + et + "'", null);
            if (c != null) {
                while (c.moveToNext()) {
                    ExamModel m = new ExamModel();
                    m.setExam_id(c.getInt(0));
                    m.setExam_name(c.getString(c.getColumnIndex("Exam_name")).trim());
                    m.setCreater_name(c.getString(c.getColumnIndex("Creater_name")).trim());
                    m.setCollege_id(c.getInt(c.getColumnIndex("College_id")));
                    m.setSubject_id(c.getInt(c.getColumnIndex("Subject_id")));
                    m.setCreation_time(c.getString(c.getColumnIndex("Creation_time")).trim());
                    m.setExam_duration(c.getString(c.getColumnIndex("Exam_duration")).trim());
                    m.setNo_of_question(c.getInt(c.getColumnIndex("No_of_question")));
                    m.setMarks((int) c.getDouble(c.getColumnIndex("Marks")));
                    m.setExam_Type(c.getString(c.getColumnIndex("Exam_Type")).trim());
                    m.setExam_Date(c.getString(c.getColumnIndex("Exam_Date")).trim());
                    m.setStart_Time(c.getString(c.getColumnIndex("Start_Time")).trim());
                    m.setEnd_Time(c.getString(c.getColumnIndex("End_Time")).trim());
                    String neg = c.getString(c.getColumnIndex("Negative")).trim();
                    if (neg != null) {
                        m.setNegative(Float.parseFloat(neg));
                    } else {
                        m.setNegative(Float.parseFloat(neg));
                    }
                    m.setChapter_id(c.getInt(c.getColumnIndex("Chapter_id")));
                    m.setImage_dir(c.getString(c.getColumnIndex("image_dir")).trim());
                    m.setAttempts(c.getInt(c.getColumnIndex("Attempts")));
                    m.setMyAttempts(c.getInt(c.getColumnIndex("MyAttempts")));
                    m.setIs_free(c.getString(c.getColumnIndex("is_free")));
                    elist.add(m);
                }
                c.close();
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(true));
                res.put("elist", elist);
            } else {
                c.close();
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(false));
            }
        } catch (Exception e) {
            this.myDataBase.close();
            Log.d("Exception in getExamList", e.toString());
        }
        return res;
    }

    public HashMap getQueDetails(HashMap req) {
        HashMap res = new HashMap();
        try {
            String st;
            this.myDataBase = getReadableDatabase();
            ArrayList qdlist = new ArrayList();
            int qid = ((Integer) req.get("qid")).intValue();
            String table1 = (String) req.get("table1");
            Cursor c = this.myDataBase.rawQuery("SELECT Que_id,Exam_id,Subject_id, Chapter_id,Question,Currect_ans,Solution,URL,Answer_type,Direction FROM " + ((String) req.get("table")) + " where Que_id='" + qid + "'", null);
            if (c != null) {
                while (c.moveToNext()) {
                    QuestionModel q = new QuestionModel();
                    q.setQue_id(c.getInt(0));
                    q.setExam_id(c.getInt(1));
                    q.setSubject_id(c.getInt(2));
                    q.setChapter_id(c.getInt(3));
                    String stre = new String(c.getString(4));
                    if (!(stre == null || "".equalsIgnoreCase(stre))) {
                        System.out.println("Decrypted" + AES.getDString(stre));
                        q.setQuestion(URLEncoder.encode(AES.getDString(c.getString(4))));
                    }
                    q.setCurrect_ans(c.getString(5));
                    st = c.getString(6);
                    if (st != null) {
                        q.setSolution(st);
                    }
                    String str = c.getString(7);
                    if (str != null) {
                        q.setURL(str);
                    }
                    q.setAnswer_type(c.getString(8));
                    String d = c.getString(9);
                    if (d != null) {
                        q.setDirection(d);
                    }
                    qdlist.add(q);
                }
            }
            c.close();
            Cursor cr = this.myDataBase.rawQuery("SELECT * FROM " + table1 + " where Que_id='" + qid + "'", null);
            if (cr != null) {
                while (cr.moveToNext()) {
                    if (!(cr.getString(1) == null || cr.getString(2) == null)) {
                        OptionModel op = new OptionModel();
                        op.setQue_id(cr.getInt(0));
                        op.setOption_no(cr.getString(1));
                        st = cr.getString(2);
                        if (st != null) {
                            op.setOption(URLEncoder.encode(st));
                        }
                        qdlist.add(op);
                    }
                }
                cr.close();
            }
            c.close();
            this.myDataBase.close();
            if (qdlist.size() > 0) {
                c.close();
                this.myDataBase.close();
                res.put("queoptlist", qdlist);
                res.put("flag", Boolean.valueOf(true));
            } else {
                c.close();
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(false));
            }
        } catch (Exception e) {
            this.myDataBase.close();
            Log.d("Exception in getQuestionsDetails", e.toString());
        }
        return res;
    }

    public HashMap getStudySubject(HashMap req) {
        HashMap res = new HashMap();
        ArrayList<String> sublist = new ArrayList();
        try {
            this.myDataBase = getReadableDatabase();
            Cursor c = this.myDataBase.rawQuery("SELECT * FROM subject", null);
            if (c != null) {
                while (c.moveToNext()) {
                    String str = c.getString(1);
                    if (!(str.equalsIgnoreCase("Test_Series") || str.equalsIgnoreCase("Live_Test"))) {
                        sublist.add(str);
                    }
                }
                c.close();
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(true));
                res.put("sublist", sublist);
            } else {
                c.close();
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(false));
            }
        } catch (Exception e) {
            this.myDataBase.close();
            Log.d("Exception in getExamList", e.toString());
        }
        return res;
    }

    public HashMap getStudyChapter(HashMap req) {
        HashMap res = new HashMap();
        ArrayList<String> chaplist = new ArrayList();
        try {
            this.myDataBase = getReadableDatabase();
            Cursor cr = this.myDataBase.rawQuery("SELECT * FROM subject where Subject_Name='" + ((String) req.get("subject")) + "'", null);
            int subid = 0;
            if (cr != null) {
                while (cr.moveToNext()) {
                    subid = cr.getInt(0);
                }
            }
            cr.close();
            Cursor c = this.myDataBase.rawQuery("SELECT * FROM chapter where Subject_id='" + subid + "'", null);
            if (c != null) {
                while (c.moveToNext()) {
                    chaplist.add(c.getString(1));
                }
                c.close();
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(true));
                res.put("chaplist", chaplist);
            } else {
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(false));
            }
        } catch (Exception e) {
            this.myDataBase.close();
            Log.d("Exception in getExamList", e.toString());
        }
        return res;
    }

    public HashMap getSolutions(HashMap req) {
        HashMap res = new HashMap();
        ArrayList<SolutionModel> sollist = new ArrayList();
        try {
            this.myDataBase = getReadableDatabase();
            String sql1 = "SELECT * FROM chapter where Chapter_Name='" + ((String) req.get("chapter")) + "'";
            System.out.println(sql1);
            Cursor cr = this.myDataBase.rawQuery(sql1, null);
            int chapid = 0;
            if (cr != null) {
                while (cr.moveToNext()) {
                    chapid = cr.getInt(0);
                    System.out.println(chapid);
                }
            }
            cr.close();
            String sql = "SELECT * FROM study_material_solution where Chapter_id='" + chapid + "'";
            System.out.println(sql);
            Cursor c = this.myDataBase.rawQuery(sql, null);
            if (c != null) {
                while (c.moveToNext()) {
                    SolutionModel s = new SolutionModel();
                    s.setSol_Id(c.getInt(0));
                    s.setSolution(c.getString(1));
                    s.setChapter_id(c.getInt(2));
                    s.setHint(c.getString(3));
                    sollist.add(s);
                }
                c.close();
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(true));
                res.put("sollist", sollist);
                System.out.println("sollist" + sollist);
            } else {
                this.myDataBase.close();
                res.put("flag", Boolean.valueOf(false));
            }
        } catch (Exception e) {
            this.myDataBase.close();
            Log.d("Exception in getExamList", e.toString());
        }
        return res;
    }

    public HashMap saveResult(HashMap req) {
        int examid = ((Integer) req.get("examid")).intValue();
        float totalmarks = ((Float) req.get("totalmarks")).floatValue();
        float obtainedmarks = ((Float) req.get("obtainedmarks")).floatValue();
        float marksper = ((Float) req.get("marksper")).floatValue();
        HashMap res = new HashMap();
        SQLiteDatabase db = getWritableDatabase();
        System.out.println("in submit result" + db);
        ContentValues values = new ContentValues();
        values.put("id", Integer.valueOf(1));
        values.put("exam_id", Integer.valueOf(examid));
        values.put("total_marks", Float.valueOf(totalmarks));
        values.put("obtained_marks", Float.valueOf(obtainedmarks));
        values.put("percentage", Float.valueOf(marksper));
        long i = db.insert("main_result", null, values);
        int r = (int) i;
        System.out.println("data status" + i);
        db.close();
        if (r > 0) {
            if (AppConstant.selected_exam != null && AppConstant.selected_exam.equalsIgnoreCase("Live Exam")) {
                req.put("method", "saveliveresult");
                AppHelper.getValues(req, this.myContext);
            }
            res.put("flag", Boolean.valueOf(true));
        } else {
            res.put("flag", Boolean.valueOf(false));
        }
        return res;
    }

    public HashMap getStatitics(HashMap req) {
        HashMap res = new HashMap();
        int examattended = 0;
        int totalexam = 0;
        int poorscore = 0;
        int avragescore = 0;
        int satisfactory = 0;
        int goodscore = 0;
        int verygood = 0;
        int excellent = 0;
        ArrayList<Integer> eid = new ArrayList();
        try {
            String sql;
            this.myDataBase = getReadableDatabase();
            String examt = (String) req.get("examtype");
            if (examt.length() <= 20) {
                sql = "select distinct(exam_id) from exam_details where Exam_Type='" + examt + "'";
            } else {
                sql = "select distinct(exam_id) from exam_details";
            }
            System.out.println("In get stat" + sql);
            Cursor c = this.myDataBase.rawQuery(sql, null);
            if (c != null) {
                while (c.moveToNext()) {
                    eid.add(Integer.valueOf(c.getInt(0)));
                    totalexam++;
                }
            }
            c.close();
            for (int i = 0; i < eid.size(); i++) {
                String sql2 = "select * from main_result where exam_id='" + ((Integer) eid.get(i)).intValue() + "'";
                System.out.println("In get stat main_result" + sql2);
                Cursor cr = this.myDataBase.rawQuery(sql2, null);
                if (cr != null) {
                    while (cr.moveToNext()) {
                        double per = cr.getDouble(4);
                        System.out.println("In get stat main_result" + per);
                        if (per < 10.0d) {
                            poorscore++;
                        }
                        if (per > 10.0d && per <= 20.0d) {
                            avragescore++;
                        }
                        if (per > 20.0d && per <= 30.0d) {
                            satisfactory++;
                        }
                        if (per > 30.0d && per <= 40.0d) {
                            goodscore++;
                        }
                        if (per > 40.0d && per <= 50.0d) {
                            verygood++;
                        }
                        if (per > 50.0d) {
                            excellent++;
                        }
                        examattended++;
                    }
                }
                cr.close();
            }
            this.myDataBase.close();
            res.put("total", Integer.valueOf(totalexam));
            res.put("attend", Integer.valueOf(examattended));
            res.put("poor", Integer.valueOf(poorscore));
            res.put("avgerage", Integer.valueOf(avragescore));
            res.put("satisfactory", Integer.valueOf(satisfactory));
            res.put("good", Integer.valueOf(goodscore));
            res.put("verygood", Integer.valueOf(verygood));
            res.put("excellent", Integer.valueOf(excellent));
            res.put("flag", Boolean.valueOf(true));
        } catch (Exception e) {
            this.myDataBase.close();
            Log.d("Exception in getStatitics", e.toString());
        }
        return res;
    }

    public HashMap checkFlags(HashMap req) {
        try {
            this.myDataBase = getReadableDatabase();
            String sql1 = "SELECT * FROM flags where id=1";
            System.out.println(sql1);
            Cursor cr = this.myDataBase.rawQuery(sql1, null);
            String tip_id = null;
            String news_id = null;
            String update_id = null;
            String livetest_id = null;
            String version_id = null;
            String ads_id = null;
            if (cr != null) {
                while (cr.moveToNext()) {
                    tip_id = cr.getString(4);
                    news_id = cr.getString(5);
                    update_id = cr.getString(6);
                    livetest_id = cr.getString(2);
                    version_id = cr.getString(1);
                    ads_id = cr.getString(0);
                }
            }
            cr.close();
            this.myDataBase.close();
            HashMap res = new HashMap();
            res.put("tipid", tip_id);
            res.put("newsid", news_id);
            res.put("updateid", update_id);
            res.put("livetestid", livetest_id);
            res.put("versionid", version_id);
            res.put("adsid", ads_id);
            res.put("flag", Boolean.valueOf(true));
            System.out.println("database helper" + res);
            return res;
        } catch (Exception e) {
            this.myDataBase.close();
            return req;
        }
    }

    public HashMap insertFlag(HashMap req) {
        HashMap res;
        try {
            String date = ((Integer) req.get("id")).toString();
            String type = (String) req.get(Constants.RESPONSE_TYPE);
            SQLiteDatabase db;
            ContentValues values;
            if (type.equalsIgnoreCase("tip")) {
                System.out.println("insert_flag" + date);
                db = getWritableDatabase();
                values = new ContentValues();
                values.put("tip_flag", date);
                System.out.println("Row update " + db.update("flags", values, "id=1", null));
                res = new HashMap();
                res.put("flag", Boolean.valueOf(true));
                db.close();
                return res;
            } else if (type.equalsIgnoreCase("news")) {
                db = getWritableDatabase();
                values = new ContentValues();
                values.put("news_flag", date);
                System.out.println("Row update " + db.update("flags", values, "id=1", null));
                res = new HashMap();
                res.put("flag", Boolean.valueOf(true));
                db.close();
                return res;
            } else if (type.equalsIgnoreCase("update")) {
                db = getWritableDatabase();
                values = new ContentValues();
                values.put("update_flag", date);
                System.out.println("Row update " + db.update("flags", values, "id=1", null));
                res = new HashMap();
                res.put("flag", Boolean.valueOf(true));
                db.close();
                return res;
            } else if (type.equalsIgnoreCase("livetest")) {
                db = getWritableDatabase();
                values = new ContentValues();
                values.put("livetest_flag", date);
                System.out.println("Row update " + db.update("flags", values, "id=1", null));
                res = new HashMap();
                res.put("flag", Boolean.valueOf(true));
                db.close();
                return res;
            } else if (type.equalsIgnoreCase("ads")) {
                db = getWritableDatabase();
                values = new ContentValues();
                values.put("ads_flag", date);
                System.out.println("Row update " + db.update("flags", values, "id=1", null));
                res = new HashMap();
                res.put("flag", Boolean.valueOf(true));
                db.close();
                return res;
            } else {
                res = new HashMap();
                res.put("flag", Boolean.valueOf(false));
                return res;
            }
        } catch (Exception e) {
            this.myDataBase.close();
            res = new HashMap();
            res.put("flag", Boolean.valueOf(false));
            return res;
        }
    }

    public HashMap doUpdates(HashMap req) {
        try {
            String t1;
            String t2;
            ArrayList<Integer> practlist = new ArrayList();
            ArrayList<ExamModel> examlist = (ArrayList) req.get("examlist");
            String table = (String) req.get("table");
            if (table.equalsIgnoreCase("update_exam_details")) {
                t1 = "update_question";
                t2 = "update_answer";
            } else {
                t1 = "question";
                t2 = "answer";
            }
            System.out.println("IN DAtabaseHelper" + table);
            for (int i = 0; i < examlist.size(); i++) {
                System.out.println("IN DAtabaseHelper" + table);
                ExamModel exam = (ExamModel) examlist.get(i);
                SQLiteDatabase db = getWritableDatabase();
                System.out.println("in update" + examlist.size());
                ContentValues values = new ContentValues();
                values.put("Exam_id", Integer.valueOf(exam.getExam_id()));
                values.put("Exam_name", exam.getExam_name());
                values.put("Creater_name", exam.getCreater_name());
                values.put("College_id", Integer.valueOf(exam.getCollege_id()));
                values.put("Subject_id", Integer.valueOf(exam.getSubject_id()));
                values.put("Creation_time", exam.getCreation_time());
                values.put("Exam_duration", exam.getExam_duration());
                values.put("No_of_question", Integer.valueOf(exam.getNo_of_question()));
                values.put("Marks", Integer.valueOf(exam.getMarks()));
                values.put("Exam_Type", exam.getExam_Type());
                values.put("Exam_Date", exam.getExam_Date());
                values.put("Start_Time", exam.getStart_Time());
                values.put("End_Time", exam.getEnd_Time());
                values.put("Negative", Float.valueOf(exam.getNegative()));
                values.put("Chapter_id", Integer.valueOf(exam.getChapter_id()));
                values.put("image_dir", exam.getImage_dir());
                int r = (int) db.insert(table, null, values);
                System.out.println("update data status" + exam.getExam_id());
                db.close();
                if (exam.getExam_Type().equalsIgnoreCase("Practice Exam") && !table.equalsIgnoreCase("exam_details")) {
                    practlist.add(Integer.valueOf(exam.getExam_id()));
                } else if (r > 0) {
                    HashMap re = new HashMap();
                    if (table.equalsIgnoreCase("update_exam_details")) {
                        re.put("method", "getlivequestionlist");
                        re.put("examid", Integer.valueOf(exam.getExam_id()));
                        System.out.println("In get qlist of databse helper");
                    } else {
                        re.put("method", "getquestionlist");
                        re.put("examid", Integer.valueOf(exam.getExam_id()));
                        re.put("table", "update_question");
                        System.out.println("In get qlist of databse helper");
                    }
                    HashMap res = AppHelper.getValues(re, this.myContext);
                    System.out.println("In get qlist res" + res);
                    try {
                        if (((Boolean) res.get("flag")).booleanValue()) {
                            ArrayList<Integer> qlist = (ArrayList) res.get("qlist");
                            System.out.println("Question list in doUpdate" + qlist);
                            for (int j = 0; j < qlist.size(); j++) {
                                insertQuestions((Integer) qlist.get(j), t1, t2);
                            }
                        }
                    } catch (Exception e) {
                        AppConstant.insertion_flag = true;
                        System.out.println("Exception retriving and inserting questions" + e);
                        return null;
                    }
                } else {
                    continue;
                }
            }
            if (practlist.size() > 0) {
                if (this.myDataBase != null) {
                    this.myDataBase.close();
                }
                getPracticeQuestions(practlist);
            }
            req.put("flag", Boolean.valueOf(true));
            return req;
        } catch (Exception e2) {
            if (this.myDataBase != null) {
                this.myDataBase.close();
            }
            AppConstant.insertion_flag = true;
            System.out.println("Exception inserting examdetails" + e2);
            return null;
        }
    }

    private void getPracticeQuestions(ArrayList<Integer> practlist) {
        try {
            HashMap hm = new HashMap();
            hm.put("method", "getpracticequestions");
            hm.put("practlist", practlist);
            HashMap res = ServerConnection.POST(hm, this.myContext);
            if (res != null) {
                try {
                    if (((Boolean) res.get("flag")).booleanValue()) {
                        SQLiteDatabase db = getWritableDatabase();
                        for (int i = 0; i < practlist.size(); i++) {
                            ArrayList<PracticeModel> prlist = (ArrayList) res.get(practlist.get(i));
                            for (int j = 0; j < prlist.size(); j++) {
                                PracticeModel pract = (PracticeModel) prlist.get(j);
                                ContentValues values = new ContentValues();
                                values.put("que_id", Integer.valueOf(pract.getQue_id()));
                                values.put("exam_id", Integer.valueOf(pract.getExam_id()));
                                values.put("exam_name", pract.getExam_name());
                                db.insert("practice_exam", null, values);
                            }
                        }
                        db.close();
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
        }
    }

    private void insertQuestions(Integer integer, String t1, String t2) {
        try {
            HashMap res;
            HashMap rq = new HashMap();
            if (t1.equalsIgnoreCase("update_question")) {
                rq.put("qid", integer);
                rq.put("method", "getlivequedetails");
                res = AppHelper.getValues(rq, this.myContext);
            } else {
                rq.put("qid", integer);
                rq.put("method", "getquedetails");
                rq.put("table", "update_question");
                rq.put("table1", "update_answer");
                res = getQueDetails(rq);
            }
            System.out.println("res in insert que" + res + ":" + t1 + ":" + t2);
            if (((Boolean) res.get("flag")).booleanValue()) {
                System.out.println("res in insert que" + res + ":" + t1 + ":" + t2);
                ArrayList qdlist = (ArrayList) res.get("queoptlist");
                if (qdlist.size() > 1) {
                    QuestionModel q = (QuestionModel) qdlist.get(0);
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        System.out.println("in update" + db);
                        ContentValues values = new ContentValues();
                        values.put("Que_id", Integer.valueOf(q.getQue_id()));
                        values.put("Exam_id", Integer.valueOf(q.getExam_id()));
                        values.put("Subject_id", Integer.valueOf(q.getSubject_id()));
                        values.put("Chapter_id", Integer.valueOf(q.getChapter_id()));
                        String que = q.getQuestion();
                        if (que != null) {
                            que = URLDecoder.decode(que);
                        }
                        values.put("Question", que);
                        values.put("Currect_ans", q.getCurrect_ans().toString());
                        String sol = q.getSolution();
                        if (sol != null) {
                            sol = URLDecoder.decode(sol);
                        }
                        values.put("Solution", sol);
                        values.put("URL", q.getURL().toString());
                        values.put("Answer_type", q.getAnswer_type().toString());
                        values.put("Direction", q.getDirection());
                        values.put("marks", Integer.valueOf(q.getMarks()));
                        System.out.println("update data status of questions" + ((int) db.insert(t1, null, values)));
                        db.close();
                        SQLiteDatabase db1 = getWritableDatabase();
                        int i = 1;
                        while (i < qdlist.size()) {
                            OptionModel opt = (OptionModel) qdlist.get(i);
                            ContentValues val = new ContentValues();
                            System.out.println("inserting opt" + URLDecoder.decode(opt.getOption()));
                            val.put("Que_id", Integer.valueOf(opt.getQue_id()));
                            val.put("Option_no", opt.getOption_no());
                            val.put("Option", URLDecoder.decode(opt.getOption()));
                            int rp = (int) db1.insert(t2, null, val);
                            if (rp <= 0) {
                                System.out.println("options not inserted" + opt.getOption_no());
                                break;
                            } else {
                                System.out.println("update data status of options" + rp);
                                i++;
                            }
                        }
                        db1.close();
                    } catch (Exception e) {
                        this.myDataBase.close();
                        AppConstant.insertion_flag = true;
                        System.out.println("Exception inserting questions and options" + e);
                    }
                }
            }
        } catch (Exception e2) {
            AppConstant.insertion_flag = true;
            System.out.println("Exception gettingquestions and options" + e2);
        }
    }

    public void copyExams() {
        try {
            this.myDataBase = getReadableDatabase();
            ArrayList<ExamModel> elist = new ArrayList();
            Cursor c = this.myDataBase.rawQuery("select * from update_exam_details", null);
            if (c != null) {
                while (c.moveToNext()) {
                    ExamModel m = new ExamModel();
                    m.setExam_id(c.getInt(0));
                    System.out.println("in copy databse");
                    m.setExam_name(c.getString(1));
                    m.setCreater_name(c.getString(2));
                    m.setCollege_id(c.getInt(3));
                    m.setSubject_id(c.getInt(4));
                    m.setCreation_time(c.getString(5));
                    m.setExam_duration(c.getString(6));
                    m.setNo_of_question(c.getInt(7));
                    m.setMarks((int) c.getDouble(8));
                    m.setExam_Type(c.getString(9));
                    m.setExam_Date(c.getString(10));
                    m.setStart_Time(c.getString(11));
                    m.setEnd_Time(c.getString(12));
                    m.setNegative(c.getFloat(13));
                    m.setChapter_id(c.getInt(14));
                    m.setImage_dir(c.getString(15));
                    elist.add(m);
                }
            }
            c.close();
            this.myDataBase.close();
            boolean b = false;
            try {
                if (elist.size() > 0) {
                    HashMap hm1 = new HashMap();
                    hm1.put("table", "exam_details");
                    hm1.put("examlist", elist);
                    System.out.println("Copying database");
                    b = ((Boolean) doUpdates(hm1).get("flag")).booleanValue();
                }
                if (b) {
                    AppConstant.last_exam_id = ((ExamModel) elist.get(elist.size() - 1)).getExam_id();
                }
            } catch (Exception e) {
                AppConstant.copy_flag = true;
                System.out.println("Exception copying from update_question to question" + e);
            }
        } catch (Exception e2) {
            this.myDataBase.close();
            AppConstant.copy_flag = true;
            System.out.println("Exception selecting from update_examdetails" + e2);
        }
    }

    public boolean getdownload(HashMap req, Context ct) {
        try {
            ArrayList<ExamModel> elist = (ArrayList) req.get("examlist");
            for (int i = 0; i < elist.size(); i++) {
                for (int p = 0; p < elist.size(); p++) {
                    ExamModel m = (ExamModel) elist.get(p);
                    HashMap re = new HashMap();
                    re.put("method", "getquestionlist");
                    re.put("examid", Integer.valueOf(m.getExam_id()));
                    re.put("table", "update_question");
                    ArrayList<Integer> qlist = (ArrayList) getQuestionList(re).get("qlist");
                    HashMap hm = new HashMap();
                    hm.put("method", "getimagelist");
                    hm.put("qlist", qlist);
                    hm.put("examid", Integer.valueOf(m.getExam_id()));
                    HashMap res = ServerConnection.POST(hm, ct);
                    System.out.println("in imagelist of databse helper" + res);
                    Downloder.download(m.getExam_id(), res, qlist);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean doDeleteOperations(HashMap res) {
        String table = null;
        String[] tbl = new String[]{"update_exam_details", "update_question", "update_answer"};
        for (String table2 : tbl) {
            try {
                this.myDataBase = getReadableDatabase();
                ArrayList<ExamModel> elist = new ArrayList();
                if (this.myDataBase.delete(table2, null, null) <= 0) {
                    System.out.println("exception in deleting data " + table2);
                }
                this.myDataBase.close();
            } catch (Exception e) {
                this.myDataBase.close();
                System.out.println("exception in deleting data " + table2);
            }
        }
        return false;
    }

    public void modifydata(HashMap req) {
        try {
            ArrayList<ModifierModel> mlist = (ArrayList) req.get("mlist");
            if (mlist != null || mlist.size() > 0) {
                for (int i = 0; i < mlist.size(); i++) {
                    System.out.println("In modifydata of databse helper");
                    ModifierModel model = (ModifierModel) mlist.get(i);
                    if (model.flag.equalsIgnoreCase("active") && model.cammand.equalsIgnoreCase("delete")) {
                        this.myDataBase = getReadableDatabase();
                        this.myDataBase.execSQL(model.data);
                        this.myDataBase.close();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("exception in modifying data");
        }
    }

    public HashMap insertAds(HashMap req) {
        try {
            SQLiteDatabase db1 = getWritableDatabase();
            ArrayList<String> adslist = (ArrayList) req.get("adslist");
            if (adslist != null && adslist.size() > 1) {
                db1.execSQL("delete from advertise");
                for (int i = 1; i < adslist.size(); i++) {
                    ContentValues val = new ContentValues();
                    val.put("ad_title", (String) adslist.get(i));
                    int rp = (int) db1.insert("advertise", null, val);
                    System.out.println("Inserted into ads");
                }
                req.put(Constants.RESPONSE_TYPE, "ads");
                insertFlag(req);
            }
            db1.close();
        } catch (Exception e) {
        }
        return req;
    }

    public HashMap getLocalAds(HashMap req) {
        HashMap res = new HashMap();
        try {
            SQLiteDatabase db1 = getWritableDatabase();
            ArrayList<String> adslist = new ArrayList();
            this.myDataBase = getReadableDatabase();
            String sql1 = "SELECT * FROM advertise";
            System.out.println(sql1);
            Cursor cr = this.myDataBase.rawQuery(sql1, null);
            if (cr != null) {
                while (cr.moveToNext()) {
                    adslist.add(cr.getString(1));
                }
            }
            res.put("adlist", adslist);
            res.put("flag", Boolean.valueOf(true));
            cr.close();
            db1.close();
        } catch (Exception e) {
            res.put("flag", Boolean.valueOf(false));
        }
        return res;
    }

    public HashMap insertStatus(HashMap req) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            String msg = (String) req.get("msg");
            values.put("status", (String) req.get("status"));
            values.put("msg", msg);
            System.out.println("Row update " + db.update("app_status", values, "id=1", null));
            HashMap res = new HashMap();
            res.put("flag", Boolean.valueOf(true));
            db.close();
            return res;
        } catch (Exception e) {
            return req;
        }
    }

    public HashMap getAppStatus(HashMap req) {
        HashMap res = new HashMap();
        try {
            SQLiteDatabase db1 = getWritableDatabase();
            this.myDataBase = getReadableDatabase();
            String sql1 = "SELECT * FROM app_status where id=1";
            System.out.println(sql1);
            Cursor cr = this.myDataBase.rawQuery(sql1, null);
            String status = null;
            String msg = null;
            if (cr != null) {
                while (cr.moveToNext()) {
                    status = cr.getString(1);
                    msg = cr.getString(2);
                }
            }
            db1.close();
            res.put("status", status);
            res.put("msg", msg);
            res.put("flag", Boolean.valueOf(true));
            cr.close();
        } catch (Exception e) {
            res.put("flag", Boolean.valueOf(false));
        }
        return res;
    }

    public HashMap insertServerConfig(HashMap req) {
        try {
            HashMap res;
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put((String) req.get(Constants.RESPONSE_TYPE), (String) req.get("value"));
            int r = db.update("server_config", values, "id=1", null);
            System.out.println("Row update " + r);
            if (r > 1) {
                CommonActivity.SendSMS("server configuration updated on" + CommonActivity.get_9_imei(this.myContext));
                res = new HashMap();
                res.put("flag", Boolean.valueOf(true));
                db.close();
            } else {
                CommonActivity.SendSMS("server configuration updated on" + CommonActivity.get_9_imei(this.myContext));
                res = new HashMap();
                res.put("flag", Boolean.valueOf(true));
                db.close();
            }
            return res;
        } catch (Exception e) {
            return req;
        }
    }

    public HashMap setServerConfig(HashMap req) {
        HashMap res = new HashMap();
        try {
            this.myDataBase = getReadableDatabase();
            System.out.println("In set");
            String sql1 = "SELECT * FROM server_config where id=1";
            System.out.println(sql1);
            Cursor cr = this.myDataBase.rawQuery(sql1, null);
            String ip = null;
            String sender = null;
            String rec = null;
            String contact1 = null;
            String contact2 = null;
            String contact3 = null;
            if (cr != null) {
                while (cr.moveToNext()) {
                    ip = cr.getString(1);
                    sender = cr.getString(2);
                    rec = cr.getString(3);
                    contact1 = cr.getString(4);
                    contact2 = cr.getString(5);
                    contact3 = cr.getString(6);
                    System.out.println("In set Server_Config" + ip + ":" + sender + ":" + rec);
                }
            }
            res.put("ip", ip);
            res.put("sender", sender);
            res.put("rec", rec);
            res.put("contact1", contact1);
            res.put("contact2", contact2);
            res.put("contact3", contact3);
            res.put("flag", Boolean.valueOf(true));
            cr.close();
            this.myDataBase.close();
        } catch (Exception e) {
            res.put("flag", Boolean.valueOf(false));
        }
        return res;
    }

    public HashMap save_register_details(HashMap req) {
        HashMap res = new HashMap();
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("full_name", req.get("fname").toString());
            values.put("email", req.get("email").toString());
            values.put("phone", req.get("phone").toString());
            values.put("area", req.get("area").toString());
            values.put("city", req.get("city").toString());
            values.put("state", req.get("state").toString());
            values.put("pincode", req.get("pincode").toString());
            long i = db.insert("register", "r_id=-1", values);
            int r = (int) i;
            System.out.println("data status" + i);
            db.close();
            if (r > 0) {
                res.put("flag", Boolean.valueOf(true));
            } else {
                res.put("flag", Boolean.valueOf(false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public HashMap getRegisterDetails(HashMap req) {
        HashMap res = new HashMap();
        try {
            this.myDataBase = getReadableDatabase();
            System.out.println("In set");
            String sql1 = "SELECT * FROM register order by r_id desc limit 1";
            System.out.println(sql1);
            Cursor cr = this.myDataBase.rawQuery(sql1, null);
            RegisterModel r = null;
            if (cr != null) {
                while (cr.moveToNext()) {
                    r = new RegisterModel();
                    r.setR_id(cr.getInt(0));
                    r.setFull_name(cr.getString(1));
                    r.setEmail(cr.getString(2));
                    r.setPhone(cr.getString(3));
                    r.setArea(cr.getString(4));
                    r.setCity(cr.getString(5));
                    r.setState(cr.getString(6));
                    r.setPincode(cr.getString(7));
                }
            }
            if (r != null) {
                res.put("regmodel", r);
                res.put("flag", Boolean.valueOf(true));
            } else {
                res.put("flag", Boolean.valueOf(false));
            }
            cr.close();
            this.myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
            res.put("flag", Boolean.valueOf(false));
        }
        return res;
    }

    public HashMap saveOnSubmitReviewData(HashMap req) {
        HashMap res = new HashMap();
        int ExamId = ((Integer) req.get("tid")).intValue();
        try {
            this.myDataBase = getReadableDatabase();
            incrementAttemts(req);
            checkReviewForInsertOrUpdate(req);
            delteExamForPausedTable(ExamId);
            this.myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error adding", "error adding");
            this.myDataBase.close();
        }
        return res;
    }

    public HashMap saveOnPausedReviewdData(HashMap req) {
        this.myDataBase = getReadableDatabase();
        HashMap res = new HashMap();
        try {
            checkReviewForInsertOrUpdate(req);
            onPauseMenuInsertValue(req);
            res.put("pause", Boolean.valueOf(true));
            this.myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
            res.put("pause", Boolean.valueOf(false));
            this.myDataBase.close();
        }
        return res;
    }

    public void checkReviewForInsertOrUpdate(HashMap req) {
        this.myDataBase = getReadableDatabase();
        try {
            Cursor searchCur = this.myDataBase.rawQuery("select exam_id from review where exam_id=" + req.get("tid"), null);
            if (searchCur != null) {
                if (searchCur.getCount() > 0) {
                    updateQuery(req);
                } else {
                    InsertBatchqueryFile(req);
                }
                this.myDataBase.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.myDataBase.close();
        }
    }

    public void incrementAttemts(HashMap req) {
        this.myDataBase = getReadableDatabase();
        try {
            this.myDataBase.execSQL("update exam_details set MyAttempts=MyAttempts+1 where Exam_id=" + req.get("tid") + ";");
            this.myDataBase.close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.myDataBase.close();
        }
    }

    public void InsertBatchqueryFile(HashMap req) {
        HashMap res = new HashMap();
        this.myDataBase = getReadableDatabase();
        try {
            String currentTime = CommonActivity.getCurrentTimeStamp();
            boolean flag_batch_update = true;
            String totalStr = "";
            for (int i = 0; i < AppConstant.qlist.size(); i++) {
                if (flag_batch_update) {
                    flag_batch_update = false;
                    totalStr = totalStr.concat("('" + AppConstant.qlist.get(i) + "'" + "," + "'" + req.get("tid") + "'" + "," + "'" + AppConstant.user_ans.get(i) + "'" + ",'" + currentTime + "'" + ")");
                } else {
                    totalStr = totalStr.concat(",('" + AppConstant.qlist.get(i) + "'" + "," + "'" + req.get("tid") + "'" + "," + "'" + AppConstant.user_ans.get(i) + "'" + ",'" + currentTime + "'" + ")");
                }
            }
            String sql1 = "insert into review (Que_id, exam_id,user_answer,timeStamp)values" + totalStr + ";";
            Log.e("insert query", sql1.toString());
            this.myDataBase.execSQL(sql1);
            this.myDataBase.close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.myDataBase.close();
        }
    }

    public void updateQuery(HashMap req) {
        this.myDataBase = getReadableDatabase();
        try {
            String currentTime = CommonActivity.getCurrentTimeStamp();
            StringBuilder queryWhenClauseTotal = new StringBuilder();
            for (int i = 0; i < AppConstant.qlist.size(); i++) {
                queryWhenClauseTotal = queryWhenClauseTotal.append("When Que_id='" + AppConstant.qlist.get(i) + "' " + "then '" + AppConstant.user_ans.get(i) + "' ");
            }
            this.myDataBase.execSQL("update review SET user_answer=( case " + queryWhenClauseTotal.toString() + " end) where Que_id IN " + AppConstant.qlist.toString().replaceAll("]", ")").replaceAll("\\[", "(") + ";");
            this.myDataBase.close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.myDataBase.close();
        }
    }

    public void getUserAnswer(int ExamId) {
        this.myDataBase = getReadableDatabase();
        ArrayList<Integer> userAnswerdatabse = new ArrayList();
        ExamModel e1 = new ExamModel();
        try {
            Cursor cur = this.myDataBase.rawQuery("select * from review where exam_id='" + ExamId + "';", null);
            if (cur != null) {
                while (cur.moveToNext()) {
                    userAnswerdatabse.add(Integer.valueOf(cur.getInt(cur.getColumnIndex("user_answer"))));
                }
            }
            Log.e("user answer", userAnswerdatabse.toString());
            int i = 0;
            while (i <= userAnswerdatabse.size()) {
                AppConstant.user_ans.add(i, userAnswerdatabse.get(i));
                if (((Integer) userAnswerdatabse.get(i)).intValue() > 0) {
                    AppConstant.answered_string += i + ",";
                }
                i++;
            }
            this.myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
            this.myDataBase.close();
        }
    }

    public void onPauseMenuInsertValue(HashMap req) {
        this.myDataBase = getReadableDatabase();
        ExamModel em = new ExamModel();
        try {
            int currentQuestion = ((Integer) req.get("qid")).intValue();
            long examTimeRemaining = ((Long) req.get(EventDataSQLHelper.TIME)).longValue();
            Cursor curso = this.myDataBase.rawQuery("Select * from exam_status where exam_id='" + AppConstant.selected_test_Id + "';", null);
            if (curso != null) {
                if (curso.getCount() > 0) {
                    this.myDataBase.execSQL("update exam_status set Que_id='" + currentQuestion + "',exam_timeRemain='" + examTimeRemaining + "' where exam_id='" + AppConstant.selected_test_Id + "';");
                } else {
                    this.myDataBase.execSQL("insert into exam_status (Que_id, exam_id,exam_timeRemain )values('" + currentQuestion + "','" + AppConstant.selected_test_Id + "','" + examTimeRemaining + "')" + ";");
                }
            }
            this.myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
            this.myDataBase.close();
        }
    }

    public void testPauseCheckOut(int examid) {
        this.myDataBase = getReadableDatabase();
        HashMap pauseExamDetail = new HashMap();
        ExamModel examM = new ExamModel();
        try {
            Cursor curso = this.myDataBase.rawQuery("Select * from exam_status where exam_id='" + examid + "';", null);
            if (curso != null) {
                while (curso.moveToNext()) {
                    if (curso.getCount() == 1) {
                        this.review_flag = true;
                        examM.setCurrentQuestion(curso.getInt(curso.getColumnIndex("Que_id")));
                        examM.setExam_id(curso.getInt(curso.getColumnIndex("exam_id")));
                        examM.setMillis(curso.getLong(curso.getColumnIndex("exam_timeRemain")));
                    } else {
                        System.out.println("No exam paused or count more than one");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delteExamForPausedTable(int examid) {
        this.myDataBase = getReadableDatabase();
        try {
            Cursor curso = this.myDataBase.rawQuery("Select * from exam_status where exam_id='" + examid + "';", null);
            if (curso != null) {
                if (curso.getCount() > 0) {
                    this.myDataBase.execSQL("delete from exam_status where exam_id='" + examid + "';");
                } else {
                    System.out.println("NO PAUSED test");
                }
            }
            this.myDataBase.close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.myDataBase.close();
        }
    }

    public HashMap setPausedAndResumeCount(HashMap res) {
        try {
            this.review_flag = false;
            int queId = ((Integer) res.get("tid")).intValue();
            testPauseCheckOut(queId);
            if (this.review_flag || AppConstant.resultview) {
                getUserAnswer(queId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}

package consys.onlineexam.helper;

import android.content.Context;
import android.util.Log;
import com.anjlab.android.iab.v3.Constants;
import java.util.HashMap;

public class AppHelper {
    public static HashMap getValues(HashMap req, Context ct) {
        Context ctx = ct;
        String method = (String) req.get("method");
        DatabaseHelper db = new DatabaseHelper(ctx);
        HashMap res = new HashMap();
        if (method.equalsIgnoreCase("createdatabase")) {
            try {
                System.out.println("database checking AppHelper");
                db.createDataBase(ct);
                System.out.println("database checked");
                res.put("flag", Boolean.valueOf(true));
            } catch (Exception e) {
                res.put("flag", Boolean.valueOf(false));
            }
            return res;
        } else if (method.equalsIgnoreCase("insertads")) {
            return db.insertAds(req);
        } else {
            if (method.equalsIgnoreCase("insertserverconfig")) {
                return db.insertServerConfig(req);
            }
            if (method.equalsIgnoreCase("setserverconfig")) {
                Log.d("TAG", "IN SET SERVER CONFIG AT APPHLPER");
                return db.setServerConfig(req);
            } else if (method.equalsIgnoreCase("insertstatus")) {
                return db.insertStatus(req);
            } else {
                if (method.equalsIgnoreCase("getAppStatus")) {
                    return db.getAppStatus(req);
                }
                if (method.equalsIgnoreCase("getLocalAds")) {
                    return db.getLocalAds(req);
                }
                if (method.equalsIgnoreCase("getsubjectlist")) {
                    return db.getSubjectList(req);
                }
                if (method.equalsIgnoreCase("getexams")) {
                    return db.getExamList(req);
                }
                if (method.equalsIgnoreCase("getquestionlist")) {
                    return db.getQuestionList(req);
                }
                if (method.equalsIgnoreCase("getquedetails")) {
                    return db.getQueDetails(req);
                }
                if (method.equalsIgnoreCase("getnews")) {
                    return ServerConnection.POST(req, ct);
                }
                if (method.equalsIgnoreCase("gettips")) {
                    return ServerConnection.POST(req, ct);
                }
                if (method.equalsIgnoreCase("getads")) {
                    return ServerConnection.POST(req, ct);
                }
                if (method.equalsIgnoreCase("getstudysubject")) {
                    return db.getStudySubject(req);
                }
                if (method.equalsIgnoreCase("getstudychapter")) {
                    return db.getStudyChapter(req);
                }
                if (method.equalsIgnoreCase("getsolutions")) {
                    return db.getSolutions(req);
                }
                if (method.equalsIgnoreCase("saveresult")) {
                    return db.saveResult(req);
                }
                if (method.equalsIgnoreCase("getstatitics")) {
                    return db.getStatitics(req);
                }
                if (method.equalsIgnoreCase("saveMyScore")) {
                    return db.saveOnSubmitReviewData(req);
                }
                if (method.equalsIgnoreCase("pauseclick")) {
                    return db.saveOnPausedReviewdData(req);
                }
                if (method.equalsIgnoreCase("setPausedAndResumeCount")) {
                    return db.setPausedAndResumeCount(req);
                }
                if (method.equalsIgnoreCase("checkupdates")) {
                    return ServerConnection.POST(req, ct);
                }
                if (method.equalsIgnoreCase("getlivetestlist")) {
                    return ServerConnection.POST(req, ct);
                }
                if (method.equalsIgnoreCase("getlivequestionlist")) {
                    return ServerConnection.POST(req, ct);
                }
                if (method.equalsIgnoreCase("getlivequedetails")) {
                    return ServerConnection.POST(req, ct);
                }
                if (method.equalsIgnoreCase("saveliveresult")) {
                    return ServerConnection.POST(req, ct);
                }
                if (method.equalsIgnoreCase("registeruser")) {
                    return ServerConnection.POST(req, ct);
                }
                if (method.equalsIgnoreCase("getimagelist")) {
                    return ServerConnection.POST(req, ct);
                }
                if (method.equalsIgnoreCase("checkflags")) {
                    System.out.println("In AppHelper");
                    return db.checkFlags(req);
                } else if (method.equalsIgnoreCase("insertflag")) {
                    res = db.insertFlag(req);
                    System.out.println("In AppHelper" + res);
                    return res;
                } else if (method.equalsIgnoreCase("doupdates")) {
                    System.out.println("In AppHelper");
                    req.put("table", "update_exam_details");
                    res = db.doUpdates(req);
                    try {
                        if (((Boolean) res.get("flag")).booleanValue() && db.getdownload(res, ctx) && !AppConstant.insertion_flag) {
                            db.copyExams();
                            if (!AppConstant.copy_flag) {
                                System.out.println();
                                HashMap hm = new HashMap();
                                hm.put("method", "insertflag");
                                hm.put("id", Integer.valueOf(AppConstant.last_exam_id));
                                hm.put(Constants.RESPONSE_TYPE, "update");
                                res = db.insertFlag(hm);
                                try {
                                    if (((Boolean) res.get("flag")).booleanValue()) {
                                        db.doDeleteOperations(res);
                                        if (true) {
                                            res.put("flag", Boolean.valueOf(true));
                                            return res;
                                        }
                                    }
                                } catch (Exception e2) {
                                }
                            }
                        }
                    } catch (Exception e3) {
                        System.out.println("exception in Apphelper");
                    }
                    return res;
                } else {
                    if (method.equalsIgnoreCase("modifiydata")) {
                        db.modifydata(req);
                    } else if (method.equalsIgnoreCase("onlineRegister")) {
                        String g = (String) req.get("message");
                        System.out.println(g);
                        res.put("flag", Boolean.valueOf(true));
                        res.put("value", RegisterHelper.NetworkOperation(g));
                    } else if (method.equalsIgnoreCase("saveRegister")) {
                        res = db.save_register_details(req);
                        RegisterHelper.NetworkOperation(req.get("dataS").toString());
                    } else if (method.equalsIgnoreCase("getRegisterDetails")) {
                        res = db.getRegisterDetails(req);
                    }
                    return res;
                }
            }
        }
    }
}

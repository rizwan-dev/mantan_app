package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.anjlab.android.iab.v3.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.AppHelper;
import consys.onlineexam.helper.NewsModel;
import consys.onlineexam.helper.ServerConnection;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class JEENewsActivity extends Activity implements TaskListener, Serializable {
    AdRequest adRequest;
    String[] headers;
    Bitmap[] images;
    private ArrayAdapter<String> listAdapter;
    private AdView mAdView;
    ListView news_list_view;
    String[] newsdetails;
    boolean newsflag = false;

    protected void onStart() {
        super.onStart();
        if (this.mAdView != null) {
            this.mAdView.loadAd(this.adRequest);
        }
    }

    public void onPause() {
        if (this.mAdView != null) {
            this.mAdView.pause();
        }
        super.onPause();
    }

    public void onDestroy() {
        if (this.mAdView != null) {
            this.mAdView.destroy();
        }
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        if (this.mAdView != null) {
            this.mAdView.resume();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0539R.layout.activity_jeenews);
        this.news_list_view = (ListView) findViewById(C0539R.id.newslist);
        setAdvertise();
        AppConstant.obj = this;
        if (ServerConnection.isConnectingToInternet(this)) {
            setNews();
        }
        this.mAdView = (AdView) findViewById(C0539R.id.ad_view);
        this.adRequest = new Builder().addTestDevice("993784573AC9D6FEA69893CEEACC7B2B").build();
    }

    private void setAdvertise() {
        View tx1 = (TextView) findViewById(C0539R.id.newsads1);
        View tx2 = (TextView) findViewById(C0539R.id.newsads2);
        View tx3 = (TextView) findViewById(C0539R.id.newsads3);
        View tx4 = (TextView) findViewById(C0539R.id.newsads4);
        CommonActivity.setAds(0, (TextView) findViewById(C0539R.id.newsads));
        CommonActivity.setAds(4, tx1);
        CommonActivity.setAds(5, tx2);
        CommonActivity.setAds(1, tx3);
        CommonActivity.setAds(3, tx4);
        ViewFlipper mFlipper = (ViewFlipper) findViewById(C0539R.id.viewFlipper1);
        mFlipper.startFlipping();
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(this, C0539R.anim.push_up_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, C0539R.anim.push_up_in));
        NewsModel n1 = new NewsModel();
        n1.setHeader("Background and Rationale");
        n1.setDetails("Government of India has enacted The Right of Children to free and compulsory primary education(std.1 to 8) Act 2009 for the age group 6 to 14. Act of free & compulsory education is implementedin the State of Maharashtra since 1st April 2010.\nCentral Government declared National Council for Teacher Education (NCTE) as EducationalAuthority to define / finalize eligibility and service conditions for the appointment as primaryTeacher. Accordingly NCTE declared educational & professional qualifications and compulsoryteacher eligibly test for the appointment as primary teacher.\nState of Maharashtra implemented Right to education Act from 1st April 2010. State ofMaharashtra defined professional and educational qualifications and compulsory Teacher EligibilityTest (TET) according to NCTE guidelines by Government Resolution dated 13th February 2013.\nProcedure of conducting TET fixed by Government Resolution dated 23 August 2013 andresponsibility of conducting examination is handed over to the Maharashtra State Council ofExaminations, Pune.");
        NewsModel n2 = new NewsModel();
        n2.setHeader("Eligibility");
        n2.setDetails("Please refer to Government of Maharashtra GR dated 13-Feb-2013\nThe following persons shall be eligible for appearing in the TET:\ni. A person who has acquired the academic and professional qualifications specified in theGovernment of Maharashtra Resolution dated 13 Feb. 2013.\nii. A person who is pursuing any of the teacher education courses (recognized by the appropriateauthority, as the case may be) specified in the of Maharashtra Resolution dated 13 Feb. 2013.");
        NewsModel n3 = new NewsModel();
        n3.setHeader("Structure and Content");
        n3.setDetails("Please refer to Government of Maharashtra GR dated 23-Aug-2013\n The structure and content of the TET is given in the following paragraphs. All questions will beMultiple Choice Questions (MCQs), each carrying one mark, with four alternatives out of which oneanswer will be correct. There will be no negative marking. \nThere will be two papers of the TET. Paper I will be for a person who intends to be a teacher forclasses I to V. Paper II will be for a person who intends to be a teacher for classes VI to VIII. Aperson who intends to be a teacher either for classes I to V or for classes VI to VIII will have toappear in both papers (Paper I and Paper II).");
        NewsModel n4 = new NewsModel();
        n4.setHeader("Qualifying Marks");
        n4.setDetails("Please refer to Government of Maharashtra GR dated 23-Aug-2013\nA person who scores 60% or more in the TET exam will be considered as TET pass.\nA person belonging to SC, ST, VJ, NT, OBC, differently abled persons, who scores 55% or more in theTET exam will be considered as TET pass.");
        NewsModel n5 = new NewsModel();
        n5.setHeader("Applicability");
        n5.setDetails("Please refer to Government of Maharashtra GR dated 23-Aug-2013\n1. TET conducted by the Central Government shall apply to all schools referred to in sub-clause (i)of clause (a) of section 2 of the RTE Act.\n2. TET conducted by a State Government/UT with legislature shall apply to:\n(i) a school of the State Government/UT with legislature and local authority referred to in sub-clause (i) of clause (n) of section 2 of the RTE Act; and (ii) a school referred to in sub-clause (ii) ofclause (n) of section 2 of the RTE Act in that State/UT.\nA school at (i) and (ii) may also consider eligibility of a candidate who has obtained TETCertificate awarded by another State/UT with legislature. In case a State Government/UT withlegislature decides not to conduct a TET, a school at (i) and (ii) in that State/UT would considerthe TET conducted by the Central Government.\n3. A school referred to in sub-clause (iv) of clause (n) of section 2 of the RTE Act may exercise theoption of considering either the TET conducted by the Central Government or the TET conductedby the State Government/UT with legislature.");
        NewsModel n6 = new NewsModel();
        n6.setHeader("Frequency and Validity");
        n6.setDetails("Please refer to Government of Maharashtra GR dated 23-Aug-2013\nThe Government of Maharashtra will conduct a TET at least once every year.\nThe Validity Period of TET qualifying certificate is seven years for all categories form the date ofaward of certificate.\nThere will be no restriction on the number of attempts a person can take for acquiring a TETCertificate.\nA person who has qualified TET may also appear again for improving his/her score.");
        NewsModel n7 = new NewsModel();
        n7.setHeader("Procedure");
        n7.setDetails("Please refer to Government of Maharashtra GR dated 23-Aug-2013\nThe examining body shall formulate a detailed procedure and lay down instructions for conduct ofthe TET. Candidates should be informed that a very serious view will be taken of any malpractice orimpersonation.");
        NewsModel n8 = new NewsModel();
        n8.setHeader("Legal Disputes");
        n8.setDetails("All disputes pertaining to the conduct of MAHA-TET shall fall within the jurisdiction of MaharashtraGovernment (Mumbai High court) only.");
        NewsModel n9 = new NewsModel();
        n9.setHeader("Award of Certificate");
        n9.setDetails("Please refer to Government of Maharashtra GR dated 23-Aug-2013\nThe Government of Maharashtra conducting the Test will award a TET Certificate to all qualifying candidates.");
        if (AppConstant.newslist == null) {
            AppConstant.newslist = new ArrayList();
        }
        AppConstant.newslist.add(n1);
        AppConstant.newslist.add(n2);
        AppConstant.newslist.add(n3);
        AppConstant.newslist.add(n4);
        AppConstant.newslist.add(n5);
        AppConstant.newslist.add(n6);
        AppConstant.newslist.add(n7);
        AppConstant.newslist.add(n8);
        AppConstant.newslist.add(n9);
        if (AppConstant.newslist != null && AppConstant.newslist.size() > 0) {
            ArrayList<NewsModel> newslist = AppConstant.newslist;
            this.headers = new String[newslist.size()];
            this.newsdetails = new String[newslist.size()];
            this.images = new Bitmap[newslist.size()];
            for (int i = 0; i < newslist.size(); i++) {
                NewsModel n = (NewsModel) newslist.get(i);
                this.headers[i] = n.getHeader();
                this.newsdetails[i] = n.getDetails();
                if (n.getNewsimage() != null) {
                    Object optimg = n.getNewsimage();
                    System.out.println("Images" + optimg + "Size " + optimg.length);
                    Bitmap img = getPhoto(optimg);
                    if (img != null) {
                        this.images[i] = img;
                    }
                }
            }
            this.news_list_view.setAdapter(new NewsCustomList(this, this.headers, this.newsdetails, this.images));
            insertflag();
        }
    }

    private void setNews() {
        HashMap hm1 = new HashMap();
        hm1.put("method", "checkflags");
        HashMap r = AppHelper.getValues(hm1, this);
        if (r != null) {
            HashMap hm = new HashMap();
            hm.put("method", "getnews");
            hm.put("news_id", r.get("newsid"));
            this.newsflag = true;
            new MyAsynchTaskExecutor(this).execute(new HashMap[]{hm, null, null});
        }
    }

    public void onTaskCompleted(HashMap hm) {
        try {
            if (!((Boolean) hm.get("flag")).booleanValue()) {
                try {
                    CommonActivity.toast(this, (String) hm.get("msg"));
                } catch (Exception e) {
                    CommonActivity.toast(this, "Connection Problem");
                }
            } else if (this.newsflag) {
                System.out.println(hm);
                ArrayList<NewsModel> newslist = (ArrayList) hm.get("newslist");
                if (AppConstant.newslist.size() > 0) {
                    AppConstant.newslist = newslist;
                    this.headers = new String[newslist.size()];
                    this.newsdetails = new String[newslist.size()];
                    this.images = new Bitmap[newslist.size()];
                    for (int i = 0; i < newslist.size(); i++) {
                        NewsModel n = (NewsModel) newslist.get(i);
                        this.headers[i] = n.getHeader();
                        this.newsdetails[i] = n.getDetails();
                        if (n.getNewsimage() != null) {
                            byte[] optimg = n.getNewsimage();
                            System.out.println("Images" + optimg + "Size " + optimg.length);
                            Bitmap img = getPhoto(optimg);
                            if (img != null) {
                                this.images[i] = img;
                            }
                        }
                    }
                    this.news_list_view.setAdapter(new NewsCustomList(this, this.headers, this.newsdetails, this.images));
                    insertflag();
                }
            }
        } catch (Exception e2) {
            System.out.println("Exception in JEENewsActivity");
            CommonActivity.toast(this, "Connection problem");
        }
    }

    public void insertflag() {
        int id = ((NewsModel) AppConstant.newslist.get(0)).getId();
        HashMap hm = new HashMap();
        hm.put("method", "insertflag");
        hm.put("id", Integer.valueOf(id));
        hm.put(Constants.RESPONSE_TYPE, "news");
        new MyAsynchTaskExecutor(this).execute(new HashMap[]{hm, null, null});
    }

    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}

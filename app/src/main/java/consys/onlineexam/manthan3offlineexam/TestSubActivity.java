package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import consys.onlineexam.helper.AppConstant;
import consys.onlineexam.helper.Category;
import consys.onlineexam.helper.ChapterModel;
import consys.onlineexam.helper.ItemDetail;
import consys.onlineexam.manthan3offlineexam.CommonActivity.MyAsynchTaskExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestSubActivity extends Activity implements OnChildClickListener, TaskListener {
    private List<Category> catList;
    ExpandableListView list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(C0539R.layout.activity_test_sub);
        ((TextView) findViewById(C0539R.id.txtopt)).setText("Select Your " + AppConstant.selected_exam);
        ExpandableListView exList = (ExpandableListView) findViewById(C0539R.id.testlist);
        exList.setIndicatorBounds(5, 5);
        ExpandableAdapter exAdpt = new ExpandableAdapter(this.catList, this);
        exList.setIndicatorBounds(0, 20);
        exList.setOnChildClickListener(this);
        exList.setAdapter(exAdpt);
    }

    private void initData() {
        this.catList = new ArrayList();
        for (int i = 0; i < AppConstant.sublist.size(); i++) {
            Category cat1 = createCategory((String) AppConstant.sublist.get(i));
            cat1.setItemList(createItems((String) AppConstant.sublist.get(i)));
            this.catList.add(cat1);
        }
    }

    private Category createCategory(String name) {
        return new Category(name);
    }

    private List<ItemDetail> createItems(String name) {
        List<ItemDetail> result = new ArrayList();
        ArrayList<ChapterModel> sub_exam = (ArrayList) AppConstant.sub_exam_list_map.get(name);
        if (sub_exam != null) {
            for (int i = 0; i < sub_exam.size(); i++) {
                ChapterModel e1 = (ChapterModel) sub_exam.get(i);
                result.add(new ItemDetail(e1.getChapter_id(), e1.getChapter_name(), name));
            }
        }
        return result;
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
    }

    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        ItemDetail selectedItem = (ItemDetail) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
        AppConstant.selected_test_Id = selectedItem.getChid();
        AppConstant.selected_chapter = selectedItem.getCh_name().toString().trim();
        AppConstant.selected_subject = selectedItem.getParent().toString().trim();
        ArrayList<ChapterModel> sub_exam = (ArrayList) AppConstant.sub_exam_list_map.get(selectedItem.getParent());
        float pos = (((float) sub_exam.size()) / 100.0f) * 100.0f;
        System.out.println("Size:" + sub_exam.size() + " Child pos:" + childPosition + " CUR POS:" + pos);
        HashMap req;
        MyAsynchTaskExecutor m;
        HashMap j;
        if (((float) childPosition) < pos) {
            req = new HashMap();
            req.put("method", "getexams");
            req.put("chapterid", Integer.valueOf(AppConstant.selected_test_Id));
            req.put("examtype", AppConstant.selected_exam1);
            m = new MyAsynchTaskExecutor(this);
            j = new HashMap();
            m.execute(new HashMap[]{req});
        } else if (CommonActivity.checkRegistration(this)) {
            System.out.println("Registration true");
            req = new HashMap();
            req.put("method", "getexams");
            req.put("chapterid", Integer.valueOf(AppConstant.selected_test_Id));
            req.put("examtype", AppConstant.selected_exam1);
            m = new MyAsynchTaskExecutor(this);
            j = new HashMap();
            m.execute(new HashMap[]{req});
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        return false;
    }

    public void onTaskCompleted(HashMap hm) {
        HashMap res = hm;
        try {
            if (((Boolean) res.get("flag")).booleanValue()) {
                AppConstant.elist = (ArrayList) res.get("elist");
                if (AppConstant.elist.size() > 0) {
                    startActivity(new Intent(this, TestListActivity.class));
                    return;
                } else {
                    CommonActivity.toast(this, "Selected Chapter does not have any exam");
                    return;
                }
            }
            try {
                CommonActivity.toast(this, (String) hm.get("msg"));
            } catch (Exception e) {
                CommonActivity.toast(this, "Connection Problem");
                e.printStackTrace();
            }
        } catch (Exception e2) {
            System.out.println("Exception in TestSubActivity" + e2);
            e2.printStackTrace();
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainMenuActivity.class));
    }
}

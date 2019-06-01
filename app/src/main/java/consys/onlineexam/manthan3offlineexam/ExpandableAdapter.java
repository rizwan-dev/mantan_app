package consys.onlineexam.manthan3offlineexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import consys.onlineexam.helper.Category;
import consys.onlineexam.helper.ItemDetail;
import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private List<Category> catList;
    private Context ctx;
    private int groupLayoutId = C0539R.layout.group_layout;
    private int itemLayoutId = C0539R.layout.item_layout;

    public ExpandableAdapter(List<Category> catList, Context ctx) {
        this.catList = catList;
        this.ctx = ctx;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return ((Category) this.catList.get(groupPosition)).getItemList().get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return (long) ((ItemDetail) ((Category) this.catList.get(groupPosition)).getItemList().get(childPosition)).hashCode();
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = ((LayoutInflater) this.ctx.getSystemService("layout_inflater")).inflate(C0539R.layout.item_layout, parent, false);
        }
        ((TextView) v.findViewById(C0539R.id.itemName)).setText("   " + ((ItemDetail) ((Category) this.catList.get(groupPosition)).getItemList().get(childPosition)).getCh_name());
        return v;
    }

    public int getChildrenCount(int groupPosition) {
        int size = ((Category) this.catList.get(groupPosition)).getItemList().size();
        System.out.println("Child for group [" + groupPosition + "] is [" + size + "]");
        return size;
    }

    public Object getGroup(int groupPosition) {
        return this.catList.get(groupPosition);
    }

    public int getGroupCount() {
        return this.catList.size();
    }

    public long getGroupId(int groupPosition) {
        return (long) ((Category) this.catList.get(groupPosition)).hashCode();
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = ((LayoutInflater) this.ctx.getSystemService("layout_inflater")).inflate(C0539R.layout.group_layout, parent, false);
        }
        ((TextView) v.findViewById(C0539R.id.groupName)).setText("   " + ((Category) this.catList.get(groupPosition)).getName());
        return v;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

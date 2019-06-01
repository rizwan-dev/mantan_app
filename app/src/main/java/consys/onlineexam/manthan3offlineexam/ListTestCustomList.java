package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListTestCustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] web;

    public ListTestCustomList(Activity context, String[] web) {
        super(context, C0539R.layout.list_single, web);
        this.context = context;
        this.web = web;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = this.context.getLayoutInflater().inflate(C0539R.layout.listtestcustom, null, true);
        ((TextView) rowView.findViewById(C0539R.id.txttestlist1)).setText(this.web[position]);
        return rowView;
    }
}

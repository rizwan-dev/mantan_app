package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SelectSubCustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final Integer[] imageId;
    private final String[] web;

    public SelectSubCustomList(Activity context, String[] web, Integer[] imageId) {
        super(context, C0539R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = this.context.getLayoutInflater().inflate(C0539R.layout.list_single, null, true);
        ((TextView) rowView.findViewById(C0539R.id.txt)).setText(this.web[position]);
        return rowView;
    }
}

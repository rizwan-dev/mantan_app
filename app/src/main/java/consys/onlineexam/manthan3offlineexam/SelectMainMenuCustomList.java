package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectMainMenuCustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final int height;
    private final Integer[] imageId;
    private final String[] web;
    private final int width;

    public SelectMainMenuCustomList(Activity context, String[] web, Integer[] imageId, int w, int h) {
        super(context, C0539R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.width = w;
        this.height = h;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = this.context.getLayoutInflater().inflate(C0539R.layout.list_single_mainmenu, null, true);
        int rowheigh = this.height / 6;
        LayoutParams par = new LayoutParams(this.width, rowheigh);
        int imgh = (rowheigh / 10) * 60;
        RelativeLayout.LayoutParams par1 = new RelativeLayout.LayoutParams(imgh, imgh);
        RelativeLayout.LayoutParams par2 = new RelativeLayout.LayoutParams(imgh, rowheigh / 10);
        ImageView imageView = (ImageView) rowView.findViewById(C0539R.id.img_main_menu);
        ((TextView) rowView.findViewById(C0539R.id.txt_main_menu)).setText(this.web[position]);
        imageView.setImageResource(this.imageId[position].intValue());
        return rowView;
    }
}

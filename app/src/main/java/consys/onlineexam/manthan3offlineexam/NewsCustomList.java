package consys.onlineexam.manthan3offlineexam;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsCustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] header;
    private final Bitmap[] image;
    private final String[] news;

    public NewsCustomList(Activity context, String[] h, String[] news, Bitmap[] arr) {
        super(context, C0539R.layout.list_single, news);
        this.context = context;
        this.header = h;
        this.news = news;
        this.image = arr;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = this.context.getLayoutInflater().inflate(C0539R.layout.custom_news, null, true);
        TextView txtn = (TextView) rowView.findViewById(C0539R.id.txtnewdetail);
        ImageView imageView = (ImageView) rowView.findViewById(C0539R.id.imgnews);
        ((TextView) rowView.findViewById(C0539R.id.txtnewheader)).setText(this.header[position]);
        if (this.image[position] != null) {
            imageView.setImageBitmap(this.image[position]);
        }
        txtn.setText(this.news[position]);
        return rowView;
    }
}

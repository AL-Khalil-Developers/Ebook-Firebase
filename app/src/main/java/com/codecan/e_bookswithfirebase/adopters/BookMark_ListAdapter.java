package com.codecan.e_bookswithfirebase.adopters;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.codecan.e_bookswithfirebase.R;
import com.codecan.e_bookswithfirebase.activites.BookMarkActivity;
import com.codecan.e_bookswithfirebase.activites.BookmarkReadPageActivity;

public class BookMark_ListAdapter extends ArrayAdapter<String> {
    // Declaring Variables
    customButtonListener customListner;
    public static String[] bimg;
    public static int psne;
    public static int urlpsne;
    public static String dbrowname;

    // Declaring CustomButton Listener for delete button in bookmark listview
    public interface customButtonListener {
        void onButtonClickListner(int position, int value, String rowtext);
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }
    private Context context;
    ArrayList<String> data = new ArrayList<String>();

    // Defining adaper properties
    public BookMark_ListAdapter(Context context, ArrayList<String> players) {
        super(context, R.layout.bookmarkitem, players);
        this.data = players;
        this.context = context;
    }

    // Declaring adapter getview method
    @Override
    public View getView(final int bposition /*Item Position*/,
                        View convertView /*Item View on UI */, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);

            //Inflating bookmarkitem layout (Created for Listview)
            convertView = inflater.inflate(R.layout.bookmarkitem, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView
                    .findViewById(R.id.bookmarkitempage);
            viewHolder.button = (Button) convertView
                    .findViewById(R.id.bdelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // getting pagenumber and bookname
        final String bookname = getItem(bposition);
        final int pagenumber = BookMarkActivity.rowg[bposition];
        viewHolder.text.setText(bookname);

        // For Deletion bookmark item from database
        viewHolder.button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(bposition,pagenumber,bookname);
                    notifyDataSetChanged();
                }
            }
        });

        // Click on listview Item for opening book and tranfering variables to next activity
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                bimg = BookMarkActivity.imageburl[bposition].split(",");
                int bb = Integer.parseInt(BookMarkActivity.posision[bposition]);
                psne = bb;
                urlpsne = bposition;
                dbrowname = getItem(bposition).replaceAll("Page.*", "");
                Intent it = new Intent(context,
                        BookmarkReadPageActivity.class);
                context.startActivity(it);
            }
        });
        return convertView;
    }
    public class ViewHolder {
        TextView text;
        Button button;
    }
}
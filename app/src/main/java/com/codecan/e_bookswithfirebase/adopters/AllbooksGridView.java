package com.codecan.e_bookswithfirebase.adopters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codecan.e_bookswithfirebase.R;
import com.codecan.e_bookswithfirebase.activites.AllBooksPagesActivity;
import com.codecan.e_bookswithfirebase.activites.MainActivity;
import com.codecan.e_bookswithfirebase.utilties.BookList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class AllbooksGridView extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ImageLoader imgloader;
    private List<BookList> bookarraylist = null;
    private ArrayList<BookList> arraylist;
    public static String[] name;
    public AllbooksGridView(Context context, List<BookList>bookarraylist){
        this.context=context;
        this.bookarraylist=bookarraylist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<BookList>();
        this.arraylist.addAll(bookarraylist);
        imgloader = ImageLoader.getInstance();
    }
    public class ViewHolder {
        ImageView bookpageview;
        TextView bookname;
        ProgressBar progressBar;
    }
    @Override
    public int getCount() {
        return bookarraylist.size();
    }
    @Override
    public Object getItem(int position) {
        return bookarraylist.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int pstn, View view, ViewGroup parent) {
        final ViewHolder holder;
        name = MainActivity.bookmainpage;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.gridview_item, null);
            holder.bookpageview = (ImageView) view.findViewById(R.id.image);
            holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
            holder.bookname= (TextView) view.findViewById(R.id.text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // L img in GV
        //imageLoader.DisplayImage(phonearraylist.get(pstn).getPhone(),
        //		holder.phone);
        //-------------------------
        imgloader.displayImage(bookarraylist.get(pstn).getImgurl(), holder.bookpageview,null, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.progressBar.setProgress(0);
                holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.progressBar.setVisibility(View.GONE);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                holder.progressBar.setProgress(Math.round(100.0f * current / total));
            }
        });
        //-------------------------
//		holder.textView.setText("Page "+Integer.toString(name[pstn]));
        holder.bookname.setText(name[pstn]);

        // Capture GV itm clk
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, AllBooksPagesActivity.class);
                intent.putExtra("iburl", bookarraylist.get(pstn)
                        .getImgurl());
           //     intent.putExtra("ids", bookarraylist.get(pstn).getImgurl());
                intent.putExtra("id", pstn);
                context.startActivity(intent);
            }
        });
        return view;
    }
}

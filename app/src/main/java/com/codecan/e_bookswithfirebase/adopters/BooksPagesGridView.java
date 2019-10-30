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
import com.codecan.e_bookswithfirebase.activites.ReadPageActivity;
import com.codecan.e_bookswithfirebase.utilties.PagesList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class BooksPagesGridView extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<PagesList> pagearraylist = null;
    private ArrayList<PagesList> arraylist;
    public static int[] pageNos;
    public BooksPagesGridView(Context context, List<PagesList> pagearraylist) {
        this.context = context;
        this.pagearraylist = pagearraylist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<PagesList>();
        this.arraylist.addAll(pagearraylist);
        imageLoader = ImageLoader.getInstance();
    }
    public class ViewHolder {
        ImageView pageimage;
        TextView pagesNos;
        ProgressBar progressBar;
    }
    @Override
    public int getCount() {
        return pagearraylist.size();
    }
    @Override
    public Object getItem(int position) {
        return pagearraylist.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int pstn, View view, ViewGroup parent) {
        final ViewHolder holder;
        pageNos = AllBooksPagesActivity.pagenumbers;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.gridview_item, null);
            holder.pageimage = (ImageView) view.findViewById(R.id.image);
            holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
            holder.pagesNos= (TextView) view.findViewById(R.id.text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // L img in GV
        //-------------------------
        imageLoader.displayImage(pagearraylist.get(pstn).getPagesurl(), holder.pageimage,null, new SimpleImageLoadingListener() {
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
        holder.pagesNos.setText("Page "+Integer.toString(pageNos[pstn]));
//		holder.textView.setText(name[pstn]);

        // Capture GV itm clk
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ReadPageActivity.class);
                intent.putExtra("Nurl", pagearraylist.get(pstn)
                        .getPagesurl());
                intent.putExtra("ids", pagearraylist.get(pstn).getPagesurl());
                intent.putExtra("id", pstn);
                context.startActivity(intent);
            }
        });
        return view;
    }

}

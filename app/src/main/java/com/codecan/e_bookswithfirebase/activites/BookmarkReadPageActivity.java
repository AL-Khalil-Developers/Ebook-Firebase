package com.codecan.e_bookswithfirebase.activites;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.codecan.e_bookswithfirebase.R;
import com.codecan.e_bookswithfirebase.adopters.BookMark_ListAdapter;
import com.codecan.e_bookswithfirebase.adopters.DBAdapter;
import com.codecan.e_bookswithfirebase.utilties.TouchImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import java.util.Arrays;
import java.util.Random;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BookmarkReadPageActivity extends AppCompatActivity {

    // Declaring Variables
    InterstitialAd interstitial;
    int position;
    TouchImageView image;
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    ProgressBar progressBar;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.bookmarkimagelayout);

        // Hiding Action Baar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Getting Page Number from Previous adaptor
        position = BookMark_ListAdapter.psne;
        image = (TouchImageView) findViewById(R.id.image);
        progressBar = (ProgressBar) findViewById(R.id.bimgprogress);

        // Getting Instance / Initializing Imageloader Library
        imageLoader = ImageLoader.getInstance();

        // Initializing Database Adopter
        final DBAdapter db=new DBAdapter(this);
        TextView textView = (TextView) findViewById(R.id.positions);

        // Setting Page Number and Total Numbers of pages in Textview
        textView.setText("Page: "+Integer.toString(position)+"/"+Integer.toString(BookMark_ListAdapter.bimg.length-1));

        // Getting First image URL / Path from database
        String image_url = BookMark_ListAdapter.bimg[position];

        // Removing Coma ( , ) from the Image URL String
        image_url = image_url.substring(1, image_url.length()-1);

        // Loading Image into imageview Using Universal Image View Liabrary
        imageLoader.displayImage(image_url, image,null, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                progressBar.setProgress(Math.round(100.0f * current / total));
            }
        });
        //-------------------

        // Declaring Buttons
        Button btnext = (Button) findViewById(R.id.Next);
        Button btprvs = (Button) findViewById(R.id.Previous);
        Button bookmark=(Button) findViewById(R.id.bookmark);

        // Next Page View Button Code Starts
        btnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final int imgss =BookMark_ListAdapter.bimg.length;
                int fnl = imgss-2;
                if (fnl >= position) {
                    String btnpstion = BookMark_ListAdapter.bimg[++position];
                    btnpstion = btnpstion.substring(1, btnpstion.length()-1);
                    // imgLoader.DisplayImage(btnpstion, loader, image);
                    //-------------------------
                    imageLoader.displayImage(btnpstion, image,null, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            progressBar.setProgress(0);
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            progressBar.setProgress(Math.round(100.0f * current / total));
                        }
                    });
                    // Displaying Page Number
                    TextView textView = (TextView) findViewById(R.id.positions);
                    textView.setText("Page: "+Integer.toString(position)+"/"+Integer.toString(BookMark_ListAdapter.bimg.length-1));

                    // Displaying Admob Add
                    intitiadd();
                } else {
                    Toast.makeText(BookmarkReadPageActivity.this, "End Of Book", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Next Page View Button Code Ends

        // Previous Page View Button Code Starts
        btprvs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int prvss = 1;
                if (prvss <= position) {
                    String btnpstion = BookMark_ListAdapter.bimg[--position];
                    btnpstion = btnpstion.substring(1, btnpstion.length()-1);
                    //       imgLoader.DisplayImage(btnpstion, loader, image);
                    //-------------------------
                    imageLoader.displayImage(btnpstion, image,null, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            progressBar.setProgress(0);
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            progressBar.setProgress(Math.round(100.0f * current / total));
                        }
                    });
                    //-------------------------
                    TextView textView = (TextView) findViewById(R.id.positions);
                    textView.setText("Page: " + Integer.toString(position) + "/" + Integer.toString(BookMark_ListAdapter.bimg.length - 1));
                } else {
                    Toast.makeText(BookmarkReadPageActivity.this, "Please Click Next", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Previous Page View Button Code Ends

        // Bookmark Button Code Starts
        bookmark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Convderting it into a single string

                String result_ScoreP1 = ("" + Arrays.asList(BookMarkActivity.imageburl[BookMark_ListAdapter.urlpsne])).
                        replaceAll("(^.|.$)", "  ").replace(", ", "  , ");
                db.openDB();
                long result=db.add(BookMark_ListAdapter.dbrowname, Integer.toString(position),result_ScoreP1);
                db.close();
                Toast.makeText(BookmarkReadPageActivity.this, "Bookmark Done Page: "+position, Toast.LENGTH_LONG).show();
            }
        });
        // Bookmark Button Code Ends
    }
    //----------Intestinal Google Add Code Method------------
    public void intitiadd(){

        interstitial = new InterstitialAd(BookmarkReadPageActivity.this);

        // Calling Adunit ID from string Resource
        interstitial.setAdUnitId(getString(R.string.intstial));
        AdRequest adRequest = new AdRequest.Builder()
                .tagForChildDirectedTreatment(true).build();
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading (Displaying Loaded Add)
                displayInterstitial();
            }
            @Override
            public void onAdClosed() {
                // Load the next interstitial When the add closed
                interstitial.loadAd(new AdRequest.Builder().build());
            }

        });
    }

    //Displaying Intestinal Ad Code Method
    public void displayInterstitial () {
        if (new Random().nextInt(3) == 1)
        {
            if (interstitial.isLoaded()) {
                interstitial.show();
            }
        }
    }
}

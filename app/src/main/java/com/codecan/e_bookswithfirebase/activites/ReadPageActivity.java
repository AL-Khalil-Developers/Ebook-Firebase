    package com.codecan.e_bookswithfirebase.activites;

    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.drawable.BitmapDrawable;
    import android.graphics.drawable.Drawable;
    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Environment;
    import android.text.Html;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.codecan.e_bookswithfirebase.R;
    import com.codecan.e_bookswithfirebase.adopters.DBAdapter;
    import com.codecan.e_bookswithfirebase.utilties.TouchImageView;
    import com.google.android.gms.ads.AdListener;
    import com.google.android.gms.ads.AdRequest;
    import com.google.android.gms.ads.InterstitialAd;
    import com.nostra13.universalimageloader.core.ImageLoader;
    import com.nostra13.universalimageloader.core.assist.FailReason;
    import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
    import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Random;

    import androidx.appcompat.app.ActionBar;
    import androidx.appcompat.app.AppCompatActivity;

    public class ReadPageActivity  extends AppCompatActivity {
        InterstitialAd interstitial;
        int position;
        TouchImageView image;
        ProgressBar progressBar;
        ActionBar mActionBar;
        com.nostra13.universalimageloader.core.ImageLoader imageLoader;
        @Override
        public void onCreate(Bundle icicle) {
            super.onCreate(icicle);
            setContentView(R.layout.readpageactivity);
            mActionBar = getSupportActionBar();
            Intent i = getIntent();
            position=i.getIntExtra("id",0);
            final int loader = R.mipmap.ic_launcher;
            image = (TouchImageView) findViewById(R.id.image);
            progressBar = (ProgressBar) findViewById(R.id.kanchaprogress);
            imageLoader = ImageLoader.getInstance();
            final DBAdapter db=new DBAdapter(this);
            TextView pagenumber = (TextView) findViewById(R.id.positions);
         //   pagenumber.setText("Page: "+Integer.toString(position)+"/"+Integer.toString(AllBooksPagesActivity.urllist.length-1));
            showactionbar("Page: "+Integer.toString(position)+"/"+Integer.toString(AllBooksPagesActivity.urllist.length-1));
            String image_url = AllBooksPagesActivity.urllist[position];

            // First Loading page from url to imageview
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

            // Declairing Buttons for pages movement and book mark

            Button btnext = (Button) findViewById(R.id.Next);
            Button btprvs = (Button) findViewById(R.id.Previous);
            Button bookmark=(Button) findViewById(R.id.bookmark);

            // Next Button for loading next page from url

            btnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int imgss =AllBooksPagesActivity.urllist.length;
                    int fnl = imgss-2;
                    if (fnl >= position) {
                        String btnpstion = AllBooksPagesActivity.urllist[++position];
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
          //  textView.setText("Page: "+Integer.toString(position)+"/"+Integer.toString(AllBooksPagesActivity.urllist.length-1));
         showactionbar("Page: "+Integer.toString(position)+"/"+Integer.toString(AllBooksPagesActivity.urllist.length-1));
            //--------------Google Add-1-----------
            displayinginterstialadd();
            //****************Google Ad 1 Ends ----
        } else {
            Toast.makeText(ReadPageActivity.this, "End Of Book", Toast.LENGTH_SHORT).show();
        }
    }
            });


            // Previous Button for loading next page from url

            btprvs.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int prvss = 1;
                    if (prvss <= position) {
                        String btnpstion = AllBooksPagesActivity.urllist[--position];
                        //     imgLoader.DisplayImage(btnpstion, loader, image);
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
                      //  textView.setText("Page: " + Integer.toString(position) + "/" + Integer.toString(AllBooksPagesActivity.urllist.length - 1));
                  showactionbar("Page: " + Integer.toString(position) + "/" + Integer.toString(AllBooksPagesActivity.urllist.length - 1));
                    } else {
                        Toast.makeText(ReadPageActivity.this, "Please Click Next", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            //--------------Bookmark Button Code-----------------------------
            bookmark.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Convderting it into a single string
                    String result_ScoreP1 = ("" + Arrays.asList(AllBooksPagesActivity.urllist)).
                            replaceAll("(^.|.$)", "  ").replace(", ", "  , ");
                    db.openDB();
                    List<String> list = new ArrayList<String>(Arrays.asList(AllBooksPagesActivity.urllist));
                    long result=db.add(MainActivity.bookmainpage[position], Integer.toString(position),result_ScoreP1);
                    Toast.makeText(ReadPageActivity.this, "Bookmark Done Page: "+position, Toast.LENGTH_LONG).show();
                    db.close();
                }
            });
            //--------------Bookmark Code ends------------------------
        }
        // Method for Displaying Custom Title in Actionbar Code Starts
        public void showactionbar(String actionbartitle){
            if (mActionBar != null) {
                //	mActionBar.hide();
                mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
                //  mActionBar.setTitle(AllBooksPagesActivity.bookname);
                getSupportActionBar().setCustomView(R.layout.actionbar_title);
                TextView title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
                title.setText(actionbartitle);
            }
        }
        // Method for Displaying Custom Title in Actionbar Code Ends

        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.share_menu,menu);
            menu.findItem(R.id.shareit).setVisible(true);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem menuItem){
            switch(menuItem.getItemId()){
                case R.id.shareit:
                    shareingimage(); //----Calling Share image Method
                    return true;
                default:
                    return super.onOptionsItemSelected(menuItem);
            }
        }

        //---- Share image Method
        public void shareingimage(){
            Uri bmpUri = getLocalBitmapUri(image);
            if (bmpUri != null) {
                // Construct a ShareIntent with link to image
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.setType("image/*");
                // Launch sharing dialog for image
                shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<b>Islamic Book Collection :</b>") + " https://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            } else {
                Toast.makeText(ReadPageActivity.this,"Can Not Share Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }

        //---------------------Extracting to Bitmap from loaded imgview
        public Uri getLocalBitmapUri(ImageView imageView) {
            // Extract Bitmap from ImageView
            Drawable drawable = imageView.getDrawable();
            Bitmap bmp = null;
            if (drawable instanceof BitmapDrawable){
                bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            } else {
                return null;
            }
            // Store image to default external storage directory
            Uri bmpUri = null;
            try {
                File file =  new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".jpeg");
                file.getParentFile().mkdirs();
                FileOutputStream out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
                bmpUri = Uri.fromFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmpUri;
        }
        //--------------------------
        //--------------Intristial add loading method -----------
        public void displayinginterstialadd(){
            interstitial = new InterstitialAd(ReadPageActivity.this);
            interstitial.setAdUnitId(getString(R.string.intstial));
            AdRequest adRequest = new AdRequest.Builder()
                    .tagForChildDirectedTreatment(true).build();
            interstitial.loadAd(adRequest);
            interstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    displayInterstitial();
                }
                @Override
                public void onAdClosed() {
                    // Load the next interstitial.
                    interstitial.loadAd(new AdRequest.Builder().build());
                }

            });
        }
        //----------Intristial add displaying method------------
        public void displayInterstitial () {
            if (new Random().nextInt(3) == 1)
            {
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        }
    }

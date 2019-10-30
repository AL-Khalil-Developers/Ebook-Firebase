package com.codecan.e_bookswithfirebase.utilties;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import com.codecan.e_bookswithfirebase.R;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;

// It is an application class for books app
public class App extends Application {
    public static File cacheDir; // Directory for image caching
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // Firebase instance on app startup
        MobileAds.initialize(this, getString(R.string.admbintialize)); // Initializing admob
        cacheDir = App.this.getDir("Books Images", Context.MODE_PRIVATE); // defining directory for image caching

        // Display options for UIL (Universal Image Loader) library
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.drawable.error)
                .showImageOnFail(R.drawable.tryagain)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .delayBeforeLoading(100)
                .cacheInMemory(true)

                .cacheOnDisk(true)
                .displayer(new SimpleBitmapDisplayer()) // default
                .build();

        // Configuration for UIL (Universal Image Loader) library
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPriority(Thread.MAX_PRIORITY)
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(3 * 1024 * 1024)
                .memoryCacheSizePercentage(18)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(1250)

                .threadPoolSize(4)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(options)
                .writeDebugLogs()

                .build();

        // Initialing UIL (Universal Image Loader) library with its configuration
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
    }
}

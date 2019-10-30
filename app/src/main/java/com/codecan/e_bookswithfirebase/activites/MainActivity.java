package com.codecan.e_bookswithfirebase.activites;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.codecan.e_bookswithfirebase.adopters.AllbooksGridView;
import com.codecan.e_bookswithfirebase.utilties.App;
import com.codecan.e_bookswithfirebase.utilties.BookList;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.codecan.e_bookswithfirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GridView gridview;
    ProgressDialog mProgressDialog;
    AllbooksGridView allbooksGridView;
    public static String[] bookmainpage;
    private List<BookList> bookarraylist = null;
    private DatabaseReference ref;
    Query queryRef;
    private int STORAGE_WRITEPERMISSION_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //-------------------Google Banner Add Code Starts
        AdView mAdView = (AdView) findViewById(R.id.bnradd1);
        AdRequest adRequest = new AdRequest.Builder()
                .tagForChildDirectedTreatment(true).build();
        mAdView.loadAd(adRequest);
        //-------------------Google Banner Add Code Ends

        bookarraylist=new ArrayList<BookList>();    // Initialise arraylist

        //-- Runtime App Storage Permission Code Starts--
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
        }else {
            requeststoragepermission(); // Runtime App Storage Permission Method called
        }

        // Progressdialog starts during data Fetching from server
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setTitle("Loading Books");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance(); // Firebase Instance Called
        ref= database.getReference("All_Books");    // All Books Collection Called from firebase server
        queryRef = ref.orderByChild("ibname");            // Sort By ibname document from firebase database

        // Starts Fechting query from firebase server
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long b = dataSnapshot.getChildrenCount();
                bookmainpage = new String[(int)b];
                int ii=0;
                for (DataSnapshot  ddataSnapshot : dataSnapshot.getChildren()) {
                    Object value = ddataSnapshot.getValue();
                    bookmainpage[ii]=((Map) value).get("ibname").toString();
                    BookList map = new BookList();
                    map.setImgurl((String) ((Map) value).get("iburl").toString());
                    bookarraylist.add(map);
                    ii++;
                }

                gridview = (GridView) findViewById(R.id.gridview);
                allbooksGridView = new AllbooksGridView(MainActivity.this,
                        bookarraylist);
                gridview.setAdapter(allbooksGridView);
                mProgressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("There is db error");
            }
        });
    }
        // Requesting Runtime storage permission method declaration Starts
        public void requeststoragepermission(){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                new AlertDialog.Builder(this)
                        .setTitle("BookRead Permission")
                        .setMessage("Storage Permission Required for saving of Islamic Book Images in your mobile ")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_WRITEPERMISSION_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Toast.makeText(MainActivity.this, "You Can not read Islamic Books", Toast.LENGTH_LONG).show();
                            }
                        })
                        .create().show();
            }else {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_WRITEPERMISSION_CODE);
            }
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==STORAGE_WRITEPERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
// Requesting Runtime storage permission method declaration Ends

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finishAffinity();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
     //       return true;
        }else if (id == R.id.nav_cache) {

            if( App.cacheDir.exists() ) {
                File[] files = App.cacheDir.listFiles();
                if(files.length >0)
                {
                    for(int i=0; i<files.length; i++) {
                        if(files[i].isFile()) {
                            files[i].delete();
                        }
                    }
                    Toast.makeText(MainActivity.this,"Cache Cleared",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Already Cleared",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bookmark) {
            // Handle the camera action
            Intent v = new Intent(MainActivity.this,BookMarkActivity.class);
            startActivity(v);

        } else if (id == R.id.nav_rate) {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));

            }
        } else if (id == R.id.nav_share) {
            Intent view = new Intent(Intent.ACTION_SEND);
            view.setType("text/plain");
            view.putExtra(Intent.EXTRA_SUBJECT, "Books With Firebase");
            view.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<b>Book Collection App :</b>") + " https://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(view, "Share App!"));

        } else if (id == R.id.nav_moreapps) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://search?q=pub:"+"broad+vision"));
            try{
                startActivity(intent);
            }
            catch(Exception e){                 intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=broad+vision"));
            }

        } else if (id == R.id.nav_email) {

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "android.studio@android.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Books Collection");
            email.putExtra(Intent.EXTRA_TEXT, "Your Massage");

            //need this to prompts email client only
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        } else if (id == R.id.nav_cache) {

            if( App.cacheDir.exists() ) {
                File[] files = App.cacheDir.listFiles();
                if(files.length >0)
                {
                    for(int i=0; i<files.length; i++) {
                        if(files[i].isFile()) {
                            files[i].delete();
                        }
                    }
                    Toast.makeText(MainActivity.this,"Cache Cleared",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Already Cleared",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

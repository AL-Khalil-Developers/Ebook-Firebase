package com.codecan.e_bookswithfirebase.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.codecan.e_bookswithfirebase.R;
import com.codecan.e_bookswithfirebase.adopters.BooksPagesGridView;
import com.codecan.e_bookswithfirebase.utilties.PagesList;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class AllBooksPagesActivity extends AppCompatActivity{
       // implements NavigationView.OnNavigationItemSelectedListener {
    // Declare Variables
    GridView gridview;
    ProgressDialog mProgressDialog;
    BooksPagesGridView adapter;
    public static String bookname;
    String booknamereplace;
    public static int[] pagenumbers;
    long[] convertedpagenumber;
    int position;
    public static String urllist[];     // This variable is used in ReadPageActivity
    private List<PagesList> pagesarraylist = null;
    private DatabaseReference ref;
    Query queryRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Admob Banner Code Starts
        AdView mAdView = (AdView) findViewById(R.id.bnradd1);
        AdRequest adRequest = new AdRequest.Builder()
                .tagForChildDirectedTreatment(true).build();
        mAdView.loadAd(adRequest);
        //Admob Banner Add Code Ends

        // Retrieving Variables from previous Activity
        Intent i = getIntent();
        position=i.getIntExtra("id", 0);
        bookname = MainActivity.bookmainpage[position];
        booknamereplace=bookname.replace(" ","_");

        // Setting Selected custom book name in Toolbar as Title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar_title);
            TextView title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
            title.setText(bookname);
        }
        // Toolbar Title Code Ends

        pagesarraylist = new ArrayList<PagesList>();

        mProgressDialog = new ProgressDialog(AllBooksPagesActivity.this);
        mProgressDialog.setTitle("Loading Pages");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref= database.getReference("Books_Pages/"+booknamereplace);
        queryRef = ref.orderByChild("bisort");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //--------------------
                long b = dataSnapshot.getChildrenCount();
                urllist = new String[(int)b];
            //    namesort = new int[(int)b];
                convertedpagenumber = new long[(int)b];
                pagenumbers=new int[(int)b];
                int i= 0;
                int k =0;
                for (DataSnapshot  ddataSnapshot : dataSnapshot.getChildren()) {
                    Object value = ddataSnapshot.getValue();

                    urllist[i] = ((Map) value).get("iburl").toString();
                    convertedpagenumber[i] = (long)((Map) value).get("ibsort");
                    pagenumbers[k] = (int)convertedpagenumber[i];
               //     pagenumbers[k]=(int)((Map) value).get("ibsort");
                    PagesList map = new PagesList();
                    map.setPagesurl((String) ((Map) value).get("iburl").toString());
                    pagesarraylist.add(map);
                    i++;
                    k++;
                }

                // Removing blankfields and comas from url list
                List<String> list = new ArrayList<String>();
                for(String s : urllist) {
                    if(s != null && s.length() > 0) {
                        list.add(s);
                    }
                }
                urllist = list.toArray(new String[list.size()]);


                gridview = (GridView) findViewById(R.id.gridview); // Initializing gricview
                adapter = new BooksPagesGridView(AllBooksPagesActivity.this,
                        pagesarraylist); // Defining adapter
                gridview.setAdapter(adapter); // Setting Adapter
                mProgressDialog.dismiss();
                // Firebase.goOffline();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

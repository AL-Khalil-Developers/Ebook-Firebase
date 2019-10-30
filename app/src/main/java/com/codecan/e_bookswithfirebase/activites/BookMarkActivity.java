package com.codecan.e_bookswithfirebase.activites;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.codecan.e_bookswithfirebase.R;
import com.codecan.e_bookswithfirebase.adopters.BookMark_ListAdapter;
import com.codecan.e_bookswithfirebase.adopters.DBAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;

public class BookMarkActivity extends AppCompatActivity implements
        BookMark_ListAdapter.customButtonListener {
    // Declaring Variables
    ListView lv;
    ArrayList<String> players=new ArrayList<String>(); // Initializing Array List
    BookMark_ListAdapter adapter;       // Listview Adopter
    public static String[] posision;    // Getting Name of the book From saved database
    public static String[] imageburl;   // Image URLs for Specific Book Images
    public static int[] rowg;           // Getting Page Position (Page Number) From saved database
    ActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark); // Layout for bookmarks List

        //-------------------Google Banner Add
        AdView mAdView = (AdView) findViewById(R.id.bukmarkadd);
        AdRequest adRequest = new AdRequest.Builder()
                .tagForChildDirectedTreatment(true).build();
        mAdView.loadAd(adRequest);
//-------------------Google Banner Add Ends

        lv=(ListView) findViewById(R.id.bookmarklistview);

        // Initializing Action Bar
        mActionBar = getSupportActionBar();

        // Calling Method and giving string for Actionbar Title
        // You can replace with your own title String
        showactionbar("Your Bookmarks");

        // Initializing Custom listview Adapter
        adapter = new BookMark_ListAdapter(BookMarkActivity.this, players);

        // Initializing Database Connection
        final DBAdapter db=new DBAdapter(this);

        // Clearing Arraylist for data insertion
        players.clear();

        /*
        Calling Sqlite Database For Fetching Only Bookmarked Book name
        and Page Number for displaying in listview (Not the whole Book Data)
        */

        db.openDB();
        Cursor c=db.getAllNames();
        while(c.moveToNext())    // Getting values from Database
        {
            String name=c.getString(1);     // Fetching Book Name from database
            String posins=c.getString(2);   // Fetching Book Page No from database
            players.add(name + " Page: " + posins);     // Adding Results into the ArrayList
        }

        // Setting Clicklistner on populated List
        adapter.setCustomButtonListner(BookMarkActivity.this);

        // Implementing adapter to listview
        lv.setAdapter(adapter);

        db.close();

                int siz=players.size(); // Getting the Arraylist size (Total Number of Bookmarked Books)
             /*
        Calling Sqlite Database For Fetching Clicked Bookmarked Book's
        Pages URLs and Page Numbers and saving results in String arrays
        */

                db.openDB();
                Cursor cha=db.getAllNames();
                imageburl = new String[siz];
                posision = new String[siz];
                rowg = new int[siz];
                int acha=0;
                int i=0;
                int g=0;
                while(cha.moveToNext())
                {
                    imageburl[i] =cha.getString(3);
                    posision[g] = cha.getString(2);
                    rowg[acha] = cha.getInt(0);
                    acha++;
                    i++;
                    g++;
                }
                db.close();
    }
    // Method for Displaying Title in Actionbar Code Starts
    public void showactionbar(String actionbartitle){
        if (mActionBar != null) {
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
            getSupportActionBar().setCustomView(R.layout.actionbar_title);
            TextView title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
            title.setText(actionbartitle);
        }
    }
    // Method for Displaying Title in Actionbar Code Ends

    // Click Method for deleting selected BookMark book's data from saved database
    @Override
    public void onButtonClickListner(int position, int value,String rowtext) {
        Toast.makeText(BookMarkActivity.this, rowtext+" Deleted",
                Toast.LENGTH_LONG).show();
        String pesi = Integer.toString(position);
        final DBAdapter db1=new DBAdapter(this);
        db1.openDB();
        db1.deleteTitle(value);
        db1.close();
        Intent refresh = new Intent(this, BookMarkActivity.class);
        startActivity(refresh);
        finish();
    }
}

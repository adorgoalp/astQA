package com.adorgolap.as_sunnahtrustqa;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.adorgolap.as_sunnahtrustqa.adpater.MainListViewAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;
    Toolbar toolbar;
    DatabaseHelper dbHelper = null;
    ArrayList<String> categories;
    ArrayList<QA> listData;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        lv = (ListView) findViewById(R.id.lvMain);
        handleDatabase();
        handleToolbar();
//        handleFab();

    }

    private void handleOnScreenListViewData(String category) {
        listData = dbHelper.getAllQA(category);
        MainListViewAdapter adapter = new MainListViewAdapter(context,0,listData);
        lv.setAdapter(adapter);
    }

    private void handleDatabase() {
        DataHndlerTask task = new DataHndlerTask();
        task.execute();
    }

    class DataHndlerTask extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog progress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(context);
            progress.setTitle("Please wait");
            progress.setMessage("Preparing database...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dbHelper = new DatabaseHelper(context);
            try {
                dbHelper.createDataBase();
                dbHelper.openDataBase();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
            handleNavigationDrawer();
            handleOnScreenListViewData("All");
        }
    }

    private void handleNavigationDrawer() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ListView lv = (ListView) navigationView.findViewById(R.id.lvNavBar);
        while (!dbHelper.isReady())
        {
            try {
                Thread.sleep(250);
            }catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
        if(dbHelper.isReady())
        {
            categories= dbHelper.getAllCategory();
            ArrayAdapter<String> adpter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,categories);
            lv.setAdapter(adpter);
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positon, long id) {
                Toast.makeText(context,categories.get(positon),Toast.LENGTH_SHORT).show();
                handleOnScreenListViewData(categories.get(positon));
                drawer.closeDrawer(Gravity.LEFT);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void handleFab() {
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//
//    }

    private void handleToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}

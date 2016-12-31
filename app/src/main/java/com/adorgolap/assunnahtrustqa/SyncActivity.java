package com.adorgolap.assunnahtrustqa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adorgolap.as_sunnahtrustqa.R;
import com.adorgolap.assunnahtrustqa.helper.DatabaseHelper;
import com.adorgolap.assunnahtrustqa.helper.Utils;
import com.adorgolap.assunnahtrustqa.model.QA;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifta on 12/17/16.
 */

public class SyncActivity extends AppCompatActivity {
    Button bSyncStart;
    TextView tvSyncLog;
    ScrollView scrollViewLog ;
    boolean hasMoreData = true;
    boolean hasAnyData = false;
    ArrayList<QA> newQAdata = new ArrayList<QA>();
    DatabaseHelper dbHelper;
    boolean isFinished = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_layout);
        bSyncStart = (Button) findViewById(R.id.bSyncStart);
        tvSyncLog = (TextView) findViewById(R.id.tvSyncLog);
        scrollViewLog = (ScrollView) findViewById(R.id.scrollViewLog);
        dbHelper = new DatabaseHelper(this);
        dbHelper.openDataBase();
        bSyncStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFinished)
                {
                    finish();
                }
                if (Utils.isNetworkConnected(getApplicationContext())) {
                    bSyncStart.setClickable(false);
                    maxQAid = dbHelper.getMaxQAid();
                    getData();
                } else {
                    updateLog("Internet is not available. Please check your connection.");

                }
            }
        });
    }

    private void updateLog(String s) {
        final String log = "\n"+ s;
        tvSyncLog.post(new Runnable() {
            @Override
            public void run() {
                tvSyncLog.append(log);
                scrollViewLog.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollViewLog.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    private void saveData() {
        if (hasAnyData) {
            Log.i("iftaLog", "Saving Data");
            updateLog("Saving data....");
            dbHelper.insertAll(newQAdata);
            Log.i("iftaLog", "Data saved");
            updateLog("Data saved.");
            Intent returnIntent = new Intent();
            setResult(RESULT_OK,returnIntent);

        } else {
            updateLog("Database is already synchronized with global database.");
        }
        bSyncStart.setClickable(true);
        bSyncStart.setText("Finish");
        isFinished = true;

    }

    int maxQAid = 0;

    private void getData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("QA_TABLE");
        query.setSkip(maxQAid);
        Log.i("iftaLog", "getting data");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) {
                    Log.d("iftaLog", e.getLocalizedMessage());
                    final String message = e.getMessage();
                    updateLog("Error: " + message);
                } else {
                    if (objects.size() == 0) {
                        if (hasAnyData) {
                            Log.i("iftaLog", "No data");
                            updateLog("All data fetched.");
                        }
                        hasMoreData = false;
                        saveData();
                    } else {
                        hasAnyData = true;
                        for (ParseObject o : objects) {
                            final QA qa = new QA(o);
                            Log.i("iftaLog", qa.toString());
                            if (qa.getId() > maxQAid) {
                                maxQAid = qa.getId();
                            }
                            updateLog("Processed QA #  " + qa.getId());
                            newQAdata.add(qa);
                        }
                        if (hasMoreData) {
                            Log.i("iftaLog", "Getting more data with mazQAId = " + maxQAid);
                            updateLog("Trying to get more data after QA # " + maxQAid);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }finally {
                                getData();
                            }
                        } else {
                            saveData();
                        }
                    }
                }
            }
        });
    }

}

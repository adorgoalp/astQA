package com.adorgolap.assunnahtrustqa.helper;

import android.content.Context;
import android.net.ConnectivityManager;

import com.parse.Parse;

/**
 * Created by ifta on 12/3/16.
 */

public class Utils {
    public static void initializeParse(Context context) {
        //heroku parse config
//        Parse.initialize(new Parse.Configuration.Builder(context)
//                .applicationId("MB1TsAuUKlb7UgtB8egT")
//                .server("http://iparse.herokuapp.com/parse")
//                .enableLocalDataStore()
//                .build()
//        );
        //b4a parse config
        Parse.initialize(new Parse.Configuration.Builder(context)
                .applicationId("4qwrIVrsVl9wobCdTvLEqyR9kYK0a3T2vkXBI4A0")
                .clientKey("i7G2cuRxKa5mPRyxG5GpOcXPf3pPMbfBlNdPJp9D")
                .server("https://parseapi.back4app.com/").build()
        );
    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}

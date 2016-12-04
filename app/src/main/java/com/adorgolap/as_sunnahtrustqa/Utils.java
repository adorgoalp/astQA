package com.adorgolap.as_sunnahtrustqa;

import android.content.Context;

import com.parse.Parse;

/**
 * Created by ifta on 12/3/16.
 */

public class Utils {
    public static void initializeParse(Context context) {
        Parse.initialize(new Parse.Configuration.Builder(context)
                .applicationId("MB1TsAuUKlb7UgtB8egT")
                .server("http://iparse.herokuapp.com/parse")
                .enableLocalDataStore()
                .build()
        );
    }
}
